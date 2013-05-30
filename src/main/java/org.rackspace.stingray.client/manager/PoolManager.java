package org.rackspace.stingray.client.manager;

import com.sun.jersey.api.client.Client;
import org.rackspace.stingray.client.pool.Pool;

import java.net.URI;

public interface PoolManager {

    public Pool retrievePool(URI endpoint, Client client, String vsName);

    //Todo: the rest of the pool manager methods...

}
