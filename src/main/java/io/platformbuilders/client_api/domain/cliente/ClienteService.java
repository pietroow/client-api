package io.platformbuilders.client_api.domain.cliente;

import io.platformbuilders.client_api.domain.cliente.dto.ClienteByIdDTO;
import io.platformbuilders.client_api.domain.cliente.dto.ClienteCriarAtualizarDTO;
import io.platformbuilders.client_api.domain.cliente.dto.ClienteListaDTO;
import io.platformbuilders.client_api.domain.cliente.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteValidator clienteValidator;

    public Cliente criar(ClienteCriarAtualizarDTO clienteCriarDTO) {
        Cliente cliente = new Cliente(clienteCriarDTO);
        clienteValidator.validarEmail(clienteCriarDTO.getEmail(), null);
        return clienteRepository.save(cliente);
    }

    public ClienteByIdDTO findById(UUID id) {
        Cliente cliente = clienteRepository.findById(id);
        return new ClienteByIdDTO(cliente);
    }

    public void update(UUID id, ClienteCriarAtualizarDTO clienteAtualizarDTO) {
        clienteValidator.validarEmail(clienteAtualizarDTO.getEmail(), id);
        Cliente cliente = clienteRepository.findById(id);
        cliente.update(clienteAtualizarDTO);
        clienteRepository.save(cliente);
    }

    public void deleteById(UUID id) {
        Cliente cliente = clienteRepository.findById(id);
        cliente.delete();
        clienteRepository.save(cliente);
    }

    public Page<ClienteListaDTO> findByPage(String filter, Pageable pageable) {
        return clienteRepository.findByPage(filter, pageable);
    }

}
