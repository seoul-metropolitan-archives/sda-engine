package rmsoft.ams.seoul.socket;

import lombok.Data;

import java.util.List;

/**
 * Created by james on 2017-01-23.
 */
@Data
public class SocketMsgTemplate {

    private String name;

    private List<SocketMsgNode> socketMsgNodes;

}
