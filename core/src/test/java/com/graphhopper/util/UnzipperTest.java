package com.graphhopper.util;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UnzipperTest {
    @Disabled("Temporairement désactivé")
    @Test
    public void testUnzip() throws Exception {
        String to = "./target/tmp/test";
        Helper.removeDir(new File(to));
        new Unzipper().unzip("./src/test/resources/com/graphhopper/util/test.zip", to, false);
        assertTrue(new File("./target/tmp/test/file2 bäh").exists());
        assertTrue(new File("./target/tmp/test/folder1").isDirectory());
        assertTrue(new File("./target/tmp/test/folder1/folder 3").isDirectory());
        Helper.removeDir(new File(to));
    }


}
