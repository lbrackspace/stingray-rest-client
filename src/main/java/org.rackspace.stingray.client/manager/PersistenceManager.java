package org.rackspace.stingray.client.manager;

import com.sun.jersey.api.client.Client;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.persistence.Persistence;
import org.rackspace.stingray.client.util.StingrayList;

import java.net.URI;

public interface PersistenceManager {

    public StingrayList getPersistences(URI endpoint, Client client) throws StingrayRestClientException;

    public Persistence retrievePersistence(URI endpoint, Client client, String vsName) throws StingrayRestClientException;

    public Persistence createPersistence(URI endpoint, Client client, String vsName, Persistence persistence) throws StingrayRestClientException;

    public Persistence updatePersistence(URI endpoint, Client client, String vsName, Persistence persistence) throws StingrayRestClientException;

    public boolean deletePersistence(URI endpoint, Client client, String vsName) throws StingrayRestClientException;



}
