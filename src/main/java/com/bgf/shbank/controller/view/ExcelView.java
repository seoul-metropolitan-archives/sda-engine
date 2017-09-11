package com.bgf.shbank.controller.view;

import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.code.ApiStatus;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by manasobi on 2017-03-09.
 */
@Slf4j
public class ExcelView extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,
                                        HttpServletRequest req, HttpServletResponse res) throws Exception {

        String txId = model.get("txId").toString();
        String fileName = model.get("fileName").toString();

        try {
            @Cleanup
            InputStream is = new ClassPathResource("/excel/" + txId + ".xlsx").getInputStream();

            XLSTransformer transformer = new XLSTransformer();

            workbook = transformer.transformXLS(is, model);

            res.setHeader("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + ".xlsx\"");

            workbook.write(res.getOutputStream());

        } catch (Exception e) {

            log.error("ExcelUtils-download :: {}", e.getMessage());
            throw new ApiException(ApiStatus.SYSTEM_ERROR, "엑셀 파일 다운로드중에 오류가 발생하였습니다.");
        }

    }
}