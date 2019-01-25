package rmsoft.ams.seoul.st.st010.controller;


import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.parameter.RequestParams;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.st.st010.service.St010Service;
import rmsoft.ams.seoul.st.st010.vo.St01004VO;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping(value = "/api/v1/st/st010")
public class St010Controller extends BaseController {

    @Inject
    private St010Service st010Service;


    @GetMapping("/01/list04")
    public Responses.PageResponse getAggregation(Pageable pageable, RequestParams<St01004VO> requestParams) {
        Page<St01004VO> pages = st010Service.getAggregation(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }





    @GetMapping("/01/excelDown")
    public ResponseEntity<InputStreamResource> getExcelDown(HttpServletResponse response) throws IOException {


        ByteArrayInputStream in = st010Service.getExcelDown();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentDispositionFormData("attachment", "test.xlsx");

        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .body(new InputStreamResource(in));
}




}
