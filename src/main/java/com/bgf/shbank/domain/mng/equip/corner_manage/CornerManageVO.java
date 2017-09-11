package com.bgf.shbank.domain.mng.equip.corner_manage;

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
public class CornerManageVO extends BaseVO {

	private String jisaCode;

	private String branchCode;

	private String branchName;

	private String cornerCode;

	private String cornerName;

	private String placeGubun;

	private String branchGubun;

	private String specialStyleGubun;

	private String operTimeGubun;

	private String operStartTime;

	private String operEndTime;

	private String checkOperEnable;

	private String seviceFee;

	private String securityCorpCode;

	private String facHireEnable;

	private String addr;

	private String unusl;

	private String operDay;


	public static CornerManageVO of(CornerManage cornerManage) {
		BoundMapperFacade<CornerManage, CornerManageVO> mapper =
				ModelMapperUtils.getMapper("CornerManage", CornerManageVO.class.getPackage().getName());

		CornerManageVO cornerManageVO = mapper.map(cornerManage);
		return cornerManageVO;
	}

    public static List<CornerManageVO> of(List<CornerManage> cornerManageList) {
        return cornerManageList.stream().map(cornerManage -> of(cornerManage)).collect(toList());
    }

    public static List<CornerManageVO> of(Page<CornerManage> cornerManagePage) {
        return cornerManagePage.getContent().stream().map(cornerManage -> of(cornerManage)).collect(toList());
    }
}