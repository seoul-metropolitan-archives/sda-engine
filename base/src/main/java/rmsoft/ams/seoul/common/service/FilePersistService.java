/*
 * Copyright (c) 2018. Dream Ant Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.service;

import io.onsemiro.core.code.Types;
import io.onsemiro.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.name.Rename;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by jung-young-il on 28/03/2017.
 */
@Service
@Slf4j
public class FilePersistService implements InitializingBean {

    @Value("${repository.upload}")
    private String path;

    private String fileSavePath;
    private AX5File ax5File;
    private String fileType;

    public void persist(AX5File ax5File) throws IOException {
        this.ax5File = ax5File;
        System.out.println("PATH:"+path);
        fileSavePath = path + File.separator + ax5File.getFilePath();
        FileUtils.forceMkdir(new File(fileSavePath));
        File file = new File(fileSavePath + File.separator + ax5File.getSaveName());
        PDFtoJPGConverter pdFtoJPGConverter = null;

        // 파일 로컬시스템에 저장
        ax5File.getMultipartFile().transferTo(file);

        // JSON 정보 저장
//        FileUtils.writeStringToFile(new File(fileSavePath + File.separator + ax5File.getJsonName()), JsonUtils.toJson(ax5File), "UTF-8");

        fileType = getFileType(ax5File.getExt());

        if (fileType.equals(Types.FileType.PDF)) {
            try {
                pdFtoJPGConverter = new PDFtoJPGConverter();
                File tempFile = pdFtoJPGConverter.convertPdfToImage(file, fileSavePath);
                makeThumbnail(tempFile, ax5File.getThumbnailFileName().replace(".pdf", ".jpg"));
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        }

        if (fileType.equals(Types.FileType.IMAGE) && !fileType.equals(Types.FileExtensions.BMP)) {
            makeThumbnail(file, ax5File.getThumbnailFileName());
        }
    }

    public void makeThumbnail(File inputFile, String thumbnailName) throws IOException {
        try {
            Thumbnails.of(inputFile)
                    .crop(Positions.CENTER)
                    .size(44, 40)
                    .toFiles(new File(fileSavePath), Rename.SUFFIX_HYPHEN_THUMBNAIL);

            if (FileUtils.sizeOf(new File(fileSavePath + File.separator + thumbnailName)) > 0) {
                ax5File.setThumbnailContent(FileUtils.readFileToByteArray(new File(fileSavePath + File.separator + thumbnailName)));
                FileUtils.deleteQuietly(new File(fileSavePath + File.separator + thumbnailName));
                if (fileType.equals(Types.FileType.PDF)) {
                    FileUtils.deleteQuietly(inputFile);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        FileUtils.forceMkdir(new File(path));
    }

    public List<AX5File> listFiles() {
        List<AX5File> files = FileUtils.listFiles(new File(path), new String[]{"json"}, false).stream().map(file -> {
            try {
                return getAx5File(file);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            return null;
        }).collect(toList());

        Collections.sort(files);

        return files;
    }

    public AX5File getAx5File(File file) throws IOException {
        AX5File ax5File = JsonUtils.fromJson(FileUtils.readFileToString(file, "UTF-8"), AX5File.class);
        ax5File.setLastModified(file.lastModified());
        String id = ax5File.getId();
        String fileSavePath = path + File.separator + id.substring(0, 4) + File.separator + id.substring(4, 6) + File.separator + id.substring(6, 8);
        ax5File.setFile(new File(fileSavePath + File.separator + ax5File.getSaveName()));
        return ax5File;
    }

    public AX5File getAx5File(String id) throws IOException {
        String fileSavePath = path + File.separator + id.substring(0, 4) + File.separator + id.substring(4, 6) + File.separator + id.substring(6, 8);
        return getAx5File(new File(fileSavePath + File.separator + id + ".json"));
    }

    public AX5File getAx5TempFile(String tempFilePath) throws IOException {
        File tempFile = new File(tempFilePath);

        AX5File ax5File = new AX5File();
        ax5File.setLastModified(tempFile.lastModified());
        ax5File.setId("");
        ax5File.setFileName(tempFile.getName());
        ax5File.setFileHash(tempFile.hashCode());
        ax5File.setFileSize(tempFile.length());
        ax5File.setExt(FilenameUtils.getExtension(tempFile.getName()));
        ax5File.setFile(tempFile);

        return ax5File;
    }

    public void flush() {
        FileUtils.deleteQuietly(new File(path));
    }

    public void delete(AX5File ax5File) throws IOException {
        ax5File = getAx5File(ax5File.getId());
        String fileId = ax5File.getId();
        String fileSavePath = path + File.separator + ax5File.getFilePath();
        FileUtils.deleteQuietly(new File(fileSavePath + File.separator + ax5File.getSaveName()));
//        FileUtils.deleteQuietly(new File(fileSavePath + File.separator + ax5File.getJsonName()));
//        FileUtils.deleteQuietly(new File(fileSavePath + File.separator + ax5File.getThumbnailSaveName()));
    }

    public void preview(HttpServletResponse response, String id, String type) throws IOException {
        AX5File ax5File = getAx5File(id);

        if (ax5File == null)
            return;

        MediaType mediaType = null;
        String imagePath = "";

        switch (ax5File.getExt().toUpperCase()) {
            case Types.FileExtensions.JPEG:
            case Types.FileExtensions.JPG:
                mediaType = MediaType.IMAGE_JPEG;
                break;

            case Types.FileExtensions.PNG:
                mediaType = MediaType.IMAGE_PNG;
                break;

            case Types.FileExtensions.GIF:
                mediaType = MediaType.IMAGE_GIF;
                break;

            default:
        }

        switch (type) {
            case Types.ImagePreviewType.ORIGIN:
                imagePath = ax5File.getSaveName();
                break;

            /*case Types.ImagePreviewType.THUMBNAIL:
                imagePath = ax5File.getThumbnailSaveName();
                break;*/
        }

        if (mediaType != null) {
            String fileSavePath = path + File.separator + ax5File.getFilePath();
            File file = new File(fileSavePath + File.separator + imagePath);
            byte[] bytes = FileUtils.readFileToByteArray(file);

            response.setContentType(mediaType.toString());
            response.setContentLength(bytes.length);
            response.getOutputStream().write(bytes);
        }
    }

    private String getFileType(String extension) {
        switch (extension.toUpperCase()) {
            case Types.FileExtensions.PNG:
            case Types.FileExtensions.JPG:
            case Types.FileExtensions.JPEG:
            case Types.FileExtensions.GIF:
            case Types.FileExtensions.BMP:
            case Types.FileExtensions.TIFF:
            case Types.FileExtensions.TIF:
                return Types.FileType.IMAGE;

            case Types.FileExtensions.PDF:
                return Types.FileType.PDF;

            default:
                return Types.FileType.ETC;
        }
    }
}
