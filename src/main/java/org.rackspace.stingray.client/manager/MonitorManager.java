package org.rackspace.stingray.client.manager;

import com.sun.jersey.api.client.Client;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.monitor.Monitor;
import org.rackspace.stingray.client.util.StingrayList;

import java.net.URI;

public interface MonitorManager {

    public StingrayList getMonitors(URI endpoint, Client client) throws StingrayRestClientException;

    public Monitor retrieveMonitor(URI endpoint, Client client, String vsName) throws StingrayRestClientException;

    public Monitor createMonitor(URI endpoint, Client client, String vsName, Monitor monitor) throws StingrayRestClientException;

    public Monitor updateMonitor(URI endpoint, Client client, String vsName, Monitor monitor) throws StingrayRestClientException;

    public boolean deleteMonitor(URI endpoint, Client client, String vsName) throws StingrayRestClientException;



}
