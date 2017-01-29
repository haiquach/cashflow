package com.hquach.common;

import com.hquach.model.Snapshot;

/**
 * Factory class to build the processor for inbound and outbound integration.
 * @author Hai Quach
 */
public final class DataFactory {

    private final static String REGEX = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

    private DataFactory() {}

    public static Snapshot buildSnapshot(String data) {
        String[] items = data.split(REGEX);
        return new SystemDataProcessor(items).build();
    }

    public static String extractSnapshot(Snapshot snapshot) {
        return new SystemDataProcessor(snapshot).extract();
    }
}
