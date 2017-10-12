package rmsoft.ams.seoul.ad.ad004.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import rmsoft.ams.seoul.ad.ad004.domain.Ad004_D;
import rmsoft.ams.seoul.ad.ad004.domain.Ad004_H;

import java.util.List;

@Mapper
public interface AD004Mapper extends MyBatisMapper
{
    @Select(" SELECT " +
            "   A.POPUP_HEADER_UUID popupHeaderUUID" +
            "   , A.POPUP_CODE popupCode"+
            "   , A.POPUP_NAME popupName"+
            "   , A.SERVICE_UUID serviceUUID"+
            "   , DECODE(A.MULTISELECT_YN,'Y','true','N','false') multiselectYN"+
            "   , A.DESCRIPTION description"+
            "   , A.NOTES notes"+
            "   , A.USE_YN useYN"+
            "   , (SELECT USER_NAME FROM AC_USER \"USER\" WHERE A.INSERT_UUID = \"USER\".USER_UUID ) insertUserName" +
            "   , TO_CHAR(A.INSERT_DATE,'yyyy-MM-dd hh:mm:ss') insertDate" +
            "   , A.UPDATE_UUID updateUUID" +
            "   , (SELECT USER_NAME FROM AC_USER \"USER\" WHERE A.UPDATE_UUID = \"USER\".USER_UUID ) updateUserName  " +
            "   , TO_CHAR(A.UPDATE_DATE,'yyyy-MM-dd hh:mm:ss') updateDate" +
            " FROM " +
            "   AD_POPUP_HEADER A" +
            "   ")
    public List<Ad004_H> searchPopupHeader(Ad004_H header);

    public List<Ad004_D> searchPopupDetail(Ad004_D header);

}
