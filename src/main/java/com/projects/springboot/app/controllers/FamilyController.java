package com.projects.springboot.app.controllers;

import com.projects.springboot.app.config.exception.NotFoundException;
import com.projects.springboot.app.entity.Family;
import com.projects.springboot.app.service.FamilyService;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/families")
public class FamilyController {

  @Autowired
  FamilyService familyService;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Service to create a family", notes = "Save the family's information")
  public Mono<Family> postFamily(@Valid @RequestBody Family family) {
    return familyService.create(family);
  }

  @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
  @ApiOperation(value = "Service to get all families", notes = "Get all families's information")
  public Flux<Family> getFamilies() {
    return familyService.findAll();
  }

  @GetMapping(path = "/{familyId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Service to get a family", notes = "Get the family's information")
  public Mono<Family> getFamilyById(@PathVariable String familyId) {
    return familyService.findById(familyId)
        .switchIfEmpty(Mono.error(new NotFoundException("Not found family by id " + familyId)));
  }

  /**
   * Update a family object by id and then introduce new information.
   * 
   */
  @PutMapping(path = "/{familyId}", headers = "content-type=application/json", 
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Service to update a family", notes = "Update the family's information")
  public Mono<Family> updateFamily(@PathVariable String familyId, 
      @Valid @RequestBody Family family) {
    return familyService.findById(familyId)
        .flatMap(fndFamily -> familyService.update(fndFamily, family))
        .switchIfEmpty(Mono.error(new NotFoundException("Not found family by id " + familyId)));
  }

  /**
   * Delete a parent object by id and then introduce new information.
   * 
   */
  @DeleteMapping(path = "/{familyId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Service to delete a family", notes = "Delete the family's information")
  public Mono<Void> deleteFamily(@PathVariable String familyId) {
    return familyService.findById(familyId)
        .switchIfEmpty(Mono.error(new NotFoundException("Not found family by id " + familyId)))
        .flatMap(dltFamily -> familyService.delete(dltFamily));
  }
  
}
