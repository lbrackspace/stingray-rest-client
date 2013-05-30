package org.rackspace.stingray.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import org.rackspace.stingray.client.config.ClientConfigKeys;
import org.rackspace.stingray.client.config.Configuration;
import org.rackspace.stingray.client.config.StingrayRestClientConfiguration;
import org.rackspace.stingray.client.util.StingrayRestClientUtil;

import java.net.URI;

public abstract class StingrayRestClientManager {
    protected URI endpoint;
    protected Configuration config;
    protected Client client;
    protected boolean isDebugging;
    protected String adminUser;
    protected String adminKey;

    /**
     * @param config      The object to retrieve configuration data
     * @param endpoint    The rest client endpoint
     * @param client      The client used to process requests
     * @param isDebugging Is debugging enabled for the client
     * @param adminUser   The admin user name
     * @param adminKey    The admin key or password
     */
    public StingrayRestClientManager(Configuration config, URI endpoint, Client client, boolean isDebugging,
                                     String adminUser, String adminKey) {

        if (config == null) {
            config = new StingrayRestClientConfiguration();
        }

        if (endpoint == null) {
            endpoint = URI.create(config.getString(ClientConfigKeys.stingray_rest_endpoint)
                    + config.getString(ClientConfigKeys.stingray_base_uri));
        }

        if (client == null) {
            client = StingrayRestClientUtil.ClientHelper.createClient(false);
        }

        if (adminUser == null) {
            adminUser = config.getString(ClientConfigKeys.stingray_admin_user);
        }

        if (adminKey == null) {
            adminKey = config.getString(ClientConfigKeys.stingray_admin_key);
        }

        this.config = config;
        this.endpoint = endpoint;
        this.client = client;
        this.isDebugging = isDebugging;
        this.adminUser = adminUser;
        this.adminKey = adminKey;

        this.client.addFilter(new HTTPBasicAuthFilter(this.adminUser, this.adminKey));
    }

    //todo: JAVADOC all the things..
    //Convenience  methods
    public StingrayRestClientManager(URI endpoint, Configuration config, Client client) {
        this(config, endpoint, client, false, null, null);
    }

    public StingrayRestClientManager(URI endpoint, Configuration config) {
        this(config, endpoint, null, false, null, null);
    }

    public StingrayRestClientManager(URI endpoint) {
        this(null, endpoint, null, false, null, null);
    }

    public StingrayRestClientManager(URI endpoint, String adminUser, String adminKey) {
        this(null, endpoint, null, false, adminUser, adminKey);
    }

    public StingrayRestClientManager(URI endpoint, boolean isDebugging, String adminUser, String adminKey) {
        this(null, endpoint, null, isDebugging, adminUser, adminKey);
    }

    public StingrayRestClientManager(boolean isDebugging) {
        this(null, null, null, isDebugging, null, null);
    }

    public StingrayRestClientManager() {
        this(null, null, null, false, null, null);
    }
}
