package server;
import exceptions.WrongPortException;
import exceptions.WrongStatusException;
import exceptions.WrongWebPathException;

import java.util.ArrayList;
public class WebServer {
    private int port;
    private String webPath;
    private String serverStatus;
    private String req;
    public ArrayList<ArrayList<String>> pages = new ArrayList<>();

    public WebServer()
    {

    }
    public WebServer(int port, String webPath,String serverStatus) throws WrongPortException, WrongStatusException, WrongWebPathException {
        validateWebServerInputs(port,webPath,serverStatus);
        this.port =port;
        this.webPath=webPath;
        this.serverStatus=serverStatus;
        this.req="";
        pages = new ArrayList<ArrayList<String>>();
    }
    public int getPort(){
        return port;

    }
    public void setPort(int port){
        this.port = port;
    }
    public String getWebPath(){
        return webPath;
    }
    public void  setWebPath(String webPath){
        this.webPath=webPath;
    }
   public String getServerStatus(){
        return serverStatus;
   }
   public void setServerStatus(String serverStatus){
        this.serverStatus=serverStatus;
   }
  public String getReq(){
        return req;
  }
  public void setReq(String req){
        this.req = req;
  }
  public void validateWebServerInputs(int port, String webPath, String serverStatus) throws WrongPortException, WrongStatusException, WrongWebPathException {
        if(port!= 11015)
            throw new WrongPortException();
        if(!serverStatus.equals("Stopped"))
            throw new WrongStatusException();
        if(!webPath.equals("src/main/java/website")){
            throw new WrongWebPathException();
        }
  }
  public void addLvl(ArrayList<String> lvl){
        pages.add(lvl);
  }
  public void addPageOnLvl(String page, int lvl){
        pages.get(lvl).add(page);
  }
}
