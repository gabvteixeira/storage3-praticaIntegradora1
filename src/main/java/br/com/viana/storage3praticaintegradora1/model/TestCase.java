package br.com.viana.storage3praticaintegradora1.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
public class TestCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_case")
    private long idCase;

    @Column(nullable = false)
    private String description;

    @Column(name = "last_update")
    private Date lastUpdate;

    @Column(name = "number_of_tries")
    private Integer numberOfTries;

    private Boolean tested, passed;
}
