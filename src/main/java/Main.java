import java.util.ArrayList;
import exceptions.*;
import server.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame{
   public static WebServer webServer;
   public static ServerController serverController;

    JPanel panel = new JPanel();


    JButton start_button;

    JButton maintenance_button;
    JButton stop_button;
    JLabel label1 = new JLabel();
    JLabel label2 = new JLabel();
    JLabel label3 = new JLabel();

    public Main(){
        super();
        panel.setBorder(BorderFactory.createEmptyBorder(150,150,50,150));
        panel.setLayout(new GridLayout(0,1));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel);
        this.setSize(1000,1000);
        this.setResizable(true);
        this.label1.setText("11015");
        this.label2.setText("src/main/java/website");
        this.label3.setText("Server status: " + webServer.getServerStatus());
        start_button = new JButton("Start Server");
        start_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               webServer.setServerStatus("Running");
               label3.setText("Server status: Running");
            }
        });
        stop_button = new JButton("Stop server");
        stop_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                webServer.setServerStatus("Stopped");
                label3.setText("Server status: Stopped");
            }
        });
        maintenance_button = new JButton("Maintenance server");
        maintenance_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                webServer.setServerStatus("Maintenance");
                label3.setText("Server status: Maintenance");
            }
        });



panel.add(start_button);
panel.add(maintenance_button);
panel.add(stop_button);
  panel.add(label1);
  panel.add(label2);
  panel.add(label3);




    }
    public static void main(String[] args) throws WrongPortException, WrongStatusException, WrongWebPathException {
           serverMainAction();
           JFrame frame = new Main();

        frame.setVisible(true);
        while(true){
                serverController.reqHandle();
        }
    }
 public static void serverMainAction() throws WrongPortException, WrongStatusException, WrongWebPathException {
     int port = 11015;
     String webPath = "src/main/java/website";
     String serverStatus = "Stopped";
     webServer = new WebServer(port, webPath,serverStatus);
     ArrayList<String> lvl0 = new ArrayList<String>();
     ArrayList<String> lvl1 = new ArrayList<String>();
     ArrayList<String> lvl2 = new ArrayList<String>();
     webServer.addLvl(lvl0);
     webServer.addLvl(lvl1);
     webServer.addLvl(lvl2);
     webServer.addPageOnLvl("01.html", 0);
     webServer.addPageOnLvl("02.html", 0);
     webServer.addPageOnLvl("03.html", 0);
     webServer.addPageOnLvl("pic.jpg", 0);

     webServer.addPageOnLvl("11.html", 1);
     webServer.addPageOnLvl("12.html", 1);

     webServer.addPageOnLvl("21.html", 2);

     serverController = new ServerController(webServer);



 }
}

