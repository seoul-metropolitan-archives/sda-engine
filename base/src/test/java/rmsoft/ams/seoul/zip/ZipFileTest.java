package rmsoft.ams.seoul.zip;

import lombok.extern.log4j.Log4j;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The type Zip file test.
 */
@Log4j
public class ZipFileTest {


    /**
     * Zip.
     *
     * @param targetPath          the target path
     * @param destinationFilePath the destination file path
     * @param password            the password
     */
    public static void zip(String targetPath, String destinationFilePath, String password) {
        try {
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

            if (password.length() > 0) {
                parameters.setEncryptFiles(true);
                parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
                parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
                parameters.setPassword(password);
            }

            ZipFile zipFile = new ZipFile(destinationFilePath);

            File targetFile = new File(targetPath);
            if (targetFile.isFile()) {
                zipFile.addFile(targetFile, parameters);
            } else if (targetFile.isDirectory()) {
                zipFile.addFolder(targetFile, parameters);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Unzip.
     *
     * @param targetZipFilePath     the target zip file path
     * @param destinationFolderPath the destination folder path
     * @param password              the password
     */
    public static void unzip(String targetZipFilePath, String destinationFolderPath, String password) {
        try {
            ZipFile zipFile = new ZipFile(targetZipFilePath);
            zipFile.setFileNameCharset("EUC-KR");
            if (zipFile.isEncrypted()) {
                zipFile.setPassword(password);
            }
            zipFile.extractAll(destinationFolderPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets entry set.
     *
     * @param unzippedFolderPath the unzipped folder path
     */
    public static void getEntrySet(String unzippedFolderPath) {
        try {
            Files.newDirectoryStream(Paths.get(unzippedFolderPath)).forEach(path -> {
                //File tfile = new File(path.toUri());
                if (Files.isDirectory(path)) {
                    log.info("Aggregation: " + path.getFileName());
                    ZipFileTest.getEntrySet(path.toString());
                }else{
                    log.info("Item and Component:" + path.getFileName());
                }

            });

            //Files.walk(Paths.get(unzippedFolderPath)).filter(Files::isRegularFile).forEach(System.out::println);

           /* Files.walk(Paths.get(unzippedFolderPath)).forEach(path -> {
                if (Files.isDirectory(path)) {
                    log.info("Aggregation: " + path.getFileName());
                    ZipFileTest.getEntrySet(path.toString());
                } else {
                    log.info("Item and Component:" + path.getFileName());
                }
            });*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    /**/ /// for test only
    public static void main(String[] args) {

        String targetPath = "D:\\02.DreamAntDev\\git-workspaces\\java\\seoul-ams\\archive_temp";
        String zipFilePath = "D:\\상주시.zip";
        String unzippedFolderPath = "D:\\seoul-ams-repo\\archive_temp";
        String password = "your_password"; // keep it EMPTY<""> for applying no password protection

        //ZipFileTest.zip(targetPath, zipFilePath, password);
        ZipFileTest.unzip(zipFilePath, unzippedFolderPath, password);
        ZipFileTest.getEntrySet(unzippedFolderPath);
    }/**/
}
