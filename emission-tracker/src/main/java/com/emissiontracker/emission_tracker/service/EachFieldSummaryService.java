package com.emissiontracker.emission_tracker.service;

import com.emissiontracker.emission_tracker.DTO.SummaryResult;
import com.emissiontracker.emission_tracker.entity.EachFieldSummary;
import com.emissiontracker.emission_tracker.repository.EachFieldSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EachFieldSummaryService {

    private final EachFieldSummaryRepository eachFieldSummaryRepository;

    /**
     * Delete summary by ObjectId
     */
    public void deleteSummaryById(ObjectId id) {
        if (!eachFieldSummaryRepository.existsEachFieldSummariesById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Summary not found: " + id);
        }
        eachFieldSummaryRepository.deleteById(id.toString());
    }

    /**
     * Get a single summary by ObjectId
     */
    public EachFieldSummary viewSummary(ObjectId id) {
        EachFieldSummary summary = eachFieldSummaryRepository.findEachFieldSummaryById(id);
        if (summary == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Summary not found: " + id);
        }
        return summary;
    }

    /**
     * Get multiple summaries by list of ObjectIds
     * Returns both found summaries and missing IDs
     */
    public SummaryResult viewSummaries(List<ObjectId> idList) {
        if (idList == null || idList.isEmpty()) {
            return new SummaryResult(Collections.emptyList(), Collections.emptyList());
        }

        List<EachFieldSummary> summaries = eachFieldSummaryRepository.findByIdIn(idList);

        // Find missing IDs
        List<ObjectId> foundIds = summaries.stream()
                .map(s -> new ObjectId(s.getId()))
                .collect(Collectors.toList());

        List<ObjectId> missingIds = idList.stream()
                .filter(id -> !foundIds.contains(id))
                .collect(Collectors.toList());

        return new SummaryResult(summaries, missingIds);
    }

    /**
     * Get all summaries
     */
    public List<EachFieldSummary> findAll() {
        return eachFieldSummaryRepository.findAll();
    }

    /**
     * Create and save a new summary
     * Returns the ObjectId of the saved summary
     */
    public ObjectId saveSummary(double cloth, double electric, double food,
                                double travel, double waste) {

        double total = cloth + electric + food + travel + waste;

        EachFieldSummary summary = EachFieldSummary.builder()
                .clothEmission(cloth)
                .electricityEmission(electric)
                .foodEmission(food)
                .transportEmission(travel)
                .wasteEmission(waste)
                .totalEmission(total)
                .date(LocalDate.now())
                .build();

        EachFieldSummary saved = eachFieldSummaryRepository.save(summary);
        return new ObjectId(saved.getId());
    }
}
