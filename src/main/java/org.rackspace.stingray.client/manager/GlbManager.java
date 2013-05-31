package org.rackspace.stingray.client.manager;

import com.sun.jersey.api.client.Client;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.glb.GlobalLoadBalancing;
import org.rackspace.stingray.client.util.StingrayList;

import java.net.URI;

public interface GlbManager {

    public StingrayList getGlobalLoadBalancings(URI endpoint, Client client) throws StingrayRestClientException;

    public GlobalLoadBalancing retrieveGlobalLoadBalancing(URI endpoint, Client client, String vsName) throws StingrayRestClientException;

    public GlobalLoadBalancing createGlobalLoadBalancing(URI endpoint, Client client, String vsName, GlobalLoadBalancing globalLoadBalancing) throws StingrayRestClientException;

    public GlobalLoadBalancing updateGlobalLoadBalancing(URI endpoint, Client client, String vsName, GlobalLoadBalancing globalLoadBalancing) throws StingrayRestClientException;

    public boolean deleteGlobalLoadBalancing(URI endpoint, Client client, String vsName) throws StingrayRestClientException;


}
