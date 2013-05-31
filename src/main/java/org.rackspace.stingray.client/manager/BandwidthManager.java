package org.rackspace.stingray.client.manager;

import com.sun.jersey.api.client.Client;
import org.rackspace.stingray.client.bandwidth.Bandwidth;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.pool.Pool;
import org.rackspace.stingray.client.util.StingrayList;

import java.net.URI;

public interface BandwidthManager {

    public StingrayList getBandwidths(URI endpoint, Client client) throws StingrayRestClientException;

    public Bandwidth retrieveBandwidth(URI endpoint, Client client, String vsName) throws StingrayRestClientException;

    public Bandwidth createBandwidth(URI endpoint, Client client, String vsName, Bandwidth bandwidth) throws StingrayRestClientException;

    public Bandwidth updateBandwidth(URI endpoint, Client client, String vsName, Bandwidth bandwidth) throws StingrayRestClientException;

    public boolean deleteBandwidth(URI endpoint, Client client, String vsName) throws StingrayRestClientException;



}
