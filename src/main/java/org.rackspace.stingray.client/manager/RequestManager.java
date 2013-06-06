package org.rackspace.stingray.client.manager;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Children;

import java.net.URI;

public interface RequestManager {

    Children getList(URI endpoint, Client client, String path) throws StingrayRestClientException;

    ClientResponse getItem(URI endpoint, Client client, String path) throws StingrayRestClientException;

    ClientResponse createItem(URI endpoint, Client client, String path, Object object) throws StingrayRestClientException;

    ClientResponse updateItem(URI endpoint, Client client, String path, Object object) throws StingrayRestClientException;

    boolean deleteItem(URI endpoint, Client client, String path) throws StingrayRestClientException;


}
