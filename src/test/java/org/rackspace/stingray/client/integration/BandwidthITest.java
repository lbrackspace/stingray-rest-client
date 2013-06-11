package org.rackspace.stingray.client.integration;


import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.bandwidth.Bandwidth;
import org.rackspace.stingray.client.bandwidth.BandwidthBasic;
import org.rackspace.stingray.client.bandwidth.BandwidthProperties;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Child;
import org.rackspace.stingray.client.list.Children;

public class BandwidthITest {
    StingrayRestClient client;
    Bandwidth bandwidth;
    BandwidthProperties bandwidthProperties;
    BandwidthBasic bandwidthBasic;
    String vsName;

    @Before
    public void standUp() {
        client = new StingrayRestClient();
        bandwidth = new Bandwidth();
        bandwidthProperties = new BandwidthProperties();
        bandwidthBasic = new BandwidthBasic();
        bandwidthProperties.setBasic(bandwidthBasic);
        bandwidth.setProperties(bandwidthProperties);
        vsName = "i_test_bandwidth";
    }

    @Test
    public void testCreateBandwidth() throws StingrayRestClientException {
        Bandwidth createdBandwidth = client.createBandwidth(vsName, bandwidth);
        Assert.assertNotNull(createdBandwidth);
        Children children = client.getBandwidths();
        Child child = children.getChildren().get(0);
        Assert.assertEquals(child.getName(), vsName);

    }


    @Test
    public void testUpdateBandwidth() throws StingrayRestClientException {
        int testLimit = 1;
        bandwidth.getProperties().getBasic().setMaximum(testLimit);
        Bandwidth updatedBandwidth = client.updateBandwidth(vsName, bandwidth);
        Assert.assertEquals((int) updatedBandwidth.getProperties().getBasic().getMaximum(), (int) testLimit);
    }

    /**
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     *
     */
    @Test
    public void testGetListOfBandwidths() throws StingrayRestClientException {
        Children children = client.getBandwidths();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    @Test
    public void testGetSpecificBandwidth() throws StingrayRestClientException {
        Children children = client.getBandwidths();
        Assert.assertTrue(children.getChildren().size() > 0);
        Child child = children.getChildren().get(0);
        String vsname = child.getName();
        Bandwidth bandwidth = client.getBandwidth(vsname);
        Assert.assertNotNull(bandwidth);
    }


    @Test
    public void deleteBandwidth() throws StingrayRestClientException {
        Boolean wasDeleted = client.deleteBandwidth(vsName);
        Assert.assertTrue(wasDeleted);
    }

}
