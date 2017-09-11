package com.bgf.shbank.domain.mng.cash.sh03001140;

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
public class Sh03001140VO extends BaseVO {

    private String jisaCode;

    private String branchCode;

    private String terminalNo;

    private String closeDate;

    private String prevDayCashSendingAmt;

    private String depositAmt;

    private String giveAmt;

    private String closeAmt;

    private String noneProcessAmt;

    private String rtrvlFund;

    private String noneProcessAt;

    private String mngOffice;

    private String adjustLackAmtCount;

    private String adjustLackAmt;

    private String stextSendGubun;

    private String memoContent;


    public static Sh03001140VO of(Sh03001140 sh03001140) {
        BoundMapperFacade<Sh03001140, Sh03001140VO> mapper =
                ModelMapperUtils.getMapper("Sh03001140", Sh03001140VO.class.getPackage().getName());
        return mapper.map(sh03001140);
    }

    public static List<Sh03001140VO> of(List<Sh03001140> sh03001140List) {
        return sh03001140List.stream().map(sh03001140 -> of(sh03001140)).collect(toList());
    }

    public static List<Sh03001140VO> of(Page<Sh03001140> sh03001140Page) {
        return sh03001140Page.getContent().stream().map(sh03001140 -> of(sh03001140)).collect(toList());
    }
}