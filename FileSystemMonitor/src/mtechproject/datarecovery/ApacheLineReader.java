package mtechproject.datarecovery;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mysql.jdbc.StringUtils;

public class ApacheLineReader {
	
	public static void main(String args[]){
		//ApacheLineReader.FileLineHashCreator("E:\\DVFS\\Soumya_DFS\\OFD.docx");
		ApacheLineReader.FileLineHashCreator("C:\\My Details1.docx");
	}
	
	public static void FileLineHashCreator(String fname){

		File file = null;
		WordExtractor extractor = null ;
		try {

			file = new File(fname);
			FileInputStream fis=new FileInputStream(file.getAbsolutePath());
			XWPFDocument docIn;  
			XWPFWordExtractor extractor1;
			String docText;
			String [] fileData = null;
			List<String> lines = new ArrayList<String>();
			if(file.toString().contains(".docx")){
				docIn = new XWPFDocument(fis);
				
				extractor1 = new XWPFWordExtractor(docIn);
				docText = extractor1.getText();
				ZipFile docxFile = new ZipFile( file );
		        ZipEntry documentXML = docxFile.getEntry( "word/document.xml" );
		        InputStream documentXMLIS = docxFile.getInputStream( documentXML );
		        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		        Document doc = dbf.newDocumentBuilder().parse( documentXMLIS );
		 
		        Element tElement = doc.getDocumentElement();
		        NodeList n = (NodeList) tElement.getElementsByTagName( "w:p" );
		 
		        for ( int j = 0; j < n.getLength(); j++ ) {
		 
		            Node child = n.item( j );
		            String line = child.getTextContent();
		 
		            if ( line != null && !line.trim().isEmpty() ) {
		            	
		                lines.add( line.trim() );
		 
		            }
		        }
		        Iterator itr = lines.iterator();
		        
		        while(itr.hasNext()){
		        	//System.out.println(itr.next().toString());
		        	SHAshing.hashing(file.getAbsolutePath(), itr.next().toString());
		        }
				//fileData = lines.toString();
		        //System.out.println(fileData);
				
			}
			else if(file.toString().contains(".doc")){
				HWPFDocument document=new HWPFDocument(fis);
				extractor = new WordExtractor(document);
				docText = extractor.getText();
				fileData = extractor.getParagraphText();
			}
              
			
			for(int i=0;i<fileData.length;i++){
				if(fileData[i] != null && !StringUtils.isEmptyOrWhitespaceOnly(fileData[i])){
					
					//System.out.println(fileData[i]);
					SHAshing.hashing(file.getAbsolutePath(),fileData[i]);
				}
			}
		}
		catch(Exception exep){}
	}

}
