package org.rackspace.stingray.client.manager;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Children;

import javax.ws.rs.core.MediaType;
import java.net.URI;

public interface RequestManager {

    Children getList(URI endpoint, Client client, String path) throws StingrayRestClientException;

    ClientResponse getItem(URI endpoint, Client client, String path, MediaType cType) throws StingrayRestClientException;

    ClientResponse createItem(URI endpoint, Client client, String path, Object object, MediaType cType) throws StingrayRestClientException;

    ClientResponse updateItem(URI endpoint, Client client, String path, Object object, MediaType cType) throws StingrayRestClientException;

    boolean deleteItem(URI endpoint, Client client, String path) throws StingrayRestClientException;

    public <T> T interpretResponse(ClientResponse response, java.lang.Class<T> clazz) throws StingrayRestClientException;

}
