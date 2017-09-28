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
public class RecvMsgWriter {

    public static void write(String packageName, SocketMsgTemplate socketMsgTemplate) {

        String localMembers = buildLocalMembers(socketMsgTemplate);

        String setterMethods = buildSetterMethods(socketMsgTemplate);

        String className = socketMsgTemplate.getName().replace("Atms", "");

        className = "Sh" + className.substring(className.length()-8, className.length()) + "RecvMsg";

        String templateCode = buildTemplateCode(packageName, className, localMembers, setterMethods);

        try {
            TemplateUtils.write(SocketMsgUtils.getOutputDir(packageName) + className + ".java", templateCode);
        } catch (IOException e) {
            log.error("RecvMsgWriter-write :: {}", e.getMessage());
        }
    }

    private static String buildLocalMembers(SocketMsgTemplate socketMsgTemplate) {

        StringBuilder nodeBuilder = new StringBuilder();

        String comments = "/* {0} [{1}] */";
        String template = "private {0} {1};";

        socketMsgTemplate.getSocketMsgNodes().stream().forEach(msgNode -> {
            nodeBuilder.append("\t" + MessageFormat.format(comments, msgNode.getName(), msgNode.getAttr() + String.valueOf(msgNode.getSize())) + "\r\n");

            if (StringUtils.equals(msgNode.getAttr(), "AN") || StringUtils.equals(msgNode.getAttr(), "C")
                    || StringUtils.equals(msgNode.getAttr(), "A")) {
                nodeBuilder.append("\t" + MessageFormat.format(template, "String", msgNode.getLocalMember()) + "\r\n\r\n");
            } else if (StringUtils.equals(msgNode.getAttr(), "N")) {
                nodeBuilder.append("\t" + MessageFormat.format(template, "int", msgNode.getLocalMember()) + "\r\n\r\n");
            }
        });

        return nodeBuilder.toString();
    }

    private static String buildSetterMethods(SocketMsgTemplate socketMsgTemplate) {

        StringBuilder resultBuilder = new StringBuilder();

        socketMsgTemplate.getSocketMsgNodes().stream().forEach(msgNode -> {

            StringBuilder templateBuilder = new StringBuilder();

            templateBuilder.append("\tvoid set@{LOCAL_MEMBER_UPPER}(byte[] bytes) {\r\n");

            if (StringUtils.equals(msgNode.getAttr(), "AN") || StringUtils.equals(msgNode.getAttr(), "C")) {
                templateBuilder.append("\t\tthis.@{LOCAL_MEMBER} = new String(bytes, CharsetUtil.UTF_8);\r\n");
                templateBuilder.append("\t\tthis.@{LOCAL_MEMBER} = StringUtils.trim(this.@{LOCAL_MEMBER});\r\n");
            } else if (StringUtils.equals(msgNode.getAttr(), "N")) {
                templateBuilder.append("\t\tString value = new String(bytes, CharsetUtil.UTF_8);\r\n");
                templateBuilder.append("\t\tthis.@{LOCAL_MEMBER} = Integer.valueOf(value);\r\n");
            }

            templateBuilder.append("\t}\r\n\r\n");

            String template = templateBuilder.toString()
                    .replace("@{LOCAL_MEMBER_UPPER}", CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, msgNode.getLocalMember()))
                    .replace("@{LOCAL_MEMBER}", msgNode.getLocalMember())
                    .replace("@{SIZE}", String.valueOf(msgNode.getSize()));

            resultBuilder.append(template);

        });

        return StringUtils.removeEnd(resultBuilder.toString(), "\r\n");
    }

    private static String buildTemplateCode(String packageName, String className, String localMembers, String setterMethods) {

        String template = SocketMsgTemplateManager.getTemplate(TemplateType.RECV_MSG);

        return template.replace("@{PACKAGE_NAME}", packageName)
                       .replace("@{CLASS_NAME}", className)
                       .replace("@{LOCAL_MEMBERS}", localMembers)
                       .replace("@{SETTER_METHODS}", setterMethods);
    }

}
