package org.rackspace.stingray.client.integration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.pool.Pool;
import org.rackspace.stingray.client.virtualserver.VirtualServer;
import org.rackspace.stingray.client.virtualserver.VirtualServerBasic;
import org.rackspace.stingray.client.virtualserver.VirtualServerProperties;

public class VirtualServerIntegrationTest extends StingrayTestBase {
    StingrayRestClient client;
    String poolName;
    String vsName;
    Integer port;
    VirtualServer virtualServer;
    VirtualServerProperties properties;
    VirtualServerBasic basic;

    @Before
    public void standUp() {
        client = new StingrayRestClient();
        poolName = "i_test_pool";
        vsName = "i_test_vs";
        port = 8998;
        basic.setPool(poolName);
        basic.setPort(port);
        properties.setBasic(basic);
        virtualServer.setProperties(properties);
    }

    /**
     * This method tests the create virtual server request, and will verify its creation with a get request.
     * @throws StingrayRestClientException
     */
    @Test
    public void createVirtualServer() throws StingrayRestClientException {
        Pool pool = client.createPool(poolName, new Pool());
        Assert.assertNotNull(pool);
        VirtualServerProperties props = new VirtualServerProperties();
        VirtualServer vs = client.createVirtualServer(vsName, virtualServer);
        Assert.assertNotNull(vs);
    }

    /**
     * This method tests that a list of children holding the name and URI for every virtual server can be retrieved.
     * @throws StingrayRestClientException
     */
    @Test
    public void getListOfVirtualServers() throws StingrayRestClientException {
        Children children = client.getVirtualServers();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    /**
     * This method tests that one virtual server can be retrieved.
     * @throws StingrayRestClientException
     */
    @Test
    public void getSpecificVirtualServer() throws StingrayRestClientException {
        VirtualServer vs = client.getVirtualServer(vsName);
        Assert.assertNotNull(vs);
    }

    @Test
    public void deleteVirtualServer() throws StingrayRestClientException {
        Boolean result = client.deleteVirtualServer(vsName);
        Assert.assertTrue(result);
    }
}