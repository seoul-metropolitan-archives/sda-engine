/*
 * Copyright (c) 2018. Dream Ant Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.JsonUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;


@Slf4j
@Data
public class AX5File implements Comparable {

    private String id;

    private String fileName;

    private String ext;

    private long fileSize;

    private String createdAt;

    @JsonIgnore
    private File file;

    @JsonIgnore
    private MultipartFile multipartFile;

    private long lastModified;

    private String originalFileName;

    private String contentsSize;

    private String fileFormatUuid;

    public static AX5File of(MultipartFile multipartFile) {
        AX5File ax5File = new AX5File();

        ax5File.setId(ax5File.makeFileId());
        ax5File.setFileName(FilenameUtils.getName(multipartFile.getOriginalFilename()));
        ax5File.setExt(FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
        ax5File.setFileSize(multipartFile.getSize());
        ax5File.setCreatedAt(LocalDateTime.now().toString());
        ax5File.setMultipartFile(multipartFile);
        ax5File.setOriginalFileName(ax5File.getSaveName());
        ax5File.setContentsSize(String.valueOf(ax5File.getFileSize()));
        ax5File.setFileFormatUuid(ax5File.getExt());
        return ax5File;
    }

    public static AX5File of(String filePath, String fileId) {
        AX5File ax5File = new AX5File();
        ObjectMapper objectMapper = new ObjectMapper();
        ax5File.setId(fileId);

        Map<String, Object> tempJsonTemplate = null;

        String fullPath  = filePath + File.separator + ax5File.getFilePath() + File.separator + ax5File.getJsonName();
        try {
            ax5File = JsonUtils.fromJson(FileUtils.readFileToString(new File(fullPath), "UTF-8"), AX5File.class);
            ax5File.setFile(new File(filePath + File.separator + ax5File.getSaveName()));
        } catch (IOException e) {
            log.error("AX5FileJsonParser-parse :: {}", e.getMessage());
        }

        return ax5File;
    }


    public String getSaveName() {
        return String.format("%s.%s", id, ext);
    }

    /*public String getThumbnailSaveName() {
        return String.format("%s-thumbnail.%s", id, ext);
    }*/

    public String getJsonName() {
        return String.format("%s.json", id, ext);
    }

    @Override
    public int compareTo(Object o) {
        AX5File ax5File = (AX5File) o;

        long result = lastModified - ax5File.lastModified;
        if (result < 0) {
            return -1;
        } else if (result > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    /*@JsonProperty("preview")
    public String preview() {
        return "/api/v1/common/preview?id=" + getId();
    }*/

  /*  @JsonProperty("thumbnail")
    public String thumbnail() {
        return "/api/v1/common/thumbnail?id=" + getId();
    }*/

    /*@JsonProperty("download")
    public String download() {
        return "/api/v1/common/download?id=" + getId();
    }*/

    public String makeFileId() {
        String fileId = String.format("%s%s", DateUtils.getNow("yyyyMMdd"), UUID.randomUUID().toString());

        return fileId;
    }

    public String getFilePath() {
        String fileSavePath = id.substring(0, 4) + File.separator + id.substring(4, 6) + File.separator + id.substring(6, 8);
        return fileSavePath;
    }
}
