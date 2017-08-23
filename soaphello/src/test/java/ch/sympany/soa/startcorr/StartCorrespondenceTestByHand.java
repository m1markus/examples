package ch.sympany.soa.startcorr;

import javax.xml.ws.BindingProvider;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.legodo.startservice.StartCorrespondenceFault;
import com.legodo.startservice.StartService;
import com.legodo.startservice.StartService_Service;

public class StartCorrespondenceTestByHand {
  
  private static Logger log = LogManager.getLogger(StartCorrespondenceTestByHand.class);

  // OpenText (C4) URL's
  //
  // opentext Master tvvvgsmecm013.sympany-test.ads
  //private static final String endpointStartCorreUrl = "http://tvvvgsmecm013.sympany-test.ads:7501/c4ApplicationServer/services/startService";
  //
  // opentext Trans  tvvvgsmecm012.sympany-test.ads
  //private static final String endpointStartCorreUrl = "http://tvvvgsmecm012.sympany-test.ads:7501/c4ApplicationServer/services/startService";
  //
  // opentext Dev tvvvgsmecm011.sympany-test.ads
  //private static final String endpointStartCorreUrl = "http://tvvvgsmecm011.sympany-test.ads:7501/c4ApplicationServer/services/startService";
  //
  // local fake opentext server
  private static final String endpointStartCorreUrl = "http://localhost:8080/startCorrEndpoint/services/StartCorrespondenceService";

  @Test
  public void test() {
    log.info("testing ...");
    
    log.error("log something that will be useful.");
    
    org.apache.logging.log4j.ThreadContext.put("mykey", "my-log4j2-thr-context-value");
    org.apache.logging.log4j.ThreadContext.put("user", "txtoto");
    
    log.error("log something with possible context info");

    StartService_Service ssServ = new StartService_Service();
    StartService port = ssServ.getStartServiceSOAP();

    BindingProvider bp = (BindingProvider) port;
    bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointStartCorreUrl);
       
    Client cxfClient = ClientProxy.getClient(port);
    cxfClient.getInInterceptors().add(new MyCXFMsgInterceptorOne());
    cxfClient.getInInterceptors().add(new MyCXFSoapInterceptorOne());

    com.legodo.startservice.Mode mode = null;
    com.legodo.startservice.Startup startup = null;
    com.legodo.startservice.Settings settings = null;

    try {
      log.info("Invoking startCorrespondence..." + endpointStartCorreUrl);

      java.lang.String result = port.startCorrespondence(mode, startup, settings);
      log.info("startCorrespondence.result=" + result);

    } catch (StartCorrespondenceFault ex) {
      log.error("Expected exception: startCorrespondenceFault has occurred.");
      log.error(ex.toString());
    } catch (Exception ex) {
      log.error(ex.toString());
    }
  }

}
