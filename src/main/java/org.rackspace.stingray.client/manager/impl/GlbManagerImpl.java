package org.rackspace.stingray.client.manager.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.glb.GlobalLoadBalancing;
import org.rackspace.stingray.client.manager.GlbManager;
import org.rackspace.stingray.client.manager.BaseManager;
import org.rackspace.stingray.client.util.ClientConstants;
import org.rackspace.stingray.client.util.StingrayList;

import javax.ws.rs.core.MediaType;
import java.net.URI;

public class GlbManagerImpl extends BaseManager implements GlbManager {
    private static final Log LOG = LogFactory.getLog(GlbManagerImpl.class);

    /**
     *
     * @param endpoint
     * @param client
     * @return
     */
    @Override
    public StingrayList getGlobalLoadBalancings(URI endpoint, Client client) {
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
    public GlobalLoadBalancing retrieveGlobalLoadBalancing(URI endpoint, Client client, String vsName) throws StingrayRestClientException {
        GlobalLoadBalancing glb = null;
        ClientResponse response = null;
        try {
            response = client.resource(endpoint + ClientConstants.GLB_PATH  + vsName)
                    .accept(MediaType.APPLICATION_JSON)
                    .get(ClientResponse.class);

            if (!isResponseValid(response)) {
                buildFaultMessage(response);
            }

            glb = response.getEntity(GlobalLoadBalancing.class);
        } catch (Exception e) {
            throw new StingrayRestClientException(ClientConstants.REQUEST_ERROR, e);
        }
        return glb;
    }

    /**
     *
     * @param endpoint
     * @param client
     * @param vsName
     * @param glb
     * @return
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     */
    @Override
    public GlobalLoadBalancing createGlobalLoadBalancing(URI endpoint, Client client, String vsName, GlobalLoadBalancing glb) throws StingrayRestClientException {
        return updateGlobalLoadBalancing(endpoint, client, vsName, glb);
    }

    /**
     *
     * @param endpoint
     * @param client
     * @param vsName
     * @param glb
     * @return
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     */
    @Override
    public GlobalLoadBalancing updateGlobalLoadBalancing(URI endpoint, Client client, String vsName, GlobalLoadBalancing glb) throws StingrayRestClientException {
       ClientResponse response = null;
        try {
            response = client.resource(endpoint + ClientConstants.GLB_PATH + vsName)
                    .accept(MediaType.APPLICATION_JSON)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(glb)
                    .put(ClientResponse.class);

            if (!isResponseValid(response)) {
                buildFaultMessage(response);
            }

            glb = response.getEntity(GlobalLoadBalancing.class);
        } catch (Exception e) {
            throw new StingrayRestClientException(ClientConstants.REQUEST_ERROR, e);
        }
        return glb;    }

    /**
     *
     * @param endpoint
     * @param client
     * @param vsName
     * @return
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     */
    @Override
    public boolean deleteGlobalLoadBalancing(URI endpoint, Client client, String vsName) throws StingrayRestClientException {
        ClientResponse response = null;
        try {
            response = client.resource(endpoint + ClientConstants.GLB_PATH + vsName)
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

