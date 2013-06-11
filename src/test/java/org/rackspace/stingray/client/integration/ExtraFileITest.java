package org.rackspace.stingray.client.integration;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.extra.file.ExtraFile;
import org.rackspace.stingray.client.extra.file.ExtraFileProperties;
import org.rackspace.stingray.client.list.Child;
import org.rackspace.stingray.client.list.Children;

public class ExtraFileITest {
    StingrayRestClient client;
    ExtraFile extraFile;
    ExtraFileProperties extraFileProperties;
    String vsName;

    @Before
    public void standUp() {
        client = new StingrayRestClient();
        extraFile = new ExtraFile();
        extraFileProperties = new ExtraFileProperties();
        vsName = "i_test_extraFile";
    }

    @Test
    public void testCreateExtraFile() throws StingrayRestClientException
    {
        ExtraFile createdExtraFile = client.createExtraFile(vsName, extraFile);
        Assert.assertEquals(extraFile, createdExtraFile);
        Child child = client.getExtraFiles().getChildren().get(0);
        Assert.assertEquals(vsName, child.getName());
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
        ExtraFile extraFile = client.getExtraFile(vsName);
        Assert.assertNotNull(extraFile);
    }
}
