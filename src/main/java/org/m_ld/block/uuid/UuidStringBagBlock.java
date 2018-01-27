package org.m_ld.block.uuid;

import org.m_ld.block.Block;

import java.math.BigInteger;
import java.util.UUID;

/**
 * A {@link UuidBagBlock} using SHA-256 type 5 UUIDs for block identity, and string data.
 */
public class UuidStringBagBlock extends UuidBagBlock<String>
{
    public static Block<UUID, String> genesis()
    {
        return UuidBlocks.genesis(UuidStringBagBlock::new);
    }

    private UuidStringBagBlock(BigInteger sum, String data)
    {
        super(sum, data);
    }

    private UuidStringBagBlock(UUID id, String data)
    {
        super(id, data);
    }

    @Override
    protected Block<UUID, String> construct(BigInteger sum, String data)
    {
        return new UuidStringBagBlock(sum, data);
    }

    @Override
    protected byte[] hash(String data)
    {
        return UuidBlocks.hash(data);
    }
}
