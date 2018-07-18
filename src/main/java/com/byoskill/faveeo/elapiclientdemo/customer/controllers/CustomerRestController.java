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
package com.byoskill.faveeo.elapiclientdemo.customer.controllers;

import java.util.List;
import java.util.Optional;

import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.byoskill.faveeo.elapiclientdemo.customer.dao.AddressFilter;
import com.byoskill.faveeo.elapiclientdemo.customer.dao.AddressFilterBuilder;
import com.byoskill.faveeo.elapiclientdemo.customer.dao.CustomerRepository;
import com.byoskill.faveeo.elapiclientdemo.customer.domain.CustomerAccount;
import com.byoskill.faveeo.elapiclientdemo.customer.domain.GenderStatistics;
import com.byoskill.faveeo.elapiclientdemo.customer.resources.CustomerSearchWithGenderStats;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

/**
 * The Class CustomerRestController defines the controller to handle
 * CustomerResource resources.
 */
@RestController("/")
@Api(value = "peopleResource")
public class CustomerRestController {

    private static final int FIXED_RECORD_LENGTH = 5;

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerRestController.class);

    /** The people repository. */
    private final CustomerRepository peopleRepository;

    /**
     * Instantiates a new people rest controller.
     *
     * @param peopleRepository
     *            the people repository
     * @param customerNameAccountAssembler
     *            the customer account assembler
     */
    @Autowired
    public CustomerRestController(final CustomerRepository peopleRepository) {
	super();
	this.peopleRepository = peopleRepository;
    }

    /**
     * Gets the TOP 5 with gender stats optionally filtered by address.
     *
     * @param address
     *            the address
     * @return the TOP 5 with gender stats filtered by address
     */
    @ApiOperation(value = "Obtain a list of people filtered by their address, restricted to five records and including the gender stats on the whole repository")
    @ApiResponse(code = 200, message = "Sucessfuly retrieved list and stats")
    @RequestMapping(path = "/people", method = RequestMethod.GET)
    public ResponseEntity<CustomerSearchWithGenderStats> getTOP5WithGenderStatsFilteredByAddress(
	    @RequestParam(value = "address") final Optional<String> address) {
	LOGGER.debug("getTOP5WithGenderStatsFilteredByAddress(): address={}", address);
	if (address.isPresent()) {
	    // We filter on address
	    final AddressFilterBuilder filterBuilder = new AddressFilterBuilder();
	    final String rawAddress = address.get();
	    filterBuilder.withAddress(StringEscapeUtils.escapeJson(rawAddress));

	    final AddressFilter addressFilter = filterBuilder.build();
	    final GenderStatistics globalGenderStatistics = peopleRepository
		    .getGenderStatisticsPerAddress(addressFilter);
	    final List<CustomerAccount> findPeople = peopleRepository.findPeopleByAddress(FIXED_RECORD_LENGTH,
		    addressFilter);

	    final CustomerSearchWithGenderStats customerSearchWithGenderStats = new CustomerSearchWithGenderStats();
	    customerSearchWithGenderStats.setResults(findPeople);
	    customerSearchWithGenderStats.setStatistics(globalGenderStatistics);

	    return ResponseEntity.ok(customerSearchWithGenderStats);
	} else {
	    // No filter
	    final GenderStatistics globalGenderStatistics = peopleRepository.getGlobalGenderStatistics();
	    final List<CustomerAccount> findPeople = peopleRepository.findPeople(FIXED_RECORD_LENGTH);

	    final CustomerSearchWithGenderStats customerSearchWithGenderStats = new CustomerSearchWithGenderStats();
	    customerSearchWithGenderStats.setResults(findPeople);
	    customerSearchWithGenderStats.setStatistics(globalGenderStatistics);

	    return ResponseEntity.ok(customerSearchWithGenderStats);
	}
    }
}
