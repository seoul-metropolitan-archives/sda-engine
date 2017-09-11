package com.bgf.shbank.domain.mng.cash.sh03001170;

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
public class Sh03001170VO extends BaseVO {

	private String txId;

    private String jisaCode;

	private String branchCode;

	private String terminalNo;

	private String cashSendingDate;

	private String cashSendingAmt;

	private String mngOffice;

	private String cash50kSendingAmt;

    private String jisaName;

    private String branchName;

    private String cornerName;

    public static Sh03001170VO of(Sh03001170 sh03001170) {
        BoundMapperFacade<Sh03001170, Sh03001170VO> mapper =
                ModelMapperUtils.getMapper("Sh03001170", Sh03001170VO.class.getPackage().getName());
        return mapper.map(sh03001170);
    }

    public static List<Sh03001170VO> of(List<Sh03001170> sh03001170List) {
        return sh03001170List.stream().map(sh03001170 -> of(sh03001170)).collect(toList());
    }

    public static List<Sh03001170VO> of(Page<Sh03001170> sh03001170Page) {
        return sh03001170Page.getContent().stream().map(sh03001170 -> of(sh03001170)).collect(toList());
    }
}