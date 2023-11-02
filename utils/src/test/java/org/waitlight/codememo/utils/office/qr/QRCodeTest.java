package org.waitlight.codememo.utils.office.qr;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class QRCodeTest {
    @Test
    void normal() {
        String content = "{\"id\":12321313,\"name\":\"王德法\",\"age\":123}";
        String logo = "C:/Users/10963/Pictures/1024.png";
        String summary = "A栋1单元2楼2002号";
        String org = null;
        String outputDir = "C:/Users/10963/Pictures/qr";

        assertDoesNotThrow(() -> new QRCode(content, logo, summary, org).draw().toPng(outputDir));
    }
}