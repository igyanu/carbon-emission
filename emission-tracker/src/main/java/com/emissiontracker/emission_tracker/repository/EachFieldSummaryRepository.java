package com.emissiontracker.emission_tracker.repository;


import com.emissiontracker.emission_tracker.entity.EachFieldSummary;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EachFieldSummaryRepository extends MongoRepository<EachFieldSummary, String> {

    boolean existsEachFieldSummariesById(ObjectId id);
    EachFieldSummary findEachFieldSummaryById(ObjectId id);
    boolean deleteById(ObjectId id);
    List<EachFieldSummary> findByIdIn(List<ObjectId> ids);


}
