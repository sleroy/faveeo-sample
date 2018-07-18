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

import javax.validation.constraints.NotNull;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.byoskill.faveeo.elapiclientdemo.customer.dao.AddressFilter;

public class FindFilteredCustomerWithGenderStatsRequest {

    private final int			   limitRecords;
    private final @NotNull AddressFilter filter;

    public FindFilteredCustomerWithGenderStatsRequest(final int limitRecords, @NotNull final AddressFilter filter) {
	this.limitRecords = limitRecords;
	this.filter = filter;

    }

    public SearchRequest getRequest() {

	final SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
	searchSourceBuilder.query(QueryBuilders.matchPhraseQuery("address", filter.getAddress()));
	searchSourceBuilder.sort("account_number");
	searchSourceBuilder.size(limitRecords);
	searchSourceBuilder.fetchSource(new String[] { "account_number", "firstname", "lastname" }, new String[] {});
	final SearchRequest searchRequest = new SearchRequest();
	searchRequest.source(searchSourceBuilder);
	return searchRequest;
    }

}
