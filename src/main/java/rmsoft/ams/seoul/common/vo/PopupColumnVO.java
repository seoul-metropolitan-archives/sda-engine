/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.vo;

import lombok.Data;

@Data
public class PopupColumnVO extends BaseColumnVO {
    private String popupCode = "";
    private String sqlColumn = "";
    private boolean editable = true;
}
