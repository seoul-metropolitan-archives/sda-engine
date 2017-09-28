package rmsoft.ams.seoul.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by james on 2017-01-23.
 */
@Slf4j
public class SocketMsgJsonParser {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private static ModelMapper modelMapper = new ModelMapper();

    public static SocketMsgTemplate parse(String jsonFile) {

        Map<String, Object> tempJsonTemplate = null;

        try {
            tempJsonTemplate  = objectMapper.readValue(new File(jsonFile), Map.class);
        } catch (IOException e) {
            log.error("SocketMsgJsonParser-parse :: {}", e.getMessage());
        }

        SocketMsgTemplate socketMsgTemplate = new SocketMsgTemplate();

        List<Map<String, Object>> socketMsgNodeList = new ArrayList<>();

        tempJsonTemplate.entrySet().forEach(it -> {
            socketMsgTemplate.setName(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, it.getKey()));
            socketMsgNodeList.addAll((List<Map<String, Object>>) it.getValue());
        });

        SocketMsgNode[] socketMsgNodes = new SocketMsgNode[socketMsgNodeList.size()];

        socketMsgNodeList.forEach(socketMsgNode -> {
            SocketMsgNode msgNode = modelMapper.map(socketMsgNode, SocketMsgNode.class);
            socketMsgNodes[msgNode.getNo()-1] = msgNode;
        });

        socketMsgTemplate.setSocketMsgNodes(Lists.newArrayList(socketMsgNodes));

        return socketMsgTemplate;
    }

}
