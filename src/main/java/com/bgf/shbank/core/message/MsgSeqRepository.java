package com.bgf.shbank.core.message;

import io.onsemiro.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by manasobi on 2017-02-26.
 */
@Repository
public interface MsgSeqRepository extends AXBootJPAQueryDSLRepository<MsgSeq, MsgSeq.MsgSeqId> {

}
