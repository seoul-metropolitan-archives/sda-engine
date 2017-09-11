package com.bgf.shbank.domain.mng.error.error_handle_mng;

import com.bgf.shbank.utils.ModelMapperUtils;
import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

import static java.util.stream.Collectors.toList;


@Data
@NoArgsConstructor
public class ErrorHandleMngVO extends BaseVO {

	private String jisaCode;

	private String branchCode;

	private String cornerCode;

	private String terminalNo;

	private String errorDatetime;

	private String noticeContent;

	private String customerInfo;

	private String handleContent;

	private String lastModifyDatetime;

	private String lastModifyEmpName;


    public static ErrorHandleMngVO of(ErrorHandleMng errorHandleMng) {
        ErrorHandleMngVO errorHandleMngVO = ModelMapperUtils.map(errorHandleMng, ErrorHandleMngVO.class);
        return errorHandleMngVO;
    }

    public static List<ErrorHandleMngVO> of(List<ErrorHandleMng> errorHandleMngList) {
        return errorHandleMngList.stream().map(errorHandleMng -> of(errorHandleMng)).collect(toList());
    }

    public static List<ErrorHandleMngVO> of(Page<ErrorHandleMng> errorHandleMngPage) {
        return errorHandleMngPage.getContent().stream().map(errorHandleMng -> of(errorHandleMng)).collect(toList());
    }
}