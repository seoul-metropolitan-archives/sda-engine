package rmsoft.ams.seoul.common.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;


public class PDFtoJPGConverter {
    public File convertPdfToImage(File file, String destination) throws Exception {

        File destinationFile = new File(destination);

        if (!destinationFile.exists()) {
            destinationFile.mkdir();
            System.out.println("DESTINATION FOLDER CREATED -> " + destinationFile.getAbsolutePath());
        }else if(destinationFile.exists()){
            System.out.println("DESTINATION FOLDER ALLREADY CREATED!!!");
        }else{
            System.out.println("DESTINATION FOLDER NOT CREATED!!!");
        }

        if (file.exists()) {
            PDDocument doc = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(doc);

            String fileName = file.getName().replace(".pdf", "");
            File convertedFile = new File(destination + File.separator + fileName + ".jpg"); // jpg or png
            BufferedImage image = renderer.renderImageWithDPI(0, 200);
            // 200 is sample dots per inch.
            // if necessary, change 200 into another integer.
            ImageIO.write(image, "JPEG", convertedFile); // JPEG or PNG

            doc.close();
            return convertedFile;
        } else {
            System.err.println(file.getName() + " FILE DOES NOT EXIST");
        }
        return null;
    }
}
