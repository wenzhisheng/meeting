package com.meeting.common.config.mongodb;

import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;

/**
 * @author dameizi
 * @description 基础类型转引用类型
 * @dateTime 2019-07-12 12:24
 * @className com.meeting.common.areacascade.dao.IAreaCascadeDao
 */
public class DoubleToBigDecimalConverter implements Converter<Double, BigDecimal> {

    @Override
    public BigDecimal convert(Double source) {
        return new BigDecimal(source);
    }

}
