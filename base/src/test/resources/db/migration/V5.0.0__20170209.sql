/**
######################################################################
##
## BGF 신한은행 위탁관리 :: Flyway 스크립트
##
######################################################################
##   코너환경점검관리 전문 관련 테이블
######################################################################
**/

/**
[테이블 생성]
## 01. atms_05001110    :: 코너일일점검결과
## 02. atms_05001120    :: 코너일일점검사진정보
## 03. atms_05001130    :: 코너환경관리결과
## 04. atms_05001140    :: 코너환경관리사진정보
## 05. atms_overhaul_plan   :: 환경시설물관리계획
**/

/*___ atms_05001110 ___*/
CREATE TABLE atms_05001110 (
  jisa_code                 CHAR(2) NOT NULL,    -- 전문에는 없지만 추가된 컬럼
  branch_code               CHAR(4) NOT NULL,
  corner_code               CHAR(2) NOT NULL,
  overhaul_date             TIMESTAMP NOT NULL,
  corner_name               VARCHAR2(40),
  terminal_inside_cleaning  CHAR(1),
  terminal_cleaning         CHAR(1),
  intercom_cleaning         CHAR(1),
  floar_cleaning            CHAR(1),
  garbagecan_cleaning       CHAR(1),
  glass_clean_confirm       CHAR(1),
  portfolio_paper           CHAR(1),
  jrnl_paper                CHAR(1),
  bnkb_ribbon               CHAR(1),
  noticeguide               CHAR(1),
  etc_notice                CHAR(1),
  intercom                  CHAR(1),
  dvr                       CHAR(1),
  fluorescent_light         CHAR(1),
  cooler                    CHAR(1),
  heater                    CHAR(1),
  booth_overhaul            CHAR(1),
  check_residual_quantity   CHAR(1),
  rtrvl_box                 CHAR(1),
  billboard_lighting        CHAR(1),
  unusl                     VARCHAR2(80),
  report_emp                VARCHAR2(20),
  office_name               VARCHAR2(40),
  take_photo_enable         CHAR(1),
  PRIMARY KEY (branch_code , corner_code , overhaul_date)
);

/*___ atms_05001120 ___*/
CREATE TABLE atms_05001120 (
  jisa_code                 CHAR(2) NOT NULL,    -- 전문에는 없지만 추가된 컬럼
  branch_code               CHAR(4) NOT NULL,
  corner_code               CHAR(2) NOT NULL,
  overhaul_date             TIMESTAMP NOT NULL,
  corner_name               VARCHAR2(40),
  photo_url                 VARCHAR2(100),
  outside_billboard_photo   VARCHAR2(100),
  gate_photo                VARCHAR2(100),
  floar_photo               VARCHAR2(100),
  booth_left_photo          VARCHAR2(100),
  booth_right_photo         VARCHAR2(100),
  ceiling_photo             VARCHAR2(100),
  terminal_top_photo        VARCHAR2(100),
  terminal_bottom_photo     VARCHAR2(100),
  PRIMARY KEY (branch_code , corner_code, overhaul_date)
);

/*___ atms_05001130 ___*/
CREATE TABLE atms_05001130 (
  jisa_code                 CHAR(2) NOT NULL,    -- 전문에는 없지만 추가된 컬럼
  branch_code               CHAR(4) NOT NULL,
  corner_code               CHAR(2) NOT NULL,
  overhaul_date             TIMESTAMP NOT NULL,
  corner_name               VARCHAR2(40),
  terminal_front_glass      CHAR(1),
  terminal_front            CHAR(1),
  terminal_cracks           CHAR(1),
  mornitor                  CHAR(1),
  bnkb_part_entrance_cracks CHAR(1),
  card_part_entrance_cracks CHAR(1),
  intercom                  CHAR(1),
  board_cradle              CHAR(1),
  branch_floar              CHAR(1),
  inside_wall               CHAR(1),
  outside_wall              CHAR(1),
  branch_glass              CHAR(1),
  ceiling                   CHAR(1),
  fluorescent_light_glass   CHAR(1),
  sticking_billboard        CHAR(1),
  garbagecan                CHAR(1),
  cooler_heater             CHAR(1),
  branch_periphery          CHAR(1),
  unusl                     VARCHAR2(80),
  overhaul_emp              VARCHAR2(20),
  office_name               VARCHAR2(40),
  take_photo_enable         CHAR(1),
  transmit_round_gubun      CHAR(1),
  PRIMARY KEY (branch_code , corner_code , overhaul_date)
);

/*___ atms_05001140 ___*/
CREATE TABLE atms_05001140 (
  jisa_code                 CHAR(2) NOT NULL,    -- 전문에는 없지만 추가된 컬럼
  branch_code               CHAR(4) NOT NULL,
  corner_code               CHAR(2) NOT NULL,
  overhaul_date             TIMESTAMP NOT NULL,
  corner_name               VARCHAR2(40),
  photo_url                 VARCHAR2(100),
  terminal_front_glass      VARCHAR2(100),
  terminal_front            VARCHAR2(100),
  terminal_cracks           VARCHAR2(100),
  monitor                   VARCHAR2(100),
  bnkb_part_entrance_cracks VARCHAR2(100),
  card_part_entrance_cracks VARCHAR2(100),
  intercom                  VARCHAR2(100),
  board_cradle              VARCHAR2(100),
  branch_floor              VARCHAR2(100),
  inside_wall               VARCHAR2(100),
  outside_wall              VARCHAR2(100),
  branch_glass              VARCHAR2(100),
  ceiling                   VARCHAR2(100),
  fluorescent_light         VARCHAR2(100),
  sticking_billboard        VARCHAR2(100),
  garbagecan                VARCHAR2(100),
  cooler_heater             VARCHAR2(100),
  branch_periphery          VARCHAR2(100),
  PRIMARY KEY (branch_code , corner_code , overhaul_date)
);


/*___ atms_overhaul_plan ___*/
CREATE TABLE atms_overhaul_plan (
  jisa_code                 CHAR(2) NOT NULL,    -- 전문에는 없지만 추가된 컬럼
  branch_code               CHAR(4) NOT NULL,
  corner_code               CHAR(2) NOT NULL,
  overhaul_date             TIMESTAMP NOT NULL,
  overhaul_gubun            CHAR(1),
  PRIMARY KEY (branch_code , corner_code , overhaul_date)
);
