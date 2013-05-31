package org.rackspace.stingray.client.manager;

import com.sun.jersey.api.client.Client;
import org.rackspace.stingray.client.location.Location;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.util.StingrayList;

import java.net.URI;

public interface LocationManager {

    public StingrayList getLocations(URI endpoint, Client client) throws StingrayRestClientException;

    public Location retrieveLocation(URI endpoint, Client client, String vsName) throws StingrayRestClientException;

    public Location createLocation(URI endpoint, Client client, String vsName, Location location) throws StingrayRestClientException;

    public Location updateLocation(URI endpoint, Client client, String vsName, Location location) throws StingrayRestClientException;

    public boolean deleteLocation(URI endpoint, Client client, String vsName) throws StingrayRestClientException;



}
