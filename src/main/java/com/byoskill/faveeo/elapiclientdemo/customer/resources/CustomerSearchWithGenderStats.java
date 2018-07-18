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
package com.byoskill.faveeo.elapiclientdemo.customer.resources;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.byoskill.faveeo.elapiclientdemo.customer.domain.CustomerAccount;
import com.byoskill.faveeo.elapiclientdemo.customer.domain.GenderStatistics;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class CustomerSearchWithGenderStats defines the DTO returned by a
 * endpoint in the CustomerResource controlled providing the required statistics
 * and account fullnames.
 */
public class CustomerSearchWithGenderStats {

    /** The results. */
    @JsonProperty("results")
    @NotNull
    private List<CustomerAccount> results = new ArrayList<>();

    /** The female. */
    @JsonProperty("Female")
    @PositiveOrZero
    private Long female;

    /** The male. */
    @JsonProperty("Male")
    @PositiveOrZero
    private Long male;

    /**
     * Gets the female.
     *
     * @return the female
     */
    public Long getFemale() {
	return female;
    }

    /**
     * Gets the male.
     *
     * @return the male
     */
    public Long getMale() {
	return male;
    }

    /**
     * Gets the results.
     *
     * @return the results
     */
    public @NotNull List<CustomerAccount> getResults() {
	return results;
    }

    /**
     * Sets the female.
     *
     * @param female
     *            the new female
     */
    public void setFemale(final Long female) {
	this.female = female;
    }

    /**
     * Sets the male.
     *
     * @param male
     *            the new male
     */
    public void setMale(final Long male) {
	this.male = male;
    }

    /**
     * Sets the results.
     *
     * @param listOfPeople
     *            the new results
     */
    public void setResults(final @NotNull List<CustomerAccount> listOfPeople) {
	results = listOfPeople;
    }

    /**
     * Sets the statistics.
     *
     * @param statistics
     *            the new statistics
     */
    public void setStatistics(final GenderStatistics statistics) {
	female = statistics.getFemale();
	male = statistics.getMale();
    }

    @Override
    public String toString() {
	return "CustomerSearchWithGenderStats [results=" + results + ", female=" + female + ", male=" + male + "]";
    }
}
