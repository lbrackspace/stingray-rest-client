package org.rackspace.stingray.client;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.rackspace.stingray.client.bandwidth.Bandwidth;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.manager.RequestManager;

import javax.ws.rs.core.MediaType;
import java.net.URI;

import static org.mockito.Mockito.when;

@RunWith(Enclosed.class)
public class StingrayRestClientTest {

    @RunWith(MockitoJUnitRunner.class)
    public static class WhenGettingAListOfItems {
        @Mock
        private RequestManager requestManager;
        @InjectMocks
        private StingrayRestClient stingrayRestClient;

        @Before
        public void standUp() throws StingrayRestClientException {
            stingrayRestClient.setRequestManager(requestManager);
            when(requestManager.getList(Matchers.<URI>any(), Matchers.<Client>any(), Matchers.<String>any())).thenReturn(new Children());
        }

        @Test
        public void shouldReturnWhenPoolPathValid() throws Exception {
            Children pools = stingrayRestClient.getPools();

            Assert.assertNotNull(pools);
        }
    }

    @RunWith(MockitoJUnitRunner.class)
    public static class whenGettingAnItem {
        @Mock
        private RequestManager requestManager;
        @InjectMocks
        private StingrayRestClient stingrayRestClient;
        @Mock
        private ClientResponse mockedResponse;
        private Bandwidth bandwidth;
        private String vsName;


        @Before
        public void standUp() throws StingrayRestClientException {
            stingrayRestClient.setRequestManager(requestManager);
            bandwidth = new Bandwidth();

            when(mockedResponse.getEntity(Bandwidth.class)).thenReturn(bandwidth);
            when(requestManager.getItem(Matchers.<URI>any(), Matchers.<Client>any(), Matchers.<String>any(), Matchers.<MediaType>any())).thenReturn(mockedResponse);
        }


        @Test
        public void shouldReturnABandwidth() throws Exception {
            vsName = "12345_1234";
            Bandwidth bandwidth = stingrayRestClient.getBandwidth(vsName);
            Assert.assertNotNull(bandwidth);
        }


    }


    @RunWith(MockitoJUnitRunner.class)
    public static class whenUpdatingAnItem {
        @Mock
        private RequestManager requestManager;
        @InjectMocks
        private StingrayRestClient stingrayRestClient;
        @Mock
        private ClientResponse mockedResponse;
        private Bandwidth bandwidth;
        private String vsName;


        @Before
        public void standUp() throws StingrayRestClientException {
            stingrayRestClient.setRequestManager(requestManager);
            bandwidth = new Bandwidth();

            when(mockedResponse.getEntity(Bandwidth.class)).thenReturn(bandwidth);
            when(requestManager.updateItem(Matchers.<URI>any(), Matchers.<Client>any(), Matchers.<String>any(), Matchers.<Bandwidth>any(), Matchers.<MediaType>any())).thenReturn(mockedResponse);
        }


        @Test
        public void shouldReturnABandwidth() throws Exception {
            vsName = "12345_1234";
            Bandwidth bandwidthTwo = stingrayRestClient.updateBandwidth(vsName, bandwidth);
            Assert.assertNotNull(bandwidthTwo);
        }
    }



    @RunWith(MockitoJUnitRunner.class)
    public static class whenDeletingAnItem {
        @Mock
        private RequestManager requestManager;
        @InjectMocks
        private StingrayRestClient stingrayRestClient;
        @Mock
        private ClientResponse mockedResponse;
        private Bandwidth bandwidth;
        private String vsName;


        @Before
        public void standUp() throws StingrayRestClientException {
            stingrayRestClient.setRequestManager(requestManager);
            bandwidth = new Bandwidth();

            when(mockedResponse.getEntity(Bandwidth.class)).thenReturn(bandwidth);
            when(requestManager.deleteItem(Matchers.<URI>any(), Matchers.<Client>any(), Matchers.<String>any())).thenReturn(true);
        }


        @Test
        public void shouldReturnABandwidth() throws Exception {
            vsName = "12345_1234";
            stingrayRestClient.deletePool(vsName);
            Assert.assertTrue(true);

        }


    }


}