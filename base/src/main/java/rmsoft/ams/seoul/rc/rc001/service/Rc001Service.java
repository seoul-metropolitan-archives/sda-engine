package rmsoft.ams.seoul.rc.rc001.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.CommonCodeUtils;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.SessionUtils;
import io.onsemiro.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.common.domain.*;
import rmsoft.ams.seoul.common.repository.*;
import rmsoft.ams.seoul.common.vo.ResponseForPaging;
import rmsoft.ams.seoul.rc.rc001.dao.Rc001Mapper;
import rmsoft.ams.seoul.rc.rc001.vo.*;
import rmsoft.ams.seoul.rc.rc004.service.Rc004Service;
import rmsoft.ams.seoul.rc.rc005.dao.Rc005Mapper;
import rmsoft.ams.seoul.rc.rc005.service.Rc005Service;
import rmsoft.ams.seoul.rc.rc005.vo.Rc00501VO;
import rmsoft.ams.seoul.rc.rc005.vo.Rc00502VO;

import javax.inject.Inject;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("Rc001Service")
public class Rc001Service extends BaseService
{
    @Autowired
    private Rc001Mapper rc001Mapper;

    @Inject
    private Rc005Mapper rc005Mapper;

    @Inject
    private Rc004Service rc004Service;

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
    public ResponseForPaging<Rc00101VO> getAllNode(Rc00104VO param)
    {
        ResponseForPaging pageData = ModelMapperUtils.map(param,ResponseForPaging.class);

        pageData.setLimit(rc001Mapper.getTotalIconCnt(param));
        param.setLimit(pageData.getLimit());
        pageData.setList(rc001Mapper.getIconList(param));

        return pageData;
    }
    public Rc00102VO getAggregationInfo(Rc00101VO param)
    {
        return rc001Mapper.getAggregationInfo(param);
    }
    public Rc00102VO getItemInfo(Rc00101VO param)
    {
        return rc001Mapper.getItemInfo(param);
    }

    public ResponseForPaging<Rc00103VO> getGridDataForPaging(Rc00104VO param)
    {
        ResponseForPaging pageData = ModelMapperUtils.map(param,ResponseForPaging.class);

        pageData.setLimit(rc001Mapper.getTotalGridCnt(param));
        param.setLimit(pageData.getLimit());
        pageData.setList(rc001Mapper.getGridList(param));

        return pageData;
    }

    public List<Rc00103VO> getGridData(Rc00101VO param)
    {
        List<Rc00103VO> gridData = new ArrayList<Rc00103VO>();
        gridData.addAll(rc001Mapper.getGridDataInAggregation(param));

        //
        if(null != param.getUuid() && !param.getUuid().equals(""))
            gridData.addAll(rc001Mapper.getGridDataInItem(param));

        return gridData;
    }

    public ApiResponse save(List<Map<String,String>> list)
    {
        for (Map<String,String> data: list) {
            rc001Mapper.save(data);
        }

        return ApiResponse.of(ApiStatus.SUCCESS,"SUCCESS");
    }

    public Object getMenu(Map<String,String> param)
    {
        return rc001Mapper.getMenu(param);
    }

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

    public List<Rc00101VO> getNaviData(Rc00101VO param)
    {
        return rc001Mapper.getNaviData(param);
    }


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
     * @param param
     * @return
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
     * @param params
     * @return
     */
    @Transactional
    public ApiResponse creItemAndMoveComponent(Rc00501VO params) {
        RequestParams<Rc00501VO> requestParams = new RequestParams();

        requestParams.put("title", params.getRaTitle());
        requestParams.put("publishedStatusUuid", params.getRiPublishedStatusUuid());
        requestParams.put("raAggregationUuid", params.getRaAggregationUuid());


        Map resultMap = rc004Service.saveItemDetails(requestParams);

        List<Rc00502VO> compList = params.getRc00502VoList();
        for (Rc00502VO comp : compList) {
            comp.setItemUuid(resultMap.get("itemUuid").toString());
        }

        // Component 이동
        moveComponent(compList);

        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    /**
     * uuid에 해당하는 Aggregaiton 및 하위 Aggregation Type 변경
     * @param param
     * @return
     */
    @Transactional
    public Responses.MapResponse updateAggregationType(Rc00101VO param){
        List<Rc00101VO> aggList = rc001Mapper.getBottomAggregations(param);

        String maxLevel = "";
        Map rtnMap = new HashMap();


        if(aggList.size() > 0){
            maxLevel = aggList.get(0).getLevel();
        }

        for (Rc00101VO item : aggList) {
            if(Integer.parseInt(maxLevel) > Integer.parseInt(item.getLevel()) && item.getChildCnt() > 0){
                rtnMap.put("isSuccess", false);
                rtnMap.put("message", "Normal Aggregation으로 변경시 Item은 최하위 Aggregation에 위치해야합니다.");
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
     * @param params
     * @return
     */
    @Transactional
    public ApiResponse creItemAndCreComponent(Rc00501VO params) {
        RequestParams<Rc00501VO> requestParams = new RequestParams();

        requestParams.put("title", params.getRaTitle());
        requestParams.put("publishedStatusUuid", CommonCodeUtils.getCode("CD121", "Draft"));
        requestParams.put("raAggregationUuid", params.getRaAggregationUuid());

        Map resultMap = rc004Service.saveItemDetails(requestParams);

        List<Rc00502VO> compList = params.getRc00502VoList();
        for (Rc00502VO comp : compList) {
            comp.setItemUuid(resultMap.get("itemUuid").toString());
            comp.setPublicationStatusUuid(CommonCodeUtils.getCode("CD121", "Draft"));
            comp.setAreaUuid(CommonCodeUtils.getCode("CD125", "Attachment"));
            comp.setTypeUuid(CommonCodeUtils.getCode("CD126", "Draft"));
            comp.setOpenStatusUuid(CommonCodeUtils.getCode("CD123", "Open"));
            comp.setElectronYn("Y");
        }

        // Component 생성 및 Item연결
        createComponent(compList);

        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    /**
     * 외부파일 Drag & Drop 시 Component 생성 및 Item에 연결
     * @param params
     * @return
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

            idx++;
        }

        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }
}
