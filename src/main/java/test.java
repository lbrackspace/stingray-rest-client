import org.rackspace.stingray.client.Basic;
import org.rackspace.stingray.client.Pool;
import org.rackspace.stingray.client.Properties;

import java.util.ArrayList;

public class test {
    Pool pool = new Pool();

    void manipObject() {
        Properties properties = new Properties();
        Basic nodeBasic = new Basic();
        nodeBasic.setNodes(new ArrayList<String>(Integer.parseInt("10.1.1.1")));
        properties.setBasic(nodeBasic);
        pool.setProperties(properties);
    }
}
