package com.bgf.shbank.domain.mng.error.sh01001240;

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
public class Sh01001240VO extends BaseVO {

	private String jisaCode;

	private String branchCode;

	private String cornerCode;

	private String terminalNo;

	private String handleSeqNo;

	private String handleDatetime;

	private String handleEmpName;

	private String handleEmpTelno;

	private String handleDesc;

	private String handleStatus;



    public static Sh01001240VO of(Sh01001240 sh01001240) {
        BoundMapperFacade<Sh01001240, Sh01001240VO> mapper =
                ModelMapperUtils.getMapper("Sh01001240", Sh01001240VO.class.getPackage().getName());
        return mapper.map(sh01001240);
    }

    public static List<Sh01001240VO> of(List<Sh01001240> sh01001240List) {
        return sh01001240List.stream().map(sh01001240 -> of(sh01001240)).collect(toList());
    }

    public static List<Sh01001240VO> of(Page<Sh01001240> sh01001240Page) {
        return sh01001240Page.getContent().stream().map(sh01001240 -> of(sh01001240)).collect(toList());
    }
}