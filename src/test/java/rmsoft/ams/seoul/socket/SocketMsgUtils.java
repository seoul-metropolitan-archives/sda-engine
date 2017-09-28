package rmsoft.ams.seoul.socket;

import java.io.File;

/**
 * Created by james on 2017-01-23.
 */
public class SocketMsgUtils {

    private static final String SEPARATOR = File.separator;

    private static String getBaseDir() {

        StringBuilder baseDir = new StringBuilder();
        baseDir.append(System.getProperty("user.dir"));
        baseDir.append(SEPARATOR);

        return baseDir.toString();
    }

    public static String getTestResourcesDir() {

        StringBuilder jsonDir = new StringBuilder();
        jsonDir.append("src" + SEPARATOR + "test" + SEPARATOR);
        jsonDir.append("resources");
        jsonDir.append(SEPARATOR);

        return getBaseDir() + jsonDir.toString();
    }

    public static String getDbDatesetDir() {

        StringBuilder dir = new StringBuilder();
        dir.append("db" + SEPARATOR + "dataset" + SEPARATOR);

        return getTestResourcesDir() + dir.toString();
    }

    public static String getTemplateJsonDir() {

        StringBuilder jsonDir = new StringBuilder();
        jsonDir.append("src" + SEPARATOR + "test" + SEPARATOR);
        jsonDir.append("resources");
        jsonDir.append(SEPARATOR);
        jsonDir.append("socket" + SEPARATOR + "msg" + SEPARATOR + "format" + SEPARATOR);

        return getBaseDir() + jsonDir.toString();
    }

    public static String getTemplateExcelDir() {

        StringBuilder jsonDir = new StringBuilder();
        jsonDir.append("src" + SEPARATOR + "test" + SEPARATOR);
        jsonDir.append("resources");
        jsonDir.append(SEPARATOR);
        jsonDir.append("socket" + SEPARATOR + "msg" + SEPARATOR + "resources" + SEPARATOR + "excel" + SEPARATOR);

        return getBaseDir() + jsonDir.toString();
    }

    public static String getOutputDir(String packageName) {

        packageName = packageName.replace(".", SEPARATOR);

        StringBuilder outputDir = new StringBuilder();
        outputDir.append("src" + SEPARATOR + "test" + SEPARATOR);
        outputDir.append("java");
        outputDir.append(SEPARATOR);
        outputDir.append(packageName + SEPARATOR);

        return getBaseDir() + outputDir.toString();
    }

}
