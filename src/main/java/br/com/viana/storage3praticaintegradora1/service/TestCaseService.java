package br.com.viana.storage3praticaintegradora1.service;

import br.com.viana.storage3praticaintegradora1.model.TestCase;
import br.com.viana.storage3praticaintegradora1.repository.TestCaseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TestCaseService implements ITestCase {

    @Autowired
    private TestCaseRepo repo;

    @Override
    public TestCase create(TestCase testCase) {
        testCase.setLastUpdate(Date.from(Instant.now()));
        return this.repo.save(testCase);
    }

    @Override
    // TODO: Handle exception using ControllerAdvice
    public List<TestCase> getAll(Optional<String> last_update) throws ParseException {
        List<TestCase> testCases = this.repo.findAll();
        if(last_update.isPresent()){
            testCases = this.filterByLastUpdate(testCases,
                    new SimpleDateFormat("dd/MM/yyyy")
                    .parse(last_update.get()));
        }

        return testCases;
    }

    private  List<TestCase> filterByLastUpdate(List<TestCase> testCases, Date last_update){
        List<TestCase> filteredTestCases = testCases.stream()
                .filter(t -> t.getLastUpdate().after(last_update))
                .collect(Collectors.toList());

        return filteredTestCases;
    }

    @Override
    public TestCase getById(long id) {
        return this.repo.findById(id).orElse(null);
    }

    @Override
    public TestCase update(long id, TestCase testCase) {
        Optional<TestCase> optionalTestCase = this.repo.findById(id);

        if(optionalTestCase.isEmpty()){
            return null;
        }

        TestCase newTestCase = optionalTestCase.get();

        newTestCase = this.updateTested(newTestCase, testCase.getTested());
        newTestCase = this.updatePassed(newTestCase, testCase.getPassed());
        newTestCase = this.updateDescription(newTestCase, testCase.getDescription());
        newTestCase = this.updateNumberOfTries(newTestCase, testCase.getNumberOfTries());
        newTestCase.setLastUpdate(Date.from(Instant.now()));

        this.repo.save(newTestCase);
        return newTestCase;
    }

    private TestCase updateNumberOfTries(TestCase testCase, Integer numberOfTries) {
        if(numberOfTries != null){
            testCase.setNumberOfTries(numberOfTries);
        }

        return testCase;
    }

    private TestCase updateDescription(TestCase testCase, String description) {
        if(description != null){
            testCase.setDescription(description);
        }

        return testCase;
    }

    private TestCase updatePassed(TestCase testCase, Boolean passed) {
        if(passed != null){
            testCase.setTested(passed);
        }

        return testCase;

    }

    private TestCase updateTested(TestCase testCase, Boolean tested){
        if(tested != null){
            testCase.setTested(tested);
        }

        return testCase;
    }

    @Override
    public void delete(long id) {
        this.repo.deleteById(id);
    }
}
