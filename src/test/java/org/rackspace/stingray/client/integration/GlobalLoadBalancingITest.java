package org.rackspace.stingray.client.integration;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.glb.GlobalLoadBalancing;
import org.rackspace.stingray.client.glb.GlobalLoadBalancingBasic;
import org.rackspace.stingray.client.glb.GlobalLoadBalancingProperties;
import org.rackspace.stingray.client.list.Child;
import org.rackspace.stingray.client.list.Children;


public class GlobalLoadBalancingITest {
    StingrayRestClient client;
    GlobalLoadBalancing glb;
    GlobalLoadBalancingProperties glbProperties;
    GlobalLoadBalancingBasic glbBasic;
    String vsName;

    @Before
    public void standUp() {
        client = new StingrayRestClient();
        glbBasic = new GlobalLoadBalancingBasic();
        glbProperties = new GlobalLoadBalancingProperties();
        glb = new GlobalLoadBalancing();
        glbProperties.setBasic(glbBasic);
        glb.setProperties(glbProperties);
        vsName = "i_test_glb";
    }


    @Test
    public void testCreateGlb() throws StingrayRestClientException {
        GlobalLoadBalancing createdGlb = client.createGlb(vsName, glb);
        Assert.assertNotNull(createdGlb);
        Children children = client.getGlbs();
        Child child = children.getChildren().get(0);
        Assert.assertEquals(vsName, child.getName());
    }

    @Test
    public void testUpdateGlb() throws StingrayRestClientException {
        int testInt = 1;
        glb.getProperties().getBasic().setGeo_effect(testInt);
        GlobalLoadBalancing updatedGlb = client.updateGlb(vsName, glb);
        Assert.assertNotNull(updatedGlb);
        Assert.assertEquals((int) testInt, (int) updatedGlb.getProperties().getBasic().getGeo_effect());
    }


    /**
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     *
     */
    @Test
    public void testGetListOfGlbs() throws StingrayRestClientException {
        Children children = client.getGlbs();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    @Test
    public void testGetSpecificGlb() throws StingrayRestClientException {
        Children children = client.getGlbs();
        Assert.assertTrue(children.getChildren().size() > 0);
        Child child = children.getChildren().get(0);
        String name = child.getName();
        GlobalLoadBalancing glb = client.getGlb(name);
        Assert.assertNotNull(glb);
    }


    @Test
    public void testDeleteGlb() throws StingrayRestClientException {
//        int expectedLength = 0;
        Boolean wasDeleted = client.deleteGlb(vsName);
        Assert.assertTrue(wasDeleted);
//       Children children = client.getGlbs();
//        Assert.assertEquals(expectedLength, children.getChildren().size());
    }

}
