package rmsoft.ams.seoul.common.vo;

import lombok.Data;

/**
 * The type Tree column info.
 */
@Data
public class TreeColumnInfo {
    private String name = "";
    private String fieldName = "";
    private int width = 150;
    private boolean visible = false;
}
