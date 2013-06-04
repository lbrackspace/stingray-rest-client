package org.rackspace.stingray.client.manager.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rackspace.stingray.client.action.ActionScript;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.manager.ActionScriptManager;
import org.rackspace.stingray.client.manager.BaseManager;
import org.rackspace.stingray.client.util.ClientConstants;

import javax.ws.rs.core.MediaType;
import java.net.URI;

public class ActionScriptManagerImpl extends BaseManager implements ActionScriptManager {
    private static final Log LOG = LogFactory.getLog(ActionScriptManagerImpl.class);

    /**
     *
     * @param endpoint
     * @param client
     * @return
     */
    @Override
    public Children getActionScripts(URI endpoint, Client client) throws StingrayRestClientException {
        Children scripts = null;
        ClientResponse response = null;
        try {
            response = client.resource(URI.create(endpoint + ClientConstants.ACTIONSCRIPT_PATH)).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
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
     * @param vsName
     * @return
     * @throws StingrayRestClientException
     */
    @Override
    public ActionScript retrieveActionScript(URI endpoint, Client client, String vsName) throws StingrayRestClientException {
        ActionScript actionScript = null;
        ClientResponse response = null;
        try {
            response = client.resource(endpoint + ClientConstants.ACTIONSCRIPT_PATH  + vsName)
                    .accept(MediaType.APPLICATION_JSON)
                    .get(ClientResponse.class);

            if (!isResponseValid(response)) {
                buildFaultMessage(response);
            }

            actionScript = response.getEntity(ActionScript.class);
        } catch (Exception e) {
            throw new StingrayRestClientException(ClientConstants.REQUEST_ERROR, e);
        }
        return actionScript;
    }

    /**
     *
     * @param endpoint
     * @param client
     * @param vsName
     * @param actionScript
     * @return
     * @throws StingrayRestClientException
     */
    @Override
    public ActionScript createActionScript(URI endpoint, Client client, String vsName, ActionScript actionScript) throws StingrayRestClientException {
        return updateActionScript(endpoint, client, vsName, actionScript);
    }

    /**
     *
     * @param endpoint
     * @param client
     * @param vsName
     * @param actionScript
     * @return
     * @throws StingrayRestClientException
     */
    @Override
    public ActionScript updateActionScript(URI endpoint, Client client, String vsName, ActionScript actionScript) throws StingrayRestClientException {
       ClientResponse response = null;
        try {
            response = client.resource(endpoint + ClientConstants.ACTIONSCRIPT_PATH + vsName)
                    .accept(MediaType.APPLICATION_JSON)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(actionScript)
                    .put(ClientResponse.class);

            if (!isResponseValid(response)) {
                buildFaultMessage(response);
            }

            actionScript = response.getEntity(ActionScript.class);
        } catch (Exception e) {
            throw new StingrayRestClientException(ClientConstants.REQUEST_ERROR, e);
        }
        return actionScript;    }

    /**
     *
     * @param endpoint
     * @param client
     * @param vsName
     * @return
     * @throws StingrayRestClientException
     */
    @Override
    public boolean deleteActionScript(URI endpoint, Client client, String vsName) throws StingrayRestClientException {
        ClientResponse response = null;
        try {
            response = client.resource(endpoint + ClientConstants.ACTIONSCRIPT_PATH + vsName)
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

