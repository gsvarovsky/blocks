[![Build Status](https://travis-ci.org/gsvarovsky/blocks.svg?branch=master)](https://travis-ci.org/gsvarovsky/blocks)
![stability-wip](https://img.shields.io/badge/stability-work_in_progress-lightgrey.svg)

# blocks
_a blockchain building blocks library_

**[Feedback](https://github.com/gsvarovsky/blocks/issues) and contributions welcome!**

## usage
Implement [Block](/src/main/java/org/m_ld/blocks/Block.java), or use one of the provided [SHA-256 UUID-based blocks](/src/main/java/org/m_ld/blocks/uuid). (If using these classes, you need to explicitly include `java-uuid-generator` in your build.)

Then choose whether to implement [BlockChain](/src/main/java/org/m_ld/blocks/BlockChain.java), use the provided [InMemoryBlockChain](/src/main/java/org/m_ld/blocks/InMemoryBlockChain.java), or roll your own container class.

[Here is a full usage example](/src/test/java/org/m_ld/blocks/uuid/BlockChainTest.java).

## biblio
### other implementations
Heavyweight frameworks:
* DZone, _The Top 3 Blockchain Libraries for Java Devs_, https://dzone.com/articles/the-top-3-blockchain-libraries-for-java-devs

Simplistic demonstrations:
* GitHub, _A simple implementation of blockchain_, https://github.com/Will1229/Blockchain/blob/master/src/main/java/agent/Block.java
* GitHub, _Simple BlockChain DEMO in JAVA_, https://github.com/Anujraval24/BlockChain/blob/master/src/Block.java