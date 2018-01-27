package org.m_ld.block.uuid;

import org.m_ld.block.AbstractBlock;
import org.m_ld.block.Block;

import java.util.UUID;

import static org.m_ld.block.uuid.UuidBlocks.toUuid;

/**
 * A block with normal block-chain semantics.
 * <p>
 * Thus (assuming a <code>genesis</code> block, and for arbitrary values <code>a</code>
 * and <code>b</code> of type <code>D</code>):<ul>
 * <li><code>genesis.next(a).equals(genesis.next(a))</code></li>
 * <li><code>!genesis.next(a).equals(genesis.next(b))</code></li>
 * <li><code>!genesis.next(a).next(b).equals(genesis.next(b).next(a))</code></li>
 * </ul>
 */
public abstract class UuidChainBlock<D> extends AbstractBlock<UUID, D>
{
    protected UuidChainBlock(UUID id, D data)
    {
        super(id, data);
    }

    @Override
    public Block<UUID, D> next(D data)
    {
        return construct(toUuid(hash(id(), data)), data);
    }

    protected abstract Block<UUID, D> construct(UUID newId, D data);

    protected abstract byte[] hash(UUID id, D data);
}
