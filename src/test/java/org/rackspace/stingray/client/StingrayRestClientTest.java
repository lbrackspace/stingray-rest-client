package org.rackspace.stingray.client;


import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class StingrayRestClientTest {
    public static class WhenTestingPoolCalls {
        protected StingrayRestClient client;

        @Before
        public void standUp() {
            client = new StingrayRestClient();
        }

        @Test
        public void stubTest() throws Exception {
        }
    }
    public static class WhenTestingActionScriptCalls {
        protected StingrayRestClient client;

        @Before
        public void standUp() {
            client = new StingrayRestClient();
        }

        @Test
        public void stubTest() throws Exception {
        }
    }
}