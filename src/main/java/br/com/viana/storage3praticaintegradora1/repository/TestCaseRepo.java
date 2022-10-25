package br.com.viana.storage3praticaintegradora1.repository;

import br.com.viana.storage3praticaintegradora1.model.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestCaseRepo extends JpaRepository<TestCase, Long> {
}
