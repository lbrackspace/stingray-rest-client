package org.rackspace.stingray.client.manager;

import com.sun.jersey.api.client.Client;
import org.rackspace.stingray.client.trafficscript.Trafficscript;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.util.StingrayList;

import java.net.URI;

public interface TrafficscriptManager {

    public StingrayList getTrafficscripts(URI endpoint, Client client) throws StingrayRestClientException;

    public Trafficscript retrieveTrafficscript(URI endpoint, Client client, String vsName) throws StingrayRestClientException;

    public Trafficscript createTrafficscript(URI endpoint, Client client, String vsName, Trafficscript trafficscript) throws StingrayRestClientException;

    public Trafficscript updateTrafficscript(URI endpoint, Client client, String vsName, Trafficscript trafficscript) throws StingrayRestClientException;

    public boolean deleteTrafficscript(URI endpoint, Client client, String vsName) throws StingrayRestClientException;



}
