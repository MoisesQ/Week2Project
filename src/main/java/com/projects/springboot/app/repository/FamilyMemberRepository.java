package com.projects.springboot.app.repository;

import com.projects.springboot.app.entity.FamilyMember;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyMemberRepository extends ReactiveMongoRepository<FamilyMember, String> {

}

