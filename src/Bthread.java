/*import java.util.*;
import java.io.*;
import java.net.*;


public class Bthread extends Thread{
    Socket socket;
    String logFile;
    public Bthread(Socket s, String log){
        socket = s;
        logFile = log;
    }
    public void run(){
        try{
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            String msg = "";
            FileWriter logWrite = new FileWriter("log1.txt");
            while(!msg.equals("disconnect")){
                msg = dis.readUTF();
                System.out.println(msg);
                logWrite.write("write this");
            }

        }catch(Exception e){
            System.out.println(e);
        }
    }
}*/
import java.util.*;
import java.io.*;
import java.net.*;


public class Bthread extends Thread{
    Socket socket;
    String logFile;
    public Bthread(Socket s, String log){
        socket = s;
        logFile = log;
    }
    public void run(){
        try{
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            String msg = "";
            //FileWriter logWrite = new FileWriter("log1.txt");
            FileOutputStream  fout = new FileOutputStream("log1.txt");
            while(!msg.equals("disconnect")){
                msg = dis.readUTF();
                char ch[] = msg.toCharArray();
                 for (int i = 0; i < msg.length(); i++) {
                //System.out.println("in loop msg: " + msg);
                //logWrite.write("write this");
                fout.write(ch[i]);
              }
            }

        }catch(Exception e){
            System.out.println(e);
        }
    }
}