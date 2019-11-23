/*
 * Copyright (c) George Svarovsky 2019. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for full license information.
 */

package org.m_ld.blocks.hash;

import org.m_ld.blocks.Block;

/**
 * A {@link HashBagBlock} using SHA-256 type 5 UUIDs for block identity, and string data.
 */
public class HashStringBagBlock extends HashBagBlock<String>
{
    public static Block<Hash, String> genesis()
    {
        return new HashStringBagBlock(Hash.random(), null);
    }

    private HashStringBagBlock(Hash id, String data)
    {
        super(id, data);
    }

    @Override
    protected Block<Hash, String> construct(Hash id, String data)
    {
        return new HashStringBagBlock(id, data);
    }

    @Override
    protected Hash hash(String data)
    {
        return Hash.digest(data);
    }
}
