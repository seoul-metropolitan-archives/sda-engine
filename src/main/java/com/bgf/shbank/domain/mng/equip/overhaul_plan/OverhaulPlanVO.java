package com.bgf.shbank.domain.mng.equip.overhaul_plan;

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
public class OverhaulPlanVO extends BaseVO {

	private String jisaCode;

	private String branchCode;

	private String cornerCode;

	private String overhaulDate;

	private String overhaulGubun;


    private String overhaulDateFrom;

    private String overhaulDateTo;


    public static OverhaulPlanVO of(OverhaulPlan overhaulPlan) {
        BoundMapperFacade<OverhaulPlan, OverhaulPlanVO> mapper =
                ModelMapperUtils.getMapper("OverhaulPlan", OverhaulPlanVO.class.getPackage().getName());
        return mapper.map(overhaulPlan);
    }

    public static List<OverhaulPlanVO> of(List<OverhaulPlan> overhaulPlanList) {
        return overhaulPlanList.stream().map(overhaulPlan -> of(overhaulPlan)).collect(toList());
    }

    public static List<OverhaulPlanVO> of(Page<OverhaulPlan> overhaulPlanPage) {
        return overhaulPlanPage.getContent().stream().map(overhaulPlan -> of(overhaulPlan)).collect(toList());
    }
}