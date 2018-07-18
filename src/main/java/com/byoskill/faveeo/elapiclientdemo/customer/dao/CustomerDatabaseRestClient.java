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

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.byoskill.faveeo.elapiclientdemo.CustomerDatabaseConfiguration;

@Component
public class CustomerDatabaseRestClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerDatabaseConfiguration.class);

    private final CustomerDatabaseConfiguration configuration;

    @Autowired
    public CustomerDatabaseRestClient(final CustomerDatabaseConfiguration configuration) {
	this.configuration = configuration;

    }

    /**
     * New client.
     *
     * @return the rest high level client
     */
    public RestHighLevelClient newClient() {
	LOGGER.info("Initializing the EL Client pool for the CustomerResource database : binded to {}",
		configuration.getHostname());
	final HttpHost httpHost = new HttpHost(configuration.getHostname(), configuration.getPort(),
		configuration.getScheme());

	final RestClientBuilder builder = RestClient.builder(httpHost);
	if (!configuration.getRelativePath().isEmpty()) {
	    builder.setPathPrefix(configuration.getRelativePath());
	}
	return new RestHighLevelClient(builder);
    }

}
