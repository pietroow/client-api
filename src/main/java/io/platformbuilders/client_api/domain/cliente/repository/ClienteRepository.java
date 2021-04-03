package io.platformbuilders.client_api.domain.cliente.repository;

import io.platformbuilders.client_api.domain.cliente.Cliente;
import io.platformbuilders.client_api.domain.cliente.dto.ClienteListaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository {

    Cliente save(Cliente cliente);

    Cliente findById(UUID id);

    Optional<Cliente> findByEmailIgnoreCase(String email);

    Optional<Cliente> findByEmailIgnoreCaseIdNot(String email, UUID id);

    Page<ClienteListaDTO> findByPage(String filter, Pageable pageable);

}
