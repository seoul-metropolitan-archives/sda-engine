package rmsoft.ams.seoul.st.st010.service;

import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import rmsoft.ams.seoul.st.st010.dao.St010Mapper;
import rmsoft.ams.seoul.st.st010.vo.St01004VO;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;

@Service
public class St010Service extends BaseService {

    @Inject
    private St010Mapper st010Mapper;

    @Autowired
    ResourceLoader loader;

    public Page<St01004VO> getAggregation(Pageable pageable, RequestParams<St01004VO> requestParams) {
        St01004VO st01004VO = new St01004VO();
        st01004VO.setLocationUuid(requestParams.getString("locationUuid"));
        return filter(st010Mapper.getAggregation(st01004VO), pageable, "", St01004VO.class);
    }


    public ByteArrayInputStream getExcelDown() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream is = null;
        try {
            is = St010Service.class.getClassLoader().getResourceAsStream("excelTemp/test11.xlsx");

            XSSFWorkbook workBook = new XSSFWorkbook(is);
            XSSFSheet sheet = null;
            XSSFRow row = null;
            XSSFCell cell = null;

            sheet = workBook.cloneSheet(0);
            workBook.setSheetName(workBook.getSheetIndex(sheet), "테스트 시트");

            //cell값바꾸는건 sheet row
            row = sheet.getRow(5);
            //row cell값
            cell = row.getCell(8);

            //바인딩
            cell.setCellValue("테스트");


            //기존 0 1새로운시트
            workBook.removeSheetAt(0);
            workBook.write(out);

            return new ByteArrayInputStream(out.toByteArray());
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }




}
