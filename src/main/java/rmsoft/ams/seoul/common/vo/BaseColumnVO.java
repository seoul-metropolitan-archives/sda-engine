package rmsoft.ams.seoul.common.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
@Setter
@Getter
public abstract class BaseColumnVO
{
    private int sortNo = 0;
    private String dataType = "";
    private String name = "";
    private String text = "";
    private int width = 0;
    private boolean editable = false;
    private boolean required = false;
    private boolean visible = true;
    private String textAlignment = "";
    private String defaultValue = "";

}
