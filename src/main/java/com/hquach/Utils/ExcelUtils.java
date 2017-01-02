package com.hquach.Utils;

import com.hquach.model.CashFlowItem;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * Excel Utilities for import/export data process
 * @author Hai Quach
 */
public class ExcelUtils {
    private static final Logger LOG = LoggerFactory.getLogger(ExcelUtils.class);

    /**
     * Import excel file to collection of cash flow item
     * @param input excel file
     * @return collection of cash flow item
     */
    public static Collection<CashFlowItem> processFile(InputStream input) {
        Collection<CashFlowItem> items = new ArrayList<CashFlowItem>();
        try {
            //Get the workbook instance for XLS file
            HSSFWorkbook workbook = new HSSFWorkbook(input);
            //Get first sheet from the workbook
            HSSFSheet sheet = workbook.getSheetAt(0);
            //Iterate through each rows from first sheet
            Iterator<Row> rowIterator = sheet.iterator();
            while(rowIterator.hasNext()) {
                Row row = rowIterator.next();
                //For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.iterator();

                CashFlowItem item = new CashFlowItem();
                int cellNumber = 0;
                while(cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch(cellNumber) {
                        case 0:
                            item.setType(cell.getStringCellValue());
                            break;
                        case 1:
                            item.setCategory(cell.getStringCellValue());
                            break;
                        case 2:
                            item.setEffective(cell.getDateCellValue());
                            break;
                        case 3:
                            item.setAmount(cell.getNumericCellValue());
                            break;
                        case 4:
                            item.setUserId(cell.getStringCellValue());
                            break;
                        case 5:
                            item.setNote(cell.getStringCellValue());
                            break;
                        case 6:
                            item.setReceipt(cell.getStringCellValue());
                            break;
                    }
                    cellNumber++;
                }
                items.add(item);
            }

        } catch (FileNotFoundException e) {
            LOG.error("File not found", e);
        } catch (IOException e) {
            LOG.error("Error in reading file", e);
        }
        return items;
    }

    /**
     * Export cash flow items to excel file
     * 0 : type
     * 1 : category
     * 2 : effective date
     * 3 : amount
     * 4 : user id
     * @param items
     * @return workbook
     */
    public static HSSFWorkbook createFile(Collection<CashFlowItem> items) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sample sheet");

        CreationHelper createHelper = workbook.getCreationHelper();
        // Date cell style
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("MM/dd/yyyy"));

        // Currency cell style
        CellStyle currencyStyle = workbook.createCellStyle();
        DataFormat df = workbook.createDataFormat();
        currencyStyle.setDataFormat(df.getFormat("$#,###.00"));

        int rowNumber = 0;
        for (CashFlowItem item : items) {
            Row row = sheet.createRow(rowNumber++);
            int cellNumber = 0;
            row.createCell(cellNumber++).setCellValue(item.getType());
            row.createCell(cellNumber++).setCellValue(item.getCategory());
            Cell dateCell = row.createCell(cellNumber++);
            dateCell.setCellValue(item.getEffective());
            dateCell.setCellStyle(dateStyle);
            Cell amountCell = row.createCell(cellNumber++);
            amountCell.setCellValue(item.getAmount());
            amountCell.setCellStyle(currencyStyle);
            row.createCell(cellNumber++).setCellValue(item.getUserId());
            row.createCell(cellNumber++).setCellValue(item.getNote());
            row.createCell(cellNumber++).setCellValue(item.getReceipt());
        }
        return workbook;
    }

    public static void exportFile(OutputStream outputStream, Collection<CashFlowItem> items) {
        try {
            HSSFWorkbook workbook = createFile(items);
            workbook.write(outputStream);
        } catch (FileNotFoundException e) {
            LOG.error("File not found", e);
        } catch (IOException e) {
            LOG.error("Error in reading file", e);
        }
    }
}
