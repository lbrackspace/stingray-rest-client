package org.rackspace.stingray.client.integration;

import com.sun.corba.se.impl.ior.POAObjectKeyTemplate;
import com.sun.tools.corba.se.idl.StringGen;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Child;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.pool.Pool;
import org.rackspace.stingray.client.pool.PoolBasic;
import org.rackspace.stingray.client.pool.PoolProperties;

public class PoolITest extends  StingrayTestBase {
    StingrayRestClient client;
    String vsName;
    Pool pool;
    PoolProperties poolProperties;
    PoolBasic poolBasic;


    @Before
    public void standUp() {
        client = new StingrayRestClient();
        vsName = TESTNAME;
        pool = new Pool();
        poolProperties = new PoolProperties();
        poolBasic = new PoolBasic();

        poolProperties.setBasic(poolBasic);
        pool.setProperties(poolProperties);


    }


    /**
     * Tests the creation of a Pool
     * Verifies using get and a comparison of content contained
     * @throws StingrayRestClientException
     */
    @Test
    public void testCreatePool() throws StingrayRestClientException {
        Pool createdPool = client.createPool(vsName, pool);
        Assert.assertNotNull(createdPool);
        Pool retrievedPool = client.getPool(vsName);
        Assert.assertNotNull(retrievedPool);
    }

    @Test
    public void testUpdatePool() throws StingrayRestClientException {
        String updateNote = "qwertyuiop";
        pool.getProperties().getBasic().setNote(updateNote);
        Pool updatedPool = client.updatePool(vsName, pool);
        Assert.assertEquals(updateNote, updatedPool.getProperties().getBasic().getNote());
    }

    /**
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     *
     */
    @Test
    public void testGetListOfPools() throws StingrayRestClientException {
        Children children = client.getPools();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    @Test
    public void testGetPool() throws StingrayRestClientException {
        Pool retrievedPool = client.getPool(vsName);
        Assert.assertNotNull(retrievedPool);
    }

    @Test
    public void testDeletePool() throws StingrayRestClientException {
        Boolean wasDeleted = client.deletePool(vsName);
        Assert.assertTrue(wasDeleted);
    }

}
