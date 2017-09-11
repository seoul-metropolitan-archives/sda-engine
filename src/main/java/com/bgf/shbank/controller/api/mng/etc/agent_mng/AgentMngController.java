package com.bgf.shbank.controller.api.mng.etc.agent_mng;

import com.bgf.shbank.controller.view.ExcelView;
import com.bgf.shbank.core.upload.AX5File;
import com.bgf.shbank.core.upload.FileUploadService;
import com.bgf.shbank.domain.mng.etc.agent_mng.AgentMng;
import com.bgf.shbank.domain.mng.etc.agent_mng.AgentMngService;
import com.bgf.shbank.domain.mng.etc.agent_mng.AgentMngVO;
import com.bgf.shbank.utils.DateUtils;
import com.bgf.shbank.utils.ModelMapperUtils;
import com.google.common.collect.Maps;
import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/mng/etc/agent_mng")
public class AgentMngController extends BaseController {

    @Inject
    private AgentMngService agentMngService;

    @Inject
    private FileUploadService fileUploadService;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<AgentMngVO> requestParams) {
        Page<AgentMng> pages = agentMngService.find(pageable, requestParams);
        return Responses.PageResponse.of(AgentMngVO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody AgentMngVO vo) {
        AgentMng agentMng = ModelMapperUtils.map(vo, AgentMng.class);

        if(StringUtils.isEmpty(agentMng.getTxId())){
            agentMng.setTxId(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));
        }

        agentMngService.save(agentMng);
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

    @GetMapping("/download/excel")
    public View download(RequestParams<AgentMngVO> requestParams, Model model) {

        List<AgentMng> resultList = agentMngService.find(requestParams);

        List<AgentMngVO> voList = AgentMngVO.of(resultList);

        Map<String, Object> params = Maps.newHashMap();
        params.put("vo", voList);

        model.addAttribute("vo", voList);
        model.addAttribute("txId", "agent_mng");
        model.addAttribute("fileName", "요원정보관리-" + DateUtils.getNow("yyyyMMdd_HHmmss"));

        ExcelView view = new ExcelView();
        return view;
    }
}