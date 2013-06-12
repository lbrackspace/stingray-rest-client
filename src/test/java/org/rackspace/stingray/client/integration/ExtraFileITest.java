package org.rackspace.stingray.client.integration;

import junit.framework.Assert;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.extra.file.ExtraFileProperties;
import org.rackspace.stingray.client.list.Child;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.util.ClientConstants;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class ExtraFileITest extends StingrayTestBase {
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
     * Tests the creation of an Extra File
     * Verifies using get and a comparison of content contained
     *
     * @throws StingrayRestClientException
     * @throws URISyntaxException
     * @throws IOException
     */
    @Test
    public void testCreateExtraFile() throws StingrayRestClientException, URISyntaxException, IOException {

        client.createExtraFile(fileName, createTestFile(fileName, fileText));
        File createdFile = client.getExtraFile(fileName);
        Assert.assertNotNull(createdFile);
        Assert.assertEquals(fileText, FileUtils.readFileToString(createdFile));
    }

    /**
     * Tests the updating of a Extra File
     * Verifies using a get and a comparison of content contained
     *
     * @throws StingrayRestClientException
     * @throws URISyntaxException
     * @throws IOException
     */
    @Test
    public void testUpdateExtraFile() throws StingrayRestClientException, URISyntaxException, IOException {
        String updatedFileText = "Updated the test script...";

        client.updateExtraFile(fileName, createTestFile(fileName, updatedFileText));

        File updatedFile = client.getExtraFile(fileName);
        Assert.assertNotNull(updatedFile);
        Assert.assertEquals(updatedFileText, FileUtils.readFileToString(updatedFile));
    }

    /**
     * Tests the retrieval of a list of Extra Files
     * Retrieves a list of action scripts and checks its size
     *
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     *
     */
    @Test
    public void testGetListOfExtraFiles() throws StingrayRestClientException {
        Children children = client.getExtraFiles();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    /**
     * Tests the get function for an individual Extra File
     * Retrieves the specific Action Script created earlier
     *
     * @throws StingrayRestClientException
     */
    @Test
    public void testGetSpecificExtraFile() throws StingrayRestClientException {
        File retrievedFile = client.getExtraFile(fileName);
        Assert.assertNotNull(retrievedFile);
    }

    /**
     * Tests the deletion of an Extra File
     * Checks return of the delete call, and throws an error
     *
     * @throws StingrayRestClientException
     */
    @Test(expected = StingrayRestClientException.class)
    public void testDeleteExtraFile() throws StingrayRestClientException {
        Boolean wasDeleted = client.deleteExtraFile(fileName);
        Assert.assertTrue(wasDeleted);
        client.getExtraFile(fileName);


    }
}
