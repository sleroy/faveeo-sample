/*
 * Copyright (C) 2017 Sylvain Leroy - BYOSkill Company All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the MIT license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the MIT license with
 * this file. If not, please write to: sleroy at byoskill.com, or visit : www.byoskill.com
 *
 */
package com.byoskill.faveeo.elapiclientdemo.utils.converter;

import java.io.IOException;

import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class HitConverter<T> implements DocumentConverterFunction<T> {

    private static final Logger	      LOGGER	   = LoggerFactory.getLogger(HitConverter.class);
    private static final ObjectMapper OBJECTMAPPER = new ObjectMapper();

    private final Class<T> classToConvert;

    public HitConverter(final Class<T> classToConvert) {
	this.classToConvert = classToConvert;
    }

    @Override
    public T apply(final SearchHit hit) {
	try {
	    return OBJECTMAPPER.readValue(hit.getSourceAsString(), classToConvert);
	} catch (final IOException e) {
	    LOGGER.error("Could not convert hit into class {} : raw {}", classToConvert, hit.getSourceAsString());
	}
	return null;
    }
}