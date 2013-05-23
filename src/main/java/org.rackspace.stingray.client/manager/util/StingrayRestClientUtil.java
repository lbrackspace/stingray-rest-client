package org.rackspace.stingray.client.manager.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.params.ClientParamBean;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParamBean;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParamBean;
import org.rackspace.stingray.client.StingrayRestClient;
import org.rackspace.stingray.client.manager.security.EasySSLSocketFactory;
import ru.hh.jersey.hchttpclient.ApacheHttpClient;
import ru.hh.jersey.hchttpclient.ApacheHttpClientHandler;

public class StingrayRestClientUtil {
    private static final Log LOG = LogFactory.getLog(StingrayRestClient.class);

    /**
     * Takes no params, builds a Client from jersey client using jersey-hc and ApacheHttpClient
     * using the values from the properties file...
     *
     * @return the configured ApacheHttpClient
     */
    public static ApacheHttpClient createHttpClient() throws Exception {
        HttpClient httpClient = null;

        try {
            //Set the BasicHttpParams()
            //HttpProtocolParamBean() will allow for Java Bean conventions to manipulate the Http protocol parameters of HttpParams()
            HttpParams params = new BasicHttpParams();
            HttpProtocolParamBean connParams = new HttpProtocolParamBean(params);
            connParams.setVersion(HttpVersion.HTTP_1_1);
            connParams.setUseExpectContinue(false);

            //Set the ConnmanagerParamBean()
            //ConnManagerParamBean() will allow for Java Bean conventions to manipulate the connection manager parameters of HttpParams()
            ConnManagerParamBean poolParams = new ConnManagerParamBean(params);

            //How many connections do we want open.
            poolParams.setMaxTotalConnections(300);

            //How long before we timeout
            poolParams.setTimeout(300);

            //Set the ClientParamBean()
            //ClientParamBean() will allow for Java Bean conventions to manipulate the client parameters of HttpParams()
            ClientParamBean clientParams = new ClientParamBean(params);

            //what is the max redirects we allow
            clientParams.setMaxRedirects(2);
            clientParams.setAllowCircularRedirects(true);
            clientParams.setRejectRelativeRedirect(false);
            clientParams.setHandleAuthentication(false);

            //Set the schemeRegistry
            SchemeRegistry schemata = new SchemeRegistry();

            //Service is HTTPS Only, uncomment if testing against non-secure service...
//        schemata.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            schemata.register(new Scheme("https", new EasySSLSocketFactory(), 443));

            //Used for multi-threaded requests, use commented line if you do not wish to multi-thread
//            ClientConnectionManager connManager = new SingleClientConnManager(params, schemata);  //Use for non threaded requests...
            ClientConnectionManager connManager = new ThreadSafeClientConnManager(params, schemata);

            //Sets the DefaultHttpClient with configured connection manager and params...
            httpClient = new DefaultHttpClient(connManager, params);
        } catch (Exception ex) {
            LOG.error("ERROR: ", ex);
            throw ex;
        }

        return new ApacheHttpClient(new ApacheHttpClientHandler(httpClient));
    }
}
