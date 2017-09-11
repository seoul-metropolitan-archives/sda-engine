package com.bgf.shbank.domain.mng.cash.sh03001110;

import com.bgf.shbank.utils.ModelMapperUtils;
import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.glasnost.orika.BoundMapperFacade;
import org.springframework.data.domain.Page;

import java.util.List;

import static java.util.stream.Collectors.toList;


@Data
@NoArgsConstructor
public class Sh03001110VO extends BaseVO {

    private String txId;

    private String referDate;

    private String jisaCode;

    private String branchCode;

    private String terminalNo;

    private String cashWithdrawAmt;

    private String cashDepositAmt;

    private String cashAmt;

    private String checkWithdrawAmt;

    private String checkDepositAmt;

    private String checkAmt;

    private String checkGiveEnableCount;

    private String cash10kGiveEnableCount;

    private String cash50kGiveEnableCount;

    private String sijeConfirm;

    private String startDate;

    private String endDate;

    private String stextGubun;

    private String totalClassifyCode;

    private String errorType;

    private String openDatetime;

    private String stextSendGubun;

    public static Sh03001110VO of(Sh03001110 sh03001110) {
        BoundMapperFacade<Sh03001110, Sh03001110VO> mapper =
                ModelMapperUtils.getMapper("Sh03001110", Sh03001110VO.class.getPackage().getName());
        return mapper.map(sh03001110);
    }

    public static List<Sh03001110VO> of(List<Sh03001110> sh03001110List) {
        return sh03001110List.stream().map(sh03001110 -> of(sh03001110)).collect(toList());
    }

    public static List<Sh03001110VO> of(Page<Sh03001110> sh03001110Page) {
        return sh03001110Page.getContent().stream().map(sh03001110 -> of(sh03001110)).collect(toList());
    }
}