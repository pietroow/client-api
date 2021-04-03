package io.platformbuilders.client_api.domain.cliente.dto;

import io.platformbuilders.client_api.domain.cliente.Cliente;
import io.platformbuilders.client_api.util.DateUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class ClienteByIdDTO {

    private UUID id;
    private String nome;
    private String email;
    private LocalDate dataNascimento;
    private long idade;

    public ClienteByIdDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.email = cliente.getEmail();
        this.dataNascimento = cliente.getDataNascimento();
        this.idade = DateUtil.calcularIdade(dataNascimento);
    }

}
