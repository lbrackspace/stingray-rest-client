package org.rackspace.stingray.client.manager.util;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.client.urlconnection.HTTPSProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.rackspace.stingray.client.pool.Pool;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

public class StingrayRestClientUtil {
    private static final Log LOG = LogFactory.getLog(StingrayRestClientUtil.class);

    public static class ClientHelper {

        public static ClientConfig configureClient() {
            TrustManager[] certs = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType)
                                throws CertificateException {
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType)
                                throws CertificateException {
                        }
                    }
            };
            SSLContext ctx = null;
            try {
                ctx = SSLContext.getInstance("TLS");
                ctx.init(null, certs, new SecureRandom());
            } catch (java.security.GeneralSecurityException ex) {
            }
            HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());

            ClientConfig config = new DefaultClientConfig();
            config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
            config.getClasses().add(JacksonJsonProvider.class);

            try {
                config.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, new HTTPSProperties(
                        new HostnameVerifier() {
                            @Override
                            public boolean verify(String hostname, SSLSession session) {
                                return true;
                            }
                        },
                        ctx
                ));
            } catch (Exception e) {
            }
            return config;
        }

        public static Client createClient() {
            return Client.create(ClientHelper.configureClient());
        }


        public static List<Pool> parsePools(String poolsString) {
            List<Pool> pools = new ArrayList<Pool>();

            String[] parsedPools = poolsString.split("[\":{}\\[\\] ]+");
            for (String vals : parsedPools) {
                Pool pool = new Pool();
                pools.add(pool);
            }
            return new ArrayList<Pool>();
        }
    }
}
