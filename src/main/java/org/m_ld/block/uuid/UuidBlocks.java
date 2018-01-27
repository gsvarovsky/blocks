package org.m_ld.block.uuid;

import org.m_ld.block.Block;

import java.io.*;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.function.BiFunction;

import static com.fasterxml.uuid.Generators.randomBasedGenerator;
import static com.fasterxml.uuid.UUIDType.NAME_BASED_SHA1;
import static com.fasterxml.uuid.impl.UUIDUtil.constructUUID;

public interface UuidBlocks
{
    static MessageDigest digest(String algorithm)
    {
        try
        {
            return MessageDigest.getInstance(algorithm);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new IllegalArgumentException(e);
        }
    }

    static UUID toUuid(byte[] hash)
    {
        return constructUUID(NAME_BASED_SHA1, hash);
    }

    OutputStream IGNORE_OUTPUT = new OutputStream()
    {
        @Override
        public void write(int b)
        {
        }
    };

    static byte[] hash(MessageDigest digest, Serializable... data)
    {
        try (final DigestOutputStream dio = new DigestOutputStream(IGNORE_OUTPUT, digest);
             final ObjectOutputStream oo = new ObjectOutputStream(dio))
        {
            for (Serializable datum : data)
                oo.writeObject(datum);
            oo.flush();
            return digest.digest();
        }
        catch (IOException e)
        {
            throw new IllegalStateException("Data cannot be serialized", e);
        }
    }

    static byte[] hash(String data)
    {
        try
        {
            return UuidBlocks.digest("SHA-256").digest(data.getBytes("UTF-8"));
        }
        catch (UnsupportedEncodingException e)
        {
            throw new IllegalStateException("UTF-8 not supported", e);
        }
    }

    static <D> Block<UUID, D> genesis(BiFunction<UUID, D, Block<UUID, D>> create)
    {
        return create.apply(randomBasedGenerator().generate(), null);
    }
}
