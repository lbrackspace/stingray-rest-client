package org.rackspace.stingray.client.manager;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import org.rackspace.stingray.client.pool.Pool;
import org.rackspace.stingray.client.util.StingrayList;

import java.net.URI;

public interface PoolManager {

    public StingrayList getPools(URI endpoint, Client client);

    public Pool retrievePool(URI endpoint, Client client, String vsName);

    public Pool createPool(URI endpoint, Client client, String vsName, Pool pool);

    public Pool updatePool(URI endpoint, Client client, String vsName, Pool pool);

    public void deletePool(URI endpoint, Client client, String vsName);

    public ClientResponse test(URI endpoint, Client client);

    //Todo: the rest of the pool manager methods...

}
