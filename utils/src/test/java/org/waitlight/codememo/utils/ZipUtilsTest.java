package org.waitlight.codememo.utils;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class ZipUtilsTest {

    @Test
    void unzip_mac() throws IOException {
        ZipUtils.unzip("C://Users/10963/Pictures/test-mac.zip", "C://Users/10963/Pictures/extra");
    }

    @Test
    void unzip_win() throws IOException {
        ZipUtils.unzip("C://Users/10963/Pictures/test-win.zip", "C://Users/10963/Pictures/extra");
    }
}