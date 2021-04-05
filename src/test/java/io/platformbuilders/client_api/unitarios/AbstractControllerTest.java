package io.platformbuilders.client_api.unitarios;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
@ContextConfiguration
public abstract class AbstractControllerTest {

    protected MockMvc mockMvc;
    protected ObjectMapper objectMapper = new ObjectMapper();

    protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    protected void registerController(Object controller) {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
    }

    protected String toJSON(Object object) throws JsonProcessingException {
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(object);
    }

}
