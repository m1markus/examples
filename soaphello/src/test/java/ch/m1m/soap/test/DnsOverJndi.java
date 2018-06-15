package ch.m1m.soap.test;

import org.junit.Test;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

import static org.junit.Assert.fail;

/* Dynamic Service resolution ... from consul, SkyDNS or coreDNS
 *
 */
public class DnsOverJndi {

    //@Test
    //@Ignore
    public void testDNSoverJNDI() throws NamingException {

        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.dns.DnsContextFactory");
        env.put(Context.PROVIDER_URL, "dns:");
        DirContext dnsCtx = new InitialDirContext(env);
        Attributes attrs = dnsCtx.getAttributes("_https._tcp.m1m.ch", new String[]{"SRV"});
    }


    @Test
    //@Ignore
    public void testSRVrecordOverDNS() throws NamingException {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.dns.DnsContextFactory");
        //env.put(Context.PROVIDER_URL, "dns://ns2.spotify.com/spotify.com");
        env.put(Context.PROVIDER_URL, "dns:");
        DirContext dnsCtx = new InitialDirContext(env);
        Attributes attrs = null;

        if (dnsCtx != null) {

            attrs = dnsCtx.getAttributes("_spotify-mac-client._tcp.spotify.com", new String[]{"SRV"});

            if (attrs != null) {
                final Attribute prioAttr = attrs.get("priority");
                final Attribute weightAttr = attrs.get("weight");
                final Attribute portAttr = attrs.get("port");
                final Attribute srvAttr = attrs.get("srv");

                String hostName = srvAttr.get().toString();
                //String port = portAttr.get().toString();

                System.out.println("TRACE resolved to: " + hostName);

            } else {
                fail("TRACE test failed: attrs is null");
            }
        } else {
            fail("test failed: dnsCtx is null");
        }
    }
}
