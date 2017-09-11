package com.bgf.shbank.core.message;

import com.bgf.shbank.core.code.RemoteGateway;
import com.bgf.shbank.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

/**
 * Created by manasobi on 2017-02-26.
 */
@Component
public class MsgSeqHandler {

    @Autowired
    private MsgSeqRepository repo;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public String findMsgSeq(RemoteGateway remoteGateway) {

        String now = DateUtils.getNow("yyyyMMdd");

        Timestamp txDate = DateUtils.convertToTimestamp(now, "yyyyMMdd");

        MsgSeq.MsgSeqId msgSeqId = MsgSeq.MsgSeqId.of(txDate, remoteGateway.getCode());

        MsgSeq msgSeq = repo.findOne(msgSeqId);

        if (msgSeq == null) {
            msgSeq = new MsgSeq();
            msgSeq.setTxDate(txDate);
            msgSeq.setTarget(remoteGateway.getCode());
            msgSeq.setSeq(1);
        } else {
            msgSeq.setSeq(msgSeq.getSeq() + 1);
        }

        repo.save(msgSeq);

        return StringUtils.leftPad(String.valueOf(msgSeq.getSeq()), 7, "0");
    }

}
