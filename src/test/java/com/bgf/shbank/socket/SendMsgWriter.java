package com.bgf.shbank.socket;

import com.google.common.base.CaseFormat;
import io.onsemiro.utils.TemplateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.MessageFormat;

/**
 * Created by tw.jang on 2017-01-23.
 */
@Slf4j
public class SendMsgWriter {

    public static void write(String packageName, SocketMsgTemplate socketMsgTemplate) {

        String localMembers = buildLocalMembers(socketMsgTemplate);

        String setterMethods = buildSetterMethods(socketMsgTemplate);

        String className = socketMsgTemplate.getName().replace("Atms", "");

        className = "Sh" + className.substring(className.length()-8, className.length()) + "SendMsg";

        String templateCode = buildTemplateCode(packageName, className, localMembers, setterMethods);

        try {
            TemplateUtils.write(SocketMsgUtils.getOutputDir(packageName) + className + ".java", templateCode);
        } catch (IOException e) {
            log.error("SendMsgWriter-write :: {}", e.getMessage());
        }
    }

    private static String buildLocalMembers(SocketMsgTemplate socketMsgTemplate) {

        StringBuilder nodeBuilder = new StringBuilder();

        String comments = "/* {0} [{1}] */";
        String template = "private byte[] {0};";

        socketMsgTemplate.getSocketMsgNodes().stream().forEach(msgNode -> {
            nodeBuilder.append("\t" + MessageFormat.format(comments, msgNode.getName(), msgNode.getAttr() + String.valueOf(msgNode.getSize())) + "\r\n");
            nodeBuilder.append("\t" + MessageFormat.format(template, msgNode.getLocalMember()) + "\r\n\r\n");
        });

        return nodeBuilder.toString();
    }

    private static String buildSetterMethods(SocketMsgTemplate socketMsgTemplate) {

        StringBuilder resultBuilder = new StringBuilder();

        socketMsgTemplate.getSocketMsgNodes().stream().forEach(msgNode -> {

            StringBuilder templateBuilder = new StringBuilder();

            if (StringUtils.equals(msgNode.getAttr(), "AN") || StringUtils.equals(msgNode.getAttr(), "C")
                    || StringUtils.equals(msgNode.getAttr(), "A")) {
                templateBuilder.append("\tvoid set@{LOCAL_MEMBER_UPPER}(String @{LOCAL_MEMBER}) {\r\n");
                templateBuilder.append("\t\t@{LOCAL_MEMBER} = StringUtils.rightPad(@{LOCAL_MEMBER}, @{SIZE}, ' ');\r\n");
                templateBuilder.append("\t\tthis.@{LOCAL_MEMBER} = @{LOCAL_MEMBER}.getBytes(CharsetUtil.UTF_8);\r\n");
            } else if (StringUtils.equals(msgNode.getAttr(), "N")) {
                templateBuilder.append("\tvoid set@{LOCAL_MEMBER_UPPER}(int @{LOCAL_MEMBER}) {\r\n");
                templateBuilder.append("\t\tString value = StringUtils.leftPad(String.valueOf(@{LOCAL_MEMBER}), @{SIZE}, '0');\r\n");
                templateBuilder.append("\t\tthis.@{LOCAL_MEMBER} = value.getBytes(CharsetUtil.UTF_8);\r\n");
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

        String template = SocketMsgTemplateManager.getTemplate(TemplateType.SEND_MSG);

        return template.replace("@{PACKAGE_NAME}", packageName)
                       .replace("@{CLASS_NAME}", className)
                       .replace("@{LOCAL_MEMBERS}", localMembers)
                       .replace("@{SETTER_METHODS}", setterMethods);
    }

}
