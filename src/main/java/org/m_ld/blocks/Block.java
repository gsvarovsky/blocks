/*
 * Copyright (c) George Svarovsky 2019. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for full license information.
 */

package org.m_ld.blocks;

/**
 * A block in a blockchain. Encapsulates data (or a hash of data) and an identity
 *
 * @param <ID> The identity type
 * @param <D> The data type (or hash type)
 */
public interface Block<ID, D>
{
    ID id();

    /**
     * @return the block data or data hash. Can be <code>null</code>.
     */
    D data();

    /**
     * Constructs a new block with the given data, verifiably chained from this one.
     * <p>
     * This method MUST:<ul>
     * <li>Be Deterministic: produce the same result for the same inputs (including <code>this</code>)</li>
     * <li>Produce a new block with an Id that is:<ul>
     *     <li>based on this block's Id and the new data</li>
     *     <li>unique according to some defined constraint</li>
     * </ul></li>
     * </ul>
     *
     * @param data the data content of the new block (or a suitable hash thereof)
     * @return a new block to be used in a blockchain
     */
    Block<ID, D> next(D data);
}
