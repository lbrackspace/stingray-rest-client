package org.rackspace.stingray.client.integration;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Child;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.traffic.ip.TrafficIp;

public class TrafficIpITest {
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
    public void getListOfTrafficIps() throws StingrayRestClientException {
        Children children = client.getTrafficIps();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    @Test
    public void getSpecificTrafficIp() throws StingrayRestClientException {
        Children children = client.getTrafficIps();
        Assert.assertTrue(children.getChildren().size() > 0);
        Child child = children.getChildren().get(0);
        String vsname = child.getName();
        TrafficIp trafficIp = client.getTrafficIp(vsname);
        Assert.assertNotNull(trafficIp);

    }
}
