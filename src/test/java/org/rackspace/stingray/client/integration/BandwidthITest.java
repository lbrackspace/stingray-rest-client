package org.rackspace.stingray.client.integration;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.bandwidth.Bandwidth;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Child;
import org.rackspace.stingray.client.list.Children;

public class BandwidthITest {
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
    public void getListOfBandwidths() throws StingrayRestClientException {
        Children children = client.getBandwidths();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    @Test
    public void getSpecificBandwidth() throws StingrayRestClientException {
        Children children = client.getBandwidths();
        Assert.assertTrue(children.getChildren().size() > 0);
        Child child = children.getChildren().get(0);
        String vsname = child.getName();
        Bandwidth bandwidth = client.getBandwidth(vsname);
        Assert.assertNotNull(bandwidth);
    }
}
