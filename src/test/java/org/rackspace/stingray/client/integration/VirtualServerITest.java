package org.rackspace.stingray.client.integration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Child;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.virtualserver.VirtualServer;
import org.rackspace.stingray.client.virtualserver.VirtualServerConnection;
import org.rackspace.stingray.client.virtualserver.VirtualServerProperties;

public class VirtualServerITest extends StingrayTestBase {
    StingrayRestClient client;

    @Before
    public void standUp() {
        client = new StingrayRestClient();
    }

    /**
     * @throws StingrayRestClientException
     */
    @Test
    public void getListOfVirtualServers() throws StingrayRestClientException {
        Children children = client.getVirtualServers();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    @Test
    public void getSpecificVirtualServer() throws StingrayRestClientException {
        Children children = client.getVirtualServers();
        Assert.assertTrue(children.getChildren().size() > 0);
        Child child = children.getChildren().get(0);
        String vsname = child.getName();
        VirtualServer vs = client.getVirtualServer(vsname);
        Assert.assertNotNull(vs);
    }

    @Test
    public void createVirtualServer() throws StingrayRestClientException {
    }
}