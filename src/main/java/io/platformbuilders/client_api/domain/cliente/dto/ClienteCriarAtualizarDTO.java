package io.platformbuilders.client_api.domain.cliente.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ClienteCriarAtualizarDTO {

    @NotBlank
    @Size(max = 100)
    private String nome;

    @Email(regexp = "[a-zA-Z0-9_\\.\\+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-\\.]+")
    @NotNull
    private String email;

    @NotNull
    private LocalDate dataNascimento;

}
