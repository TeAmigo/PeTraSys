/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petrasys.connections;

import com.ib.client.AnyWrapper;
import com.ib.client.EClientSocket;

/**
 *
 * @author rickcharon
 * //rpc - NOTE:3/12/10 6:48 AM - This is necessary to cache the clientID
 */
public class MySocket extends EClientSocket {

  int clientId;

  public int getClientId() {
    return clientId;
  }

  public void setClientId(int clientId) {
    this.clientId = clientId;
  }



  public MySocket(AnyWrapper anyWrapper, int clientId) {
    super(anyWrapper);
    this.clientId = clientId;
  }

  public void connect() {
    eConnect(IBConnectionManager.getHost(), IBConnectionManager.getPort(), clientId);
  }

  public void disConnect() {
    eDisconnect();
    IBConnectionManager.removeClientId(clientId);
  }

}

