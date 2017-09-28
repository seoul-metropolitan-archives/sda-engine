package rmsoft.ams.seoul.socket;

import com.google.common.base.CaseFormat;
import io.onsemiro.utils.TemplateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.MessageFormat;

/**
 * Created by james on 2017-01-23.
 */
@Slf4j
public class RecvMsgDecoderWriter {

    public static void write(String packageName, SocketMsgTemplate socketMsgTemplate) {

        String contents = buildContents(socketMsgTemplate);

        String baseName = socketMsgTemplate.getName().replace("Atms", "");
        baseName = "Sh" + baseName.substring(baseName.length()-8, baseName.length());

        String className = baseName + "RecvMsgDecoder";
        String recvMsg = baseName + "RecvMsg";

        String templateCode = buildTemplateCode(packageName, className, recvMsg, contents);

        try {
            TemplateUtils.write(SocketMsgUtils.getOutputDir(packageName) + className + ".java", templateCode);
        } catch (IOException e) {
            log.error("RecvMsgDecoderWriter-write :: {}", e.getMessage());
        }
    }

    private static String buildContents(SocketMsgTemplate socketMsgTemplate) {

        String comments = "/* {0} [{1}] */";

        StringBuilder templateBuilder = new StringBuilder();
        templateBuilder.append("\t\t@{COMMENT}\r\n");
        templateBuilder.append("\t\tbyteArray = new byte[@{SIZE}];\r\n");
        templateBuilder.append("\t\tin.readBytes(byteArray, 0, @{SIZE});\r\n");
        templateBuilder.append("\t\tmessage.set@{LOCAL_MEMBER_UPPER}(byteArray);\r\n\r\n");

        StringBuilder contentsBuilder = new StringBuilder();

        socketMsgTemplate.getSocketMsgNodes().stream().forEach(msgNode -> {
            String contents = templateBuilder.toString()
                                             .replace("@{COMMENT}", MessageFormat.format(comments, msgNode.getName(), msgNode.getAttr() + String.valueOf(msgNode.getSize())))
                                             .replace("@{SIZE}", String.valueOf(msgNode.getSize()))
                                             .replace("@{LOCAL_MEMBER_UPPER}", CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, msgNode.getLocalMember()))
                                             .replace("@{LOCAL_MEMBER}", String.valueOf(msgNode.getLocalMember()));
            contentsBuilder.append(contents);
        });

        return StringUtils.removeEnd(contentsBuilder.toString(), "\r\n\r\n");
    }

    private static String buildTemplateCode(String packageName, String className, String recvMsg, String contents) {

        String template = SocketMsgTemplateManager.getTemplate(TemplateType.RECV_MSG_DECODER);

        return template.replace("@{PACKAGE_NAME}", packageName)
                       .replace("@{CLASS_NAME}", className)
                       .replace("@{RECV_MSG}", recvMsg)
                       .replace("@{CONTENTS}", contents);
    }

}
