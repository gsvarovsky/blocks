package org.m_ld.blocks.uuid;

import org.m_ld.blocks.Block;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.function.BiFunction;

import static com.fasterxml.uuid.Generators.randomBasedGenerator;
import static com.fasterxml.uuid.UUIDType.NAME_BASED_SHA1;
import static com.fasterxml.uuid.impl.UUIDUtil.constructUUID;
import static java.util.Arrays.copyOf;

public interface UuidBlocks
{
    Charset UTF_8 = Charset.forName("UTF-8");

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

    OutputStream IGNORE_OUTPUT = new OutputStream()
    {
        @Override
        public void write(int b)
        {
        }
    };

    static UUID toUuid(byte[] hash)
    {
        return constructUUID(NAME_BASED_SHA1, hash);
    }

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

    static byte[] hash(MessageDigest digest, String... data)
    {
        for (String datum : data)
            digest.update(datum.getBytes(UTF_8));
        return digest.digest();
    }

    static <D> Block<UUID, D> genesis(BiFunction<UUID, D, Block<UUID, D>> create)
    {
        return create.apply(randomBasedGenerator().generate(), null);
    }

    static byte[] truncate(byte[] hash)
    {
        // UUID can only cope with 16 bytes, anything bigger is wasted space
        return copyOf(hash, 16);
    }
}
