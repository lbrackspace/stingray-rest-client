package org.rackspace.stingray.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.client.apache.ApacheHttpClient;
import org.rackspace.stingray.client.manager.util.StingrayRestClientUtil;
import org.rackspace.stingray.client.pool.PoolUpdate;

import javax.ws.rs.core.MediaType;
import java.net.URI;

public class StingrayRestClient {
    private ApacheHttpClient client;


    public ClientResponse getResource(URI stingrayEndpoint, String path) throws Exception {
        ClientResponse response = null;
        String tresponse;
        Client client = StingrayRestClientUtil.ClientHelper.createClient();

        try {
            client.addFilter(new HTTPBasicAuthFilter("admin", "possword"));
            response = client.resource(stingrayEndpoint + path)
                    .accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        } catch (UniformInterfaceException ux) {
            throw ux;
        }

        return response;
    }

    public ClientResponse updatePool(URI stingrayEndpoint, String path, PoolUpdate pool) throws Exception {
        ClientResponse response = null;
        String tresponse;
        Client client = StingrayRestClientUtil.ClientHelper.createClient();

        try {
            client.addFilter(new HTTPBasicAuthFilter("admin", "poosword"));
            response = client.resource(stingrayEndpoint + path).type(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON).entity(pool).put(ClientResponse.class);
        } catch (UniformInterfaceException ux) {
            throw ux;
        }

        return response;
    }
}
