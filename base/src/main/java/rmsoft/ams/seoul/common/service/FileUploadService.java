/*
 * Copyright (c) 2018. Dream Ant Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.service;

import eu.bitwalker.useragentutils.Browser;
import io.onsemiro.core.code.Types;
import io.onsemiro.utils.AgentUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by jung-young-il on 28/03/2017.
 */
@Service
public class FileUploadService {

    @Inject
    private FilePersistService filePersistService;

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
