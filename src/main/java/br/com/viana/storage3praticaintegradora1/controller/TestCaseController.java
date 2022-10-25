package br.com.viana.storage3praticaintegradora1.controller;

import br.com.viana.storage3praticaintegradora1.model.TestCase;
import br.com.viana.storage3praticaintegradora1.service.ITestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/api/testcases")
public class TestCaseController {

    @Autowired
    private ITestCase service;


    /**
     * Cria um novo caso de teste
     * @param testCase caso de teste a ser cadastradoÍ
     * @return HTTP Status e o novo caso de testes
     */
    @PostMapping("/new")
    public ResponseEntity<TestCase> createTestCase(@RequestBody TestCase testCase){
        TestCase newTestCase = this.service.create(testCase);

        String currentUrl = ServletUriComponentsBuilder.fromCurrentRequest().toUriString();
        String hostEndpoint = currentUrl.replace("/new", "");
        URI uri = ServletUriComponentsBuilder.fromHttpUrl(hostEndpoint).path("/" + newTestCase.getIdCase())
                .build().toUri();

        return ResponseEntity.created(uri).body(newTestCase);
    }

    /**
     * Retorna uma lista de todos os casos de testes de acordo com os filtros passados por parâmetro
     * @param last_update filtro opcional por data
     * @return HTTP Status e uma lista de casos de testes
     */
    @GetMapping
    public ResponseEntity<List<TestCase>> getAllTestCase(@RequestParam Optional<String> last_update) throws ParseException {
        return ResponseEntity.ok(this.service.getAll(last_update));
    }


    /**
     * Retorna o caso de testes especificado pelo Id
     * @param id Identificacao do caso de teste
     * @return HTTP Status e o caso de testes requerido
     */
    @GetMapping("/{id}")
    public ResponseEntity<TestCase> getTestCaseById(@PathVariable long id){
        TestCase foundTestCase = this.service.getById(id);
        return ResponseEntity.ok(foundTestCase);
    }

    /**
     * Atualiza um caso de teste pelo ID
     * @param id ID do caso de testes a ser atualizado
     * @param testCase Dados a serem alterados no caso de testes
     * @return HTTP Status e o caso de teste atualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<TestCase> updateTestCase(@PathVariable long id, @RequestBody TestCase testCase){
        TestCase updatedTestCase = this.service.update(id, testCase);
        return ResponseEntity.ok(updatedTestCase);
    }

    /**
     * Remove um caso de testes pelo ID
     * @param id ID do caso de testes a ser removido
     * @return HTTP Status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestCase(@PathVariable long id){
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
