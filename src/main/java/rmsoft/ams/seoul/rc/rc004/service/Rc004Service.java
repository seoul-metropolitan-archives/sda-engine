package rmsoft.ams.seoul.rc.rc004.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.SessionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.common.domain.RcComponent;
import rmsoft.ams.seoul.common.domain.RcItem;
import rmsoft.ams.seoul.common.domain.RcItemCon;
import rmsoft.ams.seoul.common.repository.RcItemConRepository;
import rmsoft.ams.seoul.common.repository.RcItemRepository;
import rmsoft.ams.seoul.rc.rc004.dao.Rc004Mapper;
import rmsoft.ams.seoul.rc.rc004.vo.Rc00402VO;
import rmsoft.ams.seoul.rc.rc005.vo.Rc00501VO;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class Rc004Service extends BaseService{
    @Inject
    private Rc004Mapper rc004Mapper;

    @Autowired
    private RcItemRepository rcItemRepository;

    @Autowired
    private RcItemConRepository rcItemConRepository;

    @Transactional
    public void saveItemDetails(RequestParams<Rc00501VO> requestParams){
        RcItem rcItem = new RcItem(); //RC_ITEM
        RcItemCon rcItemCon = new RcItemCon(); //RC_ITEM_CON

        //RC_ITEM  업데이트
        if(StringUtils.isEmpty(requestParams.getString("itemUuid"))){
            return;
        }
        rcItem.setItemUuid(requestParams.getString("itemUuid"));
        rcItem = rcItemRepository.findOne(rcItem.getId());

        rcItem.setTypeUuid(requestParams.getString("typeUuid"));
        rcItem.setPublishedStatusUuid(requestParams.getString("publishedStatusUuid"));
        rcItem.setTitle(requestParams.getString("title"));
        rcItem.setDescription(requestParams.getString("description"));
        rcItem.setNotes(requestParams.getString("notes"));
        rcItem.setAuthor(requestParams.getString("author"));
//        rcItem.setAggregationUuid(requestParams.getString("rcAggregationCode"));
        rcItem.setDescriptionStartDate(requestParams.getString("descriptionStartDate"));
        rcItem.setDescriptionEndDate(requestParams.getString("descriptionEndDate"));
        rcItemRepository.save(rcItem);

        //RC_ITEM  상세 업데이트
        rcItemCon.setItemUuid(requestParams.getString("itemUuid"));
        rcItemCon = rcItemConRepository.findOne(rcItemCon.getId());

        if(rcItemCon == null){
            rcItemCon.setItemUuid(requestParams.getString("itemUuid"));
        }
        rcItemCon.setItemUuid(requestParams.getString("itemUuid"));
        rcItemCon.setOpenStatusUuid(requestParams.getString("openStatusUuid"));
        rcItemCon.setProvenance(requestParams.getString("provenance"));
        rcItemCon.setReferenceCode(requestParams.getString("referenceCode"));
        rcItemCon.setCreator(requestParams.getString("creator"));
        rcItemCon.setCreationEndDate(requestParams.getString("creationStartDate"));
        rcItemCon.setCreationStartDate(requestParams.getString("creationStartDate"));
        rcItemCon.setKeyword(requestParams.getString("keyword"));
        rcItemConRepository.save(rcItemCon);

    }

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
