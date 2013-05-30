package org.rackspace.stingray.client.manager.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import org.rackspace.stingray.client.manager.BaseManager;
import org.rackspace.stingray.client.manager.PoolManager;
import org.rackspace.stingray.client.pool.Pool;
import org.rackspace.stingray.client.util.ClientConstants;

import javax.ws.rs.core.MediaType;
import java.net.URI;

public class PoolManagerImpl extends BaseManager implements PoolManager {

    @Override
    public Pool retrievePool(URI endpoint, Client client, String vsName) {
        Pool pool = null;
        ClientResponse response = null;
        try {
            response = client.resource(endpoint + ClientConstants.POOL_PATH  + vsName)
                    .accept(MediaType.APPLICATION_JSON)
                    .get(ClientResponse.class);

            if (!isResponseValid(response)) {
                //Todo: handle bad response.. create and generate error objects for parsing from stingray. package them up to checked exceptions...
            }

            pool = response.getEntity(Pool.class);
        } catch (Exception e) {
            e.printStackTrace();
            //todo: ADD Checked Exception. EX: StingrayRestClientGeneralException... Handle exceptions properly..
        }
        return pool;
    }

    @Override
    public Pool createPool(URI endpoint, Client client, String vsName, Pool pool) {
        return updatePool(endpoint, client, vsName, pool);
    }

    @Override
    public Pool updatePool(URI endpoint, Client client, String vsName, Pool pool) {
        ClientResponse response = null;
        try {
            response = client.resource(endpoint + ClientConstants.POOL_PATH + vsName)
                    .accept(MediaType.APPLICATION_JSON)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(pool)
                    .put(ClientResponse.class);

            if (!isResponseValid(response)) {
                //Todo: handle bad response.. create and generate error objects for parsing from stingray. package them up to checked exceptions...
            }

            pool = response.getEntity(Pool.class);
//            System.out.printf("Response: %s", response.getEntity(String.class));
        } catch (Exception e) {
            e.printStackTrace();
            //todo: ADD Checked Exception. EX: StingrayRestClientGeneralException... Handle exceptions properly..
        }
        return pool;
    }

    @Override
    public void deletePool(URI endpoint, Client client, String vsName) {
        ClientResponse response = null;
        try {
            response = client.resource(endpoint + ClientConstants.POOL_PATH  + vsName)
                    .accept(MediaType.APPLICATION_JSON)
                    .type(MediaType.APPLICATION_JSON)
                    .delete(ClientResponse.class);

            if (!isResponseValid(response)) {
                //Todo: handle bad response.. create and generate error objects for parsing from stingray. package them up to checked exceptions...
            }

        } catch (Exception e) {
            e.printStackTrace();
            //todo: ADD Checked Exception. EX: StingrayRestClientGeneralException... Handle exceptions properly..
        }
    }

    public ClientResponse test(URI endpoint, Client client) {
        Pool pool = null;
        ClientResponse response = null;
        try {
            response = client.resource(endpoint + ClientConstants.POOL_PATH )
                    .accept(MediaType.APPLICATION_JSON)
                    .get(ClientResponse.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
