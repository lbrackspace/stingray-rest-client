package org.rackspace.stingray.client.integration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.exception.StingrayRestClientPathException;
import org.rackspace.stingray.client.list.Child;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.pool.Pool;
import org.rackspace.stingray.client.pool.PoolProperties;
import org.rackspace.stingray.client.virtualserver.VirtualServer;
import org.rackspace.stingray.client.virtualserver.VirtualServerBasic;
import org.rackspace.stingray.client.virtualserver.VirtualServerProperties;

public class VirtualServerITest extends StingrayTestBase {
    StingrayRestClient client;
    String poolName;
    String vsName;
    Integer port;
    Pool pool;
    VirtualServer virtualServer;
    VirtualServerProperties properties;
    VirtualServerBasic basic;

    /**
     * This method is the beginning for every test following.  Initial steps to the testing are completed here.
     */
    @Before
    public void standUp() {
        client = new StingrayRestClient();
        virtualServer = new VirtualServer();
        properties = new VirtualServerProperties();
        basic = new VirtualServerBasic();
        poolName = TESTNAME;
        vsName = TESTNAME;
        port = 8998;
        pool = new Pool();
        pool.setProperties(new PoolProperties());
        basic.setPool(poolName);
        basic.setPort(port);
        properties.setBasic(basic);
        virtualServer.setProperties(properties);
    }

    /**
     * This method tests the create virtual server request, and will verify its creation with a get request.
     *
     * @throws StingrayRestClientException
     */
    @Test
    public void testCreateVirtualServer() throws StingrayRestClientException, StingrayRestClientPathException {
        Pool createdPool = client.createPool(poolName, pool);
        Assert.assertNotNull(createdPool);
        VirtualServer vs = client.createVirtualServer(vsName, virtualServer);
        Assert.assertNotNull(vs);
        Children children = client.getVirtualServers();
        Boolean containsVirtualServer = false;
        for (Child child : children.getChildren()) {
            if (child.getName().equals(vsName)) {
                containsVirtualServer = true;
            }
        }
        Assert.assertTrue(containsVirtualServer);
    }

    @Test
    public void testUpdateVirtualServer() throws StingrayRestClientException, StingrayRestClientPathException {
        Integer modPort = 8999;
        virtualServer.getProperties().getBasic().setPort(modPort);
        VirtualServer vs = client.updateVirtualServer(vsName, virtualServer);
        Assert.assertTrue(vs.getProperties().getBasic().getPort().equals(modPort));
    }

    /**
     * This method tests that a list of children holding the name and URI for every virtual server can be retrieved.
     *
     * @throws StingrayRestClientException
     */
    @Test
    public void testGetVirtualServersList() throws StingrayRestClientException, StingrayRestClientPathException {
        Children children = client.getVirtualServers();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    /**
     * This method tests that one virtual server can be retrieved.
     *
     * @throws StingrayRestClientException
     */
    @Test
    public void testGetVirtualServer() throws StingrayRestClientException, StingrayRestClientPathException {
        VirtualServer vs = client.getVirtualServer(vsName);
        Assert.assertNotNull(vs);
    }

    /**
     * This method tests that our originally created virtual server is able to be deleted.
     *
     * @throws StingrayRestClientException
     */
    @Test
    public void testDeleteVirtualServer() throws StingrayRestClientException {
        Boolean result = client.deleteVirtualServer(vsName);
        Assert.assertTrue(result);
        client.deletePool(poolName);
    }
}