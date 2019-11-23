/*
 * Copyright (c) George Svarovsky 2019. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for full license information.
 */

package org.m_ld.blocks.uuid;

import org.m_ld.blocks.Block;
import org.m_ld.blocks.hash.Hash;

import java.util.UUID;

import static java.util.UUID.randomUUID;

/**
 * A {@link UuidChainBlock} using SHA-256 type 5 UUIDs for block identity, and string data.
 */
public class UuidStringChainBlock extends UuidChainBlock<String>
{
    public static Block<UUID, String> genesis()
    {
        return new UuidStringChainBlock(randomUUID(), null);
    }

    private UuidStringChainBlock(UUID id, String data)
    {
        super(id, data);
    }

    @Override
    protected Block<UUID, String> construct(UUID newId, String data)
    {
        return new UuidStringChainBlock(newId, data);
    }

    @Override
    protected Hash hash(UUID id, String data)
    {
        return Hash.digest(id, data);
    }
}
