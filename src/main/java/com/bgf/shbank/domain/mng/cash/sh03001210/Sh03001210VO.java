package com.bgf.shbank.domain.mng.cash.sh03001210;

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
public class Sh03001210VO extends BaseVO {

	private String txId;

	private String jisaCode;

	private String branchCode;

	private String terminalNo;


    public static Sh03001210VO of(Sh03001210 sh03001210) {
        BoundMapperFacade<Sh03001210, Sh03001210VO> mapper =
                ModelMapperUtils.getMapper("Sh03001210", Sh03001210VO.class.getPackage().getName());
        return mapper.map(sh03001210);
    }

    public static List<Sh03001210VO> of(List<Sh03001210> sh03001210List) {
        return sh03001210List.stream().map(sh03001210 -> of(sh03001210)).collect(toList());
    }

    public static List<Sh03001210VO> of(Page<Sh03001210> sh03001210Page) {
        return sh03001210Page.getContent().stream().map(sh03001210 -> of(sh03001210)).collect(toList());
    }
}