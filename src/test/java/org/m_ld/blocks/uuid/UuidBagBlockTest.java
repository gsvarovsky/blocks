package org.m_ld.blocks.uuid;

import org.junit.Test;
import org.m_ld.blocks.Block;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;

public abstract class UuidBagBlockTest<D> extends UuidBlockTest<D>
{
    @Test
    public void testBranchesWithSameTwoDataDifferentOrder()
    {
        final List<D> values = values().limit(2).collect(toList());
        final Block<UUID, D> genesis = genesis();
        assertEquals(genesis.next(values.get(1)).next(values.get(0)),
                     genesis.next(values.get(0)).next(values.get(1)));
    }

    @Test
    public void testBranchesWithSameTenDataDifferentOrder()
    {
        final List<D> values = values().limit(10).collect(toList());
        final Block<UUID, D> genesis = genesis();

        assertTrue(Stream.generate(() -> {
            Collections.shuffle(values);
            return values.stream().reduce(genesis, Block::next, (b1, b2) -> unexpected());
        }).limit(10).reduce((b1, b2) -> {
            assertEquals(b1, b2);
            return b2;
        }).isPresent());
    }

    private <T> T unexpected()
    {
        fail();
        return null;
    }
}
