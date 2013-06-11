package org.rackspace.stingray.client.integration;


import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Child;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.monitor.Monitor;
import org.rackspace.stingray.client.monitor.MonitorBasic;
import org.rackspace.stingray.client.monitor.MonitorProperties;

public class MonitorITest {
    StingrayRestClient client;
    Monitor monitor;
    MonitorProperties monitorProperties;
    MonitorBasic monitorBasic;
    String vsName;

    @Before
    public void standUp() {
        client = new StingrayRestClient();
        monitor = new Monitor();
        monitorProperties = new MonitorProperties();
        monitorBasic = new MonitorBasic();
        vsName = "i_test_monitor";

        monitorProperties.setBasic(monitorBasic);
        monitor.setProperties(monitorProperties);
    }


    @Test
    public void testCreateMonitor() throws StingrayRestClientException {
        Monitor createdMonitor = client.createMonitor(vsName, monitor);
        Assert.assertNotNull(createdMonitor);
        Assert.assertEquals(createdMonitor, client.getMonitor(vsName));

    }

    @Test
    public void testUpdateMonitor() throws StingrayRestClientException {
        int updateTimeout = 17;
        monitor.getProperties().getBasic().setTimeout(updateTimeout);
        Monitor updatedMonitor = client.updateMonitor(vsName, monitor);
        Assert.assertNotNull(updatedMonitor);
        Assert.assertEquals(updateTimeout, (int) updatedMonitor.getProperties().getBasic().getTimeout());
    }


    /**
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     *
     */
    @Test
    public void testGetListOfMonitors() throws StingrayRestClientException {
        Children children = client.getMonitors();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    @Test
    public void testGetMonitor() throws StingrayRestClientException {
        Monitor retrievedMonitor = client.getMonitor(vsName);
        Assert.assertNotNull(retrievedMonitor);
    }

    @Test
    public void testDeleteMonitor() throws StingrayRestClientException {
        Boolean wasDeleted = client.deleteMonitor(vsName);
        Assert.assertTrue(wasDeleted);

    }


}
