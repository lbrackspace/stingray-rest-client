package org.rackspace.stingray.client.integration;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Child;
import org.rackspace.stingray.client.list.Children;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class TrafficscriptITest extends StingrayTestBase{
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
    public void getListOfTrafficscripts() throws StingrayRestClientException {
        Children children = client.getTrafficscripts();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    @Test
    public void getSpecificTrafficscript() throws StingrayRestClientException {
        Children children = client.getTrafficscripts();
        Assert.assertTrue(children.getChildren().size() > 0);
        Child child = children.getChildren().get(0);
        String fileName = child.getName();
        File trafficscript = client.getTraffiscript(fileName);
        Assert.assertNotNull(trafficscript);
    }

    @Test
    public void createTrafficScript() throws StingrayRestClientException, URISyntaxException, IOException {
        //the fileName is what it will be created as. ex: /rules/test_script the file in STM is 'test_script'
        String fileName = "test_script";
        String fileText = "This is a test script...";

        client.createTrafficscript(fileName, createTestFile(fileName, fileText));

        File gfile = client.getTraffiscript(fileName);
        Assert.assertNotNull(gfile);
        Assert.assertEquals(fileText, FileUtils.readFileToString(gfile));
    }

    @Test
    public void updateTrafficScript() throws StingrayRestClientException, URISyntaxException, IOException {
        //the filename is the same, we want to update the contents...
        String fileName = "test_script";
        String fileText = "Updated the test script...";

        client.updateTrafficScript(fileName, createTestFile(fileName, fileText));

        File gfile = client.getTraffiscript(fileName);
        Assert.assertNotNull(gfile);
        Assert.assertEquals(fileText, FileUtils.readFileToString(gfile));
    }

    @Test(expected = StingrayRestClientException.class)
    public void deleteTrafficScript() throws StingrayRestClientException, URISyntaxException, IOException {
        String fileName = "test_script";
        client.deleteTrafficscript(fileName);
        client.getTraffiscript(fileName);
    }
}
