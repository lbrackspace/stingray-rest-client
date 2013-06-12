package org.rackspace.stingray.client.integration;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.ssl.keypair.Keypair;
import org.rackspace.stingray.client.ssl.keypair.KeypairBasic;
import org.rackspace.stingray.client.ssl.keypair.KeypairProperties;

public class SslKeypairITest extends StingrayTestBase {
    StingrayRestClient client;
    Keypair keypair;
    KeypairProperties properties;
    KeypairBasic basic;

    /**
     * Initializes variables prior to test execution
     */
    @Before
    public void standUp() {
        client = new StingrayRestClient();
        basic = new KeypairBasic();
        properties = new KeypairProperties();
        properties.setBasic(basic);
        keypair = new Keypair();
        keypair.setProperties(properties);
    }

    /**
     * Method to test the creation of SSL Keypair
     *
     * @throws StingrayRestClientException
     */
    @Test
    public void testCreateSslKeyPair() throws StingrayRestClientException {
        Keypair createdKeypair = client.createKeypair(TESTNAME, keypair);
        Assert.assertNotNull(createdKeypair);
        Keypair verifyKeypair = client.getKeypair(TESTNAME);
        Assert.assertNotNull(verifyKeypair);
    }

    /**
     * Method that tests the request to retrieve all the names for every SSL Keypair
     *
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     *
     */
    @Test
    public void testGetListOfSslKeypairs() throws StingrayRestClientException {
        Children children = client.getKeypairs();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    /**
     * Tests the get function for an individual Keypair
     * Retrieves the specific Action Script created earlier
     *
     * @throws StingrayRestClientException
     */
    @Test
    public void testGetSslKeypair() throws StingrayRestClientException {
        Keypair keypair = client.getKeypair(TESTNAME);
        Assert.assertNotNull(keypair);
    }

    /**
     * Method to delete a specific SSL Keypair
     *
     * @throws StingrayRestClientException
     */
    @Test
    public void testDeleteSslKeyPair() throws StingrayRestClientException {
        Boolean result = client.deleteKeypair(TESTNAME);
        Assert.assertTrue(result);
    }
}
