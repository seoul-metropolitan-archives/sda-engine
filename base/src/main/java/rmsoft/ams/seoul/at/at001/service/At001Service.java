package rmsoft.ams.seoul.at.at001.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.SessionUtils;
import io.onsemiro.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.at.at001.dao.At001Mapper;
import rmsoft.ams.seoul.at.at001.vo.At00101VO;
import rmsoft.ams.seoul.common.domain.AtAuthority;
import rmsoft.ams.seoul.common.repository.AtAuthorityRepository;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import javax.inject.Inject;


/**
 * The type Cl 003 service.
 */
@Service
public class At001Service extends BaseService {
    @Inject
    private At001Mapper at001Mapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AtAuthorityRepository atAuthorityRepository;
    /**
     * Gets classified record list.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the classified record list
     */
    public Page<At00101VO> getAuthorityList(Pageable pageable, RequestParams<At00101VO> requestParams) {
        At00101VO at00101VO = new At00101VO();
        at00101VO.setAuthorityTypeUuid(requestParams.getString("authorityTypeUuid"));
        at00101VO.setAuthorityName(requestParams.getString("authorityName"));
        return filter(at001Mapper.getAuthorityList(at00101VO), pageable, "", At00101VO.class);
    }
    @Transactional
    public ApiResponse saveAuthority(At00101VO at00101VO) {
        AtAuthority atAuthority = ModelMapperUtils.map(at00101VO,AtAuthority.class);
        if(null != at00101VO.getAuthorityUuid() || "".equals(at00101VO.getAuthorityUuid())){
            if(atAuthority.isDeleted()){
                atAuthorityRepository.delete(atAuthority);
            }else{
                AtAuthority orgAtAuthority = atAuthorityRepository.findOne(atAuthority.getId());
                atAuthority.setDescriptorUuid(SessionUtils.getCurrentLoginUserUuid());
                atAuthority.setUseYn(orgAtAuthority.getUseYn());
                atAuthority.setInsertDate(orgAtAuthority.getInsertDate());
                atAuthority.setInsertUuid(orgAtAuthority.getInsertUuid());
                atAuthorityRepository.save(atAuthority);
            }
        }else{
            atAuthority.setAuthorityUuid(UUIDUtils.getUUID());
            atAuthority.setDescriptorUuid(SessionUtils.getCurrentLoginUserUuid());
            if(! "ORG".equals(CommonCodeUtils.getDetailCode("CD161",at00101VO.getAuthorityTypeUuid()))){
                atAuthority.setOrgTypeUuid(" ");
            }
            String prefix  = CommonCodeUtils.getDetailCode("CD161",at00101VO.getAuthorityTypeUuid());
            String cnt = jdbcTemplate.queryForObject("SELECT LPAD(TO_NUMBER(SUBSTR(MAX(AUTHORITY_NO),"+ (prefix.length()+3) + ")) + 1,4,'0') FROM AT_AUTHORITY WHERE AUTHORITY_NO LIKE '" + prefix +"' || '%'", String.class);
            if(cnt == null){
                cnt = "0001";
            }
            cnt = prefix + "-" + cnt;
            atAuthority.setAuthorityNo(cnt);
            atAuthority.setUseYn("Y");
            atAuthorityRepository.save(atAuthority);
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }
}
