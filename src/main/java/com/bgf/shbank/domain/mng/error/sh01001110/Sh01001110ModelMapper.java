package com.bgf.shbank.domain.mng.error.sh01001110;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by tw.jang on 2017-02-09.
 */
@Component
public class Sh01001110ModelMapper extends CustomMapper<Sh01001110, Sh01001110VO> {

    @Override
    public void mapAtoB(Sh01001110 src, Sh01001110VO dest, MappingContext context) {

        LocalDateTime errorDatetime = src.getErrorDatetime().toLocalDateTime();

        dest.setErrorDatetime(DateUtils.convertToString(errorDatetime, "yyyy-MM-dd HH:mm:ss"));
        dest.setErrorDate(DateUtils.convertToString(errorDatetime, "yyyy-MM-dd"));
        dest.setErrorTime(DateUtils.convertToString(errorDatetime, "HH:mm:ss"));

        // 개국일 정보는 null 값이 있을수 있음
        if (src.getErrorDatetime1() != null) {
            LocalDateTime errorDatetime1 = src.getErrorDatetime1().toLocalDateTime();
            dest.setErrorDatetime1(DateUtils.convertToString(errorDatetime1, "yyyy-MM-dd HH:mm:ss"));
            dest.setErrorDate1(DateUtils.convertToString(errorDatetime1, "yyyy-MM-dd"));
            dest.setErrorTime1(DateUtils.convertToString(errorDatetime1, "HH:mm:ss"));
        }

        dest.setSecurityCorp(StringUtils.trim(src.getSecurityCorp()));
        dest.setBoothType(StringUtils.trim(src.getBoothType()));

        if (!StringUtils.isEmpty(src.getTxId())) {
            if (src.getTxId().equals("9")) {
                if (StringUtils.isEmpty(src.getTotalClassifyCode())) {
                    dest.setTxId("상태통보");
                } else {
                    if (src.getTotalClassifyCode().equals("41") || src.getTotalClassifyCode().equals("45")) {
                        dest.setTxId("현금부족예보");
                    } else if (src.getTotalClassifyCode().equals("31") || src.getTotalClassifyCode().equals("35")) {
                        dest.setTxId("현금부족");
                    } else {
                        dest.setTxId("상태통보");
                    }
                }
            } else {
                String errorType = src.getTxId();
                String txStr = "";

                switch (errorType) {
                    case "1":
                        txStr = "장애(1차)";
                        break;
                    case "2":
                        txStr = "장애(2차)";
                        break;
                    case "3":
                        txStr = "현금부족예보";
                        break;
                    case "4":
                        txStr = "현금부족";
                        break;
                    case "5":
                        txStr = "자체출동";
                        break;
                    case "6":
                        txStr = "민원(점검)";
                        break;
                    default:
                        txStr = "상태";
                        break;
                }

                dest.setTxId(txStr);
            }
        }
    }
}

