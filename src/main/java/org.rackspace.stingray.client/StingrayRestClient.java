package org.rackspace.stingray.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import org.rackspace.stingray.client.action.ActionScript;
import org.rackspace.stingray.client.bandwidth.Bandwidth;
import org.rackspace.stingray.client.config.Configuration;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.exception.StingrayRestClientPathException;
import org.rackspace.stingray.client.extra.file.ExtraFile;
import org.rackspace.stingray.client.glb.GlobalLoadBalancing;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.location.Location;
import org.rackspace.stingray.client.manager.RequestManager;
import org.rackspace.stingray.client.manager.StingrayRestClientManager;
import org.rackspace.stingray.client.manager.impl.RequestManagerImpl;
import org.rackspace.stingray.client.monitor.Monitor;
import org.rackspace.stingray.client.monitor.MonitorScript;
import org.rackspace.stingray.client.persistence.Persistence;
import org.rackspace.stingray.client.pool.Pool;
import org.rackspace.stingray.client.protection.Protection;
import org.rackspace.stingray.client.rate.Rate;
import org.rackspace.stingray.client.ssl.cacrl.Cacrl;
import org.rackspace.stingray.client.ssl.client.keypair.ClientKeypair;
import org.rackspace.stingray.client.ssl.keypair.Keypair;
import org.rackspace.stingray.client.tm.TrafficManager;
import org.rackspace.stingray.client.traffic.ip.TrafficIp;
import org.rackspace.stingray.client.trafficscript.Trafficscript;
import org.rackspace.stingray.client.util.ClientConstants;
import org.rackspace.stingray.client.virtualserver.VirtualServer;

import java.net.URI;

public class StingrayRestClient extends StingrayRestClientManager {
    private final RequestManager requestManager = new RequestManagerImpl();

    public StingrayRestClient(URI endpoint, Configuration config, Client client) {
        super(config, endpoint, client, false, null, null);
    }

    public StingrayRestClient(URI endpoint, Configuration config) {
        super(config, endpoint, null, false, null, null);
    }

    public StingrayRestClient(URI endpoint) {
        super(null, endpoint, null, false, null, null);
    }

    public StingrayRestClient(URI endpoint, String adminUser, String adminKey) {
        super(null, endpoint, null, false, adminUser, adminKey);
    }

    public StingrayRestClient(URI endpoint, boolean isDebugging, String adminUser, String adminKey) {
        super(null, endpoint, null, isDebugging, adminUser, adminKey);
    }

    public StingrayRestClient(boolean isDebugging) {
        super(null, null, null, isDebugging, null, null);
    }

    public StingrayRestClient() {
        super(null, null, null, false, null, null);
    }

    private Boolean isPathValid(String path) {
        if (path.equals(ClientConstants.RATE_PATH) || path.equals(ClientConstants.PERSISTENCE_PATH)
                || path.equals(ClientConstants.POOL_PATH) || path.equals(ClientConstants.ACTIONSCRIPT_PATH)
                || path.equals(ClientConstants.BANDWIDTH_PATH) || path.equals(ClientConstants.CACRL_PATH)
                || path.equals(ClientConstants.CLIENTKEYPAIR_PATH) || path.equals(ClientConstants.EXTRAFILE_PATH)
                || path.equals(ClientConstants.GLB_PATH) || path.equals(ClientConstants.IP_PATH)
                || path.equals(ClientConstants.KEYPAIR_PATH) || path.equals(ClientConstants.LOCATION_PATH)
                || path.equals(ClientConstants.MONITOR_PATH) || path.equals(ClientConstants.MONITORSCRIPT_PATH)
                || path.equals(ClientConstants.PROTECTION_PATH) || path.equals(ClientConstants.SERVER_PATH)
                || path.equals(ClientConstants.TRAFFICMANAGER_PATH) || path.equals(ClientConstants.TRAFFICSCRIPT_PATH)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param path Path to object endpoint in the rest client
     * @return the generic list retrieval method
     * @throws StingrayRestClientException ]
     */
    private Children getItems(String path) throws StingrayRestClientException {
        if (isPathValid(path)) {
            return requestManager.getList(endpoint, client, path);
        } else {
            throw new StingrayRestClientPathException(path);
        }
    }

    /**
     * @param vsName
     * @param clazz
     * @param path
     * @param <T>
     * @return
     * @throws StingrayRestClientException
     */
    private <T> T getItem(String vsName, java.lang.Class<T> clazz, String path) throws StingrayRestClientException {
        if (isPathValid(path)) {
            ClientResponse response = requestManager.getItem(endpoint, client, path + vsName);
            T obj = interpretResponse(response, clazz);
            return obj;
        } else {
            throw new StingrayRestClientPathException(path);
        }
    }

    /**
     * @param vsName
     * @param clazz
     * @param path
     * @param obj
     * @param <T>
     * @return
     * @throws StingrayRestClientException
     */
    private <T> T createItem(String vsName, Class<T> clazz, String path, T obj) throws StingrayRestClientException {
        return updateItem(vsName, clazz, path, obj);
    }


    /**
     * @param vsName
     * @param clazz
     * @param path
     * @param obj
     * @param <T>
     * @return
     * @throws StingrayRestClientException
     */
    private <T> T updateItem(String vsName, Class<T> clazz, String path, T obj) throws StingrayRestClientException {
        if(isPathValid(path))
        {
        ClientResponse response = requestManager.updateItem(endpoint, client, path + vsName, obj);
        return interpretResponse(response, clazz);
        }
        else
        {
            throw new StingrayRestClientPathException(path);
        }
    }


    /**
     * @param vsName
     * @param path
     * @return
     * @throws StingrayRestClientException
     */
    private Boolean deleteItem(String vsName, String path) throws StingrayRestClientException {
        if (isPathValid(path))
            return requestManager.deleteItem(endpoint, client, path + vsName);
        else
            throw new StingrayRestClientException();
    }

    /**
     * @return A list of children representing individual virtual server names and URI's
     * @throws StingrayRestClientException
     */
    public Children getVirtualServers() throws StingrayRestClientException {
        return getItems(ClientConstants.SERVER_PATH);
    }

    /**
     * @param vsName
     * @return
     * @throws StingrayRestClientException
     */
    public VirtualServer getVirtualServer(String vsName) throws StingrayRestClientException {
        return getItem(vsName, VirtualServer.class, ClientConstants.SERVER_PATH);
    }

    /**
     * @param vsName
     * @param virtualServer
     * @return
     * @throws StingrayRestClientException
     */
    public VirtualServer updateVirtualServer(String vsName, VirtualServer virtualServer) throws StingrayRestClientException {
        return updateItem(vsName, VirtualServer.class, ClientConstants.SERVER_PATH, virtualServer);
    }

    /**
     * @param vsName
     * @return
     * @throws StingrayRestClientException
     */
    public boolean deleteVirtualServer(String vsName) throws StingrayRestClientException {
        return deleteItem(vsName, ClientConstants.SERVER_PATH);
    }


    /**
     * @return the generic list for pools providing the name and the endpoint for a specific request
     * @throws StingrayRestClientException
     */
    public Children getPools() throws StingrayRestClientException {
        return getItems(ClientConstants.POOL_PATH);
    }
    /*
     * POOLS
     */

    /**
     * @param vsName the virtual server name for pool retrieval
     * @throws StingrayRestClientException
     */
    public Pool getPool(String vsName) throws StingrayRestClientException {
        return getItem(vsName, Pool.class, ClientConstants.POOL_PATH);
    }

    /**
     * @param vsName The virtual server name related to the pool
     * @param pool   The pool object used to create a Stingray Pool
     * @return The configured pool object
     * @throws StingrayRestClientException
     */
    public Pool createPool(String vsName, Pool pool) throws StingrayRestClientException {
        return createItem(vsName, Pool.class, ClientConstants.POOL_PATH, pool);
    }

    /**
     * @param vsName The virtual server name related to the pool
     * @param pool   The pool object used to create a Stingray Pool
     * @return The configured pool object
     * @throws StingrayRestClientException
     */
    public Pool updatePool(String vsName, Pool pool) throws StingrayRestClientException {
        return updateItem(vsName, Pool.class, ClientConstants.POOL_PATH, pool);
    }

    /**
     * @param vsName The virtual server name related to the pool
     * @throws StingrayRestClientException
     */
    public void deletePool(String vsName) throws StingrayRestClientException {
        deleteItem(vsName, ClientConstants.POOL_PATH);
    }


    /**
     * @return the generic list for actionScripts providing the name and the endpoint for a specific request
     * @throws StingrayRestClientException
     */
    public Children getActionScripts() throws StingrayRestClientException {
        return getItems(ClientConstants.ACTIONSCRIPT_PATH);
    }

    /**
     * @param vsName the virtual server name for action script retrieval
     * @throws StingrayRestClientException
     */
    public ActionScript retrievActionScript(String vsName) throws StingrayRestClientException {
        return getItem(vsName, ActionScript.class, ClientConstants.ACTIONSCRIPT_PATH);
    }

    /**
     * @param vsName       The virtual server name related to the actionScript
     * @param actionScript The actionScript object used to create a Stingray Action Script
     * @return The configured Action Script object
     * @throws StingrayRestClientException
     */
    public ActionScript createActionScript(String vsName, ActionScript actionScript) throws StingrayRestClientException {
        return createItem(vsName, ActionScript.class, ClientConstants.ACTIONSCRIPT_PATH, actionScript);
    }


    /**
     * @param vsName       The virtual server name related to the action script
     * @param actionScript The action script object used to create a Stingray action script
     * @return The configured action script object
     * @throws StingrayRestClientException
     */
    public ActionScript updateActionScript(String vsName, ActionScript actionScript) throws StingrayRestClientException {
        return updateItem(vsName, ActionScript.class, ClientConstants.ACTIONSCRIPT_PATH, actionScript);
    }

    /**
     * @param vsName The virtual server name related to the action script
     * @throws StingrayRestClientException
     */
    public void deleteActionScript(String vsName) throws StingrayRestClientException {
        deleteItem(vsName, ClientConstants.ACTIONSCRIPT_PATH);
    }

    /**
     * @return the generic list for bandwidths providing the name and the endpoint for a specific request
     * @throws StingrayRestClientException
     */
    public Children getBandwidths() throws StingrayRestClientException {
        return getItems(ClientConstants.BANDWIDTH_PATH);
    }

    /**
     * @param vsName the virtual server name for bandwidth retrieval
     * @throws StingrayRestClientException
     */
    public Bandwidth getBandwidth(String vsName) throws StingrayRestClientException {
        return getItem(vsName, Bandwidth.class, ClientConstants.BANDWIDTH_PATH);
    }

    /**
     * @param vsName    The virtual server name related to the bandwidth
     * @param bandwidth The bandwidth object used to create a Stingray bandwidth
     * @return The configured Bandwidth object
     * @throws StingrayRestClientException
     */
    public Bandwidth createBandwidth(String vsName, Bandwidth bandwidth) throws StingrayRestClientException {
        return createItem(vsName, Bandwidth.class, ClientConstants.BANDWIDTH_PATH, bandwidth);
    }


    /**
     * @param vsName    The virtual server name related to the bandwidth
     * @param bandwidth The bandwidth object used to create a Stingray bandwidth
     * @return The configured bandwidth object
     * @throws StingrayRestClientException
     */
    public Bandwidth updateBandwidth(String vsName, Bandwidth bandwidth) throws StingrayRestClientException {
        return updateItem(vsName, Bandwidth.class, ClientConstants.BANDWIDTH_PATH, bandwidth);
    }

    /**
     * @param vsName The virtual server name related to the bandwidth
     * @throws StingrayRestClientException
     */
    public void deleteBandwidth(String vsName) throws StingrayRestClientException {
        deleteItem(vsName, ClientConstants.BANDWIDTH_PATH);
    }


    /**
     * @return the generic list for extra files providing the name and the endpoint for a specific request
     * @throws StingrayRestClientException
     */
    public Children getExtraFiles() throws StingrayRestClientException {
        return getItems(ClientConstants.EXTRAFILE_PATH);
    }

    /**
     * @param vsName the virtual server name for extra file retrieval
     * @throws StingrayRestClientException
     */
    public ExtraFile getExtraFile(String vsName) throws StingrayRestClientException {
        return getItem(vsName, ExtraFile.class, ClientConstants.EXTRAFILE_PATH);
    }

    /**
     * @param vsName    The virtual server name related to the extra file
     * @param extraFile The extra file object used to create a Stingray extra file
     * @return The configured ExtraFile object
     * @throws StingrayRestClientException
     */
    public ExtraFile createExtraFile(String vsName, ExtraFile extraFile) throws StingrayRestClientException {
        return createItem(vsName, ExtraFile.class, ClientConstants.EXTRAFILE_PATH, extraFile);
    }


    /**
     * @param vsName    The virtual server name related to the extra file
     * @param extraFile The extra file object used to create a Stingray extra files
     * @return The configured extra file object
     * @throws StingrayRestClientException
     */
    public ExtraFile updateExtraFile(String vsName, ExtraFile extraFile) throws StingrayRestClientException {
        return updateItem(vsName, ExtraFile.class, ClientConstants.EXTRAFILE_PATH, extraFile);
    }

    /**
     * @param vsName The virtual server name related to the extra file
     * @throws StingrayRestClientException
     */
    public void deleteExtraFile(String vsName) throws StingrayRestClientException {
        deleteItem(vsName, ClientConstants.EXTRAFILE_PATH);
    }

    /**
     * @return the generic list for global load balancers providing the name and the endpoint for a specific request
     * @throws StingrayRestClientException
     */
    public Children getGlbs() throws StingrayRestClientException {
        return getItems(ClientConstants.GLB_PATH);
    }

    /**
     * @param vsName the virtual server name for global load balancing retrieval
     * @throws StingrayRestClientException
     */
    public GlobalLoadBalancing getGlb(String vsName) throws StingrayRestClientException {
        return getItem(vsName, GlobalLoadBalancing.class, ClientConstants.GLB_PATH);
    }

    /**
     * @param vsName              The virtual server name related to the Glb
     * @param globalLoadBalancing The global load balancing object used to create a Stingray global load balancer
     * @return The configured ExtraFile object
     * @throws StingrayRestClientException
     */
    public GlobalLoadBalancing createGlb(String vsName, GlobalLoadBalancing globalLoadBalancing) throws StingrayRestClientException {
        return createItem(vsName, GlobalLoadBalancing.class, ClientConstants.GLB_PATH, globalLoadBalancing);
    }


    /**
     * @param vsName              The virtual server name related to the Glb
     * @param globalLoadBalancing The global load balancing object used to create a Stingray global load balancer
     * @return The configured global load balancing object
     * @throws StingrayRestClientException
     */
    public GlobalLoadBalancing updateGlb(String vsName, GlobalLoadBalancing globalLoadBalancing) throws StingrayRestClientException {
        return updateItem(vsName, GlobalLoadBalancing.class, ClientConstants.GLB_PATH, globalLoadBalancing);

    }

    /**
     * @param vsName The virtual server name related to the global load balancing
     * @throws StingrayRestClientException
     */
    public void deleteGlb(String vsName) throws StingrayRestClientException {
        deleteItem(vsName, ClientConstants.GLB_PATH);
    }


    /**
     * @return the generic list for locations providing the name and the endpoint for a specific request
     * @throws StingrayRestClientException
     */
    public Children getLocations() throws StingrayRestClientException {
        return getItems(ClientConstants.LOCATION_PATH);
    }

    /**
     * @param vsName the virtual server name for extra file retrieval
     * @throws StingrayRestClientException
     */
    public Location getLocation(String vsName) throws StingrayRestClientException {
        return getItem(vsName, Location.class, ClientConstants.LOCATION_PATH);
    }

    /**
     * @param vsName   The virtual server name related to the extra file
     * @param location The location object used to create a Stingray location
     * @return The configured Location object
     * @throws StingrayRestClientException
     */
    public Location createLocation(String vsName, Location location) throws StingrayRestClientException {
        return createItem(vsName, Location.class, ClientConstants.LOCATION_PATH, location);
    }


    /**
     * @param vsName   The virtual server name related to the location
     * @param location The extra file object used to create a Stingray locations
     * @return The configured Location object
     * @throws StingrayRestClientException
     */
    public Location updateLocation(String vsName, Location location) throws StingrayRestClientException {
        return updateItem(vsName, Location.class, ClientConstants.LOCATION_PATH, location);
    }

    /**
     * @param vsName The virtual server name related to the location
     * @throws StingrayRestClientException
     */
    public void deleteLocation(String vsName) throws StingrayRestClientException {
        deleteItem(vsName, ClientConstants.LOCATION_PATH);
    }


    /**
     * @return the generic list for extra files providing the name and the endpoint for a specific request
     * @throws StingrayRestClientException
     */
    public Children getMonitors() throws StingrayRestClientException {
        return getItems(ClientConstants.MONITOR_PATH);
    }

    /**
     * @param vsName the virtual server name for monitor retrieval
     * @throws StingrayRestClientException
     */
    public Monitor getMonitor(String vsName) throws StingrayRestClientException {
        return getItem(vsName, Monitor.class, ClientConstants.MONITOR_PATH);
    }

    /**
     * @param vsName  The virtual server name related to the monitor
     * @param monitor The monitor object used to create a Stingray monitor
     * @return The configured Monitor object
     * @throws StingrayRestClientException
     */
    public Monitor createMonitor(String vsName, Monitor monitor) throws StingrayRestClientException {
        return createItem(vsName, Monitor.class, ClientConstants.MONITOR_PATH, monitor);
    }


    /**
     * @param vsName  The virtual server name related to the monitor
     * @param monitor The monitor object used to create a Stingray monitors
     * @return The configured Monitor object
     * @throws StingrayRestClientException
     */
    public Monitor updateMonitor(String vsName, Monitor monitor) throws StingrayRestClientException {
        return updateItem(vsName, Monitor.class, ClientConstants.MONITOR_PATH, monitor);
    }

    /**
     * @param vsName The virtual server name related to the monitor
     * @throws StingrayRestClientException
     */
    public void deleteMonitor(String vsName) throws StingrayRestClientException {
        deleteItem(vsName, ClientConstants.MONITOR_PATH);
    }


    /**
     * @return the generic list for monitor scripts providing the name and the endpoint for a specific request
     * @throws StingrayRestClientException
     */
    public Children getMonitorScripts() throws StingrayRestClientException {
        return getItems(ClientConstants.MONITORSCRIPT_PATH);
    }

    /**
     * @param vsName the virtual server name for monitor script retrieval
     * @throws StingrayRestClientException
     */
    public MonitorScript getMonitorScript(String vsName) throws StingrayRestClientException {
        return getItem(vsName, MonitorScript.class, ClientConstants.MONITORSCRIPT_PATH);
    }

    /**
     * @param vsName        The virtual server name related to the monitor script
     * @param monitorScript The monitor script object used to create a Stingray monitor script
     * @return The configured MonitorScript object
     * @throws StingrayRestClientException
     */
    public MonitorScript createMonitorScript(String vsName, MonitorScript monitorScript) throws StingrayRestClientException {
        return createItem(vsName, MonitorScript.class, ClientConstants.MONITORSCRIPT_PATH, monitorScript);
    }


    /**
     * @param vsName        The virtual server name related to the monitor script
     * @param monitorScript The monitor script object used to create a Stingray monitor scripts
     * @return The configured MonitorScript object
     * @throws StingrayRestClientException
     */
    public MonitorScript updateMonitorScript(String vsName, MonitorScript monitorScript) throws StingrayRestClientException {
        return updateItem(vsName, MonitorScript.class, ClientConstants.MONITORSCRIPT_PATH, monitorScript);
    }

    /**
     * @param vsName The virtual server name related to the monitor script
     * @throws StingrayRestClientException
     */
    public void deleteMonitorScript(String vsName) throws StingrayRestClientException {
        deleteItem(vsName, ClientConstants.MONITORSCRIPT_PATH);
    }


    /**
     * @return the generic list for persistences providing the name and the endpoint for a specific request
     * @throws StingrayRestClientException
     */
    public Children getPersistence() throws StingrayRestClientException {
        return getItems(ClientConstants.PERSISTENCE_PATH);
    }

    /**
     * @param vsName the virtual server name for persistence retrieval
     * @throws StingrayRestClientException
     */
    public Persistence getPersistence(String vsName) throws StingrayRestClientException {
        return getItem(vsName, Persistence.class, ClientConstants.PERSISTENCE_PATH);
    }

    /**
     * @param vsName      The virtual server name related to the extra file
     * @param persistence The persistence object used to create a Stingray persistence
     * @return The configured Persistence object
     * @throws StingrayRestClientException
     */
    public Persistence createPersistence(String vsName, Persistence persistence) throws StingrayRestClientException {
        return createItem(vsName, Persistence.class, ClientConstants.PERSISTENCE_PATH, persistence);
    }


    /**
     * @param vsName      The virtual server name related to the persistence
     * @param persistence The persistence object used to create a Stingray persistence
     * @return The configured Persistence object
     * @throws StingrayRestClientException
     */
    public Persistence updatePersistence(String vsName, Persistence persistence) throws StingrayRestClientException {
        return updateItem(vsName, Persistence.class, ClientConstants.PERSISTENCE_PATH, persistence);
    }

    /**
     * @param vsName The virtual server name related to the persistence
     * @throws StingrayRestClientException
     */
    public void deletePersistence(String vsName) throws StingrayRestClientException {
        deleteItem(vsName, ClientConstants.PERSISTENCE_PATH);
    }


    /**
     * @return the generic list for protections providing the name and the endpoint for a specific request
     * @throws StingrayRestClientException
     */
    public Children getProtections() throws StingrayRestClientException {
        return getItems(ClientConstants.PROTECTION_PATH);
    }

    /**
     * @param vsName the virtual server name for protection retrieval
     * @throws StingrayRestClientException
     */
    public Protection getProtection(String vsName) throws StingrayRestClientException {
        return getItem(vsName, Protection.class, ClientConstants.PROTECTION_PATH);
    }

    /**
     * @param vsName     The virtual server name related to the protection
     * @param protection The protection object used to create a Stingray protection
     * @return The configured Protection object
     * @throws StingrayRestClientException
     */
    public Protection createProtection(String vsName, Protection protection) throws StingrayRestClientException {
        return createItem(vsName, Protection.class, ClientConstants.PROTECTION_PATH, protection);
    }


    /**
     * @param vsName     The virtual server name related to the bandwidth
     * @param protection The protection object used to create a Stingray protections
     * @return The configured Protection object
     * @throws StingrayRestClientException
     */
    public Protection updateProtection(String vsName, Protection protection) throws StingrayRestClientException {
        return updateItem(vsName, Protection.class, ClientConstants.PROTECTION_PATH, protection);
    }

    /**
     * @param vsName The virtual server name related to the extra file
     * @throws StingrayRestClientException
     */
    public void deleteProtection(String vsName) throws StingrayRestClientException {
        deleteItem(vsName, ClientConstants.PROTECTION_PATH);
    }


    /**
     * @return the generic list for rates providing the name and the endpoint for a specific request
     * @throws StingrayRestClientException
     */
    public Children getRates() throws StingrayRestClientException {
        return getItems(ClientConstants.RATE_PATH);
    }

    /**
     * @param vsName the virtual server name for rate retrieval
     * @throws StingrayRestClientException
     */
    public Rate getRate(String vsName) throws StingrayRestClientException {
        return getItem(vsName, Rate.class, ClientConstants.RATE_PATH);
    }

    /**
     * @param vsName The virtual server name related to the rate
     * @param rate   The rate object used to create a Stingray rate
     * @return The configured Rate object
     * @throws StingrayRestClientException
     */
    public Rate createRate(String vsName, Rate rate) throws StingrayRestClientException {
        return createItem(vsName, Rate.class, ClientConstants.RATE_PATH, rate);
    }


    /**
     * @param vsName The virtual server name related to the rate
     * @param rate   The rate object used to create a Stingray rates
     * @return The configured Rate object
     * @throws StingrayRestClientException
     */
    public Rate updateRate(String vsName, Rate rate) throws StingrayRestClientException {
        return createItem(vsName, Rate.class, ClientConstants.RATE_PATH, rate);
    }

    /**
     * @param vsName The virtual server name related to the rate
     * @throws StingrayRestClientException
     */
    public void deleteRate(String vsName) throws StingrayRestClientException {
        deleteItem(vsName, ClientConstants.RATE_PATH);
    }

     /**
     * @return the generic list for cacrls providing the name and the endpoint for a specific request
     * @throws StingrayRestClientException
     */
    public Children getCacrls() throws StingrayRestClientException {
        return getItems(ClientConstants.CACRL_PATH);
    }

    /**
     * @param vsName the virtual server name for cacrl retrieval
     * @throws StingrayRestClientException
     */
    public Cacrl getCacrl(String vsName) throws StingrayRestClientException {
        return getItem(vsName, Cacrl.class, ClientConstants.CACRL_PATH);
    }

    /**
     * @param vsName The virtual server name related to the cacrl
     * @param cacrl   The cacrl object used to create a Stingray cacrl
     * @return The configured Cacrl object
     * @throws StingrayRestClientException
     */
    public Cacrl createCacrl(String vsName, Cacrl cacrl) throws StingrayRestClientException {
        return createItem(vsName, Cacrl.class, ClientConstants.CACRL_PATH, cacrl);
    }


    /**
     * @param vsName The virtual server name related to the cacrl
     * @param cacrl   The cacrl object used to create a Stingray cacrl
     * @return The configured Cacrl object
     * @throws StingrayRestClientException
     */
    public Cacrl updateCacrl(String vsName, Cacrl cacrl) throws StingrayRestClientException {
        return createItem(vsName, Cacrl.class, ClientConstants.CACRL_PATH, cacrl);
    }

    /**
     * @param vsName The virtual server name related to the cacrl
     * @throws StingrayRestClientException
     */
    public void deleteCacrl(String vsName) throws StingrayRestClientException {
        deleteItem(vsName, ClientConstants.CACRL_PATH);
    }


     /**
     * @return the generic list for client keypairs providing the name and the endpoint for a specific request
     * @throws StingrayRestClientException
     */
    public Children getClientKeypairs() throws StingrayRestClientException {
        return getItems(ClientConstants.CLIENTKEYPAIR_PATH);
    }

    /**
     * @param vsName the virtual server name for client keypair retrieval
     * @throws StingrayRestClientException
     */
    public ClientKeypair getClientKeypair(String vsName) throws StingrayRestClientException {
        return getItem(vsName, ClientKeypair.class, ClientConstants.CLIENTKEYPAIR_PATH);
    }

    /**
     * @param vsName The virtual server name related to the client keypair
     * @param clientKeypair The client keypair object used to create a Stingray client keypair
     * @return The configured ClientKeypair object
     * @throws StingrayRestClientException
     */
    public ClientKeypair createClientKeypair(String vsName, ClientKeypair clientKeypair) throws StingrayRestClientException {
        return createItem(vsName, ClientKeypair.class, ClientConstants.CLIENTKEYPAIR_PATH, clientKeypair);
    }


    /**
     * @param vsName The virtual server name related to the clientkeypair
     * @param clientKeypair The client keypair object used to create a Stingray client keypairs
     * @return The configured ClientKeypair object
     * @throws StingrayRestClientException
     */
    public ClientKeypair updateClientKeypair(String vsName, ClientKeypair clientKeypair) throws StingrayRestClientException {
        return createItem(vsName, ClientKeypair.class, ClientConstants.CLIENTKEYPAIR_PATH, clientKeypair);
    }

    /**
     * @param vsName The virtual server name related to the client keypair
     * @throws StingrayRestClientException
     */
    public void deleteClientKeypair(String vsName) throws StingrayRestClientException {
        deleteItem(vsName, ClientConstants.CLIENTKEYPAIR_PATH);
    }

     /**
     * @return the generic list for keypairs providing the name and the endpoint for a specific request
     * @throws StingrayRestClientException
     */
    public Children getKeypairs() throws StingrayRestClientException {
        return getItems(ClientConstants.KEYPAIR_PATH);
    }

    /**
     * @param vsName the virtual server name for keypair retrieval
     * @throws StingrayRestClientException
     */
    public Keypair getKeypair(String vsName) throws StingrayRestClientException {
        return getItem(vsName, Keypair.class, ClientConstants.KEYPAIR_PATH);
    }

    /**
     * @param vsName The virtual server name related to the keypair
     * @param keypair   The keypair object used to create a Stingray keypair
     * @return The configured Keypair object
     * @throws StingrayRestClientException
     */
    public Keypair createKeypair(String vsName, Keypair keypair) throws StingrayRestClientException {
        return createItem(vsName, Keypair.class, ClientConstants.KEYPAIR_PATH, keypair);
    }


    /**
     * @param vsName The virtual server name related to the keypair
     * @param keypair The keypair object used to create a Stingray keypairs
     * @return The configured Keypair object
     * @throws StingrayRestClientException
     */
    public Keypair updateKeypair(String vsName, Keypair keypair) throws StingrayRestClientException {
        return createItem(vsName, Keypair.class, ClientConstants.KEYPAIR_PATH, keypair);
    }

    /**
     * @param vsName The virtual server name related to the keypair
     * @throws StingrayRestClientException
     */
    public void deleteKeypair(String vsName) throws StingrayRestClientException {
        deleteItem(vsName, ClientConstants.KEYPAIR_PATH);
    }


     /**
     * @return the generic list for traffic managers providing the name and the endpoint for a specific request
     * @throws StingrayRestClientException
     */
    public Children getTrafficManagers() throws StingrayRestClientException {
        return getItems(ClientConstants.TRAFFICMANAGER_PATH);
    }

    /**
     * @param vsName the virtual server name for rate retrieval
     * @throws StingrayRestClientException
     */
    public TrafficManager getTrafficManager(String vsName) throws StingrayRestClientException {
        return getItem(vsName, TrafficManager.class, ClientConstants.TRAFFICMANAGER_PATH);
    }

    /**
     * @param vsName The virtual server name related to the traffic manager
     * @param trafficManager The traffic manager object used to create a Stingray traffic manager
     * @return The configured TrafficManager object
     * @throws StingrayRestClientException
     */
    public TrafficManager createTrafficManager(String vsName, TrafficManager trafficManager) throws StingrayRestClientException {
        return createItem(vsName, TrafficManager.class, ClientConstants.TRAFFICMANAGER_PATH, trafficManager);
    }


    /**
     * @param vsName The virtual server name related to the traffic manager
     * @param trafficManager The traffic manager object used to create a Stingray traffic manager
     * @return The configured TrafficManager object
     * @throws StingrayRestClientException
     */
    public TrafficManager updateTrafficManager(String vsName, TrafficManager trafficManager) throws StingrayRestClientException {
        return createItem(vsName, TrafficManager.class, ClientConstants.TRAFFICMANAGER_PATH, trafficManager);
    }

    /**
     * @param vsName The virtual server name related to the rate
     * @throws StingrayRestClientException
     */
    public void deleteTrafficManager(String vsName) throws StingrayRestClientException {
        deleteItem(vsName, ClientConstants.TRAFFICMANAGER_PATH);
    }

     /**
     * @return the generic list for trafficscripts providing the name and the endpoint for a specific request
     * @throws StingrayRestClientException
     */
    public Children getTrafficscripts() throws StingrayRestClientException {
        return getItems(ClientConstants.TRAFFICSCRIPT_PATH);
    }

    /**
     * @param vsName the virtual server name for trafficscript retrieval
     * @throws StingrayRestClientException
     */
    public Trafficscript getTraffiscript(String vsName) throws StingrayRestClientException {
        return getItem(vsName, Trafficscript.class, ClientConstants.TRAFFICSCRIPT_PATH);
    }

    /**
     * @param vsName The virtual server name related to the trafficscript
     * @param trafficscript The rate object used to create a Stingray trafficscript
     * @return The configured Trafficscript object
     * @throws StingrayRestClientException
     */
    public Trafficscript createTrafficscript(String vsName, Trafficscript trafficscript) throws StingrayRestClientException {
        return createItem(vsName, Trafficscript.class, ClientConstants.TRAFFICSCRIPT_PATH, trafficscript);
    }


    /**
     * @param vsName The virtual server name related to the trafficscript
     * @param trafficscript The trafficscript object used to create a Stingray trafficscript
     * @return The configured Trafficscript object
     * @throws StingrayRestClientException
     */
    public Trafficscript updateTrafficScript(String vsName, Trafficscript trafficscript) throws StingrayRestClientException {
        return createItem(vsName, Trafficscript.class, ClientConstants.TRAFFICSCRIPT_PATH, trafficscript);
    }

    /**
     * @param vsName The virtual server name related to the trafficscript
     * @throws StingrayRestClientException
     */
    public void deleteTrafficscript(String vsName) throws StingrayRestClientException {
        deleteItem(vsName, ClientConstants.TRAFFICSCRIPT_PATH);
    }

     /**
     * @return the generic list for TrafficIps providing the name and the endpoint for a specific request
     * @throws StingrayRestClientException
     */
    public Children getTrafficIps() throws StingrayRestClientException {
        return getItems(ClientConstants.IP_PATH);
    }

    /**
     * @param vsName the virtual server name for rate retrieval
     * @throws StingrayRestClientException
     */
    public TrafficIp getTrafficIp(String vsName) throws StingrayRestClientException {
        return getItem(vsName, TrafficIp.class, ClientConstants.IP_PATH);
    }

    /**
     * @param vsName The virtual server name related to the Traffic Ip
     * @param trafficIp The rate object used to create a Stingray Traffic Ip
     * @return The configured TrafficIp object
     * @throws StingrayRestClientException
     */
    public TrafficIp createTrafficIp(String vsName, TrafficIp trafficIp) throws StingrayRestClientException {
        return createItem(vsName, TrafficIp.class, ClientConstants.IP_PATH, trafficIp);
    }


    /**
     * @param vsName The virtual server name related to the Traffic Ip
     * @param trafficIp   The Traffic Ip object used to create a Stingray Traffic Ip
     * @return The configured TrafficIp object
     * @throws StingrayRestClientException
     */
    public TrafficIp updateTrafficIp(String vsName, TrafficIp trafficIp) throws StingrayRestClientException {
        return createItem(vsName, TrafficIp.class, ClientConstants.IP_PATH, trafficIp);
    }

    /**
     * @param vsName The virtual server name related to the TrafficIp
     * @throws StingrayRestClientException
     */
    public void deleteTrafficIp(String vsName) throws StingrayRestClientException {
        deleteItem(vsName, ClientConstants.IP_PATH);
    }


    //Todo: rest of the methods, this is dependent on the managers being built up...
//    public ClientResponse updatePool(String path, Pool pool) throws Exception {
//        //Path will be in the client methods. This method should be generic possibly ...
//        ClientResponse response = null;
//        Client client = StingrayRestClientUtil.ClientHelper.createClient();
//
//        URI endpoint = URI.create(config.getString(ClientConfigKeys.stingray_rest_endpoint) + config.getString(ClientConfigKeys.stingray_base_uri));
//        try {
//            client.addFilter(new HTTPBasicAuthFilter(config.getString(ClientConfigKeys.stingray_admin_user), config.getString(ClientConfigKeys.stingray_admin_key)));
//            response = client.resource(endpoint + path).type(MediaType.APPLICATION_JSON)
//                    .accept(MediaType.APPLICATION_JSON).entity(pool).put(ClientResponse.class);
//        } catch (UniformInterfaceException ux) {
//            throw ux;
//        }
//
//        return response;
//    }
//

    //TODO: to do this call we need to generate object (based off of json schema we build) to pull in all lists as its 'generic' according to stingray
//    public ClientResponse getPools(String path) throws Exception {
//        ClientResponse response;
//        Client client = StingrayRestClientUtil.ClientHelper.createClient();
//
//        URI endpoint = URI.create(config.getString(ClientConfigKeys.stingray_rest_endpoint) + config.getString(ClientConfigKeys.stingray_base_uri));
//        try {
//            client.addFilter(new HTTPBasicAuthFilter(config.getString(ClientConfigKeys.stingray_admin_user), config.getString(ClientConfigKeys.stingray_admin_key)));
//            response = client.resource(endpoint + path).type(MediaType.APPLICATION_JSON)
//                    .accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
//        } catch (UniformInterfaceException ux) {
//            throw ux;
//        }
//
////        List<Pool> pools = StingrayRestClientUtil.ClientHelper.parsePools(response.getEntity(String.class));
//        return response;
//    }
}
