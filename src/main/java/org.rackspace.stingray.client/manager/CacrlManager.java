package org.rackspace.stingray.client.manager;

import com.sun.jersey.api.client.Client;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.ssl.cacrl.Cacrl;
import org.rackspace.stingray.client.util.StingrayList;

import java.net.URI;

public interface CacrlManager {

    public StingrayList getCacrls(URI endpoint, Client client) throws StingrayRestClientException;

    public Cacrl retrieveCacrl(URI endpoint, Client client, String vsName) throws StingrayRestClientException;

    public Cacrl createCacrl(URI endpoint, Client client, String vsName, Cacrl cacrl) throws StingrayRestClientException;

    public Cacrl updateCacrl(URI endpoint, Client client, String vsName, Cacrl cacrl) throws StingrayRestClientException;

    public boolean deleteCacrl(URI endpoint, Client client, String vsName) throws StingrayRestClientException;



}
