/**
######################################################################
##
## BGF 신한은행 위탁관리 :: Flyway 스크립트
##
######################################################################
##   시재관리 전문 관련 테이블
######################################################################
**/

/**
[테이블 생성]
## 01. atms_03001110             :: 시재조회
## 02. atms_03001120             :: 거래내역조회
## 03. atms_03001130             :: 마감조회
## 04. atms_03001140             :: 회수자금결과통보
## 05. atms_03001150             :: 미처리금액보고서
## 06. atms_03001160             :: 추가현송보고서
## 07. atms_03001170             :: 은행현송예측통보
## 08. atms_03001180             :: 현송계획보고
## 09. atms_03001190             :: 운영자금청구서통보
## 10. atms_03001200             :: 현송주기통보
## 11. atms_03001210             :: 시재미확정내역
## 12. atms_03001220             :: 운영자금인수정보통보
## 13. atms_03001230             :: 출납과잉금반환내역서
## 14. atms_branch_close_report  :: 지사별마감일지
## 15. atms_jisa_sije_close      :: 지사별마감일지
**/

/*___ atms_03001110 ___*/
CREATE TABLE atms_03001110 (
  tx_id                        TIMESTAMP NOT NULL, -- 전문에는 없지만 추가된 컬럼
  refer_date                     TIMESTAMP,
  jisa_code                    CHAR(2) NOT NULL,    -- 전문에는 없지만 추가된 컬럼
  branch_code                  CHAR(4) NOT NULL,
  terminal_no                  CHAR(4) NOT NULL,
  cash_withdraw_amt            CHAR(15),
  cash_deposit_amt             CHAR(15),
  cash_amt                     CHAR(15),
  check_withdraw_amt           CHAR(15),
  check_deposit_amt            CHAR(15),
  check_give_enable_count      CHAR(4),
  cash_50k_give_enable_count   CHAR(5),
  PRIMARY KEY (refer_date, branch_code , terminal_no)
);

/*___ atms_03001120 ___*/
CREATE TABLE atms_03001120 (
  jisa_code            CHAR(2) NOT NULL,    -- 전문에는 없지만 추가된 컬럼
  branch_code          CHAR(4) NOT NULL,
  terminal_no          CHAR(4) NOT NULL,
  refer_date           TIMESTAMP NOT NULL,
  refer_statement_no   CHAR(4),
  refer_start_time     TIMESTAMP,
  refer_end_time       TIMESTAMP,
  deal_seq_no          CHAR(4),
  deal_time            TIMESTAMP,
  process_status       CHAR(2),
  locate_gubun         CHAR(2),
  deal_method          CHAR(2),
  deposit_bank_code    CHAR(3),
  deposit_account_no   CHAR(16),
  withdraw_bank_code   CHAR(3),
  withdraw_account_no  CHAR(16),
  deal_amt             CHAR(12),
  PRIMARY KEY (branch_code , terminal_no , deal_time, deal_seq_no)
);

/*___ atms_03001130 ___*/
CREATE TABLE atms_03001130 (
  tx_id                    TIMESTAMP NOT NULL, -- 전문에는 없지만 추가된 컬럼
  jisa_code                CHAR(2) NOT NULL,    -- 전문에는 없지만 추가된 컬럼
  branch_code              CHAR(4) NOT NULL,
  terminal_no              CHAR(4) NOT NULL,
  safe_no                  CHAR(4),
  req_date                 TIMESTAMP,
  close_gubun              CHAR(1),
  cash_give_amt            CHAR(15),
  cash_deposit_amt         CHAR(15),
  cash_sending_amt         CHAR(15),
  rtrvl_amt                CHAR(15),
  check_withdraw_amt       CHAR(15),
  check_deposit_amt        CHAR(15),
  now_day_put_amt          CHAR(15),
  prev_day_put_amt         CHAR(15),
  prev_day_add_put_amt     CHAR(15),
  holiday_add_put_amt      CHAR(15),
  this_day_add_put_amt     CHAR(15),
  atmc_terminal_now_sijae  CHAR(15),
  sijae_cash_50k           CHAR(15),
  close_datetime           TIMESTAMP NOT NULL,
  PRIMARY KEY (tx_id, branch_code , terminal_no)
);

/*___ atms_03001140 ___*/
CREATE TABLE atms_03001140 (
  jisa_code                     CHAR(2) NOT NULL,    -- 전문에는 없지만 추가된 컬럼
  branch_code                   CHAR(4) NOT NULL,
  terminal_no                   CHAR(4) NOT NULL,
  close_date                    TIMESTAMP NOT NULL,
  prev_day_cash_sending_amt     CHAR(15),
  deposit_amt                   CHAR(15),
  give_amt                      CHAR(15),
  close_amt                     CHAR(15),
  none_process_amt              CHAR(15),
  rtrvl_fund                    CHAR(15),
  none_process_at               CHAR(1),
  mng_office                    CHAR(20),
  adjust_lack_amt_count         CHAR(10),
  adjust_lack_amt               CHAR(15),
  PRIMARY KEY (branch_code , terminal_no , close_date)
);

/*___ atms_03001150 ___*/
CREATE TABLE atms_03001150 (
  jisa_code             CHAR(2) NOT NULL,    -- 전문에는 없지만 추가된 컬럼
  branch_code           CHAR(4) NOT NULL,
  terminal_no           CHAR(4) NOT NULL,
  none_process_amt      CHAR(15),
  none_process_seq_no   CHAR(3) NOT NULL,
  deal_date             TIMESTAMP NOT NULL,
  statement_no          CHAR(4),
  deal_bank             CHAR(3), 
  card_account_no       CHAR(20),
  deal_type             CHAR(1), 
  deal_amt              CHAR(15),
  manager_name          CHAR(10),
  customer_name         CHAR(10),
  customer_telno        CHAR(12),
  mng_office            CHAR(20),
  unusl                 CHAR(50),
  process_date          TIMESTAMP,
  send_commission       CHAR(15),
  rtn_commission        CHAR(15),
  PRIMARY KEY (branch_code , terminal_no , deal_date , none_process_seq_no)
);

/*___ atms_03001160 ___*/
CREATE TABLE atms_03001160 (
  tx_id                     TIMESTAMP NOT NULL, -- 전문에는 없지만 추가된 컬럼
  jisa_code                 CHAR(2) NOT NULL,    -- 전문에는 없지만 추가된 컬럼
  branch_code               CHAR(4) NOT NULL,
  terminal_no               CHAR(4) NOT NULL,
  add_cash_sending_seq_no   CHAR(3) NOT NULL,
  cash_sending_date         TIMESTAMP NOT NULL,
  billing_expect_date       TIMESTAMP,
  add_cash_sending_amt      CHAR(15),
  mng_office                CHAR(20),
  close_gubun               CHAR(1),
  cash_sending_stnd_date    TIMESTAMP NOT NULL,
  add_cash_50k_sending_amt  CHAR(15),
  PRIMARY KEY (branch_code , terminal_no , cash_sending_date , add_cash_sending_seq_no, tx_id)
);

/*___ atms_03001170 ___*/
CREATE TABLE atms_03001170 (
  tx_id                TIMESTAMP NOT NULL, -- 전문에는 없지만 추가된 컬럼
  jisa_code            CHAR(2) NOT NULL,    -- 전문에는 없지만 추가된 컬럼
  branch_code          CHAR(4) NOT NULL,
  terminal_no          CHAR(4) NOT NULL,
  cash_sending_date    TIMESTAMP,
  cash_sending_amt     CHAR(15),
  mng_office           CHAR(20),
  cash_50k_sending_amt CHAR(15),
  PRIMARY KEY (branch_code , terminal_no , cash_sending_date , tx_id)
);

/*___ atms_03001180 ___*/
CREATE TABLE atms_03001180 (
  tx_id                TIMESTAMP NOT NULL, -- 전문에는 없지만 추가된 컬럼
  jisa_code            CHAR(2) NOT NULL,    -- 전문에는 없지만 추가된 컬럼
  branch_code          CHAR(4) NOT NULL,
  terminal_no          CHAR(4) NOT NULL,
  cash_sending_date    TIMESTAMP,
  cash_sending_amt     CHAR(15),
  accept_enable        CHAR(1),
  cash_50k_sending_amt CHAR(15),
  PRIMARY KEY (branch_code , terminal_no , cash_sending_date)
);

/*___ atms_03001190 ___*/
CREATE TABLE atms_03001190 (
  tx_id                             TIMESTAMP NOT NULL, -- 전문에는 없지만 추가된 컬럼
  jisa_code                         CHAR(2) NOT NULL,    -- 전문에는 없지만 추가된 컬럼
  branch_code                       CHAR(4) NOT NULL,
  req_date                          TIMESTAMP,
  fund_expense_start_date           TIMESTAMP,
  fund_expense_end_date             TIMESTAMP,
  fund_expense_term                 CHAR(2),
  next_day_cash_sending_amt         CHAR(15),
  now_day_add_cash_sending_amt      CHAR(15),
  now_day_rtrvl_amt                 CHAR(15),
  now_day_lack_amt                  CHAR(15),
  this_day_rtrvl_excpect_amt        CHAR(15),
  next_day_rtrvl_excpect_amt        CHAR(15),
  next_day_billing_amt              CHAR(15),
  total_stock_amt                   CHAR(15),
  before_bdate_stock_amt            CHAR(15),
  before_bdate_recv_amt             CHAR(15),
  before_bdate_cash_sending_amt     CHAR(15),
  before_bdate_give_amt             CHAR(15),
  before_bdate_deposit_amt          CHAR(15),
  mng_office                        CHAR(20),
  this_day_add_cash_sending_amt     CHAR(15),
  this_day_none_load_amt            CHAR(15),
  payment_over_amt                  CHAR(15),
  stext_send_gubun                  CHAR(1),
  PRIMARY KEY (branch_code , req_date, tx_id)
);

/*___ atms_03001200 ___*/
CREATE TABLE atms_03001200 (
  tx_id                   TIMESTAMP NOT NULL, -- 전문에는 없지만 추가된 컬럼
  jisa_code               CHAR(2) NOT NULL,    -- 전문에는 없지만 추가된 컬럼
  branch_code             CHAR(4) NOT NULL,
  terminal_no             CHAR(4) NOT NULL,
  mon_cash_sending_enable CHAR(1) ,
  tue_cash_sending_enable CHAR(1) ,
  wed_cash_sending_enable CHAR(1) ,
  thu_cash_sending_enable CHAR(1) ,
  fri_cash_sending_enable CHAR(1) ,
  PRIMARY KEY (branch_code , terminal_no)
);

/*___ atms_03001210 ___*/
CREATE TABLE atms_03001210 (
  tx_id                   TIMESTAMP NOT NULL, -- 전문에는 없지만 추가된 컬럼
  jisa_code               CHAR(2) NOT NULL,    -- 전문에는 없지만 추가된 컬럼
  branch_code             CHAR(4) NOT NULL,
  terminal_no             CHAR(4) NOT NULL,
  PRIMARY KEY (branch_code , terminal_no , tx_id)
);

/*___ atms_03001220 ___*/
CREATE TABLE atms_03001220 (
  tx_id                      TIMESTAMP NOT NULL, -- 전문에는 없지만 추가된 컬럼
  jisa_code                  CHAR(2) NOT NULL,    -- 전문에는 없지만 추가된 컬럼
  manless_branch_code        CHAR(4) NOT NULL,
  branch_code                CHAR(4) NOT NULL,
  corner_code                CHAR(2) NOT NULL,
  terminal_no                CHAR(4) NOT NULL,
  req_date                   TIMESTAMP NOT NULL,
  req_gubun                  CHAR(1),
  cash_type_code1            CHAR(2),
  cash_type_code1_recv_amt   CHAR(15),
  cash_type_code2            CHAR(2),
  cash_type_code2_recv_amt   CHAR(15),
  cash_type_code3            CHAR(2),
  cash_type_code3_recv_amt   CHAR(15),
  cash_type_code4            CHAR(2),
  cash_type_code4_recv_amt   CHAR(15),
  cash_type_code5            CHAR(2),
  cash_type_code5_recv_amt   CHAR(15),
  cash_sending_car_no        CHAR(20),
  charge_emp_name            VARCHAR2(20),
  charge_emp_regno           CHAR(13),
  charge_emp_photo_url       VARCHAR2(100),
  digital_sign_url           VARCHAR2(100),
  s20_g_card_recv_count      CHAR(4),
  s20_t_card_recv_count      CHAR(4),
  s20pink_g_card_recv_count  CHAR(4),
  s20pink_t_card_recv_count  CHAR(4),
  love_card_recv_count       CHAR(4),
  hipoint_card_recv_count    CHAR(4),
  scrty_card_recv_count      CHAR(4),
  PRIMARY KEY (branch_code , terminal_no , req_date , tx_id)
);

/*___ atms_03001230 ___*/
CREATE TABLE atms_03001230 (
  tx_id              TIMESTAMP NOT NULL, -- 전문에는 없지만 추가된 컬럼
  jisa_code          CHAR(2) NOT NULL,    -- 전문에는 없지만 추가된 컬럼
  branch_code        CHAR(4) NOT NULL,
  terminal_no        CHAR(4) NOT NULL,
  rtn_date           TIMESTAMP,
  confirm_date       TIMESTAMP,
  deal_date          TIMESTAMP,
  rtn_amt            CHAR(15),
  content            CHAR(50),
  charge_center      CHAR(50),
  note               CHAR(50),
  PRIMARY KEY (branch_code , terminal_no , rtn_date , tx_id)
);

/*___ atms_jisa_sije_close ___*/
CREATE TABLE atms_jisa_sije_close (
  tx_id                       TIMESTAMP NOT NULL,
  close_date                  TIMESTAMP NOT NULL,
  jisa_code                   VARCHAR2(2) NOT NULL,
  prev_day_reserve_sije       VARCHAR2(15),
  this_day_cash_deposit_amt   VARCHAR2(15),
  jisa_to_shinhan_send_amt    VARCHAR2(15),
  close_amt                   VARCHAR2(15),
  un_check_amt                VARCHAR2(15),
  sije_mistake_amt            VARCHAR2(15),
  cash_sending_amt            VARCHAR2(15),
  add_cash_sending_amt        VARCHAR2(15),
  jisa_safe_amt               VARCHAR2(15),
  memo_content                VARCHAR2(140),
  user_nm                     VARCHAR2(30),
  PRIMARY KEY (jisa_code , close_date)
);
