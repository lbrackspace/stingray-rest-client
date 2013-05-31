package org.rackspace.stingray.client.manager;

import com.sun.jersey.api.client.Client;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.pool.Pool;
import org.rackspace.stingray.client.util.StingrayList;

import java.net.URI;

public interface PoolManager {

    public StingrayList getPools(URI endpoint, Client client);

    public Pool retrievePool(URI endpoint, Client client, String vsName) throws StingrayRestClientException;

    public Pool createPool(URI endpoint, Client client, String vsName, Pool pool) throws StingrayRestClientException;

    public Pool updatePool(URI endpoint, Client client, String vsName, Pool pool) throws StingrayRestClientException;

    public boolean deletePool(URI endpoint, Client client, String vsName) throws StingrayRestClientException;

    //Todo: the rest of the pool manager methods...

}
