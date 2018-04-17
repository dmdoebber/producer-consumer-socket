/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consumidor;

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
public class consumidor extends Thread{
    private byte[] dado;
    private InetAddress ip;
    private int porta;
    private String servidor;
    private Socket socket;
    private BufferedOutputStream buffOut;
    private BufferedInputStream buffIn;
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

                buffOut = new BufferedOutputStream(socket.getOutputStream());
                buffIn = new BufferedInputStream(socket.getInputStream());

                int num = 10 + r.nextInt(41);
                dado = ("c"+String.valueOf(num)).getBytes();

                buffOut.write(dado, 0, dado.length);
                buffOut.flush();

                dado = new byte[1];            
                bytesLidos = buffIn.read(dado, 0, dado.length);

                switch(dado[0]){
                    case 'c':
                        totalAbastecido += num;
                        System.out.println("Consumidor["+numC+"]: abasteceu"+num+" litros!");
                    break;
                    case 'f':
                        System.out.println("O posto não possui mais combustivel\n"
                                + "Consumidor["+numC+"]: Abasteceu um total de "+ totalAbastecido + " litros!");
                        continua = false;
                }
                socket.close();
                Thread.sleep(100);
            }catch(Exception ex){
                System.out.println("Erro!"+ex);
            }  
        }
    }


}
