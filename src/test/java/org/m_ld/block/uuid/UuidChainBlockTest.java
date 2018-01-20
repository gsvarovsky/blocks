package org.m_ld.block.uuid;

import org.junit.Test;
import org.m_ld.block.Block;

import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class UuidChainBlockTest
{
    @Test
    public void testGenesisBlockIsAlwaysDifferent()
    {
        assertNotEquals(UuidChainBlock.genesis(), UuidChainBlock.genesis());
    }

    @Test
    public void testNamespaceGenesisBlockIsAlwaysDifferent()
    {
        assertNotEquals(UuidChainBlock.genesis("a"), UuidChainBlock.genesis("a"));
    }

    @Test
    public void testNextBlockSameData()
    {
        assertTrue(Stream.iterate(UuidChainBlock.genesis(), b -> b.next(1)).limit(10).reduce((b1, b2) -> {
            assertNotEquals(b1, b2);
            return b2;
        }).isPresent());
    }

    @Test
    public void testNextBlockDifferentData()
    {
        assertTrue(Stream.iterate(UuidChainBlock.<Integer>genesis().next(0),
                                  b -> b.next(b.data() + 1)).limit(10).reduce((b1, b2) -> {
            assertNotEquals(b1, b2);
            return b2;
        }).isPresent());
    }

    @Test
    public void testBranchesWithSameData()
    {
        final Block<UUID, Integer> genesis = UuidChainBlock.genesis();
        assertEquals(genesis.next(1), genesis.next(1));
        assertEquals(genesis.next(1).next(2), genesis.next(1).next(2));
    }

    @Test
    public void testBranchesWithSameDataDifferentOrder()
    {
        final Block<UUID, Integer> genesis = UuidChainBlock.genesis();
        assertNotEquals(genesis.next(2).next(1), genesis.next(1).next(2));
    }

    @Test(timeout = 100)
    public void testPerformance()
    {
        // This is just a smoke test for some crazy mistake
        assertEquals(100, Stream.iterate(UuidChainBlock.<Integer>genesis().next(0),
                                  b -> b.next(b.data() + 1)).limit(100).count());
    }
}
