package rmsoft.ams.seoul.st.st021.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rmsoft.ams.seoul.st.st008.vo.St00801VO;
import rmsoft.ams.seoul.st.st021.service.St021Service;
import rmsoft.ams.seoul.st.st021.vo.St02101VO;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping(value = "/api/v1/st/st021")
public class St021Controller extends BaseController {

    @Inject
    private St021Service service;

    @GetMapping("/01/list01")
    public Responses.PageResponse getStWithoutNoticeInoutHistStatistic(Pageable pageable, RequestParams<St02101VO> requestParams) {
        Page<St02101VO> pages = service.getStWithoutNoticeInoutHistStatistic(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }
    @GetMapping("/01/excelDown")
    public ResponseEntity<InputStreamResource> getExcelDown(RequestParams<St02101VO>  vo) throws IOException {


        ByteArrayInputStream in = service.getExcelDown(vo);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentDispositionFormData("attachment", "st021.xlsx");

        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .body(new InputStreamResource(in));
    }
}
