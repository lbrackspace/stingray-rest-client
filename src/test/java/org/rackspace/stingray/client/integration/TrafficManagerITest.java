package org.rackspace.stingray.client.integration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.tm.TrafficManager;
import org.rackspace.stingray.client.tm.TrafficManagerBasic;
import org.rackspace.stingray.client.tm.TrafficManagerProperties;

public class TrafficManagerITest extends StingrayTestBase {
    StingrayRestClient client;
    TrafficManager manager;
    TrafficManagerProperties properties;
    TrafficManagerBasic basic;

    /**
     * Initializes variables prior to test execution
     */
    @Before
    public void standUp() {
        client = new StingrayRestClient();
        basic = new TrafficManagerBasic();
        properties = new TrafficManagerProperties();
        properties.setBasic(basic);
        manager = new TrafficManager();
        manager.setProperties(properties);
    }

    /**
     * Tests the creation of a Traffic Manager
     * Verifies using get and a comparison of content contained
     *
     * @throws StingrayRestClientException
     */
    @Test
    public void testCreateTrafficManager() throws StingrayRestClientException {
        TrafficManager createdManager = client.createTrafficManager(TESTNAME, manager);
        Assert.assertNotNull(createdManager);
    }

    /**
     * Tests the retrieval of a list of Traffic Managers
     * Retrieves a list of action scripts and checks its size
     *
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     *
     */
    @Test
    public void testGetListOfTrafficManagers() throws StingrayRestClientException {
        Children children = client.getTrafficManagers();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    /**
     * Tests the get function for an individual Traffic Manager
     * Retrieves the specific Action Script created earlier
     *
     * @throws StingrayRestClientException
     */
    @Test
    public void testGetTrafficManager() throws StingrayRestClientException {
        TrafficManager trafficManager = client.getTrafficManager(TESTNAME);
        Assert.assertNotNull(trafficManager);
    }

    /**
     * Tests the deletion of a Traffic Manager
     * Checks return of the delete call, and throws an error
     *
     * @throws StingrayRestClientException
     */
    @Test
    public void testDeleteTrafficManager() throws StingrayRestClientException {
        Boolean result = client.deleteTrafficManager(TESTNAME);
        Assert.assertTrue(result);
    }
}
