package com.hquach.common;

/**
 * Factory class to readCsv the processor for inbound and outbound integration.
 * @author Hai Quach
 */
public final class DataFactory {

    private DataFactory() {}

    public static DataProcessor getProcessor() {
        return new SystemDataProcessor();
    }
}
