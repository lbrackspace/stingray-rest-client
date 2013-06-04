package org.rackspace.stingray.client;

import com.sun.jersey.api.client.ClientResponse;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.manager.RequestManager;
import org.rackspace.stingray.client.manager.impl.RequestManagerImpl;
import org.rackspace.stingray.client.pool.Pool;
import org.rackspace.stingray.client.util.ClientConstants;

public class StingrayRestClient extends StingrayRestClientManager {

    private final RequestManager requestManager = new RequestManagerImpl();

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
     *
     * @param vsName The virtual server name related to the pool
     * @param pool The pool object used to create a Stingray Pool
     * @return The configured pool object
     * @throws StingrayRestClientException
     */
    public Pool createPool(String vsName, Pool pool) throws StingrayRestClientException {
        ClientResponse response = requestManager.createItem(endpoint, client, ClientConstants.POOL_PATH + vsName, pool);
        return interpretResponse(response, Pool.class);
    }

   /**
     *
     * @param vsName The virtual server name related to the pool
     * @param pool The pool object used to create a Stingray Pool
     * @return The configured pool object
     * @throws StingrayRestClientException
     */
    public Pool updatePool(String vsName, Pool pool) throws StingrayRestClientException {
        ClientResponse response = requestManager.updateItem(endpoint, client, ClientConstants.POOL_PATH + vsName, pool);
        return interpretResponse(response, Pool.class);
    }

    /**
     *
     * @param vsName The virtual server name related to the pool
     * @throws StingrayRestClientException
     */
    public void deletePool(String vsName) throws StingrayRestClientException {
        requestManager.deleteItem(endpoint, client, ClientConstants.POOL_PATH + vsName);
    }

    /**
     *
     * @throws StingrayRestClientException
     * @return the generic list for pools providing the name and the endpoint for a specific request
     */
    public Children getActionScripts() throws StingrayRestClientException {
        return requestManager.retrieveList(endpoint, client);
    }

    //Todo: rest of the methods, this is dependent on the managers being built up...

//    public ClientResponse updatePool(String path, Pool pool) throws Exception {
//        //Path will be in the client methods. This method should be generic possibly ...
//        ClientResponse response = null;
//        Client client = StingrayRestClientUtil.ClientHelper.createClient();
//
//        URI endpoint = URI.create(config.getString(ClientConfigKeys.stingray_rest_endpoint) + config.getString(ClientConfigKeys.stingray_base_uri));
//        try {
//            client.addFilter(new HTTPBasicAuthFilter(config.getString(ClientConfigKeys.stingray_admin_user), config.getString(ClientConfigKeys.stingray_admin_key)));
//            response = client.resource(endpoint + path).type(MediaType.APPLICATION_JSON)
//                    .accept(MediaType.APPLICATION_JSON).entity(pool).put(ClientResponse.class);
//        } catch (UniformInterfaceException ux) {
//            throw ux;
//        }
//
//        return response;
//    }
//

    //TODO: to do this call we need to generate object (based off of json schema we build) to pull in all lists as its 'generic' according to stingray
//    public ClientResponse getPools(String path) throws Exception {
//        ClientResponse response;
//        Client client = StingrayRestClientUtil.ClientHelper.createClient();
//
//        URI endpoint = URI.create(config.getString(ClientConfigKeys.stingray_rest_endpoint) + config.getString(ClientConfigKeys.stingray_base_uri));
//        try {
//            client.addFilter(new HTTPBasicAuthFilter(config.getString(ClientConfigKeys.stingray_admin_user), config.getString(ClientConfigKeys.stingray_admin_key)));
//            response = client.resource(endpoint + path).type(MediaType.APPLICATION_JSON)
//                    .accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
//        } catch (UniformInterfaceException ux) {
//            throw ux;
//        }
//
////        List<Pool> pools = StingrayRestClientUtil.ClientHelper.parsePools(response.getEntity(String.class));
//        return response;
//    }

    /**
     *
     * @param response
     * @param clazz
     * @param <T>
     * @return
     */
    private <T> T interpretResponse(ClientResponse response, java.lang.Class<T> clazz) {
        return response.getEntity(clazz);
    }
}
