package rmsoft.ams.seoul.st.st011.service;

import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.st.st010.vo.St01004VO;
import rmsoft.ams.seoul.st.st011.dao.St011Mapper;
import rmsoft.ams.seoul.st.st011.vo.St01101VO;
import rmsoft.ams.seoul.st.st011.vo.St01102VO;

import javax.inject.Inject;

@Service
public class St011Service extends BaseService {

    @Inject
    private St011Mapper st011Mapper;

    public Page<St01101VO> getAggregation(Pageable pageable, RequestParams<St01101VO> requestParams) {
        St01101VO st01101VO = new St01101VO();
        st01101VO.setContainerUuid(requestParams.getString("containerUuid"));
        st01101VO.setCode(requestParams.getString("code"));
        st01101VO.setTitle(requestParams.getString("title"));
        st01101VO.setRepositoryUuid(requestParams.getString("repositoryUuid"));
        st01101VO.setShelfUuid(requestParams.getString("shelfUuid"));
        st01101VO.setLocationUuid(requestParams.getString("locationUuid"));


        return filter(st011Mapper.getAggregation(st01101VO), pageable, "", St01101VO.class);
    }

    public Page<St01102VO> getTakeInOutList(Pageable pageable, RequestParams<St01102VO> requestParams) {

        St01102VO st01102VO = new St01102VO();
        st01102VO.setAggregationUuid(requestParams.getString("aggregationUuid"));
        st01102VO.setRequestorUuid(requestParams.getString("requestorUuid"));
        return filter(st011Mapper.getTakeInOutList(st01102VO), pageable, "", St01102VO.class);
    }
}
