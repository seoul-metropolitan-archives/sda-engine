package rmsoft.ams.seoul.socket;

import com.google.common.base.CaseFormat;
import lombok.Data;

/**
 * Created by james on 2017-01-23.
 */
@Data
public class SocketMsgNode {

    private int no;

    private String name;

    private String attr;

    private String localMember;

    private String defaultValue;

    private int size;

    /**
     * Sets local member.
     *
     * @param localMember the local member
     */
    public void setLocalMember(String localMember) {
        this.localMember = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, localMember);
    }

}
