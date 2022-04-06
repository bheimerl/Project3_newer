import java.net.*;
import java.util.*;
import java.io.*;

public class ParticipantThread extends Thread{
    Socket socket;
    ParticipantThread(Socket st){
        socket = st;
    }
    public void run(){
        try{
        DataInputStream istream = new DataInputStream(socket.getInputStream());
        DataOutputStream ostream = new DataOutputStream(socket.getOutputStream());
        System.out.println("thread started");
        String msg = "";
        String nextmsg = "";
        //check to see if there is a reconnection message-> if so, 
        //collect the other information to map it with the correct ID
        //and set registered == true
        while(!msg.equals("disconnect")){
            //add if registered == true
            //update the map with the newly online user
            msg = istream.readUTF();
            if(msg.equals("reconnect")){
                String ids = istream.readUTF();
                Integer id = Integer.parseInt(ids);
                ParticipantCl pr = Cordinator.participants.get(id);
                pr.socket = socket;
                pr.port = socket.getPort();
                pr.ip = istream.readUTF();
                
                long currTime = System.currentTimeMillis();
                float tempSec = 0;
                MessageStore temp;
                long thresh = Long.valueOf(Cordinator.waitTime);

                Stack<String> msgStack = new Stack<String>();
                int stSize = 0;

                //loop through list/queue starting at beggining or end depending
                for (int i = Cordinator.listy.size() -1; i > 0; i--) {
                    temp = Cordinator.listy.get(i);
                    tempSec = (currTime - temp.timeSent) / 1000F;
                if (tempSec <= thresh) {
                    msgStack.push(temp.msgContents);
                    stSize++;
                } else {
                    break;
                }
                }//end loop
                while(stSize > 0) {
                    ostream.writeUTF(msgStack.peek());
                    msgStack.pop();
                    stSize--;
                }


                
            }
            System.out.println(msg);
            String message = "";
            //ostream.writeUTF(msg);
            //ostream.flush();
            //if command == recconect then get the ID and then the port and ip adress, update information
            if(msg.equals("register")){
                nextmsg = istream.readUTF();
                System.out.println(nextmsg+"first message taken");
                ParticipantCl currentPart = new ParticipantCl(nextmsg, socket.getPort(), socket);
                nextmsg = istream.readUTF();
                System.out.println(nextmsg+"second"+currentPart.ip);
                Cordinator.participants.put(Integer.parseInt(nextmsg), currentPart);
                //System.out.println(Cordinator.participants.get(Integer.parseInt(nextmsg)).ip + " this is the port");        
            }
            if(msg.equals("deregister")){
                nextmsg = istream.readUTF();
                System.out.println(nextmsg+"first message taken");
                Cordinator.participants.remove(Integer.parseInt(nextmsg));
                System.out.println(Cordinator.participants.isEmpty());
            }
            if(msg.equals("multicast send")){
                nextmsg = istream.readUTF();
                MessageStore storey = new MessageStore(nextmsg);
                Cordinator.listy.add(storey);
                Cordinator.multicastSend(nextmsg);
            }
            if(msg.equals("disconnect")){
                ostream.writeUTF(msg);
            }
        }
        socket.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }


}