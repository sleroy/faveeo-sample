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
package com.byoskill.faveeo.elapiclientdemo.customer.dao;

import java.io.IOException;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.byoskill.faveeo.elapiclientdemo.customer.domain.CustomerAccount;
import com.byoskill.faveeo.elapiclientdemo.customer.domain.GenderStatistics;

/**
 * The Interface CustomerRepository represents the people resources and the ways
 * to access it.
 */
public interface CustomerRepository {

    /**
     * Gets the TOP 5 with gender stats.
     *
     * @param limitRecords
     *            the limit in displayed records
     * @param filter
     *            the optional filter to be applied on the query
     * @return the TOP 5 with gender stats
     */
    List<CustomerAccount> findPeople(int limitRecords);

    /**
     * Find people with a required filter on the address
     *
     * @param limitRecords
     *            the limit records
     * @param filter
     *            the filter
     * @return the people search with gender stats DTO
     * @throws IOException
     */
    List<CustomerAccount> findPeopleByAddress(int limitRecords,
	    @NotNull AddressFilter filter);

    /**
     * Gets the gender statistics for a specific address filter
     *
     * @param addressFilter
     *            the address filter
     * @return the gender statistics per address
     */
    GenderStatistics getGenderStatisticsPerAddress(AddressFilter addressFilter);

    /**
     * Gets the global gender statistics.
     *
     * @param addressFilter
     *            the address filter
     * @return the gender statistics per address
     */
    GenderStatistics getGlobalGenderStatistics();
}
