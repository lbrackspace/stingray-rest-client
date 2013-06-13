package org.rackspace.stingray.client.integration;

import com.sun.tools.corba.se.idl.constExpr.BooleanAnd;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.exception.StingrayRestClientPathException;
import org.rackspace.stingray.client.list.Child;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.trafficscript.Trafficscript;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class TrafficscriptITest extends StingrayTestBase {
    StingrayRestClient client;
    String fileName;
    String fileText;

    /**
     * Initializes variables prior to test execution
     */
    @Before
    public void standUp() {
        client = new StingrayRestClient();
        fileName = "test_script";
        fileText = "This is a test script...";
    }

    /**
     * Tests the creation of a Traffic Script
     * Verifies using get and a comparison of content contained
     *
     * @throws StingrayRestClientException
     * @throws URISyntaxException
     * @throws IOException
     */
    @Test
    public void testCreateTrafficScript() throws StingrayRestClientException, URISyntaxException, IOException, StingrayRestClientPathException {
        //the fileName is what it will be created as. ex: /rules/test_script the file in STM is 'test_script'
        client.createTrafficscript(fileName, createTestFile(fileName, fileText));
        File gfile = client.getTraffiscript(fileName);
        Assert.assertNotNull(gfile);
        Assert.assertEquals(fileText, FileUtils.readFileToString(gfile));
    }

    /**
     * Tests the retrieval of a list of Trafficscripts
     * Retrieves a list of action scripts and checks its size
     *
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     *
     */
    @Test
    public void getListOfTrafficscripts() throws StingrayRestClientException, StingrayRestClientPathException {
        Children children = client.getTrafficscripts();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    /**
     * Tests the get function for an individual Trafficscript
     * Retrieves the specific Action Script created earlier
     *
     * @throws StingrayRestClientException
     */
    @Test
    public void testGetTrafficscript() throws StingrayRestClientException, StingrayRestClientPathException {
        File retrievedFile = client.getTraffiscript(fileName);
        Assert.assertNotNull(retrievedFile);

    }

    /**
     * Tests the updating of a Traffic Script
     * Verifies using a get and a comparison of content contained
     *
     * @throws StingrayRestClientException
     * @throws URISyntaxException
     * @throws IOException
     */
    @Test
    public void testUpdateTrafficScript() throws StingrayRestClientException, URISyntaxException, IOException, StingrayRestClientPathException {
        //the filename is the same, we want to update the contents...
        String updatedFileText = "Updated the test script...";

        client.updateTrafficScript(fileName, createTestFile(fileName, updatedFileText));

        File updatedFile = client.getTraffiscript(fileName);
        Assert.assertNotNull(updatedFile);
        Assert.assertEquals(updatedFileText, FileUtils.readFileToString(updatedFile));
    }

    @Test(expected = StingrayRestClientException.class)
    public void deleteTrafficScript() throws StingrayRestClientException, URISyntaxException, IOException, StingrayRestClientPathException {
        Boolean wasDeleted = client.deleteTrafficscript(fileName);
        Assert.assertTrue(wasDeleted);
        client.getTraffiscript(fileName);

    }
}
