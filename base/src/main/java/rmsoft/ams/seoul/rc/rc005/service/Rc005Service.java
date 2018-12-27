package rmsoft.ams.seoul.rc.rc005.service;

import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.common.domain.JobConv;
import rmsoft.ams.seoul.common.repository.JobConvRepository;
import rmsoft.ams.seoul.rc.rc005.dao.Rc005Mapper;
import rmsoft.ams.seoul.rc.rc005.vo.*;

import javax.inject.Inject;
import javax.jdo.annotations.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Rc 005 service.
 */
@Slf4j
@Service
public class Rc005Service extends BaseService {
    @Inject
    private Rc005Mapper rc005Mapper;

    @Autowired
    private JobConvRepository jobConvRepository;

    @Value("${repository.upload}")
    private String path;
    @Value("${streaming.url}")
    private String streamingUrl;
    @Value("${streaming.port}")
    private String streamingPort;
    @Value("${streaming.context}")
    private String streamingContext;
    @Value("${streaming.param}")
    private String streamingParam;
    @Value("${streaming.view}")
    private String streamingView;
    @Value("${repository.contents}")
    private String contentsPath;

    /**
     * Get record item list page.
     * @param requestParams the request params
     * @return the page
     */
    public List<Rc00501VO> getRecordItemList(RequestParams<Rc00501VO> requestParams) {
        Rc00501VO rc00501VO = new Rc00501VO();
        Rc00502VO rc00502VO = null;
        rc00501VO.setRiAggregationUuid(requestParams.getString("aggregationUuid"));
        rc00501VO.setRiItemUuid(requestParams.getString("itemUuid"));

        List<Rc00501VO> rc00501VOList = rc005Mapper.getRecordItemList(rc00501VO);
        for (Rc00501VO rc00501VO1 : rc00501VOList) {
            if (StringUtils.isNotEmpty(rc00501VO1.getRiItemUuid())) {
                if (StringUtils.isNotEmpty(rc00501VO1.getRiAggregationUuid())) {
                    rc00502VO = new Rc00502VO();
                    rc00502VO.setItemUuid(rc00501VO1.getRiItemUuid());
                    rc00501VO1.setRc00502VoList(rc005Mapper.getRecordComponentList(rc00502VO));
                    rc00501VO1.setCreatorList(rc005Mapper.getCreatorList(requestParams.getString("itemUuid")));
                    rc00501VO1.setRelatedAuthorityList(rc005Mapper.getRelatedAuthorityList(requestParams.getString("itemUuid")));
                }
            }
        }
        return rc00501VOList;
    }

    /**
     * @param requestParams
     * @return Rc00501VO
     */
    public Map exportItem(Map requestParams) {
        RequestParams params = new RequestParams();
        params.put("itemUuid", requestParams.get("itemUuid"));
        params.put("aggregationUuid", requestParams.get("aggregationUuid"));

        List<Rc00501VO> rc00501VOList = getRecordItemList(params);

        Rc00501VO itemVo = new Rc00501VO();

        for (Rc00501VO item : rc00501VOList) {
            if (StringUtils.isNotEmpty(item.getRiItemUuid())) {
                itemVo = item;
            }
        }

        Map itemMap = new HashMap();
        List<Map> creatorList = new ArrayList<>();
        List<Map> relatedAuthorityList = new ArrayList<>();
        List<Map> componentList = new ArrayList<>();

        itemMap.put("title",itemVo.getName());
        itemMap.put("itemUuid",itemVo.getRiItemUuid());
        itemMap.put("itemCode",itemVo.getRiItemCode());
        itemMap.put("typeName",itemVo.getRiTypeName());
        itemMap.put("publishedStatusName",itemVo.getRiPublishedStatusName());
        itemMap.put("description",itemVo.getDescription1());
        itemMap.put("author",itemVo.getRiAuthor());
        itemMap.put("aggregationCode",itemVo.getRaAggregationCode());
        itemMap.put("notes",itemVo.getNotes1());

        itemMap.put("descriptionStartDate", itemVo.getRiDescriptionStartDate());
        itemMap.put("descriptionEndDate", itemVo.getRiDescriptionEndDate());

        itemMap.put("creationStartDate", itemVo.getCreationStartDate());
        itemMap.put("creationEndDate", itemVo.getCreationEndDate());

        itemMap.put("provenance",itemVo.getProvenance());
        itemMap.put("keyword",itemVo.getKeyword());
        itemMap.put("openStatusName",itemVo.getOpenStatusName());


        /** 설계변경 추가 **/
        itemMap.put("keyword",itemVo.getKeyword());

        itemMap.put("languageCode", itemVo.getLanguageName());
        itemMap.put("statusDescription", itemVo.getStatusDescriptionName());
        itemMap.put("levelOfDetailName", itemVo.getLevelOfDetailName());
        //itemMap.put("sourceSystemName", itemVo.sourceSystemUuid);
        //itemMap.put("creationSystemUuid", itemVo.creationSystemUuid);
        //itemMap.put("addMetaTemplateSetUuid", itemVo.addMetaTemplateSetUuid);
        itemMap.put("legalStatusName", itemVo.getLegalStatusName());
        itemMap.put("repositoriesName", itemVo.getRepositoriesName());
        itemMap.put("electronicRecordStatusName", itemVo.getElectronicRecordStatusName());
        itemMap.put("accumulationStartDate", itemVo.getAccumulationStartDate());
        itemMap.put("accumulationEndDate", itemVo.getAccumulationEndDate());
        itemMap.put("scopeContent", itemVo.getScopeContent());
        itemMap.put("custodialHistory", itemVo.getCustodialHistory());
        itemMap.put("sourceAcquisitionName", itemVo.getSourceAcquisitionName());
        itemMap.put("physicalCondition", itemVo.getPhysicalCondition());
        itemMap.put("useCondition", itemVo.getUseCondition());
        itemMap.put("findingAids", itemVo.getFindingAids());
        itemMap.put("rulesConversionName", itemVo.getRulesConversionName());
        itemMap.put("accessCondition", itemVo.getAccessCondition());

        for (Rc00503VO data : itemVo.getCreatorList()) {
            Map dataMap = new HashMap();
            dataMap.put("creatorName", data.getCreatorName());
            creatorList.add(dataMap);
        }

        for (Rc00505VO data : itemVo.getRelatedAuthorityList()) {
            Map dataMap = new HashMap();
            dataMap.put("authorityName", data.getAuthorityName());
            relatedAuthorityList.add(dataMap);
        }

        for (Rc00502VO data : itemVo.getRc00502VoList()) {
            try{
                Map dataMap = null;
                dataMap = BeanUtils.describe(data);

                dataMap.remove("__deleted__");
                dataMap.remove("__created__");
                dataMap.remove("__modified__");
                dataMap.remove("deleted");
                dataMap.remove("created");
                dataMap.remove("modified");

                componentList.add(dataMap);
            }catch(Exception e){

            }
        }

        itemMap.put("creatorList", creatorList);
        itemMap.put("relatedAuthorityList", relatedAuthorityList);
        itemMap.put("componentList", componentList);

        return itemMap;
    }

    //TODO 소스 정리해야댐 (병합 테스트만 한것임);
    @Transactional
    public Object mergeComponent(List<Rc00502VO> mergeList) {

        String responseSB = "";
        HttpURLConnection conn = null;
        BufferedReader br = null;
        JobConv jobConv = new JobConv();
        String uuid = UUIDUtils.getUUID();
        Rc00507VO rc00507VO;
        Map<String, Object> response = new HashMap<String, Object>();
        int cnt = 0;


        jobConv.setJobid(uuid);
        jobConv.setSrcfile("sftp:///service/" + mergeList.get(0).getServiceFileName());
        jobConv.setDestfile("sftp:///merge/" + uuid + ".pdf");
        jobConv.setExtrajobs("MG");
        jobConv.setReqdate(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));
        jobConvRepository.save(jobConv);


        for (Rc00502VO rc00502VO : mergeList) {
            rc00507VO = new Rc00507VO();
            rc00507VO.setJobid(uuid);
            rc00507VO.setMergefile("sftp:///service/" + rc00502VO.getServiceFileName());
            rc00507VO.setPage("1-");
            rc00507VO.setSeq(cnt + 1);
            rc005Mapper.mergeInsert(rc00507VO);
            cnt++;
        }

        try {
            while (true) {
                String jobStatus = getJobStatus(jobConv.getJobid());

                if (jobStatus != null && (jobStatus.equals("S") || jobStatus.equals("F"))) {
                    if (jobStatus.equals("S")) {
                        log.info("Rc005Service mergeComponent :" + "PDF merge status is Success");
                        URL url = new URL(
                                streamingUrl + ":" + streamingPort + streamingContext + streamingParam + "seoul/merge/" + uuid + ".pdf"
                        );
                        System.out.println("Stream URL => "+url);
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setDoOutput(true);
                        conn.setRequestMethod("GET");
                        conn.setRequestProperty("Content-Type", "application/json");
                        // Write data
                        // Read response

                        try {
                            br = new BufferedReader(new InputStreamReader(
                                    conn.getInputStream()));

                            String line;
                            while ((line = br.readLine()) != null)
                                responseSB += line;

                            // Close streamsd
                        } catch (IOException e) {
                            log.error(e.getMessage());
                        } finally {
                            if (null != br)
                                br.close();
                        }

                        System.out.println("Stream Viewer response =>"+responseSB);
                        JSONObject obj = new JSONObject(responseSB);


                        response.put("url", streamingUrl + ":" + streamingPort + streamingContext + streamingView + obj.getString("streamdocsId") + ";currentPage=1");
                        System.out.println(streamingUrl+":"+streamingPort+streamingContext+streamingView+obj.getString("streamdocsId")+";currentPage=1");
                    } else {
                        log.info("Rc005Service mergeComponent :" + "PDF merge status is Failed");
                    }
                    break;
                }
                log.info("Rc005Service mergeComponent :" + "PDF merge status is not completed");
                Thread.sleep(2000);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());

        }finally
        {
            if(null != conn)
            {
                conn.disconnect();
            }

        }
        return response;
    }

    private String getJobStatus(String jobId) {
        return rc005Mapper.getJobStatus(jobId);

    }
}
