package br.com.goldenraspberryawards.api.infra.producer.adapters.csv;

import br.com.goldenraspberryawards.api.domain.producer.data.Producer;
import br.com.goldenraspberryawards.api.domain.producer.port.in.ProducerDataSourcePort;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static java.lang.String.*;
import static java.util.function.Predicate.not;
import static java.util.stream.StreamSupport.stream;

public class ProducerCsvDataSourceAdapter implements ProducerDataSourcePort {

    private final CSVParser csvParser;

    public ProducerCsvDataSourceAdapter(String filePath) {
        try {
            InputStream is = ProducerCsvDataSourceAdapter.class.getClassLoader().getResourceAsStream(filePath);
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            this.csvParser = new CSVParser(fileReader,
                    CSVFormat.Builder.create().setDelimiter(';').setSkipHeaderRecord(true).setHeader(
                            CsvHeader.YEAR.name, CsvHeader.TITLE.name, CsvHeader.STUDIO.name, CsvHeader.PRODUCER.name, CsvHeader.WINNER.name
                    ).build());
        } catch (IOException e) {
            throw new CsvDataSourceException(format("Error when trying to load file %s. Error: %s", filePath, e.getMessage()));
        }
    }

    @Override
    public List<Producer> readData() {
        try {
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            return stream(csvRecords.spliterator(), true).map(this::mapRecordToMovie).flatMap(List::stream).toList();
        } catch (Exception e) {
            throw new CsvDataSourceException(format("Error when trying to read data. Error: %s", e.getMessage()));
        }
    }

    private List<Producer> mapRecordToMovie(CSVRecord csvRecord) {
        var year = getValue(csvRecord, CsvHeader.YEAR, Integer::parseInt);
        var title = getValue(csvRecord, CsvHeader.TITLE);
        var studio = getValue(csvRecord, CsvHeader.STUDIO);
        var producer = getValue(csvRecord, CsvHeader.PRODUCER);
        var winner = getValue(csvRecord, CsvHeader.WINNER, "yes"::equals);

        return Arrays.stream(producer.replace("and", ",").split(","))
                .filter(not(String::isEmpty))
                .map(String::trim)
                .map(String::toUpperCase)
                .map(producerName -> new Producer(year, title, studio, producerName, winner))
                .toList();
    }

    private <R> R getValue(CSVRecord csvRecord, CsvHeader header, Function<String, R> parser) {
        return parser.apply(csvRecord.get(header.name));
    }

    private String getValue(CSVRecord csvRecord, CsvHeader header) {
        return csvRecord.get(header.name);
    }

}
