import java.net.*;
import java.io.*;
import java.util.*;
public class Cordinator{
    public static Map<Integer, ParticipantCl> participants = new HashMap<Integer, ParticipantCl>();
    public static void main(String args[]) throws Exception{
        String path = args[0];
        File config = new File(path);
        Scanner configRead = new Scanner(config);
        Integer serverS = configRead.nextInt();
        //Integer waitTime = configRead.nextInt();
        ServerSocket svr = new ServerSocket(serverS);
        // testing participant code
        //ParticipantCl part1 = new ParticipantCl("hello", 122, sl);
        //participants.put(111, part1);
        //System.out.println(participants.get(111).ip);
        while(true){
            Socket sok = svr.accept();
            //add sok and its location into the hash map
            ParticipantThread part = new ParticipantThread(sok);
            part.start();
        }


    }
    //implement where this will get the id of the sender and send the message to it last
    public static void multicastSend(String mString){
        participants.forEach((key, part) -> {
            try{
            System.out.println("this is the ip: "+key);
            Socket socketN = part.socket;
            String parseM;
            parseM = "THIS MESSAGE FROM SERVER: "+mString;
            DataOutputStream ostream = new DataOutputStream(socketN.getOutputStream());
            ostream.writeUTF(parseM);
            System.out.println(parseM);
            }catch(Exception e){
                System.out.println(e);
            }
        });
    }


}