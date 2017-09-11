/**
######################################################################
##
## BGF 신한은행 위탁관리 :: Flyway 스크립트
##
######################################################################
**/

/**
[초기데이터 생성]
## 01. 공통코드 입력 - 출동구분
**/

/*___ atms_common_code ___*/

/*
INSERT INTO ATMS_COMMON_CODE (GROUP_CD, GROUP_NM, NAME, CODE, REMARK, SORT, USE_YN, DATA1, DATA2, DATA3, DATA4, DATA5, CREATED_AT, CREATED_BY, UPDATED_AT, UPDATED_BY)
VALUES ('CALLEE_GUBUN', '출동구분코드', '자동출동', '1', null, 1, 'Y', null, null, null, null, null, TO_TIMESTAMP('2017-06-25 19:12:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), 'system', TO_TIMESTAMP('2017-06-25 19:12:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), 'system');
INSERT INTO ATMS_COMMON_CODE (GROUP_CD, GROUP_NM, NAME, CODE, REMARK, SORT, USE_YN, DATA1, DATA2, DATA3, DATA4, DATA5, CREATED_AT, CREATED_BY, UPDATED_AT, UPDATED_BY)
VALUES ('CALLEE_GUBUN', '출동구분코드', '자체출동', '2', null, 2, 'Y', null, null, null, null, null, TO_TIMESTAMP('2017-06-25 19:12:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), 'system', TO_TIMESTAMP('2017-06-25 19:12:00.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), 'system');*/
