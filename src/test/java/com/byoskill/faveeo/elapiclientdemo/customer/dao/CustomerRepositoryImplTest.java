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

import static org.mockito.Mockito.when;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.byoskill.faveeo.elapiclientdemo.CustomerDatabaseConfiguration;
import com.byoskill.faveeo.elapiclientdemo.customer.domain.CustomerAccount;
import com.byoskill.faveeo.elapiclientdemo.customer.domain.GenderStatistics;
import com.github.kristofa.test.http.MockAndProxyFacade;
import com.github.kristofa.test.http.MockAndProxyFacade.Builder;
import com.github.kristofa.test.http.MockAndProxyFacade.Mode;
import com.github.kristofa.test.http.MockHttpServer;
import com.github.kristofa.test.http.PassthroughForwardHttpRequestBuilder;
import com.github.kristofa.test.http.SimpleHttpResponseProvider;
import com.github.kristofa.test.http.file.FileHttpResponseProvider;
import com.github.kristofa.test.http.file.HttpRequestResponseFileLoggerFactory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerRepositoryImplTest {

    // Host of original service. Service which we in the end want to replace with
    // our mock implementation.
    private final static String SERVICE_HOST = "34.244.70.47";
    // Port for host.
    private final static int SERVICE_PORT = 80;
    // The port at which our mock or proxy will be running.
    private final static int	MOCK_AND_PROXY_PORT = 9001;
    private final static String	MOCK_PROXY_URL	    = "http://localhost:" + MOCK_AND_PROXY_PORT;

    // Requests and responses will be logged to src/test/resources.
    // This is what you typically want to do and check them in with your source
    // code.
    private final static String REQUEST_LOG_DIR = "src/test/resources/traces";
    // We make sure our persisted request/responses have a unique name. Name of test
    // class
    // is probably a good choice.
    private final static String REQUEST_LOG_FILE_NAME = "MockHttpRequestTest";

    @MockBean
    private CustomerDatabaseConfiguration customerDatabaseConfiguration;
    @Autowired
    private CustomerRepository		  customerRepository;

    private MockHttpServer	       server;
    private SimpleHttpResponseProvider responseProvider;

    @Before
    public void setUp() throws Exception {
	// responseProvider = new SimpleHttpResponseProvider();
	// server = new MockHttpServer(, responseProvider);
	// server.start();
	//
	when(customerDatabaseConfiguration.getHostname()).thenReturn("localhost");
	when(customerDatabaseConfiguration.getPort()).thenReturn(MOCK_AND_PROXY_PORT);
	when(customerDatabaseConfiguration.getScheme()).thenReturn("http");
	when(customerDatabaseConfiguration.getRelativePath()).thenReturn("bank");

    }

    @Test
    public final void testFindPeople() throws Exception {

	final MockAndProxyFacade facade = buildFacade(Mode.MOCKING, "findPeopleGlobal");
	try {
	    facade.start();
	    final List<CustomerAccount> findPeople = customerRepository.findPeople(5);
	    Assertions.assertThat(findPeople).hasSize(5);
	    Assertions.assertThat(findPeople).extracting("accountNumber").containsExactly(0, 1, 2, 3, 4);
	    facade.verify();

	} finally {
	    facade.stop();
	}
    }

    @Test
    public final void testFindPeopleByAddress() throws Exception {

	final MockAndProxyFacade facade = buildFacade(Mode.MOCKING, "findPeople");
	try {
	    facade.start();

	    final List<CustomerAccount> findPeopleByAddress = customerRepository.findPeopleByAddress(5,
		    new AddressFilter("Avenue"));
	    Assertions.assertThat(findPeopleByAddress).hasSize(5);
	    Assertions.assertThat(findPeopleByAddress).extracting("accountNumber").containsExactly(3, 4, 9, 12, 23);
	    // Verify that we got all and only the requests we expected.
	    facade.verify();

	} finally {
	    facade.stop();
	}
    }

    @Test
    public final void testGetGenderStatisticsPerAddress() throws Exception {

	final MockAndProxyFacade facade = buildFacade(Mode.MOCKING, "genderByAddress");
	try {
	    facade.start();
	    final GenderStatistics genderStatisticsPerAddress = customerRepository
		    .getGenderStatisticsPerAddress(new AddressFilter("Avenue"));
	    Assertions.assertThat(genderStatisticsPerAddress.getFemale()).isEqualTo(101L);
	    Assertions.assertThat(genderStatisticsPerAddress.getMale()).isEqualTo(113L);
	    // Verify that we got all and only the requests we expected.
	    facade.verify();

	} finally {
	    facade.stop();
	}
    }

    @Test
    public final void testGetGlobalGenderStatistics() throws Exception {

	final MockAndProxyFacade facade = buildFacade(Mode.MOCKING, "globalGender");
	try {
	    facade.start();
	    final GenderStatistics globalGenderStatistics = customerRepository.getGlobalGenderStatistics();
	    Assertions.assertThat(globalGenderStatistics.getFemale()).isEqualTo(493L);
	    Assertions.assertThat(globalGenderStatistics.getMale()).isEqualTo(507L);
	    // Verify that we got all and only the requests we expected.
	    facade.verify();

	} finally {
	    facade.stop();
	}

    }

    private MockAndProxyFacade buildFacade(final Mode mode, final String request) {
	final Builder builder = new Builder();
	return builder
		.mode(mode)
		.addForwardHttpRequestBuilder(new PassthroughForwardHttpRequestBuilder(SERVICE_HOST, SERVICE_PORT))
		.httpRequestResponseLoggerFactory(
			new HttpRequestResponseFileLoggerFactory(REQUEST_LOG_DIR,
				REQUEST_LOG_FILE_NAME + "_" + request))
		.port(MOCK_AND_PROXY_PORT)
		.httpResponseProvider(
			new FileHttpResponseProvider(REQUEST_LOG_DIR, REQUEST_LOG_FILE_NAME + "_" + request))
		.build();
    }
}
