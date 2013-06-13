package org.rackspace.stingray.client.integration;


import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.exception.StingrayRestClientPathException;
import org.rackspace.stingray.client.list.Child;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.monitor.Monitor;
import org.rackspace.stingray.client.monitor.MonitorBasic;
import org.rackspace.stingray.client.monitor.MonitorProperties;

public class MonitorITest extends StingrayTestBase {
    StingrayRestClient client;
    Monitor monitor;
    MonitorProperties monitorProperties;
    MonitorBasic monitorBasic;
    String vsName;

    /**
     * Initializes variables prior to test execution
     */
    @Before
    public void standUp() {
        client = new StingrayRestClient();
        monitor = new Monitor();
        monitorProperties = new MonitorProperties();
        monitorBasic = new MonitorBasic();
        vsName = TESTNAME;

        monitorProperties.setBasic(monitorBasic);
        monitor.setProperties(monitorProperties);
    }

    /**
     * Tests the creation of a Monitor
     * Verifies using get and a comparison of content contained
     *
     * @throws StingrayRestClientException
     */
    @Test
    public void testCreateMonitor() throws StingrayRestClientException, StingrayRestClientPathException {
        Monitor createdMonitor = client.createMonitor(vsName, monitor);
        Assert.assertNotNull(createdMonitor);
        Assert.assertEquals(createdMonitor, client.getMonitor(vsName));

    }

    /**
     * Tests the updating of a Monitor
     * Verifies using a get and a comparison of content contained
     *
     * @throws StingrayRestClientException
     */
    @Test
    public void testUpdateMonitor() throws StingrayRestClientException, StingrayRestClientPathException {
        int updateTimeout = 17;
        monitor.getProperties().getBasic().setTimeout(updateTimeout);
        Monitor updatedMonitor = client.updateMonitor(vsName, monitor);
        Assert.assertNotNull(updatedMonitor);
        Assert.assertEquals(updateTimeout, (int) updatedMonitor.getProperties().getBasic().getTimeout());
    }


    /**
     * Tests the retrieval of a list of Monitors
     * Retrieves a list of action scripts and checks its size
     *
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     *
     */
    @Test
    public void testGetListOfMonitors() throws StingrayRestClientException, StingrayRestClientPathException {
        Children children = client.getMonitors();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    /**
     * Tests the get function for an individual Monitor
     * Retrieves the specific Action Script created earlier
     *
     * @throws StingrayRestClientException
     */
    @Test
    public void testGetMonitor() throws StingrayRestClientException, StingrayRestClientPathException {
        Monitor retrievedMonitor = client.getMonitor(vsName);
        Assert.assertNotNull(retrievedMonitor);
    }

    /**
     * Tests the deletion of a Monitor
     * Checks return of the delete call, and throws an error
     *
     * @throws StingrayRestClientException
     */
    @Test
    public void testDeleteMonitor() throws StingrayRestClientException, StingrayRestClientPathException {
        Boolean wasDeleted = client.deleteMonitor(vsName);
        Assert.assertTrue(wasDeleted);

    }


}
