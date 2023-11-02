package org.waitlight.codememo.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipUtil {

    private ZipUtil() {
        throw new UnsupportedOperationException("This is a util class");
    }

    public static void unzip(String zipPath, Charset charset, String destDir) throws IOException {
        InputStream in = Files.newInputStream(Paths.get(zipPath));
        unzip(in, charset, pair -> {
            String path = destDir + File.separator + pair.getName();
            try (FileOutputStream fos = new FileOutputStream(path)) {
                fos.write(pair.getOs().toByteArray());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void unzip(InputStream in, Charset charset, Consumer<EntryStream> consumer) throws IOException {
        if (Objects.isNull(charset)) charset = StandardCharsets.UTF_8;

        try (ZipInputStream zin = new ZipInputStream(in, charset)) {
            ZipEntry ze;
            while (Objects.nonNull(ze = zin.getNextEntry())) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                int len;
                byte[] buffer = new byte[1024];
                while ((len = zin.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
                consumer.accept(new EntryStream(ze.getName(), out));
            }
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class EntryStream {
        private String name;
        private ByteArrayOutputStream os;
    }

}