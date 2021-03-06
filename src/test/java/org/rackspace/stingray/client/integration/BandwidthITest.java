package org.rackspace.stingray.client.integration;


import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.bandwidth.Bandwidth;
import org.rackspace.stingray.client.bandwidth.BandwidthBasic;
import org.rackspace.stingray.client.bandwidth.BandwidthProperties;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.exception.StingrayRestClientPathException;
import org.rackspace.stingray.client.list.Child;
import org.rackspace.stingray.client.list.Children;

public class BandwidthITest extends StingrayTestBase {
    StingrayRestClient client;
    Bandwidth bandwidth;
    BandwidthProperties bandwidthProperties;
    BandwidthBasic bandwidthBasic;
    String vsName;

    /**
     * Initializes variables prior to test execution
     */
    @Before
    public void standUp() {
        client = new StingrayRestClient();
        bandwidth = new Bandwidth();
        bandwidthProperties = new BandwidthProperties();
        bandwidthBasic = new BandwidthBasic();
        bandwidthProperties.setBasic(bandwidthBasic);
        bandwidth.setProperties(bandwidthProperties);
        vsName = TESTNAME;
    }

    /**
     * Tests the creation of a Bandwidth
     * Verifies using get and a comparison of content contained
     *
     * @throws StingrayRestClientException
     */
    @Test
    public void testCreateBandwidth() throws StingrayRestClientException, StingrayRestClientPathException {
        Bandwidth createdBandwidth = client.createBandwidth(vsName, bandwidth);
        Assert.assertNotNull(createdBandwidth);
        Assert.assertEquals(createdBandwidth, client.getBandwidth(vsName));


    }

    /**
     * Tests the updating of a Bandwidth
     * Verifies using a get and a comparison of content contained
     *
     * @throws StingrayRestClientException
     */
    @Test
    public void testUpdateBandwidth() throws StingrayRestClientException, StingrayRestClientPathException {
        int testLimit = 1;
        bandwidth.getProperties().getBasic().setMaximum(testLimit);
        Bandwidth updatedBandwidth = client.updateBandwidth(vsName, bandwidth);
        Assert.assertEquals((int) updatedBandwidth.getProperties().getBasic().getMaximum(), (int) testLimit);
    }

    /**
     * Tests the retrieval of a list of Bandwidths
     * Retrieves a list of action scripts and checks its size
     *
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     *
     */
    @Test
    public void testGetListOfBandwidths() throws StingrayRestClientException, StingrayRestClientPathException {
        Children children = client.getBandwidths();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    /**
     * Tests the get function for an individual Bandwidth
     * Retrieves the specific Action Script created earlier
     *
     * @throws StingrayRestClientException
     */
    @Test
    public void testGetBandwidth() throws StingrayRestClientException, StingrayRestClientPathException {
        Bandwidth retrievedBandwidth = client.getBandwidth(vsName);
        Assert.assertNotNull(retrievedBandwidth);
    }

    /**
     * Tests the deletion of a Bandwidth
     * Checks return of the delete call, and throws an error
     *
     * @throws StingrayRestClientException
     */
    @Test(expected = StingrayRestClientException.class)
    public void deleteBandwidth() throws StingrayRestClientException, StingrayRestClientPathException {
        Boolean wasDeleted = client.deleteBandwidth(vsName);
        Assert.assertTrue(wasDeleted);
        client.getBandwidth(vsName);

    }

}
