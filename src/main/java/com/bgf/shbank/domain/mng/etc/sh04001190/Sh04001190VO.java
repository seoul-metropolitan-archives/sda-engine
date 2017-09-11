package com.bgf.shbank.domain.mng.etc.sh04001190;

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
public class Sh04001190VO extends BaseVO {

	private String txId;

	private String noticeCode;

	private String noticeContent;


    public static Sh04001190VO of(Sh04001190 sh04001190) {
        BoundMapperFacade<Sh04001190, Sh04001190VO> mapper =
                ModelMapperUtils.getMapper("Sh04001190", Sh04001190VO.class.getPackage().getName());
        return mapper.map(sh04001190);
    }

    public static List<Sh04001190VO> of(List<Sh04001190> sh04001190List) {
        return sh04001190List.stream().map(sh04001190 -> of(sh04001190)).collect(toList());
    }

    public static List<Sh04001190VO> of(Page<Sh04001190> sh04001190Page) {
        return sh04001190Page.getContent().stream().map(sh04001190 -> of(sh04001190)).collect(toList());
    }
}