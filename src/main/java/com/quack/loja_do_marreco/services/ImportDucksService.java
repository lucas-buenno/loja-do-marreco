package com.quack.loja_do_marreco.services;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.quack.loja_do_marreco.domain.CsvToImportDucks;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImportDucksService {

    public List<CsvToImportDucks> readDucksImportCsv(String path) throws IOException {

        try(Reader reader = Files.newBufferedReader(Paths.get(path))) {

            var builder = new CsvToBeanBuilder<CsvToImportDucks>(reader)
                    .withType(CsvToImportDucks.class)
                    .build();
            return builder.parse();
        }
    }
}
