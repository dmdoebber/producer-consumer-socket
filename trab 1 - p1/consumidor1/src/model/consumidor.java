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
import java.net.UnknownHostException;
import java.util.Random;


/**
 *
 * @author daniel
 */
public class consumidor extends Thread{
    private DatagramPacket sendPacket, receivePacket;
    private DatagramSocket clientSocket; 
    
    private InetAddress ip;
    private final  String servidor;
    private final int porta;
    
    private byte[] sendData;
    private byte[] receiveData;
    
    private final int numC;
    private int consumo;
    
    private boolean continuar;
    
    private final Random rand;
    
    public consumidor(int numC) throws SocketException, UnknownHostException{
        sendData = new byte[Short.SIZE];
        receiveData = new byte[1];
        servidor  = "localhost";
        porta = 12345;
        rand = new Random();
        continuar = true;
        consumo = 0;
        this.numC = numC;
        
        
    }
    
    @Override
    public void run(){
        while(continuar){
            try {  
                clientSocket = new DatagramSocket();
                ip = InetAddress.getByName(servidor);
                
                int num = 10 + rand.nextInt(41);
                sendData = ("c"+String.valueOf(num)).getBytes();
                
                System.out.println("Consumidor["+numC+"]: requisitando consumo de "+num+" litros.");
                
                sendPacket = new DatagramPacket(sendData, sendData.length, ip, porta);                
                clientSocket.send(sendPacket);
                
                receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);
                receiveData = receivePacket.getData();
                
                switch(receiveData[0]){
                    case 'c':
                        consumo += num;
                    break;
                    
                    case 'f':
                        System.out.println("Consumo finalizado["+numC+"]\n"
                                + "foi consumido um total de "+consumo+" litros.");
                        continuar = false;
                    break;
                }
                Thread.sleep(100);
                clientSocket.close();
            } catch (Exception ex) {
                System.out.println("Erro!"+ex);
            }
        
            
        }    
    }    
}
