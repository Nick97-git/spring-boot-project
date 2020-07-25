package com.amazon.review.mapper;

import com.amazon.review.dto.ParsedLineDto;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ParsedLineMapperTest {
    private final ParsedLineMapper parsedLineMapper = new ParsedLineMapper();

    @Test
    public void checkConvertToDto() {
        String[] parsedLine = {"id", "productId", "userId", "profile", "1", "2", "100",
                "1299628800", "summary", "text"};
        ParsedLineDto expected = new ParsedLineDto();
        expected.setProductId("productId");
        expected.setUserId("userId");
        expected.setProfileName("profile");
        expected.setHelpfulnessNumerator(1);
        expected.setHelpfulnessDenominator(2);
        expected.setScore(100);
        expected.setTime(convertToLocalDateTime());
        expected.setSummary("summary");
        expected.setText("text");
        ParsedLineDto actual = parsedLineMapper.convertToDto(parsedLine);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void checkEmptyParsedLine() {
        String[] parsedLine = {};
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            parsedLineMapper.convertToDto(parsedLine);
        });
    }

    private LocalDateTime convertToLocalDateTime() {
        Instant instant = Instant.ofEpochMilli(Long.parseLong("1299628800"));
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
