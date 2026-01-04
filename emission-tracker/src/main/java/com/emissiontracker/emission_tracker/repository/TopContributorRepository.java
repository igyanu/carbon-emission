package com.emissiontracker.emission_tracker.repository;


import com.emissiontracker.emission_tracker.entity.TopContributor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopContributorRepository extends MongoRepository<TopContributor, String> {

}
