package org.rackspace.stingray.client.manager.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import org.rackspace.stingray.client.manager.BaseManager;
import org.rackspace.stingray.client.manager.PoolManager;
import org.rackspace.stingray.client.pool.Pool;

import javax.ws.rs.core.MediaType;
import java.net.URI;

public class PoolManagerImpl extends BaseManager implements PoolManager {
    String poolPath = "pools/";

    @Override
    public Pool retrievePool(URI endpoint, Client client, String vsName) {
        Pool pool = null;
        ClientResponse response = null;
        try {
            response = client.resource(endpoint + poolPath + vsName)
                    .accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

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
}
