
package bigone;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Tester extends JFrame {

    private JTextField userText;
    private JTextArea chatWindow;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private ServerSocket server;
    private Socket connection;
    
    //constructor
    public Tester(){
    super ("Nyklass Instant Messanger");
    userText = new JTextField();
    userText.setEditable(false);
    userText.addActionListener(
        new ActionListener(){
        public void actionPerformed(ActionEvent event){
          sendMessage(event.getActionCommand());
          userText.setText("");       
         }   
       }
    );
     add(userText,BorderLayout.NORTH);
     
    chatWindow = new JTextArea();
    add(new JScrollPane(chatWindow));
    setSize(330,280);
    setVisible(true);
    }
   //set up and run the server
    public void startRunning(){
    try{
        server = new ServerSocket(6789,100);
        while(true){
            try{
                 waitForConnection();
                 setupStreams();
                 whileChatting();
            }catch(EOFException eofException){
                 showMessage("\n Server end the connection");
            }finally{
                 closeCrap();
            }   
       }
    }catch(IOException ioException){
      ioException.printStackTrace();
    }
    
  }
    //wait for connection, then display connection information
    private void waitForConnection() throws IOException{
           showMessage("\n Waiting for someone to connect...\n");
           connection = server.accept();
           showMessage("\n Now connected to" + connection.getInetAddress().getHostName());
           
    }
    //get stream to send in receive MESSAGES
    private void setupStreams() throws IOException{
         output = new ObjectOutputStream(connection.getOutputStream());
         output.flush();
         input = new ObjectInputStream(connection.getInputStream());
         showMessage("\n Streams are now setup!\n");
     
     //chatting
    
     
     }
      private void whileChatting() throws IOException{
          String message = "You are now connected!";
          sendMessage(message);
          ableToType(true);
          do{
              try{
                  message = (String) input.readObject();
                  showMessage("\n" + message);
              
             }catch(ClassNotFoundException classNotFoundException){
               showMessage ("idk wht that user send!");
               
              }
          //have a converastion
          }while(!message.equals("CLIENT - END"));
          
      }
      //close streams and sockets when done chatting
      private void closeCrap(){
            showMessage ("\n Closing connections.......\n");
            ableToType(false);
         try{
             output.close();
             input.close();
             connection.close();
        }catch(IOException ioException){
          ioException.printStackTrace();
         }
      }
      
      //send a message to client
      private void sendMessage(String message) {
         try{
             output.writeObject("SERVER- "+ message);
             output.flush();
             showMessage("\nSERVER- "+ message);
        }catch(IOException ioException){
          chatWindow.append("\n ERROR: DUDE I CANT SEND THAT MESSAGE");
          }
      }
      
     //updates chatwindow
      
      private void showMessage(final String text){
           SwingUtilities.invokeLater(
               new Runnable(){
                public void run(){
                 chatWindow.append(text);
                  }
               }
           );
        }
      //let the user tyoe stuff into the boc
      private void ableToType(final boolean tof){
          SwingUtilities.invokeLater(
               new Runnable(){
                public void run(){
                 userText.setEditable(tof);   
                 
                  }
               }
           );
      }
 }