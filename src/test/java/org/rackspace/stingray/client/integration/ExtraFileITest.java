package org.rackspace.stingray.client.integration;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.extra.file.ExtraFile;
import org.rackspace.stingray.client.list.Child;
import org.rackspace.stingray.client.list.Children;

public class ExtraFileITest {
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
    public void getListOfExtraFiles() throws StingrayRestClientException {
        Children children = client.getExtraFiles();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    @Test
    public void getSpecificExtraFile() throws StingrayRestClientException {
        Children children = client.getExtraFiles();
        Assert.assertTrue(children.getChildren().size() > 0);
        Child child = children.getChildren().get(0);
        String vsname = child.getName();
        ExtraFile extraFile = client.getExtraFile(vsname);
        Assert.assertNotNull(extraFile);
    }
}