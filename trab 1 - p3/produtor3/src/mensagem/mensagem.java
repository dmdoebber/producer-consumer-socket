package mensagem;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;

/**
 *
 * @author daniel
 */
public class mensagem implements Serializable{
   
    /** Tipos de mensagens POP, PUSH, RET_POP */
    public String tipo;
    public short numero;
    public mensagem(String tipo, short numero) {
        this.tipo = tipo;
        this.numero = numero;
    }
    
    public mensagem() {
        this.tipo = "";
        this.numero = (short)0;
    }
    
    public void setTipo(String t) { tipo = t; }
    public String getTipo() { return tipo; }
    public void setNumero(short n) { numero = n; }
    public short getNumero() { return numero; }    
}
