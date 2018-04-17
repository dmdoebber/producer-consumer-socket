/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 *
 * @author daniel
 */
public class posto extends Thread {
    int porta = 12345;
    //int numCon = 1;
  
    private DatagramSocket serverSocket; 
    
    private byte[] receiveData;
    private byte[] sendData;
    private tanque tanque;
    
    private DatagramPacket sendPacket, receivePacket;
    private InetAddress ip;
    
    public posto() throws SocketException{
        serverSocket = new DatagramSocket(porta); 
        receiveData = new byte[Short.SIZE];
        sendData = new byte[1];

        tanque = new tanque();
    }
    
    @Override
    public void run(){
        while(true){
            try {
                System.out.println("Aguardando requisicoes ...");
                receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                
                
                String aux;
                short num;
                switch(receiveData[0]){
                    case 'c':
                        
                        aux = "";
                        aux += Character.toString((char)receiveData[1])+Character.toString((char)receiveData[2]);
                        num = Short.valueOf(aux);
                        
                        if(tanque.pop(num))
                            sendData[0] = 'c';
                        else
                            sendData[0] = 'f';
                        System.out.println("Consumiu "+ num +"litros!");
                    break;
                    case 'p':
                        aux = "";
                        for(int i = 1; i <= 5; i++)
                            aux+= Character.toString((char)receiveData[i]);
                        num = Short.valueOf(aux);    
                        if(tanque.push(num))
                            sendData[0] = 'c';
                        else
                            sendData[0] = 'f';
                        
                    break;
                }
                
                porta = receivePacket.getPort();
                ip = receivePacket.getAddress();
                
                sendPacket = new DatagramPacket(sendData, sendData.length, ip, porta);
                serverSocket.send(sendPacket);                
           
            } catch (Exception ex) {
                System.out.println("Erro!"+ex);
            }
            
            
        }
    }
}
