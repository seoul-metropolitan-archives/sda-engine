package rmsoft.ams.seoul.rc.rc005.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.common.domain.JobConv;
import rmsoft.ams.seoul.common.repository.JobConvRepository;
import rmsoft.ams.seoul.rc.rc005.dao.Rc005Mapper;
import rmsoft.ams.seoul.rc.rc005.vo.Rc00501VO;
import rmsoft.ams.seoul.rc.rc005.vo.Rc00502VO;
import rmsoft.ams.seoul.rc.rc005.vo.Rc00507VO;

import javax.inject.Inject;
import javax.jdo.annotations.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The type Rc 005 service.
 */
@Service
public class Rc005Service extends BaseService{
    @Inject
    private Rc005Mapper rc005Mapper;

    @Autowired
    private JobConvRepository jobConvRepository;

    @Value("${repository.upload}")
    private String path;
    /**
     * Get record item list page.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the page
     */
    public Page<Rc00501VO> getRecordItemList(Pageable pageable, RequestParams<Rc00501VO> requestParams){
        Rc00501VO rc00501VO = new Rc00501VO();
        Rc00502VO rc00502VO;
        rc00501VO.setRiAggregationUuid(requestParams.getString("aggregationUuid"));
        rc00501VO.setRiItemUuid(requestParams.getString("itemUuid"));

        List<Rc00501VO>  rc00501VOList =  rc005Mapper.getRecordItemList(rc00501VO);
        for (Rc00501VO rc00501VO1 : rc00501VOList){
            if(StringUtils.isNotEmpty(rc00501VO1.getRiItemUuid())) {
                if (StringUtils.isNotEmpty(rc00501VO1.getRiAggregationUuid())) {
                    rc00502VO = new Rc00502VO();
                    rc00502VO.setItemUuid(rc00501VO1.getRiItemUuid());
                    rc00501VO1.setRc00502VoList(rc005Mapper.getRecordComponentList(rc00502VO));
                    rc00501VO1.setCreatorList(rc005Mapper.getCreatorList(requestParams.getString("itemUuid")));
                    rc00501VO1.setRelatedAuthorityList(rc005Mapper.getRelatedAuthorityList(requestParams.getString("itemUuid")));
                }
            }
        }
        return filter(rc00501VOList, pageable, "", Rc00501VO.class);
    }

    @Transactional
    public ApiResponse mergeComponent(List<Rc00502VO> mergeList) {

        JobConv jobConv = new JobConv();
        String uuid = UUIDUtils.getUUID();
        Rc00507VO rc00507VO;
        int cnt = 0;


        jobConv.setJobid(uuid);
        jobConv.setSrcfile("sftp://"+ mergeList.get(0).getFilePath() + "/" + mergeList.get(0).getServiceFileName());
        jobConv.setDestfile("sftp:///merge/" +uuid + ".pdf");
        jobConv.setExtrajobs("MG");
        jobConv.setReqdate(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(),DateUtils.DATE_TIME_PATTERN)));
        jobConvRepository.save(jobConv);


        for(Rc00502VO rc00502VO : mergeList){
            rc00507VO = new Rc00507VO();
            rc00507VO.setJobid(uuid);
            rc00507VO.setMergefile("sftp://" + rc00502VO.getFilePath() + "/" + rc00502VO.getServiceFileName());
            rc00507VO.setPage("1-");
            rc00507VO.setSeq(cnt+1);
            rc005Mapper.mergeInsert(rc00507VO);
            cnt++;
        }

        Timer mergeCheckTimer = new Timer();
        TimerTask mergeCheck = new TimerTask() {
            @Override
            public synchronized void run() {
                JobConv orgJobConv = jobConvRepository.findOne(jobConv.getId());
                switch (orgJobConv.getJobstatus()){
                    case "S" :
                        mergeCheckTimer.cancel();
                        break;
                    case "F" :
                        mergeCheckTimer.cancel();
                        break;
                }
            }
        };


        mergeCheckTimer.scheduleAtFixedRate(mergeCheck, 2000, 2000);

        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }


}
