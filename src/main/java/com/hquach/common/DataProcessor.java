package com.hquach.common;

import com.hquach.model.Snapshot;
import com.hquach.model.Transaction;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is used for building or extracting from line of cvs to transaction or from transaction to cvs
 */
public interface DataProcessor {

    public final static String DEFAULT_NAME = "___SYSTEM___";
    public final static Integer SKIP_VALUE = 999;

    /**
     * Validate a line of data (from CSV file).
     * @param data csv data
     * @return error message
     */
    String validate(String data);

    /**
     * Build a line of data (from CSV file) to transaction object
     * @param data
     * @return
     */
    Transaction readCsv(String data);

    /**
     * Write a collection of transaction to output stream.
     * @param outputStream output stream
     * @param transactions collection of transactions
     * @throws IOException
     */
    void writeCsv(OutputStream outputStream, Collection<Transaction> transactions) throws IOException;
}
