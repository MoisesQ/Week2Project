package com.projects.springboot.app.controllers;

import com.projects.springboot.app.config.exception.NotFoundException;
import com.projects.springboot.app.entity.Parent;
import com.projects.springboot.app.service.ParentService;
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
@RequestMapping("/api/parents")
public class ParentController {

  @Autowired
  ParentService parentService;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Service to create a parent", notes = "Save the parent's information")
  public Mono<Parent> postParent(@Valid @RequestBody Parent parent) {
    return parentService.create(parent);
  }

  @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
  @ApiOperation(value = "Service to get all parents", notes = "Get all parents's information")
  public Flux<Parent> getParents() {
    return parentService.findAll();
  }

  @GetMapping(path = "/{parentId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Service to get a parent", notes = "Get the parent's information")
  public Mono<Parent> getParentById(@PathVariable String parentId) {
    return parentService.findById(parentId)
        .switchIfEmpty(Mono.error(new NotFoundException("Not found parent by id " + parentId)));
  }

  /**
   * Updates a parent object by id and then introduce new information.
   * 
   */
  @PutMapping(path = "/{parentId}", headers = "content-type=application/json", 
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Service to update a parent", notes = "Update the parent's information")
  public Mono<Parent> updateParent(@PathVariable String parentId, 
      @Valid @RequestBody Parent parent) {
    return parentService.findById(parentId)
        .flatMap(fndParent -> parentService.update(fndParent, parent))
        .switchIfEmpty(Mono.error(new NotFoundException("Not found parent by id " + parentId)));
  }

  /**
   * Delete a parent object by id.
   * 
   */
  @DeleteMapping(path = "/{parentId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Service to delete a parent", notes = "Delete the parent's information")
  public Mono<Void> deleteParent(@PathVariable String parentId) {
    return parentService.findById(parentId)
        .switchIfEmpty(Mono.error(new NotFoundException("Not found parent by id " + parentId)))
        .flatMap(dltParent -> parentService.delete(dltParent));
  }
}
