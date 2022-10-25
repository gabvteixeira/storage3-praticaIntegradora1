package br.com.viana.storage3praticaintegradora1.service;

import br.com.viana.storage3praticaintegradora1.model.TestCase;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ITestCase {
    TestCase create(TestCase testCase);

    List<TestCase> getAll(Optional<String> last_update) throws ParseException;

    TestCase getById(long id);

    TestCase update(long id, TestCase testCase);

    void delete(long id);
}
