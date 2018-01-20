package org.m_ld.block;

import java.util.List;

/**
 * A blockchain representation that implements {@link List} for convenience.
 * The list can be added to, but cannot be removed from.
 * May represent a sub-list of a complete blockchain.
 * <p>
 * Serializable implementations should verify the consistency when de-serializing.
 *
 * @param <ID>
 * @param <D>
 */
public interface BlockChain<ID, D> extends List<D>
{
    /**
     * @return the actual blocks that make up this blockchain, as an immutable list.
     */
    List<Block<ID, D>> blocks();

    default Block<ID, D> verify() throws IllegalStateException
    {
        return blocks().stream().reduce((b1, b2) -> {
            if (!b1.next(b2.data()).equals(b2))
                throw new IllegalStateException("Block chain is inconsistent");
            return b2;
        }).orElseThrow(() -> new IllegalStateException("Empty block chain"));
    }
}
