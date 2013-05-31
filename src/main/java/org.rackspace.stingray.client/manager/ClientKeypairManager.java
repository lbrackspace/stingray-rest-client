package org.rackspace.stingray.client.manager;

import com.sun.jersey.api.client.Client;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.ssl.client.keypair.ClientKeypair;
import org.rackspace.stingray.client.util.StingrayList;

import java.net.URI;

public interface ClientKeypairManager {

    public StingrayList getClientKeypairs(URI endpoint, Client client) throws StingrayRestClientException;

    public ClientKeypair retrieveClientKeypair(URI endpoint, Client client, String vsName) throws StingrayRestClientException;

    public ClientKeypair createClientKeypair(URI endpoint, Client client, String vsName, ClientKeypair clientKeypair) throws StingrayRestClientException;

    public ClientKeypair updateClientKeypair(URI endpoint, Client client, String vsName, ClientKeypair clientKeypair) throws StingrayRestClientException;

    public boolean deleteClientKeypair(URI endpoint, Client client, String vsName) throws StingrayRestClientException;



}
