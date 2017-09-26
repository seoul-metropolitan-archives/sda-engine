package com.bgf.shbank.core.model;

import com.bgf.shbank.utils.DateUtils;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by james on 2017-02-14.
 */
public class ModelMapperConverter {

    public static Converter<String, Timestamp> toStringDate = new AbstractConverter<String, Timestamp>() {
        @Override
        protected Timestamp convert(String source) {

            Timestamp result = null;

            source = source.replace("-", "").replace(":", "");

            switch (source.length()) {
                case 4:
                    result = DateUtils.convertToTimestamp("20170101" + source + "00", "yyyyMMddHHmmss");
                    break;
                case 6:
                    result = DateUtils.convertToTimestamp("20170101" + source, "yyyyMMddHHmmss");
                    break;
                case 8:
                    result = DateUtils.convertToTimestamp(source, "yyyyMMdd");
                    break;
                case 14:
                    result = DateUtils.convertToTimestamp(source, "yyyyMMddHHmmss");
                    break;
                case 15:
                    result = DateUtils.convertToTimestamp(source, "yyyyMMdd HHmmss");
                    break;
            }

            return result;
        }
    };

    public static class TimestampConverter extends BidirectionalConverter<LocalDateTime, Timestamp> {

        @Override
        public Timestamp convertTo(LocalDateTime source, Type<Timestamp> destinationType, MappingContext mappingContext) {

            int length = String.valueOf(source).length();

            if (length == 8) {
                return Timestamp.valueOf(DateUtils.convertToDateTime(String.valueOf(source), "yyyyMMdd"));
            }

            if (length == 15) {
                return Timestamp.valueOf(DateUtils.convertToDateTime(String.valueOf(source), "yyyyMMdd HHmmss"));
            }

            return null;
        }

        @Override
        public LocalDateTime convertFrom(Timestamp source, Type<LocalDateTime> destinationType, MappingContext mappingContext) {
            return null;
        }
    }

    public static class TrimConverter extends BidirectionalConverter<String, String> {

        @Override
        public String convertTo(String source, Type<String> destinationType, MappingContext mappingContext) {
            return StringUtils.trim(source);
        }

        @Override
        public String convertFrom(String source, Type<String> destinationType, MappingContext mappingContext) {
            return source;
        }
    }
}
