/*
 * Copyright (c) George Svarovsky 2019. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for full license information.
 */

package org.m_ld.blocks.uuid;

import org.junit.Test;
import org.m_ld.blocks.Block;
import org.m_ld.blocks.BlockTest;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertNotEquals;

public abstract class UuidChainBlockTest<D> extends BlockTest<UUID, D>
{
    @Test
    public void testBranchesWithSameDataDifferentOrder()
    {
        final List<D> values = values().limit(2).collect(toList());
        final Block<UUID, D> genesis = genesis();
        assertNotEquals(genesis.next(values.get(1)).next(values.get(0)),
                        genesis.next(values.get(0)).next(values.get(1)));
    }
}
