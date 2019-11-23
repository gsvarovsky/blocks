/*
 * Copyright (c) George Svarovsky 2019. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for full license information.
 */

package org.m_ld.blocks.hash;

import org.junit.Test;
import org.m_ld.blocks.Block;
import org.m_ld.blocks.BlockTest;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public abstract class HashBagBlockTest<D> extends BlockTest<Hash, D>
{
    @Test
    public void testBranchesWithSameTwoDataDifferentOrder()
    {
        final List<D> values = values().limit(2).collect(toList());
        final Block<Hash, D> genesis = genesis();
        assertEquals(genesis.next(values.get(1)).next(values.get(0)),
                     genesis.next(values.get(0)).next(values.get(1)));
    }

    @Test
    public void testBranchesWithSameDataDifferentOrder()
    {
        // 1000 is enough to be very sure we will hit any signed-integer manipulation issues
        final List<D> values = values().limit(1000).collect(toList());
        final Block<Hash, D> genesis = genesis();

        final List<Block<Hash, D>> finalBlocks = Stream.generate(() -> {
            Collections.shuffle(values);
            return values.stream().reduce(genesis, Block::next, (b1, b2) -> unexpected());
        }).limit(10).collect(toList());

        final Block<Hash, D> first = finalBlocks.get(0);
        finalBlocks.stream().skip(1).forEach(next -> assertEquals(first.id(), next.id()));
    }

    private <T> T unexpected()
    {
        fail();
        return null;
    }
}
