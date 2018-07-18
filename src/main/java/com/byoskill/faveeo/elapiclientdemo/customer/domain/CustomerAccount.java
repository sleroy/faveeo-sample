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
package com.byoskill.faveeo.elapiclientdemo.customer.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerAccount {

    @JsonProperty("full_name")
    private final String fullname;

    @JsonProperty("account_number")
    private final Integer accountNumber;

    /**
     * Instantiates a new people account dto.
     *
     * @param fullname
     *            the fullname (first name and last name)
     * @param accountNumber
     *            the account number
     */
    @JsonCreator
    public CustomerAccount(@JsonProperty("full_name") @NotEmpty final String fullname,
	    @JsonProperty("account_number") @PositiveOrZero final Integer accountNumber) {
	super();
	this.fullname = fullname;
	this.accountNumber = accountNumber;
    }

    /**
     * Gets the account number.
     *
     * @return the account number
     */
    public Integer getAccountNumber() {
	return accountNumber;
    }

    /**
     * Gets the fullname.
     *
     * @return the fullname
     */
    public String getFullname() {
	return fullname;
    }

    @Override
    public String toString() {
	return "CustomerAccountResource [fullname=" + fullname + ", accountNumber=" + accountNumber + "]";
    }
}
