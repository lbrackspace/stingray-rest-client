package org.rackspace.stingray.client.integration;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Child;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.rate.Rate;
import org.rackspace.stingray.client.rate.RateBasic;
import org.rackspace.stingray.client.rate.RateProperties;

public class RateITest extends StingrayTestBase{
    StingrayRestClient client;
    String vsName;
    Rate rate;
    RateProperties rateProperties;
    RateBasic rateBasic;

    @Before
    public void standUp() {
        client = new StingrayRestClient();
        vsName = TESTNAME;
        rate = new Rate();
        rateProperties = new RateProperties();
        rateBasic = new RateBasic();

        rateProperties.setBasic(rateBasic);
        rate.setProperties(rateProperties);

    }

    /**
     * Tests the creation of a Rate
     * Verifies using get and a comparison of content contained
     * @throws StingrayRestClientException
     */
    @Test
    public void testCreateRate() throws StingrayRestClientException {
        Rate createdRate = client.createRate(vsName, rate);
        Assert.assertNotNull(createdRate);
        Assert.assertEquals(createdRate, client.getRate(vsName));
    }

    @Test
    public void testUpdateRate() throws StingrayRestClientException {
        int updatePerMin = 17;
        rate.getProperties().getBasic().setMax_rate_per_minute(updatePerMin);
        Rate updatedRate = client.updateRate(vsName, rate);
        Assert.assertNotNull(updatedRate);
        int retrievedPerMin = updatedRate.getProperties().getBasic().getMax_rate_per_minute();
        Assert.assertEquals(updatePerMin, retrievedPerMin);
    }

    /**
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     *
     */
    @Test
    public void testGetListOfRates() throws StingrayRestClientException {
        Children children = client.getRates();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    @Test
    public void testGetRate() throws StingrayRestClientException {
        Rate retrievedRate = client.getRate(vsName);
        Assert.assertNotNull(retrievedRate);
    }

    @Test
    public void testDeleteRate() throws StingrayRestClientException {
        Boolean wasDeleted = client.deleteRate(vsName);
        Assert.assertTrue(wasDeleted);
    }
}
