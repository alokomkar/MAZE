package mtechproject.FCIntegratedFileCreator;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Scanner;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


public class TextToPDFConverter{
	public static void main(String arg[]){
		try{
			//InputStreamReader in= new InputStreamReader(System.in);
			//BufferedReader bin= new BufferedReader(in);
			//BufferedReader bin= new BufferedReader(new FileReader("E:/jacob-taxes-2012-8.txt"));
			//System.out.println("Enter text:");
			StringBuffer fileData = new StringBuffer();
			BufferedReader reader = new BufferedReader(
					new FileReader("E:/jacob-taxes-2012-8.txt"));
			char[] buf = new char[1024];
			int numRead=0;
			while((numRead = reader.read(buf)) != -1){
				String readData = String.valueOf(buf, 0, numRead);
				fileData.append(readData);
				//Scanner in = new Scanner(readData).useDelimiter("[^0-9]+");
				//int integer1 = in.nextInt();
				//String alphaAndDigits = readData.replaceAll("[^a-zA-Z0-9]+","");

				//String intvalue = alphaAndDigits.replaceAll("[a-zA-Z][]", "");
				//if(intvalue.length() == 16){
					//System.out.println(intvalue);
				//}
				//System.out.println(integer1);
				//String numericalString = "";
				//numericalString.concat(integer);
				

				reader.close();
				

				//String text = null;
				//while(!bin.equals("EOF")){
				//text=bin.readLine();
				}
			String text = fileData.toString();
				Document document = new Document(PageSize.A4, 36, 72, 108, 180);
				PdfWriter.getInstance(document,new FileOutputStream("E:/pdfFile.pdf"));
				document.open();
				document.add(new Paragraph(text));
				System.out.println("Text is inserted into pdf file");
				document.close();
			}catch(Exception e){}

		}
	}
