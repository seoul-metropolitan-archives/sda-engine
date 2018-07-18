package rmsoft.ams.seoul.df.df001.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.df.df001.vo.Df00101VO;
import rmsoft.ams.seoul.df.df001.vo.Df00102VO;

import java.util.List;


public interface Df001Mapper extends MyBatisMapper {

    /**
     * Search entity type list.
     *
     * @param param the param
     * @return the list
     */
    List<Df00101VO> searchList(Df00101VO param);

    /**
     * Gets classification scheme detail.
     *
     * @param param the cl 00102 vo
     * @return the classification scheme detail
     */
    Df00102VO getDetail(Df00102VO param);

    /**
     * Gets child class.
     *
     * @param disposalFreezeEventUuid the disposalFreezeEvent Uuid uuid
     * @return the child class
     */
    int checkDegree(String disposalFreezeEventUuid);
}