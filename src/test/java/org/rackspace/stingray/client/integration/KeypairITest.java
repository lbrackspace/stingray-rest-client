package org.rackspace.stingray.client.integration;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Child;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.pool.Pool;
import org.rackspace.stingray.client.ssl.keypair.Keypair;

/**
 * Created by IntelliJ IDEA.
 * User: mich6365
 * Date: 6/7/13
 * Time: 11:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class KeypairITest {
    StingrayRestClient client;

       @Before
       public void standUp() {
           client = new StingrayRestClient();
       }

       /**
        * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
        */
       @Test
       public void getListOfKeypairs() throws StingrayRestClientException {
           Children children = client.getKeypairs();
           Assert.assertTrue(children.getChildren().size() > 0);
       }

       @Test
       public void getSpecificKeypair() throws StingrayRestClientException {
           Children children = client.getKeypairs();
           Assert.assertTrue(children.getChildren().size() > 0);
           Child child = children.getChildren().get(0);
           String vsname = child.getName();
           Keypair keypair = client.getKeypair(vsname);
           Assert.assertNotNull(keypair);
       }
}
