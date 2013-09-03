package mtechproject.FCIntegratedFileCreator;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.FileOutputStream;
import java.io.IOException;

public class PdfStamperExample {

  public static void main(String[] args) {
    try {
      PdfReader pdfReader = new PdfReader("E:/CVNChangeNotice_Adithya.pdf");

      PdfStamper pdfStamper = new PdfStamper(pdfReader,
            new FileOutputStream("E:/CVNChangeNotice_Adithya_Stamped.pdf"));

      Image image = Image.getInstance("E:/ICON.png");

      for(int i=1; i<= pdfReader.getNumberOfPages(); i++){

          PdfContentByte content = pdfStamper.getUnderContent(i);

          image.setAbsolutePosition(400f, 750f);
          image.scaleAbsolute(60f, 60f);

          content.addImage(image);
      }

      pdfStamper.close();

    } catch (IOException e) {
      e.printStackTrace();
    } catch (DocumentException e) {
      e.printStackTrace();
    }
  }
}