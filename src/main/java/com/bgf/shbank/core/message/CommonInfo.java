package com.bgf.shbank.core.message;

import io.netty.util.CharsetUtil;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by manasobi on 2017-01-25.
 */
@Getter
public class CommonInfo {

    // [N4] 전문길이 - 공통전문 + 개별 전문
    private String length = "0490";
    //[AN2] 전문 종류 - EM: 장애관리, CM: 시재관리, SM: 설치관리, IQ: 운영, 기타
    private String formatType;
    // [N8] 거래일자
    private String txDate;
    // [N6] 거래시각
    private String txTime;
    //[AN7] 신한은행 전문일련번호
    private String shbankSn;
    //[AN7] 제휴기관 전문일련번호
    private String cooperationSn;
    // [N4] 전문코드
    private String msgType;
    // [N4] 업무구분
    private String txType;
    //[AN2] 응답코드
    private String resCode;
    // [N2] 제휴기관 번호
    private String cooperationNo;
    // [N1] 전문구분
    private String stextType;
    // [N1] 송신처구분
    private String senderType;
    //[AN42] FILLER
    private String filler = StringUtils.leftPad("", 42, " ");

    public void setFormatType(byte[] bytes) {
        this.formatType = new String(bytes, CharsetUtil.UTF_8);
    }

    public void setFormatTypeStr(String formatType) {
        this.formatType = formatType;
    }

    public void setTxDate(byte[] bytes) {
        this.txDate = new String(bytes, CharsetUtil.UTF_8);
    }

    public void setTxDateStr(String txDate) {
        this.txDate = txDate;
    }

    public void setTxTime(byte[] bytes) {
        this.txTime = new String(bytes, CharsetUtil.UTF_8);
    }

    public void setTxTimeStr(String txTime) {
        this.txTime = txTime;
    }

    public void setShbankSn(byte[] bytes) {
        this.shbankSn = new String(bytes, CharsetUtil.UTF_8);
    }

    public void setShbankSnStr(String shbankSn) {
        this.shbankSn = shbankSn;
    }

    public void setCooperationSn(byte[] bytes) {
        this.cooperationSn = new String(bytes, CharsetUtil.UTF_8);
    }

    public void setCooperationSnStr(String cooperationSn) {
        this.cooperationSn = cooperationSn;
    }

    public void setMsgType(byte[] bytes) {
        this.msgType = new String(bytes, CharsetUtil.UTF_8);
    }

    public void setMsgTypeStr(String msgType) {
        this.msgType = msgType;
    }

    public void setTxType(byte[] bytes) {
        this.txType = new String(bytes, CharsetUtil.UTF_8);
    }

    public void setTxTypeStr(String txType) {
        this.txType = txType;
    }

    public void setResCode(byte[] bytes) {
        this.resCode = new String(bytes, CharsetUtil.UTF_8);
    }

    public void setResCodeStr(String resCode) {
        this.resCode = resCode;
    }

    public void setCooperationNo(byte[] bytes) {
        this.cooperationNo = new String(bytes, CharsetUtil.UTF_8);
    }

    public void setCooperationNoStr(String cooperationNo) {
        this.cooperationNo = cooperationNo;
    }

    public void setStextType(byte[] bytes) {
        this.stextType = new String(bytes, CharsetUtil.UTF_8);
    }

    public void setStextTypeStr(String stextType) {
        this.stextType = stextType;
    }

    public void setSenderType(byte[] bytes) {
        this.senderType = new String(bytes, CharsetUtil.UTF_8);
    }

    public void setSenderTypeStr(String senderType) {
        this.senderType = senderType;
    }

    public void setResMsgType(String resMsgType) {
        this.msgType = resMsgType;
    }

    public void setCreateDate(String date) { this.txDate = date; }

    public void setCreateTime(String time) { this.txTime = time; }

    public void setErrorCode() { this.resCode = "99"; }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, SocketMsgToStringStyle.STYLE);
    }
}