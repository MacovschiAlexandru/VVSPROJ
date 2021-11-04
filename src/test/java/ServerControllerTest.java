import exceptions.WrongPortException;
import exceptions.WrongStatusException;
import exceptions.WrongWebPathException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import server.ServerController;
import server.WebServer;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
public class ServerControllerTest {
    private int port = 11015;
    private String webFilePath="src/main/java/website";
    private ArrayList<String> status = new ArrayList<String>(Arrays.asList("Stopped","Running","Maintenance"));


            @Test
    public void testNewServerSocketOk() throws WrongWebPathException, WrongPortException, WrongStatusException {
                WebServer webServer = new WebServer(port,webFilePath,status.get(0));
                webServer.setServerStatus(status.get(1));
                try {
                    ServerSocket socket = ServerController.newSS(port);
                    assertTrue(socket.isBound());
                    socket.close();
                } catch (BindException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Test(expected = IllegalArgumentException.class)
                public void TestServerSocketPortIsntOk() throws WrongPortException,WrongStatusException,WrongWebPathException{
                WebServer webServer = new WebServer(port, webFilePath, status.get(0));
                webServer.setServerStatus(status.get(1));

             try{
                 ServerSocket socket = ServerController.newSS(65540);
             }catch(BindException e){

             }

            }
            @Test(expected = BindException.class)
    public void TestPortNotAvailabe() throws IOException {
                ServerSocket s1 = ServerController.newSS(port);
                ServerSocket s2 = ServerController.newSS(port);
                assertTrue(s1.isClosed());
            }
            @Test(expected =NullPointerException.class)
    public void closeServerNotWorking() throws IOException, WrongPortException, WrongStatusException, WrongWebPathException {
               ServerController.closeSS(null);

            }
            @Test
    public void CloseServerWorking(){
                try{
                    ServerSocket s = ServerController.newSS(port);
                    ServerController.closeSS(s);
                    assertTrue(s.isClosed());
                } catch (BindException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Test
    public void testNewCSOK() throws Exception {
               try{
                   ServerSocket ss = ServerController.newSS(port);
                   Socket cs = ServerController.newCS(ss);
                   assertTrue(cs.isBound());
               }catch(Exception e){
                   e.printStackTrace();
               }
            }
           @Test
    public void testNewCSNotOk(){
              try{
                  Socket cs = ServerController.newCS(null);
                  assertTrue(cs.isClosed());
              } catch (Exception e) {
                  e.printStackTrace();
              }
           }
           @Test
    public void testCloseCSIsWorking() throws IOException {
               try{
                   ServerSocket s = ServerController.newSS(port);
                   Socket cs = ServerController.newCS(s);
                   ServerController.closeCS(cs);
                   assertTrue(cs.isClosed());
               }catch(IOException e){
                   e.printStackTrace();
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
           @Test(expected = NullPointerException.class)
           public void TestCloseCSIsNotWorking() throws IOException {
             try{
                 ServerController.closeCS(null);
             }catch(IOException e){
                 e.printStackTrace();
             }

           }
           @Test
    public void AcceptWorking(){
                try{
                    ServerSocket ss = ServerController.newSS(port);
                    Socket cs = ServerController.newCS(ss);
                    assertTrue(ss.isBound());
                    assertTrue(cs.isBound());
                    ss.close();
                    cs.close();
                } catch (BindException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
           }
     @Test(expected = NullPointerException.class)
    public void AcceptNotWorking() throws Exception {
              ServerSocket s = ServerController.newSS(port);
              Socket cs = ServerController.newCS(null);
              assertTrue(!cs.isBound());
     }
    @Test
    public void clientHandlerWorking() throws Exception {
WebServer ws = new WebServer(port,webFilePath,status.get(0));
ServerController sc = new ServerController(ws);
ServerSocket ss = ServerController.newSS(port);
Socket cs = ServerController.newCS(ss);
sc.clientHandle(cs);

    }
  @Test
  public void clientHandlerNotWorking()throws WrongPortException,WrongStatusException,WrongWebPathException{
                WebServer ws = new WebServer(port,webFilePath,status.get(0));
                ServerController sc = new ServerController(ws);
                sc.clientHandle(null);
  }

 @Test
    public void reqHandlerServerStopped()throws WrongPortException,WrongStatusException,WrongWebPathException{
                WebServer ws = new WebServer(port,webFilePath, status.get(0));
                ServerController sc = new ServerController(ws);
                sc.reqHandle();
 }
 @Test
    public void reqHandlerServerRuns()throws WrongPortException,WrongStatusException,WrongWebPathException{
                try{
                    WebServer ws = new WebServer(port, webFilePath, status.get(0));
                    ws.setServerStatus(status.get(1));
                    ServerController sc = new ServerController(ws);
                    sc.reqHandle();
                } catch (WrongPortException e) {
                    e.printStackTrace();
                } catch (WrongStatusException e) {
                    e.printStackTrace();
                } catch (WrongWebPathException e) {
                    e.printStackTrace();
                }
 }
 @Test
    public void reqHandlerInMaintenance()throws WrongPortException,WrongStatusException,WrongWebPathException {

     WebServer ws = new WebServer(port, webFilePath, status.get(0));
     ws.setServerStatus(status.get(2));
     ServerController sc = new ServerController(ws);
     sc.reqHandle();
 }

}

