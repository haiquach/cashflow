package com.hquach.common;

import com.hquach.model.DataMapping;
import com.hquach.model.User;

/**
 * Factory class to readCsv the processor for inbound and outbound integration.
 * @author Hai Quach
 */
public final class DataFactory {

    private DataFactory() {}

    public static DataProcessor getProcessor(User user, String name) {
        DataMapping dataMapping = user.getDataDefined(name);
        if (dataMapping != null) {
            return new CustomDataProcessor(dataMapping);
        }
        return new SystemDataProcessor(DataMapping.DEFAULT_MAPPING);
    }
}
