package rmsoft.ams.seoul.at.at001.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.DateUtils;
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
import rmsoft.ams.seoul.at.at001.vo.At00102VO;
import rmsoft.ams.seoul.at.at001.vo.At00103VO;
import rmsoft.ams.seoul.at.at001.vo.At001VO;
import rmsoft.ams.seoul.common.domain.AtAuthority;
import rmsoft.ams.seoul.common.domain.AtAuthorityRelation;
import rmsoft.ams.seoul.common.repository.AtAuthorityRelationRepository;
import rmsoft.ams.seoul.common.repository.AtAuthorityRepository;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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

    @Autowired
    private AtAuthorityRelationRepository atAuthorityRelationRepository;

    private AtAuthorityRelation relAuthrelAuth;

    private At00102VO at00102VO;
    /**
     * Gets classified record list.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the classified record list
     */
    @Transactional
    public Page<At001VO> getAuthorityList(Pageable pageable, RequestParams<At00101VO> requestParams) {
        At001VO at001VO = null;
        At00101VO at00101VO = new At00101VO();
        At00102VO at00102VO = new At00102VO();
        List<At001VO> at001VOList = new ArrayList<At001VO>();
        List<At00101VO> at00101VOList = null;
        List<At00102VO> at00102VOList = null;

        at00101VO.setAuthorityTypeUuid(requestParams.getString("authorityTypeUuid"));
        at00101VO.setAuthorityName(requestParams.getString("authorityName"));

        at00101VOList = at001Mapper.getAuthorityList(at00101VO);

        for(At00101VO item : at00101VOList)
        {
            at00102VO.setAuthorityUuid(item.getAuthorityUuid());
            at00102VOList = at001Mapper.getRelAuthorityList(at00102VO);
            at001VO = new At001VO();
            at001VO.setAuthInfo(item);
            at001VO.setRelAuthList(at00102VOList);
            at001VOList.add(at001VO);

        }

        return filter(at001VOList, pageable, "", At001VO.class);
    }

    @Transactional
    public Page<At001VO> getAuthorityListForMetaInfo(Pageable pageable, RequestParams<At00101VO> requestParams) {
        At00101VO at00101VO = new At00101VO();
        List<At00101VO> at00101VOList = null;
        at00101VO.setAuthorityTypeUuid(requestParams.getString("authorityTypeUuid"));
        at00101VO.setAuthorityName(requestParams.getString("authorityName"));
        at00101VO.setAntiAuthorityUuid(requestParams.getString("antiAuthorityUuid"));

        at00101VOList = at001Mapper.getAuthorityList(at00101VO);
        return filter(at00101VOList, pageable, "", At001VO.class);
    }


    @Transactional
    public Page<At001VO> getAuthorityMetaInfoList(Pageable pageable, RequestParams<At00103VO> requestParams) {
        At00103VO at00103VO = new At00103VO();
        List<At00103VO> at00103VOList = null;
        at00103VO.setAuthorityUuid(requestParams.getString("authorityUuid"));
        at00103VOList = at001Mapper.getAuthorityMetaInfoList(at00103VO);
        return filter(at00103VOList, pageable, "", At001VO.class);
    }

    @Transactional
    public ApiResponse saveAuthority(At001VO at001VO) {
        boolean isCreate = false;
        At00101VO at00101VO =  at001VO.getAuthInfo();
        AtAuthority atAuthority = ModelMapperUtils.map(at001VO.getAuthInfo(),AtAuthority.class);
        List<AtAuthorityRelation> relAuthList = ModelMapperUtils.mapList(at001VO.getRelAuthList(),AtAuthorityRelation.class);

        if(null != at00101VO.getAuthorityUuid() || "".equals(at00101VO.getAuthorityUuid())){
            if(atAuthority.isDeleted()){//delete
                atAuthorityRepository.delete(atAuthority);
                if(null != relAuthList){
                    for(AtAuthorityRelation child : relAuthList)
                    {
                        atAuthorityRelationRepository.delete(child);
                        atAuthorityRelationRepository.delete(atAuthorityRelationRepository.findOne(getRelAuthId(child,at00101VO).getId()));
                    }
                }
            }else{//modify
                AtAuthority orgAtAuthority = atAuthorityRepository.findOne(atAuthority.getId());
                atAuthority.setDescriptorUuid(SessionUtils.getCurrentLoginUserUuid());
                atAuthority.setUseYn(orgAtAuthority.getUseYn());
                atAuthority.setDescriptionDate(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));
                atAuthority.setInsertDate(orgAtAuthority.getInsertDate());
                atAuthority.setInsertUuid(orgAtAuthority.getInsertUuid());
                atAuthorityRepository.save(atAuthority);
                if(null != relAuthList){
                    AtAuthorityRelation orgAtAuthorityRelation;
                    AtAuthorityRelation orgRelAtAuthorityRelation;
                    String orgRelationTypeUuid = "";
                    String orgRelAuthorityUuid = "";
                    for(AtAuthorityRelation child : relAuthList)
                    {
                        child.setAuthorityUuid(atAuthority.getAuthorityUuid());

                        if(child.isCreated()){
                            child.setAuthorityRelationUuid(UUIDUtils.getUUID());
                            child.setAuthorityUuid(atAuthority.getAuthorityUuid());
                            atAuthorityRelationRepository.save(child);
                            saveRelAuthOfRelAuth(child);
                        }
                        else if(child.isModified()){
                            orgAtAuthorityRelation =  atAuthorityRelationRepository.findOne(child.getId());
                            orgRelationTypeUuid = orgAtAuthorityRelation.getRelationTypeUuid();
                            orgRelAuthorityUuid = orgAtAuthorityRelation.getRelAuthorityUuid();

                            child.setInsertDate(orgAtAuthorityRelation.getInsertDate());
                            child.setInsertUuid(orgAtAuthorityRelation.getInsertUuid());
                            atAuthorityRelationRepository.save(child);

                            orgRelAtAuthorityRelation = atAuthorityRelationRepository.findOne(getRelAuthId(child,orgRelationTypeUuid,orgRelAuthorityUuid).getId());

                            saveRelAuthOfRelAuth(child,orgRelAtAuthorityRelation);

                        }
                        else if(child.isDeleted()){
                            atAuthorityRelationRepository.delete(child);
                            atAuthorityRelationRepository.delete(atAuthorityRelationRepository.findOne(getRelAuthId(child,at00101VO).getId()));
                        }
                    }
                }

            }
        }else{//create
            atAuthority.setAuthorityUuid(UUIDUtils.getUUID());
            atAuthority.setDescriptorUuid(SessionUtils.getCurrentLoginUserUuid());
            if(! "OR".equals(CommonCodeUtils.getDetailCode("CD161",at00101VO.getAuthorityTypeUuid()))){
                atAuthority.setOrgTypeUuid(" ");
            }
            String prefix  = CommonCodeUtils.getDetailCode("CD161",at00101VO.getAuthorityTypeUuid());
            String cnt = jdbcTemplate.queryForObject("SELECT LPAD(TO_NUMBER(SUBSTR(MAX(AUTHORITY_NO),"+ (prefix.length()+2) + ")) + 1,5,'0') FROM AT_AUTHORITY WHERE AUTHORITY_NO LIKE '" + prefix +"' || '%'", String.class);
            if(cnt == null){
                cnt = "00001";
            }
            cnt = prefix + "-" + cnt;
            atAuthority.setAuthorityNo(cnt);
            atAuthority.setUseYn("Y");
            atAuthority.setDescriptionDate(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));
            atAuthorityRepository.save(atAuthority);

            //relation authority
            if(null != relAuthList){
//                AtAuthorityRelation relAuthrelAuth;
                for(AtAuthorityRelation child : relAuthList)
                {
                    child.setAuthorityRelationUuid(UUIDUtils.getUUID());
                    child.setAuthorityUuid(atAuthority.getAuthorityUuid());
                    atAuthorityRelationRepository.save(child);
                    saveRelAuthOfRelAuth(child);

                }
            }
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    public void saveRelAuthOfRelAuth(AtAuthorityRelation child){

        relAuthrelAuth = new AtAuthorityRelation();
        relAuthrelAuth.setAuthorityRelationUuid(UUIDUtils.getUUID());
        relAuthrelAuth.setAuthorityUuid(child.getRelAuthorityUuid());
        relAuthrelAuth.setRelAuthorityUuid(child.getAuthorityUuid());

        if(!"미등록코드".equals(CommonCodeUtils.getAttr01Code("CD162",child.getRelationTypeUuid()))){
            relAuthrelAuth.setRelationTypeUuid(CommonCodeUtils.getCodeDetailUuidByCode("CD162",CommonCodeUtils.getAttr01Code("CD162",child.getRelationTypeUuid())));
        }else{
            relAuthrelAuth.setRelationTypeUuid(child.getRelationTypeUuid());
        }
        atAuthorityRelationRepository.save(relAuthrelAuth);
    }

    public void saveRelAuthOfRelAuth(AtAuthorityRelation child, AtAuthorityRelation old){

        relAuthrelAuth = new AtAuthorityRelation();
        relAuthrelAuth.setAuthorityRelationUuid(old.getAuthorityRelationUuid());
        relAuthrelAuth.setAuthorityUuid(child.getRelAuthorityUuid());
        relAuthrelAuth.setRelAuthorityUuid(child.getAuthorityUuid());
        relAuthrelAuth.setInsertDate(old.getInsertDate());
        relAuthrelAuth.setInsertUuid(old.getInsertUuid());

        if(!"미등록코드".equals(CommonCodeUtils.getAttr01Code("CD162",child.getRelationTypeUuid()))){
            relAuthrelAuth.setRelationTypeUuid(CommonCodeUtils.getCodeDetailUuidByCode("CD162",CommonCodeUtils.getAttr01Code("CD162",child.getRelationTypeUuid())));
        }else{
            relAuthrelAuth.setRelationTypeUuid(child.getRelationTypeUuid());
        }
        atAuthorityRelationRepository.save(relAuthrelAuth);
    }

    public AtAuthorityRelation getRelAuthId(AtAuthorityRelation child, At00101VO at00101VO){
        relAuthrelAuth = new AtAuthorityRelation();
        at00102VO = new At00102VO();
        at00102VO.setAuthorityUuid(child.getRelAuthorityUuid());
        at00102VO.setRelAuthorityUuid(at00101VO.getAuthorityUuid());
        if(!"미등록코드".equals(CommonCodeUtils.getAttr01Code("CD162",child.getRelationTypeUuid()))) {
            at00102VO.setRelationTypeUuid(CommonCodeUtils.getCodeDetailUuidByCode("CD162", CommonCodeUtils.getAttr01Code("CD162", child.getRelationTypeUuid())));
        }else{
            at00102VO.setRelationTypeUuid(child.getRelationTypeUuid());
        }
        return ModelMapperUtils.map(at001Mapper.getAuthorityRelation(at00102VO),AtAuthorityRelation.class);
    }

    public AtAuthorityRelation getRelAuthId(AtAuthorityRelation child,String orgRelationTypeUuid, String orgRelAuthorityUuid){
        at00102VO = new At00102VO();
        at00102VO.setAuthorityUuid(orgRelAuthorityUuid);
        at00102VO.setRelAuthorityUuid(child.getAuthorityUuid());
        if(!"미등록코드".equals(CommonCodeUtils.getAttr01Code("CD162",orgRelationTypeUuid))) {
            at00102VO.setRelationTypeUuid(CommonCodeUtils.getCodeDetailUuidByCode("CD162", CommonCodeUtils.getAttr01Code("CD162", orgRelationTypeUuid)));
        }else{
            at00102VO.setRelationTypeUuid(orgRelationTypeUuid);
        }
        return ModelMapperUtils.map(at001Mapper.getAuthorityRelation(at00102VO),AtAuthorityRelation.class);
    }
}
