package org.rackspace.stingray.client.manager.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.manager.KeypairManager;
import org.rackspace.stingray.client.manager.BaseManager;
import org.rackspace.stingray.client.ssl.keypair.Keypair;
import org.rackspace.stingray.client.util.ClientConstants;
import org.rackspace.stingray.client.util.StingrayList;

import javax.ws.rs.core.MediaType;
import java.net.URI;

public class KeypairManagerImpl extends BaseManager implements KeypairManager {
    private static final Log LOG = LogFactory.getLog(KeypairManagerImpl.class);

    /**
     *
     * @param endpoint
     * @param client
     * @return
     */
    @Override
    public StingrayList getKeypairs(URI endpoint, Client client) {
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
    public Keypair retrieveKeypair(URI endpoint, Client client, String vsName) throws StingrayRestClientException {
        Keypair keypair = null;
        ClientResponse response = null;
        try {
            response = client.resource(endpoint + ClientConstants.KEYPAIR_PATH  + vsName)
                    .accept(MediaType.APPLICATION_JSON)
                    .get(ClientResponse.class);

            if (!isResponseValid(response)) {
                buildFaultMessage(response);
            }

            keypair = response.getEntity(Keypair.class);
        } catch (Exception e) {
            throw new StingrayRestClientException(ClientConstants.REQUEST_ERROR, e);
        }
        return keypair;
    }

    /**
     *
     * @param endpoint
     * @param client
     * @param vsName
     * @param keypair
     * @return
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     */
    @Override
    public Keypair createKeypair(URI endpoint, Client client, String vsName, Keypair keypair) throws StingrayRestClientException {
        return updateKeypair(endpoint, client, vsName, keypair);
    }

    /**
     *
     * @param endpoint
     * @param client
     * @param vsName
     * @param keypair
     * @return
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     */
    @Override
    public Keypair updateKeypair(URI endpoint, Client client, String vsName, Keypair keypair) throws StingrayRestClientException {
       ClientResponse response = null;
        try {
            response = client.resource(endpoint + ClientConstants.KEYPAIR_PATH + vsName)
                    .accept(MediaType.APPLICATION_JSON)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(keypair)
                    .put(ClientResponse.class);

            if (!isResponseValid(response)) {
                buildFaultMessage(response);
            }

            keypair = response.getEntity(Keypair.class);
        } catch (Exception e) {
            throw new StingrayRestClientException(ClientConstants.REQUEST_ERROR, e);
        }
        return keypair;    }

    /**
     *
     * @param endpoint
     * @param client
     * @param vsName
     * @return
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     */
    @Override
    public boolean deleteKeypair(URI endpoint, Client client, String vsName) throws StingrayRestClientException {
        ClientResponse response = null;
        try {
            response = client.resource(endpoint + ClientConstants.KEYPAIR_PATH + vsName)
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

