package com.emissiontracker.emission_tracker.DTO;

import com.emissiontracker.emission_tracker.entity.EachFieldSummary;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.List;


@Data
public class SummaryResult {
    private List<EachFieldSummary> summaries;
    private List<ObjectId> missingIds;

    public SummaryResult(List<EachFieldSummary> summaries, List<ObjectId> missingIds) {
        this.summaries = summaries;
        this.missingIds = missingIds;
    }

    public List<EachFieldSummary> getSummaries() {
        return summaries;
    }

    public List<ObjectId> getMissingIds() {
        return missingIds;
    }
}

