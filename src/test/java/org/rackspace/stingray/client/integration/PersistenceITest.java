package org.rackspace.stingray.client.integration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.exception.StingrayRestClientPathException;
import org.rackspace.stingray.client.list.Child;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.persistence.Persistence;
import org.rackspace.stingray.client.persistence.PersistenceBasic;
import org.rackspace.stingray.client.persistence.PersistenceProperties;
import org.rackspace.stingray.client.util.ClientConstants;

public class PersistenceITest extends StingrayTestBase {
    StingrayRestClient client;
    Persistence persistence;
    PersistenceProperties persistenceProperties;
    PersistenceBasic persistenceBasic;
    String vsName;

    /**
     * Initializes variables prior to test execution
     */
    @Before
    public void standUp() {
        client = new StingrayRestClient();
        vsName = TESTNAME;
        persistence = new Persistence();
        persistenceProperties = new PersistenceProperties();
        persistenceBasic = new PersistenceBasic();

        persistenceProperties.setBasic(persistenceBasic);
        persistence.setProperties(persistenceProperties);
    }

    /**
     * Tests the creation of a Persistence
     * Verifies using get and a comparison of content contained
     *
     * @throws StingrayRestClientException
     */
    @Test
    public void testCreatePersistence() throws StingrayRestClientException, StingrayRestClientPathException {
        Persistence createdPersistence = client.createPersistence(vsName, persistence);
        Assert.assertNotNull(createdPersistence);
        Assert.assertEquals(createdPersistence, client.getPersistence(vsName));
    }

    /**
     * Tests the updating of a Persistence
     * Verifies using a get and a comparison of content contained
     *
     * @throws StingrayRestClientException
     */
    @Test
    public void testUpdatePersistence() throws StingrayRestClientException, StingrayRestClientPathException {
        String updateNote = "qwertyuiop";
        persistence.getProperties().getBasic().setNote(updateNote);
        Persistence updatedPersistence = client.updatePersistence(vsName, persistence);
        Assert.assertEquals(updateNote, client.getPersistence(vsName).getProperties().getBasic().getNote());

    }


    /**
     * Tests the retrieval of a list of Persistences
     * Retrieves a list of action scripts and checks its size
     *
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     *
     */
    @Test
    public void testGetListOfPersistences() throws StingrayRestClientException, StingrayRestClientPathException {
        Children children = client.getPersistences();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    /**
     * Tests the get function for an individual Persistence
     * Retrieves the specific Action Script created earlier
     *
     * @throws StingrayRestClientException
     */
    @Test
    public void testGetPersistence() throws StingrayRestClientException, StingrayRestClientPathException {
        Persistence retrievedPersistence = client.getPersistence(vsName);
        Assert.assertNotNull(retrievedPersistence);
    }

    /**
     * Tests the deletion of a Persistence
     * Checks return of the delete call, and throws an error
     *
     * @throws StingrayRestClientException
     */
    @Test
    public void testDeletePersistence() throws StingrayRestClientException {
        Boolean wasDeleted = client.deletePersistence(vsName);
        Assert.assertTrue(wasDeleted);
    }


}
