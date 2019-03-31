/*
 * Copyright (c) George Svarovsky 2019. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for full license information.
 */

package org.m_ld.blocks;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public class InMemoryBlockChainTest
{
    private AbstractBlock<UUID, Integer> genesis;

    @Before
    public void setUp()
    {
        genesis = new TestBlock();
    }

    @Test
    public void testEmptyChain()
    {
        final InMemoryBlockChain<UUID, Integer> chain = new InMemoryBlockChain<>(genesis);
        assertEquals(1, chain.size());
        assertNull(chain.get(0));
        assertNull(chain.blocks().findFirst().orElseThrow(AssertionError::new).data());
    }

    @Test
    public void testSingletonChain()
    {
        final InMemoryBlockChain<UUID, Integer> chain = new InMemoryBlockChain<>(genesis);
        chain.add(1);

        assertEquals(2, chain.size());
        assertNull(chain.get(0));
        assertNull(chain.blocks().findFirst().orElseThrow(AssertionError::new).data());
        assertEquals(1, (int)chain.get(1));
        assertEquals(1, (int)chain.blocks().skip(1).findFirst().orElseThrow(AssertionError::new).data());
    }

    @Test
    public void testSubChain()
    {
        final InMemoryBlockChain<UUID, Integer> chain = new InMemoryBlockChain<>(genesis);
        chain.add(1);
        chain.add(2);
        chain.add(3);

        final InMemoryBlockChain<UUID, Integer> subchain = chain.subList(1, 3);
        assertEquals(2, subchain.size());
        assertEquals(1, (int)subchain.get(0));
        assertEquals(1, (int)subchain.blocks().findFirst().orElseThrow(AssertionError::new).data());
        assertEquals(2, (int)subchain.get(1));
        assertEquals(2, (int)subchain.blocks().skip(1).findFirst().orElseThrow(AssertionError::new).data());
    }

    @Test
    public void testValidChain()
    {
        final InMemoryBlockChain<UUID, Integer> chain = new InMemoryBlockChain<>(genesis);
        chain.add(1);
        chain.add(2);

        chain.verify();
    }

    @Test(expected = IllegalStateException.class)
    public void testInvalidChain()
    {
        final InMemoryBlockChain<UUID, Integer> chain = new InMemoryBlockChain<>(new TestBlock()
        {
            @Override
            public Block<UUID, Integer> next(Integer data)
            {
                // Does not conform to Block specification
                return new TestBlock(UUID.randomUUID(), data);
            }
        });
        chain.add(1);
        chain.add(2);

        chain.verify();
    }

    class TestBlock extends AbstractBlock<UUID, Integer>
    {
        TestBlock()
        {
            this(UUID.randomUUID(), null);
        }

        private TestBlock(UUID uuid, Integer data)
        {
            super(uuid, data);
        }

        @Override
        public Block<UUID, Integer> next(Integer data)
        {
            return new TestBlock(UUID.nameUUIDFromBytes((id().toString() + data).getBytes()), data);
        }
    }
}
