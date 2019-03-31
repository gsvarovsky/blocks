/*
 * Copyright (c) George Svarovsky 2019. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for full license information.
 */

package org.m_ld.blocks.uuid;

import org.junit.Test;
import org.m_ld.blocks.Block;

import java.io.*;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class UuidSerialChainBlockTest extends UuidChainBlockTest<Integer>
{
    @Override
    protected Block<UUID, Integer> genesis()
    {
        return UuidSerialChainBlock.genesis();
    }

    @Override
    protected Stream<Integer> values()
    {
        return Stream.iterate(0, v -> v + 1);
    }

    @Test
    public void testSerializable() throws IOException, ClassNotFoundException
    {
        final Block<UUID, Integer> block = genesis().next(1);
        final ByteArrayOutputStream bo = new ByteArrayOutputStream();
        final ObjectOutputStream os = new ObjectOutputStream(bo);
        os.writeObject(block);
        os.flush();

        assertEquals(block, new ObjectInputStream(new ByteArrayInputStream(bo.toByteArray())).readObject());
    }
}
