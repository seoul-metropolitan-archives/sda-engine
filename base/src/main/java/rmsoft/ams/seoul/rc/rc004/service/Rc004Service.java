package rmsoft.ams.seoul.rc.rc004.service;

import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.SessionUtils;
import io.onsemiro.utils.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.common.domain.*;
import rmsoft.ams.seoul.common.repository.*;
import rmsoft.ams.seoul.rc.rc004.dao.Rc004Mapper;
import rmsoft.ams.seoul.rc.rc004.vo.Rc00402VO;
import rmsoft.ams.seoul.rc.rc005.vo.Rc00501VO;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * The type Rc 004 service.
 */
@Service
public class Rc004Service extends BaseService{
    @Inject
    private Rc004Mapper rc004Mapper;

    @Autowired
    private RcItemRepository rcItemRepository;

    @Autowired
    private RcItemConRepository rcItemConRepository;

    @Autowired
    private RcItemCreatorRepository rcItemCreatorRepository;

    @Autowired
    private RcItemMaterialRepository rcItemMaterialRepository;

    @Autowired
    private RcItemRelatedAuthorityRepository rcItemRelatedAuthorityRepository;

    @Autowired
    private RcItemRelatedRecordRepository rcItemRelatedRecordRepository;

    /**
     * Save item details.
     *
     * @param requestParams the request params
     * @return the rc 00501 vo
     */
    @Transactional
    public Rc00501VO saveItemDetails(Rc00501VO requestParams){
        RcItem rcItem = new RcItem(); //RC_ITEM
        RcItem oldRcItem = new RcItem(); //RC_ITEM
        RcItemCon rcItemCon = new RcItemCon(); //RC_ITEM_CON
        RcItemCon oldRcItemCon = new RcItemCon(); //RC_ITEM_CON
        List<RcItemCreator> creatorList = null;
        List<RcItemMaterial> materialList = null;
        List<RcItemRelatedAuthority> authorityList = null;
        List<RcItemRelatedRecord> recordList = null;

        String itemUuid = UUIDUtils.getUUID();

        creatorList = ModelMapperUtils.mapList(requestParams.getCreatorList(), RcItemCreator.class);
        materialList = ModelMapperUtils.mapList(requestParams.getMaterialList(), RcItemMaterial.class);
        authorityList = ModelMapperUtils.mapList(requestParams.getRelatedAuthorityList(), RcItemRelatedAuthority.class);
        recordList = ModelMapperUtils.mapList(requestParams.getRelatedRecordList(), RcItemRelatedRecord.class);

        //RC_ITEM  업데이트
        if(StringUtils.isEmpty(requestParams.getRiItemUuid())){
            rcItem.setItemUuid(itemUuid);
            String itemCode = jdbcTemplate.queryForObject("select fc_rc_item_code from dual", String.class);
            rcItem.setItemCode(itemCode);
        }else{
            rcItem.setItemUuid(requestParams.getRiItemUuid());
            oldRcItem = rcItemRepository.findOne(rcItem.getId());
            rcItem.setItemCode(requestParams.getRiItemCode());
            rcItem.setInsertDate(oldRcItem.getInsertDate());
            rcItem.setInsertUuid(oldRcItem.getInsertUuid());
        }

        rcItem.setUpdateDate(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));
        rcItem.setUpdateUuid(SessionUtils.getCurrentLoginUserUuid());
        rcItem.setTypeUuid(requestParams.getRiTypeUuid());
        rcItem.setPublishedStatusUuid(requestParams.getRiPublishedStatusUuid());
        rcItem.setTitle(requestParams.getName());
        rcItem.setDescription(requestParams.getDescription());
        rcItem.setNotes(requestParams.getNotes());
        rcItem.setAuthor(requestParams.getRiAuthor());
        rcItem.setAggregationUuid(requestParams.getRaAggregationUuid());
        /** 설계변경 추가 **/
        rcItem.setLanguageCode(requestParams.getLanguageCode());
        rcItem.setStatusDescription(requestParams.getStatusDescription());
        rcItem.setLevelOfDetailUuid(requestParams.getLevelOfDetailUuid());

        if(requestParams.getRiDescriptionStartDate() != null)
            rcItem.setDescriptionStartDate(requestParams.getRiDescriptionStartDate().replace("-",""));

        if(requestParams.getRiDescriptionEndDate() != null)
            rcItem.setDescriptionEndDate(requestParams.getRiDescriptionEndDate().replace("-",""));

        rcItemRepository.save(rcItem);

        //RC_ITEM  상세 업데이트
        rcItemCon =  ModelMapperUtils.map(requestParams, RcItemCon.class);
        rcItemCon.setItemUuid(rcItem.getItemUuid());
        oldRcItemCon = rcItemConRepository.findOne(rcItemCon.getId());

        if(oldRcItemCon != null){//create
            rcItemCon.setInsertDate(oldRcItemCon.getInsertDate());
            rcItemCon.setInsertUuid(oldRcItemCon.getInsertUuid());
        }

        if(requestParams.getCreationStartDate() != null) {
            rcItemCon.setCreationStartDate(requestParams.getCreationStartDate().replace("-", ""));
        }

        if(requestParams.getCreationEndDate() != null) {
            rcItemCon.setCreationEndDate(requestParams.getCreationEndDate().replace("-",""));
        }

        if(requestParams.getAccumulationStartDate() != null) {
            rcItemCon.setAccumulationStartDate(requestParams.getAccumulationStartDate().replace("-", ""));
        }

        if(requestParams.getAccumulationEndDate() != null) {
            rcItemCon.setAccumulationEndDate(requestParams.getAccumulationEndDate().replace("-",""));
        }

        if(requestParams.getExtraMetadata() != null)
            rcItemCon.setExtraMetadata(requestParams.getExtraMetadata().toString());

        rcItemConRepository.save(rcItemCon);

        if (null != creatorList) {
            RcItemCreator prevCreator = null;
            for (RcItemCreator child : creatorList) {
                if(!child.isCreated() && !child.isModified() && !child.isDeleted())
                    continue;

                if(child.isDeleted()){
                    rcItemCreatorRepository.delete(child.getId());
                    continue;
                }

                if(child.isCreated()){
                    child.setItemCreatorUuid(UUIDUtils.getUUID());
                }else if(child.isModified()){
                    prevCreator = rcItemCreatorRepository.findOne(child.getId());
                    child.setInsertDate(prevCreator.getInsertDate());
                    child.setInsertUuid(prevCreator.getInsertUuid());
                }

                child.setItemUuid(rcItem.getItemUuid());
                rcItemCreatorRepository.save(child);
            }
        }
        if (null != materialList) {
            for (RcItemMaterial child : materialList) {
                child.setItemMaterialUuid(UUIDUtils.getUUID());
                child.setItemUuid(rcItem.getItemUuid());
                rcItemMaterialRepository.save(child);
            }
        }
        if (null != authorityList) {
            RcItemRelatedAuthority prevAuthority = null;
            for (RcItemRelatedAuthority child : authorityList) {
                if(!child.isCreated() && !child.isModified() && !child.isDeleted())
                    continue;

                if(child.isDeleted()){
                    rcItemRelatedAuthorityRepository.delete(child.getId());
                    continue;
                }

                if(child.isCreated()){
                    child.setItemRelatedAuthorityUuid(UUIDUtils.getUUID());
                }else if(child.isModified()){
                    prevAuthority = rcItemRelatedAuthorityRepository.findOne(child.getId());
                    child.setInsertDate(prevAuthority.getInsertDate());
                    child.setInsertUuid(prevAuthority.getInsertUuid());
                }

                child.setItemUuid(rcItem.getItemUuid());
                rcItemRelatedAuthorityRepository.save(child);
            }
        }
        if (null != recordList) {
            for (RcItemRelatedRecord child : recordList) {
                child.setItemRelatedRecordUuid(UUIDUtils.getUUID());
                child.setItemUuid(rcItem.getItemUuid());
                rcItemRelatedRecordRepository.save(child);
            }
        }

        requestParams.setRiItemUuid(rcItem.getItemUuid());
        return requestParams;
    }

    /**
     * Save component list.
     *
     * @param list the list
     */
    @Transactional
    public void saveComponentList(List<Rc00402VO> list){
        List<RcComponent> rcComponentList = ModelMapperUtils.mapList(list, RcComponent.class);
        int idx = 0;

        for(Rc00402VO rc00402VO : list){
            if(rcComponentList.get(idx).isModified()){
                rc00402VO.setUpdateUuid(SessionUtils.getCurrentLoginUserUuid());
                rc00402VO.setUpdateDate(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));
                rc004Mapper.saveComponentList(rc00402VO);
            }
            idx++;
        }
    }
}
