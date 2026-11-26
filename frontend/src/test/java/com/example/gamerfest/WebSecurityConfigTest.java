package com.example.gamerfest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class WebSecurityConfigTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(username = "usuario", roles = "USER")
    void deberiaDenegarAccesoAdministrativoAUsuarioNormal() throws Exception {
        mvc.perform(get("/admin"))
                .andExpect(status().isForbidden());
    }
}