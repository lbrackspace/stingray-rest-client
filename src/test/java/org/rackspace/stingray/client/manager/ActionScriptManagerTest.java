package org.rackspace.stingray.client.manager;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.config.ClientConfigKeys;
import org.rackspace.stingray.client.config.Configuration;
import org.rackspace.stingray.client.config.StingrayRestClientConfiguration;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.manager.impl.ActionScriptManagerImpl;

import static junit.framework.Assert.assertTrue;

@RunWith(Enclosed.class)
public class ActionScriptManagerTest {
    public static class WhenRetrievingActionScripts {

        protected String ROOT;
        ActionScriptManager manager;
        StingrayRestClient client;

        @Before
        public void setUp() {
            Configuration config = new StingrayRestClientConfiguration();

            ROOT = config.getString(ClientConfigKeys.stingray_rest_endpoint) + config.getString(ClientConfigKeys.stingray_base_uri);
            manager = new ActionScriptManagerImpl();
            client = new StingrayRestClient();
        }

        @Test
        public void shouldRetrieveGenericActionScriptList() throws Exception {
            Children scripts = client.getActionScripts();
            assertTrue(scripts.getChildren().size() >= 0);
        }

    }
}
