/**
######################################################################
##
## BGF 신한은행 위탁관리 :: Flyway 스크립트
##
######################################################################
**/

/**
[데이터 수정]
## 01. 사용자 테이블에 지사, 부서 코드 추가.
**/

/*___ atms_user ___*/
UPDATE atms_user SET JISA_CODE = '02', DEPT_NO = '01' WHERE USER_CD = 'system';
UPDATE atms_user SET JISA_CODE = '02', DEPT_NO = '01' WHERE USER_CD = 'test';