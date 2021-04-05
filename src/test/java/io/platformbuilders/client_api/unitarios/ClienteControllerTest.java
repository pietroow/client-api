package io.platformbuilders.client_api.unitarios;

import io.platformbuilders.client_api.domain.cliente.Cliente;
import io.platformbuilders.client_api.domain.cliente.ClienteController;
import io.platformbuilders.client_api.domain.cliente.ClienteService;
import io.platformbuilders.client_api.domain.cliente.dto.ClienteByIdDTO;
import io.platformbuilders.client_api.domain.cliente.dto.ClienteCriarAtualizarDTO;
import io.platformbuilders.client_api.domain.cliente.dto.ClienteListaDTO;
import io.platformbuilders.client_api.util.FilterPageable;
import io.platformbuilders.client_api.util.ResourceUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClienteControllerTest extends AbstractControllerTest {

    private static final String URI = "/clientes";

    @Mock(name = "clienteService")
    private ClienteService clienteServiceMock;

    @InjectMocks
    private ClienteController clienteController;
    private Cliente cliente;
    private String clienteJson;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        this.clienteController = new ClienteController(clienteServiceMock);
        this.registerController(this.clienteController);

        cliente = new Cliente("VICTOR PIETRO", "VICTOR_PIETRO@HOTMAIL.COM", LocalDate.of(1995, 6, 7));
        clienteJson = ResourceUtils.getContentFromResource("/json/criar-cliente.json");
    }

    @Test
    public void findById() throws Exception {
        String uri = String.format("%s/%s", URI, "9de5354e-17fc-4cf8-ab1a-17d0a15ac57e");
        ClienteByIdDTO clienteByIdDTO = new ClienteByIdDTO(cliente);

        when(clienteServiceMock.findById(any(UUID.class))).thenReturn(clienteByIdDTO);

        mockMvc.perform(get(uri))
                .andExpect(jsonPath("$.nome", is("VICTOR PIETRO")))
                .andExpect(jsonPath("$.email", is("VICTOR_PIETRO@HOTMAIL.COM")))
                .andExpect(status().isOk());

        verify(clienteServiceMock, times(1)).findById(any(UUID.class));
        verifyNoMoreInteractions(clienteServiceMock);
    }

    @Test
    public void findByPage() throws Exception {
        List<ClienteListaDTO> clientes = Arrays.asList(
                new ClienteListaDTO(UUID.fromString("0820b9b7-eaad-4c3d-b28d-0245bd060a28"), "EMAIL_ALEATORIO1@HOTMAIL.COM", "SOLDADO 01", LocalDate.of(2000, 1, 1), 10),
                new ClienteListaDTO(UUID.fromString("d65bc391-d2b4-4085-a591-fa2e0e5e87ce"), "EMAIL_ALEATORIO2@HOTMAIL.COM", "SOLDADO 02", LocalDate.of(2000, 2, 1), 15),
                new ClienteListaDTO(UUID.fromString("89b94907-448d-41c5-8dcf-0360390fbf03"), "EMAIL_ALEATORIO3@HOTMAIL.COM", "SOLDADO 03", LocalDate.of(2000, 3, 1), 20));

        FilterPageable filterPageable = new FilterPageable(0, 2, "nome", "ASC");
        Pageable pageable = filterPageable.listByPage();
        Page<ClienteListaDTO> clientePage = new PageImpl<>(clientes, pageable, 3);

        when(clienteServiceMock.findByPage(anyString(), any(Pageable.class))).thenReturn(clientePage);

        mockMvc.perform(get(URI)
                .contentType(contentType)
                .param("direction", "ASC"))
                .andExpect(jsonPath("$.totalElements", is(3)))
                .andExpect(status().isOk());

        verify(clienteServiceMock, times(1)).findByPage(anyString(), any(Pageable.class));
        verifyNoMoreInteractions(clienteServiceMock);
    }

    @Test
    public void salvarComSucessoRetornando201Created() throws Exception {
        String payload = clienteJson
                .replace("{{nome}}", "VICTOR PIETRO")
                .replace("{{email}}", "VICTOR_PIETRO@HOTMAIL.COM")
                .replace("{{dataNascimento}}", "1995-06-07");

        when(clienteServiceMock.criar(any(ClienteCriarAtualizarDTO.class))).thenReturn(cliente);

        mockMvc.perform(post(URI)
                .contentType(contentType)
                .content(payload))
                .andExpect(status().isCreated());

        verify(clienteServiceMock, times(1)).criar(any(ClienteCriarAtualizarDTO.class));
        verifyNoMoreInteractions(clienteServiceMock);
    }

    @Test
    public void atualizarComSucessoRetornando204() throws Exception {
        String uri = String.format("%s/%s", URI, "9de5354e-17fc-4cf8-ab1a-17d0a15ac57e");

        String payload = clienteJson
                .replace("{{nome}}", "VICTOR PIETRO")
                .replace("{{email}}", "VICTOR_PIETRO@HOTMAIL.COM")
                .replace("{{dataNascimento}}", "1995-06-07");

        mockMvc.perform(put(uri)
                .contentType(contentType)
                .content(payload))
                .andExpect(status().isNoContent());

        verify(clienteServiceMock, times(1)).update(any(UUID.class), any(ClienteCriarAtualizarDTO.class));
        verifyNoMoreInteractions(clienteServiceMock);
    }

    @Test
    public void deletarComSucessoRetornando204NoContent() throws Exception {
        String uri = String.format("%s/%s", URI, "9de5354e-17fc-4cf8-ab1a-17d0a15ac57e");

        mockMvc.perform(delete(uri))
                .andExpect(status().isNoContent());

        verify(clienteServiceMock, times(1)).deleteById(any(UUID.class));
        verifyNoMoreInteractions(clienteServiceMock);
    }

    @Test
    public void deletarComFalhaRetornando400BadRequest() throws Exception {
        String uri = String.format("%s/%s", URI, "433c-8971-4a3913762e7e");

        mockMvc.perform(delete(uri))
                .andExpect(status().isBadRequest());
    }

}
