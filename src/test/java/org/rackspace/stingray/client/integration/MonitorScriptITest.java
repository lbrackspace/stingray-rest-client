package org.rackspace.stingray.client.integration;


import junit.framework.Assert;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Child;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.monitor.MonitorScript;
import org.rackspace.stingray.client.monitor.script.MonitorScriptProperties;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class MonitorScriptITest extends StingrayTestBase {
    StingrayRestClient client;
    String fileName;
    String fileText;

    /**
     * Initializes variables prior to test execution
     */
    @Before
    public void standUp() {
        client = new StingrayRestClient();
        fileName = TESTNAME;
        fileText = "test file";

    }

    /**
     * Tests the creation of a Monitor Script
     * Verifies using get and a comparison of content contained
     *
     * @throws StingrayRestClientException
     * @throws URISyntaxException
     * @throws IOException
     */
    @Test
    public void testCreateMonitorScript() throws StingrayRestClientException, URISyntaxException, IOException {
        client.createMonitorScript(fileName, createTestFile(fileName, fileText));
        File createdFile = client.getMonitorScript(fileName);
        Assert.assertNotNull(createdFile);
        Assert.assertEquals(fileText, FileUtils.readFileToString(createdFile));

    }

    /**
     * Tests the updating of a Monitor Script
     * Verifies using a get and a comparison of content contained
     *
     * @throws StingrayRestClientException
     * @throws URISyntaxException
     * @throws IOException
     */
    @Test
    public void testUpateMonitorScript() throws StingrayRestClientException, URISyntaxException, IOException {
        String updatedFileText = "Updated the test script...";

        client.updateMonitorScript(fileName, createTestFile(fileName, updatedFileText));

        File updatedFile = client.getMonitorScript(fileName);
        Assert.assertNotNull(updatedFile);
        Assert.assertEquals(updatedFileText, FileUtils.readFileToString(updatedFile));
    }


    /**
     * Tests the retrieval of a list of Monitor Scripts
     * Retrieves a list of action scripts and checks its size
     *
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     *
     */
    @Test
    public void testGetListOfMonitorScripts() throws StingrayRestClientException {
        Children children = client.getMonitorScripts();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    /**
     * Tests the get function for an individual Monitor Script
     * Retrieves the specific Action Script created earlier
     *
     * @throws StingrayRestClientException
     */
    @Test
    public void testGetMonitorScript() throws StingrayRestClientException {
        File retrievedFile = client.getMonitorScript(fileName);
        Assert.assertNotNull(retrievedFile);
    }

    /**
     * Tests the deletion of a Monitor Script
     * Checks return of the delete call, and throws an error
     *
     * @throws StingrayRestClientException
     */
    @Test(expected = StingrayRestClientException.class)
    public void testDeleteMonitorScript() throws StingrayRestClientException {
        Boolean wasDeleted = client.deleteMonitorScript(fileName);
        Assert.assertTrue(wasDeleted);
        client.getMonitorScript(fileName);
    }

}
