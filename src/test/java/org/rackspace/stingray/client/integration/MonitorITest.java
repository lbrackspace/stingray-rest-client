package org.rackspace.stingray.client.integration;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Child;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.monitor.Monitor;

public class MonitorITest {
    StingrayRestClient client;


    @Before
    public void standUp() {
        client = new StingrayRestClient();
    }

    /**
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     *
     */
    @Test
    public void getListOfMonitors() throws StingrayRestClientException {
        Children children = client.getMonitors();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    @Test
    public void getSpecificMonitor() throws StingrayRestClientException {
        Children children = client.getMonitors();
        Assert.assertTrue(children.getChildren().size() > 0);
        Child child = children.getChildren().get(0);
        String vsname = child.getName();
        Monitor monitor = client.getMonitor(vsname);
        Assert.assertNotNull(monitor);
    }
}
