package mtechproject.filesysmonitor;

import java.io.*;
import java.util.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;

public class ReadExcel{
	public static void main(String[]args){
		short a=0;  
		short b=1;  
		int i=0;   
		ArrayList list1=new ArrayList();
		ArrayList list2=new ArrayList();
		String   value1="", value2="";  
		String filename ="C:/hello.xls";     
		if(filename != null && !filename.equals("")){
			try{    
				FileInputStream fs =new FileInputStream(filename);    
				HSSFWorkbook wb = new HSSFWorkbook(fs);   
				for(int k = 0; k < wb.getNumberOfSheets(); k++){    
					int j=i+1;    
					HSSFSheet sheet = wb.getSheetAt(k);    
					int rows  = sheet.getPhysicalNumberOfRows();    
					for(int r =     1; r < rows; r++){    
						HSSFRow row   = sheet.getRow(r);    
						int cells = row.getPhysicalNumberOfCells();    
						HSSFCell cell1  = row.getCell(a);      
						value1 = cell1.getStringCellValue();   
						HSSFCell cell2  = row.getCell(b);     
						value2 = cell2.getStringCellValue();    

						list1.add(value1);
						list2.add(value2);   
					}       
					i++;   
				}    
			}catch(Exception e){  

			}

		}
		for(int j=0;j<list1.size();j++){
			System.out.println(list1.get(j).toString()+"\t"+list2.get(j).toString());
		}


	}
}