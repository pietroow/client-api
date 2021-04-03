package io.platformbuilders.client_api.domain.cliente.repository;

import io.platformbuilders.client_api.domain.cliente.Cliente;
import io.platformbuilders.client_api.domain.cliente.dto.ClienteListaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
class ClienteRepositoryImpl implements ClienteRepository {

    private static final String CLIENTE_NAO_ENCONTRADO = "CLIENTE NÃO ENCONTRADO.";

    private final ClienteRepositoryJpa clienteRepositoryJpa;

    @Override
    public Cliente save(Cliente cliente) {
        return clienteRepositoryJpa.save(cliente);
    }

    @Override
    public Cliente findById(UUID id) {
        Optional<Cliente> clienteOptional = clienteRepositoryJpa.findById(id);
        return clienteOptional.orElseThrow(() -> new EntityNotFoundException(CLIENTE_NAO_ENCONTRADO));
    }

    @Override
    public Optional<Cliente> findByEmailIgnoreCase(String email) {
        return clienteRepositoryJpa.findByEmailIgnoreCase(email);
    }

    @Override
    public Optional<Cliente> findByEmailIgnoreCaseIdNot(String email, UUID id) {
        return clienteRepositoryJpa.findByEmailIgnoreCaseAndIdNot(email, id);
    }

    @Override
    public Page<ClienteListaDTO> findByPage(String filter, Pageable pageable) {
        return clienteRepositoryJpa.findByPage(filter, pageable);
    }

}
