/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author daniel
 */
public class posto extends Thread{
    private final Socket socket;
    private final tanque tanque;
    
    private BufferedOutputStream buffOut;
    private BufferedInputStream buffIn;
    
    private byte[] dado, dadoSaida;
    
    private short num;
    
    public posto(Socket s, tanque t){
        this.socket = s;
        this.tanque = t; 
    }
    
    @Override
    public void run(){
        try {
            buffOut = new BufferedOutputStream(socket.getOutputStream());
            buffIn = new BufferedInputStream(socket.getInputStream());
            
            dadoSaida = new byte[1];
            dado = new byte[Short.SIZE];
            buffIn.read(dado, 0, dado.length);
            
            switch(dado[0]){
                case 'c':
                    num = Short.valueOf(Character.toString((char)dado[1])+Character.toString((char)dado[2]));
                    if(tanque.pop(num))
                        dadoSaida[0] = 'c';
                    else
                        dadoSaida[0] = 'f';
                break;
                case 'p':
                    String aux = "";
                    for(int i = 1; i <= 5; i++)
                        aux += Character.toString((char)dado[i]);
                    num = Short.valueOf(aux);
                    if(tanque.push(num))
                        dadoSaida[0] = 'c';
                    else
                        dadoSaida[0] = 'f';
                break;
            }
            buffOut.write(dadoSaida, 0, dadoSaida.length);
            buffOut.flush(); 


        } catch (IOException ex) {
            System.out.println("Erro!"+ex);
        } 
    }
}
