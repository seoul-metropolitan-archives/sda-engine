package rmsoft.ams.seoul.common.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Tree data vo.
 */
@Data
public class TreeDataVO {
    private int icon = 0;
    private String dataUuid = "";
    private String dataName = "";
    private List<TreeDataVO> children = new ArrayList<TreeDataVO>();

    /**
     * Add tree data vo.
     *
     * @param treeDataVO the tree data vo
     * @return the tree data vo
     */
    public TreeDataVO add(TreeDataVO treeDataVO) {
        children.add(treeDataVO);
        return this;
    }

    /**
     * Remove tree data vo.
     *
     * @param treeDataVO the tree data vo
     * @return the tree data vo
     */
    public TreeDataVO remove(TreeDataVO treeDataVO) {
        children.remove(treeDataVO);
        return this;
    }

    /**
     * Clear tree data vo.
     *
     * @return the tree data vo
     */
    public TreeDataVO clear() {
        children.clear();
        return this;
    }

}
