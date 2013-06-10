package org.rackspace.stingray.client.integration;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.glb.GlobalLoadBalancing;
import org.rackspace.stingray.client.list.Child;
import org.rackspace.stingray.client.list.Children;

public class GlobalLoadBalancingITest {
    StingrayRestClient client;

    @Before
    public void standUp() {
        client = new StingrayRestClient();
    }

    /**
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     *
     */
    @Test
    public void getListOfGlbs() throws StingrayRestClientException {
        Children children = client.getGlbs();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    @Test
    public void getSpecificGlb() throws StingrayRestClientException {
        Children children = client.getGlbs();
        Assert.assertTrue(children.getChildren().size() > 0);
        Child child = children.getChildren().get(0);
        String vsname = child.getName();
        GlobalLoadBalancing glb = client.getGlb(vsname);
        Assert.assertNotNull(glb);
    }
}
