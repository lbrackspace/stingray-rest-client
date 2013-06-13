package org.rackspace.stingray.client.exception;

public class StingrayRestClientPathException extends StingrayRestClientException {

    public StingrayRestClientPathException(String message) {
        super("The invalid path was " + message);
    }
}
