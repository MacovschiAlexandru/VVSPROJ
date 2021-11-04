import java.util.ArrayList;
import exceptions.*;
import server.*;
public class Main {

    public static void main(String[] args) throws WrongPortException, WrongStatusException, WrongWebPathException {
	int port = 11015;
    String webPath = "src/main/java/website";
    String serverStatus = "Stopped";
    WebServer webServer = new WebServer(port,webPath,serverStatus);
    ArrayList<String> lvl0 = new ArrayList<String>();
    ArrayList<String> lvl1 = new ArrayList<String>();
    ArrayList<String> lvl2 = new ArrayList<String>();
    webServer.addLvl(lvl0);
        webServer.addLvl(lvl1);
        webServer.addLvl(lvl2);
    webServer.addPageOnLvl("01.html",0);
    webServer.addPageOnLvl("02.html",0);
    webServer.addPageOnLvl("03.html",0);
    webServer.addPageOnLvl("pic.jpg", 0);

    webServer.addPageOnLvl("11.html",1);
    webServer.addPageOnLvl("12.html",1);

    webServer.addPageOnLvl("21.html",2);

    ServerController serverController = new ServerController(webServer);

    webServer.setServerStatus("Running");

    while(true){

        serverController.reqHandle();
    }
    }
}
