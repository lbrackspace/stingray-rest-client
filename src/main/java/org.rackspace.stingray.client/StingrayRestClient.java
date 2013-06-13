package org.rackspace.stingray.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import org.rackspace.stingray.client.bandwidth.Bandwidth;
import org.rackspace.stingray.client.config.Configuration;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.exception.StingrayRestClientPathException;
import org.rackspace.stingray.client.glb.GlobalLoadBalancing;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.location.Location;
import org.rackspace.stingray.client.manager.RequestManager;
import org.rackspace.stingray.client.manager.StingrayRestClientManager;
import org.rackspace.stingray.client.manager.impl.RequestManagerImpl;
import org.rackspace.stingray.client.monitor.Monitor;
import org.rackspace.stingray.client.persistence.Persistence;
import org.rackspace.stingray.client.pool.Pool;
import org.rackspace.stingray.client.protection.Protection;
import org.rackspace.stingray.client.rate.Rate;
import org.rackspace.stingray.client.ssl.client.keypair.ClientKeypair;
import org.rackspace.stingray.client.ssl.keypair.Keypair;
import org.rackspace.stingray.client.tm.TrafficManager;
import org.rackspace.stingray.client.traffic.ip.TrafficIp;
import org.rackspace.stingray.client.util.ClientConstants;
import org.rackspace.stingray.client.virtualserver.VirtualServer;

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.net.URI;

public class StingrayRestClient extends StingrayRestClientManager {
    private RequestManager requestManager = new RequestManagerImpl();

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

    public void setRequestManager(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    /**
     * This method will check that a path is defined in one of the constants described in REST api documentation
     *
     * @param path  Variable holding the path used in a request
     * @return      Result from checking the path's validity
     */
    private Boolean isPathValid(String path) {
        if (path.equals(ClientConstants.RATE_PATH) || path.equals(ClientConstants.PERSISTENCE_PATH)
                || path.equals(ClientConstants.POOL_PATH) || path.equals(ClientConstants.ACTIONSCRIPT_PATH)
                || path.equals(ClientConstants.BANDWIDTH_PATH) || path.equals(ClientConstants.CACRL_PATH)
                || path.equals(ClientConstants.CLIENTKEYPAIR_PATH) || path.equals(ClientConstants.EXTRAFILE_PATH)
                || path.equals(ClientConstants.GLB_PATH) || path.equals(ClientConstants.IP_PATH)
                || path.equals(ClientConstants.KEYPAIR_PATH) || path.equals(ClientConstants.LOCATION_PATH)
                || path.equals(ClientConstants.MONITOR_PATH) || path.equals(ClientConstants.MONITORSCRIPT_PATH)
                || path.equals(ClientConstants.PROTECTION_PATH) || path.equals(ClientConstants.V_SERVER_PATH)
                || path.equals(ClientConstants.TRAFFICMANAGER_PATH) || path.equals(ClientConstants.TRAFFICSCRIPT_PATH)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Generic method to retrieve a list of the object at the specified path
     *
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
     * Generic method to retrieve an item at the specified path
     *
     * @param name      Name of the object to retrieve
     * @param clazz     Class type of the object being retrieved
     * @param path      Path to the object
     * @param <T>       Object generic declaration
     * @return          Calls another method to retrieve an item of a specified type
     * @throws StingrayRestClientException
     */
    private <T> T getItem(String name, java.lang.Class<T> clazz, String path) throws StingrayRestClientException {
        return getItem(name, clazz, path, MediaType.APPLICATION_JSON_TYPE);
    }

    /**
     * Overloaded method to retrieve an item at the specified path
     *
     * @param name      Name of the object to retrieve
     * @param clazz     Class type of the object being retrieved
     * @param path      Path to the object
     * @param <T>       Object generic declaration
     * @param cType     Type of request being sent (IE application/json)
     * @return          Return object of a specified type
     * @throws StingrayRestClientException
     */
    private <T> T getItem(String name, java.lang.Class<T> clazz, String path, MediaType cType) throws StingrayRestClientException {
        if (isPathValid(path)) {
            ClientResponse response = requestManager.getItem(endpoint, client, path + name, cType);
            T obj = interpretResponse(response, clazz);
            return obj;
        } else {
            throw new StingrayRestClientPathException(path);
        }
    }

    /**
     * @param name
     * @param clazz
     * @param path
     * @param obj
     * @param <T>
     * @return
     * @throws StingrayRestClientException
     */
    private <T> T createItem(String name, Class<T> clazz, String path, T obj) throws StingrayRestClientException {
        return createItem(name, clazz, path, obj, MediaType.APPLICATION_JSON_TYPE);
    }

    /**
     *
     * @param name
     * @param clazz
     * @param path
     * @param obj
     * @param cType
     * @param <T>
     * @return
     * @throws StingrayRestClientException
     */
    private <T> T createItem(String name, Class<T> clazz, String path, T obj, MediaType cType) throws StingrayRestClientException {
        return updateItem(name, clazz, path, obj, cType);
    }


    /**
     * @param name
     * @param clazz
     * @param path
     * @param obj
     * @param <T>
     * @return
     * @throws StingrayRestClientException
     */
    private <T> T updateItem(String name, Class<T> clazz, String path, T obj) throws StingrayRestClientException {
        return updateItem(name, clazz, path, obj, MediaType.APPLICATION_JSON_TYPE);
    }

    /**
     *
     * @param name
     * @param clazz
     * @param path
     * @param obj
     * @param cType
     * @param <T>
     * @return
     * @throws StingrayRestClientException
     */
    private <T> T updateItem(String name, Class<T> clazz, String path, T obj, MediaType cType) throws StingrayRestClientException {
        if (isPathValid(path)) {
            ClientResponse response = requestManager.updateItem(endpoint, client, path + name, obj, cType);
            return interpretResponse(response, clazz);
        } else {
            throw new StingrayRestClientPathException(path);
        }
    }


    /**
     * @param name
     * @param path
     * @return
     * @throws StingrayRestClientException
     */
    private Boolean deleteItem(String name, String path) throws StingrayRestClientException {
        if (isPathValid(path))
            return requestManager.deleteItem(endpoint, client, path + name);
        else
            throw new StingrayRestClientException();
    }

    public VirtualServer createVirtualServer(String name, VirtualServer vs) throws StingrayRestClientException {
        return createItem(name, VirtualServer.class, ClientConstants.V_SERVER_PATH, vs);
    }

    /**
     * @return A list of children representing individual virtual server names and URI's
     * @throws StingrayRestClientException
     */
    public Children getVirtualServers() throws StingrayRestClientException {
        return getItems(ClientConstants.V_SERVER_PATH);
    }

    /**
     * @param name
     * @return
     * @throws StingrayRestClientException
     */
    public VirtualServer getVirtualServer(String name) throws StingrayRestClientException {
        return getItem(name, VirtualServer.class, ClientConstants.V_SERVER_PATH);
    }

    /**
     * @param name
     * @param virtualServer
     * @return
     * @throws StingrayRestClientException
     */
    public VirtualServer updateVirtualServer(String name, VirtualServer virtualServer) throws StingrayRestClientException {
        return updateItem(name, VirtualServer.class, ClientConstants.V_SERVER_PATH, virtualServer);
    }

    /**
     * @param name
     * @return
     * @throws StingrayRestClientException
     */
    public boolean deleteVirtualServer(String name) throws StingrayRestClientException {
        return deleteItem(name, ClientConstants.V_SERVER_PATH);
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
     * @param name the virtual server name for pool retrieval
     * @throws StingrayRestClientException
     */
    public Pool getPool(String name) throws StingrayRestClientException {
        return getItem(name, Pool.class, ClientConstants.POOL_PATH);
    }

    /**
     * @param name The virtual server name related to the pool
     * @param pool   The pool object used to create a Stingray Pool
     * @return The configured pool object
     * @throws StingrayRestClientException
     */
    public Pool createPool(String name, Pool pool) throws StingrayRestClientException {
        return createItem(name, Pool.class, ClientConstants.POOL_PATH, pool);
    }

    /**
     * @param name The virtual server name related to the pool
     * @param pool   The pool object used to create a Stingray Pool
     * @return The configured pool object
     * @throws StingrayRestClientException
     */
    public Pool updatePool(String name, Pool pool) throws StingrayRestClientException {
        return updateItem(name, Pool.class, ClientConstants.POOL_PATH, pool);
    }

    /**
     * @param name The virtual server name related to the pool
     * @throws StingrayRestClientException
     */
    public Boolean deletePool(String name) throws StingrayRestClientException {
        return deleteItem(name, ClientConstants.POOL_PATH);
    }


    /**
     * @return the generic list for actionScripts providing the name and the endpoint for a specific request
     * @throws StingrayRestClientException
     */
    public Children getActionScripts() throws StingrayRestClientException {
        return getItems(ClientConstants.ACTIONSCRIPT_PATH);
    }

    /**
     * @param name the virtual server name for action script retrieval
     * @throws StingrayRestClientException
     */
    public File getActionScript(String name) throws StingrayRestClientException {
        return getItem(name, File.class, ClientConstants.ACTIONSCRIPT_PATH, MediaType.APPLICATION_OCTET_STREAM_TYPE);
    }

    /**
     * @param name       The virtual server name related to the actionScript
     * @param actionScript The actionScript object used to create a Stingray Action Script
     * @return The configured Action Script object
     * @throws StingrayRestClientException
     */
    public File createActionScript(String name, File actionScript) throws StingrayRestClientException {
        return createItem(name, File.class, ClientConstants.ACTIONSCRIPT_PATH, actionScript, MediaType.APPLICATION_OCTET_STREAM_TYPE);
    }


    /**
     * @param name       The virtual server name related to the action script
     * @param actionScript The action script object used to create a Stingray action script
     * @return The configured action script object
     * @throws StingrayRestClientException
     */
    public File updateActionScript(String name, File actionScript) throws StingrayRestClientException {
        return updateItem(name, File.class, ClientConstants.ACTIONSCRIPT_PATH, actionScript, MediaType.APPLICATION_OCTET_STREAM_TYPE);
    }

    /**
     * @param name The virtual server name related to the action script
     * @throws StingrayRestClientException
     */
    public Boolean deleteActionScript(String name) throws StingrayRestClientException {
        return deleteItem(name, ClientConstants.ACTIONSCRIPT_PATH);
    }

    /**
     * @return the generic list for bandwidths providing the name and the endpoint for a specific request
     * @throws StingrayRestClientException
     */
    public Children getBandwidths() throws StingrayRestClientException {
        return getItems(ClientConstants.BANDWIDTH_PATH);
    }

    /**
     * @param name the virtual server name for bandwidth retrieval
     * @throws StingrayRestClientException
     */
    public Bandwidth getBandwidth(String name) throws StingrayRestClientException {
        return getItem(name, Bandwidth.class, ClientConstants.BANDWIDTH_PATH);
    }

    /**
     * @param name    The virtual server name related to the bandwidth
     * @param bandwidth The bandwidth object used to create a Stingray bandwidth
     * @return The configured Bandwidth object
     * @throws StingrayRestClientException
     */
    public Bandwidth createBandwidth(String name, Bandwidth bandwidth) throws StingrayRestClientException {
        return createItem(name, Bandwidth.class, ClientConstants.BANDWIDTH_PATH, bandwidth);
    }


    /**
     * @param name    The virtual server name related to the bandwidth
     * @param bandwidth The bandwidth object used to create a Stingray bandwidth
     * @return The configured bandwidth object
     * @throws StingrayRestClientException
     */
    public Bandwidth updateBandwidth(String name, Bandwidth bandwidth) throws StingrayRestClientException {
        return updateItem(name, Bandwidth.class, ClientConstants.BANDWIDTH_PATH, bandwidth);
    }

    /**
     * @param name The virtual server name related to the bandwidth
     * @throws StingrayRestClientException
     */
    public Boolean deleteBandwidth(String name) throws StingrayRestClientException {
        return deleteItem(name, ClientConstants.BANDWIDTH_PATH);
    }


    /**
     * @return the generic list for extra files providing the name and the endpoint for a specific request
     * @throws StingrayRestClientException
     */
    public Children getExtraFiles() throws StingrayRestClientException {
        return getItems(ClientConstants.EXTRAFILE_PATH);
    }

    /**
     * @param fileName the virtual server name for extra file retrieval
     * @throws StingrayRestClientException
     * @return File
     */
    public File getExtraFile(String fileName) throws StingrayRestClientException {
        return getExtraFile(fileName, MediaType.APPLICATION_OCTET_STREAM_TYPE);
    }

    /**
     *
     * @param fileName
     * @param cType
     * @return File
     * @throws StingrayRestClientException
     */
    public File getExtraFile(String fileName, MediaType cType) throws StingrayRestClientException {
        return getItem(fileName, File.class, ClientConstants.EXTRAFILE_PATH, cType);
    }

    /**
     * @param fileName    The virtual server name related to the extra file
     * @param extraFile The extra file object used to create a Stingray extra file
     * @return The configured ExtraFile object
     * @throws StingrayRestClientException
     */
    public File createExtraFile(String fileName, File extraFile) throws StingrayRestClientException {
        return createExtraFile(fileName, extraFile, MediaType.APPLICATION_OCTET_STREAM_TYPE);
    }

    /**
     *
     * @param name
     * @param extraFile
     * @param cType
     * @return File
     * @throws StingrayRestClientException
     */
    public File createExtraFile(String name, File extraFile, MediaType cType) throws StingrayRestClientException {
        return createItem(name, File.class, ClientConstants.EXTRAFILE_PATH, extraFile, cType);
    }


    /**
     * @param fileName    The virtual server name related to the extra file
     * @param extraFile The extra file object used to create a Stingray extra files
     * @return File
     * @throws StingrayRestClientException
     */
    public File updateExtraFile(String fileName, File extraFile) throws StingrayRestClientException {
        return updateExtraFile(fileName, extraFile, MediaType.APPLICATION_OCTET_STREAM_TYPE);
    }

    /**
     *
     * @param fileName
     * @param extraFile
     * @param cType
     * @return File
     * @throws StingrayRestClientException
     */
    public File updateExtraFile(String fileName, File extraFile, MediaType cType) throws StingrayRestClientException {
        return updateItem(fileName, File.class, ClientConstants.EXTRAFILE_PATH, extraFile, cType);
    }

    /**
     * @param name The virtual server name related to the extra file
     * @throws StingrayRestClientException
     */
    public Boolean deleteExtraFile(String name) throws StingrayRestClientException {
        return deleteItem(name, ClientConstants.EXTRAFILE_PATH);
    }

    /**
     * @return the generic list for global load balancers providing the name and the endpoint for a specific request
     * @throws StingrayRestClientException
     */
    public Children getGlbs() throws StingrayRestClientException {
        return getItems(ClientConstants.GLB_PATH);
    }

    /**
     * @param name the virtual server name for global load balancing retrieval
     * @throws StingrayRestClientException
     */
    public GlobalLoadBalancing getGlb(String name) throws StingrayRestClientException {
        return getItem(name, GlobalLoadBalancing.class, ClientConstants.GLB_PATH);
    }

    /**
     * @param name              The virtual server name related to the Glb
     * @param globalLoadBalancing The global load balancing object used to create a Stingray global load balancer
     * @return The configured ExtraFile object
     * @throws StingrayRestClientException
     */
    public GlobalLoadBalancing createGlb(String name, GlobalLoadBalancing globalLoadBalancing) throws StingrayRestClientException {
        return createItem(name, GlobalLoadBalancing.class, ClientConstants.GLB_PATH, globalLoadBalancing);
    }


    /**
     * @param name              The virtual server name related to the Glb
     * @param globalLoadBalancing The global load balancing object used to create a Stingray global load balancer
     * @return The configured global load balancing object
     * @throws StingrayRestClientException
     */
    public GlobalLoadBalancing updateGlb(String name, GlobalLoadBalancing globalLoadBalancing) throws StingrayRestClientException {
        return updateItem(name, GlobalLoadBalancing.class, ClientConstants.GLB_PATH, globalLoadBalancing);

    }

    /**
     * @param name The virtual server name related to the global load balancing
     * @throws StingrayRestClientException
     */
    public Boolean deleteGlb(String name) throws StingrayRestClientException {
        return deleteItem(name, ClientConstants.GLB_PATH);
    }


    /**
     * @return the generic list for locations providing the name and the endpoint for a specific request
     * @throws StingrayRestClientException
     */
    public Children getLocations() throws StingrayRestClientException {
        return getItems(ClientConstants.LOCATION_PATH);
    }

    /**
     * @param name the virtual server name for extra file retrieval
     * @throws StingrayRestClientException
     */
    public Location getLocation(String name) throws StingrayRestClientException {
        return getItem(name, Location.class, ClientConstants.LOCATION_PATH);
    }

    /**
     * @param name   The virtual server name related to the extra file
     * @param location The location object used to create a Stingray location
     * @return The configured Location object
     * @throws StingrayRestClientException
     */
    public Location createLocation(String name, Location location) throws StingrayRestClientException {
        return createItem(name, Location.class, ClientConstants.LOCATION_PATH, location);
    }


    /**
     * @param name   The virtual server name related to the location
     * @param location The extra file object used to create a Stingray locations
     * @return The configured Location object
     * @throws StingrayRestClientException
     */
    public Location updateLocation(String name, Location location) throws StingrayRestClientException {
        return updateItem(name, Location.class, ClientConstants.LOCATION_PATH, location);
    }

    /**
     * @param name The virtual server name related to the location
     * @throws StingrayRestClientException
     */
    public Boolean deleteLocation(String name) throws StingrayRestClientException {
        return deleteItem(name, ClientConstants.LOCATION_PATH);
    }


    /**
     * @return the generic list for extra files providing the name and the endpoint for a specific request
     * @throws StingrayRestClientException
     */
    public Children getMonitors() throws StingrayRestClientException {
        return getItems(ClientConstants.MONITOR_PATH);
    }

    /**
     * @param name the virtual server name for monitor retrieval
     * @throws StingrayRestClientException
     */
    public Monitor getMonitor(String name) throws StingrayRestClientException {
        return getItem(name, Monitor.class, ClientConstants.MONITOR_PATH);
    }

    /**
     * @param name  The virtual server name related to the monitor
     * @param monitor The monitor object used to create a Stingray monitor
     * @return The configured Monitor object
     * @throws StingrayRestClientException
     */
    public Monitor createMonitor(String name, Monitor monitor) throws StingrayRestClientException {
        return createItem(name, Monitor.class, ClientConstants.MONITOR_PATH, monitor);
    }


    /**
     * @param name  The virtual server name related to the monitor
     * @param monitor The monitor object used to create a Stingray monitors
     * @return The configured Monitor object
     * @throws StingrayRestClientException
     */
    public Monitor updateMonitor(String name, Monitor monitor) throws StingrayRestClientException {
        return updateItem(name, Monitor.class, ClientConstants.MONITOR_PATH, monitor);
    }

    /**
     * @param name The virtual server name related to the monitor
     * @throws StingrayRestClientException
     */
    public Boolean deleteMonitor(String name) throws StingrayRestClientException {
        return deleteItem(name, ClientConstants.MONITOR_PATH);
    }


    /**
     * @return the generic list for monitor scripts providing the name and the endpoint for a specific request
     * @throws StingrayRestClientException
     */
    public Children getMonitorScripts() throws StingrayRestClientException {
        return getItems(ClientConstants.MONITORSCRIPT_PATH);
    }

    /**
     * @param fileName the virtual server name for monitor script retrieval
     * @throws StingrayRestClientException
     */
    public File getMonitorScript(String fileName) throws StingrayRestClientException {
        return getItem(fileName, File.class, ClientConstants.MONITORSCRIPT_PATH, MediaType.APPLICATION_OCTET_STREAM_TYPE);
    }

    /**
     * @param fileName        The virtual server name related to the monitor script
     * @param monitorScript The monitor script object used to create a Stingray monitor script
     * @return The configured MonitorScript object
     * @throws StingrayRestClientException
     */
    public File createMonitorScript(String fileName, File monitorScript) throws StingrayRestClientException {
        return createItem(fileName, File.class, ClientConstants.MONITORSCRIPT_PATH, monitorScript, MediaType.APPLICATION_OCTET_STREAM_TYPE);
    }


    /**
     * @param fileName        The virtual server name related to the monitor script
     * @param monitorScript The monitor script object used to create a Stingray monitor scripts
     * @return The configured MonitorScript object
     * @throws StingrayRestClientException
     */
    public File updateMonitorScript(String fileName, File monitorScript) throws StingrayRestClientException {
        return updateItem(fileName, File.class, ClientConstants.MONITORSCRIPT_PATH, monitorScript, MediaType.APPLICATION_OCTET_STREAM_TYPE);
    }

    /**
     * @param fileName The virtual server name related to the monitor script
     * @throws StingrayRestClientException
     */
    public Boolean deleteMonitorScript(String fileName) throws StingrayRestClientException {
        return deleteItem(fileName, ClientConstants.MONITORSCRIPT_PATH);
    }


    /**
     * @return the generic list for persistences providing the name and the endpoint for a specific request
     * @throws StingrayRestClientException
     */
    public Children getPersistences() throws StingrayRestClientException {
        return getItems(ClientConstants.PERSISTENCE_PATH);
    }

    /**
     * @param name the virtual server name for persistence retrieval
     * @throws StingrayRestClientException
     */
    public Persistence getPersistence(String name) throws StingrayRestClientException {
        return getItem(name, Persistence.class, ClientConstants.PERSISTENCE_PATH);
    }

    /**
     * @param name      The virtual server name related to the extra file
     * @param persistence The persistence object used to create a Stingray persistence
     * @return The configured Persistence object
     * @throws StingrayRestClientException
     */
    public Persistence createPersistence(String name, Persistence persistence) throws StingrayRestClientException {
        return createItem(name, Persistence.class, ClientConstants.PERSISTENCE_PATH, persistence);
    }


    /**
     * @param name      The virtual server name related to the persistence
     * @param persistence The persistence object used to create a Stingray persistence
     * @return The configured Persistence object
     * @throws StingrayRestClientException
     */
    public Persistence updatePersistence(String name, Persistence persistence) throws StingrayRestClientException {
        return updateItem(name, Persistence.class, ClientConstants.PERSISTENCE_PATH, persistence);
    }

    /**
     * @param name The virtual server name related to the persistence
     * @throws StingrayRestClientException
     */
    public Boolean deletePersistence(String name) throws StingrayRestClientException {
        return deleteItem(name, ClientConstants.PERSISTENCE_PATH);
    }


    /**
     * @return the generic list for protections providing the name and the endpoint for a specific request
     * @throws StingrayRestClientException
     */
    public Children getProtections() throws StingrayRestClientException {
        return getItems(ClientConstants.PROTECTION_PATH);
    }

    /**
     * @param name the virtual server name for protection retrieval
     * @throws StingrayRestClientException
     */
    public Protection getProtection(String name) throws StingrayRestClientException {
        return getItem(name, Protection.class, ClientConstants.PROTECTION_PATH);
    }

    /**
     * @param name     The virtual server name related to the protection
     * @param protection The protection object used to create a Stingray protection
     * @return The configured Protection object
     * @throws StingrayRestClientException
     */
    public Protection createProtection(String name, Protection protection) throws StingrayRestClientException {
        return createItem(name, Protection.class, ClientConstants.PROTECTION_PATH, protection);
    }


    /**
     * @param name     The virtual server name related to the bandwidth
     * @param protection The protection object used to create a Stingray protections
     * @return The configured Protection object
     * @throws StingrayRestClientException
     */
    public Protection updateProtection(String name, Protection protection) throws StingrayRestClientException {
        return updateItem(name, Protection.class, ClientConstants.PROTECTION_PATH, protection);
    }

    /**
     * @param name The virtual server name related to the extra file
     * @throws StingrayRestClientException
     */
    public Boolean deleteProtection(String name) throws StingrayRestClientException {
        return deleteItem(name, ClientConstants.PROTECTION_PATH);
    }


    /**
     * @return the generic list for rates providing the name and the endpoint for a specific request
     * @throws StingrayRestClientException
     */
    public Children getRates() throws StingrayRestClientException {
        return getItems(ClientConstants.RATE_PATH);
    }

    /**
     * @param name the virtual server name for rate retrieval
     * @throws StingrayRestClientException
     */
    public Rate getRate(String name) throws StingrayRestClientException {
        return getItem(name, Rate.class, ClientConstants.RATE_PATH);
    }

    /**
     * @param name The virtual server name related to the rate
     * @param rate   The rate object used to create a Stingray rate
     * @return The configured Rate object
     * @throws StingrayRestClientException
     */
    public Rate createRate(String name, Rate rate) throws StingrayRestClientException {
        return createItem(name, Rate.class, ClientConstants.RATE_PATH, rate);
    }


    /**
     * @param name The virtual server name related to the rate
     * @param rate   The rate object used to create a Stingray rates
     * @return The configured Rate object
     * @throws StingrayRestClientException
     */
    public Rate updateRate(String name, Rate rate) throws StingrayRestClientException {
        return createItem(name, Rate.class, ClientConstants.RATE_PATH, rate);
    }

    /**
     * @param name The virtual server name related to the rate
     * @throws StingrayRestClientException
     */
    public Boolean deleteRate(String name) throws StingrayRestClientException {
        return deleteItem(name, ClientConstants.RATE_PATH);
    }

    /**
     * @return the generic list for cacrls providing the name and the endpoint for a specific request
     * @throws StingrayRestClientException
     */
    public Children getCacrls() throws StingrayRestClientException {
        return getItems(ClientConstants.CACRL_PATH);
    }

    /**
     * @param fileName the virtual server name for cacrl retrieval
     * @throws StingrayRestClientException
     */
    public File getCacrl(String fileName) throws StingrayRestClientException {
        return getItem(fileName, File.class, ClientConstants.CACRL_PATH, MediaType.APPLICATION_OCTET_STREAM_TYPE);
    }

    /**
     * @param fileName The virtual server name related to the cacrl
     * @param cacrl  The cacrl object used to create a Stingray cacrl
     * @return The configured Cacrl object
     * @throws StingrayRestClientException
     */
    public File createCacrl(String fileName, File cacrl) throws StingrayRestClientException {
        return createItem(fileName, File.class, ClientConstants.CACRL_PATH, cacrl, MediaType.APPLICATION_OCTET_STREAM_TYPE);
    }


    /**
     * @param fileName The virtual server name related to the cacrl
     * @param cacrl  The cacrl object used to create a Stingray cacrl
     * @return The configured Cacrl object
     * @throws StingrayRestClientException
     */
    public File updateCacrl(String fileName, File cacrl) throws StingrayRestClientException {
        return updateItem(fileName, File.class, ClientConstants.CACRL_PATH, cacrl, MediaType.APPLICATION_OCTET_STREAM_TYPE);
    }

    /**
     * @param fileName The virtual server name related to the cacrl
     * @throws StingrayRestClientException
     */
    public Boolean deleteCacrl(String fileName) throws StingrayRestClientException {
        return deleteItem(fileName, ClientConstants.CACRL_PATH);
    }


    /**
     * @return the generic list for client keypairs providing the name and the endpoint for a specific request
     * @throws StingrayRestClientException
     */
    public Children getClientKeypairs() throws StingrayRestClientException {
        return getItems(ClientConstants.CLIENTKEYPAIR_PATH);
    }

    /**
     * @param name the virtual server name for client keypair retrieval
     * @throws StingrayRestClientException
     */
    public ClientKeypair getClientKeypair(String name) throws StingrayRestClientException {
        return getItem(name, ClientKeypair.class, ClientConstants.CLIENTKEYPAIR_PATH);
    }

    /**
     * @param name        The virtual server name related to the client keypair
     * @param clientKeypair The client keypair object used to create a Stingray client keypair
     * @return The configured ClientKeypair object
     * @throws StingrayRestClientException
     */
    public ClientKeypair createClientKeypair(String name, ClientKeypair clientKeypair) throws StingrayRestClientException {
        return createItem(name, ClientKeypair.class, ClientConstants.CLIENTKEYPAIR_PATH, clientKeypair);
    }


    /**
     * @param name        The virtual server name related to the clientkeypair
     * @param clientKeypair The client keypair object used to create a Stingray client keypairs
     * @return The configured ClientKeypair object
     * @throws StingrayRestClientException
     */
    public ClientKeypair updateClientKeypair(String name, ClientKeypair clientKeypair) throws StingrayRestClientException {
        return createItem(name, ClientKeypair.class, ClientConstants.CLIENTKEYPAIR_PATH, clientKeypair);
    }

    /**
     * @param name The virtual server name related to the client keypair
     * @throws StingrayRestClientException
     */
    public Boolean deleteClientKeypair(String name) throws StingrayRestClientException {
        return deleteItem(name, ClientConstants.CLIENTKEYPAIR_PATH);
    }

    /**
     * @return the generic list for keypairs providing the name and the endpoint for a specific request
     * @throws StingrayRestClientException
     */
    public Children getKeypairs() throws StingrayRestClientException {
        return getItems(ClientConstants.KEYPAIR_PATH);
    }

    /**
     * @param name the virtual server name for keypair retrieval
     * @throws StingrayRestClientException
     */
    public Keypair getKeypair(String name) throws StingrayRestClientException {
        return getItem(name, Keypair.class, ClientConstants.KEYPAIR_PATH);
    }

    /**
     * @param name  The virtual server name related to the keypair
     * @param keypair The keypair object used to create a Stingray keypair
     * @return The configured Keypair object
     * @throws StingrayRestClientException
     */
    public Keypair createKeypair(String name, Keypair keypair) throws StingrayRestClientException {
        return createItem(name, Keypair.class, ClientConstants.KEYPAIR_PATH, keypair);
    }


    /**
     * @param name  The virtual server name related to the keypair
     * @param keypair The keypair object used to create a Stingray keypairs
     * @return The configured Keypair object
     * @throws StingrayRestClientException
     */
    public Keypair updateKeypair(String name, Keypair keypair) throws StingrayRestClientException {
        return createItem(name, Keypair.class, ClientConstants.KEYPAIR_PATH, keypair);
    }

    /**
     * @param name The virtual server name related to the keypair
     * @throws StingrayRestClientException
     */
    public Boolean deleteKeypair(String name) throws StingrayRestClientException {
        return deleteItem(name, ClientConstants.KEYPAIR_PATH);
    }


    /**
     * @return the generic list for traffic managers providing the name and the endpoint for a specific request
     * @throws StingrayRestClientException
     */
    public Children getTrafficManagers() throws StingrayRestClientException {
        return getItems(ClientConstants.TRAFFICMANAGER_PATH);
    }

    /**
     * @param name the virtual server name for rate retrieval
     * @throws StingrayRestClientException
     */
    public TrafficManager getTrafficManager(String name) throws StingrayRestClientException {
        return getItem(name, TrafficManager.class, ClientConstants.TRAFFICMANAGER_PATH);
    }

    /**
     * @param name         The virtual server name related to the traffic manager
     * @param trafficManager The traffic manager object used to create a Stingray traffic manager
     * @return The configured TrafficManager object
     * @throws StingrayRestClientException
     */
    public TrafficManager createTrafficManager(String name, TrafficManager trafficManager) throws StingrayRestClientException {
        return createItem(name, TrafficManager.class, ClientConstants.TRAFFICMANAGER_PATH, trafficManager);
    }


    /**
     * @param name         The virtual server name related to the traffic manager
     * @param trafficManager The traffic manager object used to create a Stingray traffic manager
     * @return The configured TrafficManager object
     * @throws StingrayRestClientException
     */
    public TrafficManager updateTrafficManager(String name, TrafficManager trafficManager) throws StingrayRestClientException {
        return createItem(name, TrafficManager.class, ClientConstants.TRAFFICMANAGER_PATH, trafficManager);
    }

    /**
     * @param name The virtual server name related to the rate
     * @throws StingrayRestClientException
     */
    public Boolean deleteTrafficManager(String name) throws StingrayRestClientException {
        return deleteItem(name, ClientConstants.TRAFFICMANAGER_PATH);
    }

    /**
     * @return the generic list for trafficscripts providing the name and the endpoint for a specific request
     * @throws StingrayRestClientException
     */
    public Children getTrafficscripts() throws StingrayRestClientException {
        return getItems(ClientConstants.TRAFFICSCRIPT_PATH);
    }

    /**
     * @param fileName the virtual server name for trafficscript retrieval
     * @throws StingrayRestClientException
     */
    public File getTraffiscript(String fileName) throws StingrayRestClientException {
        return getItem(fileName, File.class, ClientConstants.TRAFFICSCRIPT_PATH, MediaType.APPLICATION_OCTET_STREAM_TYPE);
    }

    /**
     * @param fileName        The virtual server name related to the trafficscript
     * @param trafficscript The rate object used to create a Stingray trafficscript
     * @return The configured Trafficscript object
     * @throws StingrayRestClientException
     */
    public File createTrafficscript(String fileName, File trafficscript) throws StingrayRestClientException {
        return createItem(fileName, File.class, ClientConstants.TRAFFICSCRIPT_PATH, trafficscript, MediaType.APPLICATION_OCTET_STREAM_TYPE);
    }


    /**
     * @param name        The virtual server name related to the trafficscript
     * @param trafficscript The trafficscript object used to create a Stingray trafficscript
     * @return The configured Trafficscript object
     * @throws StingrayRestClientException
     */
    public File updateTrafficScript(String name, File trafficscript) throws StingrayRestClientException {
        return createItem(name, File.class, ClientConstants.TRAFFICSCRIPT_PATH, trafficscript, MediaType.APPLICATION_OCTET_STREAM_TYPE);
    }

    /**
     * @param name The virtual server name related to the trafficscript
     * @throws StingrayRestClientException
     */
    public Boolean deleteTrafficscript(String name) throws StingrayRestClientException {
        return deleteItem(name, ClientConstants.TRAFFICSCRIPT_PATH);
    }

    /**
     * @return the generic list for TrafficIps providing the name and the endpoint for a specific request
     * @throws StingrayRestClientException
     */
    public Children getTrafficIps() throws StingrayRestClientException {
        return getItems(ClientConstants.IP_PATH);
    }

    /**
     * @param name the virtual server name for rate retrieval
     * @throws StingrayRestClientException
     */
    public TrafficIp getTrafficIp(String name) throws StingrayRestClientException {
        return getItem(name, TrafficIp.class, ClientConstants.IP_PATH);
    }

    /**
     * @param name    The virtual server name related to the Traffic Ip
     * @param trafficIp The rate object used to create a Stingray Traffic Ip
     * @return The configured TrafficIp object
     * @throws StingrayRestClientException
     */
    public TrafficIp createTrafficIp(String name, TrafficIp trafficIp) throws StingrayRestClientException {
        return createItem(name, TrafficIp.class, ClientConstants.IP_PATH, trafficIp);
    }


    /**
     * @param name    The virtual server name related to the Traffic Ip
     * @param trafficIp The Traffic Ip object used to create a Stingray Traffic Ip
     * @return The configured TrafficIp object
     * @throws StingrayRestClientException
     */
    public TrafficIp updateTrafficIp(String name, TrafficIp trafficIp) throws StingrayRestClientException {
        return createItem(name, TrafficIp.class, ClientConstants.IP_PATH, trafficIp);
    }

    /**
     * @param name The virtual server name related to the TrafficIp
     * @throws StingrayRestClientException
     */
    public Boolean deleteTrafficIp(String name) throws StingrayRestClientException {
        return deleteItem(name, ClientConstants.IP_PATH);
    }
}
