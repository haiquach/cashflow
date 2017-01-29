package com.hquach.common;

import com.hquach.model.Snapshot;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is used for building or extracting from line of cvs to transaction or from transaction to cvs
 */
public interface DataProcessor {

    default String dateFormat() { return "MM/dd/yyyy"; }

    default Map<String, Integer> getCsvColumns() {
        Map<String, Integer> result = new HashMap();
        result.put("category", 1);
        result.put("amount", 2);
        result.put("description", 3);
        result.put("date", 4);
        return Collections.unmodifiableMap(result);
    }

    Snapshot build();

    String extract();
}
