package org.m_ld.block.uuid;

import org.m_ld.block.Block;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.UUID;

import static org.m_ld.block.uuid.UuidBlocks.digest;

/**
 * A {@link UuidChainBlock} using SHA-256 type 5 UUIDs for block identity, and arbitrary serializable data.
 */
public class UuidSerialChainBlock<D extends Serializable> extends UuidChainBlock<D> implements Serializable
{
    public static <D extends Serializable> Block<UUID, D> genesis()
    {
        return UuidBlocks.genesis(UuidSerialChainBlock::new);
    }

    private UuidSerialChainBlock(UUID id, D data)
    {
        super(id, data);
    }

    @Override
    protected Block<UUID, D> construct(UUID newId, D data)
    {
        return new UuidSerialChainBlock<>(newId, data);
    }

    @Override
    protected byte[] hash(UUID id, D data)
    {
        return UuidBlocks.hash(digest("SHA-256"), id, data);
    }

    private Object writeReplace() throws ObjectStreamException
    {
        return new SerialProxy<>(id(), data());
    }

    protected static class SerialProxy<D extends Serializable> implements Serializable
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
            return new UuidSerialChainBlock<>(id, data);
        }
    }
}
