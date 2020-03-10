package com.projects.springboot.app;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.projects.springboot.app.entity.Family;
import com.projects.springboot.app.entity.FamilyMember;
import com.projects.springboot.app.entity.Parent;
import com.projects.springboot.app.entity.Student;
import com.projects.springboot.app.service.FamilyMemberService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FamilyMemberTest {

  @Autowired
  private WebTestClient webClient;

  static final String USERNAME = "user";
  static final String PASSWORD = "user";
  
  @MockBean
  private FamilyMemberService familyMemberService;

  private Parent prTest;
  private Student stTest;
  private Family fmTest;
  
  private FamilyMember fmmTest;
  private FamilyMember fmmTest2;

  /**
   * This method is created because we need to start with a family Object to use
   * in all tests.
   * 
   * @throws ParseException Using this exception because the date string format
   *                        doesn't work in the default constructor
   */
  @BeforeEach
  public void beforeAll() throws ParseException {
    prTest = new Parent("M", "Lucio", "Andres",
        "Gonzales","Nice Parent");
    
    stTest = new Student("M", "Alejandro", "Mateo", "Gonzales", 
        new SimpleDateFormat("yyyy-mm-dd").parse("1994-06-06"),
        "Nice Student");
    
    fmTest = new Family(prTest, "Los Gonzales");
    
    fmmTest = new FamilyMember(fmTest,"Parent",prTest,null);
    fmmTest2 = new FamilyMember(fmTest,"Student",null,stTest);
  }
  
  
  @Test
  public void createFamilyMemberIsOkTest() throws Exception {

    when(familyMemberService.create(fmmTest)).thenReturn(Mono.just(fmmTest));

    webClient.post().uri("/api/familyMembers")
    .headers(headers -> headers.setBasicAuth(USERNAME, PASSWORD))
        .contentType(MediaType.APPLICATION_JSON).body(Mono.just(fmmTest), FamilyMember.class)
        .exchange().expectHeader().contentType(MediaType.APPLICATION_JSON)
        .expectStatus().isOk();

  }

  @Test
  public void findOneFamilyMemberIsOkTest() throws Exception {

    when(familyMemberService.findById("11L")).thenReturn(Mono.just(fmmTest));

    webClient.get().uri("/api/familyMembers/11L")
    .headers(headers -> headers.setBasicAuth(USERNAME, PASSWORD)).exchange()
        .expectHeader().contentType(MediaType.APPLICATION_JSON)
        .expectStatus().isOk().expectBodyList(FamilyMember.class);
  }

  @Test
  public void allFamilyMembersIsOkTest() throws Exception {

    fmmTest.setFamilyMemberId("11L");
    fmmTest2.setFamilyMemberId("12L");
    
    when(familyMemberService.findAll()).thenReturn(Flux.just(fmmTest, fmmTest2));

    webClient.get().uri("/api/familyMembers")
    .headers(headers -> headers.setBasicAuth(USERNAME, PASSWORD)).exchange()
        .expectHeader().contentType(MediaType.APPLICATION_STREAM_JSON_VALUE)
        .expectStatus().isOk().expectBody().jsonPath("$.length()").isEqualTo("5");

  }

  @Test
  public void updateFamilyMemberIsOkTest() throws Exception {

    fmmTest.setFamilyMemberId("11L");
    when(familyMemberService.findById(fmmTest.getFamilyMemberId())).thenReturn(Mono.just(fmmTest));
    when(familyMemberService.update(any(),any())).thenReturn(Mono.just(fmmTest2));
    fmmTest2.setFamilyMemberId("11L");

    webClient.put().uri("/api/familyMembers/{familyMemberId}","11L")
    .headers(headers -> headers.setBasicAuth(USERNAME, PASSWORD))
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(fmmTest),FamilyMember.class)
            .exchange()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.familyMemberId").isEqualTo("11L");
  }

  @Test
  public void deleteFamilyMemberIsOkTest() throws Exception {

    fmmTest.setFamilyMemberId("11L");

    when(familyMemberService.findById(fmmTest.getFamilyMemberId())).thenReturn(Mono.just(fmmTest));
    when(familyMemberService.delete(fmmTest)).thenReturn(Mono.empty());


    webClient.delete().uri("/api/familyMembers/{familyMemberId}", fmmTest.getFamilyMemberId())
    .headers(headers -> headers.setBasicAuth(USERNAME, PASSWORD))
    .exchange().expectStatus().isOk();

  }
  
}
