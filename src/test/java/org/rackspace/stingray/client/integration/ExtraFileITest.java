package org.rackspace.stingray.client.integration;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.extra.file.ExtraFileProperties;
import org.rackspace.stingray.client.list.Child;
import org.rackspace.stingray.client.list.Children;

import java.io.File;

public class ExtraFileITest {
    StingrayRestClient client;
    File extraFile;
    ExtraFileProperties extraFileProperties;
    String vsName;
    String fileName;

    @Before
    public void standUp() {
        client = new StingrayRestClient();
        extraFile = new File("test_file");
        extraFileProperties = new ExtraFileProperties();
        vsName = "i_test_extraFile";
        fileName = "test_file";
    }

    @Test
    public void testCreateExtraFile() throws StingrayRestClientException
    {

        File createdExtraFile = client.createExtraFile(fileName, extraFile);
        Assert.assertEquals(extraFile, createdExtraFile);
        //TODO need to get by name...
        Child child = client.getExtraFiles().getChildren().get(0);
        Assert.assertEquals(fileName, child.getName());
    }

    @Test
    public void testUpdateExtraFile() throws StingrayRestClientException
    {
        System.out.println(extraFileProperties.toString());
    }

    /**
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     *
     */
    @Test
    public void testGetListOfExtraFiles() throws StingrayRestClientException {
        Children children = client.getExtraFiles();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    @Test
    public void testGetSpecificExtraFile() throws StingrayRestClientException {
        Children children = client.getExtraFiles();
        Assert.assertTrue(children.getChildren().size() > 0);
        Child child = children.getChildren().get(0);
        String vsname = child.getName();
        File extraFile = client.getExtraFile(fileName);
        Assert.assertNotNull(extraFile);
    }
}
