package org.rackspace.stingray.client.manager.impl;

import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.manager.PoolManager;
import org.rackspace.stingray.client.pool.Pool;

public class PoolManagerImpl implements PoolManager {
    String poolPath = "pools/";

    @Override
    public Pool retrievePool(String vsName) {
        StingrayRestClient client = new StingrayRestClient();
        Pool pool = null;
        try {
            pool = client.getResource(poolPath + vsName).getEntity(Pool.class);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            //ADD Checked Exception. EX: StingrayRestClientGeneralException...
        }
        return pool;
    }
}
