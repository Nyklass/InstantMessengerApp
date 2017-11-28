
package client;

import javax.swing.JFrame;

public class Client {    
    public static void main(String[] args) {
        clientest adddi;
        adddi = new clientest("127.0.0.1");
        adddi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adddi.startRunning();
    }
    
}
