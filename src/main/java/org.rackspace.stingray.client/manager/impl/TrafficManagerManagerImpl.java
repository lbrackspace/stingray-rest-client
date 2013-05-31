package org.rackspace.stingray.client.manager.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rackspace.stingray.client.tm.TrafficManager;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.manager.TrafficManagerManager;
import org.rackspace.stingray.client.manager.BaseManager;
import org.rackspace.stingray.client.util.ClientConstants;
import org.rackspace.stingray.client.util.StingrayList;

import javax.ws.rs.core.MediaType;
import java.net.URI;

public class TrafficManagerManagerImpl extends BaseManager implements TrafficManagerManager {
    private static final Log LOG = LogFactory.getLog(TrafficManagerManagerImpl.class);

    /**
     *
     * @param endpoint
     * @param client
     * @return
     */
    @Override
    public StingrayList getTrafficManagers(URI endpoint, Client client) {
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
    public TrafficManager retrieveTrafficManager(URI endpoint, Client client, String vsName) throws StingrayRestClientException {
        TrafficManager trafficManager = null;
        ClientResponse response = null;
        try {
            response = client.resource(endpoint + ClientConstants.TRAFFICMANAGER_PATH  + vsName)
                    .accept(MediaType.APPLICATION_JSON)
                    .get(ClientResponse.class);

            if (!isResponseValid(response)) {
                buildFaultMessage(response);
            }

            trafficManager = response.getEntity(TrafficManager.class);
        } catch (Exception e) {
            throw new StingrayRestClientException(ClientConstants.REQUEST_ERROR, e);
        }
        return trafficManager;
    }

    /**
     *
     * @param endpoint
     * @param client
     * @param vsName
     * @param trafficManager
     * @return
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     */
    @Override
    public TrafficManager createTrafficManager(URI endpoint, Client client, String vsName, TrafficManager trafficManager) throws StingrayRestClientException {
        return updateTrafficManager(endpoint, client, vsName, trafficManager);
    }

    /**
     *
     * @param endpoint
     * @param client
     * @param vsName
     * @param trafficManager
     * @return
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     */
    @Override
    public TrafficManager updateTrafficManager(URI endpoint, Client client, String vsName, TrafficManager trafficManager) throws StingrayRestClientException {
       ClientResponse response = null;
        try {
            response = client.resource(endpoint + ClientConstants.TRAFFICMANAGER_PATH + vsName)
                    .accept(MediaType.APPLICATION_JSON)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(trafficManager)
                    .put(ClientResponse.class);

            if (!isResponseValid(response)) {
                buildFaultMessage(response);
            }

            trafficManager = response.getEntity(TrafficManager.class);
        } catch (Exception e) {
            throw new StingrayRestClientException(ClientConstants.REQUEST_ERROR, e);
        }
        return trafficManager;    }

    /**
     *
     * @param endpoint
     * @param client
     * @param vsName
     * @return
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     */
    @Override
    public boolean deleteTrafficManager(URI endpoint, Client client, String vsName) throws StingrayRestClientException {
        ClientResponse response = null;
        try {
            response = client.resource(endpoint + ClientConstants.TRAFFICMANAGER_PATH + vsName)
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

