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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;

public class DocumentConverter {

    private final RestHighLevelClient elClient;

    public DocumentConverter(final RestHighLevelClient elClient) {
	super();
	this.elClient = elClient;
    }

    /**
     * Execute query and convert.
     *
     * @param searchRequest
     *            the search request
     * @param toConvert
     *            the to convert
     * @return the list
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public <T> List<T> executeQueryAndConvert(final SearchRequest searchRequest,
	    final DocumentConverterFunction<T> converterFunction)
	    throws IOException {
	final SearchResponse searchResponse = elClient.search(searchRequest);
	final SearchHit[] hits = searchResponse.getHits().getHits();
	return Arrays.stream(hits).map(converterFunction)
		.filter(p -> p != null).collect(Collectors.toList());
    }
}
