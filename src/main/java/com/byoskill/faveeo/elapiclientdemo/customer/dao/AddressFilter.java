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

import net.karneim.pojobuilder.GeneratePojoBuilder;

/**
 * The Class AddressFilter represents the filter to be applied to the
 * CustomerResource search query.
 */
@GeneratePojoBuilder
public class AddressFilter {

    /** The address. */
    private String address;

    public AddressFilter() {
	super();
    }

    public AddressFilter(final String address) {
	super();
	this.address = address;
    }

    /**
     * Gets the address.
     *
     * @return the address
     */
    public String getAddress() {
	return address;
    }

    /**
     * Sets the address.
     *
     * @param address
     *            the new address
     */
    public void setAddress(final String address) {
	this.address = address;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "AddressFilter [address=" + address + "]";
    }
}
