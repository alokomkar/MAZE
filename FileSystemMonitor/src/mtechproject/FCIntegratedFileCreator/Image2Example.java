package mtechproject.FCIntegratedFileCreator;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.net.URL;

public class Image2Example {
    public static void main(String[] args) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document,
                    new FileOutputStream("E:/Image2.pdf"));
            document.open();

           // String imageUrl = "http://jenkov.com/images/" +
             //       "20081123-20081123-3E1W7902-small-portrait.jpg";
           // String Imagename = "E:\\ICON.png";
            String imageUrl ="E:/ICON.png";
            Image image = Image.getInstance("E:/ICON.png");
            image.setAbsolutePosition(500f, 650f);
            document.add(image);

            document.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}