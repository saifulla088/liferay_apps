package com.liferay.ismail.helper;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

public class XlSheetParser {
	public static Map<String,String> parseXlSheet(File file) {
Map<String,String> map = new HashMap<String, String>();
		try {
			FileInputStream isfile = new FileInputStream(file);
			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(isfile);
			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);
			String key = StringPool.BLANK;
			String value = StringPool.BLANK;
			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if (row.getRowNum() == 0) {
					System.out.println("Continue" + row.getRowNum());
					continue;
				}
				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					// Check the cell type and format accordingly

					switch (cell.getColumnIndex()) {
					case 2:
						if (Validator.isNotNull(cell)
								&& Validator.isNotNull(cell
										.getStringCellValue())) {
							if (cell.getStringCellValue().trim().contains("=")) {
								key = cell
										.getStringCellValue()
										.trim()
										.substring(
												0,
												cell.getStringCellValue()
														.trim().indexOf('='));

							} else {
								key = cell.getStringCellValue().trim();
							}
							System.out.print("key is =" + key + "  ");

						}
						break;
					case 4:
						value = cell.getStringCellValue().trim();
						System.out.print("value==" + cell.getStringCellValue().trim());
						break;
					}
				}
				map.put(key, value);
				System.out.println("");
			}
			isfile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
return map;
	}
}
