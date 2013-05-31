package org.rackspace.stingray.client.manager.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.manager.MonitorScriptManager;
import org.rackspace.stingray.client.manager.BaseManager;
import org.rackspace.stingray.client.monitor.MonitorScript;
import org.rackspace.stingray.client.util.ClientConstants;
import org.rackspace.stingray.client.util.StingrayList;

import javax.ws.rs.core.MediaType;
import java.net.URI;

public class MonitorScriptManagerImpl extends BaseManager implements MonitorScriptManager {
    private static final Log LOG = LogFactory.getLog(MonitorScriptManagerImpl.class);

    /**
     *
     * @param endpoint
     * @param client
     * @return
     */
    @Override
    public StingrayList getMonitorScripts(URI endpoint, Client client) {
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
    public MonitorScript retrieveMonitorScript(URI endpoint, Client client, String vsName) throws StingrayRestClientException {
        MonitorScript monitorScript = null;
        ClientResponse response = null;
        try {
            response = client.resource(endpoint + ClientConstants.MONITORSCRIPT_PATH  + vsName)
                    .accept(MediaType.APPLICATION_JSON)
                    .get(ClientResponse.class);

            if (!isResponseValid(response)) {
                buildFaultMessage(response);
            }

            monitorScript = response.getEntity(MonitorScript.class);
        } catch (Exception e) {
            throw new StingrayRestClientException(ClientConstants.REQUEST_ERROR, e);
        }
        return monitorScript;
    }

    /**
     *
     * @param endpoint
     * @param client
     * @param vsName
     * @param monitorScript
     * @return
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     */
    @Override
    public MonitorScript createMonitorScript(URI endpoint, Client client, String vsName, MonitorScript monitorScript) throws StingrayRestClientException {
        return updateMonitorScript(endpoint, client, vsName, monitorScript);
    }

    /**
     *
     * @param endpoint
     * @param client
     * @param vsName
     * @param monitorScript
     * @return
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     */
    @Override
    public MonitorScript updateMonitorScript(URI endpoint, Client client, String vsName, MonitorScript monitorScript) throws StingrayRestClientException {
       ClientResponse response = null;
        try {
            response = client.resource(endpoint + ClientConstants.MONITORSCRIPT_PATH + vsName)
                    .accept(MediaType.APPLICATION_JSON)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(monitorScript)
                    .put(ClientResponse.class);

            if (!isResponseValid(response)) {
                buildFaultMessage(response);
            }

            monitorScript = response.getEntity(MonitorScript.class);
        } catch (Exception e) {
            throw new StingrayRestClientException(ClientConstants.REQUEST_ERROR, e);
        }
        return monitorScript;    }

    /**
     *
     * @param endpoint
     * @param client
     * @param vsName
     * @return
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     */
    @Override
    public boolean deleteMonitorScript(URI endpoint, Client client, String vsName) throws StingrayRestClientException {
        ClientResponse response = null;
        try {
            response = client.resource(endpoint + ClientConstants.MONITORSCRIPT_PATH + vsName)
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

