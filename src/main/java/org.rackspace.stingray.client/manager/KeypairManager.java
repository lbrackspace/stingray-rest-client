package org.rackspace.stingray.client.manager;

import com.sun.jersey.api.client.Client;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.ssl.keypair.Keypair;
import org.rackspace.stingray.client.util.StingrayList;

import java.net.URI;

public interface KeypairManager {

    public StingrayList getKeypairs(URI endpoint, Client client) throws StingrayRestClientException;

    public Keypair retrieveKeypair(URI endpoint, Client client, String vsName) throws StingrayRestClientException;

    public Keypair createKeypair(URI endpoint, Client client, String vsName, Keypair keypair) throws StingrayRestClientException;

    public Keypair updateKeypair(URI endpoint, Client client, String vsName, Keypair keypair) throws StingrayRestClientException;

    public boolean deleteKeypair(URI endpoint, Client client, String vsName) throws StingrayRestClientException;



}
