/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package produtor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author daniel
 */
public class produtor extends Thread{
    private byte[] dado;
    private InetAddress ip;
    private int porta;
    private String servidor;
    private Socket socket;
    private BufferedOutputStream buffOut;
    private BufferedInputStream buffIn;
    private int bytesLidos;

    private boolean continua;
    private int totalProduzido;
    private int timeWait;
    
    public produtor(){
        dado = new byte[Short.SIZE];
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

                buffOut = new BufferedOutputStream(socket.getOutputStream());
                buffIn = new BufferedInputStream(socket.getInputStream());

                dado = "p10000".getBytes();

                buffOut.write(dado, 0, dado.length);
                buffOut.flush();

                dado = new byte[1];            
                bytesLidos = buffIn.read(dado, 0, dado.length);

                switch(dado[0]){
                    case 'c':
                        totalProduzido += 10000;
                        System.out.println("Produziu 10000 litros!");
                    break;
                    case 'f':
                        System.out.println("Foram produzidos "+ totalProduzido + " litros!");
                        continua = false;
                }
                socket.close();
                Thread.sleep(timeWait*1000);
            }catch(Exception ex){
                System.out.println("Erro!"+ex);
            }  
        }
    }
}
