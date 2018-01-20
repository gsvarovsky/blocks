package org.m_ld.block.uuid;

import org.m_ld.block.AbstractBlock;
import org.m_ld.block.Block;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import static com.fasterxml.uuid.Generators.nameBasedGenerator;
import static com.fasterxml.uuid.Generators.randomBasedGenerator;

/**
 * A block using SHA-256 type 5 UUIDs for block identity, and arbitrary serializable data.
 *
 * @param <D> the data type
 */
public class UuidChainBlock<D extends Serializable> extends AbstractBlock<UUID, D> implements Serializable
{
    private final UUID namespace;

    private static MessageDigest DIGEST;

    static
    {
        try
        {
            // Default digest in JUG is SHA-1, which is too weak for blockchain
            DIGEST = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new Error(e);
        }
    }

    public static <D extends Serializable> Block<UUID, D> genesis()
    {
        return genesis((UUID)null);
    }

    public static <D extends Serializable> Block<UUID, D> genesis(String namespace)
    {
        return genesis(namespace == null ? null : nameBasedGenerator(null, DIGEST).generate(namespace));
    }

    private static <D extends Serializable> Block<UUID, D> genesis(UUID namespace)
    {
        return new UuidChainBlock<>(randomBasedGenerator().generate(), null, namespace);
    }

    private UuidChainBlock(UUID id, D data, UUID namespace)
    {
        super(id, data);
        this.namespace = namespace;
    }

    @Override
    public Block<UUID, D> next(D data)
    {
        try (final ByteArrayOutputStream bo = new ByteArrayOutputStream();
             final ObjectOutputStream oo = new ObjectOutputStream(bo))
        {
            oo.writeObject(id());
            oo.writeObject(data);
            oo.flush();

            return new UuidChainBlock<>(nameBasedGenerator(namespace, DIGEST).generate(bo.toByteArray()),
                                        data, namespace);
        }
        catch (IOException e)
        {
            throw new IllegalStateException("Block data cannot be hashed", e);
        }
    }

    @Override
    public String toString()
    {
        return "UuidChainBlock{" +
            "id=" + id() +
            ", data=" + data() +
            ", namespace=" + namespace +
            "}";
    }
}
