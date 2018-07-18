package rmsoft.ams.seoul.common.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rmsoft.ams.seoul.common.service.AX5File;
import rmsoft.ams.seoul.common.service.FileUploadService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * The type Common api controller.
 */
@RestController
@RequestMapping("/api/v1/common")
public class CommonFileController extends BaseController {

    @Inject
    private FileUploadService fileUploadService;

    /**
     * Controller invoker object.
     *
     * @param pageable      the pageable
     * @param serviceParams the service params
     * @param requestParams the request params
     * @return the object
     */
    @PostMapping(value = "/upload")
    public AX5File upload(@RequestParam MultipartFile file) throws IOException {
        return fileUploadService.upload(file);
    }

    @PostMapping(value = "/delete")
    public ApiResponse delete(@RequestBody List<AX5File> files) throws IOException {
        fileUploadService.delete(files);
        return ok();
    }

    @GetMapping(value = "/download")
    @ResponseBody
    public ResponseEntity<byte[]> download(HttpServletRequest request, @RequestParam String id) throws IOException {
        return fileUploadService.download(request, id);
    }

    @RequestMapping(value = "/preview", method = RequestMethod.GET)
    public void preview(HttpServletResponse response, @RequestParam String id) throws IOException {
        fileUploadService.preview(response, id);
    }

    @RequestMapping(value = "/thumbnail", method = RequestMethod.GET)
    public void thumbnail(HttpServletResponse response, @RequestParam String id) throws IOException {
        fileUploadService.thumbnail(response, id);
    }

    @GetMapping(value = "/files")
    public List<AX5File> files() {
        return fileUploadService.files();
    }

    @GetMapping(value = "/flush")
    public ApiResponse flush() {
        fileUploadService.flush();
        return ok();
    }
}
