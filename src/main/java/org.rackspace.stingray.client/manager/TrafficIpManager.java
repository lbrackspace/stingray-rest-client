package org.rackspace.stingray.client.manager;

import com.sun.jersey.api.client.Client;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.traffic.ip.TrafficIp;
import org.rackspace.stingray.client.util.StingrayList;

import java.net.URI;

public interface TrafficIpManager {

    public StingrayList getTrafficIps(URI endpoint, Client client) throws StingrayRestClientException;

    public TrafficIp retrieveTrafficIp(URI endpoint, Client client, String vsName) throws StingrayRestClientException;

    public TrafficIp createTrafficIp(URI endpoint, Client client, String vsName, TrafficIp trafficIp) throws StingrayRestClientException;

    public TrafficIp updateTrafficIp(URI endpoint, Client client, String vsName, TrafficIp trafficIp) throws StingrayRestClientException;

    public boolean deleteTrafficIp(URI endpoint, Client client, String vsName) throws StingrayRestClientException;



}
