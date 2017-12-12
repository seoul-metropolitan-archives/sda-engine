package rmsoft.ams.seoul.common.service;

import io.onsemiro.core.domain.BaseService;
import io.onsemiro.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.common.domain.RcComponent;
import rmsoft.ams.seoul.common.repository.RcComponentRepository;

import java.util.Map;

@Service
public class CommonService extends BaseService {

    @Autowired
    RcComponentRepository rcComponentRepository;

    public RcComponent getComponentData(Map<String,String> data)
    {
        RcComponent rcComponent = ModelMapperUtils.map(data,RcComponent.class);
        return rcComponentRepository.findOne(rcComponent.getId());
    }

}
