package org.ipfs.utils;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;

import java.io.*;

public class FileUtils {

    public static String upload(IPFS ipfs, File file) throws IOException {
        String hash = "";
        NamedStreamable.FileWrapper fileWrapper = new NamedStreamable.FileWrapper(file);
        MerkleNode node = ipfs.add(fileWrapper).get(0);
        hash = node.hash.toString();
        return hash;
    }

    public static byte[] download(IPFS ipfs, String hash) throws IOException {
        Multihash multihash = Multihash.fromBase58(hash);
        return ipfs.cat(multihash);
    }
    //QmXpZjnfB8p6rGZa2opbZD6ns9nEdCS31ZpC8jn2vUYHTA
}
