import exceptions.WrongPortException;
import exceptions.WrongStatusException;
import exceptions.WrongWebPathException;
import org.junit.Assert;
import org.junit.BeforeClass;
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

import static org.junit.Assert.*;

public class WebServerTest {
static WebServer webServer;

@BeforeClass
    public static void before() throws WrongPortException,WrongStatusException,WrongWebPathException{
    try{
        webServer = new WebServer(11015, "src/main/java/website", "Stopped");

    } catch (WrongPortException e) {
        System.out.println("Wrong port");
    } catch (WrongStatusException e) {
        System.out.println("Wrong status");
    } catch (WrongWebPathException e) {
        System.out.println("Wrong webPath");
    }
}
@Test
    public void checkServerNotNull(){
    assertNotNull(webServer);
}
@Test
    public void PortOk(){
    assertEquals(webServer.getPort(),11015);
}
@Test(expected = WrongPortException.class)
    public void PortNotOk() throws WrongPortException, WrongStatusException, WrongWebPathException {
    WebServer ws = new WebServer(10008, "src/main/java/website","Stopped");
    assertNotSame(ws.getPort(), 11015);
}
@Test
public void webPathOk(){
    assertEquals(webServer.getWebPath(),"src/main/java/website");
}
@Test(expected = WrongWebPathException.class)
    public void webPathNotOk() throws WrongPortException, WrongStatusException, WrongWebPathException {
    WebServer ws = new WebServer(11015, "src/main/java/server", "Stopped");
    assertNotSame(ws.getWebPath(),"src/main/java/website");
}
@Test
    public void serverStatusOk(){
    assertEquals(webServer.getServerStatus(),"Stopped");
}
@Test(expected = WrongStatusException.class)
public void serverStatusNotOk() throws WrongPortException, WrongStatusException, WrongWebPathException {
    WebServer ws = new WebServer(11015,"src/main/java/website","Running");
    assertNotSame(ws.getServerStatus(),"Stopped");
}
@Test
    public void addLvlIsOk() throws WrongPortException, WrongStatusException, WrongWebPathException {
    WebServer ws = new WebServer(11015,"src/main/java/website","Stopped");
    assertEquals(0,ws.pages.size());
    ArrayList<String> test = new ArrayList<String>();
    ws.addLvl(test);
    assertEquals(1,ws.pages.size());

}
@Test
  public void addPageOnLvlIsOk() throws WrongPortException, WrongStatusException, WrongWebPathException {
    WebServer ws = new WebServer(11015, "src/main/java/website", "Stopped");
    ArrayList<String> testlvl = new ArrayList<String>();
    ws.addLvl(testlvl);
    assertEquals(0,ws.pages.get(0).size());
    String test = new String();
    ws.pages.get(0).add(test);
    assertEquals(1, ws.pages.get(0).size());
}
@Test
    public void getAndSetPortOk(){
    WebServer ws = new WebServer();
    ws.setPort(11015);
    assertEquals(11015, ws.getPort());
}
@Test
    public void getAndSetWebPathOk(){
    WebServer ws = new WebServer();
    ws.setWebPath("src/main/java/website");
    assertEquals("src/main/java/website", ws.getWebPath());
}
@Test
    public void getAndSetStatusOk(){
    WebServer ws = new WebServer();
    ws.setServerStatus("Running");
    assertEquals("Running", ws.getServerStatus());
}
@Test
    public void getAndSetReqOk(){
    WebServer ws = new WebServer();
    ws.setReq("Request");
    assertEquals("Request",ws.getReq());
}
}
