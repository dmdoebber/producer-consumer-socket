/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package produtor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import mensagem.mensagem;

/**
 *
 * @author daniel
 */
public class produtor extends Thread{
    private InetAddress ip;
    private int porta;
    private String servidor;
    private Socket socket;
    private ObjectOutputStream objectOut;
    private ObjectInputStream objectIn;
    private boolean continua;
    private int totalProduzido;
    private int timeWait;
    private mensagem msg;
    
    public produtor(){
        porta = 12345;
        servidor = "localhost";
        continua = true;
        totalProduzido = 0;
    }

    @Override
    public void run(){
        System.out.print("Digite o tempo de espera entre cargas: ");
        Scanner ler  = new Scanner(System.in);
        timeWait = ler.nextInt();
        while(continua){
            try{
                ip = InetAddress.getByName(servidor);
                socket = new Socket(ip, porta); 

                objectOut = new ObjectOutputStream(socket.getOutputStream());
                objectIn = new ObjectInputStream(socket.getInputStream());
                
                msg = new mensagem("PUSH", (short)10000);
                objectOut.writeObject(msg);
                    
                msg = (mensagem) objectIn.readObject();
                if((msg.getTipo()).equals("RET_PUSH"))
                    if(msg.getNumero() == 0)
                        continua = false;
                
                socket.close();
                Thread.sleep(timeWait*1000);
            }catch(Exception ex){
                System.out.println("Erro!"+ex);
            }  
        }
    }
}
