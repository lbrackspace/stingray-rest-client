package org.rackspace.stingray.client.integration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Child;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.pool.Pool;

public class PoolITest {
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
    public void getListOfPools() throws StingrayRestClientException {
        Children children = client.getPools();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    @Test
    public void getSpecificPool() throws StingrayRestClientException {
        Children children = client.getPools();
        Assert.assertTrue(children.getChildren().size() > 0);
        Child child = children.getChildren().get(0);
        String vsname = child.getName();
        Pool pool = client.getPool(vsname);
        Assert.assertNotNull(pool);
    }
}
