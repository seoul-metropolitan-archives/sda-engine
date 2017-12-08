package rmsoft.ams.seoul.common.vo;

import lombok.Data;

/**
 * The type Base column vo.
 */
@Data
public abstract class BaseColumnVO {
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
    private String treeRelationType = "";
    private String treeColumnYN = "N";

    /**
     * Sets required.
     *
     * @param required the required
     */
    public void setRequired(String required) {
        this.required = Boolean.parseBoolean(required);
    }
}
