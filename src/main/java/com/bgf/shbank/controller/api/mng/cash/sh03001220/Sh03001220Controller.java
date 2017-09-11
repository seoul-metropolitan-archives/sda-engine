package com.bgf.shbank.controller.api.mng.cash.sh03001220;

import com.bgf.shbank.core.upload.AX5File;
import com.bgf.shbank.core.upload.FileUploadService;
import com.bgf.shbank.domain.mng.cash.sh03001220.Sh03001220;
import com.bgf.shbank.domain.mng.cash.sh03001220.Sh03001220Service;
import com.bgf.shbank.domain.mng.cash.sh03001220.Sh03001220VO;
import com.bgf.shbank.utils.DateUtils;
import com.bgf.shbank.utils.ModelMapperUtils;
import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/mng/cash/sh03001220")
public class Sh03001220Controller extends BaseController {

    @Value("${onsemiro.upload.repository.img}")
    private String uploadImgPath;

    @Inject
    private Sh03001220Service sh03001220Service;

    @Inject
    private FileUploadService fileUploadService;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Sh03001220> requestParams) {
        Page<Sh03001220> pages = sh03001220Service.find(pageable, requestParams);
        return Responses.PageResponse.of(Sh03001220VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody Sh03001220VO sh03001220VO) {
        if (sh03001220VO.getStextSendGubun().equals("1")) {
            return this.stextSend(sh03001220VO);
        }

        Sh03001220 sh03001220 = ModelMapperUtils.map(sh03001220VO, Sh03001220.class);

        if (!StringUtils.isEmpty(sh03001220.getCashTypeCode1RecvAmt())) {
            sh03001220.setCashTypeCode1RecvAmt(StringUtils.replace(sh03001220.getCashTypeCode1RecvAmt(), ",", ""));
        } else {
            sh03001220.setCashTypeCode1RecvAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001220.getCashTypeCode2RecvAmt())) {
            sh03001220.setCashTypeCode2RecvAmt(StringUtils.replace(sh03001220.getCashTypeCode2RecvAmt(), ",", ""));
        } else {
            sh03001220.setCashTypeCode2RecvAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001220.getCashTypeCode3RecvAmt())) {
            sh03001220.setCashTypeCode3RecvAmt(StringUtils.replace(sh03001220.getCashTypeCode3RecvAmt(), ",", ""));
        } else {
            sh03001220.setCashTypeCode3RecvAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001220.getCashTypeCode4RecvAmt())) {
            sh03001220.setCashTypeCode4RecvAmt(StringUtils.replace(sh03001220.getCashTypeCode4RecvAmt(), ",", ""));
        } else {
            sh03001220.setCashTypeCode4RecvAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001220.getCashTypeCode5RecvAmt())) {
            sh03001220.setCashTypeCode5RecvAmt(StringUtils.replace(sh03001220.getCashTypeCode5RecvAmt(), ",", ""));
        } else {
            sh03001220.setCashTypeCode5RecvAmt("0");
        }
//        sh03001220.setReqDate(DateUtils.convertToTimestamp(sh03001220VO.getReqDate(), "yyyy-MM-dd"));

//        sh03001220.setChargeEmpPhotoUrl(uploadImgPath + "/" + sh03001220.getChargeEmpPhotoUrl());
//        sh03001220.setDigitalSignUrl(uploadImgPath + "/" + sh03001220.getDigitalSignUrl());

        sh03001220.setChargeEmpPhotoUrl("/img_upload/" + sh03001220.getChargeEmpPhotoUrl());
        sh03001220.setDigitalSignUrl("/img_upload/" + sh03001220.getDigitalSignUrl());

        if(StringUtils.isEmpty(sh03001220.getTxId())){
            sh03001220.setTxId(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));
        }

        sh03001220Service.save(sh03001220);
        return ok();
    }

    @GetMapping(params = "stextSend")
    public ApiResponse stextSend(Sh03001220VO sh03001220VO) {

        if (!StringUtils.isEmpty(sh03001220VO.getCashTypeCode1RecvAmt())) {
            sh03001220VO.setCashTypeCode1RecvAmt(StringUtils.replace(sh03001220VO.getCashTypeCode1RecvAmt(), ",", ""));
        } else {
            sh03001220VO.setCashTypeCode1RecvAmt("0");
        }
        if (!StringUtils.isEmpty(sh03001220VO.getCashTypeCode2RecvAmt())) {
            sh03001220VO.setCashTypeCode2RecvAmt(StringUtils.replace(sh03001220VO.getCashTypeCode2RecvAmt(), ",", ""));
        } else {
            sh03001220VO.setCashTypeCode2RecvAmt("0");
        }
        if (!StringUtils.isEmpty(sh03001220VO.getCashTypeCode3RecvAmt())) {
            sh03001220VO.setCashTypeCode3RecvAmt(StringUtils.replace(sh03001220VO.getCashTypeCode3RecvAmt(), ",", ""));
        } else {
            sh03001220VO.setCashTypeCode3RecvAmt("0");
        }
        if (!StringUtils.isEmpty(sh03001220VO.getCashTypeCode4RecvAmt())) {
            sh03001220VO.setCashTypeCode4RecvAmt(StringUtils.replace(sh03001220VO.getCashTypeCode4RecvAmt(), ",", ""));
        } else {
            sh03001220VO.setCashTypeCode4RecvAmt("0");
        }
        if (!StringUtils.isEmpty(sh03001220VO.getCashTypeCode5RecvAmt())) {
            sh03001220VO.setCashTypeCode5RecvAmt(StringUtils.replace(sh03001220VO.getCashTypeCode5RecvAmt(), ",", ""));
        } else {
            sh03001220VO.setCashTypeCode5RecvAmt("0");
        }

        sh03001220VO.setReqDate(StringUtils.replace(sh03001220VO.getReqDate(), "-", ""));
//        sh03001220VO.setChargeEmpPhotoUrl(uploadImgPath + "/" + sh03001220VO.getChargeEmpPhotoUrl());
//        sh03001220VO.setDigitalSignUrl(uploadImgPath + "/" + sh03001220VO.getDigitalSignUrl());

        sh03001220VO.setChargeEmpPhotoUrl("/img_upload/" + sh03001220VO.getChargeEmpPhotoUrl());
        sh03001220VO.setDigitalSignUrl("/img_upload/" + sh03001220VO.getDigitalSignUrl());

        if(StringUtils.isEmpty(sh03001220VO.getTxId())){
            sh03001220VO.setTxId(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN));
        }

        ApiResponse apiResponse = sh03001220Service.stextSend(sh03001220VO);
        if (apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }

        return apiResponse;
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
}