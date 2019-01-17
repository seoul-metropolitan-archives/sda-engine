/*
 * Copyright (c) 2018. Dream Ant Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.service;

import eu.bitwalker.useragentutils.Browser;
import io.onsemiro.core.code.Types;
import io.onsemiro.utils.AgentUtils;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.JsonUtils;
import io.onsemiro.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rmsoft.ams.seoul.rc.rc005.service.Rc005Service;
import rmsoft.ams.seoul.utils.ArchiveUtils;
import rmsoft.ams.seoul.utils.HashUtils;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jung-young-il on 28/03/2017.
 */
@Slf4j
@Service
public class FileUploadService {
    @Value("${repository.contents}")
    private String pathContents;

    @Value("${repository.temp}")
    private String pathTemp;

    @Inject
    private FilePersistService filePersistService;

    @Inject
    private Rc005Service rc005Service;

    public AX5File upload(MultipartFile multipartFile) throws IOException {
        AX5File file = AX5File.of(multipartFile);
        filePersistService.persist(file);
        return file;
    }

    public List<AX5File> files() {
        return filePersistService.listFiles();
    }

    public AX5File getFile(String id) throws IOException {
        return filePersistService.getAx5File(id);
    }

    public void flush() {
        filePersistService.flush();
    }

    public void delete(List<AX5File> files) throws IOException {
        for (AX5File ax5File : files) {
            filePersistService.delete(ax5File);
        }
    }

    public ResponseEntity<byte[]> download(HttpServletRequest request, String id) throws IOException {
        AX5File ax5File = getFile(id);

        byte[] bytes = FileUtils.readFileToByteArray(ax5File.getFile());

        String fileName = getDisposition(request, ax5File.getFileName());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentLength(bytes.length);
        httpHeaders.setContentDispositionFormData("attachment", fileName);

        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }

    public ResponseEntity<byte[]> downloadItem(HttpServletRequest request, Map params) throws IOException {

        // itemId 로 item 관련 메타 및 파일 정보 찾아오기
        Map itemInfo = rc005Service.exportItem(params);

        // item 정보를 json으로 저장
        String instantTempPath = UUIDUtils.getUUID();
        byte[] bytes = null;
        HttpHeaders httpHeaders = null;

        try {

            File jsonMetaFile = FileUtils.getFile(pathTemp + File.separator + instantTempPath + File.separator + "itemInfo.json");
            FileUtils.write(jsonMetaFile, JsonUtils.toPrettyJson(itemInfo), "UTF-8");

            // item 에 속해있는 component 파일 추출
            List<Map> componentList = (List<Map>) itemInfo.get("componentList");

            // Component 파일 복사
            componentList.forEach(component -> {
                try {
                    FileUtils.copyFileToDirectory(FileUtils.getFile(pathContents + File.separator + component.get("filePath") + File.separator + component.get("originalFileName")), new File(pathTemp + File.separator + instantTempPath + File.separator + component.get("filePath")), true);
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            });

            // Item 정보와 Component 파일 압축
            String bagitPath = pathTemp + File.separator + instantTempPath + File.separator + "bagit";

            ArchiveUtils.archive(pathTemp + File.separator + instantTempPath, pathTemp + File.separator + instantTempPath + File.separator + "itemInfo.zip", "", false);

            // bagit temp 폴더 생성
            //FileUtils.forceMkdir(new File(bagitPath));
            FileUtils.moveFileToDirectory(FileUtils.getFile(pathTemp + File.separator + instantTempPath + File.separator + "itemInfo.zip"), FileUtils.getFile(bagitPath), true);

            File itemInfoArchiveFile = FileUtils.getFile(bagitPath + File.separator + "itemInfo.zip");

            // Bagit 정보 생성
            Map<String, String> bagitData = new HashMap<>();
            bagitData.put("fileName", itemInfoArchiveFile.getName());
            bagitData.put("fileHash", HashUtils.extractFileHashSHA256(itemInfoArchiveFile));
            bagitData.put("fileSize", itemInfoArchiveFile.length() + " bytes");
            bagitData.put("lastModified Date", itemInfoArchiveFile.lastModified() + "");

            File bagitMetaFile = FileUtils.getFile(bagitPath + File.separator + "bagit.txt");
            FileUtils.write(bagitMetaFile, JsonUtils.toPrettyJson(bagitData), "UTF-8");

            // bagit 생성
            String exportFilePath = pathTemp + File.separator + instantTempPath + File.separator + "item-" + DateUtils.getNow("yyyyMMddHHmmss") + ".zip";
            ArchiveUtils.archive(bagitPath, exportFilePath, "", false);
            // hash.txt 파일과 위 압축된 정보를 다시 한번 압축하여 temp 에 이동

            // 다운로드 실행
            AX5File ax5File = filePersistService.getAx5TempFile(exportFilePath);

            bytes = FileUtils.readFileToByteArray(ax5File.getFile());

            String fileName = getDisposition(request, ax5File.getFileName());

            httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            httpHeaders.setContentLength(bytes.length);
            httpHeaders.setContentDispositionFormData("attachment", fileName);
        } catch (Exception e) {
            log.error("아이템 내보내기 프로그램 실행중 에러가 발생하였습니다.");
            log.error(e.getMessage());
        }

        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }

    public static String getDisposition(HttpServletRequest request, String fileName) {
        try {
            Browser browser = AgentUtils.getBrowser(request);

            switch (browser) {
                case IE:
                    return URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
                case CHROME:
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < fileName.length(); i++) {
                        char c = fileName.charAt(i);
                        if (c > '~') {
                            sb.append(URLEncoder.encode("" + c, "UTF-8"));
                        } else {
                            sb.append(c);
                        }
                    }
                    return sb.toString();

                case OPERA:
                case FIREFOX:
                    return new String(fileName.getBytes("UTF-8"), "8859_1");

            }
        } catch (Exception e) {
            // ignore;
        }

        return fileName;
    }


    public void preview(HttpServletResponse response, String id) throws IOException {
        filePersistService.preview(response, id, Types.ImagePreviewType.ORIGIN);
    }

    public void thumbnail(HttpServletResponse response, String id) throws IOException {
        filePersistService.preview(response, id, Types.ImagePreviewType.THUMBNAIL);
    }
}
