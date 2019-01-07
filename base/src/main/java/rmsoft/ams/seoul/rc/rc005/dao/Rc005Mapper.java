package rmsoft.ams.seoul.rc.rc005.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.rc.rc005.vo.*;

import java.util.List;

/**
 * The interface Rc 005 mapper.
 */
public interface Rc005Mapper extends MyBatisMapper {
    /**
     * Gets record item list.
     *
     * @param rc00501VO the rc 00501 vo
     * @return the record item list
     */
    List<Rc00501VO> getRecordItemList(Rc00501VO rc00501VO);

    /**
     * Gets record component list.
     *
     * @param rc00502VO the rc 00502 vo
     * @return the record component list
     */
    List<Rc00502VO> getRecordComponentList(Rc00502VO rc00502VO);

    /**
     * Gets creator list.
     *
     * @param itemUuid the item uuid
     * @return the creator list
     */
    List<Rc00503VO> getCreatorList(String itemUuid);

    /**
     * Gets related authority list.
     *
     * @param itemUuid the item uuid
     * @return the related authority list
     */
    List<Rc00505VO> getRelatedAuthorityList(String itemUuid);

    /**
     * Merge insert int.
     *
     * @param rc00507VO the rc 00507 vo
     * @return the int
     */
    int mergeInsert(Rc00507VO rc00507VO);

    /**
     * Gets job status.
     *
     * @param jobId the job id
     * @return the job status
     */
    String getJobStatus(String jobId);
}
