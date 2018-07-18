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
public class SendMsgEncoderWriter {

    /**
     * Write.
     *
     * @param packageName       the package name
     * @param socketMsgTemplate the socket msg template
     */
    public static void write(String packageName, SocketMsgTemplate socketMsgTemplate) {

        String contents = buildContents(socketMsgTemplate);

        String baseName = socketMsgTemplate.getName().replace("Atms", "");
        baseName = "Sh" + baseName.substring(baseName.length()-8, baseName.length());

        String className = baseName + "SendMsgEncoder";
        String sendMsg = baseName + "SendMsg";

        String templateCode = buildTemplateCode(packageName, className, sendMsg, contents);

        try {
            TemplateUtils.write(SocketMsgUtils.getOutputDir(packageName) + className + ".java", templateCode);
        } catch (IOException e) {
            log.error("SendMsgEncoderWriter-write :: {}", e.getMessage());
        }
    }

    private static String buildContents(SocketMsgTemplate socketMsgTemplate) {

        String comments = "/* {0} [{1}] */";

        String template = "\t\t@{COMMENT}\r\n" + "\t\tout.writeBytes(msg.get@{LOCAL_MEMBER}());\r\n";

        StringBuilder contentsBuilder = new StringBuilder();

        socketMsgTemplate.getSocketMsgNodes().stream().forEach(msgNode -> {
            String contents = template
                                .replace("@{COMMENT}", MessageFormat.format(comments, msgNode.getName(), msgNode.getAttr() + String.valueOf(msgNode.getSize())))
                                .replace("@{LOCAL_MEMBER}", CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, msgNode.getLocalMember()));
            contentsBuilder.append(contents);
        });

        return StringUtils.removeEnd(contentsBuilder.toString(), "\r\n\r\n");
    }

    private static String buildTemplateCode(String packageName, String className, String sendMsg, String contents) {

        String template = SocketMsgTemplateManager.getTemplate(TemplateType.SEND_MSG_ENCODER);

        return template.replace("@{PACKAGE_NAME}", packageName)
                       .replace("@{CLASS_NAME}", className)
                       .replace("@{SEND_MSG}", sendMsg)
                       .replace("@{CONTENTS}", contents);
    }

}
