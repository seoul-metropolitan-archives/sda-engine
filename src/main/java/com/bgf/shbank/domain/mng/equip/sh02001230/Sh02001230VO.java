package com.bgf.shbank.domain.mng.equip.sh02001230;

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
public class Sh02001230VO extends BaseVO {

	private String txId;

	private String changeBeforeAssetSeqNo;

	private String changeAfterAssetSeqNo;

	private String jisaCode;

	private String branchCode;

	private String cornerCode;

	private String changeDatetime;

    private String changeDate;

    private String changeTime;

    private String stextSendGubun;

    public static Sh02001230VO of(Sh02001230 sh02001230) {

        BoundMapperFacade<Sh02001230, Sh02001230VO> mapper =
                ModelMapperUtils.getMapper("Sh02001230", Sh02001230VO.class.getPackage().getName());
        return mapper.map(sh02001230);
    }

    public static List<Sh02001230VO> of(List<Sh02001230> sh02001230List) {
        return sh02001230List.stream().map(sh02001230 -> of(sh02001230)).collect(toList());
    }

    public static List<Sh02001230VO> of(Page<Sh02001230> sh02001230Page) {
        return sh02001230Page.getContent().stream().map(sh02001230 -> of(sh02001230)).collect(toList());
    }
}