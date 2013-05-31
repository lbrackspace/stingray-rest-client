package org.rackspace.stingray.client.manager.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.manager.ClientKeypairManager;
import org.rackspace.stingray.client.manager.BaseManager;
import org.rackspace.stingray.client.ssl.client.keypair.ClientKeypair;
import org.rackspace.stingray.client.util.ClientConstants;
import org.rackspace.stingray.client.util.StingrayList;

import javax.ws.rs.core.MediaType;
import java.net.URI;

public class ClientKeypairManagerImpl extends BaseManager implements ClientKeypairManager {
    private static final Log LOG = LogFactory.getLog(ClientKeypairManagerImpl.class);

    /**
     *
     * @param endpoint
     * @param client
     * @return
     */
    @Override
    public StingrayList getClientKeypairs(URI endpoint, Client client) {
         return null;
    }

    /**
     *
     * @param endpoint
     * @param client
     * @param vsName
     * @return
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     */
    @Override
    public ClientKeypair retrieveClientKeypair(URI endpoint, Client client, String vsName) throws StingrayRestClientException {
        ClientKeypair clientKeypair = null;
        ClientResponse response = null;
        try {
            response = client.resource(endpoint + ClientConstants.CLIENTKEYPAIR_PATH  + vsName)
                    .accept(MediaType.APPLICATION_JSON)
                    .get(ClientResponse.class);

            if (!isResponseValid(response)) {
                buildFaultMessage(response);
            }

            clientKeypair = response.getEntity(ClientKeypair.class);
        } catch (Exception e) {
            throw new StingrayRestClientException(ClientConstants.REQUEST_ERROR, e);
        }
        return clientKeypair;
    }

    /**
     *
     * @param endpoint
     * @param client
     * @param vsName
     * @param clientKeypair
     * @return
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     */
    @Override
    public ClientKeypair createClientKeypair(URI endpoint, Client client, String vsName, ClientKeypair clientKeypair) throws StingrayRestClientException {
        return updateClientKeypair(endpoint, client, vsName, clientKeypair);
    }

    /**
     *
     * @param endpoint
     * @param client
     * @param vsName
     * @param clientKeypair
     * @return
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     */
    @Override
    public ClientKeypair updateClientKeypair(URI endpoint, Client client, String vsName, ClientKeypair clientKeypair) throws StingrayRestClientException {
       ClientResponse response = null;
        try {
            response = client.resource(endpoint + ClientConstants.CLIENTKEYPAIR_PATH + vsName)
                    .accept(MediaType.APPLICATION_JSON)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(clientKeypair)
                    .put(ClientResponse.class);

            if (!isResponseValid(response)) {
                buildFaultMessage(response);
            }

            clientKeypair = response.getEntity(ClientKeypair.class);
        } catch (Exception e) {
            throw new StingrayRestClientException(ClientConstants.REQUEST_ERROR, e);
        }
        return clientKeypair;    }

    /**
     *
     * @param endpoint
     * @param client
     * @param vsName
     * @return
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     */
    @Override
    public boolean deleteClientKeypair(URI endpoint, Client client, String vsName) throws StingrayRestClientException {
        ClientResponse response = null;
        try {
            response = client.resource(endpoint + ClientConstants.CLIENTKEYPAIR_PATH + vsName)
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

