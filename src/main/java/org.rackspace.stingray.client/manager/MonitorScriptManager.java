package org.rackspace.stingray.client.manager;

import com.sun.jersey.api.client.Client;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.monitor.MonitorScript;
import org.rackspace.stingray.client.util.StingrayList;

import java.net.URI;

public interface MonitorScriptManager {

    public StingrayList getMonitorScripts(URI endpoint, Client client) throws StingrayRestClientException;

    public MonitorScript retrieveMonitorScript(URI endpoint, Client client, String vsName) throws StingrayRestClientException;

    public MonitorScript createMonitorScript(URI endpoint, Client client, String vsName, MonitorScript monitorScript) throws StingrayRestClientException;

    public MonitorScript updateMonitorScript(URI endpoint, Client client, String vsName, MonitorScript monitorScript) throws StingrayRestClientException;

    public boolean deleteMonitorScript(URI endpoint, Client client, String vsName) throws StingrayRestClientException;



}
