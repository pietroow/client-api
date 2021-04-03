package io.platformbuilders.client_api.domain.cliente.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class ClienteListaDTO {

    public static final String PATH = "io.platformbuilders.client_api.domain.cliente.dto.ClienteListaDTO";

    private UUID id;
    private String nome;
    private String email;
    private LocalDate dataNascimento;
    private int idade;

    public ClienteListaDTO(UUID id, String email, String nome, LocalDate dataNascimento, int idade) {
        this.id = id;
        this.email = email;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.idade = idade;
    }

}
