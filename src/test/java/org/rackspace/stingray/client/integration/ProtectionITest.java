package org.rackspace.stingray.client.integration;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TestName;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Child;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.protection.Protection;
import org.rackspace.stingray.client.protection.ProtectionBasic;
import org.rackspace.stingray.client.protection.ProtectionProperties;

public class ProtectionITest extends StingrayTestBase{
    StingrayRestClient client;
    String vsName = TESTNAME;
    Protection protection;
    ProtectionProperties protectionProperties;
    ProtectionBasic protectionBasic;

    @Before
    public void standUp() {
        client = new StingrayRestClient();
        protection = new Protection();
        protectionProperties = new ProtectionProperties();
        protectionBasic = new ProtectionBasic();

        protectionProperties.setBasic(protectionBasic);
        protection.setProperties(protectionProperties);

    }

    /**
     * Tests the creation of a Protection
     * Verifies using get and a comparison of content contained
     * @throws StingrayRestClientException
     */
    @Test
    public void testCreateProtection() throws StingrayRestClientException {
        Protection createdProtection = client.createProtection(vsName, protection);
        Assert.assertNotNull(createdProtection);
        Assert.assertEquals(createdProtection, client.getProtection(vsName));
    }

    @Test
    public void testUpdateProtection() throws StingrayRestClientException {
        String updateNote = "qwertyuiop";
        protection.getProperties().getBasic().setNote(updateNote);
        Protection updatedProtection = client.updateProtection(vsName, protection);
        String actualNote = updatedProtection.getProperties().getBasic().getNote();
        Assert.assertEquals(updateNote, actualNote);
    }


    /**
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     *
     */
    @Test
    public void testGetListOfProtections() throws StingrayRestClientException {
        Children children = client.getProtections();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    @Test
    public void testGetProtection() throws StingrayRestClientException {
        Protection retrievedProtection = client.getProtection(vsName);
        Assert.assertNotNull(retrievedProtection);
    }

    @Test
    public void testDeleteProtection() throws StingrayRestClientException {
        Boolean wasDeleted = client.deleteProtection(vsName);
        Assert.assertTrue(wasDeleted);
    }

}
