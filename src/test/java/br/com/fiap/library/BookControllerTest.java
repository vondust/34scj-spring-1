package br.com.fiap.library;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;

import br.com.fiap.library.controller.BookController;

@SpringBootTest
public class BookControllerTest {

    TestRestTemplate template;

    @Test
    public void getAllTest() {
        String response = template.exchange("http://localhost:8080/livros", HttpMethod.GET, null, String.class).getBody();
        System.out.println(response);
    }

}