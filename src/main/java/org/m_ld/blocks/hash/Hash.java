/*
 * Copyright (c) George Svarovsky 2019. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for full license information.
 */

package org.m_ld.blocks.hash;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Hash
{
    private static final int BYTE_WIDTH = 32;
    private static final ThreadLocal<MessageDigest> DIGEST = ThreadLocal.withInitial(() -> {
        try
        {
            return MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new IllegalArgumentException(e);
        }
    });
    private static final OutputStream IGNORE_OUTPUT = new OutputStream()
    {
        @Override
        public void write(int b)
        {
        }
    };
    /**
     * Always 32 bytes
     */
    private final BigInteger value;

    public static Hash digest(Serializable... data)
    {
        try (final DigestOutputStream dio = new DigestOutputStream(IGNORE_OUTPUT, DIGEST.get());
             final ObjectOutputStream oo = new ObjectOutputStream(dio))
        {
            for (Serializable datum : data)
                oo.writeObject(datum);
            oo.flush();
            return new Hash(DIGEST.get().digest());
        }
        catch (IOException e)
        {
            throw new IllegalStateException("Data cannot be serialized", e);
        }
    }

    public static Hash digest(String... data)
    {
        for (String datum : data)
            DIGEST.get().update(datum.getBytes(UTF_8));
        return new Hash(DIGEST.get().digest());
    }

    public static Hash random()
    {
        byte[] hash = new byte[BYTE_WIDTH];
        ThreadLocalRandom.current().nextBytes(hash);
        return new Hash(hash);
    }

    public static Hash decode(String encoded)
    {
        return new Hash(Base64.getDecoder().decode(encoded));
    }

    public String encode()
    {
        return Base64.getEncoder().encodeToString(toBytes());
    }

    public byte[] toBytes()
    {
        return toWidth(value.toByteArray(), BYTE_WIDTH);
    }

    public Hash add(Hash that)
    {
        return new Hash(value.add(that.value).toByteArray());
    }

    @Override public String toString()
    {
        return encode();
    }

    public boolean equals(Hash that)
    {
        return that != null && this.value.equals(that.value);
    }

    @Override public boolean equals(Object o)
    {
        return o instanceof Hash && this.equals((Hash)o);
    }

    @Override public int hashCode()
    {
        return value.hashCode();
    }

    private Hash(byte[] hash)
    {
        this.value = new BigInteger(toWidth(hash, BYTE_WIDTH));
    }

    public static byte[] toWidth(byte[] src, int width)
    {
        if (src.length == width)
        {
            return src;
        }
        else
        {
            final byte[] rtn = new byte[width];
            if (src.length > width)
            {
                // We truncate the front of the array to allow use for integer overflow
                System.arraycopy(src, src.length - width, rtn, 0, rtn.length);
            }
            else
            {
                // https://en.wikipedia.org/wiki/Two%27s_complement#Sign_extension
                // Pad left with the sign; (byte)-1 is 11111111
                if (src[0] < 0)
                    Arrays.fill(rtn, 0, width - src.length, (byte)-1);
                System.arraycopy(src, 0, rtn, width - src.length, src.length);
            }

            return rtn;
        }
    }
}
