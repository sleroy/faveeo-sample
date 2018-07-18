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
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.byoskill.faveeo.elapiclientdemo.customer.converters.CustomerLightDtoConverter;
import com.byoskill.faveeo.elapiclientdemo.customer.domain.CustomerAccount;
import com.byoskill.faveeo.elapiclientdemo.customer.domain.GenderStatistics;
import com.byoskill.faveeo.elapiclientdemo.customer.domain.GenderStatisticsBuilder;
import com.byoskill.faveeo.elapiclientdemo.customer.request.FindFilteredCustomerWithGenderStatsRequest;
import com.byoskill.faveeo.elapiclientdemo.customer.request.GenderCountRequest;
import com.byoskill.faveeo.elapiclientdemo.utils.converter.DocumentConverter;
import com.byoskill.faveeo.elapiclientdemo.utils.dao.QueryException;

/**
 * The Class CustomerRepositoryImpl implements a CustomerResource repository
 * indexed inside an EL using a REST API.
 */
@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerRepositoryImpl.class);

    private final CustomerDatabaseRestClient peopleDBRestClient;

    /**
     * Instantiates a new people repository impl.
     *
     * @param peopleDBRestClient
     *            the people DB rest client
     */
    public CustomerRepositoryImpl(final CustomerDatabaseRestClient peopleDBRestClient) {
	super();
	this.peopleDBRestClient = peopleDBRestClient;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.byoskill.faveeo.elapiclientdemo.customer.dao.CustomerRepository#
     * findPeople(int)
     */
    @Override
    public List<CustomerAccount> findPeople(final int limitRecords) {
	LOGGER.debug("findPeople({})", limitRecords);

	try (final RestHighLevelClient client = peopleDBRestClient.newClient();) {
	    // CustomerResource Request w/o filter

	    final DocumentConverter documentConverter = new DocumentConverter(client);
	    return documentConverter.executeQueryAndConvert(
		    new FindAllCustomerRequest(limitRecords).getRequest(),
		    new CustomerLightDtoConverter());

	} catch (final IOException e) {
	    throw new QueryException(e);
	}
    }

    /*
     * (non-Javadoc)
     *
     * @see com.byoskill.faveeo.elapiclientdemo.customer.dao.CustomerRepository#
     * findPeopleByAddress(int,
     * com.byoskill.faveeo.elapiclientdemo.customer.dao.AddressFilter)
     */
    @Override
    public List<CustomerAccount> findPeopleByAddress(final int limitRecords, @NotNull final AddressFilter filter) {
	LOGGER.debug("findPeopleByAddress({}, {})", limitRecords, filter);
	try (final RestHighLevelClient client = peopleDBRestClient.newClient();) {
	    // CustomerResource query
	    final DocumentConverter documentConverter = new DocumentConverter(client);
	    return documentConverter.executeQueryAndConvert(
		    new FindFilteredCustomerWithGenderStatsRequest(limitRecords, filter).getRequest(),
		    new CustomerLightDtoConverter());

	} catch (final IOException e) {
	    throw new QueryException(e);
	}
    }

    /*
     * (non-Javadoc)
     *
     * @see com.byoskill.faveeo.elapiclientdemo.customer.dao.CustomerRepository#
     * getGenderStatisticsPerAddress(com.byoskill.faveeo.elapiclientdemo.customer.
     * dao.AddressFilter)
     */
    @Override
    public GenderStatistics getGenderStatisticsPerAddress(final AddressFilter addressFilter) {
	SearchResponse searchResponse;
	try (final RestHighLevelClient client = peopleDBRestClient.newClient();) {
	    searchResponse = client
		    .search(new GenderCountRequest(Optional.of(addressFilter)).getRequest());
	    final ParsedStringTerms aggregation = searchResponse.getAggregations()
		    .get(GenderCountRequest.AGG_BY_GENDER);

	    final GenderStatisticsBuilder genderStatisticsBuilder = new GenderStatisticsBuilder();
	    genderStatisticsBuilder.withFemale(aggregation.getBucketByKey("F").getDocCount());
	    genderStatisticsBuilder.withMale(aggregation.getBucketByKey("M").getDocCount());

	    final GenderStatistics genderStatistics = genderStatisticsBuilder.build();

	    // Stats
	    return genderStatistics;
	} catch (final IOException e) {
	    throw new QueryException(e);
	}
    }

    /*
     * (non-Javadoc)
     *
     * @see com.byoskill.faveeo.elapiclientdemo.customer.dao.CustomerRepository#
     * getGlobalGenderStatistics()
     */
    @Override
    public GenderStatistics getGlobalGenderStatistics() {
	SearchResponse searchResponse;
	try (final RestHighLevelClient client = peopleDBRestClient.newClient();) {
	    searchResponse = client
		    .search(new GenderCountRequest(Optional.empty()).getRequest());
	    final ParsedStringTerms aggregation = searchResponse.getAggregations()
		    .get(GenderCountRequest.AGG_BY_GENDER);

	    final GenderStatisticsBuilder genderStatisticsBuilder = new GenderStatisticsBuilder();
	    genderStatisticsBuilder.withFemale(aggregation.getBucketByKey("F").getDocCount());
	    genderStatisticsBuilder.withMale(aggregation.getBucketByKey("M").getDocCount());

	    final GenderStatistics genderStatistics = genderStatisticsBuilder.build();

	    return genderStatistics;
	} catch (final IOException e) {
	    throw new QueryException(e);
	}
    }

}
