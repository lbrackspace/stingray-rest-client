package org.rackspace.stingray.client.exception;

/**
 * Created by IntelliJ IDEA.
 * User: mich6365
 * Date: 6/5/13
 * Time: 2:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class StingrayRestClientPathException extends StingrayRestClientException {

               public StingrayRestClientPathException(String message) {
        super("The invalid path was "+ message);
    }



}
