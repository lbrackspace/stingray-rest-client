package org.rackspace.stingray.client.integration;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.ssl.client.keypair.ClientKeypair;
import org.rackspace.stingray.client.ssl.client.keypair.ClientKeypairBasic;
import org.rackspace.stingray.client.ssl.client.keypair.ClientKeypairProperties;


public class SslClientKeypairITest extends StingrayTestBase {
    StingrayRestClient client;
    String vsName;
    ClientKeypair clientKeypair;
    ClientKeypairProperties clientKeypairProperties;
    ClientKeypairBasic clientKeypairBasic;

    /**
     * Initializes variables prior to test execution
     */
    @Before
    public void standUp() {
        client = new StingrayRestClient();
        vsName = TESTNAME;
        clientKeypair = new ClientKeypair();
        clientKeypairProperties = new ClientKeypairProperties();
        clientKeypairBasic = new ClientKeypairBasic();

        clientKeypairProperties.setBasic(clientKeypairBasic);
        clientKeypair.setProperties(clientKeypairProperties);

    }

    /**
     * Tests the creation of a Client Keypair
     * Verifies using get and a comparison of content contained
     *
     * @throws StingrayRestClientException
     */
    @Test
    public void testCreateClientKeypair() throws StingrayRestClientException {
        ClientKeypair createdClientKeypair = client.createClientKeypair(vsName, clientKeypair);
        Assert.assertNotNull(createdClientKeypair);
        Assert.assertEquals(createdClientKeypair, client.getClientKeypair(vsName));
    }

    /**
     * Tests the updating of a Client Keypair
     * Verifies using a get and a comparison of content contained
     *
     * @throws StingrayRestClientException
     */
    @Test
    public void testUpdateClientKeypair() throws StingrayRestClientException {
        String updateNote = "qwertyuiop";
        clientKeypair.getProperties().getBasic().setNote(updateNote);
        ClientKeypair updatedKeypair = client.updateClientKeypair(vsName, clientKeypair);
        Assert.assertNotNull(updatedKeypair);
        String actualNote = updatedKeypair.getProperties().getBasic().getNote();
        Assert.assertEquals(updateNote, actualNote);


    }

    /**
     * Tests the retrieval of a list of Client Keypairs
     * Retrieves a list of action scripts and checks its size
     *
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     *
     */
    @Test
    public void testGetListOfClientKeypairs() throws StingrayRestClientException {
        Children children = client.getClientKeypairs();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    /**
     * Tests the get function for an individual Client Keypair
     * Retrieves the specific Action Script created earlier
     *
     * @throws StingrayRestClientException
     */
    @Test
    public void testGetClientKeypair() throws StingrayRestClientException {
        ClientKeypair retrievedKeypair = client.getClientKeypair(vsName);
        Assert.assertNotNull(retrievedKeypair);
    }

    /**
     * Tests the deletion of a Client Keypair
     * Checks return of the delete call, and throws an error
     *
     * @throws StingrayRestClientException
     */
    @Test(expected = StingrayRestClientException.class)
    public void testDeleteClientKeypair() throws StingrayRestClientException {
        Boolean wasDeleted = client.deleteClientKeypair(vsName);
        Assert.assertTrue(wasDeleted);
        client.getClientKeypair(vsName);
    }


}
