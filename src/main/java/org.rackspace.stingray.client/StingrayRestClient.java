package org.rackspace.stingray.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import org.rackspace.stingray.client.config.Configuration;
import org.rackspace.stingray.client.config.virtualserver.VirtualServer;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.manager.RequestManager;
import org.rackspace.stingray.client.manager.StingrayRestClientManager;
import org.rackspace.stingray.client.manager.impl.RequestManagerImpl;
import org.rackspace.stingray.client.pool.Pool;
import org.rackspace.stingray.client.util.ClientConstants;

import java.net.URI;

public class StingrayRestClient extends StingrayRestClientManager {
    private final RequestManager requestManager = new RequestManagerImpl();

    public StingrayRestClient(URI endpoint, Configuration config, Client client) {
        super(config, endpoint, client, false, null, null);
    }

    public StingrayRestClient(URI endpoint, Configuration config) {
        super(config, endpoint, null, false, null, null);
    }

    public StingrayRestClient(URI endpoint) {
        super(null, endpoint, null, false, null, null);
    }

    public StingrayRestClient(URI endpoint, String adminUser, String adminKey) {
        super(null, endpoint, null, false, adminUser, adminKey);
    }

    public StingrayRestClient(URI endpoint, boolean isDebugging, String adminUser, String adminKey) {
        super(null, endpoint, null, isDebugging, adminUser, adminKey);
    }

    public StingrayRestClient(boolean isDebugging) {
        super(null, null, null, isDebugging, null, null);
    }

    public StingrayRestClient() {
        super(null, null, null, false, null, null);
    }


    /**
     * Virtual Servers
     */


    /**
     * @param vsName
     * @return
     * @throws StingrayRestClientException
     */
    public VirtualServer retrieveVirtualServer(String vsName) throws StingrayRestClientException {
        ClientResponse response = requestManager.retrieveItem(endpoint, client, ClientConstants.SERVER_PATH + vsName);
        return interpretResponse(response, VirtualServer.class);
    }

    /**
     * @param vsName
     * @param virtualServer
     * @return
     * @throws StingrayRestClientException
     */
    public VirtualServer createVirtualServer(String vsName, VirtualServer virtualServer) throws StingrayRestClientException {
        ClientResponse response = requestManager.createItem(endpoint, client, ClientConstants.SERVER_PATH + vsName, virtualServer);
        return interpretResponse(response, VirtualServer.class);
    }

    /**
     * @param vsName
     * @param virtualServer
     * @return
     * @throws StingrayRestClientException
     */
    public VirtualServer updateVirtualServer(String vsName, VirtualServer virtualServer) throws StingrayRestClientException {
        ClientResponse response = requestManager.updateItem(endpoint, client, ClientConstants.SERVER_PATH + vsName, virtualServer);
        return interpretResponse(response, VirtualServer.class);
    }

    /**
     * @param vsName
     * @param virtualServer
     * @return
     * @throws StingrayRestClientException
     */
    public boolean deleteVirtualServer(String vsName, VirtualServer virtualServer) throws StingrayRestClientException {
        return requestManager.deleteItem(endpoint, client, ClientConstants.SERVER_PATH + vsName);
    }


    /**
     * POOLS
     */

    /**
     * @param vsName the virtual server name for pool retrieval
     * @throws StingrayRestClientException
     */
    public Pool retrievePool(String vsName) throws StingrayRestClientException {
        ClientResponse response = requestManager.retrieveItem(endpoint, client, ClientConstants.POOL_PATH + vsName);
        Pool pool = interpretResponse(response, Pool.class);
        return pool;
    }

    /**
     * @param vsName The virtual server name related to the pool
     * @param pool   The pool object used to create a Stingray Pool
     * @return The configured pool object
     * @throws StingrayRestClientException
     */
    public Pool createPool(String vsName, Pool pool) throws StingrayRestClientException {
        ClientResponse response = requestManager.createItem(endpoint, client, ClientConstants.POOL_PATH + vsName, pool);
        return interpretResponse(response, Pool.class);
    }

    /**
     * @param vsName The virtual server name related to the pool
     * @param pool   The pool object used to create a Stingray Pool
     * @return The configured pool object
     * @throws StingrayRestClientException
     */
    public Pool updatePool(String vsName, Pool pool) throws StingrayRestClientException {
        ClientResponse response = requestManager.updateItem(endpoint, client, ClientConstants.POOL_PATH + vsName, pool);
        return interpretResponse(response, Pool.class);
    }

    /**
     * @param vsName The virtual server name related to the pool
     * @throws StingrayRestClientException
     */
    public void deletePool(String vsName) throws StingrayRestClientException {
        requestManager.deleteItem(endpoint, client, ClientConstants.POOL_PATH + vsName);
    }

    /**
     * @return the generic list for pools providing the name and the endpoint for a specific request
     * @throws StingrayRestClientException
     */
    public Children getActionScripts() throws StingrayRestClientException {
        return requestManager.retrieveList(endpoint, client, ClientConstants.ACTIONSCRIPT_PATH);
    }

    //Todo: rest of the methods, this is dependent on the managers being built up...
}
