package rmsoft.ams.seoul.common.vo;

import java.util.ArrayList;
import java.util.List;

public class TreeDataVO {
    private int icon = 0;
    private String dataUuid = "";
    private String dataName = "";
    private List<TreeDataVO> children = new ArrayList<TreeDataVO>();

    public TreeDataVO add(TreeDataVO treeDataVO)
    {
        children.add(treeDataVO);
        return this;
    }

    public TreeDataVO remove(TreeDataVO treeDataVO)
    {
        children.remove(treeDataVO);
        return this;
    }
    public TreeDataVO clear()
    {
        children.clear();
        return this;
    }

}
