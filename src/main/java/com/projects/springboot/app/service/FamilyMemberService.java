package com.projects.springboot.app.service;

import com.projects.springboot.app.entity.FamilyMember;
import com.projects.springboot.app.repository.FamilyMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FamilyMemberService {

  @Autowired
  FamilyMemberRepository familyMemberRepository;

  public Mono<FamilyMember> create(FamilyMember familyMember) {
    return familyMemberRepository.save(familyMember);
  }

  public Flux<FamilyMember> findAll() {
    return familyMemberRepository.findAll();
  }

  public Mono<FamilyMember> findById(String id) {
    return familyMemberRepository.findById(id);
  }

  /**
   * This method is created to update a FamilyMember Object by id.
   */
  public Mono<FamilyMember> update(FamilyMember previousFamilyMember, FamilyMember familyMember) {
      previousFamilyMember.setFamilyId(familyMember.getFamilyId());
      previousFamilyMember.setParentOrStudentMember(familyMember.getParentOrStudentMember());
      previousFamilyMember.setParentId(familyMember.getParentId());
      previousFamilyMember.setStudentId(familyMember.getStudentId());
      return familyMemberRepository.save(previousFamilyMember);
  }

  /**
   * This method is created to delete a FamilyMember Object by id.
   */
  public Mono<Void> delete(FamilyMember familyMember) {
    return familyMemberRepository.delete(familyMember);
  }
  
}
