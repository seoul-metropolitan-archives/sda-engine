package com.bgf.shbank.controller.api.mng.equip.corner_manage;

import com.bgf.shbank.controller.view.ExcelView;
import com.bgf.shbank.core.upload.AX5File;
import com.bgf.shbank.core.upload.FileUploadService;
import com.bgf.shbank.domain.mng.equip.corner_manage.CornerManage;
import com.bgf.shbank.domain.mng.equip.corner_manage.CornerManageService;
import com.bgf.shbank.domain.mng.equip.corner_manage.CornerManageVO;
import com.bgf.shbank.utils.DateUtils;
import com.google.common.collect.Maps;
import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/mng/equip/corner_manage")
public class CornerManageController extends BaseController {

    @Inject
    private CornerManageService cornerManageService;

    @Inject
    private FileUploadService fileUploadService;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<CornerManage> requestParams) {
        Page<CornerManage> pages = cornerManageService.find(pageable, requestParams);
        return Responses.PageResponse.of(CornerManageVO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<CornerManage> request) {
        cornerManageService.save(request);
        return ok();
    }

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


    @GetMapping("/m01")
    public ApiResponse findOne(CornerManageVO vo) {
        return ok();
    }



    @GetMapping("/download/excel")
    public View download(RequestParams<CornerManageVO> requestParams, Model model) {

        List<CornerManage> resultList = cornerManageService.find(requestParams);

        List<CornerManageVO> voList = CornerManageVO.of(resultList);

        Map<String, Object> params = Maps.newHashMap();
        params.put("vo", voList);

        model.addAttribute("vo", voList);
        model.addAttribute("txId", "corner_status");
        model.addAttribute("fileName", "코너 목록-" + DateUtils.getNow("yyyyMMdd_HHmmss"));

        ExcelView view = new ExcelView();
        return view;
    }
}