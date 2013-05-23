package org.rackspace.stingray.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import org.rackspace.stingray.client.manager.util.StingrayRestClientUtil;
import ru.hh.jersey.hchttpclient.ApacheHttpClient;

import javax.ws.rs.core.MediaType;
import java.net.URI;

public class StingrayRestClient {
    private ApacheHttpClient client;


    public String testConnection(URI stingrayEndpoint, String path) throws Exception {
        ClientResponse response = null;
        String tresponse;
        Client client = StingrayRestClientUtil.createHttpClient();

        try {
            client.addFilter(new HTTPBasicAuthFilter("admin", "password"));
            tresponse = client.resource(stingrayEndpoint + path)
                    .accept(MediaType.APPLICATION_JSON).get(String.class);
        } catch (UniformInterfaceException ux) {
            throw ux;
        }

//        return response.getEntity(ClientResponse.class);
        return tresponse;
    }
}
