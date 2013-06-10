package org.rackspace.stingray.client.integration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Child;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.tm.TrafficManager;
import org.rackspace.stingray.client.tm.TrafficManagerBasic;
import org.rackspace.stingray.client.tm.TrafficManagerProperties;

public class TrafficManagerITest extends StingrayTestBase {
    StingrayRestClient client;
    TrafficManager manager;
    TrafficManagerProperties properties;
    TrafficManagerBasic basic;

    @Before
    public void standUp() {
        client = new StingrayRestClient();
        basic = new TrafficManagerBasic();
        properties = new TrafficManagerProperties();
        properties.setBasic(basic);
        manager = new TrafficManager();
        manager.setProperties(properties);
    }

    @Test
    public void testCreateTrafficManager() throws StingrayRestClientException {
        TrafficManager createdManager = client.createTrafficManager(TESTNAME, manager);
        Assert.assertNotNull(createdManager);
    }

    /**
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     *
     */
    @Test
    public void testGetListOfTrafficManagers() throws StingrayRestClientException {
        Children children = client.getTrafficManagers();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    @Test
    public void testGetTrafficManager() throws StingrayRestClientException {
        TrafficManager trafficManager = client.getTrafficManager(TESTNAME);
        Assert.assertNotNull(trafficManager);
    }

    @Test
    public void testDeleteTrafficManager() throws StingrayRestClientException {
        client.deleteTrafficManager(TESTNAME);
        Children children = client.getTrafficManagers();
        Boolean deleted = true;
        for (Child child : children.getChildren()) {
            if (child.getName().equals(TESTNAME)) {
                deleted = false;
            }
        }
        Assert.assertTrue(deleted);
    }
}
