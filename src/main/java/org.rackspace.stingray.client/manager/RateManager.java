package org.rackspace.stingray.client.manager;

import com.sun.jersey.api.client.Client;
import org.rackspace.stingray.client.rate.Rate;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.util.StingrayList;

import java.net.URI;

public interface RateManager {

    public StingrayList getRates(URI endpoint, Client client) throws StingrayRestClientException;

    public Rate retrieveRate(URI endpoint, Client client, String vsName) throws StingrayRestClientException;

    public Rate createRate(URI endpoint, Client client, String vsName, Rate rate) throws StingrayRestClientException;

    public Rate updateRate(URI endpoint, Client client, String vsName, Rate rate) throws StingrayRestClientException;

    public boolean deleteRate(URI endpoint, Client client, String vsName) throws StingrayRestClientException;



}
