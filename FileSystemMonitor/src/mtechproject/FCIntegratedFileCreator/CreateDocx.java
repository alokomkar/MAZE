package mtechproject.FCIntegratedFileCreator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import mtechproject.useraccounts.Database_Credentials;

import org.docx4j.convert.out.pdf.PdfConversion;
import org.docx4j.convert.out.pdf.viaXSLFO.PdfSettings;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

public class CreateDocx implements Database_Credentials{
	
	public static void main(String args[]){
		
		WordprocessingMLPackage wordMLPackage = null;
		try {
			wordMLPackage = WordprocessingMLPackage.createPackage();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		wordMLPackage.getMainDocumentPart().addParagraphOfText("Hello Word!");
		try {
			wordMLPackage.save(new java.io.File("E:/HelloWord1.docx"));
			// 1) Load DOCX into WordprocessingMLPackage
			InputStream is = new FileInputStream(new File("E:/HelloWord1.docx"));
			wordMLPackage = WordprocessingMLPackage
					.load(is);

			// 2) Prepare Pdf settings
			PdfSettings pdfSettings = new PdfSettings();

			// 3) Convert WordprocessingMLPackage to Pdf
			OutputStream out = new FileOutputStream(new File(
					"E:/HelloWord1.pdf"));
			PdfConversion converter = new org.docx4j.convert.out.pdf.viaXSLFO.Conversion(
					wordMLPackage);
			converter.output(out, pdfSettings);


		} catch (Docx4JException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			}

}
