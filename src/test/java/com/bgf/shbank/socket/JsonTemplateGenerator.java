package com.bgf.shbank.socket;

import com.bgf.shbank.utils.CmdUtils;
import com.bgf.shbank.utils.FileUtils;

import java.io.File;

/**
 * Created by james on 2017-02-02.
 */
public class JsonTemplateGenerator {

    public static void generate(String jsonFile) {

        String excelFile = jsonFile.replace(".json", ".xlsx");

        String targetDir = SocketMsgUtils.getTemplateExcelDir().replace("excel" + File.separatorChar, "");

        FileUtils.copyFile(SocketMsgUtils.getTemplateExcelDir() + excelFile, targetDir + excelFile);

        CmdUtils.execute("wscript " + targetDir + "Excel2Json.js " + excelFile);

        FileUtils.moveFileToDir(targetDir + "output" + File.separator + jsonFile, SocketMsgUtils.getTemplateJsonDir());

        FileUtils.deleteFile(targetDir + excelFile);
    }
}
