/*
 * Copyright (c) 2016-2022 University of Washington
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *  Neither the name of the University of Washington nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE UNIVERSITY OF WASHINGTON AND CONTRIBUTORS “AS IS” AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE UNIVERSITY OF WASHINGTON OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package edu.uw.cse.ifrcdemo.distplan;

import edu.uw.cse.ifrcdemo.planningsharedlib.logic.ResourceInputStreamSupplier;
import edu.uw.cse.ifrcdemo.planningsharedlib.model.csv.CsvRepository;
import edu.uw.cse.ifrcdemo.planningsharedlib.model.csv.FileCsvRepository;
import edu.uw.cse.ifrcdemo.planningsharedlib.util.CsvFileUtil;
import edu.uw.cse.ifrcdemo.sharedlib.model.row.CsvAuthorization;
import edu.uw.cse.ifrcdemo.sharedlib.model.row.CsvBeneficiaryEntity;
import edu.uw.cse.ifrcdemo.sharedlib.model.row.CsvDistribution;
import edu.uw.cse.ifrcdemo.sharedlib.model.row.CsvEntitlement;
import edu.uw.cse.ifrcdemo.sharedlib.model.row.CsvIndividual;
import edu.uw.cse.ifrcdemo.sharedlib.model.row.CsvVisit;
import edu.uw.cse.ifrcdemo.sharedlib.util.FileUtil;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CsvRepoArgConverter extends SimpleArgumentConverter {
  @Override
  protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
    assertNotNull(source);
    assertEquals(CsvRepository.class, targetType);

    return build((String) source);
  }

  public static CsvRepository build(String csvDirPath) {
    Path csvPath = Paths.get(csvDirPath);

    Function<String, Supplier<InputStream>> inputStreamSupplier = name ->
        new ResourceInputStreamSupplier(FileUtil.getPathToCSV(csvPath, name).toString());

    CsvRepository csvRepository = new FileCsvRepository();

    CompletableFuture<?>[] baseAndCustomTables = Stream
        .of(
            CsvIndividual.class,
            CsvBeneficiaryEntity.class,
            CsvVisit.class
        )
        .filter(clazz -> resourceExists(FileUtil.getPathToCsv(csvPath, clazz).toString()))
        .map(clazz -> CsvFileUtil.readBaseTableWithCustomTable(
            inputStreamSupplier,
            clazz,
            row -> row.getCustomTableFormId(),
            csvRepository
        ))
        .toArray(CompletableFuture<?>[]::new);

    CompletableFuture<?>[] baseTablesOnly = Stream
        .of(
            CsvAuthorization.class,
            CsvEntitlement.class,
            CsvDistribution.class
        )
        .filter(clazz -> resourceExists(FileUtil.getPathToCsv(csvPath, clazz).toString()))
        .map(clazz -> CsvFileUtil.readBaseTable(
            inputStreamSupplier.apply(FileUtil.getFileName(clazz)),
            clazz,
            csvRepository
        ))
        .toArray(CompletableFuture<?>[]::new);

    CompletableFuture
        .allOf(
            CompletableFuture.allOf(baseAndCustomTables),
            CompletableFuture.allOf(baseTablesOnly)
        )
        .join();

    return csvRepository;
  }

  private static boolean resourceExists(String name) {
    return CsvRepoArgConverter.class.getClassLoader().getResource(name) != null;
  }
}
