package rmsoft.ams.seoul.socket;

/**
 * Created by james on 2017-01-23.
 */
public class SocketMsgResourcesGenerator {

    private static final String buildPackageName(String jsonFileName) {

        String[] fileNameUnits = jsonFileName.split("_");

        String packageName = "rmsoft.ams.seoul.domain.";

        packageName += fileNameUnits[1].toLowerCase() + "." + fileNameUnits[2].toLowerCase() + ".sh" + fileNameUnits[3] + ".socket";
        packageName = packageName.replace(".json", "");

        return packageName;
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {

        if (GENERATE_JSON_TEMPLATE) {
            JsonTemplateGenerator.generate(JSON_FILE);
        }

        String jsonPath = SocketMsgUtils.getTemplateJsonDir() + JSON_FILE;

        SocketMsgTemplate socketMsgTemplate = SocketMsgJsonParser.parse(jsonPath);

        String packageName = buildPackageName(JSON_FILE);

        SendMsgWriter.write(packageName, socketMsgTemplate);

        RecvMsgWriter.write(packageName, socketMsgTemplate);

        RecvMsgDecoderWriter.write(packageName, socketMsgTemplate);

        SendMsgEncoderWriter.write(packageName, socketMsgTemplate);

        TaskHandlerWriter.write(packageName, socketMsgTemplate);
    }

    private static final String JSON_FILE = "ATMS_MNG_ERROR_01001110.json";

    private static final boolean GENERATE_JSON_TEMPLATE = true;

}
