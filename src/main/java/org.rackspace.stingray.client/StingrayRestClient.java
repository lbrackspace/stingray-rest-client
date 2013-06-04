package org.rackspace.stingray.client;

import com.sun.jersey.api.client.ClientResponse;
import org.rackspace.stingray.client.action.ActionScript;
import org.rackspace.stingray.client.bandwidth.Bandwidth;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.extra.file.ExtraFile;
import org.rackspace.stingray.client.glb.GlobalLoadBalancing;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.location.Location;
import org.rackspace.stingray.client.manager.RequestManager;
import org.rackspace.stingray.client.manager.impl.RequestManagerImpl;
import org.rackspace.stingray.client.monitor.Monitor;
import org.rackspace.stingray.client.monitor.MonitorScript;
import org.rackspace.stingray.client.persistence.Persistence;
import org.rackspace.stingray.client.pool.Pool;
import org.rackspace.stingray.client.protection.Protection;
import org.rackspace.stingray.client.rate.Rate;
import org.rackspace.stingray.client.util.ClientConstants;

import javax.xml.bind.annotation.XmlElementDecl;
import java.awt.*;

public class StingrayRestClient extends StingrayRestClientManager {

    private final RequestManager requestManager = new RequestManagerImpl();

    /**
     *
     * @throws StingrayRestClientException
     * @return the generic list for pools providing the name and the endpoint for a specific request
     */
    public Children getPools() throws StingrayRestClientException {
        return requestManager.retrieveList(endpoint, client, ClientConstants.POOL_PATH);
    }

    /**
     * @param vsName the virtual server name for pool retrieval
     * @throws StingrayRestClientException
     */
    public Pool retrievePool(String vsName) throws StingrayRestClientException {
        ClientResponse response = requestManager.retrieveItem(endpoint, client, ClientConstants.POOL_PATH + vsName);
        Pool pool = interpretResponse(response, Pool.class);
        return pool;
    }

    /**
     *
     * @param vsName The virtual server name related to the pool
     * @param pool The pool object used to create a Stingray Pool
     * @return The configured pool object
     * @throws StingrayRestClientException
     */
    public Pool createPool(String vsName, Pool pool) throws StingrayRestClientException {
        ClientResponse response = requestManager.createItem(endpoint, client, ClientConstants.POOL_PATH + vsName, pool);
        return interpretResponse(response, Pool.class);
    }

   /**
     *
     * @param vsName The virtual server name related to the pool
     * @param pool The pool object used to create a Stingray Pool
     * @return The configured pool object
     * @throws StingrayRestClientException
     */
    public Pool updatePool(String vsName, Pool pool) throws StingrayRestClientException {
        ClientResponse response = requestManager.updateItem(endpoint, client, ClientConstants.POOL_PATH + vsName, pool);
        return interpretResponse(response, Pool.class);
    }

    /**
     *
     * @param vsName The virtual server name related to the pool
     * @throws StingrayRestClientException
     */
    public void deletePool(String vsName) throws StingrayRestClientException {
        requestManager.deleteItem(endpoint, client, ClientConstants.POOL_PATH + vsName);
    }



    /**
     *
     * @throws StingrayRestClientException
     * @return the generic list for actionScripts providing the name and the endpoint for a specific request
     */
    public Children getActionScripts() throws StingrayRestClientException {
        return requestManager.retrieveList(endpoint, client, ClientConstants.ACTIONSCRIPT_PATH);
    }

    /**
     * @param vsName the virtual server name for action script retrieval
     * @throws StingrayRestClientException
     */
    public ActionScript retrievActionScript(String vsName) throws StingrayRestClientException {
        ClientResponse response = requestManager.retrieveItem(endpoint, client, ClientConstants.ACTIONSCRIPT_PATH + vsName);
        ActionScript actionScript = interpretResponse(response, ActionScript.class);
        return actionScript;
    }

    /**
     *
     * @param vsName The virtual server name related to the actionScript
     * @param actionScript The actionScript object used to create a Stingray Action Script
     * @return The configured Action Script object
     * @throws StingrayRestClientException
     */
    public ActionScript createActionScript(String vsName, ActionScript actionScript) throws StingrayRestClientException {
        ClientResponse response = requestManager.createItem(endpoint, client, ClientConstants.ACTIONSCRIPT_PATH + vsName, actionScript);
        return interpretResponse(response, ActionScript.class);
    }


    /**
     *
     * @param vsName The virtual server name related to the action script
     * @param actionScript The action script object used to create a Stingray action script
     * @return The configured action script object
     * @throws StingrayRestClientException
     */
    public ActionScript updateActionScript(String vsName, ActionScript actionScript) throws StingrayRestClientException {
        ClientResponse response = requestManager.updateItem(endpoint, client, ClientConstants.ACTIONSCRIPT_PATH + vsName, actionScript);
        return interpretResponse(response, ActionScript.class);
    }

    /**
     *
     * @param vsName The virtual server name related to the action script
     * @throws StingrayRestClientException
     */
    public void deleteActionScript(String vsName) throws StingrayRestClientException {
        requestManager.deleteItem(endpoint, client, ClientConstants.ACTIONSCRIPT_PATH + vsName);
    }

    /**
     *
     * @throws StingrayRestClientException
     * @return the generic list for bandwidths providing the name and the endpoint for a specific request
     */
    public Children getBandwidths() throws StingrayRestClientException {
        return requestManager.retrieveList(endpoint, client, ClientConstants.BANDWIDTH_PATH);
    }

    /**
     * @param vsName the virtual server name for bandwidth retrieval
     * @throws StingrayRestClientException
     */
    public Bandwidth retrieveBandwidth(String vsName) throws StingrayRestClientException {
        ClientResponse response = requestManager.retrieveItem(endpoint, client, ClientConstants.BANDWIDTH_PATH + vsName);
        Bandwidth bandwidth = interpretResponse(response, Bandwidth.class);
        return bandwidth;
    }

    /**
     *
     * @param vsName The virtual server name related to the bandwidth
     * @param bandwidth The bandwidth object used to create a Stingray bandwidth
     * @return The configured Bandwidth object
     * @throws StingrayRestClientException
     */
    public Bandwidth createBandwidth(String vsName, Bandwidth bandwidth) throws StingrayRestClientException {
        ClientResponse response = requestManager.createItem(endpoint, client, ClientConstants.BANDWIDTH_PATH + vsName, bandwidth);
        return interpretResponse(response, Bandwidth.class);
    }


    /**
     *
     * @param vsName The virtual server name related to the bandwidth
     * @param bandwidth The bandwidth object used to create a Stingray bandwidth
     * @return The configured bandwidth object
     * @throws StingrayRestClientException
     */
    public Bandwidth updateBandwidth(String vsName, Bandwidth bandwidth) throws StingrayRestClientException {
        ClientResponse response = requestManager.updateItem(endpoint, client, ClientConstants.BANDWIDTH_PATH + vsName, bandwidth);
        return interpretResponse(response, Bandwidth.class);
    }

    /**
     *
     * @param vsName The virtual server name related to the bandwidth
     * @throws StingrayRestClientException
     */
    public void deleteBandwidth(String vsName) throws StingrayRestClientException {
        requestManager.deleteItem(endpoint, client, ClientConstants.BANDWIDTH_PATH + vsName);
    }


     /**
     *
     * @throws StingrayRestClientException
     * @return the generic list for extra files providing the name and the endpoint for a specific request
     */
    public Children getExtraFiles() throws StingrayRestClientException {
        return requestManager.retrieveList(endpoint, client, ClientConstants.EXTRAFILE_PATH);
    }

    /**
     * @param vsName the virtual server name for extra file retrieval
     * @throws StingrayRestClientException
     */
    public ExtraFile retrieveExtraFile(String vsName) throws StingrayRestClientException {
        ClientResponse response = requestManager.retrieveItem(endpoint, client, ClientConstants.EXTRAFILE_PATH + vsName);
        ExtraFile extraFile = interpretResponse(response, ExtraFile.class);
        return extraFile;
    }

    /**
     *
     * @param vsName The virtual server name related to the extra file
     * @param extraFile The extra file object used to create a Stingray extra file
     * @return The configured ExtraFile object
     * @throws StingrayRestClientException
     */
    public ExtraFile createExtraFile(String vsName, ExtraFile extraFile) throws StingrayRestClientException {
        ClientResponse response = requestManager.createItem(endpoint, client, ClientConstants.EXTRAFILE_PATH + vsName, extraFile);
        return interpretResponse(response, ExtraFile.class);
    }


    /**
     *
     * @param vsName The virtual server name related to the extra file
     * @param extraFile The extra file object used to create a Stingray extra files
     * @return The configured extra file object
     * @throws StingrayRestClientException
     */
    public ExtraFile updateExtraFile(String vsName, ExtraFile extraFile) throws StingrayRestClientException {
        ClientResponse response = requestManager.updateItem(endpoint, client, ClientConstants.EXTRAFILE_PATH + vsName, extraFile);
        return interpretResponse(response, ExtraFile.class);
    }

    /**
     *
     * @param vsName The virtual server name related to the extra file
     * @throws StingrayRestClientException
     */
    public void deleteExtraFile(String vsName) throws StingrayRestClientException {
        requestManager.deleteItem(endpoint, client, ClientConstants.EXTRAFILE_PATH + vsName);
    }

    /**
     *
     * @throws StingrayRestClientException
     * @return the generic list for global load balancers providing the name and the endpoint for a specific request
     */
    public Children getGlbs() throws StingrayRestClientException {
        return requestManager.retrieveList(endpoint, client, ClientConstants.GLB_PATH);
    }

    /**
     * @param vsName the virtual server name for global load balancing retrieval
     * @throws StingrayRestClientException
     */
    public GlobalLoadBalancing retrieveGlb(String vsName) throws StingrayRestClientException {
        ClientResponse response = requestManager.retrieveItem(endpoint, client, ClientConstants.GLB_PATH + vsName);
        GlobalLoadBalancing globalLoadBalancing = interpretResponse(response, GlobalLoadBalancing.class);
        return globalLoadBalancing;
    }

    /**
     *
     * @param vsName The virtual server name related to the Glb
     * @param globalLoadBalancing The global load balancing object used to create a Stingray global load balancer
     * @return The configured ExtraFile object
     * @throws StingrayRestClientException
     */
    public GlobalLoadBalancing createGlb(String vsName, GlobalLoadBalancing globalLoadBalancing) throws StingrayRestClientException {
        ClientResponse response = requestManager.createItem(endpoint, client, ClientConstants.GLB_PATH + vsName, globalLoadBalancing);
        return interpretResponse(response, GlobalLoadBalancing.class);
    }


    /**
     *
     * @param vsName The virtual server name related to the Glb
     * @param globalLoadBalancing The global load balancing object used to create a Stingray global load balancer
     * @return The configured global load balancing object
     * @throws StingrayRestClientException
     */
    public GlobalLoadBalancing updateGlb(String vsName, GlobalLoadBalancing globalLoadBalancing) throws StingrayRestClientException {
        ClientResponse response = requestManager.updateItem(endpoint, client, ClientConstants.GLB_PATH + vsName, globalLoadBalancing);
        return interpretResponse(response, GlobalLoadBalancing.class);
    }

    /**
     *
     * @param vsName The virtual server name related to the global load balancing
     * @throws StingrayRestClientException
     */
    public void deleteGlb(String vsName) throws StingrayRestClientException {
        requestManager.deleteItem(endpoint, client, ClientConstants.GLB_PATH + vsName);
    }




    /**
     *
     * @throws StingrayRestClientException
     * @return the generic list for locations providing the name and the endpoint for a specific request
     */
    public Children getLocations() throws StingrayRestClientException {
        return requestManager.retrieveList(endpoint, client, ClientConstants.LOCATION_PATH);
    }

    /**
     * @param vsName the virtual server name for extra file retrieval
     * @throws StingrayRestClientException
     */
    public Location retrieveLocation(String vsName) throws StingrayRestClientException {
        ClientResponse response = requestManager.retrieveItem(endpoint, client, ClientConstants.LOCATION_PATH + vsName);
        Location location = interpretResponse(response, Location.class);
        return location;
    }

    /**
     *
     * @param vsName The virtual server name related to the extra file
     * @param location The location object used to create a Stingray location
     * @return The configured Location object
     * @throws StingrayRestClientException
     */
    public ExtraFile createLocation(String vsName, Location location) throws StingrayRestClientException {
        ClientResponse response = requestManager.createItem(endpoint, client, ClientConstants.LOCATION_PATH + vsName, location);
        return interpretResponse(response, ExtraFile.class);
    }


    /**
     *
     * @param vsName The virtual server name related to the location
     * @param location The extra file object used to create a Stingray locations
     * @return The configured Location object
     * @throws StingrayRestClientException
     */
    public Location updateLocation(String vsName, Location location) throws StingrayRestClientException {
        ClientResponse response = requestManager.updateItem(endpoint, client, ClientConstants.LOCATION_PATH + vsName, location);
        return interpretResponse(response, Location.class);
    }

    /**
     *
     * @param vsName The virtual server name related to the location
     * @throws StingrayRestClientException
     */
    public void deleteLocation(String vsName) throws StingrayRestClientException {
        requestManager.deleteItem(endpoint, client, ClientConstants.LOCATION_PATH + vsName);
    }



    /**
     *
     * @throws StingrayRestClientException
     * @return the generic list for extra files providing the name and the endpoint for a specific request
     */
    public Children getMonitors() throws StingrayRestClientException {
        return requestManager.retrieveList(endpoint, client, ClientConstants.MONITOR_PATH);
    }

    /**
     * @param vsName the virtual server name for monitor retrieval
     * @throws StingrayRestClientException
     */
    public Monitor retrieveMonitor(String vsName) throws StingrayRestClientException {
        ClientResponse response = requestManager.retrieveItem(endpoint, client, ClientConstants.MONITOR_PATH + vsName);
        Monitor monitor = interpretResponse(response, Monitor.class);
        return monitor;
    }

    /**
     *
     * @param vsName The virtual server name related to the monitor
     * @param monitor The monitor object used to create a Stingray monitor
     * @return The configured Monitor object
     * @throws StingrayRestClientException
     */
    public Monitor createMonitor(String vsName, Monitor monitor) throws StingrayRestClientException {
        ClientResponse response = requestManager.createItem(endpoint, client, ClientConstants.MONITOR_PATH + vsName, monitor);
        return interpretResponse(response, Monitor.class);
    }


    /**
     *
     * @param vsName The virtual server name related to the monitor
     * @param monitor The monitor object used to create a Stingray monitors
     * @return The configured Monitor object
     * @throws StingrayRestClientException
     */
    public Monitor updateMonitor(String vsName, Monitor monitor) throws StingrayRestClientException {
        ClientResponse response = requestManager.updateItem(endpoint, client, ClientConstants.MONITOR_PATH + vsName, monitor);
        return interpretResponse(response, Monitor.class);
    }

    /**
     *
     * @param vsName The virtual server name related to the monitor
     * @throws StingrayRestClientException
     */
    public void deleteMonitor(String vsName) throws StingrayRestClientException {
        requestManager.deleteItem(endpoint, client, ClientConstants.MONITOR_PATH + vsName);
    }




    /**
     *
     * @throws StingrayRestClientException
     * @return the generic list for monitor scripts providing the name and the endpoint for a specific request
     */
    public Children getMonitorScripts() throws StingrayRestClientException {
        return requestManager.retrieveList(endpoint, client, ClientConstants.MONITORSCRIPT_PATH);
    }

    /**
     * @param vsName the virtual server name for monitor script retrieval
     * @throws StingrayRestClientException
     */
    public MonitorScript retrieveMonitorScript(String vsName) throws StingrayRestClientException {
        ClientResponse response = requestManager.retrieveItem(endpoint, client, ClientConstants.MONITORSCRIPT_PATH + vsName);
        MonitorScript monitorScript = interpretResponse(response, MonitorScript.class);
        return monitorScript;
    }

    /**
     *
     * @param vsName The virtual server name related to the monitor script
     * @param monitorScript The monitor script object used to create a Stingray monitor script
     * @return The configured MonitorScript object
     * @throws StingrayRestClientException
     */
    public MonitorScript createMonitorScript(String vsName, MonitorScript monitorScript) throws StingrayRestClientException {
        ClientResponse response = requestManager.createItem(endpoint, client, ClientConstants.MONITORSCRIPT_PATH + vsName, monitorScript);
        return interpretResponse(response, MonitorScript.class);
    }


    /**
     *
     * @param vsName The virtual server name related to the monitor script
     * @param monitorScript The monitor script object used to create a Stingray monitor scripts
     * @return The configured MonitorScript object
     * @throws StingrayRestClientException
     */
    public MonitorScript updateMonitorScript(String vsName, MonitorScript monitorScript) throws StingrayRestClientException {
        ClientResponse response = requestManager.updateItem(endpoint, client, ClientConstants.MONITORSCRIPT_PATH + vsName, monitorScript);
        return interpretResponse(response, MonitorScript.class);
    }

    /**
     *
     * @param vsName The virtual server name related to the monitor script
     * @throws StingrayRestClientException
     */
    public void deleteMonitorScript(String vsName) throws StingrayRestClientException {
        requestManager.deleteItem(endpoint, client, ClientConstants.MONITORSCRIPT_PATH + vsName);
    }



    /**
     *
     * @throws StingrayRestClientException
     * @return the generic list for persistences providing the name and the endpoint for a specific request
     */
    public Children getPersistence() throws StingrayRestClientException {
        return requestManager.retrieveList(endpoint, client, ClientConstants.PERSISTENCE_PATH);
    }

    /**
     * @param vsName the virtual server name for persistence retrieval
     * @throws StingrayRestClientException
     */
    public Persistence retrievePersistence(String vsName) throws StingrayRestClientException {
        ClientResponse response = requestManager.retrieveItem(endpoint, client, ClientConstants.PERSISTENCE_PATH + vsName);
        Persistence persistence = interpretResponse(response, Persistence.class);
        return persistence;
    }

    /**
     *
     * @param vsName The virtual server name related to the extra file
     * @param persistence The persistence object used to create a Stingray persistence
     * @return The configured Persistence object
     * @throws StingrayRestClientException
     */
    public Persistence createPersistence(String vsName, Persistence persistence) throws StingrayRestClientException {
        ClientResponse response = requestManager.createItem(endpoint, client, ClientConstants.EXTRAFILE_PATH + vsName, persistence);
        return interpretResponse(response, Persistence.class);
    }


    /**
     *
     * @param vsName The virtual server name related to the persistence
     * @param persistence The persistence object used to create a Stingray persistence
     * @return The configured Persistence object
     * @throws StingrayRestClientException
     */
    public Persistence updatePersistence(String vsName, Persistence persistence) throws StingrayRestClientException {
        ClientResponse response = requestManager.updateItem(endpoint, client, ClientConstants.PERSISTENCE_PATH + vsName, persistence);
        return interpretResponse(response, Persistence.class);
    }

    /**
     *
     * @param vsName The virtual server name related to the persistence
     * @throws StingrayRestClientException
     */
    public void deletePersistence(String vsName) throws StingrayRestClientException {
        requestManager.deleteItem(endpoint, client, ClientConstants.PERSISTENCE_PATH + vsName);
    }


    /**
     *
     * @throws StingrayRestClientException
     * @return the generic list for protections providing the name and the endpoint for a specific request
     */
    public Children getProtections() throws StingrayRestClientException {
        return requestManager.retrieveList(endpoint, client, ClientConstants.PROTECTION_PATH);
    }

    /**
     * @param vsName the virtual server name for protection retrieval
     * @throws StingrayRestClientException
     */
    public Protection retrieveProtection(String vsName) throws StingrayRestClientException {
        ClientResponse response = requestManager.retrieveItem(endpoint, client, ClientConstants.PROTECTION_PATH + vsName);
        Protection protection = interpretResponse(response, Protection.class);
        return protection;
    }

    /**
     *
     * @param vsName The virtual server name related to the protection
     * @param protection The protection object used to create a Stingray protection
     * @return The configured Protection object
     * @throws StingrayRestClientException
     */
    public Protection createProtection(String vsName, Protection protection) throws StingrayRestClientException {
        ClientResponse response = requestManager.createItem(endpoint, client, ClientConstants.EXTRAFILE_PATH + vsName, protection);
        return interpretResponse(response, Protection.class);
    }


    /**
     *
     * @param vsName The virtual server name related to the bandwidth
     * @param protection The protection object used to create a Stingray protections
     * @return The configured Protection object
     * @throws StingrayRestClientException
     */
    public Protection updateProtection(String vsName, Protection protection) throws StingrayRestClientException {
        ClientResponse response = requestManager.updateItem(endpoint, client, ClientConstants.PROTECTION_PATH + vsName, protection);
        return interpretResponse(response, Protection.class);
    }

    /**
     *
     * @param vsName The virtual server name related to the extra file
     * @throws StingrayRestClientException
     */
    public void deleteProtection(String vsName) throws StingrayRestClientException {
        requestManager.deleteItem(endpoint, client, ClientConstants.PROTECTION_PATH + vsName);
    }



                                           /**
     *
     * @throws StingrayRestClientException
     * @return the generic list for extra files providing the name and the endpoint for a specific request
     */
    public Children getExtraFiles() throws StingrayRestClientException {
        return requestManager.retrieveList(endpoint, client, ClientConstants.EXTRAFILE_PATH);
    }

    /**
     * @param vsName the virtual server name for extra file retrieval
     * @throws StingrayRestClientException
     */
    public ExtraFile retrieveExtraFile(String vsName) throws StingrayRestClientException {
        ClientResponse response = requestManager.retrieveItem(endpoint, client, ClientConstants.EXTRAFILE_PATH + vsName);
        ExtraFile extraFile = interpretResponse(response, ExtraFile.class);
        return extraFile;
    }

    /**
     *
     * @param vsName The virtual server name related to the extra file
     * @param extraFile The extra file object used to create a Stingray extra file
     * @return The configured ExtraFile object
     * @throws StingrayRestClientException
     */
    public Rate createRate(String vsName, Rate rate) throws StingrayRestClientException {
        ClientResponse response = requestManager.createItem(endpoint, client, ClientConstants.RATE_PATH + vsName, rate);
        return interpretResponse(response, Rate.class);
    }


    /**
     *
     * @param vsName The virtual server name related to the rate
     * @param extraFile The rate object used to create a Stingray rates
     * @return The configured Rate object
     * @throws StingrayRestClientException
     */
    public Rate updateRate(String vsName, ExtraFile extraFile) throws StingrayRestClientException {
        ClientResponse response = requestManager.updateItem(endpoint, client, ClientConstants.RATE_PATH + vsName, extraFile);
        return interpretResponse(response, Rate.class);
    }

    /**
     *
     * @param vsName The virtual server name related to the rate
     * @throws StingrayRestClientException
     */
    public void deleteRate(String vsName) throws StingrayRestClientException {
        requestManager.deleteItem(endpoint, client, ClientConstants.RATE_PATH + vsName);
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

    /**
     *
     * @param response
     * @param clazz
     * @param <T>
     * @return
     */
    private <T> T interpretResponse(ClientResponse response, java.lang.Class<T> clazz) {
        return response.getEntity(clazz);
    }
}
