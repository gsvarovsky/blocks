/*
 * Copyright (c) George Svarovsky 2019. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for full license information.
 */

package org.m_ld.blocks.hash;

import org.m_ld.blocks.Block;

import java.util.UUID;
import java.util.stream.Stream;

public class HashStringBagBlockTest extends HashBagBlockTest<String>
{
    @Override
    protected Block<Hash, String> genesis()
    {
        return HashStringBagBlock.genesis();
    }

    @Override
    protected Stream<String> values()
    {
        return Stream.generate(UUID::randomUUID).map(UUID::toString);
    }
}
