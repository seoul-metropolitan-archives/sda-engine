/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type Popup column vo.
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class PopupColumnVO extends BaseColumnVO {
    private String popupCode = "";
    private String sqlColumn = "";
    private boolean editable = true;
}
