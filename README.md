[![Build Status](https://travis-ci.org/gsvarovsky/blocks.svg?branch=master)](https://travis-ci.org/gsvarovsky/blocks)

# blocks
_a blockchain building blocks library_

**[Feedback](https://github.com/gsvarovsky/blocks/issues) and contributions welcome!**

## usage
Implement [Block](/src/main/java/org/m_ld/block/Block.java), or use the provided [SHA-256 UUID-based chain block](/src/main/java/org/m_ld/block/uuid/UuidChainBlock.java).

Then choose whether to implement [BlockChain](/src/main/java/org/m_ld/block/BlockChain.java), use the provided [InMemoryBlockChain](/src/main/java/org/m_ld/block/InMemoryBlockChain.java), or roll your own container class.

[Here is a full usage example](/src/test/java/org/m_ld/block/uuid/UuidBlockChainTest.java).