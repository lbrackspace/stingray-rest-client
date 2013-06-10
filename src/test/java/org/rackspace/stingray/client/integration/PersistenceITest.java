package org.rackspace.stingray.client.integration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Child;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.persistence.Persistence;

public class PersistenceITest {
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
    public void getListOfPersistences() throws StingrayRestClientException {
        Children children = client.getPersistences();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    @Test
    public void getSpecificPersistence() throws StingrayRestClientException {
        Children children = client.getPersistences();
        Assert.assertTrue(children.getChildren().size() > 0);
        Child child = children.getChildren().get(0);
        String vsname = child.getName();
        Persistence persistence = client.getPersistence(vsname);
        Assert.assertNotNull(persistence);
    }
}
