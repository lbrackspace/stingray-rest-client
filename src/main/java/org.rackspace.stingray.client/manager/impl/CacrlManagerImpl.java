package org.rackspace.stingray.client.manager.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.manager.BaseManager;
import org.rackspace.stingray.client.manager.CacrlManager;
import org.rackspace.stingray.client.ssl.cacrl.Cacrl;
import org.rackspace.stingray.client.util.ClientConstants;
import org.rackspace.stingray.client.util.StingrayList;

import javax.ws.rs.core.MediaType;
import java.net.URI;

public class CacrlManagerImpl extends BaseManager implements CacrlManager {
    private static final Log LOG = LogFactory.getLog(CacrlManagerImpl.class);

    /**
     *
     * @param endpoint
     * @param client
     * @return
     */
    @Override
    public StingrayList getCacrls(URI endpoint, Client client) {
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
    public Cacrl retrieveCacrl(URI endpoint, Client client, String vsName) throws StingrayRestClientException {
        Cacrl cacrl = null;
        ClientResponse response = null;
        try {
            response = client.resource(endpoint + ClientConstants.BANDWIDTH_PATH  + vsName)
                    .accept(MediaType.APPLICATION_JSON)
                    .get(ClientResponse.class);

            if (!isResponseValid(response)) {
                buildFaultMessage(response);
            }

            cacrl = response.getEntity(Cacrl.class);
        } catch (Exception e) {
            throw new StingrayRestClientException(ClientConstants.REQUEST_ERROR, e);
        }
        return cacrl;
    }

    /**
     *
     * @param endpoint
     * @param client
     * @param vsName
     * @param cacrl
     * @return
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     */
    @Override
    public Cacrl createCacrl(URI endpoint, Client client, String vsName, Cacrl cacrl) throws StingrayRestClientException {
        return updateCacrl(endpoint, client, vsName, cacrl);
    }

    /**
     *
     * @param endpoint
     * @param client
     * @param vsName
     * @param cacrl
     * @return
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     */
    @Override
    public Cacrl updateCacrl(URI endpoint, Client client, String vsName, Cacrl cacrl) throws StingrayRestClientException {
       ClientResponse response = null;
        try {
            response = client.resource(endpoint + ClientConstants.BANDWIDTH_PATH + vsName)
                    .accept(MediaType.APPLICATION_JSON)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(cacrl)
                    .put(ClientResponse.class);

            if (!isResponseValid(response)) {
                buildFaultMessage(response);
            }

            cacrl = response.getEntity(Cacrl.class);
        } catch (Exception e) {
            throw new StingrayRestClientException(ClientConstants.REQUEST_ERROR, e);
        }
        return cacrl;    }

    /**
     *
     * @param endpoint
     * @param client
     * @param vsName
     * @return
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     */
    @Override
    public boolean deleteCacrl(URI endpoint, Client client, String vsName) throws StingrayRestClientException {
        ClientResponse response = null;
        try {
            response = client.resource(endpoint + ClientConstants.BANDWIDTH_PATH + vsName)
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

