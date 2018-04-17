/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 *
 * @author daniel
 */
public class produtor extends Thread {
    private DatagramPacket sendPacket, receivePacket;
    private DatagramSocket clientSocket; 

    private InetAddress ip;
    private String servidor;
    private int porta;

    private byte[] sendData;
    private byte[] receiveData;

    private final int prod = 10000;
    private int timeWait;
    private int totalProduzido;

    private boolean continuar;

    public produtor(){
        sendData = new byte[Short.SIZE];
        receiveData = new byte[1];

        servidor = "localhost";
        porta = 12345;

        totalProduzido = 0;
        continuar = true;
    }   

   @Override
   public void run(){
       Scanner ler = new Scanner(System.in);
       System.out.print("Digite o tempo de espera entre producoes(em segundos):");
       timeWait = ler.nextInt();
       while(continuar){
           try{
                clientSocket = new DatagramSocket();
                ip = InetAddress.getByName(servidor);

                sendData = ("p"+String.valueOf(prod)).getBytes();
                sendPacket = new DatagramPacket(sendData, sendData.length, ip, porta);
                clientSocket.send(sendPacket);
                System.out.println("Tentativa de abastecimento!");
                

                receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);
                receiveData = receivePacket.getData();
                
                switch(receiveData[0]){
                    case 'c':
                        totalProduzido += prod;
                        System.out.println("Abastecimento concluido com sucesso!");
                    break;
                    
                    case 'f':
                        System.out.println("Producao finalizada! "
                                + "foi produzido um total de "+totalProduzido+" litros.");
                        continuar = false;
                    break;
                }
                
                clientSocket.close();
                Thread.sleep(timeWait * 1000);

           }catch(Exception ex){
               System.out.println("Erro!"+ex);
           }
       }

   } 
}
