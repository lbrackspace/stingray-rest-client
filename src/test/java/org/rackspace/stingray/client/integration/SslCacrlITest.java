package org.rackspace.stingray.client.integration;

import com.sun.jersey.core.util.StringIgnoreCaseKeyComparator;
import junit.framework.Assert;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.exception.StingrayRestClientPathException;
import org.rackspace.stingray.client.list.Child;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.ssl.cacrl.Cacrl;
import org.rackspace.stingray.client.ssl.cacrl.CacrlProperties;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class SslCacrlITest extends StingrayTestBase {
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
     * Tests the creation of a Traffic Script
     * Verifies using get and a comparison of content contained
     *
     * @throws StingrayRestClientException
     * @throws java.net.URISyntaxException
     * @throws java.io.IOException
     */
    @Test
    public void testCreateCacrl() throws StingrayRestClientException, URISyntaxException, IOException, StingrayRestClientPathException {
        //the fileName is what it will be created as. ex: /rules/test_script the file in STM is 'test_script'
        client.createCacrl(fileName, createTestFile(fileName, fileText));
        File gfile = client.getCacrl(fileName);
        Assert.assertNotNull(gfile);
        Assert.assertEquals(fileText, FileUtils.readFileToString(gfile));
    }

    /**
     * Tests the retrieval of a list of Cacrls
     * Retrieves a list of action scripts and checks its size
     *
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     *
     */
    @Test
    public void getListOfCacrls() throws StingrayRestClientException, StingrayRestClientPathException {
        Children children = client.getCacrls();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    /**
     * Tests the get function for an individual Cacrl
     * Retrieves the specific Action Script created earlier
     *
     * @throws StingrayRestClientException
     */
    @Test
    public void testGetCacrl() throws StingrayRestClientException, StingrayRestClientPathException {
        File retrievedFile = client.getCacrl(fileName);
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
    public void testUpdateCacrl() throws StingrayRestClientException, URISyntaxException, IOException, StingrayRestClientPathException {
        //the filename is the same, we want to update the contents...
        String updatedFileText = "Updated the test script...";

        client.updateCacrl(fileName, createTestFile(fileName, updatedFileText));

        File updatedFile = client.getCacrl(fileName);
        Assert.assertNotNull(updatedFile);
        Assert.assertEquals(updatedFileText, FileUtils.readFileToString(updatedFile));
    }

    /**
     * Tests the deletion of a Cacrl
     * Checks return of the delete call, and throws an error
     *
     * @throws StingrayRestClientException
     */
    @Test(expected = StingrayRestClientException.class)
    public void deleteCacrl() throws StingrayRestClientException, URISyntaxException, IOException, StingrayRestClientPathException {
        Boolean wasDeleted = client.deleteCacrl(fileName);
        Assert.assertTrue(wasDeleted);
        client.getCacrl(fileName);

    }
}
