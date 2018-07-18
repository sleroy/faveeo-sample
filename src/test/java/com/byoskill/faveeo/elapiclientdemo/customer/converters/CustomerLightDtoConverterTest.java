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

import java.util.HashMap;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.byoskill.faveeo.elapiclientdemo.customer.converters.CustomerLightDtoConverter;
import com.byoskill.faveeo.elapiclientdemo.customer.domain.CustomerAccount;

public class CustomerLightDtoConverterTest {

    @Test
    public void testMappingFields() throws Exception {
	final CustomerLightDtoConverter peopleAccountConverter = new CustomerLightDtoConverter();
	final HashMap<String, Object> sourceAsMap = new HashMap<>();
	sourceAsMap.put("firstname", "Sylvain");
	sourceAsMap.put("lastname", "Leroy");
	sourceAsMap.put("account_number", 12);
	final CustomerAccount accountDto = peopleAccountConverter.mappingFields(sourceAsMap);
	Assertions.assertThat(accountDto).isNotNull();
	Assertions.assertThat(accountDto.getFullname()).isEqualTo("Sylvain Leroy");
	Assertions.assertThat(accountDto.getAccountNumber()).isEqualTo(12);

    }

}
