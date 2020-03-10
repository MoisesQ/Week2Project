package com.projects.springboot.app.repository;

import com.projects.springboot.app.entity.Parent;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentRepository extends ReactiveMongoRepository<Parent, String> {

}
