package ch.m1m.ldap;

import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.sdk.*;

// doc:  https://docs.ldap.com/ldap-sdk/docs/index.html
// FAQ:  https://docs.ldap.com/ldap-sdk/docs/ldapsdk-faq.html
// Java: https://www.ldap.com/unboundid-ldap-sdk-for-java

/**
 * Created by mue on 15.07.17.
 */
public class MainLdapExample {
	
	static final int LDAP_PORT = 3389;
	
	
	public static void doSimpleQuery(LDAPConnection conn) throws LDAPSearchException {
		
		System.out.println("Executing new simpleQuery...");
		
		// SearchResultEntry entry = conn.getEntry("dc=example,dc=com");
		// System.out.println(entry.toString());
		
		//SearchResult searchResult = conn.search("dc=example,dc=com", SearchScope.SUB, "(cn=*)");
		// SearchResult searchResult = conn.search("dc=example,dc=com", SearchScope.SUB, "(cn=Markus Mueller)");

		// ok but no groups
		//SearchResult searchResult = conn.search("dc=example,dc=com", SearchScope.SUB, "(uid=mue)");

		SearchResult searchResult = conn.search("dc=example,dc=com", SearchScope.SUB, "(uid=mue)");
		System.out.println(searchResult.getEntryCount() + " entries returned.");
		for (SearchResultEntry e : searchResult.getSearchEntries()) {

			System.out.println(e.toLDIFString());
			System.out.println();
			System.out.println("from result... dn : " + e.getDN());
			Attribute attribute = e.getAttribute("mail");
			if (attribute != null) {
				System.out.println("  mail : " + attribute.getValue());
			}
			String [] ou = e.getAttributeValues("ou");
			if (ou != null) {
				int ii = 0;
				for (ii = 0; ii < ou.length; ii++) {
					System.out.println("  ou : " + ou[ii]);
				}
			}

		}	
	}
	

	public static void main(String[] args) throws LDAPException {

		//String ldifImport = "/home/mue/work/java/ldapex1/src/main/resources/example1.ldif";
		String ldifImport = "/home/mue/work/java/ldapex1/src/main/resources/example-com.ldif";
		ldifImport = "./src/main/resources/example-com.ldif";

		// Create the configuration to use for the server.
		InMemoryDirectoryServerConfig config = new InMemoryDirectoryServerConfig("dc=example,dc=com");
		config.addAdditionalBindCredentials("cn=Manager", "myPassword");
		
		// disable schema checking
		config .setSchema(null);
		
		InMemoryListenerConfig listenConf = InMemoryListenerConfig.createLDAPConfig("myLdapListener", LDAP_PORT);
		
		//InMemoryListenerConfig listenConf = new InMemoryListenerConfig(ldifImport, null, 0, null, null, null);		
		config.setListenerConfigs(listenConf);

		// Create the directory server instance, populate it with data from the
		// "test-data.ldif" file, and start listening for client connections.
		InMemoryDirectoryServer ds = new InMemoryDirectoryServer(config);

		ds.importFromLDIF(true, ldifImport);
		System.out.println("loaded ldif file: " + ldifImport);
		
		ds.startListening();
		

		// Get a client connection to the server and use it to perform various
		// operations.
		LDAPConnection connInternal = ds.getConnection();
		System.out.println("using connection: connInternal");
		doSimpleQuery(connInternal);
		
		
		// create a secured connection trusting any server cert
		//
//		SSLUtil sslUtil = new SSLUtil(new TrustAllTrustManager());
//		SSLSocketFactory socketFactory = sslUtil.createSSLSocketFactory();
//		LDAPConnection connection = new LDAPConnection(socketFactory,
//		     "server.example.com", 636);
				
		LDAPConnection connExternal = new LDAPConnection("localhost", LDAP_PORT);
		System.out.println("using connection: connExternal");
		connExternal.bind("cn=Manager", "myPassword");
		doSimpleQuery(connExternal);


		connExternal.bind("uid=mue,ou=People,dc=example,dc=com", "myPassword");


		// Disconnect from the server and cause the server to shut down.
		connExternal.close();
		connInternal.close();
		ds.shutDown(true);

	}
	
	

}
