package rmsoft.ams.seoul.rc.rc002.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.SessionUtils;
import io.onsemiro.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.common.domain.*;
import rmsoft.ams.seoul.common.repository.*;
import rmsoft.ams.seoul.rc.rc002.dao.Rc002Mapper;
import rmsoft.ams.seoul.rc.rc002.vo.Rc00204VO;
import rmsoft.ams.seoul.rc.rc002.vo.Rc00205VO;
import rmsoft.ams.seoul.rc.rc002.vo.Rc002VO;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Rc 002 service.
 */
@Service("Rc002Service")
public class Rc002Service extends BaseService {

    @Autowired
    private RcLevelRepository rcLevelRepository;

    @Autowired
    private RcAggregationRepository rcAggregationRepository;

    @Autowired
    private RcAggregationConRepository rcAggregationConRepository;

    @Autowired
    private RcRecordReferenceRepository rcRecordReferenceRepository;

    @Autowired
    private RcAggregationCreatorRepository rcAggregationCreatorRepository;

    @Autowired
    private RcAggregationMaterialRepository rcAggregationMaterialRepository;

    @Autowired
    private RcAggrRelatedAuthorityRepository rcAggrRelatedAuthorityRepository;

    @Autowired
    private RcAggrRelatedRecordRepository rcAggrRelatedRecordRepository;

    @Autowired
    private Rc002Mapper rc002Mapper;

    /**
     * Get level list.
     *
     * @return the list
     */
    public List<Rc00205VO> getLevel() {
        return ModelMapperUtils.mapList(rcLevelRepository.findAll(), Rc00205VO.class);
    }

    /**
     * Save api response.
     *
     * @param data the data
     * @return the api response
     */
    public Map save(Rc002VO data) {

        String uuid = "";
        boolean isCreate = false;
        RcAggregation rcAggregation = null;
        RcAggregationCon rcAggregationCon = null;

        List<RcAggregation> childrenAggregation = null;
        List<RcRecordReference> referenceAggregation = null;
        List<RcRecordReference> referenceItem = null;
        List<RcAggregationCreator> creatorList = null;
        List<RcAggregationMaterial> materialList = null;
        List<RcAggrRelatedAuthority> authorityList = null;
        List<RcAggrRelatedRecord> recordList = null;

        rcAggregation = ModelMapperUtils.map(data.getSystemMeta(), RcAggregation.class);

        if (data.getContextualMeta() != null)
            rcAggregationCon = ModelMapperUtils.map(data.getContextualMeta(), RcAggregationCon.class);

        childrenAggregation = ModelMapperUtils.mapList(data.getChildrenAggregationList(), RcAggregation.class);
        referenceAggregation = ModelMapperUtils.mapList(data.getReferenceAggregationList(), RcRecordReference.class);
        referenceItem = ModelMapperUtils.mapList(data.getReferenceItemList(), RcRecordReference.class);
        creatorList = ModelMapperUtils.mapList(data.getCreatorList(), RcAggregationCreator.class);
        materialList = ModelMapperUtils.mapList(data.getMaterialList(), RcAggregationMaterial.class);
        authorityList = ModelMapperUtils.mapList(data.getRelatedAuthorityList(), RcAggrRelatedAuthority.class);
        recordList = ModelMapperUtils.mapList(data.getRelatedRecordList(), RcAggrRelatedRecord.class);

        try {
            if (null == rcAggregation.getAggregationUuid() || rcAggregation.getAggregationUuid().equals("")) {
                uuid = UUIDUtils.getUUID();
                rcAggregation.setAggregationUuid(uuid);
                String aggregationCode = jdbcTemplate.queryForObject("select fc_rc_aggregation_code from dual", String.class);
                rcAggregation.setAggregationCode(aggregationCode);
                String descriptionStartDate = rcAggregation.getDescriptionStartDate() == null ? null : rcAggregation.getDescriptionStartDate().replace("-", "");
                rcAggregation.setDescriptionStartDate(descriptionStartDate);
                String descriptionEndDate = rcAggregation.getDescriptionEndDate() == null ? null : rcAggregation.getDescriptionEndDate().replace("-", "");
                rcAggregation.setDescriptionEndDate(descriptionEndDate);
                rcAggregation.set__created__(true);
                isCreate = true;
                rcAggregationRepository.save(rcAggregation);
            } else {
                uuid = rcAggregation.getAggregationUuid();
                RcAggregation before = rcAggregationRepository.findOne(rcAggregation.getId());
                rcAggregation.setInsertDate(before.getInsertDate());
                rcAggregation.setInsertUuid(before.getInsertUuid());
                rcAggregation.setAggregationCode(before.getAggregationCode());
                rcAggregation.setUpdateDate(new Timestamp(System.currentTimeMillis()));
                rcAggregation.setUpdateUuid(SessionUtils.getCurrentLoginUserUuid());

                rcAggregationRepository.save(rcAggregation);
            }


            if (isCreate) {
                rcAggregationCon.setAggregationUuid(uuid);
                String creationStartDate = rcAggregationCon.getCreationStartDate() == null ? null : rcAggregationCon.getCreationStartDate().replace("-", "");
                rcAggregationCon.setCreationStartDate(creationStartDate);
                String creationEndDate = rcAggregationCon.getCreationEndDate() == null ? null : rcAggregationCon.getCreationEndDate().replace("-", "");
                rcAggregationCon.setCreationEndDate(creationEndDate);
                rcAggregationCon.set__created__(true);
                rcAggregationConRepository.save(rcAggregationCon);

                if (null != childrenAggregation) {
                    for (RcAggregation child : childrenAggregation) {
                        String aggregationCode = jdbcTemplate.queryForObject("select fc_rc_aggregation_code from dual", String.class);
                        child.setAggregationCode(aggregationCode);
                        child.setPublishedStatusUuid(rcAggregation.getPublishedStatusUuid());
                        child.setParentAggregationUuid(uuid);
                        child.setTypeUuid(rcAggregation.getTypeUuid());
                        child.set__created__(true);
                        uuid = UUIDUtils.getUUID();
                        child.setAggregationUuid(uuid);
                        rcAggregationRepository.save(child);
                    }
                }
                if (null != referenceAggregation) {
                    for (RcRecordReference child : referenceAggregation) {
                        child.setRecordReferenceUuid(UUIDUtils.getUUID());
                        child.setVirtualAggregationUuid(rcAggregation.getAggregationUuid());
                        child.set__created__(true);
                        rcRecordReferenceRepository.save(child);
                    }
                }
                if (null != referenceItem) {
                    for (RcRecordReference child : referenceItem) {
                        child.setRecordReferenceUuid(UUIDUtils.getUUID());
                        child.setVirtualAggregationUuid(rcAggregation.getAggregationUuid());
                        child.set__created__(true);
                        rcRecordReferenceRepository.save(child);
                    }
                }
            } else {
                if (rcAggregationCon != null) {
                    rcAggregationCon.setAggregationUuid(uuid);
                    RcAggregationCon beforeCon = rcAggregationConRepository.findOne(rcAggregationCon.getId());
                    if (null != beforeCon) {
                        rcAggregationCon.setInsertDate(beforeCon.getInsertDate());
                        rcAggregationCon.setInsertUuid(beforeCon.getInsertUuid());

                        rcAggregationCon.setUpdateDate(new Timestamp(System.currentTimeMillis()));
                        rcAggregationCon.setUpdateUuid(SessionUtils.getCurrentLoginUserUuid());

                        rcAggregationConRepository.save(rcAggregationCon);
                    } else {
                        rcAggregationCon.setAggregationUuid(uuid);
                        String creationStartDate = rcAggregationCon.getCreationStartDate().replace("-", "");
                        rcAggregationCon.setCreationStartDate(creationStartDate);
                        String creationEndDate = rcAggregationCon.getCreationEndDate().replace("-", "");
                        rcAggregationCon.setCreationEndDate(creationEndDate);
                        rcAggregationCon.set__created__(true);
                        rcAggregationConRepository.save(rcAggregationCon);
                    }
                }
            }

            if (null != creatorList) {
                RcAggregationCreator prevCreator = null;
                for (RcAggregationCreator child : creatorList) {
                    if (!child.isCreated() && !child.isModified() && !child.isDeleted())
                        continue;

                    if (child.isDeleted()) {
                        rcAggregationCreatorRepository.delete(child.getId());
                        continue;
                    }

                    if (child.isCreated()) {
                        child.setAggregationCreatorUuid(UUIDUtils.getUUID());
                    } else if (child.isModified()) {
                        prevCreator = rcAggregationCreatorRepository.findOne(child.getId());
                        child.setInsertDate(prevCreator.getInsertDate());
                        child.setInsertUuid(prevCreator.getInsertUuid());
                    }

                    child.setAggregationUuid(rcAggregation.getAggregationUuid());
                    rcAggregationCreatorRepository.save(child);
                }
            }
            if (null != materialList) {
                for (RcAggregationMaterial child : materialList) {
                    child.setAggregationMaterialUuid(UUIDUtils.getUUID());
                    child.setAggregationUuid(rcAggregation.getAggregationUuid());
                    rcAggregationMaterialRepository.save(child);
                }
            }
            if (null != authorityList) {
                RcAggrRelatedAuthority prevAuthority = null;
                for (RcAggrRelatedAuthority child : authorityList) {
                    if (!child.isCreated() && !child.isModified() && !child.isDeleted())
                        continue;

                    if (child.isDeleted()) {
                        rcAggrRelatedAuthorityRepository.delete(child.getId());
                        continue;
                    }

                    if (child.isCreated()) {
                        child.setAggrRelatedAuthorityUuid(UUIDUtils.getUUID());
                    } else if (child.isModified()) {
                        prevAuthority = rcAggrRelatedAuthorityRepository.findOne(child.getId());
                        child.setInsertDate(prevAuthority.getInsertDate());
                        child.setInsertUuid(prevAuthority.getInsertUuid());
                    }

                    child.setAggregationUuid(rcAggregation.getAggregationUuid());
                    rcAggrRelatedAuthorityRepository.save(child);
                }
            }
            if (null != recordList) {
                for (RcAggrRelatedRecord child : recordList) {
                    child.setAggrRelatedRecordUuid(UUIDUtils.getUUID());
                    child.setAggregationUuid(rcAggregation.getAggregationUuid());
                    rcAggrRelatedRecordRepository.save(child);
                }
            }
        }catch(Exception e){
            Map returnMap = new HashMap();
            returnMap.put("resultMessage", "FAIL");
        }

        Map returnMap = new HashMap();
        returnMap.put("resultMessage", "SUCCESS");
        returnMap.put("uuid", uuid);

        return returnMap;
    }


    /**
     * Save api response.
     *
     * @param data the data
     * @return the api response
     */
    @Transactional
    public ApiResponse saveIngestAggregation(Rc002VO data) {

        RcAggregation rcAggregation = ModelMapperUtils.map(data.getSystemMeta(), RcAggregation.class);
        String aggregationCode = jdbcTemplate.queryForObject("select fc_rc_aggregation_code from dual", String.class);
        rcAggregation.setAggregationCode(aggregationCode);
        rcAggregation.setPublishedStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD121", "Draft"));
        rcAggregation.setTypeUuid(CommonCodeUtils.getCodeDetailUuid("CD127", "Temporary"));
        rcAggregationRepository.save(rcAggregation);

        if (data.getContextualMeta() != null) {
            RcAggregationCon rcAggregationCon = ModelMapperUtils.map(data.getContextualMeta(), RcAggregationCon.class);
            rcAggregationConRepository.save(rcAggregationCon);
        }

        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    public List<Rc00204VO> getTreeData(Rc00204VO param) {
        return rc002Mapper.getTreeData(param);
    }
}
