/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.wf.wf999.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.common.domain.RcComponent;
import rmsoft.ams.seoul.common.domain.WfParameter;
import rmsoft.ams.seoul.common.domain.WfWorkflow;
import rmsoft.ams.seoul.common.domain.WfWorkflowJob;
import rmsoft.ams.seoul.common.repository.WfParameterRepository;
import rmsoft.ams.seoul.common.repository.WfWorkflowJobRepository;
import rmsoft.ams.seoul.common.repository.WfWorkflowRepository;
import rmsoft.ams.seoul.rc.rc005.vo.Rc00502VO;
import rmsoft.ams.seoul.utils.ArchiveUtils;
import rmsoft.ams.seoul.wf.wf999.dao.Wf999Mapper;
import rmsoft.ams.seoul.wf.wf999.vo.Wf99901VO;
import rmsoft.ams.seoul.wf.wf999.vo.Wf99902VO;
import rmsoft.ams.seoul.wf.wf999.vo.Wf99903VO;

import javax.inject.Inject;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * The type Wf 999 service.
 */
@Slf4j
@Service
public class Wf999Service extends BaseService {
    @Value("${repository.upload}")
    private String uploadPath;

    @Value("${repository.contents}")
    private String contentsPath;

    @Inject
    private Wf999Mapper Wf999Mapper;


/*public Page<Wf99901VO> findAllWorkflowResult(Pageable pageable, RequestParams<Wf99901VO> requestParams) {

        Wf99901VO Wf99901VO = new Wf99901VO();
        Wf99901VO.setServiceUuid(requestParams.getString("serviceUuid"));
        Wf99901VO.setStatusUuid(requestParams.getString("statusUuid"));
        Wf99901VO.setBatchId(requestParams.getString("batchId"));
        Wf99901VO.setWorkflowName(requestParams.getString("workflowName"));
        Wf99901VO.setExecuter(requestParams.getString("executer"));
        Wf99901VO.setMenu(requestParams.getString("menu"));
        Wf99901VO.setStartFromDate(requestParams.getString("startFromDate"));
        Wf99901VO.setStartToDate(requestParams.getString("startToDate"));
        Wf99901VO.setEndFromDate(requestParams.getString("endFromDate"));
        Wf99901VO.setEndToDate(requestParams.getString("endToDate"));


        return filter(Wf999Mapper.findAllWorkflowResult(Wf99901VO), pageable, "", Wf99901VO.class);
    }*/


    /**
     * Extract archive api response.
     *
     * @param rc00502VOList the rc 00502 vo list
     * @return the api response
     */
    @Transactional
    public ApiResponse extractArchive(List<Rc00502VO> rc00502VOList) {

        if (rc00502VOList == null || rc00502VOList.size() == 0) {
            return ApiResponse.of(ApiStatus.SYSTEM_ERROR, "아카이브할 대상 파일에 대한 정보가 없습니다.");
        }
        // 파일이 업로드 되면 일단 파일 확장자 여부에 따라서 압축을 풀지 말지 결정한다.
        Rc00502VO rc00502VO = rc00502VOList.get(0);
        if (rc00502VO.getFileFormatUuid().equals("zip")) {
            // 압축파일인 경우
            // Extrace Zip File
            ArchiveUtils.extract(uploadPath + File.separator + rc00502VO.getFilePath() + File.separator + rc00502VO.getOriginalFileName(), contentsPath + File.separator + getFileName(rc00502VO.getOriginalFileName()), "");

            try {
                Files.newDirectoryStream(Paths.get(contentsPath + File.separator + getFileName(rc00502VO.getOriginalFileName()))).forEach(path -> {
                    //File tfile = new File(path.toUri());
                    if (Files.isDirectory(path)) {
                        log.info("Aggregation: " + path.getFileName());
                        ArchiveUtils.getEntrySet(path.toString());
                    } else {
                        log.info("Item and Component:" + path.getFileName());
                    }

                });

                //Files.walk(Paths.get(unzippedFolderPath)).filter(Files::isRegularFile).forEach(System.out::println);

           /* Files.walk(Paths.get(unzippedFolderPath)).forEach(path -> {
                if (Files.isDirectory(path)) {
                    log.info("Aggregation: " + path.getFileName());
                    ZipFileTest.getEntrySet(path.toString());
                } else {
                    log.info("Item and Component:" + path.getFileName());
                }
            });*/

            } catch (Exception e) {
                e.printStackTrace();
                return ApiResponse.of(ApiStatus.SYSTEM_ERROR, "아카이브 파일 해제중 에러가 발생하였습니다. 관리자에게 문의하세요");
            }
        } else {
            // 일반 파일인 경우
        }

        // 어쩃든 대상 파일을 upload 폴더가 아닌 contents 폴더로 이동시킨다.

        // DB에 저장한다.
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    private String getFileName(String fullFileName) {
        if (StringUtils.isNotEmpty(fullFileName)) {
            return fullFileName.substring(0, fullFileName.lastIndexOf("."));
        }

        return "";
    }
}