/*
 * Copyright (c) George Svarovsky 2019. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for full license information.
 */

package org.m_ld.blocks.hash;

import org.junit.Test;
import org.m_ld.blocks.Block;

import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

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

    @Test public void testStandardBlock1()
    {
        assertEquals("a4ayc/80/OGda4BO/1o/V0etpOqiLx1JwB5S3beHW0s=",
                     HashStringBagBlock.genesis("1").id().toString());
    }

    @Test public void testStandardBlock2()
    {
        assertEquals("P/oQriWTE9B9qtnAivWcWklJrMNY9Dzamli5TKObBoA=",
                     HashStringBagBlock.genesis("1").next("2").id().toString());
    }
}
