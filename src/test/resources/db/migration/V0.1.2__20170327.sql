/**
######################################################################
##
## BGF 신한은행 위탁관리 :: Flyway 스크립트
##
######################################################################
**/

/**
[테이블 변경]
## 01. atms_user 에 column 추가

**/

/*___ atms_user ___*/
ALTER TABLE ATMS_STEXT_SEQ modify (
  TARGET NUMBER(2)
);

