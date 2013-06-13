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
     * Implementation of the method to retrieve a list from the REST api
     *
     * @param endpoint  The REST URL endpoint for retrieving the list items
     * @param client    The client to handle the specific request
     * @return          Returns the entity inside the response of the REST api
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
     * Implementation of the method to retrieve a specific item from the REST api
     *
     * @param endpoint  The REST URL endpoint for retrieving the item
     * @param client    The client to handle the specific request
     * @param path      The path to the specific object, including its name descriptor
     * @return          Returns the entity inside the response of the REST api
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
     * Implementation of the object creation method through the REST api.
     * In the case of the REST api, creation is the same method as updating,
     *   so this method will simply call the update method.
     *
     * @param endpoint  The REST URL endpoint for creating an item
     * @param client    The client to handle the specific request
     * @param path      The path to the specific object, including its name descriptor
     * @param object    The generic object to be created on Stingray's side
     * @return          Returns the entity inside the response of the REST api
     * @throws StingrayRestClientException
     */
    @Override
    public ClientResponse createItem(URI endpoint, Client client, String path, Object object, MediaType cType) throws StingrayRestClientException {
        return updateItem(endpoint, client, path, object, cType);
    }

    /**
     * Implementation of the method for updating an item through the REST api
     *
     * @param endpoint  The REST URL endpoint for updating an item
     * @param client    The client to handle the specific request
     * @param path      The path to the specific object, including its name descriptor
     * @param object    The generic object to be updated on Stingray's side
     * @return          Returns the entity inside the response of the REST api
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
     * Implementation of the method for deleting an item through the REST api
     *
     * @param endpoint  The REST URL endpoint for deleting an item
     * @param client    The client to handle the specific request
     * @param path      The path to the specific object, including its name descriptor
     * @return          Returns a boolean value evaluating the result of the operation
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

