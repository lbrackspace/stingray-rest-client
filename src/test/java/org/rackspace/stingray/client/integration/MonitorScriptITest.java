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


    @Before
    public void standUp() {
        client = new StingrayRestClient();
        fileName = TESTNAME;
        fileText = "test file";

    }

    /**
     * Tests the creation of a Monitor Script
     * Verifies using get and a comparison of content contained
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


    @Test
    public void testUpateMonitorScript() throws StingrayRestClientException, URISyntaxException, IOException {
        String updatedFileText = "Updated the test script...";

        client.updateMonitorScript(fileName, createTestFile(fileName, updatedFileText));

        File updatedFile = client.getMonitorScript(fileName);
        Assert.assertNotNull(updatedFile);
        Assert.assertEquals(updatedFileText, FileUtils.readFileToString(updatedFile));
    }


    /**
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     *
     */
    @Test
    public void testGetListOfMonitorScripts() throws StingrayRestClientException {
        Children children = client.getMonitorScripts();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    @Test
    public void testGetMonitorScript() throws StingrayRestClientException {
        File retrievedFile = client.getMonitorScript(fileName);
        Assert.assertNotNull(retrievedFile);
    }


    @Test(expected = StingrayRestClientException.class)
    public void testDeleteMonitorScript() throws StingrayRestClientException {
        Boolean wasDeleted = client.deleteMonitorScript(fileName);
        Assert.assertTrue(wasDeleted);
        client.getMonitorScript(fileName);
    }

}
