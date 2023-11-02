package org.waitlight.codememo.utils.image;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class ImageUtilTest {

    @Test
    void compress() throws IOException {
        String sourcePath = "C://Users/10963/Pictures/IMG_6290.jpg";
        String outputFilePath = "C://Users/10963/Pictures/images/IMG_6290" + System.currentTimeMillis() + ".jpg";
         new ImageUtil().sourcePath(sourcePath).compress().toFile(outputFilePath);
    }
}