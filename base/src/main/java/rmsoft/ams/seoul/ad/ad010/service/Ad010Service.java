package rmsoft.ams.seoul.ad.ad010.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.SessionUtils;
import io.onsemiro.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.ad.ad010.dao.Ad010Mapper;
import rmsoft.ams.seoul.ad.ad010.vo.Ad01001VO;
import rmsoft.ams.seoul.ad.ad010.vo.Ad01002VO;
import rmsoft.ams.seoul.common.domain.AdNotice;
import rmsoft.ams.seoul.common.repository.AdNoticeRepository;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class Ad010Service extends BaseService {
    @Inject
    private Ad010Mapper ad010Mapper;

    @Autowired
    private AdNoticeRepository adNoticeRepository;

    public Ad01001VO getList01() {
        return ad010Mapper.getList01();
    }

    public Page<Ad01002VO> getNoticeList(Pageable pageable) {
        return filter(ad010Mapper.getNoticeList(), pageable, "", Ad01002VO.class);
    }

    public ApiResponse saveNotice(Ad01002VO requestParam) {
        AdNotice adNotice = ModelMapperUtils.map(requestParam,AdNotice.class);// new AdNotice();

        if(null != adNotice.getNoticeUuid() && !"".equals(adNotice.getNoticeUuid())){//수정
            adNotice = adNoticeRepository.getOne(adNotice.getId());

            if(adNotice != null){
                adNotice.setContents(requestParam.getContents());
                adNotice.setFileName(requestParam.getFileName());
                adNotice.setFilePath(requestParam.getFilePath());
                adNotice.setTitle(requestParam.getTitle());
                adNotice.setRegisterDate(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));
                adNotice.setUpdateDate(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));
                adNotice.setUpdateUuid(SessionUtils.getCurrentLoginUserUuid());
                adNoticeRepository.save(adNotice);
            }
        }else{
            adNotice.setNoticeUuid(UUIDUtils.getUUID());
            adNotice.setRegisterUuid(SessionUtils.getCurrentLoginUserUuid());
            adNotice.setRegisterDate(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));
            adNoticeRepository.save(adNotice);
        }

        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    public ApiResponse deleteNotice(Ad01002VO requestParam) {
        AdNotice adNotice = ModelMapperUtils.map(requestParam,AdNotice.class);// new AdNotice();
        adNoticeRepository.delete(adNotice);
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }
}