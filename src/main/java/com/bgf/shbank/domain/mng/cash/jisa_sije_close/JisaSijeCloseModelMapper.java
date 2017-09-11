package com.bgf.shbank.domain.mng.cash.jisa_sije_close;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by jung-young-il on 16/02/2017.
 */
@Component
public class JisaSijeCloseModelMapper extends CustomMapper<JisaSijeClose, JisaSijeCloseVO> {

    @Override
    public void mapAtoB(JisaSijeClose src, JisaSijeCloseVO dest, MappingContext context) {

        LocalDateTime closeDate = src.getCloseDate().toLocalDateTime();
        dest.setCloseDate(DateUtils.convertToString(closeDate, "yyyy-MM-dd"));

        if(src.getTxId()!=null) {
            LocalDateTime txId = src.getTxId().toLocalDateTime();
            dest.setTxId(DateUtils.convertToString(txId, "yyyy-MM-dd HH:mm"));
        }

        if(src.getPrevDayReserveSije()==null) {
            dest.setPrevDayReserveSije("0");
        }

        if(src.getThisDayCashDepositAmt()==null) {
            dest.setThisDayCashDepositAmt("0");
        }

        if(src.getJisaToShinhanSendAmt()==null) {
            dest.setJisaToShinhanSendAmt("0");
        }

        if(src.getCloseAmt()==null) {
            dest.setCloseAmt("0");
        }

        if(src.getUnCheckAmt()==null) {
            dest.setUnCheckAmt("0");
        }

        if(src.getSijeMistakeAmt()==null) {
            dest.setSijeMistakeAmt("0");
        }

        if(src.getCashSendingAmt()==null) {
            dest.setCashSendingAmt("0");
        }

        if(src.getAddCashSendingAmt()==null) {
            dest.setAddCashSendingAmt("0");
        }

        if(src.getJisaSafeAmt()==null) {
            dest.setJisaSafeAmt("0");
        }

        Long jisaSafeAmt = Long.parseLong(dest.getPrevDayReserveSije()) + Long.parseLong(dest.getThisDayCashDepositAmt()) + Long.parseLong(dest.getJisaToShinhanSendAmt())
                + Long.parseLong(dest.getCloseAmt()) + Long.parseLong(dest.getUnCheckAmt()) + Long.parseLong(dest.getSijeMistakeAmt())
                - Long.parseLong(dest.getCashSendingAmt()) - Long.parseLong(dest.getAddCashSendingAmt());

        dest.setJisaSafeAmt(String.valueOf(jisaSafeAmt));
    }
}