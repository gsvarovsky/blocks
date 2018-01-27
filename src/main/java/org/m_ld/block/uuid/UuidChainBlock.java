package org.m_ld.block.uuid;

import org.m_ld.block.AbstractBlock;
import org.m_ld.block.Block;

import java.io.*;
import java.util.UUID;

/**
 * A block using SHA-256 type 5 UUIDs for block identity, and arbitrary serializable data.
 *
 * @param <D> the data type
 */
public class UuidChainBlock<D extends Serializable> extends AbstractBlock<UUID, D> implements Serializable
{
    public static <D extends Serializable> Block<UUID, D> genesis()
    {
        return UuidBlocks.genesis(UuidChainBlock::new);
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
        return UuidBlocks.hash(bytes);
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
