package io.platformbuilders.client_api.domain.cliente;

import io.platformbuilders.client_api.domain.cliente.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityExistsException;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class ClienteValidator {

    private static final String EMAIL_EM_USO = "O E-MAIL %s JÁ ESTÁ EM USO.";

    private final ClienteRepository clienteRepository;

    public void validarEmail(String email, UUID id) {
        Optional<Cliente> clienteOptional = isNull(id)
                ? clienteRepository.findByEmailIgnoreCase(email)
                : clienteRepository.findByEmailIgnoreCaseIdNot(email, id);
        clienteOptional.ifPresent(throwsException());
    }

    private Consumer<Cliente> throwsException() {
        return cliente -> {
            String mensagem = String.format(EMAIL_EM_USO, cliente.getEmail());
            throw new EntityExistsException(mensagem);
        };
    }

}
