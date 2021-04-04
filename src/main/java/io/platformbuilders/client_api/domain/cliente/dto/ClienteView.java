package io.platformbuilders.client_api.domain.cliente.dto;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Entity
@Table(name = "vw_cliente", schema = "client_api")
public class ClienteView {

    @Id
    private UUID id;
    private String nome;
    private String email;
    private LocalDate dataNascimento;
    private int idade;

}
