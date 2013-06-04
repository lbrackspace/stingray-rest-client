package org.rackspace.stingray.client.manager;

import com.sun.jersey.api.client.Client;
import org.rackspace.stingray.client.action.ActionScript;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Children;

import java.net.URI;

public interface ActionScriptManager {

    public Children getActionScripts(URI endpoint, Client client) throws StingrayRestClientException;

    public ActionScript retrieveActionScript(URI endpoint, Client client, String vsName) throws StingrayRestClientException;

    public ActionScript createActionScript(URI endpoint, Client client, String vsName, ActionScript actionScript) throws StingrayRestClientException;

    public ActionScript updateActionScript(URI endpoint, Client client, String vsName, ActionScript actionScript) throws StingrayRestClientException;

    public boolean deleteActionScript(URI endpoint, Client client, String vsName) throws StingrayRestClientException;



}
