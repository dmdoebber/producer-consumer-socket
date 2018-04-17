/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posto;


import mensagem.mensagem;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 *
 * @author daniel
 */
public class posto extends Thread{
    private final Socket socket;
    private final tanque tanque;
    
    private ObjectOutputStream objectOut;
    private ObjectInputStream objectIn;
    
    private mensagem msg;
    
    private short num;
    
    public posto(Socket s, tanque t){
        this.socket = s;
        this.tanque = t; 
    }
    
    @Override
    public void run(){
        try {
            objectOut = new ObjectOutputStream(socket.getOutputStream());
            objectIn = new ObjectInputStream(socket.getInputStream());
            
            msg = (mensagem) objectIn.readObject();
            if ((msg.getTipo()).equals("POP")) {
                if(tanque.pop(num))
                    msg.setNumero(msg.getNumero());
                else
                    msg.setNumero((short)0);
                msg.setTipo("RET_POP");
                objectOut.writeObject(msg);
            }
            
            if((msg.getTipo()).equals("PUSH")){
                if(tanque.push(num))
                    msg.setNumero(msg.getNumero());
                else
                    msg.setNumero((short)0);
                msg.setTipo("RET_PUSH");
                objectOut.writeObject(msg);
            }
            
        } catch (Exception ex) {
            System.out.println("Erro!"+ex);
        } 
    }
}
