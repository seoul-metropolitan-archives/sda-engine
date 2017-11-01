/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.ac.ac007.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Ac00702VO extends BaseVO {
    private String roleMenuUuid;

    private String roleUuid;

    private String menuUuid;

    private String parentMenuCode;

    private String menuCode;

    private String menuName;

    private String programUuid;

    private String programId;

    private String programName;

    private String parameter;

    private String allYn;

    private String useYn;

    private String saveYn;

    private String inquiryYn;

    private List<Ac00702VO> children = new ArrayList<>();

    public void addChildren(Ac00702VO menu) {
        children.add(menu);
    }
}