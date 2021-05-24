package excel;

import java.io.FileInputStream;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelFile {

	public ArrayList<String> read() {
		ArrayList<String> cellsArray = new ArrayList<String>();
		
		try {
			FileInputStream fis = new FileInputStream("file.xlsx");
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sheet = wb.getSheetAt(0);
			wb.close();
			
			for(Row row: sheet) {
				if (row.getCell(0) != null) {
					if (row.getCell(0).toString() == "") {
						for(Cell cell: row) {
							if (cell.getColumnIndex() != 0) {
								cellsArray.add(cell.toString());
							}
						}
					}
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cellsArray;
	}

}
