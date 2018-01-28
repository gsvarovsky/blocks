package org.m_ld.block.uuid;

import org.junit.Test;
import org.m_ld.block.Block;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;

public abstract class UuidBlockTest<D>
{
    protected abstract Block<UUID, D> genesis();

    protected abstract Stream<D> values();

    @Test
    public void testGenesisBlockIsAlwaysDifferent()
    {
        assertNotEquals(genesis(), genesis());
    }

    @Test
    public void testNextBlockSameData()
    {
        final D value = values().findFirst().orElseThrow(AssertionError::new);
        assertTrue(Stream.iterate(genesis(), b -> b.next(value)).limit(10).reduce((b1, b2) -> {
            assertNotEquals(b1, b2);
            return b2;
        }).isPresent());
    }

    @Test
    public void testNextBlockDifferentData()
    {
        final Iterator<D> values = values().iterator();
        assertTrue(Stream.iterate(genesis(), b -> b.next(values.next())).limit(10).reduce((b1, b2) -> {
            assertNotEquals(b1, b2);
            return b2;
        }).isPresent());
    }

    @Test
    public void testBranchesWithSameData()
    {
        final List<D> values = values().limit(2).collect(toList());
        final Block<UUID, D> genesis = genesis();
        assertEquals(genesis.next(values.get(0)), genesis.next(values.get(0)));
        assertEquals(genesis.next(values.get(0)).next(values.get(1)), genesis.next(values.get(0)).next(values.get(1)));
    }

    @Test(timeout = 1000)
    public void testPerformance()
    {
        // This is just a smoke test for some crazy mistake
        final Iterator<D> values = values().iterator();
        assertEquals(1000, Stream.iterate(genesis(), b -> b.next(values.next())).limit(1000).count());
    }
}
