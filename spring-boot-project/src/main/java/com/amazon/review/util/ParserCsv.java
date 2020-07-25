package com.amazon.review.util;

import com.amazon.review.dto.ParsedLineDto;
import com.amazon.review.mapper.ParsedLineMapper;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class ParserCsv {
    private final ParsedLineMapper parsedLineMapper;

    public List<ParsedLineDto> parseCsv(List<String> lines) {
        CsvParserSettings settings = new CsvParserSettings();
        settings.setMaxCharsPerColumn(100000);
        CsvParser csvParser = new CsvParser(settings);
        List<ParsedLineDto> dtos = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            String[] parsedLine = csvParser.parseLine(lines.get(i));
            dtos.add(parsedLineMapper.convertToDto(parsedLine));
        }
        log.info("Lines have been parsed!");
        return dtos;
    }
}
