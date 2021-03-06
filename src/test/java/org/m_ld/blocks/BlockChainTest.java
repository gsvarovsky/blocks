/*
 * Copyright (c) George Svarovsky 2019. All rights reserved.
 * Licensed under the MIT License. See LICENSE file in the project root for full license information.
 */

package org.m_ld.blocks;

import org.junit.Test;
import org.m_ld.blocks.uuid.UuidSerialChainBlock;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * An overall usage example
 */
public class BlockChainTest
{
    static class Data implements Serializable
    {
        final long timestamp;
        final String creator;
        final String payload;

        Data(String creator, String payload)
        {
            this.payload = payload;
            this.timestamp = new Date().getTime();
            this.creator = creator;
        }

        @Override
        public String toString()
        {
            return "Data{timestamp=" + timestamp + ", creator='" + creator + '\'' + ", payload='" + payload + "'}";
        }
    }

    @Test
    public void testOverallUsage()
    {
        final InMemoryBlockChain<UUID, Data> blockChain = new InMemoryBlockChain<>(UuidSerialChainBlock.genesis());
        blockChain.add(new Data("gsvarovsky", "proof-of-work 1"));
        blockChain.add(new Data("gsvarovsky", "proof-of-work 2"));

        // Not really necessary if I've just created it, but it feels good
        blockChain.verify();

        System.out.println("Data:");
        blockChain.forEach(System.out::println);

        System.out.println("Blocks:");
        blockChain.blocks().forEach(System.out::println);
    }
}
