package org.m_ld.block.uuid;

import org.m_ld.block.Block;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.function.BiFunction;

import static com.fasterxml.uuid.Generators.nameBasedGenerator;
import static com.fasterxml.uuid.Generators.randomBasedGenerator;

public interface UuidBlocks
{
    class Sha256
    {
        static final MessageDigest DIGEST;
        static
        {
            try
            {
                DIGEST = MessageDigest.getInstance("SHA-256");
            }
            catch (NoSuchAlgorithmException e)
            {
                throw new Error(e);
            }
        }
    }

    static UUID hash(byte[] bytes)
    {
        return nameBasedGenerator(null, Sha256.DIGEST).generate(bytes);
    }

    static <D extends Serializable> Block<UUID, D> genesis(BiFunction<UUID, D, Block<UUID, D>> create)
    {
        return create.apply(randomBasedGenerator().generate(), null);
    }
}
