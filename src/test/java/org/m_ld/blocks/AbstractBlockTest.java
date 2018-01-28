package org.m_ld.blocks;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class AbstractBlockTest
{
    class DummyBlock extends AbstractBlock<String, String>
    {
        public DummyBlock(String s, String data)
        {
            super(s, data);
        }

        @Override
        public Block<String, String> next(String data)
        {
            return null;
        }
    }

    @Test
    public void testReportsMembers()
    {
        final DummyBlock dummy = new DummyBlock("a", "b");
        assertEquals("a", dummy.id());
        assertEquals("b", dummy.data());
    }

    @Test
    public void testEqualityById()
    {
        assertEquals(new DummyBlock("a", "a"),
                     new DummyBlock("a", "a"));
        assertEquals(new DummyBlock("a", "a"),
                     new DummyBlock("a", "b"));
        assertNotEquals(new DummyBlock("a", "a"),
                        new DummyBlock("b", "a"));
    }
}
