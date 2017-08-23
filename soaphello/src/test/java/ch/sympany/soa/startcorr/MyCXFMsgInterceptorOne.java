package ch.sympany.soa.startcorr;

import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

public class MyCXFMsgInterceptorOne extends AbstractPhaseInterceptor<Message> {
 
  
  public MyCXFMsgInterceptorOne() {
    super(Phase.RECEIVE);
  }

  
  public void handleMessage(Message message) {
    String contentType = (String) message.get(Message.CONTENT_TYPE);
    
    System.err.println("TRACE MyCXFInterceptorOne:handleMessage() " + contentType);
  }

  
  public void handleFault(Message messageParam) {
    System.err.println("TRACE MyCXFInterceptorOne:handleFault() ");
  }
  
}