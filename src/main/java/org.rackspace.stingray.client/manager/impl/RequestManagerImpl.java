package org.rackspace.stingray.client.manager.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.manager.RequestManager;
import org.rackspace.stingray.client.util.ClientConstants;

import javax.ws.rs.core.MediaType;
import java.net.URI;

import static org.rackspace.stingray.client.manager.util.RequestManagerUtil.*;

public class RequestManagerImpl implements RequestManager {
    private static final Log LOG = LogFactory.getLog(RequestManagerImpl.class);

    /**
     *
     * @param endpoint
     * @param client
     * @return
     */
    @Override
    public Children getList(URI endpoint, Client client, String path) throws StingrayRestClientException {
        Children scripts = null;
        ClientResponse response = null;
        try {
            response = client.resource(URI.create(endpoint + path))
                    .accept(MediaType.APPLICATION_JSON)
                    .get(ClientResponse.class);
            if (!isResponseValid(response)) {
                buildFaultMessage(response);
            }

            scripts = response.getEntity(Children.class);
        } catch(Exception e) {
            throw new StingrayRestClientException(ClientConstants.REQUEST_ERROR, e);
        }
        return scripts;
    }

    /**
     *
     * @param endpoint
     * @param client
     * @param path
     * @return
     * @throws StingrayRestClientException
     */
    @Override
    public ClientResponse getItem(URI endpoint, Client client, String path) throws StingrayRestClientException {
        ClientResponse response = null;
        try {
              response = client.resource(endpoint + path)
                      .accept(MediaType.APPLICATION_JSON)
                      .get(ClientResponse.class);

            if (!isResponseValid(response)) {
                buildFaultMessage(response);
            }
        } catch (Exception e) {
            throw new StingrayRestClientException(ClientConstants.REQUEST_ERROR, e);
        }
        return response;
    }

    /**
     *
     * @param endpoint
     * @param client
     * @param path
     * @param object
     * @return
     * @throws StingrayRestClientException
     */
    @Override
    public ClientResponse createItem(URI endpoint, Client client, String path, Object object) throws StingrayRestClientException {
        return updateItem(endpoint, client, path, object);
    }

    /**
     *
     * @param endpoint
     * @param client
     * @param path
     * @param object
     * @return
     * @throws StingrayRestClientException
     */
    @Override
    public ClientResponse updateItem(URI endpoint, Client client, String path, Object object) throws StingrayRestClientException {
       ClientResponse response = null;
        try {
            response = client.resource(endpoint + path)
                    .accept(MediaType.APPLICATION_JSON)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(object)
                    .put(ClientResponse.class);

            if (!isResponseValid(response)) {
                buildFaultMessage(response);
            }
        } catch (Exception e) {
            throw new StingrayRestClientException(ClientConstants.REQUEST_ERROR, e);
        }
        return response;
    }

    /**
     *
     * @param endpoint
     * @param client
     * @param path
     * @return
     * @throws StingrayRestClientException
     */
    @Override
    public boolean deleteItem(URI endpoint, Client client, String path) throws StingrayRestClientException {
        com.sun.jersey.api.client.ClientResponse response = null;
        try {
            response = client.resource(endpoint + path)
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

