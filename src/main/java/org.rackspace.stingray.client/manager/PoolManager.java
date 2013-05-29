package org.rackspace.stingray.client.manager;

import org.rackspace.stingray.client.pool.Pool;

public interface PoolManager {

    public Pool retrievePool(String vsName);

}
