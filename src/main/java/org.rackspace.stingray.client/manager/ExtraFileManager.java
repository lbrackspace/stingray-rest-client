package org.rackspace.stingray.client.manager;

import com.sun.jersey.api.client.Client;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.extra.file.ExtraFile;
import org.rackspace.stingray.client.util.StingrayList;

import java.net.URI;

public interface ExtraFileManager {

    public StingrayList getExtraFiles(URI endpoint, Client client) throws StingrayRestClientException;

    public ExtraFile retrieveExtraFile(URI endpoint, Client client, String vsName) throws StingrayRestClientException;

    public ExtraFile createExtraFile(URI endpoint, Client client, String vsName, ExtraFile extraFile) throws StingrayRestClientException;

    public ExtraFile updateExtraFile(URI endpoint, Client client, String vsName, ExtraFile extraFile) throws StingrayRestClientException;

    public boolean deleteExtraFile(URI endpoint, Client client, String vsName) throws StingrayRestClientException;


}
