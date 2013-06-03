package org.rackspace.stingray.client.manager;

import com.sun.jersey.api.client.Client;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.rackspace.stingray.client.list.GenericActionScripts;
import org.rackspace.stingray.client.manager.impl.ActionScriptManagerImpl;
import org.rackspace.stingray.client.util.ClientConstants;

import java.net.URI;

import static junit.framework.Assert.assertTrue;

@RunWith(Enclosed.class)
public class ActionScriptManagerTest {
    public static class WhenRetrievingActionScripts {

        URI base_uri;
        ActionScriptManager manager;
        Client client;

        @Before
        public void setUp() {
            base_uri = URI.create("https://10.12.99.45:9070/api/tm/1.0/config/active/");
            manager = new ActionScriptManagerImpl();
            client = new Client();
        }

        @Ignore
        @Test
        public void shouldRetrieveGenericActionScriptList() throws Exception {
            GenericActionScripts scripts = manager.getActionScripts(URI.create(base_uri
                    + ClientConstants.TRAFFICSCRIPT_PATH), client);
            assertTrue(scripts.getStingrayList().size() > 0);
        }

    }
}
