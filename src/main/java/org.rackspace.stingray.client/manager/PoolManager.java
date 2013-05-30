package org.rackspace.stingray.client.manager;

import com.sun.jersey.api.client.Client;
import org.rackspace.stingray.client.pool.Pool;

import java.net.URI;

public interface PoolManager {

    public Pool retrievePool(URI endpoint, Client client, String vsName);

    public Pool createPool(URI endpoint, Client client, String vsName, Pool pool);

    public Pool updatePool(URI endpoint, Client client, String vsName, Pool pool);

    public void deletePool(URI endpoint, Client client, String vsName);

    //Todo: the rest of the pool manager methods...

}
