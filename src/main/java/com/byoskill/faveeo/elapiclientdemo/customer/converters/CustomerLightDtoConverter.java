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
package com.byoskill.faveeo.elapiclientdemo.customer.converters;

import java.util.Map;

import org.elasticsearch.search.SearchHit;

import com.byoskill.faveeo.elapiclientdemo.customer.domain.CustomerAccount;
import com.byoskill.faveeo.elapiclientdemo.utils.converter.DocumentConverterFunction;

public class CustomerLightDtoConverter implements DocumentConverterFunction<CustomerAccount> {

    @Override
    public CustomerAccount apply(final SearchHit t) {
	final Map<String, Object> sourceAsMap = t.getSourceAsMap();

	return mappingFields(sourceAsMap);
    }

    CustomerAccount mappingFields(final Map<String, Object> sourceAsMap) {
	final String firstname = (String) sourceAsMap.get("firstname");
	final String lastname = (String) sourceAsMap.get("lastname");
	final String fullName = firstname + " " + lastname;
	final Integer accountNumber = (Integer) sourceAsMap.get("account_number");
	final CustomerAccount customerAccount = new CustomerAccount(fullName, accountNumber);
	return customerAccount;
    }

}
