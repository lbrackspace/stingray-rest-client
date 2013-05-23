package org.rackspace.stingray.client;

import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.net.URI;

public class StingrayRestClientTest {

    @Test
    public void verifyStingrayRestClientConnection() throws Exception, JAXBException {
        StingrayRestClient client = new StingrayRestClient();
        String response = client.testConnection(new URI("https://10.1.1.1:9070/api/tm/1.0/config/active/"), "pools");
        System.out.print(response.toString());
    }
}
