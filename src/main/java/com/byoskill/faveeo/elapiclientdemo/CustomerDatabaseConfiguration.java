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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The Class CustomerDatabaseConfiguration defines the configuration to access
 * to the EL CustomerResource database.
 */
@Configuration
@ConfigurationProperties(prefix = "peopledb")
public class CustomerDatabaseConfiguration {

    /** The hostname. */
    private String hostname;

    /** The scheme. */
    private String scheme;

    /** The port. */
    private int port;

    /** The relative path. */
    private String relativePath;

    /**
     * Instantiates a new people database configuration.
     */
    public CustomerDatabaseConfiguration() {
	super();
    }

    /**
     * Gets the hostname.
     *
     * @return the hostname
     */
    public String getHostname() {
	return hostname;
    }

    /**
     * Gets the port.
     *
     * @return the port
     */
    public int getPort() {
	return port;
    }

    /**
     * Gets the relative path.
     *
     * @return the relative path
     */
    public String getRelativePath() {
	return relativePath;
    }

    /**
     * Gets the scheme.
     *
     * @return the scheme
     */
    public String getScheme() {
	return scheme;
    }

    /**
     * Sets the hostname.
     *
     * @param endpoint
     *            the new hostname
     */
    public void setHostname(final String endpoint) {
	hostname = endpoint;
    }

    /**
     * Sets the port.
     *
     * @param port
     *            the new port
     */
    public void setPort(final int port) {
	this.port = port;
    }

    /**
     * Sets the relative path.
     *
     * @param relativePath
     *            the new relative path
     */
    public void setRelativePath(final String relativePath) {
	this.relativePath = relativePath;
    }

    /**
     * Sets the scheme.
     *
     * @param scheme
     *            the new scheme
     */
    public void setScheme(final String scheme) {
	this.scheme = scheme;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "CustomerDatabaseConfiguration [hostname=" + hostname + "]";
    }

}
