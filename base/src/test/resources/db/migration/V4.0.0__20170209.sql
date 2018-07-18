/**
######################################################################
##
## BGF 신한은행 위탁관리 :: Flyway 스크립트
##
######################################################################
##   기타관리 전문 관련 테이블
######################################################################
**/

/**
[테이블 생성]
## 01. atms_04001110   :: 결번요청
## 02. atms_04001120   :: 요원정보조회
## 02. atms_04001130   :: 용역료관리
## 03. atms_04001140   :: 기기점검결과
## 04. atms_04001150   :: 페널티적용명세통보
## 05. atms_04001170   :: 기기정보전문
## 06. atms_04001180   :: CRT번호등록/변경
## 07. atms_04001190   :: 관리자메세지
## 08. atms_04001200   :: 코너입퇴실시간정보
## 09. atms_agent_mng  :: 요원정보관리
**/

/*___ atms_04001110 ___*/
CREATE TABLE atms_04001110 (
  tx_id            TIMESTAMP NOT NULL, -- 전문에는 없지만 추가된 컬럼
  req_stext_date   TIMESTAMP NOT NULL,
  req_stext_seq_no CHAR(7),
  message          VARCHAR2(100),
  PRIMARY KEY (tx_id)
);

/*___ atms_04001120 ___*/
CREATE TABLE atms_04001120 (
  tx_id      TIMESTAMP NOT NULL, -- 전문에는 없지만 추가된 컬럼
  emp_name    VARCHAR2(20),
  emp_regno   CHAR(13),
  emp_enable  CHAR(1),
  req_seq_no  CHAR(7),
  coop_org_no CHAR(2),
  PRIMARY KEY (tx_id)
);

/*___ atms_04001130 ___*/
CREATE TABLE atms_04001130 (
  service_fee_calc_year_month TIMESTAMP NOT NULL,
  jisa_code                  CHAR(2) NOT NULL,    -- 전문에는 없지만 추가된 컬럼
  branch_code                CHAR(4) NOT NULL,
  corner_code                CHAR(2) NOT NULL,
  corner_name                VARCHAR2(40),
  branch_gubun_code          CHAR(1),
  oper_time_gubun_code       CHAR(1),
  terminal_count             CHAR(3),
  check_oper_enable          CHAR(1),
  basic_service_fee           CHAR(15),
  terminal_add_service_fee    CHAR(15),
  time_add_service_fee        CHAR(15),
  check_add_service_fee       CHAR(15),
  oper_date_count            CHAR(2),
  month_service_fee           CHAR(15),
  penalty                    CHAR(15),
  total_give_service_fee      CHAR(15),
  note                       VARCHAR2(100),
  PRIMARY KEY (branch_code, corner_code, service_fee_calc_year_month)
);

/*___ atms_04001140 ___*/
CREATE TABLE atms_04001140 (
  seq_no                    CHAR(5) NOT NULL,
  jisa_code                 CHAR(2) NOT NULL,    -- 전문에는 없지만 추가된 컬럼
  branch_code               CHAR(4) NOT NULL,
  mng_branch_code           CHAR(4),
  corner_code               CHAR(2) NOT NULL,
  terminal_no               CHAR(4) NOT NULL,
  model_code                CHAR(3),
  overhaul_corp             CHAR(1),
  overhaul_emp_name         CHAR(10),
  inspection_emp_name       VARCHAR2(10),
  inspection_corp           CHAR(1),
  overhaul_gubun            CHAR(1),
  overhaul_date             TIMESTAMP,
  overhaul_start_time   TIMESTAMP,
  overhaul_end_time     TIMESTAMP,
  overhaul_handle_content   VARCHAR2(100),
  inspection_opinion        VARCHAR2(100),
  PRIMARY KEY (seq_no, branch_code, mng_branch_code, corner_code, terminal_no)
);

/*___ atms_04001150 ___*/
CREATE TABLE atms_04001150 (
  jisa_code               CHAR(2) NOT NULL,    -- 전문에는 없지만 추가된 컬럼
  branch_code             CHAR(4) NOT NULL,
  corner_code             CHAR(2) NOT NULL,
  terminal_no             CHAR(4) NOT NULL,
  corner_name             VARCHAR2(40),
  error_datetime          TIMESTAMP,
  callee_req_datetime     TIMESTAMP,
  callee_req_seq_no       CHAR(10),
  callee_req_chasu        CHAR(1),
  callee_chasu_gubun      CHAR(1),
  corner_arrival_datetime TIMESTAMP,
  repair_datetime         TIMESTAMP,
  terminal_type           CHAR(3),
  error_content           VARCHAR2(60),
  penalty_amt             CHAR(5),
  none_penalty_enable    CHAR(1),
  none_penalty_reason     VARCHAR2(200),
  PRIMARY KEY (branch_code, corner_code, terminal_no, error_datetime)
);

/*___ atms_04001170 ___*/
CREATE TABLE atms_04001170 (
  tx_id           TIMESTAMP NOT NULL, -- 전문에는 없지만 추가된 컬럼
  jisa_code       CHAR(2) NOT NULL,    -- 전문에는 없지만 추가된 컬럼
  branch_code     CHAR(4) NOT NULL,
  terminal_no     CHAR(4) NOT NULL,
  info_gubun      CHAR(1),
  partition_count VARCHAR2(2),
  drive_gubun1    VARCHAR2(1),
  total_limit1    VARCHAR2(7),
  usage1          VARCHAR2(7),
  drive_gubun2    VARCHAR2(1),
  total_limit2    VARCHAR2(7),
  usage2          VARCHAR2(7),
  drive_gubun3    VARCHAR2(1),
  total_limit3    VARCHAR2(7),
  usage3          VARCHAR2(7),
  drive_gubun4    VARCHAR2(1),
  total_limit4    VARCHAR2(7),
  usage4          VARCHAR2(7),
  drive_gubun5    VARCHAR2(1),
  total_limit5    VARCHAR2(7),
  usage5          VARCHAR2(7),
  drive_gubun6    VARCHAR2(1),
  total_limit6    VARCHAR2(7),
  usage6          VARCHAR2(7),
  contents        VARCHAR2(300),
  PRIMARY KEY (branch_code, terminal_no, tx_id)
);

/*___ atms_04001180 ___*/
CREATE TABLE atms_04001180 (
  tx_id       TIMESTAMP NOT NULL, -- 전문에는 없지만 추가된 컬럼
  jisa_code   CHAR(2) NOT NULL,    -- 전문에는 없지만 추가된 컬럼
  branch_code CHAR(4) NOT NULL,
  corner_code CHAR(2) NOT NULL,
  crt_no      VARCHAR2(15),
  PRIMARY KEY (branch_code, corner_code, tx_id)
);

/*___ atms_04001190 ___*/
CREATE TABLE atms_04001190 (
  tx_id          TIMESTAMP NOT NULL, -- 전문에는 없지만 추가된 컬럼
  notice_code    CHAR(2) NOT NULL,
  notice_content VARCHAR2(350),
  PRIMARY KEY (tx_id)
);

/*___ atms_04001200 ___*/
CREATE TABLE atms_04001200 (
  tx_id          TIMESTAMP NOT NULL, -- 전문에는 없지만 추가된 컬럼
  jisa_code      CHAR(2) NOT NULL,    -- 전문에는 없지만 추가된 컬럼
  branch_code    CHAR(4) NOT NULL,
  corner_code    CHAR(2) NOT NULL,
  corp_code      CHAR(2),
  inout_datetime TIMESTAMP,
  inout_gubun    CHAR(1),
  task_gubun     CHAR(1),
  auth_emp_no    CHAR(10),
  PRIMARY KEY (branch_code, corner_code, inout_datetime)
);


/*___ atms_agent_mng ___*/
CREATE TABLE atms_agent_mng (
  tx_id             TIMESTAMP NOT NULL,
  jisa_code         CHAR(2) NOT NULL,
  emp_name          VARCHAR2(20) NOT NULL,
  emp_regno         CHAR(13),
  emp_enable        CHAR(1),
  emp_gubun         CHAR(1),
  corp_gubun        CHAR(1),
  emp_phone_no      VARCHAR2(20),
  digital_seal_url  VARCHAR2(100),
  digital_sign_url  VARCHAR2(100),
  PRIMARY KEY (tx_id)
);