package com.hquach.common;

import com.hquach.model.DataMapping;
import com.hquach.model.Transaction;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * System data processor which its owner defined data structure.
 * @author Hai Quach
 */

public class SystemDataProcessor extends BaseDataProcessor {

    SystemDataProcessor(DataMapping mapping) {
        super(mapping);
    }

    @Override
    protected DateTimeFormatter getDateFormatPattern() {
        return DateTimeFormatter.ofPattern("MM/dd/yyyy");
    }
}
