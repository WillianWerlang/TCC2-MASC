package org.example.test;

import org.example.GreetingController;
//import org.junit.jupiter.api.Test;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
//import org.testng.annotations.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@SpringBootTest
@RunWith(SpringRunner.class)
//@DataJpaTest
public class Server_mascTest {
    @LocalServerPort
    private int port;

    @Autowired
    private GreetingController controller;

    @Test
    public void contexLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Autowired
    private TestRestTemplate restTemplate;
    @Test
    //greeting sem parametro
    public void greetingShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/greeting",
                String.class)).contains("Hello, World");
    }
    @Test
    //greeting com parametro
    public void greetingParamShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/greeting?name=Marcelot",
                String.class)).contains("Hello, Marcelot");
    }
    @Test
    //greetingjson
    public void greetingjsonShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/greetingjson",
                String.class)).contains("{\"id\":2,\"content\":\"Hello, World!\"}");
    //{
    //  "id": 1,
    //  "content": "Hello, World!"
    //}

    }
    @Test
    //greetingjson
    public void greetingjsonParamShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/greetingjson?name=Marcelot",
                String.class)).contains("{\"id\":1,\"content\":\"Hello, Marcelot!\"}");
    }
    //{
    //  "id": 1,
    //  "content": "Hello, Marcelot!"
    //}

}
