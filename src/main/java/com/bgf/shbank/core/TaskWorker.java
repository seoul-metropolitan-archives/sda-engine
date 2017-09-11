package com.bgf.shbank.core;

import com.bgf.shbank.core.code.JobStatus;
import com.bgf.shbank.core.code.RemoteGateway;
import com.bgf.shbank.core.message.CommonInfo;
import com.bgf.shbank.core.message.RecvSocketMsg;
import com.bgf.shbank.core.message.SendSocketMsg;
import com.bgf.shbank.domain.mng.cash.sh03001120.socket.Sh03001120RecvMsg;
import com.bgf.shbank.domain.mng.cash.sh03001120.socket.Sh03001120SendMsg;
import com.bgf.shbank.domain.mng.cash.sh03001120.socket.Sh03001120SendMsgEncoder;
import com.bgf.shbank.utils.ClassUtils;
import com.bgf.shbank.utils.DateUtils;
import com.bgf.shbank.utils.ModelMapperUtils;
import com.bgf.shbank.utils.TaskUtils;
import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.onsemiro.core.context.AppContextManager;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.BoundMapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

/**
 * Created by tw.jang on 2017-02-16.
 */
@Slf4j
public class TaskWorker {

    public static <T, R> R buildEntity(T t) {

        String beanName = t.getClass().getSimpleName().replace("RecvMsg", "");
        String packageName = t.getClass().getPackage().getName();

        BoundMapperFacade<T, R> mapper = ModelMapperUtils.getMapper(beanName, packageName);

        return mapper.map(t);
    }

    private static void buildCommonInfo(CommonInfo commonInfo, RemoteGateway remoteGateway) {

        String txId = commonInfo.getMsgType() + commonInfo.getTxType();

        setCommonInfoDetails(commonInfo, txId, remoteGateway);
    }

    private static <SendMsg> CommonInfo buildCommonInfo(SendMsg sendMsg, RemoteGateway remoteGateway) {

        CommonInfo commonInfo = new CommonInfo();

        String txId = sendMsg.getClass().getSimpleName().replace("Sh", "").replace("SendMsg", "");

        setCommonInfoDetails(commonInfo, txId, remoteGateway);

        return commonInfo;
    }

    private static void setCommonInfoDetails(CommonInfo commonInfo, String txId, RemoteGateway remoteGateway) {

        DateUtils.setCurrentDateTime(commonInfo);

        String msgType = txId.substring(0, 4);
        String txType = txId.substring(4);

        String formatType = "";

        if (msgType.startsWith("01")) {
            formatType = "EM";
        } else if (msgType.startsWith("02")) {
            formatType = "SM";
        } else if (msgType.startsWith("03")) {
            formatType = "CM";
        } else if (msgType.startsWith("04") || msgType.startsWith("05")) {
            formatType = "IQ";
        }

        //[AN2] 전문 종류 - EM: 장애관리, CM: 시재관리, SM: 설치관리, IQ: 운영, 기타
        commonInfo.setFormatTypeStr(formatType);
        //[AN7] 신한은행 전문일련번호
        if (StringUtils.isEmpty(commonInfo.getShbankSn())) {
            commonInfo.setShbankSnStr("0000000");
        }
        //[AN7] 제휴기관 전문일련번호
        commonInfo.setCooperationSnStr(TaskUtils.findCooperationSn(remoteGateway));
        //[ N4] 전문코드
        commonInfo.setMsgTypeStr(msgType);
        // [N4] 업무구분
        commonInfo.setTxTypeStr(txType);
        //[AN2] 응답코드
        if (StringUtils.isEmpty(commonInfo.getResCode())) {
            commonInfo.setResCodeStr("00");
        }
        // [N2] 제휴기관 번호
        commonInfo.setCooperationNoStr("26");
        // [N1] 전문구분
        commonInfo.setStextTypeStr(TaskUtils.findSTextType(txId)); // 0: 온라인   1: 배치
        // [N1] 송신처구분
        commonInfo.setSenderTypeStr(remoteGateway == RemoteGateway.SH_BANK ?  "1" : "0"); // 0: 신한은행 1: 업체
    }

    public static <R> Optional<R> buildRecvMsg(RecvSocketMsg recvSocketMsg) {

        Class decoderClass = ClassUtils.getClass(recvSocketMsg.getCommonInfo(), "RecvMsgDecoder");

        Method method = ReflectionUtils.findMethod(decoderClass, "decode", ByteBuf.class);

        Optional<R> result = null;

        try {
            result = Optional.of((R) ReflectionUtils.invokeMethod(method, decoderClass.newInstance(), recvSocketMsg.getDataBytes()));
        } catch (Exception e) {
            log.error("TaskWorker-buildRecvMsg :: {}", e.getMessage());
        }

        return result;
    }

    public static <Entity, SendMsg> SendMsg buildSendMsg(Entity entity, Class<SendMsg> sendMsgClass) {

        ModelMapper modelMapper = ModelMapperUtils.getModelMapper();

        return modelMapper.map(entity, sendMsgClass);
    }

    private static <SendMsg, RecvMsg> SendSocketMsg buildSendSocketMsg(CommonInfo commonInfo, RecvMsg recvMsg, Class<SendMsg> sendMsgClass) {

        DateUtils.setCurrentDateTime(commonInfo);

        SendSocketMsg sendSocketMsg = new SendSocketMsg();
        sendSocketMsg.setCommonInfo(commonInfo);

        SendMsg sendMsg = ModelMapperUtils.map(recvMsg, sendMsgClass);

        Class encoderClass = ClassUtils.getClass(commonInfo, "SendMsgEncoder");

        Method method = ReflectionUtils.findMethod(encoderClass, "encode", sendMsgClass);

        ByteBuf sendMsgBytes;

        try {
            sendMsgBytes = (ByteBuf) ReflectionUtils.invokeMethod(method, encoderClass.newInstance(), sendMsg);
        } catch (Exception e) {
            log.error("TaskWorker-buildSendSocketMsg :: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        sendSocketMsg.setDataBytes(sendMsgBytes);

        return sendSocketMsg;
    }

    public static <RecvMsg> void sendMsgToRemoteGateway(CommonInfo commonInfo, RecvMsg recvMsg, RemoteGateway remoteGateway) {

        buildCommonInfo(commonInfo, remoteGateway);

        Class sendMsgClass = ClassUtils.getClass(commonInfo, "SendMsg");

        SendSocketMsg sendSocketMsg = buildSendSocketMsg(commonInfo, recvMsg, sendMsgClass);

        MsgExchangeClient msgExchangeClient = AppContextManager.getBean(MsgExchangeClient.class);

        JobStatus jobStatus = msgExchangeClient.send(sendSocketMsg, remoteGateway);

        if (jobStatus == JobStatus.ERROR) {
            String errorMsg = "원격 서버와의 통신중에 오류가 발생하였습니다.";
            log.error("TaskWorker-sendMsgToRemoteGateway :: {}", errorMsg);

            throw new RuntimeException(errorMsg);
        }
    }

    public static <SendMsg> void sendMsgToRemoteGateway(SendMsg sendMsg, RemoteGateway remoteGateway) throws Exception {

        CommonInfo commonInfo = buildCommonInfo(sendMsg, remoteGateway);

        Class encoderClass = ClassUtils.getClass(commonInfo, "SendMsgEncoder");

        Method method = ReflectionUtils.findMethod(encoderClass, "encode", sendMsg.getClass());

        ByteBuf sendMsgBytes = null;

        try {
            sendMsgBytes = (ByteBuf) ReflectionUtils.invokeMethod(method, encoderClass.newInstance(), sendMsg);
        } catch (Exception e) {
            log.error("TaskWorker-buildSendSocketMsg :: {}", e.getMessage());
        }

        SendSocketMsg sendSocketMsg = new SendSocketMsg();

        sendSocketMsg.setCommonInfo(commonInfo);
        sendSocketMsg.setDataBytes(sendMsgBytes);

        MsgExchangeClient msgExchangeClient = AppContextManager.getBean(MsgExchangeClient.class);

        JobStatus jobStatus = msgExchangeClient.send(sendSocketMsg, remoteGateway);

        if (jobStatus == JobStatus.ERROR) {
            String errorMsg = "원격 서버와의 통신중에 오류가 발생하였습니다.";
            log.error("TaskWorker-sendMsgToRemoteGateway :: {}", errorMsg);

            throw new Exception(errorMsg);
        }
    }

    public static <SendMsg, RecvMsg> RecvMsg sendAndReceiveMsgToRemoteGateway(SendMsg sendMsg, RemoteGateway remoteGateway) throws Exception {

        CommonInfo commonInfo = buildCommonInfo(sendMsg, remoteGateway);

        Class encoderClass = ClassUtils.getClass(commonInfo, "SendMsgEncoder");

        Method method = ReflectionUtils.findMethod(encoderClass, "encode", sendMsg.getClass());

        ByteBuf sendMsgBytes = null;

        try {
            sendMsgBytes = (ByteBuf) ReflectionUtils.invokeMethod(method, encoderClass.newInstance(), sendMsg);
        } catch (Exception e) {
            log.error("TaskWorker-buildSendSocketMsg :: {}", e.getMessage());
        }

        SendSocketMsg sendSocketMsg = new SendSocketMsg();

        sendSocketMsg.setCommonInfo(commonInfo);
        sendSocketMsg.setDataBytes(sendMsgBytes);

        MsgExchangeClient msgExchangeClient = AppContextManager.getBean(MsgExchangeClient.class);

        return msgExchangeClient.sendAndReceive(sendSocketMsg, remoteGateway);
    }

    public static <RecvMsg, Result> Result sendAndReceiveMsgToRemoteGateway(CommonInfo commonInfo, RecvMsg recvMsg, RemoteGateway remoteGateway) {

        buildCommonInfo(commonInfo, remoteGateway);

        Class sendMsgClass = ClassUtils.getClass(commonInfo, "SendMsg");

        SendSocketMsg sendSocketMsg = buildSendSocketMsg(commonInfo, recvMsg, sendMsgClass);

        MsgExchangeClient msgExchangeClient = AppContextManager.getBean(MsgExchangeClient.class);

        return msgExchangeClient.sendAndReceive(sendSocketMsg, remoteGateway);
    }

    public static List<Sh03001120RecvMsg> sendMsgToRemoteGateway(Sh03001120SendMsg sendMsg) throws Exception {

        List<Sh03001120RecvMsg> resultList = Lists.newArrayList();

        CommonInfo commonInfo = buildCommonInfo(sendMsg, RemoteGateway.SH_BANK);

        ByteBuf sendMsgBytes = Sh03001120SendMsgEncoder.encode(sendMsg);

        SendSocketMsg sendSocketMsg = new SendSocketMsg();

        sendSocketMsg.setCommonInfo(commonInfo);
        sendSocketMsg.setDataBytes(sendMsgBytes);

        MsgExchangeClient msgExchangeClient = AppContextManager.getBean(MsgExchangeClient.class);

        Sh03001120RecvMsg recvMsg = msgExchangeClient.send(sendSocketMsg);

        if (recvMsg == null) {
            String errorMsg = "원격 서버와의 통신중에 오류가 발생하였습니다.";
            log.error("TaskWorker-sendMsgToRemoteGateway :: {}", errorMsg);

            throw new Exception(errorMsg);
        }

        resultList.add(recvMsg);

        if (recvMsg.getHasNext().equals("01")) {
            return resultList;
        }

        boolean hasNext = true;

        while (hasNext) {

            sendMsgBytes = Sh03001120SendMsgEncoder.encode(sendMsg);
            sendSocketMsg.setDataBytes(sendMsgBytes);

            recvMsg = msgExchangeClient.send(sendSocketMsg);

            resultList.add(recvMsg);

            if (recvMsg.getHasNext().equals("01")) {
                hasNext = false;
            }
        }

        return resultList;
    }

    public static <RecvMsg> void responseSocketMsg(ChannelHandlerContext ctx, CommonInfo commonInfo, RecvMsg recvMsg, RemoteGateway remoteGateway) {

        buildCommonInfo(commonInfo, remoteGateway);

        Class sendMsgClass = ClassUtils.getClass(commonInfo, "SendMsg");

        try {
            SendSocketMsg sendSocketMsg = buildSendSocketMsg(commonInfo, recvMsg, sendMsgClass);
            ctx.writeAndFlush(sendSocketMsg);
        } catch (Exception e) {
            log.error("TaskWorker-sendSocketMsg :: 전문 변환 작업 중에 내부 오류가 발생하였습니다. [{}]", e.getMessage());
        }
    }

}
