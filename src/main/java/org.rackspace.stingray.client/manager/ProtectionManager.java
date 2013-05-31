package org.rackspace.stingray.client.manager;

import com.sun.jersey.api.client.Client;
import org.rackspace.stingray.client.protection.Protection;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.util.StingrayList;

import java.net.URI;

public interface ProtectionManager {

    public StingrayList getProtections(URI endpoint, Client client) throws StingrayRestClientException;

    public Protection retrieveProtection(URI endpoint, Client client, String vsName) throws StingrayRestClientException;

    public Protection createProtection(URI endpoint, Client client, String vsName, Protection protection) throws StingrayRestClientException;

    public Protection updateProtection(URI endpoint, Client client, String vsName, Protection protection) throws StingrayRestClientException;

    public boolean deleteProtection(URI endpoint, Client client, String vsName) throws StingrayRestClientException;



}
