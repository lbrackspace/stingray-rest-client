package org.rackspace.stingray.client;

import com.sun.jersey.api.client.ClientResponse;
import org.junit.Test;
import org.rackspace.stingray.client.manager.PoolManager;
import org.rackspace.stingray.client.manager.impl.PoolManagerImpl;
import org.rackspace.stingray.client.pool.Pool;
import org.rackspace.stingray.client.pool.PoolBasic;
import org.rackspace.stingray.client.pool.PoolProperties;

import javax.xml.bind.JAXBException;

public class StingrayRestClientTest {

    @Test
    public void verifyStingrayPoolManager() throws Exception, JAXBException {
        PoolManager poolManager = new PoolManagerImpl();
        Pool pool = poolManager.retrievePool("528830_770");
        org.junit.Assert.assertNotNull(pool);
    }

    @Test
    public void verifyStingrayRestClientConnection() throws Exception, JAXBException {
        StingrayRestClient client = new StingrayRestClient();
        ClientResponse response = client.getResource("pools/528830_770");
        System.out.print(response.toString());
//        System.out.print("PoolRead: " + response.getEntity(String.class));
        System.out.print("PoolRead: " + response.getEntity(Pool.class));
    }

    @Test
    public void verifyUpdateNodeOnPool() throws Exception, JAXBException {
        StingrayRestClient client = new StingrayRestClient();

        Pool pool = new Pool();
        PoolProperties properties = new PoolProperties();
        PoolBasic basic = new PoolBasic();

        ClientResponse response = client.getResource("pools/528830_770");
//        System.out.print("BEFORE UPDATE: " + response.getEntity(String.class));

        Pool read = response.getEntity(Pool.class);

        read.getProperties().getBasic().getNodes().add("10.1.1.2:9090");

//        String jsonstring = "{\"properties\":{\"basic\":{\"bandwidth_class\":\"\",\"disabled\":[],\"draining\":[],\"failure_pool\":\"\",\"max_idle_connections_pernode\":50,\"monitors\":[],\"node_connection_attempts\":3,\"nodes\":[\"50.57.174.153:9090\",\"50.57.174.150:9090\"],\"note\":\"\",\"passive_monitoring\":false,\"persistence_class\":\"\",\"transparent\":false},\"auto_scaling\":{\"cloud_credentials\":\"\",\"cluster\":\"\",\"data_center\":\"\",\"data_store\":\"\",\"enabled\":false,\"external\":true,\"hysteresis\":20,\"imageid\":\"\",\"ips_to_use\":\"publicips\",\"last_node_idle_time\":3600,\"max_nodes\":4,\"min_nodes\":1,\"name\":\"\",\"port\":80,\"refractory\":180,\"response_time\":1000,\"scale_down_level\":95,\"scale_up_level\":40,\"size_id\":\"\"},\"connection\":{\"max_connect_time\":4,\"max_connections_per_node\":0,\"max_queue_size\":0,\"max_reply_time\":30,\"queue_timeout\":10},\"ftp\":{\"support_rfc_2428\":false},\"http\":{\"keepalive\":true,\"keepalive_non_idempotent\":false},\"load_balancing\":{\"algorithm\":\"random\",\"node_weighting\":[],\"priority_enabled\":false,\"priority_nodes\":1,\"priority_values\":[\"50.57.174.153:9090:2\",\"50.57.174.150:9090:2\"]},\"node\":{\"close_on_death\":false,\"retry_fail_time\":60},\"smtp\":{\"send_starttls\":true},\"ssl\":{\"client_auth\":false,\"enable\":false,\"enhance\":false,\"send_close_alerts\":false,\"server_name\":false,\"strict_verify\":false},\"tcp\":{\"nagle\":true},\"udp\":{\"accept_from\":\"dest_only\",\"accept_from_mask\":\"\"}}}";
        ClientResponse response2 = client.updatePool("pools/528830_770", read);
//        System.out.print("AFTER UPDATE: " + response2.getEntity(String.class));
        Pool updatedpool = response2.getEntity(Pool.class);
//        System.out.print("PoolRead: " + response2.getEntity(PoolUpdate.class));
        org.junit.Assert.assertEquals(4, updatedpool.getProperties().getBasic().getNodes().size());
    }

    @Test
    public void verifyGetAllPools() throws Exception {
        StingrayRestClient client = new StingrayRestClient();
        ClientResponse response = client.getPools("pools");
    }
}
