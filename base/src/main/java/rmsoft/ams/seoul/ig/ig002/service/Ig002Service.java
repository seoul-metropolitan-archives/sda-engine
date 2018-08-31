package rmsoft.ams.seoul.ig.ig002.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;

import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.UUIDUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.common.domain.*;

import rmsoft.ams.seoul.common.repository.IgAccessionRecordDetailRepository;
import rmsoft.ams.seoul.common.repository.IgAccessionRecordRepository;
import rmsoft.ams.seoul.ig.ig002.dao.Ig002Mapper;
import rmsoft.ams.seoul.ig.ig002.vo.Ig00201VO;
import rmsoft.ams.seoul.ig.ig002.vo.Ig00202VO;
import rmsoft.ams.seoul.ig.ig002.vo.Ig002VO;


import javax.inject.Inject;
import java.util.List;


@Service
public class Ig002Service extends BaseService {
    @Inject
    private Ig002Mapper ig002Mapper;

    @Autowired
    private IgAccessionRecordRepository igAccessionRecordRepository;

    @Autowired
    private IgAccessionRecordDetailRepository igAccessionRecordDetailRepository;

    public Ig00201VO getIgAccessionNo() {
        return ig002Mapper.getIgAccessionNo();
    }
    @Transactional
    public Ig002VO getIgAccessionRecord(RequestParams<Ig00201VO> requestParams) {
        Ig002VO ig002VO = new Ig002VO();
        Ig00201VO ig00201VO = new Ig00201VO();

        ig00201VO.setAccessionRecordUuid(requestParams.getString("accessionRecordUuid"));
        ig002VO.setAccessionRecord(ig002Mapper.getIgAccessionRecord(ig00201VO));
        List<Ig00202VO> drnList = ig002Mapper.getChildrenDrnInfoList(ig00201VO);
        List<Ig00202VO> mngList = ig002Mapper.getChildrenMngInfoList(ig00201VO);

        ig002VO.setChildrenDrnInfoList(drnList);
        ig002VO.setChildrenMngInfoList(mngList);

        return ig002VO;
    }
    @Transactional
    public ApiResponse save(Ig002VO data) {

        String uuid = "";
        boolean isCreate = false;
        IgAccessionRecord igAccessionRecord = null;
        IgAccessionRecordDetail igAccessionRecordDetail = null;

        List<IgAccessionRecordDetail> childrenDrnInfoList = null;
        List<IgAccessionRecordDetail> childrenMngInfoList = null;


        igAccessionRecord = ModelMapperUtils.map(data.getAccessionRecord(),IgAccessionRecord.class);
        childrenDrnInfoList = ModelMapperUtils.mapList(data.getChildrenDrnInfoList(),IgAccessionRecordDetail.class);
        childrenMngInfoList = ModelMapperUtils.mapList(data.getChildrenMngInfoList(),IgAccessionRecordDetail.class);

        String acquisitionDate = igAccessionRecord.getAcquisitionDate().replace("-","");

        if(null == igAccessionRecord.getAccessionRecordUuid() || igAccessionRecord.getAccessionRecordUuid().equals("") ){
            uuid = UUIDUtils.getUUID();
            igAccessionRecord.setAccessionRecordUuid(uuid);
            String accessionNo = jdbcTemplate.queryForObject("SELECT 'ARR'||'-'||TO_CHAR(sysdate,'yyyymmdd')||'-'||NVL(LPAD(TO_NUMBER(SUBSTR(MAX(ACCESSION_NO),14)) + 1,3,'0'),'001') as accessionNo FROM IG_ACCESSION_RECORD WHERE ACCESSION_NO LIKE '%' || TO_CHAR(sysdate,'yyyymmdd') || '%'",String.class);
            igAccessionRecord.setAccessionNo(accessionNo);
            igAccessionRecord.setAcquisitionDate(acquisitionDate);
            igAccessionRecord.set__created__(true);
            isCreate = true;
            igAccessionRecordRepository.save(igAccessionRecord);

        }else{
            uuid = igAccessionRecord.getAccessionRecordUuid();
            if(igAccessionRecord.isModified()){
                IgAccessionRecord before = igAccessionRecordRepository.findOne(igAccessionRecord.getId());
                igAccessionRecord.setInsertDate(before.getInsertDate());
                igAccessionRecord.setInsertUuid(before.getInsertUuid());
                igAccessionRecord.setAcquisitionDate(acquisitionDate);
                igAccessionRecordRepository.save(igAccessionRecord);
            }
        }
        if(isCreate){
            if(null != childrenDrnInfoList){
                for(IgAccessionRecordDetail child : childrenDrnInfoList)
                {
                    child.setAccessionRecordEtcUuid(UUIDUtils.getUUID());
                    child.setAccessionRecordUuid(uuid);
                    child.setAuthorityUuid("drn");
                    igAccessionRecordDetailRepository.save(child);
                }
            }
            if(null != childrenMngInfoList){
                for(IgAccessionRecordDetail child : childrenMngInfoList)
                {
                    child.setAccessionRecordEtcUuid(UUIDUtils.getUUID());
                    child.setAccessionRecordUuid(uuid);
                    child.setAuthorityUuid("mng");
                    igAccessionRecordDetailRepository.save(child);
                }
            }
        }else{
            if(null != childrenDrnInfoList){
                IgAccessionRecordDetail before = null;
                for(IgAccessionRecordDetail child : childrenMngInfoList)
                {
                    if(child.isDeleted()){
                        igAccessionRecordDetailRepository.delete(child.getId());
                    }else if(child.isCreated()||child.isModified()){
                        if(child.isCreated()){
                            child.setAccessionRecordEtcUuid(UUIDUtils.getUUID());
                            child.setAccessionRecordUuid(uuid);
                            child.setAuthorityUuid("drn");
                        }else if(child.isModified()){
                            before = igAccessionRecordDetailRepository.findOne(child.getId());
                            child.setAuthorityUuid("drn");
                            child.setInsertDate(before.getInsertDate());
                            child.setInsertUuid(before.getInsertUuid());
                        }
                        igAccessionRecordDetailRepository.save(child);
                    }
                }
            }
            if(null != childrenMngInfoList){
                IgAccessionRecordDetail before = null;
                for(IgAccessionRecordDetail child : childrenMngInfoList)
                {
                    if(child.isDeleted()){
                        igAccessionRecordDetailRepository.delete(child.getId());
                    }else if(child.isCreated()||child.isModified()){
                        if(child.isCreated()){
                            child.setAccessionRecordEtcUuid(UUIDUtils.getUUID());
                            child.setAccessionRecordUuid(uuid);
                            child.setAuthorityUuid("mng");
                        }else if(child.isModified()){
                            before = igAccessionRecordDetailRepository.findOne(child.getId());
                            child.setAuthorityUuid("mng");
                            child.setInsertDate(before.getInsertDate());
                            child.setInsertUuid(before.getInsertUuid());
                        }
                        igAccessionRecordDetailRepository.save(child);
                    }
                }
            }
        }
        return ApiResponse.of(ApiStatus.SUCCESS,"SUCCESS");
    }
}