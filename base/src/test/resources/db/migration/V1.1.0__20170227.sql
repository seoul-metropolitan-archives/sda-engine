/**
######################################################################
##
## BGF 신한은행 위탁관리 :: Flyway 스크립트
##
######################################################################
**/

/**
[테이블 변경]
## 01. atms_01001130에 column 추가

**/

/*___ atms_user ___*/
ALTER TABLE atms_01001130 add  (
  arrival_plan_gubun CHAR(1),
  security_corp_code CHAR(2)
);

