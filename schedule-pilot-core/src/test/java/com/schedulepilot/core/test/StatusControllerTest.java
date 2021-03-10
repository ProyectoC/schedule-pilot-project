package com.schedulepilot.core.test;

import com.schedulepilot.core.constants.RolAccountConstants;
import com.schedulepilot.core.constants.StatusConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StatusControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getAllProductRequestStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(StatusConstants.REST_PATH_DEFAULT_V1 + StatusConstants.GET_REQUEST_CHECK_IN_REST))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllTicketStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(StatusConstants.REST_PATH_DEFAULT_V1 + StatusConstants.GET_TICKET_CHECK_IN_REST))
                .andExpect(status().isOk());
    }

}
