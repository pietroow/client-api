package io.platformbuilders.client_api.domain.cliente.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ClienteCriarAtualizarDTO {

    @NotBlank
    @Size(max = 100)
    private String nome;

    @Email
    @NotBlank
    private String email;

    @NotNull
    private LocalDate dataNascimento;

}
