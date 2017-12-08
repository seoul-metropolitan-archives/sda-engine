package rmsoft.ams.seoul.socket;

import io.onsemiro.utils.TemplateUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Created by james on 2017-01-23.
 */
@Slf4j
public class TaskHandlerWriter {

    /**
     * Write.
     *
     * @param packageName       the package name
     * @param socketMsgTemplate the socket msg template
     */
    public static void write(String packageName, SocketMsgTemplate socketMsgTemplate) {

        String className = socketMsgTemplate.getName().replace("Atms", "");

        className = "Sh" + className.substring(className.length()-8, className.length()) + "TaskHandler";

        String templateCode = buildTemplateCode(packageName, className);

        try {
            TemplateUtils.write(SocketMsgUtils.getOutputDir(packageName) + className + ".java", templateCode);
        } catch (IOException e) {
            log.error("TaskHandlerWriter-write :: {}", e.getMessage());
        }
    }

    private static String buildTemplateCode(String packageName, String className) {

        String template = SocketMsgTemplateManager.getTemplate(TemplateType.TASK_HANDLER);

        return template.replace("@{PACKAGE_NAME}", packageName)
                       .replace("@{CLASS_NAME}", className);
    }

}
