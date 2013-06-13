package org.rackspace.stingray.client.integration;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.exception.StingrayRestClientPathException;
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

    /**
     * Initializes variables prior to test execution
     */
    @Before
    public void standUp() {
        client = new StingrayRestClient();
        basic = new TrafficIpBasic();
        properties = new TrafficIpProperties();
        properties.setBasic(basic);
        tip = new TrafficIp();
        tip.setProperties(properties);
    }

    /**
     * Tests the creation of a Traffic IP
     * Verifies using get and a comparison of content contained
     *
     * @throws StingrayRestClientException
     */
    @Test
    public void testCreateTrafficIp() throws StingrayRestClientException, StingrayRestClientPathException {
        TrafficIp createdTip = client.createTrafficIp(TESTNAME, tip);
        Assert.assertNotNull(createdTip);
    }

    /**
     * Tests the retrieval of a list of Traffic Ips
     * Retrieves a list of action scripts and checks its size
     *
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     *
     */
    @Test
    public void testGetListOfTrafficIps() throws StingrayRestClientException, StingrayRestClientPathException {
        Children children = client.getTrafficIps();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    /**
     * Tests the get function for an individual Traffic Ip
     * Retrieves the specific Action Script created earlier
     *
     * @throws StingrayRestClientException
     */
    @Test
    public void testGetTrafficIp() throws StingrayRestClientException, StingrayRestClientPathException {
        Children children = client.getTrafficIps();
        Assert.assertTrue(children.getChildren().size() > 0);
        Child child = children.getChildren().get(0);
        String vsname = child.getName();
        TrafficIp trafficIp = client.getTrafficIp(vsname);
        Assert.assertNotNull(trafficIp);
    }

    /**
     * Tests the deletion of a Traffic Ip
     * Checks return of the delete call, and throws an error
     *
     * @throws StingrayRestClientException
     */
    @Test
    public void testDeleteTrafficIp() throws StingrayRestClientException, StingrayRestClientPathException {
        Boolean result = client.deleteTrafficIp(TESTNAME);
        Assert.assertTrue(result);
    }
}
