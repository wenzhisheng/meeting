package com.meeting.common.config.mongodb;

import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;

/**
 * @author dameizi
 * @description 引用类型转基础类型
 * @dateTime 2019-07-12 12:24
 * @className com.meeting.common.areacascade.dao.IAreaCascadeDao
 */
public class BigDecimalToDoubleConverter implements Converter<BigDecimal, Double> {

    @Override
    public Double convert(BigDecimal source) {
        return source.doubleValue();
    }

}