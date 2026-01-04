package com.emissiontracker.emission_tracker.repository;


import com.emissiontracker.emission_tracker.entity.EachFieldSummary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummaryOfEachFieldRepository extends MongoRepository<EachFieldSummary, String> {


}
