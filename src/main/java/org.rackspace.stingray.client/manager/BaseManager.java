package org.rackspace.stingray.client.manager;

import com.sun.jersey.api.client.ClientResponse;
import org.rackspace.stingray.client.util.ClientConstants;

public abstract class BaseManager {

    public boolean isResponseValid(ClientResponse response) {
        return (response != null && (response.getStatus() == ClientConstants.ACCEPTED
                || response.getStatus() == ClientConstants.NON_AUTHORATIVE
                || response.getStatus() == ClientConstants.OK
                || response.getStatus() == ClientConstants.NO_CONTENT
                || response.getStatus() == ClientConstants.CREATED));
    }
}
