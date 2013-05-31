package org.rackspace.stingray.client.manager;

import com.sun.jersey.api.client.Client;
import org.rackspace.stingray.client.tm.TrafficManager;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.util.StingrayList;

import java.net.URI;

public interface TrafficManagerManager {

    public StingrayList getTrafficManagers(URI endpoint, Client client) throws StingrayRestClientException;

    public TrafficManager retrieveTrafficManager(URI endpoint, Client client, String vsName) throws StingrayRestClientException;

    public TrafficManager createTrafficManager(URI endpoint, Client client, String vsName, TrafficManager trafficManager) throws StingrayRestClientException;

    public TrafficManager updateTrafficManager(URI endpoint, Client client, String vsName, TrafficManager trafficManager) throws StingrayRestClientException;

    public boolean deleteTrafficManager(URI endpoint, Client client, String vsName) throws StingrayRestClientException;



}
