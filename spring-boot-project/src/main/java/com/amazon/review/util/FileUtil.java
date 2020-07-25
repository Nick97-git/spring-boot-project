package com.amazon.review.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FileUtil {

    public List<String> readFromFile(String path) {
        InputStream inputStream = getClass().getResourceAsStream(path);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            List<String> lines = reader.lines().collect(Collectors.toList());
            log.info("File has been read!");
            return lines;
        } catch (IOException e) {
            throw new RuntimeException("Reading of file has been failed!", e);
        }
    }
}
