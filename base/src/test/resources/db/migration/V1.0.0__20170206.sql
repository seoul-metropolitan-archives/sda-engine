/**
######################################################################
##
## BGF 신한은행 위탁관리 :: Flyway 스크립트
##
######################################################################
##    장애관리 전문 관련 테이블
######################################################################
**/

/**
[테이블 생성]
## 01. atms_01001110 :: 장애통보
## 02. atms_01001120 :: 출동요청
## 03. atms_01001130 :: 도착예정
## 04. atms_01001140 :: 도착통보
## 05. atms_01001150 :: 조치결과
## 06. atms_01001160 :: 장애출동취소요청
## 07. atms_01001170 :: 고객대기정보통보
## 08. atms_error_status :: 실시간장애모니터링
## 09. atms_error_handle_mng :: 장애조치사항관리
## 10. atms_01001180 :: 동시다발장애출동요청
## 11. atms_01001190 :: 동시다발장애조치결과
## 12. atms_01001230 :: 후처리내역통보
## 13. atms_01001240 :: 후처리내역 조치예정/완료
## 14. atms_minwon_mng :: 민원관리

**/

/*___ atms_01001110 ___*/
CREATE TABLE atms_01001110 (
  error_datetime              TIMESTAMP NOT NULL,
  jisa_code                   CHAR(2) NOT NULL, -- 전문에는 없지만 추가된 컬럼
  branch_code                 CHAR(4) NOT NULL,
  branch_name                 VARCHAR2(40),
  branch_tel                  VARCHAR2(15),
  corner_code                 CHAR(2) NOT NULL,
  corner_name                 VARCHAR2(40),
  terminal_no                 CHAR(4) NOT NULL,
  place_gubun                 CHAR(1),
  oper_time_gubun             CHAR(1),
  runtime                     VARCHAR2(11),
  prod_serial_no              VARCHAR2(15),
  terminal_corp_code          CHAR(1),
  model_code                  CHAR(3),
  progress_status             CHAR(1),
  stext_gubun                 CHAR(1),
  terminal_error1             CHAR(5),
  terminal_error2             CHAR(10),
  total_classify_code         CHAR(2),
  error_type                  CHAR(1),
  cash_part_status            CHAR(1),
  check_part_status           CHAR(1),
  portfolio_part_status       CHAR(1),
  jrnl_part_status            CHAR(1),
  rtrvl_box_status            CHAR(1),
  card_part_status            CHAR(1),
  bnkb_part_status            CHAR(1),
  giro_part_status            CHAR(1),
  suspend_status              CHAR(1),
  hw_error_status             CHAR(1),
  maintenance_status          CHAR(1),
  version                     CHAR(8),
  enable_des_board            CHAR(1),
  enable_ic_card              CHAR(1),
  enable_emv                  CHAR(1),
  enable_ir                   CHAR(1),
  enable_rf                   CHAR(1),
  enable_fingerprint          CHAR(1),
  encryption_status           CHAR(1),
  terminal_info               VARCHAR2(100),
  atms_terminal_no            VARCHAR2(10),
  cash_part_status_50k_won    CHAR(1),
  atmc_excclc_terminal_error  CHAR(1),
  atmc_excclc_exec_result     CHAR(1),
  terminal_ver_info           VARCHAR2(10),
  card_issued_terminal_status CHAR(1),
  id_scanner_status           CHAR(1),
  bio_scanner_status          CHAR(1),
  scrty_card_highend_atm_only CHAR(1),
  card_style_otp              CHAR(1),
  s20_general                 CHAR(1),
  s20_frpy                    CHAR(1),
  sline_general               CHAR(1),
  sline_frpy                  CHAR(1),
  fourtune_general            CHAR(1),
  fourtune_frpy               CHAR(1),
  rcppay_bnkb                 CHAR(1),
  PRIMARY KEY (error_datetime, branch_code, corner_code, terminal_no)
);

/*___ atms_01001120 ___*/
CREATE TABLE atms_01001120 (
  callee_req_datetime       TIMESTAMP NOT NULL,
  error_datetime            TIMESTAMP NOT NULL,
  callee_req_seq_no         VARCHAR2(10),
  callee_req_chasu          CHAR(2),
  callee_chasu_gubun        CHAR(1),
  jisa_code                 CHAR(2) NOT NULL, -- 전문에는 없지만 추가된 컬럼
  branch_code               CHAR(4) NOT NULL,
  branch_name               VARCHAR2(40),
  corner_code               CHAR(2) NOT NULL,
  corner_name               VARCHAR2(40),
  terminal_no               CHAR(4) NOT NULL,
  terminal_corp_code        CHAR(1),
  model_code                VARCHAR2(3),
  callee_req_gubun_code     CHAR(1),
  callee_req_reason_code    CHAR(1),
  callee_emp_name           VARCHAR2(10),
  callee_emp_telno          VARCHAR2(13),
  terminal_error_code_1     VARCHAR2(5),
  terminal_error_code_2     VARCHAR2(10),
  total_classify_code       VARCHAR2(2),
  callee_req_unusl          VARCHAR2(200),
  crt_no                    VARCHAR2(15),
  customer_wait_enable      CHAR(1),
  part_mng_callee_enable    CHAR(1),
  PRIMARY KEY (error_datetime, branch_code, corner_code, terminal_no)
);

/*___ atms_01001130 ___*/
CREATE TABLE atms_01001130 (
  jisa_code                 CHAR(2) NOT NULL, -- 전문에는 없지만 추가된 컬럼
  branch_code               CHAR(4) NOT NULL,
  corner_code               CHAR(2) NOT NULL,
  terminal_no               CHAR(4) NOT NULL,
  error_datetime            TIMESTAMP NOT NULL,
  callee_req_seq_no         VARCHAR2(10),
  callee_req_chasu          CHAR(2),
  callee_req_gubun          CHAR(1),
  callee_chasu_gubun        CHAR(1),
  callee_plan_datetime      TIMESTAMP NOT NULL,
  arrival_plan_datetime     TIMESTAMP NOT NULL,
  corp_receipt_emp_name     VARCHAR2(10),
  corp_callee_emp_name      VARCHAR2(10),
  corp_callee_emp_telno     VARCHAR2(13),
  unusl                     VARCHAR2(100),
  crt_no                    VARCHAR2(15),
  PRIMARY KEY (error_datetime, branch_code, corner_code, terminal_no)
);


/*___ atms_01001140 ___*/
CREATE TABLE atms_01001140 (
  jisa_code                 CHAR(2) NOT NULL, -- 전문에는 없지만 추가된 컬럼
  branch_code               CHAR(4) NOT NULL,
  corner_code               CHAR(2) NOT NULL,
  terminal_no               CHAR(4) NOT NULL,
  error_datetime            TIMESTAMP NOT NULL,
  callee_req_seq_no         VARCHAR2(10),
  callee_req_chasu          CHAR(2),
  callee_req_gubun          CHAR(1),
  callee_chasu_gubun        CHAR(1),
  corner_arrival_datetime   TIMESTAMP NOT NULL,
  arrival_corp_code         VARCHAR2(1),
  unusl                     VARCHAR2(100),
  crt_no                    VARCHAR2(15),
  PRIMARY KEY (error_datetime, branch_code, corner_code, terminal_no)
);

/*___ atms_01001150 ___*/
CREATE TABLE atms_01001150 (
  jisa_code                  CHAR(2) NOT NULL, -- 전문에는 없지만 추가된 컬럼
  branch_code                CHAR(4) NOT NULL,
  corner_code                CHAR(2) NOT NULL,
  terminal_no                CHAR(4) NOT NULL,
  error_datetime             TIMESTAMP NOT NULL,
  callee_req_seq_no          VARCHAR2(10),
  callee_req_chasu           CHAR(2),
  callee_req_gubun           CHAR(1),
  callee_chasu_gubun         CHAR(1),
  handle_result              CHAR(1),
  handle_fail_reason         VARCHAR2(1),
  as_method                  VARCHAR2(1),
  handle_datetime            TIMESTAMP NOT NULL,
  handle_emp_name            VARCHAR2(10),
  handle_emp_telno           VARCHAR2(13),
  handle_content_code        CHAR(1),
  handle_unusl               VARCHAR2(200),
  crt_no                     VARCHAR2(15),
  PRIMARY KEY (error_datetime, branch_code, corner_code, terminal_no)
);

/*___ atms_01001160 ___*/
CREATE TABLE atms_01001160 (
  jisa_code                   CHAR(2) NOT NULL, -- 전문에는 없지만 추가된 컬럼
  branch_code                 CHAR(4) NOT NULL,
  corner_code                 CHAR(2) NOT NULL,
  terminal_no                 CHAR(4) NOT NULL,
  error_datetime              TIMESTAMP NOT NULL,
  callee_req_seq_no           VARCHAR2(10),
  callee_req_chasu            CHAR(2),
  callee_req_gubun_code       CHAR(1),
  callee_chasu_gubun          CHAR(1),
  cancle_req_datetime         TIMESTAMP NOT NULL,
  callee_cancle_reason_code   VARCHAR2(1),
  unusl                       VARCHAR2(200),
  crt_no                      VARCHAR2(15),
  callee_req_reason_code      VARCHAR2(1),
  terminal_error_code_1       VARCHAR2(5),
  terminal_error_code_2       VARCHAR2(10),
  total_classify_code         VARCHAR2(2),
  PRIMARY KEY (error_datetime, branch_code, corner_code, terminal_no)
);

/*___ atms_01001170 ___*/
CREATE TABLE atms_01001170 (
  jisa_code                   CHAR(2) NOT NULL, -- 전문에는 없지만 추가된 컬럼
  branch_code                 CHAR(4) NOT NULL,
  corner_code                 CHAR(2) NOT NULL,
  terminal_no                 CHAR(4) NOT NULL,
  error_datetime              TIMESTAMP NOT NULL,
  callee_req_seq_no           VARCHAR2(10),
  callee_req_chasu            CHAR(2),
  callee_req_gubun_code       CHAR(1),
  callee_chasu_gubun          CHAR(1),
  change_gubun_code           CHAR(1),
  unusl                       VARCHAR2(200),
  crt_no                      VARCHAR2(15),
  PRIMARY KEY (error_datetime, branch_code, corner_code, terminal_no)
);

/*___ atms_error_status ___*/
CREATE TABLE atms_error_status (
  jisa_code                   CHAR(2) NOT NULL, -- 전문에는 없지만 추가된 컬럼
  branch_code                 CHAR(4) NOT NULL,
  corner_code                 CHAR(2) NOT NULL,
  terminal_no                 CHAR(4) NOT NULL,
  error_datetime              TIMESTAMP NOT NULL,
  callee_req_datetime         TIMESTAMP,
  callee_plan_datetime        TIMESTAMP,
  arrival_plan_datetime       TIMESTAMP,
  corner_arrival_datetime     TIMESTAMP,
  handle_datetime             TIMESTAMP,
  cancle_req_datetime         TIMESTAMP,
  elapsed_time                TIMESTAMP,
  callee_req_seq_no           CHAR(10),
  tx_id                       CHAR(7),
  error_type                  CHAR(1),
  error_process_status        NUMBER(1),
  cash_10k_empty_status       CHAR(1),
  cash_50k_empty_status       CHAR(1),
  callee_gubun                CHAR(1), -- 1: 자동출동 , 2: 자체출동
  PRIMARY KEY (error_datetime, branch_code, corner_code, terminal_no)
);

/*___ atms_error_handle_mng ___*/
CREATE TABLE atms_error_handle_mng (
  jisa_code                 CHAR(2) NOT NULL, -- 전문에는 없지만 추가된 컬럼
  branch_code               CHAR(4) NOT NULL,
  corner_code               CHAR(2) NOT NULL,
  terminal_no               CHAR(4) NOT NULL,
  error_datetime            TIMESTAMP NOT NULL,
  notice_content            VARCHAR2(300),
  customer_info             VARCHAR2(300),
  handle_content            VARCHAR2(300),
  last_modify_datetime      TIMESTAMP,
  last_modify_emp_name      VARCHAR2(20),
  PRIMARY KEY (branch_code, corner_code, terminal_no, error_datetime)
);

/*___ atms_01001180 ___*/
CREATE TABLE atms_01001180 (
  error_datetime          TIMESTAMP NOT NULL,
  mng_stnd_date           CHAR(2),
  mng_stnd_critic         CHAR(3),
  error_count             CHAR(3),
  jisa_code               CHAR(2) NOT NULL, -- 전문에는 없지만 추가된 컬럼
  branch_code             CHAR(4),
  terminal_no             CHAR(4),
  corner_code             CHAR(2),
  model_code              CHAR(3),
  corner_name             VARCHAR2(40),
  unusl                   VARCHAR2(100),
  callee_seq_no           CHAR(4),
  error_method            CHAR(1),
  error_type              CHAR(1),
  total_classify_code     CHAR(2),
  terminal_error1         CHAR(5),
  terminal_error2         CHAR(10),
  handle_datetime         TIMESTAMP,
  handle_emp_name         VARCHAR2(20),
  memo_content            VARCHAR2(140),
  callee_req_date         TIMESTAMP,
  PRIMARY KEY (branch_code, corner_code, terminal_no, error_datetime)
);

/*___ atms_01001190 ___ 미사용예정*/
CREATE TABLE atms_01001190 (
  handle_datetime         TIMESTAMP NOT NULL,
  mng_stnd_date           CHAR(2),
  mng_stnd_critic         CHAR(3),
  error_count             CHAR(3),
  jisa_code               CHAR(2) NOT NULL, -- 전문에는 없지만 추가된 컬럼
  branch_code             CHAR(4),
  terminal_no             CHAR(4),
  corner_code             CHAR(2),
  model_code              CHAR(3),
  corner_name             VARCHAR2(40),
  handle_emp_name         VARCHAR2(10),
  memo_content            VARCHAR2(140),
  callee_req_date         TIMESTAMP,
  callee_seq_no           CHAR(4),
  error_method            CHAR(1),
  PRIMARY KEY (branch_code, corner_code, terminal_no, handle_datetime)
);

/*___ atms_01001230 ___*/
CREATE TABLE atms_01001230 (
  jisa_code               CHAR(2) NOT NULL, -- 전문에는 없지만 추가된 컬럼
  branch_code             CHAR(4),
  corner_code             CHAR(2),
  terminal_no             CHAR(4),
  stext_chasu             CHAR(1),
  handle_occur_datetime   TIMESTAMP,
  handle_seq_no           CHAR(6),
  handle_notice_datetime  TIMESTAMP,
  handle_notice_emp_name  VARCHAR2(20),
  handle_notice_emp_telno VARCHAR2(20),
  handle_req_desc         VARCHAR2(200),
  handle_status           CHAR(1),
  sms_enable              CHAR(1),
  customer_name           VARCHAR2(20),
  customer_telno          VARCHAR2(15),
  error_type              CHAR(1),
  account_no              VARCHAR2(20),
  deal_seq_no             CHAR(4),
  deal_amount             CHAR(8),
  handle_datetime         TIMESTAMP,
  handle_emp_name         VARCHAR2(20),
  handle_emp_telno        VARCHAR2(20),
  handle_desc             VARCHAR2(200),
  PRIMARY KEY (branch_code, corner_code, terminal_no, handle_occur_datetime, handle_seq_no)
);

/*___ atms_01001240 ___미사용예정*/
CREATE TABLE atms_01001240 (
  jisa_code               CHAR(2) NOT NULL, -- 전문에는 없지만 추가된 컬럼
  branch_code             CHAR(4),
  corner_code             CHAR(2),
  terminal_no             CHAR(4),
  handle_seq_no           CHAR(6),
  handle_datetime         TIMESTAMP,
  handle_emp_name         VARCHAR2(20),
  handle_emp_telno        VARCHAR2(20),
  handle_desc             VARCHAR2(200),
  handle_status           CHAR(1),
  PRIMARY KEY (branch_code, corner_code, terminal_no, handle_datetime, handle_seq_no)
);

/*___ atms_minwon_mng ___*/
CREATE TABLE atms_minwon_mng (
  jisa_code                 CHAR(2) NOT NULL, -- 전문에는 없지만 추가된 컬럼
  branch_code               CHAR(4) NOT NULL,
  corner_code               CHAR(2) NOT NULL,
  terminal_no               CHAR(4) NOT NULL,
  reg_datetime              TIMESTAMP NOT NULL, -- 작성일시
  minwon_type               CHAR(2),
  minwon_status             CHAR(1),
  handle_dept               CHAR(1),
  minwon_content            VARCHAR2(300),
  handle_content            VARCHAR2(300),
  update_datetime           TIMESTAMP, -- 변경일시
  last_modify_emp_name      VARCHAR2(20), -- 민원작성자
  PRIMARY KEY (branch_code, corner_code, terminal_no, reg_datetime)
);