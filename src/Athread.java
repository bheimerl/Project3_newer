import java.util.*;
import java.io.*;
import java.net.*;


public class Athread extends Thread{
    Integer clientID;
    String logFil;
    String ip;
    Integer port;
    Integer socketPort;
    public Athread(Integer id, String logFile, String ipl, int portl){
        clientID = id;
        logFil = logFile;
        ip = ipl;
        port = portl;
    }
    public void run(){
 
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String msg = "";
            boolean needReconnect = false;
            boolean connected = true;
            Socket socket = new Socket(ip, port);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            Bthread btd = new Bthread(socket, logFil);
            btd.start();
            while(true){
                if(needReconnect){
                    socket = new Socket(ip, port);
                    Bthread bthread = new Bthread(socket, logFil);
                    bthread.start();
                    needReconnect = false;
                    System.out.println("works");
                    
                    dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeUTF("reconnect");
                    dos.writeUTF(Integer.toString(clientID));
                    dos.writeUTF(ip);
                    connected = true;
                }
                msg = br.readLine();
                if(connected){
                    dos.writeUTF(msg);
                    dos.flush();
                }
                if(msg.equals("disconnect")){
                    connected = false;
                }
                if(msg.equals("reconnect")){
                    needReconnect = true;
                    System.out.println("con");
                }
            }

        }catch(Exception e){
            System.out.println(e);
        }
    }
}