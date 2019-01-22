package rmsoft.ams.seoul.ac.ac002.service;

import io.onsemiro.core.domain.BaseService;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.SessionUtils;
import io.onsemiro.utils.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.ac.ac002.dao.Ac002Mapper;
import rmsoft.ams.seoul.ac.ac002.vo.Ac00201VO;
import rmsoft.ams.seoul.ac.ac002.vo.Ac002VO;
import rmsoft.ams.seoul.common.domain.AdBookmark;
import rmsoft.ams.seoul.common.domain.AdMenu;
import rmsoft.ams.seoul.common.repository.AdBookmarkRepository;
import rmsoft.ams.seoul.common.repository.AdMenuRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Ac 002 service.
 */
@Service
public class Ac002Service extends BaseService
{
    /**
     * The Ac 002 mapper.
     */
    @Autowired
    Ac002Mapper ac002Mapper;

    @Autowired
    AdBookmarkRepository adBookmarkRepository;

    @Autowired
    AdMenuRepository adMenuRepository;

    /**
     * Gets startup program.
     *
     * @param param the param
     * @return the startup program
     */
    public Ac002VO getStartupProgram(Ac002VO param)
    {
        Ac002VO ac002VO = ac002Mapper.getStartupProgram(param);


        return ac002VO;
    }

    /**
     * Gets startup program.
     *
     * @param param the param
     * @return the startup program
     */
    public List<Ac00201VO> addBookMark(Ac00201VO param)
    {
        //List<Ac00201VO> ac00201VO = ac002Mapper.getStartupProgram(param);

        AdBookmark adBookmark = ModelMapperUtils.map(param, AdBookmark.class);

        AdMenu adMenu = new AdMenu();
        adMenu.setMenuUuid(param.getMenuUuid());
        adMenu = adMenuRepository.findOne(adMenu.getId());

        adBookmark.setMenuName(adMenu.getMenuName());
        adBookmark.setUserUuid(SessionUtils.getCurrentLoginUserUuid());

        if(StringUtils.isEmpty(adBookmark.getBookmarkUuid())) {
            adBookmark.setBookmarkUuid(UUIDUtils.getUUID());
            adBookmarkRepository.save(adBookmark);
        }else{
            adBookmarkRepository.delete(adBookmark.getId());
        }

        return getBookMark();
    }

    /**
     * Gets startup program.
     *
     * @return the startup program
     */
    public List<Ac00201VO> getBookMark(){
        Ac00201VO ac00201VO = new Ac00201VO();
        ac00201VO.setUserUuid(SessionUtils.getCurrentLoginUserUuid());

        return ac002Mapper.getBookmark(ac00201VO);
    }
}
