/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posto;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author daniel
 */
public class server extends Thread{
    private ServerSocket server;
    private tanque t;
    
    public server() throws IOException{
        server = new ServerSocket(12345);
        t = new tanque();
    }
    
    public void run(){
        while(true){
            try {
                Socket s = server.accept();
                posto p = new posto(s, t);
                p.start();

            } catch (IOException ex) {
                System.out.println("Erro!"+ex);
            }
        }
    }
    
    
    
    
    
}
