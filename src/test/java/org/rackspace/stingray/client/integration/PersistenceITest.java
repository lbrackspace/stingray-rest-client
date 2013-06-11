package org.rackspace.stingray.client.integration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Child;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.persistence.Persistence;
import org.rackspace.stingray.client.persistence.PersistenceBasic;
import org.rackspace.stingray.client.persistence.PersistenceProperties;
import org.rackspace.stingray.client.util.ClientConstants;

public class PersistenceITest {
    StingrayRestClient client;
    Persistence persistence;
    PersistenceProperties persistenceProperties;
    PersistenceBasic persistenceBasic;
    String vsName;


    @Before
    public void standUp() {
        client = new StingrayRestClient();
        vsName = "i_test_persistence";
        persistence = new Persistence();
        persistenceProperties = new PersistenceProperties();
        persistenceBasic = new PersistenceBasic();

        persistenceProperties.setBasic(persistenceBasic);
        persistence.setProperties(persistenceProperties);
    }

    @Test
    public void testCreatePersistence() throws StingrayRestClientException {
        Persistence createdPersistence = client.createPersistence(vsName, persistence);
        Assert.assertNotNull(createdPersistence);
        Assert.assertEquals(createdPersistence, client.getPersistence(vsName));
    }

    @Test
    public void testUpdatePersistence() throws StingrayRestClientException {
        String updateNote = "qwertyuiop";
        persistence.getProperties().getBasic().setNote(updateNote);
        Persistence updatedPersistence = client.updatePersistence(vsName, persistence);
        Assert.assertEquals(updateNote, client.getPersistence(vsName).getProperties().getBasic().getNote());

    }


    /**
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     *
     */
    @Test
    public void testGetListOfPersistences() throws StingrayRestClientException {
        Children children = client.getPersistences();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    @Test
    public void testGetPersistence() throws StingrayRestClientException {
        Persistence retrievedPersistence = client.getPersistence(vsName);
        Assert.assertNotNull(retrievedPersistence);
    }

    @Test
    public void testDeletePersistence() throws StingrayRestClientException {
        Boolean wasDeleted = client.deletePersistence(vsName);
        Assert.assertTrue(wasDeleted);
    }


}
