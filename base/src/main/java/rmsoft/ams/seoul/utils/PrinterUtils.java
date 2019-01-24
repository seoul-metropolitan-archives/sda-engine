package rmsoft.ams.seoul.utils;
import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.code.ApiStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rmsoft.ams.seoul.st.st018.vo.St018PrinterVO;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrinterUtils {

    protected final static Logger logger = LoggerFactory.getLogger(PrinterUtils.class);

    static String sixbitStr = "A#000001,B#000010,C#000011,D#000100,E#000101,F#000110,G#000111,H#001000,I#001001,J#001010,K#001011,L#001100,M#001101,N#001110,O#001111,P#010000,Q#010001,R#010010,S#010011,T#010100,U#010101,V#010110,W#010111,X#011000,Y#011001,Z#011010,0#011011,1#011100,2#011101,3#011110,4#011111,5#100000,6#100001,7#100010,8#100011,9#100100";


    public static class ModelPrinter {
        // ex) { "서울기록원","2010" ,"30년","0000000000002547","0000000000002547","KKRBSAA00001","0544092CB4824C1070C30C31"}

        //바코드번호
        public String barcNo;
        //생산기관
        public String mkOrgName;
        //생산년도
        public String mkYear;
        //보존기간
        public String consDtStr;
        //관리번호
        public String assetNo;
        // rfid 번호
        public String rfidNo;

    }
    public static void main(String[] args) throws Exception {

		/*
		String[][] dataList = {
				{"1","서울기록원","2010","30년","0000000000002547","0000000000002547","KKRBSAA1234A","6544092CB483154423030303039"}
				,{"2","서울특별시 시민소통기획관, 2012-01-01 ~ ","2010~2012","준영구","0000000000002994","0000000000002994","KKRBSAA1235A","0544092CB483154423030303032"}
				,{"3","서울시건강가정지원센터, 2007~","2012.01~2012.12","30년","0000000000002995","0000000000002995","KKRBSAA1236A","0544092CB483154423030303033"}
				,{"4","서울특별시 시민소통기획관 뉴미디어담당관, 2010-09-27 ~ ","2011.01.02~2012.03.04","영구","0000000000003007","0000000000003007","KKRBSAA1237A","0544092CB483154423030303034"}
				,{"5","서울특별시 여성가족정책실 가족담당관, 2015-01-01 ~ ","2013.12","30년","0000000000003008","0000000000003008","KKRBSAA1238A","0544092CB483154423030303035"}
				,{"6","서울시건강가정지원센터","2014","준영구","0000000000003009","0000000000003009","KKRBSAA1239A","0544092CB483154423030303036"}
				,{"7","서울특별시 주택본부 건축기획과, 2010-09-27 ~ 2012-01-01","2011.01.12","30년","0000000000003010","0000000000003010","KKRBSAA1240A","0544092CB483154423030303037"}
				,{"8","서울특별시 주택본부 주거정비기획관 공공관리과, 2010-09-27 ~ 2011-07-28","2015~2016","영구","0000000000003011","0000000000003011","KKRBSAA1241A","0544092CB483154423030303038"}
				,{"9","서울특별시 주택본부 주거정비기획관 재정비1과, 2010-09-27 ~ 2011-07-28","2017","30년","0000000000003012","0000000000003012","KKRBSAA1242A","0544092CB483154423030303039"}
				,{"10","서울특별시 주택본부 주거정비기획관 재정비2과, 2010-09-27 ~ 2011-07-28","2018","영구","0000000000003013","0000000000003013","KKRBSAA1243A","0544092CB483154423030303130"}
		};
		*/

        String[][] dataList = {
                {"1","서울기록원"            ,"2010" ,"30년",		"0000000000002547","0000000000002547","KKRBSAA00001","0544092CB4824C1070C30C31"}
//                ,{"2","서울특별시 시민소통기획관" ,"2012" ,"준영구",	"0000000000002994","0000000000002994","KKRBSAA00002","0544092CB4824C1070C30C32"}
//                ,{"3","서울시 건강가정지원센터"  ,"2012.12"  ,"30년",	"0000000000002995","0000000000002995","KKRBSAA00003","0544092CB4824C1070C30C33"}
//                ,{"4","서울특별시 시민소통기획관" ,"2012.03" ,"영구",	"0000000000003007","0000000000003007","KKRBSAA00004","0544092CB4824C1070C30C34"}
//                ,{"5","서울특별시 여성가족정책실" ,"2013.12" ,"30년",	"0000000000003008","0000000000003008","KKRBSAA00005","0544092CB4824C1070C30C35"}
//                ,{"6","서울시건강가정지원센터"   ,"2014" ,"준영구",	"0000000000003009","0000000000003009","KKRBSAA00006","0544092CB4824C1070C30C36"}
//                ,{"7","서울특별시 주택본부"      ,"2011.01" ,"30년",	"0000000000003010","0000000000003010","KKRBSAA00007","0544092CB4824C1070C30C37"}
//                ,{"8","서울특별시 주택본부"      ,"2016" ,"영구",		"0000000000003011","0000000000003011","KKRBSAA00008","0544092CB4824C1070C30C38"}
//                ,{"9","서울특별시 주택본부"      ,"2017" ,"30년",		"0000000000003012","0000000000003012","KKRBSAA00009","0544092CB4824C1070C30C39"}
//                ,{"10","서울특별시 주택본부"     ,"2018" ,"영구",		"0000000000003013","0000000000003013","KKRBSAA0000A","0544092CB4824C1070C30C01"}
        };


        Socket socket = null;
        OutputStream os = null;
        String ip = "192.168.0.150";	//프린트 설정 아이피
        int port = 9110;			//프린트 설정 포트 (1번째장비)
        //int port = 9100;			//프린트 설정 포트 (2번째신규장비)

        try {
            socket = new Socket(ip, port);
            os     = socket.getOutputStream();

            ArrayList<HashMap<String,String>> printList = new ArrayList<HashMap<String,String>>();
            for (int i=0; i<dataList.length; i++) {
                String[] data = dataList[i];
                HashMap<String,String> printMap = new HashMap<String,String>();
                printMap.put("mkOrgName", data[1]);		//생산기관
                printMap.put("mkYear", data[2]);		//생산년도
                printMap.put("consDtStr", data[3]);		//보존기간
                printMap.put("assetNo", data[4]);		//관리번호
                printMap.put("barcNo", data[5]);		//바코드번호
                printMap.put("rfidNo", generateRfidNo( data[6]));

                //System.out.println(printMap.get("rfidNo"));
                printList.add(printMap);
            }

            if(socket!=null && socket.isBound()) {
                for (int i=0; i<printList.size(); i++) {

                    HashMap<String,String> param = printList.get(i);

                    List<String> list = asset(param);

                    for(String content : list){
                        System.out.print(new String(content+"\r\n"));
                        os.write(new String(content+"\r\n").getBytes("UTF-8"));
                        os.flush();
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(socket!=null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(os!=null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    /**
     * sequence 를 5자리 숫자+알파벳 으로 만들어줌
     * @param seq
     * @return
     */
    public static String convertSequenceTo5Char(int seq){
        String orgText = generateBase36(seq);
        String result = orgText;
        // 5자리로 무조건 만들어줌.
        for (int cnt = 0; cnt < Math.abs(orgText.length() - 5); cnt++) {
            result = "0" + result;
        }

        return result;
    }
    /**
     * 36 진수
     * 0 ~ Z 까지
     * @param i
     * @return
     */
    private static String generateBase36(int i) {

        // 원래 0부터 시작 했지만, '00' 이라는 값이 출력되면 '0' 값과 동일 하므로 빼버림.
        char[] ALPHABET = "123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        int quot = i / 35;
        int rem = i % 35;

        char letter = ALPHABET[rem];
        if (quot == 0) {
            return "" + letter;
        } else {
            return generateBase36(quot - 1) + letter;
        }
    }


    public static String generateRfidNo(String rfidNo){
        return  "054409" +  encSixbit_1(rfidNo);
    }

    public static void startPrint(String ipOfPrinter, String portOfPrinter, ModelPrinter modelPrinter) throws ApiException {
        List<ModelPrinter> aModelTag = new ArrayList<>();
        aModelTag.add(modelPrinter);
        startPrint(ipOfPrinter, portOfPrinter, aModelTag);
    }
    /**
     *
     * @param ipOfPrinter
     * @param portOfPrinter
     * @param aModelTag print 할 정보객체
     * @throws Exception
     */
    public static void startPrint(String ipOfPrinter, String portOfPrinter, List<ModelPrinter> aModelTag) throws ApiException {
        Socket socket = null;
        OutputStream os = null;

        try {
            socket = new Socket(ipOfPrinter, Integer.parseInt(portOfPrinter));
            os     = socket.getOutputStream();

            if(socket!=null && socket.isBound()) {
                // 프린터연결 ok
                // do nothing
            }else{
                String errorMsg = "프린터에 소켓연결을 할수 없습니다. ip 또는 port 가 올바른지 확인 하세요. "+ipOfPrinter+":"+portOfPrinter;
                logger.error(errorMsg);
                throw new ApiException(ApiStatus.SYSTEM_ERROR, errorMsg);
            }


            for (ModelPrinter eachModelTag : aModelTag) {

                Map<String,String> printMap = new HashMap<>();
                printMap.put("mkOrgName", eachModelTag.mkOrgName);	 //생산기관
                printMap.put("mkYear", eachModelTag.mkYear);		 //생산년도
                printMap.put("consDtStr", eachModelTag.consDtStr); //보존기간
                printMap.put("assetNo", eachModelTag.assetNo);		//관리번호
                printMap.put("barcNo", eachModelTag.barcNo);		//바코드번호
                printMap.put("rfidNo", "054409" + encSixbit_1(eachModelTag.rfidNo));

                List<String> list = asset(printMap);

                for(String content : list){
                    System.out.print(new String(content+"\r\n"));
                    os.write(new String(content+"\r\n").getBytes("UTF-8"));
                    os.flush();
                }
            }

        } catch (Exception e) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, e.getMessage());
        } finally {
            if(socket!=null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(os!=null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 라벨
    public static List<String> asset(Map<String,String> param) {
        String barcNo = param.get("barcNo");				//바코드번호
        String mkOrgName = param.get("mkOrgName");			//생산기관
        String mkYear = param.get("mkYear");				//생산년도
        String consDtStr = param.get("consDtStr");			//보존기간
        String assetNo = param.get("assetNo");				//관리번호
        String rfidNo = param.get("rfidNo");				//RFID번호
        List<String> list = new ArrayList<String>();

        System.out.println("rfidNo : " + rfidNo);

        list.add("~NORMAL");
        list.add("~CREATE;RFID;X");							//(Enter Create Form Mode)
        list.add("FONT;NAME QGULIM.TTF"); 					//font
        list.add("ALPHA");
        list.add(String.format("POINT;4;24;9;9;\"%s\"", mkOrgName));	//POINT;y좌표;x좌표;글자높이;글자넓이;"DATA"
        list.add(String.format("POINT;6;24;9;9;\"%s\"", mkYear));
        list.add(String.format("POINT;6;35.5;9;9;\"%s\"", consDtStr));
        list.add(String.format("POINT;8;24;9;9;\"%s\"", assetNo));
        list.add("STOP");
        list.add("BARCODE");								//(Bar Code command)
        list.add(String.format("C128B;%s;%s;%s;%s"
                ,"XRD3:3:6:6:9:9:12:12"				//MAG, 1:2:3:4 비율 유지
                ,"H4"								//바코드높이 (3~99)
                ,"6.5"								//SR, y좌표
                ,"4"								//SC, x좌표
        ));
        list.add(String.format("*%s*", barcNo)); 			//바코드내용 *data*
        list.add("STOP");									//(Ends Bar Code command)
        list.add("RFWTAG;96"); 						//(RFID Code command)
        list.add(String.format("96;H;*%s*", rfidNo));	//RFID데이터
        list.add("STOP"); 									//(Ends RFID Code command)
        list.add("END");
        list.add("~EXECUTE;RFID;1");
        list.add("~NORMAL");

        return list;
    }

    public static String stringToHex(String s) {
        String result = "";

        for (int i = 0; i < s.length(); i++) {
            result += String.format("%02X", (int) s.charAt(i));
        }

        return result;
    }

    public static String rpad(String str, int len, String padChar) {

        if (str == null)
            return "";

        if (str.length() >= len)
            return str;

        for (int i=str.length(); i<len; i++) {
            str = str + padChar;
        }

        return str;
    }

    public static String encSixbit(String str) {
        String encStr = "";
        StringBuffer sb = new StringBuffer();

        for(int i=0; i<str.length(); i++){
            char s = str.charAt(i);

            Pattern p = Pattern.compile(s + "[#](\\d{6})");
            Matcher m = p.matcher(sixbitStr);

            if (m.find()) {
                sb.append(m.group(1));
            } else {
                return "ERROR";
            }
        }

        System.out.println(sb.toString());

        String sbStr = "";
        for(int i=0; i<sb.toString().length(); i++) {
            sbStr = sbStr + sb.toString().charAt(i);
            if (i>0 && (i+1)%4==0) {
                encStr = encStr + Integer.toHexString(Integer.parseInt(sbStr,2));
                sbStr = "";
            }
        }

        //System.out.println(encStr.toUpperCase());
        return encStr.toUpperCase();
    }

    public static String encSixbit_1(String str)
    {
        String encStr = "";
        if( str != null && !"".equals(str) )
        {
            String gStr = get6Bit(str);
            String hexStr = get2to16(gStr);
            //System.out.println( hexStr );
            encStr = hexStr;
        }
        return encStr.toUpperCase();
    }

    public static String get6Bit(String str)
    {
        String sRet = "";

        if( str != null && !"".equals(str) )
        {
            StringBuffer sb = new StringBuffer();
            char s;

            str = str.toUpperCase();
            for(int i=0; i<str.length(); i++)
            {
                s = str.charAt(i);
                switch(s)
                {
                    case '0' : sb.append("110000"); break;
                    case '1' : sb.append("110001"); break;
                    case '2' : sb.append("110010"); break;
                    case '3' : sb.append("110011"); break;
                    case '4' : sb.append("110100"); break;
                    case '5' : sb.append("110101"); break;
                    case '6' : sb.append("110110"); break;
                    case '7' : sb.append("110111"); break;
                    case '8' : sb.append("111000"); break;
                    case '9' : sb.append("111001"); break;
                    case 'A' : sb.append("000001"); break;
                    case 'B' : sb.append("000010"); break;
                    case 'C' : sb.append("000011"); break;
                    case 'D' : sb.append("000100"); break;
                    case 'E' : sb.append("000101"); break;
                    case 'F' : sb.append("000110"); break;
                    case 'G' : sb.append("000111"); break;
                    case 'H' : sb.append("001000"); break;
                    case 'I' : sb.append("001001"); break;
                    case 'J' : sb.append("001010"); break;
                    case 'K' : sb.append("001011"); break;
                    case 'L' : sb.append("001100"); break;
                    case 'M' : sb.append("001101"); break;
                    case 'N' : sb.append("001110"); break;
                    case 'O' : sb.append("001111"); break;
                    case 'P' : sb.append("010000"); break;
                    case 'Q' : sb.append("010001"); break;
                    case 'R' : sb.append("010010"); break;
                    case 'S' : sb.append("010011"); break;
                    case 'T' : sb.append("010100"); break;
                    case 'U' : sb.append("010101"); break;
                    case 'V' : sb.append("010110"); break;
                    case 'W' : sb.append("010111"); break;
                    case 'X' : sb.append("011000"); break;
                    case 'Y' : sb.append("011001"); break;
                    case 'Z' : sb.append("011010"); break;
                    default :  break;
                }
            }

            sRet = sb.toString();
        }

        return sRet;
    }

    public static String get2to16(String str)
    {
        String sRet = "";

        if( str != null && !"".equals(str) )
        {
            char s;
            String tmp = "";

            str = str.toUpperCase();
            for(int i=0; i<str.length(); i++)
            {
                s = str.charAt(i);

                if( (i+1) % 4 == 0 )
                {
                    tmp = tmp + s;

                    //System.out.println("["+ tmp +"]["+  Integer.toHexString( Integer.parseInt(tmp, 2) ) +"]");
                    sRet = sRet + Integer.toHexString( Integer.parseInt(tmp, 2) );

                    tmp = "";
                }
                else
                {
                    tmp = tmp + s;
                }
            }
        }

        return sRet;
    }

}
