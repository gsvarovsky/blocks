package org.m_ld.block.uuid;

import org.m_ld.block.Block;

import java.util.UUID;

import static org.m_ld.block.uuid.UuidBlocks.digest;

/**
 * A {@link UuidChainBlock} using SHA-256 type 5 UUIDs for block identity, and string data.
 */
public class UuidStringChainBlock extends UuidChainBlock<String>
{
    public static Block<UUID, String> genesis()
    {
        return UuidBlocks.genesis(UuidStringChainBlock::new);
    }

    private UuidStringChainBlock(UUID id, String data)
    {
        super(id, data);
    }

    @Override
    protected Block<UUID, String> construct(UUID newId, String data)
    {
        return new UuidStringChainBlock(newId, data);
    }

    @Override
    protected byte[] hash(UUID id, String data)
    {
        return UuidBlocks.hash(digest("SHA-256"), id, data);
    }
}
