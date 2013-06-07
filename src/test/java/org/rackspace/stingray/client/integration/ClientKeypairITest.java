package org.rackspace.stingray.client.integration;

import com.sun.jersey.api.client.Client;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Child;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.ssl.client.keypair.ClientKeypair;

/**
 * Created by IntelliJ IDEA.
 * User: mich6365
 * Date: 6/7/13
 * Time: 11:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class ClientKeypairITest {
    StingrayRestClient client;

       @Before
       public void standUp() {
           client = new StingrayRestClient();
       }

       /**
        * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
        */
       @Test
       public void getListOfClientKeypairs() throws StingrayRestClientException {
           Children children = client.getClientKeypairs();
           Assert.assertTrue(children.getChildren().size() > 0);
       }

       @Test
       public void getSpecificClientKeypair() throws StingrayRestClientException {
           Children children = client.getClientKeypairs();
           Assert.assertTrue(children.getChildren().size() > 0);
           Child child = children.getChildren().get(0);
           String vsname = child.getName();
           ClientKeypair clientKeypair = client.getClientKeypair(vsname);
           Assert.assertNotNull(clientKeypair);
       }
}
