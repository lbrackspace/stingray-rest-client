package org.rackspace.stingray.client.integration;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Child;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.rate.Rate;

public class RateITest {
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
    public void getListOfRates() throws StingrayRestClientException {
        Children children = client.getRates();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    @Test
    public void getSpecificRate() throws StingrayRestClientException {
        Children children = client.getRates();
        Assert.assertTrue(children.getChildren().size() > 0);
        Child child = children.getChildren().get(0);
        String vsname = child.getName();
        Rate rate = client.getRate(vsname);
        Assert.assertNotNull(rate);
    }
}
