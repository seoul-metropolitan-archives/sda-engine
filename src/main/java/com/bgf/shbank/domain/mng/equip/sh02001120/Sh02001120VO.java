package com.bgf.shbank.domain.mng.equip.sh02001120;

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
public class Sh02001120VO extends BaseVO {

	private String workSeqNo;

	private String changeChasu;

	private String stextGubun;

	private String closingBranchNo;

	private String closingCornerCode;

	private String addr;

	private String unusl;



    public static Sh02001120VO of(Sh02001120 sh02001120) {
        BoundMapperFacade<Sh02001120, Sh02001120VO> mapper =
                ModelMapperUtils.getMapper("Sh02001120", Sh02001120VO.class.getPackage().getName());
        return mapper.map(sh02001120);
    }


    public static List<Sh02001120VO> of(List<Sh02001120> sh02001120List) {
        return sh02001120List.stream().map(sh02001120 -> of(sh02001120)).collect(toList());
    }

    public static List<Sh02001120VO> of(Page<Sh02001120> sh02001120Page) {
        return sh02001120Page.getContent().stream().map(sh02001120 -> of(sh02001120)).collect(toList());
    }
}