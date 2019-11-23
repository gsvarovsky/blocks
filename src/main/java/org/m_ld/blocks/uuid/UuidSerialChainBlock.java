/*
 * Copyright (c) George Svarovsky 2019. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for full license information.
 */

package org.m_ld.blocks.uuid;

import org.m_ld.blocks.Block;
import org.m_ld.blocks.hash.Hash;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.UUID;

import static java.util.UUID.randomUUID;

/**
 * A {@link UuidChainBlock} using SHA-256 type 5 UUIDs for block identity, and arbitrary serializable data.
 */
public class UuidSerialChainBlock<D extends Serializable> extends UuidChainBlock<D> implements Serializable
{
    public static <D extends Serializable> Block<UUID, D> genesis()
    {
        return new UuidSerialChainBlock<>(randomUUID(), null);
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
    protected Hash hash(UUID id, D data)
    {
        return Hash.digest(id, data);
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
