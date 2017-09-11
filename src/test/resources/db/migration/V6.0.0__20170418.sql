/**
######################################################################
##
## BGF 신한은행 위탁관리 :: Flyway 스크립트
##
######################################################################
##   SLA 관련 테이블
######################################################################
**/

/**
[테이블 생성]
## 01. atms_sla_10    :: 패널티 내역 + 소명내역
## 02. atms_sla_20    :: 현금부족 내역 + 소명내역
## 03. atms_sla_30    :: 민원관리 내역
## 04. atms_sla_40    :: 해피콜
## 05. atms_sla_50    :: 운영자금증감률
## 06. atms_sla_60    :: 운영자금시재검사
## 07. atms_sla_70    :: 촐동평균시간 + 소명내역
## 08. atms_sla_80    :: 출동준수율
## 09. atms_sla_f0    :: 1차장애처리 완료율 +  소명내역
## 10. atms_sla_a0    :: 환경점검
## 11. atms_sla_b0    :: 기기점검내역
## 12. atms_sla_e0    :: 자동화기기 가동률
## 13. atms_sla_g1    :: 회수통장카드 관리내역
**/

/*___ atms_sla_10 ___*/
CREATE TABLE atms_sla_10 (
  tx_date               TIMESTAMP   NOT NULL,    -- 집계년월
  tx_id                 CHAR(8)     NOT NULL,    -- 기기번호 (점번 + 기번)
  error_datetime        TIMESTAMP   NOT NULL,    -- 장애발생 일시
  callee_chasu          CHAR(2)     NOT NULL,    -- 출동차수
  callee_gubun          CHAR(1),                 -- 1: 자동출동, 2: 수동출동, 3: 민원출동 4: 자동2차출동, 5: 영업점통보, 6: 수동2차출동
  jisa_code             VARCHAR2(2),             -- 지사코드
  branch_code           VARCHAR2(4),             -- 점번
  branch_name           VARCHAR2(100),           -- 점명
  corner_code           VARCHAR2(2),             -- 코너코드
  corner_name           VARCHAR2(40),            -- 코너명
  terminal_no           VARCHAR2(4),             -- 기번
  security_corp         VARCHAR2(2),             -- 출동업체
  callee_req_reason     VARCHAR2(400),           -- 출동내용
  callee_req_datetime   TIMESTAMP,              -- 출동요청 일시
  arrival_datetime      TIMESTAMP,              -- 도착 일시
  elapsed_time          VARCHAR2(8),             -- 경과시간
  report                VARCHAR2(800),           -- 처리내용
  accept                VARCHAR2(1),             -- 인정유무
  refuse_reason         VARCHAR2(400),           -- 불인정 사유
  PRIMARY KEY (tx_date, tx_id , error_datetime , callee_chasu)
);

/*___ atms_sla_20 ___*/
CREATE TABLE atms_sla_20 (
  tx_date               TIMESTAMP   NOT NULL,    -- 집계년월
  tx_id                 CHAR(8)     NOT NULL,    -- 기기번호 (점번 + 기번)
  error_datetime        TIMESTAMP   NOT NULL,    -- 장애발생 일시
  corp_code             VARCHAR2(2),             -- 업체코드
  jisa_code             VARCHAR2(2),             -- 지사코드
  branch_code           VARCHAR2(4),             -- 점번
  branch_name           VARCHAR2(100),           -- 점명
  corner_code           VARCHAR2(2),             -- 코너코드
  corner_name           VARCHAR2(40),            -- 코너명
  terminal_no           VARCHAR2(4),             -- 기번
  recovery_datetime     TIMESTAMP,               -- 복구 일시
  elapsed_time          VARCHAR2(8),             -- 경과시간
  elapsed_seconds       VARCHAR2(8),             -- 경과시간(초)
  error_content         VARCHAR2(400),            -- 장애내용
  model_name            VARCHAR2(40),            -- 모델명
  accept                VARCHAR2(1),             -- 인정유무
  refuse_reason         VARCHAR2(400),           -- 불인정 사유
  PRIMARY KEY (tx_date, tx_id , error_datetime)
);

/*___ atms_sla_30 ___*/
CREATE TABLE atms_sla_30 (
  tx_date               TIMESTAMP   NOT NULL,    -- 집계년월
  seq_no                CHAR(7)     NOT NULL,    -- 일련번호
  minwon_datetime       TIMESTAMP,               -- 민원발생 일시
  jisa_code             VARCHAR2(2),             -- 지사코드
  branch_code           VARCHAR2(4),             -- 점번
  branch_name           VARCHAR2(100),           -- 점명
  corner_code           VARCHAR2(2),             -- 코너코드
  corner_name           VARCHAR2(40),            -- 코너명
  corp_code             VARCHAR2(2),             -- 업체코드
  reg_type              VARCHAR2(10),            -- 등록구분 (해피콜/민원)
  handle_type           VARCHAR2(1),             -- 접수구분 1: 콜센터접수, 2: VOC접수, 3: 금감원등 대외기관접수
  minwon_type           VARCHAR2(1),             -- 민원구분 0: 만족, 1: 불만
  content               VARCHAR2(2000),          -- 내용
  reg_datetime          TIMESTAMP,               -- 등록일
  PRIMARY KEY (tx_date, seq_no)
);

/*___ atms_sla_40 ___*/
CREATE TABLE atms_sla_40 (
  tx_date               TIMESTAMP   NOT NULL,    -- 집계년월
  seq_no                CHAR(10)    NOT NULL,    -- 일련번호
  corp_code             VARCHAR2(2),             -- 업체코드
  gubun                 VARCHAR2(10),            -- 장애/민원상담
  jisa_code             VARCHAR2(2),             -- 지사코드
  branch_code           VARCHAR2(4),             -- 점번
  branch_name           VARCHAR2(100),           -- 점명
  corner_code           VARCHAR2(2),             -- 코너코드
  corner_name           VARCHAR2(50),            -- 코너명
  terminal_no           VARCHAR2(4),             -- 기번
  callee_req_datetime   TIMESTAMP,               -- 출동요청일시
  checkpoint_score1     VARCHAR2(1),             -- 충분설명(점수) 0: 대상외, 1: 민원, 2: 미흡, 3: 보통, 4: 만족, 5: 칭찬
  checkpoint_score2     VARCHAR2(1),             -- 인사실시(점수) 0: 대상외, 1: 민원, 2: 미흡, 3: 보통, 4: 만족, 5: 칭찬
  checkpoint_score3     VARCHAR2(1),             -- 친절투어(점수) 0: 대상외, 1: 민원, 2: 미흡, 3: 보통, 4: 만족, 5: 칭찬
  checkpoint_score4     VARCHAR2(1),             -- 사과실시(점수) 0: 대상외, 1: 민원, 2: 미흡, 3: 보통, 4: 만족, 5: 칭찬
  happycall_agent       VARCHAR2(20),            -- 해피콜실시자
  happycall_datetime    TIMESTAMP,               -- 해피콜실시 일시
  error_datetime        TIMESTAMP,               -- 장애발생 일시
  callee_req_user       VARCHAR2(20),            -- 출동요청자
  customer              VARCHAR2(20),            -- 고객명
  tel_no                VARCHAR2(20),            -- 전화번호
  content               VARCHAR2(1024),          -- 고객의견
  PRIMARY KEY (tx_date, seq_no)
);

/*___ atms_sla_50 ___*/
CREATE TABLE atms_sla_50 (
  tx_date               TIMESTAMP   NOT NULL,    -- 집계년월
  corp_code             VARCHAR2(2),             -- 업체코드
  tx_datetime           TIMESTAMP,               -- 일시
  ocm_amount            VARCHAR2(12),            -- OCM산출금액 (단위: 천원)
  ocm_amount2           VARCHAR2(12),            -- OCM산출금액 * 1.8 (단위: 천원)
  account_balance       VARCHAR2(12),            -- 업체잔고액 (단위: 천원)
  PRIMARY KEY (tx_date, corp_code, tx_datetime)
);

/*___ atms_sla_60 ___*/
CREATE TABLE atms_sla_60 (
  tx_date               TIMESTAMP   NOT NULL,    -- 집계년월
  corp_code             VARCHAR2(2),             -- 업체코드
  reg_datetime          TIMESTAMP,               -- 등록일자
  equals                VARCHAR2(10),            -- 일치여부
  etc                   VARCHAR2(400),           -- 비고
  PRIMARY KEY (tx_date, corp_code, reg_datetime)
);

/*___ atms_sla_70 ___*/
CREATE TABLE atms_sla_70 (
  tx_date                      TIMESTAMP   NOT NULL,    -- 집계년월
  tx_id                        CHAR(8)     NOT NULL,   -- 기기번호 (점번 + 기번)
  error_datetime               TIMESTAMP   NOT NULL,   -- 장애발생 일시
  callee_chasu                 CHAR(2)     NOT NULL,   -- 출동차수
  customer_wait                VARCHAR2(1),            -- 고객대기유무
  eval_type                    VARCHAR2(10),           -- 평가구분
  corp_code                    VARCHAR2(2),            -- 업체코드
  callee_type                  VARCHAR2(10),           -- 출동형태
  jisa_code                    VARCHAR2(2),            -- 지사코드
  branch_code                  VARCHAR2(4),            -- 점번
  branch_name                  VARCHAR2(100),          -- 점명
  corner_code                  VARCHAR2(2),            -- 코너코드
  corner_name                  VARCHAR2(50),           -- 코너명
  location                     VARCHAR2(20),           -- 위치구분
  area                         VARCHAR2(10),           -- 지역구분
  terminal_no                  VARCHAR2(4),            -- 기번
  callee_gubun                 VARCHAR2(10),           -- 출동구분
  callee_req_datetime          TIMESTAMP,              -- 출동요청 일시
  arrival_plan_datetime        TIMESTAMP,              -- 도착 예정 일시
  arrival_datetime             TIMESTAMP,              -- 도착 일시
  arrival_type                 VARCHAR2(20),           -- 도착처리방법법
  callee_req_elapsed_time      VARCHAR2(8),            -- 출동경과시간
  callee_req_elapsed_seconds   VARCHAR2(8),            -- 출동경과시간(초)
  arrival_elapsed_time         VARCHAR2(8),            -- 도착준수시간
  arrival_elapsed_seconds      VARCHAR2(8),            -- 도착준수시간(초)
  report_datetime              TIMESTAMP,              -- 도착후 업체보고 예정일시
  error_content                VARCHAR2(400),          -- 처리내용
  model_name                   VARCHAR2(40),            -- 모델명
  accept                       VARCHAR2(1),            -- 인정유무
  refuse_reason                VARCHAR2(400),          -- 불인정 사유
  PRIMARY KEY (tx_date, tx_id , error_datetime , callee_chasu)
);


/*___ atms_sla_80 ___*/
CREATE TABLE atms_sla_80 (
  tx_date               TIMESTAMP   NOT NULL,    -- 집계년월
  tx_id                        CHAR(8)     NOT NULL,    -- 점번 + 기번(기기번호)
  error_datetime               TIMESTAMP   NOT NULL,   -- 장애발생일시
  callee_chasu                 CHAR(2)     NOT NULL,    -- 출동차수
  customer_wait_enable         CHAR(1) ,                 -- 고객대기유무(1: 유,0: 무)
  corp_code                    VARCHAR2(2),               -- 업체코드
  callee_req_gubun             VARCHAR2(10),              -- 출동형태(장애/민원)
  jisa_code                    VARCHAR2(2),               -- 지사코드
  branch_code                  VARCHAR2(4),               -- 점번
  branch_name                  VARCHAR2(100),             -- 점명
  corner_code                  VARCHAR2(2),               -- 코너코드
  corner_name                  VARCHAR2(50),              -- 코너명
  location                     VARCHAR2(20),             -- 위치구분
  area                         VARCHAR2(10),             -- 지역구분
  terminal_no                  CHAR(4),                   -- 기번
  callee_gubun                 VARCHAR2(10),              -- 출동구분(수동/자동)
  callee_req_datetime          TIMESTAMP,               -- 출동요청일시
  arrival_plan_datetime        TIMESTAMP,               -- 도착예정
  arrival_datetime             TIMESTAMP,               -- 도착일시
  handle_method                VARCHAR2(20),              -- 도착처리방법(상담원확정/해제중출동/정상/일괄등록)
  callee_elapsed_time          VARCHAR2(8),               -- 출동경과시간(출동요청~도착까지)
  callee_elapsed_seconds       VARCHAR2(8),               -- 출동경과시간(출동요청~도착까지)(초)
  arrival_elapsed_time         VARCHAR2(8),               -- 도착준수시간(도착예정~도착까지)
  arrival_elapsed_seconds      VARCHAR2(8),               -- 도착준수시간(도착예정~도착까지)(초)
  arrival_plan_report_datetime TIMESTAMP,                -- 도착후 업체보고 예정일자(YYYYMMDD) + 예정시간(HHMMSS)
  error_content                VARCHAR2(400),              -- 장애내용
  model_name                   VARCHAR2(40),               -- 기종
  PRIMARY KEY (tx_date, tx_id , error_datetime , callee_chasu)
);

/*___ atms_sla_f0 ___*/
CREATE TABLE atms_sla_f0 (
  tx_date               TIMESTAMP   NOT NULL,    -- 집계년월
  tx_id                 CHAR(8)     NOT NULL,    -- 점번 + 기번(기기번호)
  error_datetime        TIMESTAMP   NOT NULL,   -- 장애발생일시
  callee_chasu          CHAR(2)     NOT NULL,    -- 출동차수
  corp_code             VARCHAR2(2),               -- 업체코드
  jisa_code             VARCHAR2(2),               -- 지사코드
  branch_code           VARCHAR2(4) ,              -- 점번
  branch_name           VARCHAR2(100),             -- 점명
  corner_code           VARCHAR2(2) ,              -- 코너코드
  corner_name           VARCHAR2(50),              -- 코너명
  location              VARCHAR2(20),              -- 위치구분
  area                  VARCHAR2(10),              -- 지역구분
  terminal_no           CHAR(4),                   -- 기번
  callee_gubun          VARCHAR2(20),              -- 출동구분(자동출동/수동출동/민원출동/자동2차출동/영업점통보/수동2차출동)
  callee_result         VARCHAR2(20),              -- 결과(출동요청/업체접수/도착예정/도착/조치완료/조치불가)
  callee_req_datetime   TIMESTAMP,               -- 출동요청일시
  arrival_datetime      TIMESTAMP,               -- 도착일시
  handle_datetime       TIMESTAMP,               -- 완료일시(조치완료)
  recover_datetime      TIMESTAMP,               -- 복구일시
  elapsed_time          VARCHAR2(8),              -- 경과시간
  elapsed_seconds       VARCHAR2(8),              -- 경과시간(초)
  error_content         VARCHAR2(400),             -- 장애내용
  model_name            VARCHAR2(40),             -- 기종
  accept                VARCHAR2(1),              -- 인정유무(0: 인정, 1:불인정)
  refuse_reason         VARCHAR2(400),            -- 불인정 사유
  PRIMARY KEY (tx_date, tx_id , error_datetime , callee_chasu)
);

/*___ atms_sla_a0 ___*/
CREATE TABLE atms_sla_a0 (
  tx_date               TIMESTAMP   NOT NULL,    -- 집계년월
  reg_datetime          TIMESTAMP   NOT NULL,   -- 등록일 + 등록시간
  jisa_code             VARCHAR2(2),               -- 지사코드
  branch_code           VARCHAR2(4) NOT NULL,     -- 점번
  corner_code           VARCHAR2(2) NOT NULL,     -- 코너코드
  overhaul_date         TIMESTAMP,                -- 점검일(YYYYMMDD)
  branch_name           VARCHAR2(100),             -- 점명
  corner_name           VARCHAR2(50),              -- 코너명
  oper_date             TIMESTAMP,               -- 운영일(YYYYMMDD)
  branch_gubun          VARCHAR2(10),              -- 점포구분(코너형태) 점포형/박스형/단독형/특수형/점내
  corp_code             VARCHAR2(2),               -- 업체코드
  take_photo_enable     VARCHAR2(10),              -- 사진촬영유무(미등록/사진촬영/사진불가)
  success_count         VARCHAR2(4),               -- 정상건수
  overhaul_count        VARCHAR2(4),               -- 점검건수
  overhaul_result       VARCHAR2(4),               -- 점검결과(점검/연결안됨/카메라불량)
  overhaul_unusl        VARCHAR2(1024),            -- 점검특이사항
  submit_target_count   VARCHAR2(4),               -- 제출대상건수
  submit_count          VARCHAR2(4),               -- 제출건수
  product_condt         VARCHAR2(10),              -- 상품안내장(대상외/양호/불량)
  terminal_condt        VARCHAR2(10),              -- 자동화기기(대상외/양호/불량)
  ground_condt          VARCHAR2(10),              -- 바닥(대상외/양호/불량)
  garbage_condt         VARCHAR2(10),              -- 휴지통(대상외/양호/불량)
  cash_envelope_condt   VARCHAR2(10),              -- 현금봉투(대상외/양호/불량)
  poster_condt          VARCHAR2(10),              -- 포스터(대상외/양호/불량)
  PRIMARY KEY (tx_date, reg_datetime , branch_code , corner_code)
);

/*___ atms_sla_b0 ___*/
CREATE TABLE atms_sla_b0 (
  tx_date               TIMESTAMP   NOT NULL,    -- 집계년월
  tx_id                 CHAR(8)     NOT NULL,   -- 점번 + 기번
  total_corp            VARCHAR2(2),              -- 토탈업체(BGF : 26)
  jisa_code             VARCHAR2(2),              -- 지사코드
  branch_code           VARCHAR2(4),              -- 점번
  branch_name           VARCHAR2(100),            -- 점명
  corner_code           VARCHAR2(2),              -- 코너코드
  corner_name           VARCHAR2(40),             -- 코너명
  terminal_no           VARCHAR2(4),              -- 기번
  mng_gubun             VARCHAR2(10),                 -- 관리구분(지점관리/본부관리)
  oper_time_gubun       CHAR(1),                 -- 운영시간구분(1:평일,2:365,3:24시,4:기타)
  terminal_corp_name    VARCHAR2(20),             -- 기기업체명
  model_name            VARCHAR2(50),             -- 모델명
  atm_gubun             VARCHAR2(10),             -- ATM/CD구분
  overhaul_datetime     TIMESTAMP   NOT NULL,   -- 점검일시
  PRIMARY KEY (tx_date, tx_id , overhaul_datetime)
);

/*___ atms_sla_e0 ___*/
CREATE TABLE atms_sla_e0 (
  tx_date               TIMESTAMP   NOT NULL,    -- 집계년월
  stnd_date             TIMESTAMP NOT NULL,   -- 기준월(YYYYMM)
  tx_id                 CHAR(8)   NOT NULL,    -- 점번 + 기번(기기번호)
  corp_code             VARCHAR2(2),             -- 업체코드
  jisa_code             VARCHAR2(2),             -- 지사코드
  branch_code           VARCHAR2(4),             -- 점번
  branch_name           VARCHAR2(100),           -- 점명
  corner_code           VARCHAR2(2),             -- 코너코드
  corner_name           VARCHAR2(50),            -- 코너명
  terminal_no           VARCHAR2(4),             -- 기번
  oper_start_time       CHAR(4),                -- 운영시작시간
  oper_end_time         CHAR(4),                -- 운영종료시간
  model_name            VARCHAR2(50),            -- 모델명
  total_oper_time       VARCHAR2(20),            -- 총운영시간
  real_oper_time        VARCHAR2(20),            -- 실제운영시간
  none_oper_time        VARCHAR2(20),            -- 미운영시간
  PRIMARY KEY (tx_date, stnd_date , tx_id)
);

/*___ atms_sla_g1 ___*/
CREATE TABLE atms_sla_g1 (
  tx_date              TIMESTAMP   NOT NULL,    -- 집계년월
  withdraw_datetime    TIMESTAMP   NOT NULL,  -- 회수일 +회수시간
  real_classify        VARCHAR2(20),             -- 실물종류
  real_no              VARCHAR2(100) NOT NULL,  -- 실물번호
  issue_org            VARCHAR2(20),             -- 발행기관
  withdraw_org         VARCHAR2(20),             --회수기관
  tx_id                CHAR(8)  NOT NULL,      -- 점번 + 기번(기기번호)
  jisa_code            VARCHAR2(2),             -- 지사코드
  progress_status      CHAR(1),                 -- 진행현황(0:발생/1:현장보관/3:반환/4:영업점인계/5:폐기
  withdraw_emp_name    VARCHAR2(20),             -- 회수자
  transfer_emp_name    VARCHAR2(20),             -- 인계자
  takeover_emp_name    VARCHAR2(20),             -- 인수자
  return_emp_name      VARCHAR2(20),             -- 반환자
  receive_emp_name     VARCHAR2(20),             -- 수령자
  transfer_datetime    TIMESTAMP,              -- 인계일 + 인계시간
  return_datetime      TIMESTAMP,              -- 반환일 + 반환시간
  storage_unusl        VARCHAR2(1024) ,         -- 보관사유/진행메모
  PRIMARY KEY (tx_date, withdraw_datetime , real_no , tx_id)
);