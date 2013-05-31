package org.rackspace.stingray.client.manager.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.extra.file.ExtraFile;
import org.rackspace.stingray.client.manager.ExtraFileManager;
import org.rackspace.stingray.client.manager.BaseManager;
import org.rackspace.stingray.client.util.ClientConstants;
import org.rackspace.stingray.client.util.StingrayList;

import javax.ws.rs.core.MediaType;
import java.net.URI;

public class ExtraFileManagerImpl extends BaseManager implements ExtraFileManager {
    private static final Log LOG = LogFactory.getLog(ExtraFileManagerImpl.class);

    /**
     *
     * @param endpoint
     * @param client
     * @return
     */
    @Override
    public StingrayList getExtraFiles(URI endpoint, Client client) {
         return null;
    }

    /**
     *
     * @param endpoint
     * @param client
     * @param vsName
     * @return
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     */
    @Override
    public ExtraFile retrieveExtraFile(URI endpoint, Client client, String vsName) throws StingrayRestClientException {
        ExtraFile extraFile = null;
        ClientResponse response = null;
        try {
            response = client.resource(endpoint + ClientConstants.EXTRAFILE_PATH  + vsName)
                    .accept(MediaType.APPLICATION_JSON)
                    .get(ClientResponse.class);

            if (!isResponseValid(response)) {
                buildFaultMessage(response);
            }

            extraFile = response.getEntity(ExtraFile.class);
        } catch (Exception e) {
            throw new StingrayRestClientException(ClientConstants.REQUEST_ERROR, e);
        }
        return extraFile;
    }

    /**
     *
     * @param endpoint
     * @param client
     * @param vsName
     * @param extraFile
     * @return
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     */
    @Override
    public ExtraFile createExtraFile(URI endpoint, Client client, String vsName, ExtraFile extraFile) throws StingrayRestClientException {
        return updateExtraFile(endpoint, client, vsName, extraFile);
    }

    /**
     *
     * @param endpoint
     * @param client
     * @param vsName
     * @param extraFile
     * @return
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     */
    @Override
    public ExtraFile updateExtraFile(URI endpoint, Client client, String vsName, ExtraFile extraFile) throws StingrayRestClientException {
       ClientResponse response = null;
        try {
            response = client.resource(endpoint + ClientConstants.EXTRAFILE_PATH + vsName)
                    .accept(MediaType.APPLICATION_JSON)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(extraFile)
                    .put(ClientResponse.class);

            if (!isResponseValid(response)) {
                buildFaultMessage(response);
            }

            extraFile = response.getEntity(ExtraFile.class);
        } catch (Exception e) {
            throw new StingrayRestClientException(ClientConstants.REQUEST_ERROR, e);
        }
        return extraFile;    }

    /**
     *
     * @param endpoint
     * @param client
     * @param vsName
     * @return
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     */
    @Override
    public boolean deleteExtraFile(URI endpoint, Client client, String vsName) throws StingrayRestClientException {
        ClientResponse response = null;
        try {
            response = client.resource(endpoint + ClientConstants.EXTRAFILE_PATH + vsName)
                    .accept(MediaType.APPLICATION_JSON)
                    .type(MediaType.APPLICATION_JSON)
                    .delete(ClientResponse.class);

            if (!isResponseValid(response)) {
                buildFaultMessage(response);
            }

        } catch (Exception e) {
            throw new StingrayRestClientException(ClientConstants.REQUEST_ERROR, e);
        }
        return true;
    }
}

