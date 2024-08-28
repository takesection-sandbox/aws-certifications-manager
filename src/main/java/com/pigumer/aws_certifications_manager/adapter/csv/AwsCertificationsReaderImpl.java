package com.pigumer.aws_certifications_manager.adapter.csv;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.pigumer.aws_certifications_manager.domain.port.AwsCertificationsReader;
import com.pigumer.aws_certifications_manager.domain.valueobject.AwsCertification;
import org.springframework.stereotype.Component;

@Component
public class AwsCertificationsReaderImpl implements AwsCertificationsReader {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public List<AwsCertification> readList(InputStream is) throws Exception {
        try (InputStreamReader reader = new InputStreamReader(is, Charset.forName("MS932"));
             CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
        ) {
            ArrayList<AwsCertification> res = new ArrayList<>();
            while (true) {
                String[] row = csvReader.readNext();
                if (null == row) {
                    break;
                }
                res.add(new AwsCertification(
                        row[0],
                        row[1],
                        row[2],
                        row[3],
                        LocalDate.parse(row[4], formatter),
                        LocalDate.parse(row[5], formatter)
                ));
            }
            return res;
        }
    }
}
