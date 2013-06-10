package org.rackspace.stingray.client.integration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Child;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.tm.TrafficManager;

public class TrafficManagerITest {
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
    public void getListOfTrafficManagers() throws StingrayRestClientException {
        Children children = client.getTrafficManagers();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    @Test
    public void getSpecificTrafficManager() throws StingrayRestClientException {
        Children children = client.getTrafficManagers();
        Assert.assertTrue(children.getChildren().size() > 0);
        Child child = children.getChildren().get(0);
        String vsname = child.getName();
        TrafficManager trafficManager = client.getTrafficManager(vsname);
        Assert.assertNotNull(trafficManager);
    }
}
