package com.bgf.shbank.domain.mng.etc.sh04001180;

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
public class Sh04001180VO extends BaseVO {

	private String txId;

	private String jisaCode;
    private String jisaCodeName;

	private String branchCode;
    private String branchCodeName;

	private String cornerCode;

	private String crtNo;


    public static Sh04001180VO of(Sh04001180 sh04001180) {
        BoundMapperFacade<Sh04001180, Sh04001180VO> mapper =
                ModelMapperUtils.getMapper("Sh04001180", Sh04001180VO.class.getPackage().getName());
        return mapper.map(sh04001180);
    }

    public static List<Sh04001180VO> of(List<Sh04001180> sh04001180List) {
        return sh04001180List.stream().map(sh04001180 -> of(sh04001180)).collect(toList());
    }

    public static List<Sh04001180VO> of(Page<Sh04001180> sh04001180Page) {
        return sh04001180Page.getContent().stream().map(sh04001180 -> of(sh04001180)).collect(toList());
    }
}