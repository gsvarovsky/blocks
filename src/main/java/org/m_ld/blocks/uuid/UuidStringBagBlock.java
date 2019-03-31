/*
 * Copyright (c) George Svarovsky 2019. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for full license information.
 */

package org.m_ld.blocks.uuid;

import org.m_ld.blocks.Block;

import java.math.BigInteger;
import java.util.UUID;

import static org.m_ld.blocks.uuid.UuidBlocks.digest;

/**
 * A {@link UuidBagBlock} using SHA-256 type 5 UUIDs for block identity, and string data.
 */
public class UuidStringBagBlock extends UuidBagBlock<String>
{
    public static Block<UUID, String> genesis()
    {
        return UuidBlocks.genesis(UuidStringBagBlock::new);
    }

    private UuidStringBagBlock(BigInteger sum, String data)
    {
        super(sum, data);
    }

    private UuidStringBagBlock(UUID id, String data)
    {
        super(id, data);
    }

    @Override
    protected Block<UUID, String> construct(BigInteger sum, String data)
    {
        return new UuidStringBagBlock(sum, data);
    }

    @Override
    protected byte[] hash(String data)
    {
        return UuidBlocks.hash(digest("SHA-256"), data);
    }
}
