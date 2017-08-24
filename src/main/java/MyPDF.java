import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

import com.itextpdf.io.IOException;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;

public class MyPDF {
	
	public static void main(String[] args) throws FileNotFoundException, MalformedURLException {
		
		PdfWriter writer = new PdfWriter("mojPrviDoc.PDF");
		PdfDocument pdf = new PdfDocument(writer);
		Document document = new Document(pdf);
		
		String hello = "Hello world from Amel today";
		
		Paragraph paragraph = new Paragraph(hello);
		document.add(paragraph);
		
		Image image = new Image(ImageDataFactory.create("C:/Users/Amel/Desktop/artb.jpg"));
		
		paragraph.add(image);
		document.add(paragraph);
			
		
		
		document.close();
		
		System.out.println("Document gotov");
		
	}//Main end
	
	
	
	public void print() {
	    //The desktop api can help calling other applications in our machine
	    //and also many other features...
	    Desktop desktop = Desktop.getDesktop();
	    try {
	    //desktop.print(new File("DocXfile.docx"));
	        desktop.print(new File("Docfile.pdf"));
	    } catch (IOException e) {           
	        e.printStackTrace();
	    } catch (java.io.IOException e) {
			e.printStackTrace();
		}
	} //End print
	
	
}
