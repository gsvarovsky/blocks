package org.m_ld.blocks.uuid;

import org.m_ld.blocks.AbstractBlock;
import org.m_ld.blocks.Block;

import java.math.BigInteger;
import java.util.UUID;

import static com.fasterxml.uuid.impl.UUIDUtil.asByteArray;
import static org.m_ld.blocks.uuid.UuidBlocks.toUuid;
import static org.m_ld.blocks.uuid.UuidBlocks.truncate;

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
public abstract class UuidBagBlock<D> extends AbstractBlock<UUID, D>
{
    private final BigInteger sum;

    protected UuidBagBlock(BigInteger sum, D data)
    {
        super(toUuid(truncate(sum.toByteArray())), data);
        this.sum = sum;
    }

    protected UuidBagBlock(UUID id, D data)
    {
        super(id, data);
        this.sum = new BigInteger(asByteArray(id));
    }

    @Override
    public Block<UUID, D> next(D data)
    {
        return construct(sum.add(new BigInteger(truncate(hash(data)))), data);
    }

    protected abstract Block<UUID, D> construct(BigInteger sum, D data);

    protected abstract byte[] hash(D data);
}
