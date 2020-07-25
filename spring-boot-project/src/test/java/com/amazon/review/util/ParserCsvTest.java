package com.amazon.review.util;

import com.amazon.review.dto.ParsedLineDto;
import com.amazon.review.mapper.ParsedLineMapper;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ParserCsvTest {
    private final ParserCsv parserCsv = new ParserCsv(new ParsedLineMapper());

    @Test
    public void checkParsingOk() {
        String pathToFile = "/testData.csv";
        List<String> lines = new FileUtil().readFromFile(pathToFile);
        List<ParsedLineDto> actual = parserCsv.parseCsv(lines);
        Assertions.assertEquals(500, actual.size());
    }
}
