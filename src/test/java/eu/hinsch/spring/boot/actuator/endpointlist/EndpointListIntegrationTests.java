package eu.hinsch.spring.boot.actuator.endpointlist;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

/**
 * Created by lh on 26/04/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = EndpointListIntegrationTests.TestConfig.class)
@TestPropertySource(properties = "endpoints.list.excludes=shutdown")
public class EndpointListIntegrationTests {

    @SpringBootApplication
    static class TestConfig {}

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void shouldIncludeListToEndpoints() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(xpath("//ul/li/a[@href='autoconfig']").string("autoconfig"))
                .andExpect(xpath("//ul/li/a[@href='beans']").string("beans"))
                .andExpect(xpath("//ul/li/a[@href='configprops']").string("configprops"))
                .andExpect(xpath("//ul/li/a[@href='dump']").string("dump"))
                .andExpect(xpath("//ul/li/a[@href='env']").string("env"))
                .andExpect(xpath("//ul/li/a[@href='health']").string("health"))
                .andExpect(xpath("//ul/li/a[@href='info']").string("info"))
                .andExpect(xpath("//ul/li/a[@href='mappings']").string("mappings"))
                .andExpect(xpath("//ul/li/a[@href='metrics']").string("metrics"))
                .andExpect(xpath("//ul/li/a[@href='trace']").string("trace"))
                .andExpect(xpath("//ul/li/a[@href='shutdown']").doesNotExist());
    }
}
