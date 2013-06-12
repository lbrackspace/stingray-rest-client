package org.rackspace.stingray.client.manager.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.manager.RequestManager;
import org.rackspace.stingray.client.util.ClientConstants;

import javax.ws.rs.core.MediaType;
import java.net.URI;

import static org.rackspace.stingray.client.manager.util.RequestManagerUtil.buildFaultMessage;
import static org.rackspace.stingray.client.manager.util.RequestManagerUtil.isResponseValid;

public class RequestManagerImpl implements RequestManager {
    private static final Log LOG = LogFactory.getLog(RequestManagerImpl.class);

    /**
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
        } catch (Exception e) {
            throw new StingrayRestClientException(ClientConstants.REQUEST_ERROR, e);
        }
        return scripts;
    }

    /**
     * @param endpoint
     * @param client
     * @param path
     * @return
     * @throws StingrayRestClientException
     */
    @Override
    public ClientResponse getItem(URI endpoint, Client client, String path, MediaType cType) throws StingrayRestClientException {
        ClientResponse response = null;
        try {
            response = client.resource(endpoint + path)
                    .accept(cType)
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
     * @param endpoint
     * @param client
     * @param path
     * @param object
     * @return
     * @throws StingrayRestClientException
     */
    @Override
    public ClientResponse createItem(URI endpoint, Client client, String path, Object object, MediaType cType) throws StingrayRestClientException {
        return updateItem(endpoint, client, path, object, cType);
    }

    /**
     * @param endpoint
     * @param client
     * @param path
     * @param object
     * @return
     * @throws StingrayRestClientException
     */
    @Override
    public ClientResponse updateItem(URI endpoint, Client client, String path, Object object, MediaType cType) throws StingrayRestClientException {
        ClientResponse response = null;
        try {
            response = client.resource(endpoint + path)
                    .accept(cType)
                    .type(cType)
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

    /**
     * Retrieves and interprets the response entity.
     *
     * @param response
     * @param clazz
     * @param <T>
     * @return
     */
    public synchronized <T> T interpretResponse(ClientResponse response, java.lang.Class<T> clazz) throws StingrayRestClientException {
        T t;
        try {
            t = response.getEntity(clazz);
        } catch (Exception ex) {
            LOG.error("Could not retrieve object of type: " + clazz + " Exception: " + ex);
            if (!isResponseValid(response)) {
                throw new StingrayRestClientException(ClientConstants.REQUEST_ERROR, ex);
            }
            //The script calls dont return on POST/PUT...
            return null;
        }
        return t;
    }
}

