package rmsoft.ams.seoul.st.st024.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.st.st024.vo.St02401VO;
import rmsoft.ams.seoul.st.st024.vo.St02402VO;
import rmsoft.ams.seoul.st.st024.vo.St02403VO;

import java.util.List;

public interface St024Mapper extends MyBatisMapper {
    List<St02401VO> getTagList(St02401VO st02401VO);

    List<St02402VO> getTagList02(St02402VO st02402VO);

    List<St02403VO> getTagList03(St02403VO st02403VO);
}
