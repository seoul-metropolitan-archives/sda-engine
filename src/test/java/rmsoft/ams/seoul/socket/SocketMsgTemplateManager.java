package rmsoft.ams.seoul.socket;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by james on 2017-01-23.
 */
@Slf4j
public class SocketMsgTemplateManager {

    private static final String TEMPLATE_DIR = "/socket/msg/templates/";

    public static String getTemplate(String type) {

        try {
            switch (type) {
                case TemplateType.SEND_MSG:
                    return IOUtils.toString(new ClassPathResource(TEMPLATE_DIR + "SEND_MSG.tpl").getInputStream(), "UTF-8");
                case TemplateType.RECV_MSG:
                    return IOUtils.toString(new ClassPathResource(TEMPLATE_DIR + "RECV_MSG.tpl").getInputStream(), "UTF-8");
                case TemplateType.SEND_MSG_ENCODER:
                    return IOUtils.toString(new ClassPathResource(TEMPLATE_DIR + "SEND_MSG_ENCODER.tpl").getInputStream(), "UTF-8");
                case TemplateType.RECV_MSG_DECODER:
                    return IOUtils.toString(new ClassPathResource(TEMPLATE_DIR + "RECV_MSG_DECODER.tpl").getInputStream(), "UTF-8");
                case TemplateType.TASK_HANDLER:
                    return IOUtils.toString(new ClassPathResource(TEMPLATE_DIR + "TASK_HANDLER.tpl").getInputStream(), "UTF-8");
            }
        } catch (Exception e) {
            log.error("SocketMsgTemplateManager-getTemplate :: {}", e.getMessage());
        }

        return "";
    }
}
