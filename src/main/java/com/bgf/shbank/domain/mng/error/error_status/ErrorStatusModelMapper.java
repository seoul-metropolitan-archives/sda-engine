package com.bgf.shbank.domain.mng.error.error_status;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Created by tw.jang on 2017-02-09.
 */
@Component
public class ErrorStatusModelMapper extends CustomMapper<ErrorStatus, ErrorStatusVO> {

    @Override
    public void mapAtoB(ErrorStatus src, ErrorStatusVO dest, MappingContext context) {

        LocalDateTime errorDatetime = src.getErrorDatetime().toLocalDateTime();
        dest.setErrorDate(DateUtils.convertToString(errorDatetime, "yyyy-MM-dd"));
        dest.setErrorTime(DateUtils.convertToString(errorDatetime, "HH:mm:ss"));
        dest.setErrorDatetime(DateUtils.convertToString(errorDatetime, "yyyy-MM-dd HH:mm:ss"));

        if (src.getCalleeReqDatetime() != null) {
            LocalDateTime calleeReqDatetime = src.getCalleeReqDatetime().toLocalDateTime();
            dest.setCalleeReqDatetime(DateUtils.convertToString(calleeReqDatetime, "yyyy-MM-dd HH:mm:ss"));
            dest.setCalleeReqDate(DateUtils.convertToString(calleeReqDatetime, "yyyy-MM-dd"));
            dest.setCalleeReqTime(DateUtils.convertToString(calleeReqDatetime, "HH:mm:ss"));
        }

        if (src.getCalleePlanDatetime() != null) {
            LocalDateTime calleePlanDatetime = src.getCalleePlanDatetime().toLocalDateTime();
            dest.setCalleePlanDatetime(DateUtils.convertToString(calleePlanDatetime, "yyyy-MM-dd HH:mm:ss"));
            dest.setCalleePlanDate(DateUtils.convertToString(calleePlanDatetime, "yyyy-MM-dd"));
            dest.setCalleePlanTime(DateUtils.convertToString(calleePlanDatetime, "HH:mm:ss"));
        }

        if (src.getArrivalPlanDatetime() != null) {
            LocalDateTime arrivalPlanDatetime = src.getArrivalPlanDatetime().toLocalDateTime();
            dest.setArrivalPlanDatetime(DateUtils.convertToString(arrivalPlanDatetime, "yyyy-MM-dd HH:mm:ss"));
            dest.setArrivalPlanDate(DateUtils.convertToString(arrivalPlanDatetime, "yyyy-MM-dd"));
            dest.setArrivalPlanTime(DateUtils.convertToString(arrivalPlanDatetime, "HH:mm:ss"));
        }

        if (src.getCornerArrivalDatetime() != null) {
            LocalDateTime cornerArrivalDatetime = src.getCornerArrivalDatetime().toLocalDateTime();
            dest.setCornerArrivalDatetime(DateUtils.convertToString(cornerArrivalDatetime, "yyyy-MM-dd HH:mm:ss"));
            dest.setCornerArrivalDate(DateUtils.convertToString(cornerArrivalDatetime, "yyyy-MM-dd"));
            dest.setCornerArrivalTime(DateUtils.convertToString(cornerArrivalDatetime, "HH:mm:ss"));
        }

        if (src.getHandleDatetime() != null) {
            LocalDateTime handleDatetime = src.getHandleDatetime().toLocalDateTime();
            dest.setHandleDatetime(DateUtils.convertToString(handleDatetime, "yyyy-MM-dd HH:mm:ss"));
            dest.setHandleDate(DateUtils.convertToString(handleDatetime, "yyyy-MM-dd"));
            dest.setHandleTime(DateUtils.convertToString(handleDatetime, "HH:mm:ss"));
        }

        if (src.getCancleReqDatetime() != null) {
            LocalDateTime cancleReqDateTime = src.getCancleReqDatetime().toLocalDateTime();
            dest.setCancleReqDatetime(DateUtils.convertToString(cancleReqDateTime, "yyyy-MM-dd HH:mm:ss"));
            dest.setCancleReqDate(DateUtils.convertToString(cancleReqDateTime, "yyyy-MM-dd"));
            dest.setCancleReqtime(DateUtils.convertToString(cancleReqDateTime, "HH:mm:ss"));
        }

        if (src.getLastModifyDatetime() != null) {
            LocalDateTime lastModifyDatetime = src.getLastModifyDatetime().toLocalDateTime();
            dest.setLastModifyDatetime(DateUtils.convertToString(lastModifyDatetime, "yyyy-MM-dd HH:mm:ss"));
        }


        if (src.getTxId().substring(2, 3).equals("3") || src.getTxId().substring(2, 3).equals("4")) {
            // 현금부족예보, 현금부족
            Duration duration = Duration.between(errorDatetime, LocalDateTime.now());
            long elapsedTime = duration.getSeconds();

            dest.setElapsedTime(getElapsedTime(elapsedTime));

            if (elapsedTime >= 1800) {
                // 30분 이상이면 빨간색 + 음영
                dest.setErrorStatusClass("error-status error-status-more-danger");

                if ((elapsedTime/60) % 2 == 0) {
                    if (src.getTxId().substring(2, 3).equals("4")) {
                        dest.setPushString("단말기번 : [" + src.getTerminalNo() + "] 현금부족 예보 발생 후 " + getElapsedTime(elapsedTime) + " 경과!!!");
                    } else {
                        dest.setPushString("단말기번 : [" + src.getTerminalNo() + "] 현금부족 발생 후 " + getElapsedTime(elapsedTime) + " 경과!!!");
                    }
                }
            } else if (elapsedTime >= 900) {
                // 15분 이상이면 빨간색
                dest.setErrorStatusClass("error-status error-status-danger");

                if ((elapsedTime/60) % 2 == 0) {
                    if (src.getTxId().substring(2, 3).equals("3")) {
                        dest.setPushString("단말기번 : [" + src.getTerminalNo() + "] 현금부족 예보 발생 후 " + getElapsedTime(elapsedTime) + " 경과!!!");
                    } else {
                        dest.setPushString("단말기번 : [" + src.getTerminalNo() + "] 현금부족 발생 후 " + getElapsedTime(elapsedTime) + " 경과!!!");
                    }
                }
            } else {
                dest.setErrorStatusClass("error-status error-status-warning");
            }
        } else {
            // 일반장애
            if (src.getCalleeReqDatetime() != null) {
                Duration duration = Duration.between(src.getCalleeReqDatetime().toLocalDateTime(), LocalDateTime.now());
                long elapsedTime = duration.getSeconds();

                dest.setElapsedTime(getElapsedTime(elapsedTime));

                if (elapsedTime >= 720) {
                    // 12분 이상이면 빨간색 + 음영
                    dest.setErrorStatusClass("error-status error-status-more-danger");

                    if ((elapsedTime/60) % 2 == 0) {
                        dest.setPushString("단말기번 : [" + src.getTerminalNo() + "] 출동요청 후 " + getElapsedTime(elapsedTime) + " 경과!!!");
                    }
                } else if (elapsedTime >= 540) {
                    // 9분 이상이면 빨간색
                    dest.setErrorStatusClass("error-status error-status-danger");

                    if ((elapsedTime/60) % 2 == 0) {
                        dest.setPushString("단말기번 : [" + src.getTerminalNo() + "] 출동요청 후 " + getElapsedTime(elapsedTime) + " 경과!!!");
                    }
                } else {
                    dest.setErrorStatusClass("error-status error-status-success");
                }
            } else {
                Duration duration = Duration.between(errorDatetime, LocalDateTime.now());
                long elapsedTime = duration.getSeconds();

                dest.setElapsedTime(getElapsedTime(elapsedTime));

                if (elapsedTime >= 1800) {
                    // 12분 이상이면 빨간색 + 음영
                    dest.setErrorStatusClass("error-status error-status-more-danger");

                    if ((elapsedTime/60) % 2 == 0) {
                        dest.setPushString("단말기번 : [" + src.getTerminalNo() + "] 장애발생 후 " + getElapsedTime(elapsedTime) + " 경과!!!");
                    }
                } else if (elapsedTime >= 900) {
                    // 9분 이상이면 빨간색
                    dest.setErrorStatusClass("error-status error-status-danger");
                    if ((elapsedTime/60) % 2 == 0) {
                        dest.setPushString("단말기번 : [" + src.getTerminalNo() + "] 장애발생 후 " + getElapsedTime(elapsedTime) + " 경과!!!");
                    }
                } else {
                    dest.setErrorStatusClass("error-status error-status-success");
                }
            }
        }


        /*Duration duration = Duration.between(errorDatetime, LocalDateTime.now());
        long elapsedTime = duration.getSeconds();

        dest.setElapsedTime(getElapsedTime(elapsedTime));

        if (src.getTxId().substring(2, 3).equals("2")) {
            // 미조치건은 다시 장애통보요청
            dest.setErrorStatusClass("error-status error-status-fail");
        } else {
            dest.setErrorStatusClass(getErrorStatusClass(elapsedTime, src.getErrorProcessStatus()));
        }*/

        dest.setTxId(src.getTxId().substring(0, 2) + "-" + src.getTxId().substring(2, 3) + "-" + src.getTxId().substring(3, src.getTxId().length()));
    }

    private String getElapsedTime(long elapsedTime) {

        //final int MINUTES_PER_HOUR = 60;
        final int SECONDS_PER_MINUTE = 60;
        //final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;

        //long minutes = ((elapsedTime * SECONDS_PER_HOUR) / SECONDS_PER_MINUTE);
        long minutes = elapsedTime / SECONDS_PER_MINUTE;
        long secs = minutes < 1 ? elapsedTime : elapsedTime % minutes;

        return String.format("%02d분%02d초", minutes, secs);
    }

    private String getErrorStatusClass(long elapsedTime, int errorProcessStatus) {

        String errorStatusClass = "error-status error-status-success";

        if (elapsedTime >= 720) {
            if (errorProcessStatus == 2) {
                errorStatusClass = "error-status error-status-warning";
            } else {
                errorStatusClass = "error-status error-status-danger";
            }
        }

        return errorStatusClass;
    }
}

