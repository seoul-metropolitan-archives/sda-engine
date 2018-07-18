/**
######################################################################
##
## BGF 신한은행 위탁관리 :: Flyway 스크립트
##
######################################################################
##    설치관리 전문 관련 테이블
######################################################################
**/

/**
[테이블 생성]
## 01. atms_02001100        :: 작업일정통보
## 02. atms_02001110        :: 신규코너정보
## 03. atms_02001200        :: 코너정보변경
## 04. atms_02001120        :: 폐쇄코너정보
## 05. atms_02001270        :: 코너상태변경통보
## 06. atms_02001130        :: 신규설치기기정보
## 07. atms_02001210        :: 이전/교체기기정보
## 08. atms_02001140        :: 철수기기정보
## 09. atms_02001150        :: 기기설치/철수결과
## 10. atms_02001160        :: 신규시설물정보
## 11. atms_02001190        :: 시설물품정보(시설물정보변경)
## 12. atms_02001170        :: 철수시설물통보
## 13. atms_02001180        :: 시설물설치/철수결과
## 14. atms_02001230        :: 시설물일련번호변경
## 15. atms_02001280        :: 기타작업정보
## 16. atms_02001290        :: 일회성비용청구
## 17. atms_corner_status   :: 코너현황(메인화면)
## 18. atms_terminal_status :: 기기현황(메인화면)
## 19. atms_facility_status :: 시설물현황(메인화면)
## 20. atms_02001220        :: 시설물 교체/변경
**/

/*___ atms_02001100 ___*/
CREATE TABLE atms_02001100 (
  work_seq_no               CHAR(8) NOT NULL,
  change_chasu              CHAR(2),
  stext_gubun               CHAR(1),
  work_gubun                CHAR(2),
  notice_datetime           TIMESTAMP,
  task_apply_date           TIMESTAMP,
  work_date                 TIMESTAMP,
  install_terminal_count    CHAR(2),
  install_fac_count         CHAR(2),
  branch_name               VARCHAR2(40),
  corner_name               VARCHAR2(40),
  biz_manager_name          VARCHAR2(20),
  manager_telno             VARCHAR2(20),
  work_content              VARCHAR2(200),
  PRIMARY KEY (work_seq_no)
);

/*___ atms_02001110 ___*/
CREATE TABLE atms_02001110 (
  work_seq_no          CHAR(8) NOT NULL,
  change_chasu         CHAR(2),
  stext_gubun          CHAR(1),
  jisa_code            CHAR(2) NOT NULL, -- 전문에는 없지만 추가된 컬럼
  branch_code          CHAR(4) NOT NULL,
  branch_name          VARCHAR2(40),
  corner_code          CHAR(2) NOT NULL,
  corner_name          VARCHAR2(40),
  place_gubun          CHAR(1),
  branch_gubun         CHAR(1),
  special_style_gubun  CHAR(1),
  oper_time_gubun      CHAR(1),
  oper_start_time      TIMESTAMP,
  oper_end_time        TIMESTAMP,
  check_oper_enable    CHAR(1),
  service_fee          CHAR(10),
  security_corp_code   CHAR(2),
  fac_hire_enable      CHAR(1),
  addr                 VARCHAR2(60),
  unusl                VARCHAR2(100),
  oper_day             CHAR(7),
  PRIMARY KEY (work_seq_no, branch_code, corner_code)
);

/*___ atms_02001200 ___*/
CREATE TABLE atms_02001200 (
  stext_notice_datetime TIMESTAMP NOT NULL,
  stext_gubun           CHAR(1),
  jisa_code             CHAR(2) NOT NULL, -- 전문에는 없지만 추가된 컬럼
  branch_code           CHAR(4) NOT NULL,
  corner_code           CHAR(2) NOT NULL,
  corner_name           VARCHAR2(40),
  place_gubun           CHAR(1),
  branch_gubun          CHAR(1),
  oper_start_gubun      CHAR(1),
  oper_start_time       TIMESTAMP,
  oper_end_time         TIMESTAMP,
  security_corp_code    CHAR(2),
  check_oper_enable     CHAR(1),
  oper_day              CHAR(7),
  addr                  VARCHAR2(60),
  install_place         VARCHAR2(60),
  detail_fac_info       VARCHAR2(150),
  PRIMARY KEY (stext_notice_datetime)
);

/*___ atms_02001120 ___*/
CREATE TABLE atms_02001120 (
  work_seq_no         CHAR(8) NOT NULL,
  change_chasu        CHAR(2),
  stext_gubun         CHAR(1),
  closing_branch_no   CHAR(4),
  closing_corner_code CHAR(2),
  addr                VARCHAR2(60),
  unusl               VARCHAR2(100),
  PRIMARY KEY (work_seq_no)
);

/*___ atms_02001270 ___*/
CREATE TABLE atms_02001270 (
  work_seq_no        CHAR(8) NOT NULL,
  chasu              CHAR(2),
  jisa_code          CHAR(2) NOT NULL, -- 전문에는 없지만 추가된 컬럼
  branch_code        CHAR(4) NOT NULL,
  corner_code        CHAR(2) NOT NULL,
  corner_name        VARCHAR2(40),
  stext_gubun        CHAR(1),
  closing_date       TIMESTAMP,
  restart_gubun      CHAR(1),
  restart_date       TIMESTAMP,
  change_item_gubun  CHAR(1),
  oper_start_time    TIMESTAMP,
  oper_end_time      TIMESTAMP,
  check_oper         CHAR(1),
  apply_date         CHAR(8),
  PRIMARY KEY (work_seq_no)
);

/*___ atms_02001130 ___*/
CREATE TABLE atms_02001130 (
  jisa_code                CHAR(2) NOT NULL, -- 전문에는 없지만 추가된 컬럼
  branch_code              CHAR(4) NOT NULL,
  corner_code              CHAR(2) NOT NULL,
  terminal_no              CHAR(4) ,
  work_seq_no              CHAR(8) NOT NULL,
  terminal_seq_no          CHAR(2),
  change_chasu             CHAR(2),
  stext_gubun              CHAR(1),
  terminal_corp_code       CHAR(1),
  model_code               CHAR(3),
  hire_terminal_enable     CHAR(1),
  install_terminal_gubun   CHAR(1),
  terminal_hire_fee        CHAR(10),
  gateway_ip_addr          VARCHAR2(15),
  terminal_ip_addr         VARCHAR2(15),
  terminal_smask_addr      VARCHAR2(15),
  work_plan_datetime       TIMESTAMP ,
  terminal_prod_no         VARCHAR2(15),
  PRIMARY KEY (work_seq_no)
);

/*___ atms_02001210 ___*/
CREATE TABLE atms_02001210 (
  work_seq_no                      CHAR(8) NOT NULL,
  terminal_seq_no                  CHAR(2),
  change_chasu                     CHAR(2),
  stext_gubun                      CHAR(1),
  nouse_jisa_code                  CHAR(2) NOT NULL, -- 전문에는 없지만 추가된 컬럼
  nouse_branch_code                CHAR(4) NOT NULL,
  nouse_corner_code                CHAR(2) NOT NULL,
  nouse_terminal_no                CHAR(4) NOT NULL,
  nouse_terminal_corp_code         CHAR(1),
  nouse_model_code                 CHAR(3),
  nouse_hire_terminal_enable       CHAR(1),
  nouse_gubun                      CHAR(1),
  work_plan_datetime               TIMESTAMP,
  new_branch_code                  CHAR(4) NOT NULL,
  new_corner_code                  CHAR(2) NOT NULL,
  new_terminal_no                  CHAR(4) NOT NULL,
  new_terminal_corp_code           CHAR(1),
  new_model_code                   CHAR(3),
  new_hire_terminal_enable         CHAR(1),
  new_install_terminal_gubun       CHAR(1),
  new_terminal_hire_fee            CHAR(10),
  new_gateway_ip_addr              VARCHAR2(15),
  new_terminal_ip_addr             VARCHAR2(15),
  new_terminal_smask_addr          VARCHAR2(15),
  terminal_prod_no                 VARCHAR2(15),
  new_terminal_mng_gubun           CHAR(1),
  PRIMARY KEY (work_seq_no)
);

/*___ atms_02001140 ___*/
CREATE TABLE atms_02001140 (
  work_seq_no            CHAR(8) NOT NULL,
  terminal_seq_no        CHAR(2),
  change_chasu           CHAR(2),
  stext_gubun            CHAR(1),
  nouse_jisa_code        CHAR(2) NOT NULL, -- 전문에는 없지만 추가된 컬럼
  nouse_branch_code      CHAR(4) NOT NULL,
  nouse_corner_code      CHAR(2) NOT NULL,
  nouse_terminal_no      CHAR(4) NOT NULL,
  terminal_corp_code     CHAR(1),
  model_code             CHAR(3),
  hire_terminal_enable   CHAR(1),
  nouse_gubun            CHAR(1),
  work_plan_datetime     TIMESTAMP,
  PRIMARY KEY (work_seq_no)
);

/*___ atms_02001150 ___*/
CREATE TABLE atms_02001150 (
  work_seq_no            CHAR(8) NOT NULL,
  terminal_seq_no        CHAR(2),
  result_stext_gubun     CHAR(1),
  jisa_code              CHAR(2) NOT NULL, -- 전문에는 없지만 추가된 컬럼
  branch_code            CHAR(4) NOT NULL,
  corner_code            CHAR(2) NOT NULL,
  terminal_no            CHAR(4) NOT NULL,
  terminal_corp_code     CHAR(1),
  model_code             CHAR(3),
  terminal_prod_no       VARCHAR2(15),
  work_complete_enable   CHAR(1),
  work_complete_date     TIMESTAMP,
  unusl                  VARCHAR2(200),
  PRIMARY KEY (work_seq_no)
);

/*___ atms_02001160 ___*/
CREATE TABLE atms_02001160 (
  work_seq_no              CHAR(8) NOT NULL,
  fac_seq_no               CHAR(2),
  change_chasu             CHAR(2),
  stext_gubun              CHAR(1),
  jisa_code                CHAR(2) NOT NULL, -- 전문에는 없지만 추가된 컬럼
  branch_code              CHAR(4) NOT NULL,
  corner_code              CHAR(2) NOT NULL,
  fac_gubun_code           CHAR(2),
  fac_code                 CHAR(4) NOT NULL,
  hire_fac_enable          CHAR(1),
  install_article_gubun    CHAR(1),
  asset_seq_no             VARCHAR2(12),
  fac_ip_addr              VARCHAR2(15),
  fac_gw_addr              VARCHAR2(15),
  fac_smask_addr           VARCHAR2(15),
  hire_fee                 CHAR(10),
  detail_fac_info          VARCHAR2(200),
  PRIMARY KEY (work_seq_no)
);

/*___ atms_02001190 ___*/
CREATE TABLE atms_02001190 (
  tx_id                             TIMESTAMP NOT NULL, -- 전문에는 없지만 추가된 컬럼
  stext_gubun                       CHAR(1),
  change_before_fac_gubun_code      CHAR(2),
  change_before_fac_code            CHAR(4),
  change_after_fac_gubun_code       CHAR(2),
  change_after_fac_code             CHAR(4),
  change_after_fac_name             VARCHAR2(50),
  change_after_corp_name            VARCHAR2(20),
  change_after_hire_fee             CHAR(10),
  PRIMARY KEY (tx_id)
);

/*___ atms_02001170 ___*/
CREATE TABLE atms_02001170 (
  work_seq_no          CHAR(8) NOT NULL,
  fac_seq_no           CHAR(2),
  change_chasu         CHAR(2),
  stext_gubun          CHAR(1),
  jisa_code            CHAR(2) NOT NULL, -- 전문에는 없지만 추가된 컬럼
  branch_code          CHAR(4) NOT NULL,
  corner_code          CHAR(2) NOT NULL,
  fac_gubun_code       CHAR(2),
  fac_code             CHAR(4) NOT NULL,
  hire_fac_enable      CHAR(1),
  nouse_gubun          CHAR(1),
  asset_seq_no         VARCHAR2(12),
  PRIMARY KEY (work_seq_no)
);

/*___ atms_02001180 ___*/
CREATE TABLE atms_02001180 (
  work_seq_no           CHAR(8) NOT NULL,
  fac_seq_no            CHAR(2),
  result_stext_gubun    CHAR(1),
  jisa_code             CHAR(2) NOT NULL, -- 전문에는 없지만 추가된 컬럼
  branch_code           CHAR(4) NOT NULL,
  corner_code           CHAR(2) NOT NULL,
  fac_code              CHAR(4) NOT NULL,
  fac_name              VARCHAR2(50),
  asset_seq_no          VARCHAR2(12),
  adopt_date            TIMESTAMP,
  work_complete_enable  CHAR(1),
  work_complete_date    TIMESTAMP,
  unusl                 VARCHAR2(200),
  PRIMARY KEY (work_seq_no)
);

/*___ atms_02001230 ___*/
CREATE TABLE atms_02001230 (
  tx_id                      TIMESTAMP NOT NULL, -- 전문에는 없지만 추가된 컬럼
  change_before_asset_seq_no VARCHAR2(12),
  change_after_asset_seq_no  VARCHAR2(12),
  jisa_code                  CHAR(2) NOT NULL, -- 전문에는 없지만 추가된 컬럼
  branch_code                CHAR(4) NOT NULL,
  corner_code                CHAR(2) NOT NULL,
  change_datetime            TIMESTAMP,
  PRIMARY KEY (tx_id)
);

/*___ atms_02001280 ___*/
CREATE TABLE atms_02001280 (
  work_seq_no  CHAR(8) NOT NULL,
  chasu        CHAR(2),
  jisa_code    CHAR(2) NOT NULL, -- 전문에는 없지만 추가된 컬럼
  branch_code  CHAR(4) NOT NULL,
  corner_code  CHAR(2) NOT NULL,
  corner_name  VARCHAR2(40),
  work_date    TIMESTAMP,
  work_content VARCHAR2(300),
  PRIMARY KEY (work_seq_no)
);

/*___ atms_02001290 ___*/
CREATE TABLE atms_02001290 (
  tx_id             TIMESTAMP NOT NULL, -- 전문에는 없지만 추가된 컬럼
  billing_month     TIMESTAMP,
  cost_gubun        CHAR(2),
  detail_item_gubun CHAR(2),
  seq_no            CHAR(20),
  work_date         TIMESTAMP,
  inspection_month  TIMESTAMP,
  billing_amt       CHAR(8),
  jisa_code         CHAR(2) NOT NULL, -- 전문에는 없지만 추가된 컬럼
  branch_code       CHAR(4) NOT NULL,
  corner_code       CHAR(2) NOT NULL,
  terminal_no       CHAR(4) NOT NULL,
  corner_name       VARCHAR2(40),
  detail_content    VARCHAR2(200),
  PRIMARY KEY (tx_id)
);

/*___ atms_corner_status ___*/
CREATE TABLE atms_corner_status (
  jisa_code            CHAR(2) NOT NULL,
  branch_code          CHAR(4) NOT NULL,
  branch_name          VARCHAR2(40),
  corner_code          CHAR(2) NOT NULL,
  corner_name          VARCHAR2(40),
  place_gubun          CHAR(1),
  branch_gubun         CHAR(1),
  special_style_gubun  CHAR(1),
  oper_time_gubun      CHAR(1),
  oper_start_time      TIMESTAMP,
  oper_end_time        TIMESTAMP,
  check_oper_enable    CHAR(1),
  sevice_fee           CHAR(10),
  security_corp_code   CHAR(2),
  fac_hire_enable      CHAR(1),
  addr                 VARCHAR2(100),
  unusl                VARCHAR2(100),
  oper_day             CHAR(7),
  PRIMARY KEY (jisa_code, branch_code, corner_code )
);

/*___ atms_terminal_status ___*/
CREATE TABLE atms_terminal_status (
  jisa_code                  CHAR(2) NOT NULL,
  branch_code                CHAR(4) NOT NULL,
  branch_name                VARCHAR2(40),
  corner_code                CHAR(2) NOT NULL,
  corner_name                VARCHAR2(40),
  terminal_no                CHAR(4) NOT NULL,
  oper_enable                CHAR(1),  -- 운영여부
  model_code                 CHAR(3),
  branch_gubun               CHAR(1),  -- 코너형태(점포구분)
  terminal_type              CHAR(1),  -- 전문에는 없을것으로 추정 , 기존에는 부스형태라는 단어로 쓰임
  place_gubun                CHAR(1),
  oper_time_gubun            CHAR(1),
  weekend_oper_gubun         CHAR(1), -- 주말운영여부 -> 운영구분이 평일일때만 check
  check_oper_enable          CHAR(1),
  cash_50k_oper_enable       CHAR(1),
  oper_start_time            TIMESTAMP,
  oper_end_time              TIMESTAMP,
  task_apply_date            TIMESTAMP, -- 업무적용일 -> 작업일정통보가 오면 insert
  work_date                  TIMESTAMP, -- 업무적용일 -> 작업일정통보가 오면 insert
  security_corp              CHAR(2),
  install_place_gubun        CHAR(3),   -- 전문에서 안보임
  addr                       VARCHAR2(150) ,
  photo_enable               CHAR(1), -- 촬영여부
  booth_corp                 CHAR(1), -- 부스업체
  booth_type                 CHAR(2), -- 부스종류
  intercom_enable            CHAR(1), -- 인터폰유무
  envelope_enable            CHAR(1), -- 봉투비치대 유무
  garbagecan_enable          CHAR(1), -- 쓰레기통유무
  shredder_enable            CHAR(1), -- 세단기 유무
  extinguisher_enable        CHAR(1), -- 소화기유무
  poster_count               CHAR(2), -- 액자수
  cooler_heater_enable       CHAR(1), -- 냉/난방기 유무
  slope_enable               CHAR(1), -- 경사로 유무
  terminal_corp_code         CHAR(1),
  hire_terminal_enable       CHAR(1),
  install_terminal_gubun     CHAR(1),
  terminal_hire_fee          CHAR(10),
  gateway_ip_addr            VARCHAR2(15),
  terminal_ip_addr           VARCHAR2(15),
  terminal_smask_addr        VARCHAR2(15),
  terminal_prod_no           VARCHAR2(15),
  intercom_no                VARCHAR2(20), -- 인터폰번호
  terminal_location          VARCHAR2(50), -- 기기위치
  comm_office                VARCHAR2(10), -- 통신실
  internet_classify          VARCHAR2(10), -- 인터넷종류 -> 코드화필요?
  security_corp_contract_no  VARCHAR2(20), -- 경비사 계약번호
  mng_channel                VARCHAR2(10), -- 관리채널 -> 코드화필요?
  PRIMARY KEY (jisa_code, branch_code, corner_code, terminal_no )
);

/*___ atms_facility_status ___*/
CREATE TABLE atms_facility_status (
  jisa_code                CHAR(2) NOT NULL,
  branch_code              CHAR(4) NOT NULL,
  branch_name              VARCHAR2(40),
  corner_code              CHAR(2) NOT NULL,
  corner_name              VARCHAR2(40),
  fac_gubun_code           CHAR(2),
  fac_code                 CHAR(4) NOT NULL,
  oper_enable              CHAR(1),  -- 운영여부
  hire_fac_enable          CHAR(1),
  install_article_gubun    CHAR(1),
  asset_seq_no             VARCHAR2(12),
  fac_ip_addr              VARCHAR2(15),
  fac_gw_addr              VARCHAR2(15),
  fac_smask_addr           VARCHAR2(15),
  hire_fee                 CHAR(10),
  detail_fac_info          VARCHAR2(200),
  adopt_date               TIMESTAMP, -- 도입일
  install_date             TIMESTAMP, -- 설치완료일
  unit_price               VARCHAR2(15),    -- 단가
  remarks                  VARCHAR2(200),    -- 비고
  PRIMARY KEY (jisa_code, branch_code, corner_code, fac_code)
);

/*___ atms_02001220 ___*/
CREATE TABLE atms_02001220 (
  work_seq_no               CHAR(8) NOT NULL,
  fac_seq_no                CHAR(2),
  change_chasu              CHAR(2),
  stext_gubun               CHAR(1),
  jisa_code                 CHAR(2) NOT NULL, -- 전문에는 없지만 추가된 컬럼
  nouse_branch_code         CHAR(4),
  nouse_corner_code         CHAR(2),
  nouse_fac_gubun_code      CHAR(2),
  nouse_fac_code            CHAR(4),
  nouse_asset_seq_no        VARCHAR2(12),
  nouse_gubun               CHAR(1),
  new_branch_code           CHAR(4),
  new_corner_code           CHAR(2),
  new_fac_gubun_code        CHAR(2),
  new_fac_code              CHAR(4),
  new_hire_fac_enable       CHAR(1),
  new_install_article_gubun CHAR(1),
  new_asset_seq_no          VARCHAR2(12),
  fac_ip_addr               VARCHAR2(15),
  fac_gw_addr               VARCHAR2(15),
  fac_smask_addr            VARCHAR2(15),
  new_hire_fee              CHAR(10),
  PRIMARY KEY (work_seq_no)
);
