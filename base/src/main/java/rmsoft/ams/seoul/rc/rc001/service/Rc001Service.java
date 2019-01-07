package rmsoft.ams.seoul.rc.rc001.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.SessionUtils;
import io.onsemiro.utils.UUIDUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.common.domain.*;
import rmsoft.ams.seoul.common.repository.*;
import rmsoft.ams.seoul.common.vo.ResponseForPaging;
import rmsoft.ams.seoul.rc.rc001.dao.Rc001Mapper;
import rmsoft.ams.seoul.rc.rc001.vo.*;
import rmsoft.ams.seoul.rc.rc002.service.Rc002Service;
import rmsoft.ams.seoul.rc.rc002.vo.Rc00201VO;
import rmsoft.ams.seoul.rc.rc002.vo.Rc00202VO;
import rmsoft.ams.seoul.rc.rc002.vo.Rc002VO;
import rmsoft.ams.seoul.rc.rc004.service.Rc004Service;
import rmsoft.ams.seoul.rc.rc005.dao.Rc005Mapper;
import rmsoft.ams.seoul.rc.rc005.vo.Rc00501VO;
import rmsoft.ams.seoul.rc.rc005.vo.Rc00502VO;
import rmsoft.ams.seoul.utils.CommonCodeUtils;
import rmsoft.ams.seoul.utils.CommonMessageUtils;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Rc 001 service.
 */
@Service("Rc001Service")
public class Rc001Service extends BaseService
{
    @Autowired
    private Rc001Mapper rc001Mapper;

    @Inject
    private Rc005Mapper rc005Mapper;

    @Inject
    private Rc004Service rc004Service;

    @Inject
    private Rc002Service rc002Service;

    @Autowired
    private RcItemRepository rcItemRepository;

    @Autowired
    private RcItemConRepository rcItemConRepository;

    @Autowired
    private RcItemComponentRepository rcItemComponentRepository;

    @Autowired
    private RcComponentRepository rcComponentRepository;

    @Autowired
    private RcAggregationRepository rcAggregationRepository;

    @Autowired
    private JobConvRepository jobConvRepository;

    /**
     * Gets all node.
     *
     * @param param the param
     * @return the all node
     */
    public List<Rc00101VO> getAllNode(Rc00101VO param)
    {
        ArrayList<Rc00101VO> nodes = new ArrayList<Rc00101VO>();
        nodes.addAll(rc001Mapper.getAggregationNode(param));

        //
        if(null != param.getIsDisplayItem() && param.getIsDisplayItem().equals("true")
                //||(null != param.getUuid() && !param.getUuid().equals(""))
                )
            nodes.addAll(rc001Mapper.getItemNode(param));

        return nodes;
    }

    /**
     * Gets all node.
     *
     * @param param the param
     * @return the all node
     */
    public ResponseForPaging<Rc00101VO> getAllNode(Rc00104VO param)
    {
        ResponseForPaging pageData = ModelMapperUtils.map(param,ResponseForPaging.class);

        pageData.setLimit(rc001Mapper.getTotalIconCnt(param));
        param.setLimit(pageData.getLimit());
        pageData.setList(rc001Mapper.getIconList(param));

        return pageData;
    }

    /**
     * Gets aggregation info.
     *
     * @param param the param
     * @return the aggregation info
     */
    public Rc00102VO getAggregationInfo(Rc00101VO param)
    {
        return rc001Mapper.getAggregationInfo(param);
    }

    /**
     * Gets item info.
     *
     * @param param the param
     * @return the item info
     */
    public Rc00102VO getItemInfo(Rc00101VO param)
    {
        return rc001Mapper.getItemInfo(param);
    }

    /**
     * Gets grid data for paging.
     *
     * @param param the param
     * @return the grid data for paging
     */
    public ResponseForPaging<Rc00103VO> getGridDataForPaging(Rc00104VO param)
    {
        ResponseForPaging pageData = ModelMapperUtils.map(param,ResponseForPaging.class);

        pageData.setLimit(rc001Mapper.getTotalGridCnt(param));
        param.setLimit(pageData.getLimit());
        pageData.setList(rc001Mapper.getGridList(param));

        return pageData;
    }

    /**
     * Gets grid data.
     *
     * @param param the param
     * @return the grid data
     */
    public List<Rc00103VO> getGridData(Rc00101VO param)
    {
        List<Rc00103VO> gridData = new ArrayList<Rc00103VO>();
        gridData.addAll(rc001Mapper.getGridDataInAggregation(param));

        //
        if(null != param.getUuid() && !param.getUuid().equals(""))
            gridData.addAll(rc001Mapper.getGridDataInItem(param));

        return gridData;
    }

    /**
     * Save api response.
     *
     * @param list the list
     * @return the api response
     */
    @Transactional
    public ApiResponse save(List<Map<String,String>> list)
    {
        for (Map<String,String> data: list) {
            Map<String, BigDecimal> cntMap = rc001Mapper.getItemAggregationCnt(data.get("parentUuid"));

            if(data.get("parentNodeType").equals("normal")){
                if(data.get("nodeType").equals("normal") && cntMap.get("ITEMCNT").intValue() > 0){
                    return ApiResponse.of(ApiStatus.SYSTEM_ERROR, CommonMessageUtils.getMessage("RC001_08"));
                }else if(data.get("nodeType").equals("item") &&  cntMap.get("AGGREGATIONCNT").intValue() > 0){
                    return ApiResponse.of(ApiStatus.SYSTEM_ERROR,CommonMessageUtils.getMessage("RC001_08"));
                }
            }

            rc001Mapper.save(data);
        }

        return ApiResponse.of(ApiStatus.SUCCESS,"SUCCESS");
    }

    /**
     * Save records api response.
     *
     * @param list the list
     * @return the api response
     */
    @Transactional
    public ApiResponse saveRecords(List<Map<String,String>> list)
    {
        RcItem rcItem = null;
        RcAggregation rcAggregation = null;

        for (Map<String,String> data: list) {
            if(data.get("nodeType").equals("item")){
                rcItem = new RcItem();
                rcItem.setItemUuid(data.get("uuid"));
                rcItem = rcItemRepository.findOne(rcItem.getId());
                rcItem.setTitle(data.get("title"));
                rcItem.setDescription(data.get("description"));
                rcItem.setNotes(data.get("notes"));
                rcItemRepository.save(rcItem);
            }else{
                rcAggregation = new RcAggregation();
                rcAggregation.setAggregationUuid(data.get("uuid"));
                rcAggregation = rcAggregationRepository.findOne(rcAggregation.getId());

                rcAggregation.setTitle(data.get("title"));
                rcAggregation.setDescription(data.get("description"));
                rcAggregation.setNotes(data.get("notes"));
                rcAggregationRepository.save(rcAggregation);
            }
        }

        return ApiResponse.of(ApiStatus.SUCCESS,"SUCCESS");
    }

    /**
     * Save records grid api response.
     *
     * @param list the list
     * @return the api response
     */
    @Transactional
    public ApiResponse saveRecordsGrid(List<Map<String,Object>> list){
        RcAggregation rcAggregation = null;

        for (Map<String,Object> data: list) {
            if(data.get("nodeType").equals("item")){
                if(data.get("riDescriptionStartDate") != null)
                    data.put("riDescriptionStartDate", data.get("riDescriptionStartDate").toString().replace("-","").substring(0,8));
                if(data.get("riDescriptionEndDate") != null)
                    data.put("riDescriptionEndDate", data.get("riDescriptionEndDate").toString().replace("-","").substring(0,8));
                if(data.get("creationStartDate") != null)
                    data.put("creationStartDate", data.get("creationStartDate").toString().replace("-","").substring(0,8));
                if(data.get("creationEndDate") != null)
                    data.put("creationEndDate", data.get("creationEndDate").toString().replace("-","").substring(0,8));

                Rc00501VO rc00501VO = ModelMapperUtils.map(data, Rc00501VO.class);

                rc004Service.saveItemDetails(rc00501VO);
            }else{
                Rc002VO rc002VO = new Rc002VO();
                Rc00201VO rc00201VO = ModelMapperUtils.map(data.get("systemMeta"), Rc00201VO.class);
                if(rc00201VO.getDescriptionStartDate() != null)
                    rc00201VO.setDescriptionStartDate(rc00201VO.getDescriptionStartDate().replace("-","").substring(0,8));
                if(rc00201VO.getDescriptionEndDate() != null)
                    rc00201VO.setDescriptionEndDate(rc00201VO.getDescriptionEndDate().replace("-","").substring(0,8));

                Rc00202VO rc00202VO = ModelMapperUtils.map(data.get("contextualMeta"), Rc00202VO.class);
                if(rc00202VO.getCreationStartDate() != null)
                    rc00202VO.setCreationStartDate(rc00202VO.getCreationStartDate().replace("-","").substring(0,8));
                if(rc00202VO.getCreationEndDate() != null)
                    rc00202VO.setCreationEndDate(rc00202VO.getCreationEndDate().replace("-","").substring(0,8));

                rc002VO.setSystemMeta(rc00201VO);
                rc002VO.setContextualMeta(rc00202VO);

                rc002Service.save(rc002VO);
            }
        }

        return ApiResponse.of(ApiStatus.SUCCESS,"SUCCESS");
    }


    /**
     * Gets menu.
     *
     * @param param the param
     * @return the menu
     */
    public Object getMenu(Map<String,String> param)
    {
        return rc001Mapper.getMenu(param);
    }

    /**
     * Update state api response.
     *
     * @param list the list
     * @return the api response
     */
    @Transactional
    public ApiResponse updateState(List<Map<String,String>> list) {
        List<RcAggregation> updateList = ModelMapperUtils.mapList(list,RcAggregation.class);
        for(RcAggregation data : updateList)
        {
            data.setUpdateUuid(SessionUtils.getCurrentLoginUserUuid());
            rc001Mapper.updateState(data);
        }
        return ApiResponse.of(ApiStatus.SUCCESS,"AA007");
    }

    /**
     * Delete aggregation api response.
     *
     * @param list the list
     * @return the api response
     */
    @Transactional
    public ApiResponse deleteAggregation(List<Rc00101VO> list) {
        boolean error = false;
        String msg = "";
            for(Rc00101VO data : list)
            {
                if(rc001Mapper.checkPublished(data) > 0)
                {
                    error = true;
                    msg = "RC001_05";
                    break;
                }
                if(!data.getNodeType().equals("item") && rc001Mapper.getChildrenCnt(data) > 0)
                {
                    error = true;
                    msg = "RC001_04";
                    break;
                }
                else
                    rc001Mapper.deleteAggregation(data);
            }
        if(error)
            return ApiResponse.of(ApiStatus.SYSTEM_ERROR,msg);
        else
            return ApiResponse.of(ApiStatus.SUCCESS,"AA007");
    }

    /**
     * Gets navi data.
     *
     * @param param the param
     * @return the navi data
     */
    public List<Rc00101VO> getNaviData(Rc00101VO param)
    {
        return rc001Mapper.getNaviData(param);
    }


    /**
     * Move api response.
     *
     * @param data the data
     * @return the api response
     */
    public ApiResponse move(Rc00105VO data)
    {
        String targetUuid = data.getTargetUuid();
        List<Rc00106VO> list = data.getList();

        for(Rc00106VO moveBean : list)
        {
            if(!targetUuid.equals(moveBean.getUuid()))
            {
                moveBean.setParentUuid(targetUuid);
                moveBean.setUpdateUuid(SessionUtils.getCurrentLoginUserUuid());
                rc001Mapper.move(moveBean);
            }

        }

        return ApiResponse.of(ApiStatus.SUCCESS,"AA007");
    }


    /**
     * Move component api response.
     *
     * @param param the param
     * @return the api response
     */
    @Transactional
    public ApiResponse moveComponent(List<Rc00502VO> param){
        List<RcItemComponent> itemList = ModelMapperUtils.mapList(param, RcItemComponent.class);

        for (RcItemComponent item : itemList) {
            item.setItemComponentUuid(item.getItemComponentUuid());
            RcItemComponent orgItem = rcItemComponentRepository.findOne(item.getId());
            orgItem.setItemUuid(item.getItemUuid());
            orgItem.setInsertDate(orgItem.getInsertDate());
            orgItem.setInsertUuid(orgItem.getInsertUuid());

            rcItemComponentRepository.save(orgItem);
        }

        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    /**
     * Item 2 Item
     *
     * @param param the param
     * @return api response
     */
    @Transactional
    public ApiResponse delItemAndMoveComponent(List<Rc00502VO> param) {
        for (Rc00502VO rc00502VO : param){
            List<Rc00502VO> compList = rc005Mapper.getRecordComponentList(rc00502VO);
            for (Rc00502VO comp : compList) {
                comp.setItemUuid(rc00502VO.getTargetItemUuid());
            }
            // Component 이동
            moveComponent(compList);

            // RC_ITEM_CON 삭제
            RcItemCon rcItemCon = new RcItemCon();
            rcItemCon.setItemUuid(rc00502VO.getItemUuid());
            rcItemConRepository.delete(rcItemCon.getId());

            // RC_ITEM 삭제
            RcItem rcItem = new RcItem();
            rcItem.setItemUuid(rc00502VO.getItemUuid());
            rcItemRepository.delete(rcItem.getId());
        }

        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    /**
     * Component 2 Record Window
     *
     * @param params the params
     * @return api response
     */
    @Transactional
    public ApiResponse creItemAndMoveComponent(Rc00501VO params) {
        Rc00501VO rc00501VO = new Rc00501VO();

        rc00501VO.setName(params.getRaTitle());
        rc00501VO.setRiPublishedStatusUuid(params.getRiPublishedStatusUuid());
        rc00501VO.setRaAggregationUuid(params.getRaAggregationUuid());

        rc00501VO = rc004Service.saveItemDetails(rc00501VO);

        List<Rc00502VO> compList = params.getRc00502VoList();
        for (Rc00502VO comp : compList) {
            comp.setItemUuid(rc00501VO.getRiItemUuid());
        }

        // Component 이동
        moveComponent(compList);

        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    /**
     * uuid에 해당하는 Aggregaiton 및 하위 Aggregation Type 변경
     *
     * @param param the param
     * @return responses . map response
     */
    @Transactional
    public Responses.MapResponse updateAggregationType(Rc00101VO param){
        List<Rc00101VO> aggList = rc001Mapper.getBottomAggregations(param);

        Map rtnMap = new HashMap();

        for (Rc00101VO item : aggList) {
            if((item.getAggregationCnt() > 0 && item.getChildCnt() > 0)){
                rtnMap.put("isSuccess", false);
                rtnMap.put("message", "Normal Aggregation으로 변경시 Item은 최하위 Aggregation에 위치해야합니다.");
                return Responses.MapResponse.of(rtnMap);
            }else if(item.getClassifyCnt() > 0){
                rtnMap.put("isSuccess", false);
                rtnMap.put("message", "Classify Result에 등록된 정보가 있습니다.");
                return Responses.MapResponse.of(rtnMap);
            }
        }


        for (Rc00101VO item : aggList) {
            RcAggregation rcAggregation = new RcAggregation();

            rcAggregation.setAggregationUuid(item.getUuid());
            RcAggregation orgItem = rcAggregationRepository.findOne(rcAggregation.getId());
            orgItem.setTypeUuid(param.getNodeType());
            orgItem.setInsertDate(orgItem.getInsertDate());
            orgItem.setInsertUuid(orgItem.getInsertUuid());

            rcAggregationRepository.save(orgItem);
        }

        rtnMap.put("isSuccess", true);
        rtnMap.put("list", aggList);

        return Responses.MapResponse.of(rtnMap);
    }

    /**
     * 외부파일 Drag & Drop시 Item 생성 및 Component생성
     *
     * @param params the params
     * @return api response
     */
    @Transactional
    public ApiResponse creItemAndCreComponent(Rc00501VO params) {
        Rc00501VO rc00501VO = new Rc00501VO();

        rc00501VO.setName(params.getRaTitle());
        rc00501VO.setRiPublishedStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD121", "Draft"));
        rc00501VO.setRaAggregationUuid(params.getRaAggregationUuid());
        rc00501VO.setExtraMetadata(params.getExtraMetadata());

        rc00501VO = rc004Service.saveItemDetails(rc00501VO);

        List<Rc00502VO> compList = params.getRc00502VoList();
        for (Rc00502VO comp : compList) {
            comp.setItemUuid(rc00501VO.getRiItemUuid());
            comp.setPublicationStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD121", "Draft"));
            comp.setAreaUuid(CommonCodeUtils.getCodeDetailUuid("CD125", "Attachment"));
            comp.setTypeUuid(CommonCodeUtils.getCodeDetailUuid("CD126", "Document"));
            comp.setOpenStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD123", "Open"));
            comp.setElectronYn("Y");
        }

        // Component 생성 및 Item연결
        createComponent(compList);

        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    /**
     * 외부파일 Drag & Drop 시 Component 생성 및 Item에 연결
     *
     * @param param the param
     * @return api response
     */
    @Transactional
    public ApiResponse createComponent(List<Rc00502VO> param){
        List<RcComponent> itemList = ModelMapperUtils.mapList(param, RcComponent.class);
        int idx = 0;

        for (RcComponent rcComponent : itemList) {
            String componentUuid = UUIDUtils.getUUID();     //component Uuid생성
            String itemUuid = param.get(idx).getItemUuid();

            rcComponent.setComponentUuid(componentUuid);
            rcComponentRepository.save(rcComponent);

            rcComponent = rcComponentRepository.findOne(rcComponent.getId());

            RcItemComponent rcItemComponent = new RcItemComponent();
            rcItemComponent.setItemComponentUuid(UUIDUtils.getUUID());
            rcItemComponent.setComponentUuid(rcComponent.getComponentUuid());
            rcItemComponent.setItemUuid(itemUuid);
            rcItemComponentRepository.save(rcItemComponent);

            JobConv jobConv = new JobConv();
            jobConv.setJobid(componentUuid);
            jobConv.setDestfile("sftp://" + rcComponent.getServiceFilePath() + "/" + rcComponent.getServiceFileName());
            jobConv.setSrcfile("sftp://" + rcComponent.getFilePath() + "/" + rcComponent.getOriginalFileName());
            jobConv.setJobstatus("W");
            jobConv.setReqdate(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(),DateUtils.DATE_TIME_PATTERN)));
            jobConvRepository.save(jobConv);

            idx++;
        }

        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    /**
     * Gets Search data for paging.
     *
     * @param param the param
     * @return the grid data for paging
     */
    public ResponseForPaging<Rc00107VO> getSearchDataForPaging(Rc00107VO param)
    {
        ResponseForPaging pageData = ModelMapperUtils.map(param,ResponseForPaging.class);

        pageData.setLimit(rc001Mapper.getTotalSearchCnt(param));
        param.setLimit(pageData.getLimit());
        pageData.setList(rc001Mapper.getSearchList(param));

        return pageData;
    }
}
