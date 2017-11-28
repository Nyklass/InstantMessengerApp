
package client;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class clientest extends JFrame {
    
    private JTextField jtext;
    private JTextArea chatwindow;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String message = "";
    private String serverIP;
    private Socket connection;
    //constructor
    
   public clientest(String host){
   super("Online");
   serverIP= host;
   jtext = new JTextField();
   jtext.setEditable(false);
   jtext.addActionListener(
     new ActionListener(){
         public void actionPerformed(ActionEvent event){
         sendMessage(event.getActionCommand());
         jtext.setText("");
       }
     }
   );
    add(jtext,BorderLayout.NORTH);
    chatwindow = new JTextArea();
    add(new JScrollPane(chatwindow), BorderLayout.CENTER);
    setSize(330,280);
    setVisible(true);
    
  }
  //connect to server
    public void startRunning(){
         try{
              connectToServer();
              setupStreams();
              whileChatting();
         
         }catch(EOFException eofException){
           showMessage("\n Client terminated connection");
         }catch(IOException ioException){
            ioException.printStackTrace();
         }finally{
             closeCrap();
         }
         
    }
//connect to server
    private void connectToServer() throws IOException{
       showMessage("Attempting connection.....\n");
       connection = new Socket(InetAddress.getByName(serverIP),6789 );
       showMessage("Connected to: " + connection.getInetAddress().getHostName());
    
    }
        //set up streams to send and receive messages
    private void setupStreams() throws IOException{
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
        showMessage("\n Dude Your streams are now good to go\n");     
    }
    //while chatting 
    private void whileChatting() throws IOException{
        
       ableToType(true);
       do{
           try{
               message = (String ) input.readObject();
               showMessage("\n" + message);
           }catch(ClassNotFoundException classNotfoundException){
            showMessage("\n I dont know that object Type");
           }
       
       }while(!message.equals("SERVER - END"));
    
    }
    //close the streams and sockets
    private void closeCrap(){
         showMessage("\n closing crap down...");
         ableToType(false);
        try{
             output.close();
             input.close();
             connection.close();
        }catch(IOException ioException){
          ioException.printStackTrace();
       }
    
    }
    //send message to server
    private void sendMessage(String message){
          try{  
             output.writeObject("CLIENT - "+ message);
             output.flush();
             showMessage("\nCLIENT - "+  message);
          }catch(IOException ioException){
           chatwindow.append("\n something went wrong");
          }
    }
    //show message update chatwindow
    public void showMessage(final String m){
       SwingUtilities.invokeLater(
           new Runnable(){
               public void run(){
                 chatwindow.append(m);
               }
           }
       );    
    }
    //gives user permission to type in the textbox
    
    private void ableToType(final boolean tof){
          SwingUtilities.invokeLater(
           new Runnable(){
               public void run(){
                 jtext.setEditable(tof);
    
                }
            }    
        ); 
    }
}    
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
              