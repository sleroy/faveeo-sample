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
package com.byoskill.faveeo.elapiclientdemo;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.byoskill.faveeo.elapiclientdemo.customer.controllers.CustomerRestController;
import com.byoskill.faveeo.elapiclientdemo.customer.dao.AddressFilter;
import com.byoskill.faveeo.elapiclientdemo.customer.dao.CustomerRepository;
import com.byoskill.faveeo.elapiclientdemo.customer.domain.CustomerAccount;
import com.byoskill.faveeo.elapiclientdemo.customer.domain.GenderStatistics;
import com.byoskill.faveeo.elapiclientdemo.customer.resources.CustomerSearchWithGenderStats;
import com.google.common.collect.Lists;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElapiClientIntegrationTest {

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerRestController customerRestController;

    @Before
    public void setup() {
	when(customerRepository.getGlobalGenderStatistics()).thenReturn(new GenderStatistics(5L, 10L));
	when(customerRepository.findPeople(5)).thenReturn(Lists.newArrayList(new CustomerAccount("Sylvain Leroy", 0)));

	when(customerRepository.getGenderStatisticsPerAddress(ArgumentMatchers.isA(AddressFilter.class)))
		.thenReturn(new GenderStatistics(5L, 10L));
	when(customerRepository.findPeopleByAddress(ArgumentMatchers.eq(5), ArgumentMatchers.isA(AddressFilter.class)))
		.thenReturn(Lists.newArrayList(new CustomerAccount("Sylvain Leroy", 0)));

    }

    @Test
    public void test_address_filter() {
	final ResponseEntity<CustomerSearchWithGenderStats> responseEntity = customerRestController
		.getTOP5WithGenderStatsFilteredByAddress(Optional.of("address"));
	final CustomerSearchWithGenderStats customerSearchWithGenderStats = responseEntity.getBody();
	Assertions.assertThat(customerSearchWithGenderStats.getFemale()).isEqualTo(5);
	Assertions.assertThat(customerSearchWithGenderStats.getMale()).isEqualTo(10);
	Assertions.assertThat(customerSearchWithGenderStats.getResults()).hasSize(1);
    }

    @Test
    public void test_global_request() {
	final ResponseEntity<CustomerSearchWithGenderStats> responseEntity = customerRestController
		.getTOP5WithGenderStatsFilteredByAddress(Optional.empty());
	final CustomerSearchWithGenderStats customerSearchWithGenderStats = responseEntity.getBody();
	Assertions.assertThat(customerSearchWithGenderStats.getFemale()).isEqualTo(5);
	Assertions.assertThat(customerSearchWithGenderStats.getMale()).isEqualTo(10);
	Assertions.assertThat(customerSearchWithGenderStats.getResults()).hasSize(1);
    }

}
