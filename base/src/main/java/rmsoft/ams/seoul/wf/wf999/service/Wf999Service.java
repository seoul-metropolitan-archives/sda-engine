/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.wf.wf999.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.rc.rc001.service.Rc001Service;
import rmsoft.ams.seoul.rc.rc002.service.Rc002Service;
import rmsoft.ams.seoul.rc.rc002.vo.Rc00201VO;
import rmsoft.ams.seoul.rc.rc002.vo.Rc002VO;
import rmsoft.ams.seoul.rc.rc005.vo.Rc00501VO;
import rmsoft.ams.seoul.rc.rc005.vo.Rc00502VO;
import rmsoft.ams.seoul.utils.ArchiveUtils;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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

    @Autowired
    private Rc002Service rc002Service;

    @Autowired
    private Rc001Service rc001Service;

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
            try {
                ArchiveUtils.extract(uploadPath + File.separator + rc00502VO.getFilePath() + File.separator + rc00502VO.getOriginalFileName(), contentsPath + File.separator + getFileNameNoExt(rc00502VO.getOriginalFileName()), "");

                // 최상위 Aggregation 생성
                String rootAggregationUUID = UUIDUtils.getUUID();

                Rc002VO rc002VO = new Rc002VO();
                Rc00201VO rc00201VO = new Rc00201VO();
                rc00201VO.setAggregationUuid(rootAggregationUUID);
                rc00201VO.setTitle(getFileNameNoExt(rc00502VO.getFileName()));

                rc002VO.setSystemMeta(rc00201VO);
                rc002Service.saveIngestAggregation(rc002VO);

                // 하위 폴더 탐색하면서 집합체/아이템/컴포넌트 정보 생성
                saveArchiveIngest(rootAggregationUUID, contentsPath + File.separator + getFileNameNoExt(rc00502VO.getOriginalFileName()));
            } catch (Exception e) {
                e.printStackTrace();

                return ApiResponse.of(ApiStatus.SYSTEM_ERROR, "아카이브 파일 해제중 에러가 발생하였습니다. 관리자에게 문의하세요");
            }
        } else {
            // 일반 파일인 경우
        }


        // DB에 저장한다.
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    private String aggregationUUID;

    @Transactional
    public void saveArchiveIngest(String parentAggregationUUID, String unzippedFolderPath) {
        try {
            Files.newDirectoryStream(Paths.get(unzippedFolderPath)).forEach(path -> {
                //File tfile = new File(path.toUri());
                if (Files.isDirectory(path)) {
                    aggregationUUID = UUIDUtils.getUUID();

                    log.info("Aggregation: " + path.getFileName());

                    Rc002VO rc002VO = new Rc002VO();
                    Rc00201VO rc00201VO = new Rc00201VO();
                    rc00201VO.setAggregationUuid(aggregationUUID);
                    rc00201VO.setParentsAggregationUuid(parentAggregationUUID);
                    rc00201VO.setTitle(path.getFileName().toString());

                    rc002VO.setSystemMeta(rc00201VO);
                    rc002Service.saveIngestAggregation(rc002VO);

                    saveArchiveIngest(aggregationUUID, path.toString());

                } else {
                    log.info("Item and Component:" + path.getFileName());

                    // item 정보생성
                    Rc00501VO rc00501VO = new Rc00501VO();
                    rc00501VO.setRaTitle(getFileNameNoExt(path.getFileName().toString()));
                    rc00501VO.setRaAggregationUuid(parentAggregationUUID);

                    // component 정보생성
                    List<Rc00502VO> componentsList = new ArrayList<>();
                    Rc00502VO rc00502VO = new Rc00502VO();
                    rc00502VO.setTitle(getFileNameNoExt(path.getFileName().toString()));

                    try{
                        rc00502VO.setContentsSize((int)Files.size(path));
                    }catch(IOException e){
                        log.error("Can not check file size. :: {}", e.getMessage());
                    }

                    rc00502VO.setFilePath(path.toString());
                    rc00502VO.setFileName(path.getFileName().toString());
                    rc00502VO.setOriginalFileName(path.getFileName().toString());
                    componentsList.add(rc00502VO);
                    rc00501VO.setRc00502VoList(componentsList);

                    // component save
                    rc001Service.creItemAndCreComponent(rc00501VO);
                }

            });
        } catch (Exception e) {
            log.error("Aggregation, Item , Component 정보 생성중 에러가 발생하였습니다. :: {}", e.getMessage());
        }
    }

    private String getFileNameNoExt(String fullFileName) {
        if (StringUtils.isNotEmpty(fullFileName)) {
            return fullFileName.substring(0, fullFileName.lastIndexOf("."));
        }

        return "";
    }
}