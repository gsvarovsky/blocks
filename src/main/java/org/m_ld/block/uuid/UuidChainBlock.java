package org.m_ld.block.uuid;

import org.m_ld.block.AbstractBlock;
import org.m_ld.block.Block;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import static com.fasterxml.uuid.Generators.nameBasedGenerator;
import static com.fasterxml.uuid.Generators.randomBasedGenerator;

/**
 * A block using SHA-256 type 5 UUIDs for block identity, and arbitrary serializable data.
 *
 * @param <D> the data type
 */
public class UuidChainBlock<D extends Serializable> extends AbstractBlock<UUID, D> implements Serializable
{
    private static final MessageDigest DIGEST;
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

    public static <D extends Serializable> Block<UUID, D> genesis()
    {
        return new UuidChainBlock<>(randomBasedGenerator().generate(), null);
    }

    private UuidChainBlock(UUID id, D data)
    {
        super(id, data);
    }

    @Override
    public Block<UUID, D> next(D data)
    {
        return construct(hash(serialize(id(), data)), data);
    }

    protected Block<UUID, D> construct(UUID newId, D data)
    {
        return new UuidChainBlock<>(newId, data);
    }

    protected byte[] serialize(UUID id, D data)
    {
        try (final ByteArrayOutputStream bo = new ByteArrayOutputStream();
             final ObjectOutputStream oo = new ObjectOutputStream(bo))
        {
            oo.writeObject(id);
            oo.writeObject(data);
            oo.flush();

            return bo.toByteArray();
        }
        catch (IOException e)
        {
            throw new IllegalStateException("Block data cannot be hashed", e);
        }
    }

    protected UUID hash(byte[] bytes)
    {
        return nameBasedGenerator(null, DIGEST).generate(bytes);
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName() + "{" +
            "id=" + id() +
            ", data=" + data() +
            "}";
    }

    private Object writeReplace() throws ObjectStreamException
    {
        return new SerialProxy<>(id(), data());
    }

    private static class SerialProxy<D extends Serializable> implements Serializable
    {
        UUID id;
        D data;

        SerialProxy(UUID id, D data)
        {
            this.id = id;
            this.data = data;
        }

        Object readResolve() throws ObjectStreamException
        {
            return new UuidChainBlock<>(id, data);
        }
    }
}
