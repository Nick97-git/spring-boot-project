package com.amazon.review.util;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FileUtilTest {
    private final FileUtil fileUtil = new FileUtil();

    @Test
    public void checkFailedReadFromFile() {
        String pathToFile = "/test.csv";
        Assertions.assertThrows(RuntimeException.class, () -> {
            fileUtil.readFromFile(pathToFile);
        });
    }

    @Test
    public void checkSuccessReadFromFile() {
        String pathToFile = "/testData.csv";
        List<String> rows = fileUtil.readFromFile(pathToFile);
        Assertions.assertEquals(501, rows.size());
    }
}
