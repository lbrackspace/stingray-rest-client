package org.rackspace.stingray.client.manager.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.manager.BaseManager;
import org.rackspace.stingray.client.manager.PoolManager;
import org.rackspace.stingray.client.pool.Pool;
import org.rackspace.stingray.client.util.ClientConstants;
import org.rackspace.stingray.client.util.StingrayList;

import javax.ws.rs.core.MediaType;
import java.net.URI;

public class PoolManagerImpl extends BaseManager implements PoolManager {
    private static final Log LOG = LogFactory.getLog(PoolManagerImpl.class);


    @Override public StingrayList getPools(URI endpoint, Client client) throws StingrayRestClientException{
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Pool retrievePool(URI endpoint, Client client, String vsName) throws StingrayRestClientException {
        Pool pool = null;
        ClientResponse response = null;
        try {
            response = client.resource(endpoint + ClientConstants.POOL_PATH  + vsName)
                    .accept(MediaType.APPLICATION_JSON)
                    .get(ClientResponse.class);

            if (!isResponseValid(response)) {
                buildFaultMessage(response);
            }

            pool = response.getEntity(Pool.class);
        } catch (Exception e) {
            throw new StingrayRestClientException(ClientConstants.REQUEST_ERROR, e);
        }
        return pool;
    }

    @Override
    public Pool createPool(URI endpoint, Client client, String vsName, Pool pool) throws StingrayRestClientException {
        return updatePool(endpoint, client, vsName, pool);
    }

    @Override
    public Pool updatePool(URI endpoint, Client client, String vsName, Pool pool) throws StingrayRestClientException {
        ClientResponse response = null;
        try {
            response = client.resource(endpoint + ClientConstants.POOL_PATH + vsName)
                    .accept(MediaType.APPLICATION_JSON)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(pool)
                    .put(ClientResponse.class);

            if (!isResponseValid(response)) {
                buildFaultMessage(response);
            }

            pool = response.getEntity(Pool.class);
        } catch (Exception e) {
            throw new StingrayRestClientException(ClientConstants.REQUEST_ERROR, e);
        }
        return pool;
    }

    @Override
    public boolean deletePool(URI endpoint, Client client, String vsName) throws StingrayRestClientException {
        ClientResponse response = null;
        try {
            response = client.resource(endpoint + ClientConstants.POOL_PATH + vsName)
                    .accept(MediaType.APPLICATION_JSON)
                    .type(MediaType.APPLICATION_JSON)
                    .delete(ClientResponse.class);

            if (!isResponseValid(response)) {
                buildFaultMessage(response);
            }

        } catch (Exception e) {
            throw new StingrayRestClientException(ClientConstants.REQUEST_ERROR, e);
        }
        return true;
    }

}
