/**
######################################################################
##
## BGF 신한은행 위탁관리 :: Flyway 스크립트
##
######################################################################
**/

/**
[초기데이터 생성]
## 01. 장애/민원 관리 관련 데이터 생성
##   - 출동취소 이력
**/

/*___ atms_auth_group_map ___*/

/*___ atms_menu ___*/

/*___ atms_program ___*/
INSERT INTO ATMS_PROGRAM (PROG_CD, PROG_NM, PROG_PH, AUTH_CHECK, SCH_AH, SAV_AH, DEL_AH, EXL_AH, FN1_AH, FN2_AH, FN3_AH, FN4_AH, FN5_AH, REMARK, TARGET, CREATED_AT, CREATED_BY, UPDATED_AT, UPDATED_BY)
VALUES ('overhaul_plan', '[자산관리] 점검계획', '/mng/equip/overhaul_plan', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', null, '_self', TO_TIMESTAMP('2017-03-30 16:46:27.498000', 'YYYY-MM-DD HH24:MI:SS.FF6'), 'system', TO_TIMESTAMP('2017-03-30 16:46:27.498000', 'YYYY-MM-DD HH24:MI:SS.FF6'), 'system');