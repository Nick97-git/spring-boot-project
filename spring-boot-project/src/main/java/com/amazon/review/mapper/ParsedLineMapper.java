package com.amazon.review.mapper;

import com.amazon.review.dto.ParsedLineDto;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.springframework.stereotype.Component;

@Component
public class ParsedLineMapper {
    private static final int PRODUCT_ID_INDEX = 1;
    private static final int USER_ID_INDEX = 2;
    private static final int PROFILE_NAME_INDEX = 3;
    private static final int HELPFULNESS_NUMERATOR_INDEX = 4;
    private static final int HELPFULNESS_DENOMINATOR_INDEX = 5;
    private static final int SCORE_INDEX = 6;
    private static final int TIME_INDEX = 7;
    private static final int SUMMARY_INDEX = 8;
    private static final int TEXT_INDEX = 9;

    public ParsedLineDto convertToDto(String[] parsedLine) {
        ParsedLineDto dto = new ParsedLineDto();
        dto.setProductId(parsedLine[PRODUCT_ID_INDEX]);
        dto.setUserId(parsedLine[USER_ID_INDEX]);
        dto.setProfileName(parsedLine[PROFILE_NAME_INDEX]);
        dto.setHelpfulnessNumerator(Integer
                .parseInt(parsedLine[HELPFULNESS_NUMERATOR_INDEX]));
        dto.setHelpfulnessDenominator(Integer
                .parseInt(parsedLine[HELPFULNESS_DENOMINATOR_INDEX]));
        dto.setScore(Integer.parseInt(parsedLine[SCORE_INDEX]));
        dto.setTime(convertToLocalDateTime(parsedLine[TIME_INDEX]));
        dto.setSummary(parsedLine[SUMMARY_INDEX]);
        dto.setText(parsedLine[TEXT_INDEX]);
        return dto;
    }

    private LocalDateTime convertToLocalDateTime(String millis) {
        Instant instant = Instant.ofEpochMilli(Long.parseLong(millis));
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
