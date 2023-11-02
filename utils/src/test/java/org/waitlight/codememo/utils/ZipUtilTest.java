package org.waitlight.codememo.utils;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

class ZipUtilTest {

    @Test
    void unzip_mac() throws IOException {
        ZipUtil.unzip("C://Users/10963/Pictures/test-mac.zip", StandardCharsets.UTF_8, "C://Users/10963/Pictures/extra");
    }

    @Test
    void unzip_win() throws IOException {
        ZipUtil.unzip("C://Users/10963/Pictures/test-win.zip", Charset.forName("GBK"), "C://Users/10963/Pictures/extra");
    }
}