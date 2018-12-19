/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.wf.wf999.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.JsonUtils;
import io.onsemiro.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.rc.rc001.service.Rc001Service;
import rmsoft.ams.seoul.rc.rc002.service.Rc002Service;
import rmsoft.ams.seoul.rc.rc002.vo.Rc00201VO;
import rmsoft.ams.seoul.rc.rc002.vo.Rc00202VO;
import rmsoft.ams.seoul.rc.rc002.vo.Rc002VO;
import rmsoft.ams.seoul.rc.rc005.vo.Rc00501VO;
import rmsoft.ams.seoul.rc.rc005.vo.Rc00502VO;
import rmsoft.ams.seoul.utils.ArchiveUtils;
import rmsoft.ams.seoul.wf.wf999.dao.Wf999Mapper;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Value("${repository.streamDoc}")
    private String servicePath;

    @Autowired
    private Rc001Service rc001Service;

    @Autowired
    private Rc002Service rc002Service;

    @Inject
    private Wf999Mapper wf999Mapper;

    /**
     * Extract archive api response.
     *
     * @param rc00501VO the rc 00502 vo list
     * @return the api response
     */
    @Transactional
    public ApiResponse extractArchive(Rc00501VO rc00501VO) {
        List<Rc00502VO> rc00502VOList = rc00501VO.getRc00502VoList();

        if (rc00501VO == null || rc00502VOList == null || rc00502VOList.size() == 0) {
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
                rc00201VO.setParentAggregationUuid(rc00501VO.getRaAggregationUuid());
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

            // item 정보생성
            rc00502VOList.forEach(fileInfo -> {
                Rc00501VO itemVO = new Rc00501VO();
                //2018-12-18 신영현
                //파일확장자 얻기

                itemVO.setRaTitle(getFileNameNoExt(fileInfo.getFileName()));
                itemVO.setRaAggregationUuid(rc00501VO.getRaAggregationUuid());


                // component 정보생성
                File file = new File(uploadPath + File.separator + fileInfo.getFilePath() + File.separator + fileInfo.getOriginalFileName());
                File fileToMove = new File(contentsPath + File.separator + fileInfo.getFilePath());
                try {
                    FileUtils.moveToDirectory(file, fileToMove, true);
                }catch (IOException e){
                    e.printStackTrace();
                }

                List<Rc00502VO> componentsList = new ArrayList<>();
                fileInfo.setTitle(getFileNameNoExt(fileInfo.getFileName().toString()));
                fileInfo.setFilePath(fileToMove.getAbsolutePath().replace(contentsPath, ""));
//                fileInfo.setServiceFilePath("\\service");
                fileInfo.setServiceFilePath(servicePath);
                if(isWindows()){
                    fileInfo.setFilePath(fileInfo.getFilePath().replace(File.separator, "/"));
                    fileInfo.setServiceFilePath(fileInfo.getServiceFilePath().replace(File.separator, "/"));
                }
                fileInfo.setServiceFileName(fileInfo.getOriginalFileName().substring(0, fileInfo.getOriginalFileName().lastIndexOf( "." )) + ".pdf");
                componentsList.add(fileInfo);
                itemVO.setRc00502VoList(componentsList);

                // component save
                rc001Service.creItemAndCreComponent(itemVO);
            });
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
                    rc00201VO.setParentAggregationUuid(parentAggregationUUID);
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

                    rc00502VO.setFilePath(path.getParent().toString().replace(contentsPath, ""));
                    rc00502VO.setServiceFilePath(servicePath);
                    if(isWindows()){
                        rc00502VO.setFilePath(rc00502VO.getFilePath().replace(File.separator, "/"));
                        rc00502VO.setServiceFilePath(rc00502VO.getServiceFilePath().replace(File.separator, "/"));
                    }
                    rc00502VO.setServiceFileName(rc00502VO.getOriginalFileName().substring(0, rc00502VO.getOriginalFileName().lastIndexOf( "." )) + ".pdf");
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

    /**
     * 표준RMS Ingest Workflow by Excel
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse workflowIngestExcel(String rootFilePath) {
        ArrayList<Map> aggList = wf999Mapper.findAllAggregationInf();
        ArrayList<Map> itemList = wf999Mapper.findAllItemInf();
        ArrayList<Map> compList = wf999Mapper.findAllComponentInf();

        Map aggHeader = aggList.get(0);
        Map itemHeader = itemList.get(0);
        Map compHeader = compList.get(0);

        aggList.remove(0);
        itemList.remove(0);
        compList.remove(0);

        try {
            // 최상위 Aggregation 생성
            String rootAggregationUUID = UUIDUtils.getUUID();

            Rc002VO rc002VO = new Rc002VO();
            Rc00201VO rc00201VO = new Rc00201VO();
            rc00201VO.setAggregationUuid(rootAggregationUUID);
            rc00201VO.setParentAggregationUuid("");
            rc00201VO.setTitle("표준RMS_" + DateUtils.getNow(DateUtils.DATE_PATTERN));

            rc002VO.setSystemMeta(rc00201VO);
            rc002Service.saveIngestAggregation(rc002VO);

            int index = 0;

            for(Map aggregation : aggList){
                rc002VO = new Rc002VO();
                rc00201VO = new Rc00201VO();

                String aggregationUUID = UUIDUtils.getUUID();
                rc00201VO.setAggregationUuid(aggregationUUID);
                rc00201VO.setParentAggregationUuid(rootAggregationUUID);
                rc00201VO.setTitle(aggregation.get("TITLE").toString());

                Rc00202VO rc00202VO = new Rc00202VO();
                rc00202VO.setAggregationUuid(aggregationUUID);
                rc00202VO.setExtraMetadata(generateExtraMeta(aggHeader, aggregation, "aggregation"));     //JSON 추출

                rc002VO.setSystemMeta(rc00201VO);
                rc002VO.setContextualMeta(rc00202VO);

                rc002Service.saveIngestAggregation(rc002VO);

                //if(index != 0) continue;

                for(int itemIdx=0; itemList.size()>itemIdx; itemIdx++){
                    Map item = itemList.get(itemIdx);
                    if(item.get("FOLDER_ID").equals(aggregation.get("FOLDER_ID"))){
                        // item 정보생성
                        Rc00501VO rc00501VO = new Rc00501VO();
                        rc00501VO.setRaTitle(item.get("TITLE").toString());
                        rc00501VO.setRaAggregationUuid(aggregationUUID);
                        rc00501VO.setExtraMetadata(generateExtraMeta(itemHeader, item, "item"));     //JSON 추출

                        // component 정보생성
                        List<Rc00502VO> componentsList = new ArrayList<Rc00502VO>();
                        for(int compIdx=0; compList.size()>compIdx; compIdx++){
                            Map component = compList.get(compIdx);
                            if(item.get("RECORD_ID").equals(component.get("RECORD_ID"))) {
                                Rc00502VO rc00502VO = new Rc00502VO();
                                rc00502VO.setTitle(getFileNameNoExt(component.get("FILE_TITLE").toString()));
                                rc00502VO.setContentsSize(Integer.parseInt(component.get("FILE_SIZE").toString()));
                                rc00502VO.setFilePath(rootFilePath + "/");
                                rc00502VO.setFileName(component.get("FILE_NAME").toString());
                                rc00502VO.setOriginalFileName(component.get("FILE_NAME").toString());
                                componentsList.add(rc00502VO);
                                compList.remove(component);
                                compIdx--;
                            }
                        }

                        rc00501VO.setRc00502VoList(componentsList);
                        itemList.remove(item);
                        itemIdx--;

                        // item/component save
                        rc001Service.creItemAndCreComponent(rc00501VO);
                    }
                }

                index++;
            }
        } catch (Exception e) {
            e.printStackTrace();

            return ApiResponse.of(ApiStatus.SYSTEM_ERROR, "아카이브 파일 해제중 에러가 발생하였습니다. 관리자에게 문의하세요");
        }

        // DB에 저장한다.
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    /**
     * File 확장자를 제외한 File명 추출
     * @param fullFileName
     * @return String
     */
    private String getFileNameNoExt(String fullFileName) {
        if (StringUtils.isNotEmpty(fullFileName)) {
            return fullFileName.substring(0, fullFileName.lastIndexOf("."));
        }

        return "";
    }

    /**
     * 서버OS의 Window 여부 리턴
     * @return Boolean
     */
    private boolean isWindows() {
        String os = System.getProperty("os.name").toLowerCase();
        return (os.indexOf("win") >= 0);
    }

    /**
     * Extra Metadata JSON Generator
     * @param header
     * @param item
     * @param type
     * @return
     */
    private String generateExtraMeta(Map<String, String> header, Map<String, String> item, String type){
        List<Map> metaList = new ArrayList<>();

        for (Object key : header.keySet()) {
            Map<String, String> metaMap = new HashMap<>();

            //값이 없을 때는 지나간다.
            //if(StringUtils.isEmpty(item.get(key)))
            //    continue;

            metaMap.put("fieldName", key.toString());
            metaMap.put("title", header.get(key));
            metaMap.put("value", item.get(key) == null ? "" : item.get(key));

            metaList.add(metaMap);
        }

        String rtn = JsonUtils.toJson(metaList);

        return rtn;
    }
}