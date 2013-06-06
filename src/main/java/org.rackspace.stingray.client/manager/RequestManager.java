package org.rackspace.stingray.client.manager;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Children;

import java.net.URI;

public interface RequestManager {

    public Children getList(URI endpoint, Client client, String path) throws StingrayRestClientException;

    public ClientResponse getItem(URI endpoint, Client client, String path) throws StingrayRestClientException;

    public ClientResponse createItem(URI endpoint, Client client, String path, Object object) throws StingrayRestClientException;

    public ClientResponse updateItem(URI endpoint, Client client, String path, Object object) throws StingrayRestClientException;

    public boolean deleteItem(URI endpoint, Client client, String path) throws StingrayRestClientException;


}
