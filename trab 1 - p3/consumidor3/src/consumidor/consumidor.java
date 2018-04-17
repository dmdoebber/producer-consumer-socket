/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consumidor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;
import mensagem.mensagem;

/**
 *
 * @author daniel
 */
public class consumidor extends Thread{
    private byte[] dado;
    private InetAddress ip;
    private int porta;
    private String servidor;
    private Socket socket;
    private ObjectOutputStream objectOut;
    private ObjectInputStream objectIn;
    private int bytesLidos;
    private Random r;
    private boolean continua;
    private int totalAbastecido;
    private int numC;
    
    public consumidor(int numc){
        dado = new byte[Short.SIZE];
        porta = 12345;
        servidor = "localhost";
        r = new Random();
        continua = true;
        totalAbastecido = 0;
        this.numC = numc;
    }
    @Override
    public void run(){
        while(continua){
            try{
                ip = InetAddress.getByName(servidor);
                socket = new Socket(ip, porta); 

                objectOut = new ObjectOutputStream(socket.getOutputStream());
                objectIn = new ObjectInputStream(socket.getInputStream());

                int num = 10 + r.nextInt(41);
                mensagem msg = new mensagem("POP", (short) num);
                
                objectOut.writeObject(msg);
                
                msg = (mensagem) objectIn.readObject();
                if((msg.getTipo()).equals("RET_POP"))
                    num = msg.getNumero();
                if(num == 0)
                    continua = false; 
                socket.close();
                totalAbastecido += num;
                Thread.sleep(100);
            }catch(Exception ex){
                System.out.println("Erro!"+ex);
            }  
        }
        System.out.println("Total consumido: "+totalAbastecido);
    }


}
