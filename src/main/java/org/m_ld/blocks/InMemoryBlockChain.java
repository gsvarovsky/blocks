package org.m_ld.blocks;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.singleton;

public class InMemoryBlockChain<ID, D> extends AbstractList<D> implements BlockChain<ID, D>
{
    private final List<Block<ID, D>> blocks;

    public InMemoryBlockChain(Block<ID, D> genesis)
    {
        this.blocks = new ArrayList<>(singleton(genesis));
    }

    private InMemoryBlockChain(List<Block<ID, D>> blocks)
    {
        this.blocks = new ArrayList<>(blocks);
    }

    @Override
    public boolean add(D data)
    {
       return blocks.add(blocks.get(blocks.size() - 1).next(data));
    }

    @Override
    public D get(int index)
    {
        return blocks.get(index).data();
    }

    @Override
    public int size()
    {
        return blocks.size();
    }

    @Override
    public Stream<Block<ID, D>> blocks()
    {
        return blocks.stream();
    }

    @Override
    public InMemoryBlockChain<ID, D> subList(int fromIndex, int toIndex)
    {
        return new InMemoryBlockChain<>(blocks.subList(fromIndex, toIndex));
    }
}
