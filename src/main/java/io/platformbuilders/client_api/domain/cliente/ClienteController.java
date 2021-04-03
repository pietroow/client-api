package io.platformbuilders.client_api.domain.cliente;

import io.platformbuilders.client_api.domain.cliente.dto.ClienteByIdDTO;
import io.platformbuilders.client_api.domain.cliente.dto.ClienteCriarAtualizarDTO;
import io.platformbuilders.client_api.domain.cliente.dto.ClienteListaDTO;
import io.platformbuilders.client_api.util.FilterPageable;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    @ApiOperation(value = "Criar um cliente")
    public ResponseEntity<Cliente> criar(@Valid @RequestBody ClienteCriarAtualizarDTO clienteCriarDTO) {
        Cliente clienteCriado = clienteService.criar(clienteCriarDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(clienteCriado.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable("id") UUID id,
                       @Valid @RequestBody ClienteCriarAtualizarDTO clienteAtualizarDTO) {
        clienteService.update(id, clienteAtualizarDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @ApiOperation("Excluir um cliente por id")
    public void deleteById(@PathVariable("id") UUID id) {
        clienteService.deleteById(id);
    }

    @GetMapping("/{id}")
    @ApiOperation("Busca e retorna cliente por id")
    public ClienteByIdDTO findById(@PathVariable("id") UUID id) {
        return clienteService.findById(id);
    }

    @GetMapping
    @ApiOperation("Paginação de todos os clientes")
    public Page<ClienteListaDTO> findByPage(@RequestParam(value = "filter", defaultValue = "") String filter,
                                            FilterPageable filterPageable) {
        return clienteService.findByPage(filter, filterPageable.listByPage());
    }

}
