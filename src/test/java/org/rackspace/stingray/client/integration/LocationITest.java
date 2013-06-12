package org.rackspace.stingray.client.integration;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.exception.StingrayRestClientException;
import org.rackspace.stingray.client.list.Child;
import org.rackspace.stingray.client.list.Children;
import org.rackspace.stingray.client.location.Location;
import org.rackspace.stingray.client.location.LocationBasic;
import org.rackspace.stingray.client.location.LocationProperties;

public class LocationITest extends StingrayTestBase {
    StingrayRestClient client;
    Location location;
    LocationProperties locationProperties;
    LocationBasic locationBasic;
    String vsName;
    int locationId;


    /**
     * Initializes variables prior to test execution
     */
    @Before
    public void standUp() {
        client = new StingrayRestClient();
        location = new Location();
        locationProperties = new LocationProperties();
        locationBasic = new LocationBasic();
        locationId = 20;
        vsName = TESTNAME;
        locationBasic.setId(locationId);
        locationProperties.setBasic(locationBasic);
        location.setProperties(locationProperties);

    }

    /**
     * Tests the creation of a Location
     * Verifies using get and a comparison of content contained
     *
     * @throws StingrayRestClientException
     */
    @Test
    public void testCreateLocation() throws StingrayRestClientException {
        Location createdLocation = client.createLocation(vsName, location);
        Assert.assertNotNull(createdLocation);
        Assert.assertEquals(createdLocation, client.getLocation(vsName));
    }

    /**
     * Tests the updating of a Location
     * Verifies using a get and a comparison of content contained
     *
     * @throws StingrayRestClientException
     */
    @Test
    public void testUpdateLocation() throws StingrayRestClientException {
        int updateId = 33;
        location.getProperties().getBasic().setId(updateId);
        Location updatedLocation = client.updateLocation(vsName, location);
        Assert.assertEquals(updateId, (int) updatedLocation.getProperties().getBasic().getId());
    }


    /**
     * Tests the retrieval of a list of Locations
     * Retrieves a list of action scripts and checks its size
     *
     * @throws org.rackspace.stingray.client.exception.StingrayRestClientException
     *
     */
    @Test
    public void testGetListOfLocations() throws StingrayRestClientException {
        Children children = client.getLocations();
        Assert.assertTrue(children.getChildren().size() > 0);
    }

    /**
     * Tests the get function for an individual Location
     * Retrieves the specific Action Script created earlier
     *
     * @throws StingrayRestClientException
     */
    @Test
    public void testGetSpecificLocation() throws StingrayRestClientException {
        Location retrievedLocation = client.getLocation(vsName);
        Assert.assertNotNull(retrievedLocation);
    }

    /**
     * Tests the deletion of a Location
     * Checks return of the delete call, and throws an error
     *
     * @throws StingrayRestClientException
     */
    @Test
    public void testDeleteLocation() throws StingrayRestClientException {
        Boolean wasDeleted = client.deleteLocation(vsName);
        Assert.assertTrue(wasDeleted);

    }
}
