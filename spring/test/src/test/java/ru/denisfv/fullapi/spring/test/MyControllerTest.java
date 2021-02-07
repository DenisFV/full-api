package ru.denisfv.fullapi.spring.test;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.denisfv.fullapi.spring.test.conf.ControllerTestConf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(classes = ControllerTestConf.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class MyControllerTest {

    @Autowired
    WebApplicationContext wac;

    @Autowired
    public MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @SneakyThrows
    @Test
    public void testMockMvc() {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(mvcResult -> mvcResult.getResponse().getContentAsString().equals("aaa"));
    }
}
