import java.net.*;
import java.io.*;
import java.util.*;
public class Participant{

    public static void main(String args[]) throws Exception{
        String path = args[0];
        File configFile = new File(path);
        Scanner configReader = new Scanner(configFile);
        String ids = configReader.nextLine();
        String logPath = configReader.nextLine();
        String ip = configReader.next();
        String portS = configReader.next();
        Integer id = Integer.parseInt(ids);
        Integer port = Integer.parseInt(portS);
        System.out.println(logPath);
        File logFile = new File(logPath);

        //add two threads one for reciving messages from the cordinator(Thread-B)
        //one for reciving messages from the user(Thread-A)
        //Socket client = new Socket(ip, port);
        Athread athread = new Athread(id, logPath, ip, port);
        athread.start();
        //DataInputStream dis = new DataInputStream(client.getInputStream());
        //DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        //BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        //String Cmsg ="";
        //String Smsg ="";
       /* while(Cmsg !="blap"){
            Cmsg = br.readLine();
            dos.writeUTF(Cmsg);
            Smsg= dis.readUTF();
            System.out.println(Smsg);
            dos.flush();
        }
        */
        /*dis.close();
        dos.close();
        client.close();
        */

    }

}