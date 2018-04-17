/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posto;

/**
 *
 * @author daniel
 */
public class tanque {
    private final int CAP_MAX = 100000;
    private int tanque;
    
    public tanque(){
        tanque = 100;
    }
    
    public synchronized boolean push(short valor){
        if(tanque + valor < CAP_MAX){
            tanque += valor;
            System.out.println("O tanque possui "+tanque+ " litros!");
            return true;
        }else
            return false;
    }
    
    public synchronized boolean pop(short valor){
        if(tanque > valor){
            tanque -= valor;
             System.out.println("O tanque possui "+tanque+ " litros!");
            return true;
        }else
            return false;            
    } 
}
