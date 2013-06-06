package org.rackspace.stingray.client;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.junit.Assert;

import org.rackspace.stingray.client.config.Configuration;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.manager.RequestManager;
import org.rackspace.stingray.client.manager.impl.RequestManagerImpl;
import org.rackspace.stingray.client.mock.MockClientHandler;
import org.rackspace.stingray.client.pool.Pool;
import org.rackspace.stingray.client.pool.PoolHttp;
import org.rackspace.stingray.client.pool.PoolProperties;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(Enclosed.class)
public class StingrayRestClientTest {


    @RunWith(MockitoJUnitRunner.class)
    public static class whenGettingAListOfItems {
        private Client client;
        private MockClientHandler mockClientHandler;
        private ClientResponse mockedResponse;
        private RequestManager mockedManager;
        private Children children;
        private WebResource webResource;
        private WebResource.Builder builder;

        @Before
        public void standUp() throws URISyntaxException {
            mockClientHandler = new MockClientHandler();
            mockedManager = mock(RequestManager.class);
            children = new Children();
            ArrayList list = new ArrayList<Pool>();
            children.setChildren(list);


        }

        private void setupMocks() throws URISyntaxException {
            ClientRequest clientRequest = new ClientRequest.Builder().accept(MediaType.APPLICATION_JSON).build(getItemsPath(), "GET");
            mockedResponse = mockClientHandler.handle(clientRequest);

            client = mock(Client.class);
            webResource = mock(WebResource.class);
            builder = mock(WebResource.Builder.class);

            when(client.resource(anyString())).thenReturn(webResource);
            when(webResource.accept(MediaType.APPLICATION_JSON)).thenReturn(builder);
            when(builder.get(ClientResponse.class)).thenReturn(mockedResponse);
            try {
                when(mockedManager.getList(getItemsPath(), client, anyString())).thenReturn(children);
            } catch (Exception e) {
            }
        }


        private URI getItemsPath() throws URISyntaxException {

            return new URI(MockClientHandler.ROOT + "pools/");
        }

        @Test
        public void genericGetItemsReturnsValidList() throws Exception {
            mockClientHandler.when("pools/", "GET").thenReturn(Response.Status.ACCEPTED, children);
            ArrayList<Pool> actualList = new ArrayList<Pool>();
            setupMocks();


            StingrayRestClient myClient = new StingrayRestClient();
            Assert.assertNotNull(myClient.getPools());
            Assert.assertNotNull(myClient.getPools().getChildren().get(0));
            Assert.assertEquals(actualList.getClass(), myClient.getPools().getChildren().getClass());
        }


    }


    @RunWith(MockitoJUnitRunner.class)
    public static class whenGettingAnItem {

        private Client client;
        private MockClientHandler mockClientHandler;
        private ClientResponse mockedResponse;
        private RequestManager mockedManager;
        private WebResource webResource;
        private WebResource.Builder builder;
        private Pool pool;
        private String vsName;

        @Before
        public void standUp() throws URISyntaxException {
            mockClientHandler = new MockClientHandler();
            mockedManager = mock(RequestManager.class);


            vsName = "12345_1234";

            pool = createPool();


        }


        private Pool createPool() {
            PoolProperties poolProperties = new PoolProperties();
            PoolHttp poolHttp = new PoolHttp();
            poolHttp.setKeepalive(true);
            poolProperties.setHttp(poolHttp);

            Pool pool = new Pool();
            pool.setProperties(poolProperties);
            return pool;
        }

        private void setupMocks() throws URISyntaxException {
            ClientRequest clientRequest = new ClientRequest.Builder().accept(MediaType.APPLICATION_JSON).build(getItemPath(), "GET");
            mockedResponse = mockClientHandler.handle(clientRequest);

            client = mock(Client.class);
            webResource = mock(WebResource.class);
            builder = mock(WebResource.Builder.class);

            when(client.resource(anyString())).thenReturn(webResource);
            when(webResource.accept(MediaType.APPLICATION_JSON)).thenReturn(builder);
            when(builder.get(ClientResponse.class)).thenReturn(mockedResponse);

        }


        private URI getItemPath() throws URISyntaxException {

            return new URI(MockClientHandler.ROOT + "pools/" + vsName);
        }

        @Ignore
        @Test
        public void genericGetItemReturnsValidObject() throws Exception {
            mockClientHandler.when("pools/" + vsName, "GET").thenReturn(Response.Status.ACCEPTED, pool);

            setupMocks();


            StingrayRestClient myClient = new StingrayRestClient();
            Assert.assertNotNull(myClient.getPool(vsName));
        }


    }


    @Test
    public void genericGetItemReturnsValidObject() throws Exception {

    }

    @Test
    public void testValidPath() {

    }

    @Test
    public void testInvalidPath() {

    }
}