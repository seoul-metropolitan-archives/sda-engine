package com.bgf.shbank.domain.mng.cash.sh03001200;

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
public class Sh03001200VO extends BaseVO {

	private String txId;

	private String jisaCode;

	private String branchCode;

	private String terminalNo;

	private String monCashSendingEnable;

	private String tueCashSendingEnable;

	private String wedCashSendingEnable;

	private String thuCashSendingEnable;

	private String friCashSendingEnable;


    public static Sh03001200VO of(Sh03001200 sh03001200) {
        BoundMapperFacade<Sh03001200, Sh03001200VO> mapper =
                ModelMapperUtils.getMapper("Sh03001200", Sh03001200VO.class.getPackage().getName());
        return mapper.map(sh03001200);
    }

    public static List<Sh03001200VO> of(List<Sh03001200> sh03001200List) {
        return sh03001200List.stream().map(sh03001200 -> of(sh03001200)).collect(toList());
    }

    public static List<Sh03001200VO> of(Page<Sh03001200> sh03001200Page) {
        return sh03001200Page.getContent().stream().map(sh03001200 -> of(sh03001200)).collect(toList());
    }
}