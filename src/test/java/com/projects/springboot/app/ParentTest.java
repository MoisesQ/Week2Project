package com.projects.springboot.app;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.projects.springboot.app.entity.Parent;
import com.projects.springboot.app.service.ParentService;
import java.text.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParentTest {

  @Autowired
  private WebTestClient webClient;

  static final String USERNAME = "user";
  static final String PASSWORD = "user";
  
  @MockBean
  private ParentService parentService;

  private Parent prTest;
  private Parent prTest2;

  /**
   * This method is created because we need to start with a parent Object to use
   * in all tests.
   * 
   * @throws ParseException Using this exception because the date string format
   *                        doesn't work in the default constructor
   */
  @BeforeEach
  public void beforeAll() throws ParseException {
    prTest = new Parent("M", "Lucio", "Andres", "Gonzales", "Nice Parent");
    prTest2 =  new Parent("M", "Mariano", "Luis", "Gonzales","Nice Parent");
  }
  
  
  @Test
  public void createParentIsOkTest() throws Exception {

    when(parentService.create(prTest)).thenReturn(Mono.just(prTest));

    webClient.post().uri("/api/parents")
    .headers(headers -> headers.setBasicAuth(USERNAME, PASSWORD))
        .contentType(MediaType.APPLICATION_JSON).body(Mono.just(prTest), Parent.class)
        .exchange().expectHeader().contentType(MediaType.APPLICATION_JSON)
        .expectStatus().isOk();

  }

  @Test
  public void findOneParentIsOkTest() throws Exception {

    when(parentService.findById("7L")).thenReturn(Mono.just(prTest));

    webClient.get().uri("/api/parents/7L")
    .headers(headers -> headers.setBasicAuth(USERNAME, PASSWORD)).exchange()
        .expectHeader().contentType(MediaType.APPLICATION_JSON)
        .expectStatus().isOk().expectBodyList(Parent.class);
  }

  @Test
  public void allParentsIsOkTest() throws Exception {

    prTest.setParentId("7L");
    prTest2.setParentId("8L");
    
    when(parentService.findAll()).thenReturn(Flux.just(prTest, prTest2));

    webClient.get().uri("/api/parents")
    .headers(headers -> headers.setBasicAuth(USERNAME, PASSWORD)).exchange()
        .expectHeader().contentType(MediaType.APPLICATION_STREAM_JSON_VALUE)
        .expectStatus().isOk().expectBody().jsonPath("$.length()").isEqualTo("7");

  }

  @Test
  public void updateParentIsOkTest() throws Exception {

    prTest.setParentId("7L");
    when(parentService.findById(prTest.getParentId())).thenReturn(Mono.just(prTest));
    when(parentService.update(any(),any())).thenReturn(Mono.just(prTest2));
    prTest2.setParentId("7L");

    webClient.put().uri("/api/parents/{parentId}","7L")
    .headers(headers -> headers.setBasicAuth(USERNAME, PASSWORD))
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(prTest),Parent.class)
            .exchange()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.parentId").isEqualTo("7L");
  }

  @Test
  public void deleteParentIsOkTest() throws Exception {

    prTest.setParentId("7L");

    when(parentService.findById(prTest.getParentId())).thenReturn(Mono.just(prTest));
    when(parentService.delete(prTest)).thenReturn(Mono.empty());


    webClient.delete().uri("/api/parents/{parentId}", prTest.getParentId())
    .headers(headers -> headers.setBasicAuth(USERNAME, PASSWORD))
    .exchange().expectStatus().isOk();

  }

  
}
