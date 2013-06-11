package org.rackspace.stingray.client.integration;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Child;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.ssl.client.keypair.ClientKeypair;
import org.rackspace.stingray.client.ssl.client.keypair.ClientKeypairBasic;
import org.rackspace.stingray.client.ssl.client.keypair.ClientKeypairProperties;

public class SslClientKeypairITest {
    StingrayRestClient client;
    String vsName;
    ClientKeypair clientKeypair;
    ClientKeypairProperties clientKeypairProperties;
    ClientKeypairBasic clientKeypairBasic;

    @Before
    public void standUp() {
        client = new StingrayRestClient();
        vsName = "i_test_clientKeypair";
        clientKeypair = new ClientKeypair();
        clientKeypairProperties = new ClientKeypairProperties();
        clientKeypairBasic = new ClientKeypairBasic();

        clientKeypairProperties.setBasic(clientKeypairBasic);
        clientKeypair.setProperties(clientKeypairProperties);

    }

    @Test
    public void testCreateClientKeypair() throws StingrayRestClientException
    {
        ClientKeypair createdClientKeypair = client.createClientKeypair(vsName, clientKeypair);
        Assert.assertNotNull(createdClientKeypair);
    }


    @Test
    public void testUpdateClientKeypair() throws StingrayRestClientException
    {
        String updateNote = "qwertyuiop";
        clientKeypair.getProperties().getBasic().setNote(updateNote);
        ClientKeypair updatedKeypair = client.updateClientKeypair(vsName, clientKeypair);
        Assert.assertNotNull(updatedKeypair);
        String actualNote = updatedKeypair.getProperties().getBasic().getNote();
        Assert.assertEquals(updateNote, actualNote);


    }

    /**
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     *
     */
    @Test
    public void testGetListOfClientKeypairs() throws StingrayRestClientException {
        Children children = client.getClientKeypairs();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    @Test
    public void testGetClientKeypair() throws StingrayRestClientException {
        ClientKeypair retrievedKeypair = client.getClientKeypair(vsName);
        Assert.assertNotNull(retrievedKeypair);
    }

    @Test
    public void testDeleteClientKeypair() throws StingrayRestClientException
    {
        Boolean wasDeleted = client.deleteClientKeypair(vsName);
        Assert.assertTrue(wasDeleted);
    }


}
