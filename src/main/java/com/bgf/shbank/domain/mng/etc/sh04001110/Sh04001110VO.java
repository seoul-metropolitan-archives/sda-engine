package com.bgf.shbank.domain.mng.etc.sh04001110;

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
public class Sh04001110VO extends BaseVO {

    private String txId;

    private String reqStextDate;

    private String reqStextSeqNo;

    private String message;

    private String stextSendGubun;


    public static Sh04001110VO of(Sh04001110 sh04001110) {
        BoundMapperFacade<Sh04001110, Sh04001110VO> mapper =
                ModelMapperUtils.getMapper("Sh04001110", Sh04001110VO.class.getPackage().getName());
        return mapper.map(sh04001110);
    }

    public static List<Sh04001110VO> of(List<Sh04001110> sh04001110List) {
        return sh04001110List.stream().map(sh04001110 -> of(sh04001110)).collect(toList());
    }

    public static List<Sh04001110VO> of(Page<Sh04001110> sh04001110Page) {
        return sh04001110Page.getContent().stream().map(sh04001110 -> of(sh04001110)).collect(toList());
    }
}