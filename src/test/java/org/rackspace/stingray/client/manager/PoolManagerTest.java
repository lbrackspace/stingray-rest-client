package org.rackspace.stingray.client.manager;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.manager.impl.PoolManagerImpl;
import org.rackspace.stingray.client.mock.MockClientHandler;
import org.rackspace.stingray.client.pool.Pool;
import org.rackspace.stingray.client.pool.PoolHttp;
import org.rackspace.stingray.client.pool.PoolProperties;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(Enclosed.class)
public class PoolManagerTest {

    @RunWith(MockitoJUnitRunner.class)
    public static class WhenRetrievingAPool {
        private PoolManager poolManager;
        private Client client;
        private WebResource webResource;
        private WebResource.Builder builder;
        private String vsName;
        private ClientResponse mockedResponse;
        private MockClientHandler mockClientHandler;
        private Pool pool;

        @Before
        public void standUp() throws URISyntaxException, IOException {
            poolManager = new PoolManagerImpl();
            vsName = "12345_1234";

            pool = createPool();

            mockClientHandler = new MockClientHandler();
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
            ClientRequest clientRequest = new ClientRequest.Builder().accept(MediaType.APPLICATION_JSON).build(getPoolPath(), "GET");
            mockedResponse = mockClientHandler.handle(clientRequest);

            client = mock(Client.class);
            webResource = mock(WebResource.class);
            builder = mock(WebResource.Builder.class);

            when(client.resource(anyString())).thenReturn(webResource);
            when(webResource.accept(MediaType.APPLICATION_JSON)).thenReturn(builder);
            when(builder.get(ClientResponse.class)).thenReturn(mockedResponse);
        }

        private URI getPoolPath() throws URISyntaxException {
            return new URI(MockClientHandler.ROOT + "pool");
        }


        @Test
        public void shouldReturnAPoolWhenResponseIsValid() throws URISyntaxException, StingrayRestClientException {
            mockClientHandler.when("pool", "GET").thenReturn(Response.Status.ACCEPTED, pool);

            setupMocks();

            Pool actualPool = poolManager.retrievePool(getPoolPath(), client, vsName);
            Assert.assertTrue(actualPool.getProperties().getHttp().getKeepalive());
            Assert.assertTrue(true);
        }

        @Test(expected = StingrayRestClientException.class)
        public void shouldThrowExceptionWhenBadResponseStatus() throws URISyntaxException, StingrayRestClientException {
            mockClientHandler.when("pool", "GET").thenReturn(Response.Status.BAD_REQUEST, pool);

            setupMocks();

            poolManager.retrievePool(getPoolPath(), client, vsName);
        }
    }
}
