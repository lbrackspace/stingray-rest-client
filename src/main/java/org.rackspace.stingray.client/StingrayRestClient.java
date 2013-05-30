package org.rackspace.stingray.client;

import org.rackspace.stingray.client.manager.PoolManager;
import org.rackspace.stingray.client.manager.impl.PoolManagerImpl;
import org.rackspace.stingray.client.pool.Pool;

public class StingrayRestClient extends StingrayRestClientManager {

    private final PoolManager poolManager = new PoolManagerImpl();

    /*
     * @param Sting vsName the virtual server name to retrieve the pool for
     * @throws Exception, because we have yet to throw a checked one, update this at that time....
     */
    public Pool retrievePool(String vsName) throws Exception {
        //Todo: see @throws javadoc
        return poolManager.retrievePool(endpoint, client, vsName);
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
}
