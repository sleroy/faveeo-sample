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
package com.byoskill.faveeo.elapiclientdemo.customer.request;

import java.util.Optional;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.byoskill.faveeo.elapiclientdemo.customer.dao.AddressFilter;

public class GenderCountRequest {

    public static final String		    AGG_BY_GENDER = "agg_by_gender";
    private final Optional<AddressFilter> filter;

    /**
     * Instantiates a new gender count request.
     *
     * @param filter
     *            the filter
     */
    public GenderCountRequest(final Optional<AddressFilter> filter) {
	this.filter = filter;
    }

    public SearchRequest getRequest() {

	final SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
	if (filter.isPresent()) {
	    searchSourceBuilder.query(QueryBuilders.matchPhraseQuery("address", filter.get().getAddress()));
	} else {
	    searchSourceBuilder.query(QueryBuilders.matchAllQuery());
	}
	searchSourceBuilder.size(0);

	final TermsAggregationBuilder byGender = AggregationBuilders.terms(AGG_BY_GENDER);
	byGender.field("gender.keyword");
	searchSourceBuilder.aggregation(byGender);

	final SearchRequest searchRequest = new SearchRequest();
	searchRequest.source(searchSourceBuilder);
	return searchRequest;
    }

}
