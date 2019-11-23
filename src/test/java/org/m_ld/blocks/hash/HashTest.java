/*
 * Copyright (c) George Svarovsky 2019. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for full license information.
 */

package org.m_ld.blocks.hash;

import org.junit.Test;

import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.*;

public class HashTest
{
    @Test public void testDigestStrings()
    {
        final String one = "1", two = "2", three = "3";
        assertNotEquals(Hash.digest(one), Hash.digest(two));
        assertEquals(Hash.digest(one), Hash.digest(one));
        assertEquals(Hash.digest(one, two), Hash.digest(one, two));
        assertNotEquals(Hash.digest(one, two), Hash.digest(two, one));
        assertNotEquals(Hash.digest(one, two), Hash.digest(one, three));
    }

    @Test public void testDigestSerializables()
    {
        final UUID one = randomUUID(), two = randomUUID(), three = randomUUID();
        assertNotEquals(Hash.digest(one), Hash.digest(two));
        assertEquals(Hash.digest(one), Hash.digest(one));
        assertEquals(Hash.digest(one, two), Hash.digest(one, two));
        assertNotEquals(Hash.digest(one, two), Hash.digest(two, one));
        assertNotEquals(Hash.digest(one, two), Hash.digest(one, three));
    }

    @Test public void testRandom()
    {
        for (int i = 0; i < 100; i++)
            assertNotEquals(Hash.random(), Hash.random());
    }

    @Test public void testEncode()
    {
        assertEquals(44, Hash.random().encode().length());
        assertEquals(44, Hash.digest("1").encode().length());
        assertEquals(44, Hash.digest(randomUUID()).encode().length());
    }

    @Test public void testDecode()
    {
        final Hash hash = Hash.random();
        assertEquals(hash, Hash.decode(hash.encode()));
    }

    @Test public void testToBytes()
    {
        assertEquals(32, Hash.random().toBytes().length);
        assertEquals(32, Hash.digest("1").toBytes().length);
        assertEquals(32, Hash.digest(randomUUID()).toBytes().length);
    }

    @Test public void testAdd()
    {
        final Hash one = Hash.random(), two = Hash.random();
        assertNotEquals(one, one.add(two));
        assertEquals(one.add(two), two.add(one));
    }

    @Test public void testToWidth()
    {
        assertArrayEquals(new byte[]{0}, Hash.toWidth(new byte[]{0}, 1));
        assertArrayEquals(new byte[]{0,0}, Hash.toWidth(new byte[]{0}, 2));
        assertArrayEquals(new byte[]{0,1}, Hash.toWidth(new byte[]{1}, 2));
        assertArrayEquals(new byte[]{-1,-1}, Hash.toWidth(new byte[]{-1}, 2));
        assertArrayEquals(new byte[]{0}, Hash.toWidth(new byte[]{0,0}, 1));
        assertArrayEquals(new byte[]{0}, Hash.toWidth(new byte[]{1,0}, 1));
        assertArrayEquals(new byte[]{1}, Hash.toWidth(new byte[]{1,1}, 1));
    }
}