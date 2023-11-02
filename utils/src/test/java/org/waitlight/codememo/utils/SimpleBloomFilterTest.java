package org.waitlight.codememo.utils;

import org.junit.jupiter.api.Test;
import org.waitlight.codememo.utils.SimpleBloomFilter;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SimpleBloomFilterTest {

    @Test
    void contains() throws NoSuchAlgorithmException {
        SimpleBloomFilter f = new SimpleBloomFilter(1024, 3);
        f.add("tom");
        f.add("jerry");

        assertTrue(f.contains("tom"));
        assertFalse(f.contains("tom1"));
    }
}