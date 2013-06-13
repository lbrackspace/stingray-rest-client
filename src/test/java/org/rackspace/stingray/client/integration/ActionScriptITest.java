package org.rackspace.stingray.client.integration;

import junit.framework.Assert;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.exception.StingrayRestClientPathException;
import org.rackspace.stingray.client.list.Children;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class ActionScriptITest extends StingrayTestBase {
    StingrayRestClient client;
    String fileName;
    String fileText;

    /**
     * Initializes variables prior to test execution
     */
    @Before
    public void standUp() {
        client = new StingrayRestClient();
        fileText = "test_file";
        fileName = TESTNAME;
    }


    /**
     * Tests the creation of an Action Script
     * Verifies using get and a comparison of content contained
     *
     * @throws StingrayRestClientException
     * @throws URISyntaxException
     * @throws IOException
     */
    @Test
    public void testCreateActionScript() throws StingrayRestClientException, URISyntaxException, IOException, StingrayRestClientPathException {

        client.createActionScript(fileName, createTestFile(fileName, fileText));
        File createdFile = client.getActionScript(fileName);
        Assert.assertNotNull(createdFile);
        Assert.assertEquals(fileText, FileUtils.readFileToString(createdFile));
    }

    /**
     * Tests the updating of an Action Script
     * Verifies using a get and a comparison of content contained
     *
     * @throws StingrayRestClientException
     * @throws URISyntaxException
     * @throws IOException
     */
    @Test
    public void testUpdateActionScript() throws StingrayRestClientException, URISyntaxException, IOException, StingrayRestClientPathException {
        String updatedFileText = "Updated the test script...";

        client.updateActionScript(fileName, createTestFile(fileName, updatedFileText));

        File updatedFile = client.getActionScript(fileName);
        Assert.assertNotNull(updatedFile);
        Assert.assertEquals(updatedFileText, FileUtils.readFileToString(updatedFile));
    }

    /**
     * Tests the retrieval of a list of Action Scripts
     * Retrieves a list of action scripts and checks its size
     *
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     *
     */
    @Test
    public void testGetListOfActionScripts() throws StingrayRestClientException, StingrayRestClientPathException {
        Children children = client.getActionScripts();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    /**
     * Tests the get function for an individual Action Script
     * Retrieves the specific Action Script created earlier
     *
     * @throws StingrayRestClientException
     */
    @Test
    public void testGetSpecificActionScript() throws StingrayRestClientException, StingrayRestClientPathException {
        File retrievedFile = client.getActionScript(fileName);
        Assert.assertNotNull(retrievedFile);
    }

    /**
     * Tests the deletion of an Action Script
     * Checks return of the delete call, and throws an error
     *
     * @throws StingrayRestClientException
     */
    @Test(expected = StingrayRestClientException.class)
    public void testDeleteActionScript() throws StingrayRestClientException, StingrayRestClientPathException {
        Boolean wasDeleted = client.deleteActionScript(fileName);
        Assert.assertTrue(wasDeleted);
        client.getActionScript(fileName);


    }
}
