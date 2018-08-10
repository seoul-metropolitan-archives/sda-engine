package rmsoft.ams.seoul.rc.rc001.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.common.domain.RcAggregation;
import rmsoft.ams.seoul.common.domain.RcItemComponent;
import rmsoft.ams.seoul.common.repository.RcItemComponentRepository;
import rmsoft.ams.seoul.common.vo.ResponseForPaging;
import rmsoft.ams.seoul.rc.rc001.dao.Rc001Mapper;
import rmsoft.ams.seoul.rc.rc001.vo.*;
import rmsoft.ams.seoul.rc.rc005.vo.Rc00502VO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("Rc001Service")
public class Rc001Service extends BaseService
{
    @Autowired
    private Rc001Mapper rc001Mapper;

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

    @Autowired
    private RcItemComponentRepository repository;

    public ApiResponse moveComponent(Rc00502VO param){
        RcItemComponent item = new RcItemComponent();
        item.setItemComponentUuid(param.getItemComponentUuid());

        RcItemComponent orgItem = repository.findOne(item.getId());
        orgItem.setItemUuid(param.getItemUuid());
        orgItem.setInsertDate(orgItem.getInsertDate());
        orgItem.setInsertUuid(orgItem.getInsertUuid());

        repository.save(orgItem);

        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }
}
