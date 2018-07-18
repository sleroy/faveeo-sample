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

import java.beans.ConstructorProperties;

import net.karneim.pojobuilder.GeneratePojoBuilder;

/**
 * The Class GenderStatistics per fixed gender (male, female). Well not american
 * politically correct but anyway.
 */

public class GenderStatistics {

    /** The female. */
    private final Long female;

    /** The male. */
    private final Long male;

    /**
     * Instantiates a new gender statistics.
     *
     * @param female
     *            the female
     * @param male
     *            the male
     */
    @GeneratePojoBuilder
    @ConstructorProperties({ "female", "male" })
    public GenderStatistics(final Long female, final Long male) {
	super();
	this.female = female;
	this.male = male;
    }

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

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "GenderStatistics [female=" + female + ", male=" + male + "]";
    }
}
