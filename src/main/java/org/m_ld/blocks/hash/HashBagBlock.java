/*
 * Copyright (c) George Svarovsky 2019. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for full license information.
 */

package org.m_ld.blocks.hash;

import org.m_ld.blocks.AbstractBlock;
import org.m_ld.blocks.Block;

/**
 * A "BagBlock" is a block for which the chain ordering is unimportant.
 * <p>
 * Thus (assuming a <code>genesis</code> block, and for arbitrary values <code>a</code>
 * and <code>b</code> of type <code>D</code>):<ul>
 * <li><code>genesis.next(a).equals(genesis.next(a))</code></li>
 * <li><code>!genesis.next(a).equals(genesis.next(b))</code></li>
 * <li><strong><code>genesis.next(a).next(b).equals(genesis.next(b).next(a))</code></strong></li>
 * </ul>
 */
public abstract class HashBagBlock<D> extends AbstractBlock<Hash, D>
{
    protected HashBagBlock(Hash id, D data)
    {
        super(id, data);
    }

    @Override
    public Block<Hash, D> next(D data)
    {
        return construct(id().add(hash(data)), data);
    }

    protected abstract Block<Hash, D> construct(Hash id, D data);

    protected abstract Hash hash(D data);
}
