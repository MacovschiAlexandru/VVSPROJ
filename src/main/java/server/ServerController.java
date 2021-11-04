package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ServerController{
    private WebServer webServer;
    public ServerController(WebServer webServer){
        this.webServer=webServer;
    }
    public static ServerSocket newSS(int sPort) throws BindException{
        try{
            ServerSocket newS = new ServerSocket(sPort);
            System.out.println("Server created on port: " +sPort);
            return newS;
        }catch(IllegalArgumentException e){
            System.out.println("Port has to be between 0 and 65535");
            throw e;
        } catch (BindException e) {
            System.out.println("Port unavailable");
            throw e;
        } catch (Exception e){
            System.out.println("Server creation failed for port: " + sPort);
            return null;
        }

    }
    public static Socket newCS(ServerSocket sS) throws Exception{
        try{
           Socket newCS = acceptS(sS);

            System.out.println("Client created");
            return newCS;
        }catch(Exception e){
            System.out.println("couldnt create client");
            throw e;
        }
    }
    public static void closeSS(ServerSocket SS) throws NullPointerException, IOException {
        try{
            SS.close();
            System.out.println("server closed");
        }catch(NullPointerException e){
            System.out.println("null socket");
            throw e;
        }catch(Exception e){
            System.out.println("failed to close server");
            throw e;
        }
    }
    public static void closeCS(Socket cS) throws NullPointerException, IOException {
        try{
            cS.close();
            System.out.println("client removed succesfully");
        }catch(NullPointerException e){
            System.out.println("this client doesnt exist");
            throw e;
        }catch(Exception e){
            System.out.println("failed to close this client");
            throw e;
        }
    }
    public static Socket acceptS(ServerSocket sS) throws Exception{
        try {
            return sS.accept();
        }catch(Exception e){
            System.out.println("couldnt accept socket");
            throw e;
        }
    }
    public void respond(OutputStream out, String status, String typeOfContent, byte[] content) throws IOException{
        out.write(("HTTP/1.1 \r\n" + status).getBytes());
        out.write("\r\n".getBytes());
        out.write(content);
        out.write("\r\n\r\n".getBytes());
    }
    public void clientHandle(Socket clientSocket){

        ArrayList<String> inputs = new ArrayList<String>();

        Path file;
        String contentType;
        String rawPath;

        try{

            OutputStream out = clientSocket.getOutputStream(); // send response to client
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // read data from the client

            String inputString;

            while((inputString = in.readLine())!=null){

                // print the input for debug
                System.out.println("Input : " + inputString);

                //adding the lines into array to be easier to handle
                inputs.add(inputString);

                if(inputString.isEmpty()){
                    break;
                }
            }

            //handle the input
            if(!inputs.isEmpty())
            {
                rawPath = inputs.get(0).split(" ")[1];
                // Construct the path for file
                if(rawPath.equals("/") || rawPath.equals("/index.html")){
                    file = Paths.get(webServer.getWebPath(), "index/index.html");
                }else if(rawPath.equals("/favicon.icon")){
                   file = Paths.get(webServer.getWebPath(),"favicon.icon");
                }

                else{
                    file = Paths.get(rawPath);
                }

                System.out.println("DEBUG : raw path : " + rawPath);


                if(!file.toString().contains("index") && !file.toString().contains("favicon")){
                    if(webServer.pages.get(0).contains(rawPath.substring(1))) {
                        String aux = webServer.getWebPath() + "/html";
                        file = Paths.get(aux, rawPath);
                    }else if(webServer.pages.get(1).contains(rawPath.substring(1))) {
                        String aux = webServer.getWebPath() + "/html/htmllvl1";
                        file = Paths.get(aux, rawPath);
                    }else if(webServer.pages.get(2).contains(rawPath.substring(1))) {
                        String aux = webServer.getWebPath() + "/html/htmllvl1/htmllvl2";
                        file = Paths.get(aux, rawPath);
                    }else{
                        file = Paths.get(rawPath);
                    }
                }

                System.out.println("DEBUG : file path : "+file);


                contentType = Files.probeContentType(file);

                String temp = webServer.getReq() + " " + file;
                webServer.setReq(temp);


                if(webServer.getServerStatus().equals("Running")){

                    if(Files.exists(file)){
                        respond(out,"Status OK",contentType,Files.readAllBytes(file));
                    }else{
                        respond(out, "Error 404", contentType, Files.readAllBytes(Paths.get(webServer.getWebPath(), "Error/404.html")));
                    }
                }else if (webServer.getServerStatus().equals("Maintenance")) {
                    if (contentType.contains("html")) {
                        respond(out, " Service Unavailable", contentType, Files.readAllBytes(Paths.get(webServer.getWebPath(), "maintenance/maintenance.html")));
                    } else {
                        respond(out, "Status OK", contentType, Files.readAllBytes(file));
                    }
                }else  {

                        respond(out, "Status OK", contentType, Files.readAllBytes(file));

                }

            }

            in.close();
            out.close();

        }catch(NullPointerException e){
            System.err.println("Client is null");
        }catch(IOException e){
            e.printStackTrace();
            System.err.println("Communication problem");
        }

    }
    public void reqHandle(){
        try(ServerSocket sS = this.newSS(webServer.getPort())){
            Socket clientS = this.newCS(sS);
            clientHandle(clientS);
            this.closeCS(clientS);
            this.closeSS(sS);
        }catch(IOException e){
e.printStackTrace();
            System.err.println("Communication problem");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
