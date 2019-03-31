/*
 * Copyright (c) George Svarovsky 2019. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for full license information.
 */

package org.m_ld.blocks;

public abstract class AbstractBlock<ID, D> implements Block<ID, D>
{
    private final ID id;
    private final D data;

    public AbstractBlock(ID id, D data)
    {
        if (id == null)
            throw new NullPointerException("Block ID cannot be null");

        this.id = id;
        this.data = data;
    }

    @Override
    public ID id()
    {
        return id;
    }

    @Override
    public D data()
    {
        return data;
    }

    @Override
    public int hashCode()
    {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof Block && this.id.equals(((Block)obj).id());
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName() + '{' +
            "id=" + id +
            ", data=" + data +
            '}';
    }
}
