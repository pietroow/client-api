package io.platformbuilders.client_api.domain.cliente.repository;

import io.platformbuilders.client_api.domain.cliente.Cliente;
import io.platformbuilders.client_api.domain.cliente.dto.ClienteListaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

interface ClienteRepositoryJpa extends JpaRepository<Cliente, UUID> {

    Optional<Cliente> findByEmailIgnoreCase(String email);

    Optional<Cliente> findByEmailIgnoreCaseAndIdNot(String email, UUID id);

    @Query("SELECT new " + ClienteListaDTO.PATH + "(cliente.id, cliente.email, cliente.nome, cliente.dataNascimento, " +
            "extract(year from age(CURRENT_DATE, cliente.dataNascimento))) " +
            "FROM Cliente cliente " +
            "WHERE (cliente.nome LIKE %:filter% " +
            "OR cliente.email LIKE %:filter%)")
    Page<ClienteListaDTO> findByPage(String filter, Pageable pageable);

}
