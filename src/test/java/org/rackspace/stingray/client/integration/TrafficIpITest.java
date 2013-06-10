package org.rackspace.stingray.client.integration;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Child;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.traffic.ip.TrafficIp;
import org.rackspace.stingray.client.traffic.ip.TrafficIpBasic;
import org.rackspace.stingray.client.traffic.ip.TrafficIpProperties;

public class TrafficIpITest extends StingrayTestBase {
    StingrayRestClient client;
    TrafficIp tip;
    TrafficIpProperties properties;
    TrafficIpBasic basic;

    @Before
    public void standUp() {
        client = new StingrayRestClient();
        basic = new TrafficIpBasic();
        properties = new TrafficIpProperties();
        properties.setBasic(basic);
        tip = new TrafficIp();
        tip.setProperties(properties);
    }

    @Test
    public void testCreateTrafficIp() throws StingrayRestClientException {
        TrafficIp createdTip = client.createTrafficIp(TESTNAME, tip);
        Assert.assertNotNull(createdTip);
    }

    /**
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     *
     */
    @Test
    public void testGetListOfTrafficIps() throws StingrayRestClientException {
        Children children = client.getTrafficIps();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    @Test
    public void testGetTrafficIp() throws StingrayRestClientException {
        Children children = client.getTrafficIps();
        Assert.assertTrue(children.getChildren().size() > 0);
        Child child = children.getChildren().get(0);
        String vsname = child.getName();
        TrafficIp trafficIp = client.getTrafficIp(vsname);
        Assert.assertNotNull(trafficIp);
    }

    @Test
    public void testDeleteTrafficIp() throws StingrayRestClientException {
        client.deleteTrafficIp(TESTNAME);
        Children children = client.getTrafficIps();
        Boolean deleted = true;
        for (Child child : children.getChildren()) {
            if (child.getName().equals(TESTNAME)) {
                deleted = false;
            }
        }
        Assert.assertTrue(deleted);
    }
}
