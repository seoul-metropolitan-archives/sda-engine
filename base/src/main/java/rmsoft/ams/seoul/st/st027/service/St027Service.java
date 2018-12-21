package rmsoft.ams.seoul.st.st027.service;

        import io.onsemiro.core.api.response.ApiResponse;
        import io.onsemiro.core.code.ApiStatus;
        import io.onsemiro.core.domain.BaseService;
        import io.onsemiro.core.parameter.RequestParams;
        import io.onsemiro.utils.ModelMapperUtils;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.data.domain.Page;
        import org.springframework.data.domain.Pageable;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Transactional;
/*import rmsoft.ams.seoul.common.domain.StZone;
import rmsoft.ams.seoul.common.repository.StZoneRepository;*/
        import rmsoft.ams.seoul.common.domain.StZone;
        import rmsoft.ams.seoul.common.repository.StZoneRepository;
        import rmsoft.ams.seoul.st.st027.dao.St027Mapper;
        import rmsoft.ams.seoul.st.st027.vo.St02701VO;

        import javax.inject.Inject;
        import java.util.List;

@Service
public class St027Service extends BaseService {

    @Inject
    private St027Mapper st027Mapper;


    @Autowired
    private StZoneRepository stZoneRepository;

    public Page<St02701VO> getStZone(Pageable pageable, RequestParams<St02701VO> requestParams) {
        St02701VO st02701VO = new St02701VO();
        st02701VO.setZoneId(requestParams.getString("zoneId"));
        st02701VO.setZoneName(requestParams.getString("zoneName"));
        //검색조건 추가시

        return filter(st027Mapper.getStZone(st02701VO), pageable, "", St02701VO.class);
    }

    @Transactional
    public ApiResponse saveZone(List<St02701VO> list) {
        List<StZone> stZoneList = ModelMapperUtils.mapList(list, StZone.class);
        StZone orgStZone = null;
        for(StZone stZone : stZoneList){
            if(stZone.isDeleted()){
                stZoneRepository.delete(stZone);
                //TODO : 삭제시 ST_GATE도 같이 삭제해주어야 한다.
            }else{
                if(stZone.isCreated()){
                    int no = jdbcTemplate.queryForObject("SELECT ST_ZONE_SEQ.NEXTVAL FROM dual", int.class);
                    stZone.setNo(no);
                }else if(stZone.isModified()){
                    orgStZone = stZoneRepository.findOne(stZone.getId());
                    stZone.setInsertDate(orgStZone.getInsertDate());
                    stZone.setInsertUuid(orgStZone.getInsertUuid());
                }

                stZoneRepository.save(stZone);
            }
        }


        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }
}
