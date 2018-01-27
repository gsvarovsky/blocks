package org.m_ld.block.uuid;

import org.m_ld.block.Block;

import java.util.UUID;
import java.util.stream.Stream;

public class UuidStringBagBlockTest extends UuidBagBlockTest<String>
{
    @Override
    protected Block<UUID, String> genesis()
    {
        return UuidStringBagBlock.genesis();
    }

    @Override
    protected Stream<String> values()
    {
        return Stream.generate(UUID::randomUUID).map(UUID::toString);
    }
}
