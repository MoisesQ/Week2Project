package com.projects.springboot.app.service;

import com.projects.springboot.app.entity.Family;
import com.projects.springboot.app.repository.FamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FamilyService {

  @Autowired
  FamilyRepository familyRepository;

  public Mono<Family> create(Family family) {
    return familyRepository.save(family);
  }

  public Flux<Family> findAll() {
    return familyRepository.findAll();
  }

  public Mono<Family> findById(String id) {
    return familyRepository.findById(id);
  }

  /**
   * This method is created to update a Family Object by id.
   */
  public Mono<Family> update(Family previousFamily, Family family) {
    previousFamily.setFamilyName(family.getFamilyName());
    previousFamily.setHeadOfFamilyParentId(family.getHeadOfFamilyParentId());
    return familyRepository.save(previousFamily);
  }

  /**
   * This method is created to delete a Family Object by id.
   */
  public Mono<Void> delete(Family family) {
    return familyRepository.delete(family);
  }

}
