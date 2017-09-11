package com.bgf.shbank.domain.mng.cash.sh03001120.socket;

import com.bgf.shbank.core.TaskWorker;
import com.bgf.shbank.domain.mng.cash.sh03001120.Sh03001120;
import com.bgf.shbank.domain.mng.cash.sh03001120.Sh03001120VO;
import com.google.common.collect.Lists;
import io.onsemiro.utils.CommonCodeUtils;
import io.onsemiro.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Created by manasobi on 2017-01-25.
 */
@Slf4j
@Component
public class Sh03001120TaskHandler {


    public List<Sh03001120VO> process(Sh03001120 entity) {

        return doProcess(entity);
    }

    private List<Sh03001120VO> doProcess(Sh03001120 entity) {

        Sh03001120SendMsg sendMsg = TaskWorker.buildSendMsg(entity, Sh03001120SendMsg.class);

        try {

            List<Sh03001120RecvMsg> resultList = TaskWorker.sendMsgToRemoteGateway(sendMsg);

            return convertRecvMsgListToVOList(resultList, entity);

        } catch (Exception e) {

            log.error("Sh03001120TaskHandler-doProcess :: {}", e.getMessage());

            throw new RuntimeException(e.getMessage());
        }
    }

    private List<Sh03001120VO> convertRecvMsgListToVOList(List<Sh03001120RecvMsg> recvMsgList, Sh03001120 entity) {

        List<Sh03001120VO> resultList = Lists.newArrayList();

        recvMsgList.forEach(recvMsg -> {

            int size = Integer.parseInt(recvMsg.getResultSize());

            for (int i = 1; i <= size; i++) {
                Optional<Sh03001120VO> voUnit = convertRecvMsgToVO(recvMsg, entity, i);
                voUnit.ifPresent(e -> resultList.add(e));
            }
        });

        return resultList;
    }

    private Optional<Sh03001120VO> convertRecvMsgToVO(Sh03001120RecvMsg recvMsg, Sh03001120 entity, int index) {

        try {
            Field branchCodeField = ReflectionUtils.findField(recvMsg.getClass(), "branchCode");
            Field terminalNoField = ReflectionUtils.findField(recvMsg.getClass(), "terminalNo");
            Field referDateField = ReflectionUtils.findField(recvMsg.getClass(), "referDate");
            Field referStatementNoField = ReflectionUtils.findField(recvMsg.getClass(), "referStatementNo");
            Field referStartField = ReflectionUtils.findField(recvMsg.getClass(), "dealTimeReferStart");
            Field referEndField = ReflectionUtils.findField(recvMsg.getClass(), "dealTimeReferEnd");

            Field dealSeqNoField = ReflectionUtils.findField(recvMsg.getClass(), "dealSeqNo" + index);
            Field dealTimeField = ReflectionUtils.findField(recvMsg.getClass(), "dealTime" + index);
            Field processStatusField = ReflectionUtils.findField(recvMsg.getClass(), "processStatus" + index);
            Field locateGubunField = ReflectionUtils.findField(recvMsg.getClass(), "locateGubun" + index);
            Field dealMethodField = ReflectionUtils.findField(recvMsg.getClass(), "dealMethod" + index);
            Field depositBankCodeField = ReflectionUtils.findField(recvMsg.getClass(), "depositBankCode" + index);
            Field depositAccountNoField = ReflectionUtils.findField(recvMsg.getClass(), "depositAccountNo" + index);
            Field withdrawBankCodeField = ReflectionUtils.findField(recvMsg.getClass(), "withdrawBankCode" + index);
            Field withdrawAccountNoField = ReflectionUtils.findField(recvMsg.getClass(), "withdrawAccountNo" + index);
            Field dealAmtField = ReflectionUtils.findField(recvMsg.getClass(), "dealAmt" + index);

            ReflectionUtils.makeAccessible(branchCodeField);
            ReflectionUtils.makeAccessible(terminalNoField);
            ReflectionUtils.makeAccessible(referDateField);
            ReflectionUtils.makeAccessible(referStatementNoField);
            ReflectionUtils.makeAccessible(referStartField);
            ReflectionUtils.makeAccessible(referEndField);

            ReflectionUtils.makeAccessible(dealSeqNoField);
            ReflectionUtils.makeAccessible(dealTimeField);
            ReflectionUtils.makeAccessible(processStatusField);
            ReflectionUtils.makeAccessible(locateGubunField);
            ReflectionUtils.makeAccessible(dealMethodField);
            ReflectionUtils.makeAccessible(depositBankCodeField);
            ReflectionUtils.makeAccessible(depositAccountNoField);
            ReflectionUtils.makeAccessible(withdrawBankCodeField);
            ReflectionUtils.makeAccessible(withdrawAccountNoField);
            ReflectionUtils.makeAccessible(dealAmtField);

            LocalDateTime referDateTime  = DateUtils.convertToDateTime(referDateField.get(recvMsg).toString()+dealTimeField.get(recvMsg).toString(),"yyyyMMddHHmmss");

            Sh03001120VO sh03001120VO = new Sh03001120VO();
//            sh03001120VO.setJisaCode(entity.getJisaCode());
//            sh03001120VO.setBranchCode(branchCodeField.get(recvMsg).toString());
//            sh03001120VO.setTerminalNo(terminalNoField.get(recvMsg).toString());
//            sh03001120VO.setReferDate(DateUtils.convertToString(referDateTime, "yyyy-MM-dd"));
//            sh03001120VO.setReferStatementNo(referStatementNoField.get(recvMsg).toString());
//            sh03001120VO.setDealTimeReferStart(referStartField.get(recvMsg).toString());
//            sh03001120VO.setDealTimeReferEnd(referEndField.get(recvMsg).toString());


            sh03001120VO.setDealSeqNo(dealSeqNoField.get(recvMsg).toString());
            sh03001120VO.setDealTime(DateUtils.convertToString(referDateTime, "HH:mm:ss"));
            sh03001120VO.setProcessStatus(CommonCodeUtils.getName("PROCESS_STATUS",processStatusField.get(recvMsg).toString()));
            sh03001120VO.setLocateGubun(CommonCodeUtils.getName("LOCATE_GUBUN",locateGubunField.get(recvMsg).toString()));
            sh03001120VO.setDealMethod(CommonCodeUtils.getName("DEAL_METHOD",dealMethodField.get(recvMsg).toString()));
            sh03001120VO.setDepositBankCode(depositBankCodeField.get(recvMsg).toString());
            sh03001120VO.setDepositBankName(CommonCodeUtils.getName("BANK_CODE",depositBankCodeField.get(recvMsg).toString()));
            sh03001120VO.setDepositAccountNo(depositAccountNoField.get(recvMsg).toString());
            sh03001120VO.setWithdrawBankCode(withdrawBankCodeField.get(recvMsg).toString());
            sh03001120VO.setWithdrawBankName(CommonCodeUtils.getName("BANK_CODE",withdrawBankCodeField.get(recvMsg).toString()));
            sh03001120VO.setWithdrawAccountNo(withdrawAccountNoField.get(recvMsg).toString());
            sh03001120VO.setDealAmt(dealAmtField.get(recvMsg).toString());

            return Optional.of(sh03001120VO);

        } catch (IllegalAccessException e) {
            log.error("Sh03001120TaskHandler-convertRecvMsgToVO :: {}", e.getMessage());
            return null;
        }
    }

    private String convertAccountNo(String accountNo) {
        return "";
    }

}
