package org.rackspace.stingray.client.manager;

import com.sun.jersey.api.client.Client;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.util.StingrayList;
import org.rackspace.stingray.client.config.virtualserver.VirtualServer;

import java.net.URI;

public interface VirtualServerManager {

    public StingrayList getVirtualServers(URI endpoint, Client client) throws StingrayRestClientException;

    public VirtualServer retrieveVirtualServer(URI endpoint, Client client, String vsName) throws StingrayRestClientException;

    public VirtualServer createVirtualServer(URI endpoint, Client client, String vsName, VirtualServer virtualServer) throws StingrayRestClientException;

    public VirtualServer updateVirtualServer(URI endpoint, Client client, String vsName, VirtualServer virtualServer) throws StingrayRestClientException;

    public boolean deleteVirtualServer(URI endpoint, Client client, String vsName) throws StingrayRestClientException;



}
