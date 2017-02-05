package com.hquach.common;

import com.hquach.model.DataMapping;
import com.hquach.model.Transaction;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Map;

/**
 * Custom data processor which user defined data structure of a line.
 * @author Hai Quach
 */
public class CustomDataProcessor extends BaseDataProcessor {

    protected CustomDataProcessor(DataMapping mapping) {
        super(mapping);
    }

    @Override
    protected DateTimeFormatter getDateFormatPattern() {
        return DateTimeFormatter.ofPattern("MM/dd/yyyy");
    }
}
