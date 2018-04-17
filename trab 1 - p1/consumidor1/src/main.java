
import java.net.SocketException;
import java.net.UnknownHostException;
import model.consumidor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author daniel
 */
public class main {

    /**
     * @param args the command line arguments
     * @throws java.net.SocketException
     * @throws java.net.UnknownHostException
     */
    public static void main(String[] args) throws SocketException, UnknownHostException {
        consumidor c1 = new consumidor(1);
        consumidor c2 = new consumidor(2);
        c1.start();
        c2.start();
    }
    
}
