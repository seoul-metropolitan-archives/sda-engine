package com.bgf.shbank.domain.mng.equip.facility_status;

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
public class FacilityStatusVO extends BaseVO {

	private String jisaCode;
	private String jisaCodeName;

	private String branchCode;

	private String branchName;

	private String cornerCode;

	private String cornerName;

	private String facGubunCode;
	private String facGubunCodeName;

	private String facCode;
	private String facCodeName;

	private String operEnable;
	private String operEnableName;

	private String hireFacEnable;
	private String hireFacEnableName;

	private String installArticleGubun;
	private String installArticleGubunName;

	private String assetSeqNo;

	private String facIpAddr;

	private String facGwAddr;

	private String facSmaskAddr;

	private String hireFee;

	private String detailFacInfo;

	private String adoptDate;

	private String installDate;

	private String remarks;

	private String unitPrice;

    public static FacilityStatusVO of(FacilityStatus facilityStatus) {
		BoundMapperFacade<FacilityStatus, FacilityStatusVO> mapper =
				ModelMapperUtils.getMapper("FacilityStatus", FacilityStatusVO.class.getPackage().getName());
		FacilityStatusVO facilityStatusVO = mapper.map(facilityStatus);
		return facilityStatusVO;
    }

    public static List<FacilityStatusVO> of(List<FacilityStatus> facilityStatusList) {
        return facilityStatusList.stream().map(facilityStatus -> of(facilityStatus)).collect(toList());
    }

    public static List<FacilityStatusVO> of(Page<FacilityStatus> facilityStatusPage) {
        return facilityStatusPage.getContent().stream().map(facilityStatus -> of(facilityStatus)).collect(toList());
    }
}