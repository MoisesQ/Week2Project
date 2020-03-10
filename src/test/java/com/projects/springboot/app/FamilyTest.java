package com.projects.springboot.app;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.projects.springboot.app.entity.Family;
import com.projects.springboot.app.entity.Parent;
import com.projects.springboot.app.service.FamilyService;
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
public class FamilyTest {

  @Autowired
  private WebTestClient webClient;

  static final String USERNAME = "user";
  static final String PASSWORD = "user";
  
  @MockBean
  private FamilyService familyService;

  private Parent hofTest;
  
  private Family fmTest;
  private Family fmTest2;

  /**
   * This method is created because we need to start with a family Object to use
   * in all tests.
   * 
   * @throws ParseException Using this exception because the date string format
   *                        doesn't work in the default constructor
   */
  @BeforeEach
  public void beforeAll() throws ParseException {
    hofTest = new Parent("M", "Lucio", "Andres",
        "Gonzales","Nice Parent");

    
    fmTest = new Family(hofTest, "Los Gonzales");
    fmTest2 = new Family(hofTest, "Los Gonsáles");
  }
  
  
  @Test
  public void createFamilyIsOkTest() throws Exception {

    when(familyService.create(fmTest)).thenReturn(Mono.just(fmTest));

    webClient.post().uri("/api/families")
    .headers(headers -> headers.setBasicAuth(USERNAME, PASSWORD))
        .contentType(MediaType.APPLICATION_JSON).body(Mono.just(fmTest), Family.class)
        .exchange().expectHeader().contentType(MediaType.APPLICATION_JSON)
        .expectStatus().isOk();

  }

  @Test
  public void findOneFamilyIsOkTest() throws Exception {

    when(familyService.findById("9L")).thenReturn(Mono.just(fmTest));

    webClient.get().uri("/api/families/9L")
    .headers(headers -> headers.setBasicAuth(USERNAME, PASSWORD)).exchange()
        .expectHeader().contentType(MediaType.APPLICATION_JSON)
        .expectStatus().isOk().expectBodyList(Family.class);
  }

  @Test
  public void allFamilysIsOkTest() throws Exception {

    fmTest.setFamilyId("9L");
    fmTest2.setFamilyId("10L");
    
    when(familyService.findAll()).thenReturn(Flux.just(fmTest, fmTest2));

    webClient.get().uri("/api/families")
    .headers(headers -> headers.setBasicAuth(USERNAME, PASSWORD)).exchange()
        .expectHeader().contentType(MediaType.APPLICATION_STREAM_JSON_VALUE)
        .expectStatus().isOk().expectBody().jsonPath("$.length()").isEqualTo("3");
  }

  @Test
  public void updateFamilyIsOkTest() throws Exception {

    fmTest.setFamilyId("9L");
    when(familyService.findById(fmTest.getFamilyId())).thenReturn(Mono.just(fmTest));
    when(familyService.update(any(),any())).thenReturn(Mono.just(fmTest2));
    fmTest2.setFamilyId("9L");

    webClient.put().uri("/api/families/{familyId}","9L")
    .headers(headers -> headers.setBasicAuth(USERNAME, PASSWORD))
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(fmTest),Family.class)
            .exchange()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.familyId").isEqualTo("9L");
  }

  @Test
  public void deleteFamilyIsOkTest() throws Exception {

    fmTest.setFamilyId("9L");

    when(familyService.findById(fmTest.getFamilyId())).thenReturn(Mono.just(fmTest));
    when(familyService.delete(fmTest)).thenReturn(Mono.empty());


    webClient.delete().uri("/api/families/{familyId}", fmTest.getFamilyId())
    .headers(headers -> headers.setBasicAuth(USERNAME, PASSWORD))
    .exchange().expectStatus().isOk();

  }
  
}
