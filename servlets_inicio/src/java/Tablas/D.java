package Tablas;
// Generated 26-feb-2020 20:48:27 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * D generated by hbm2java
 */
public class D  implements java.io.Serializable {


     private int numero;
     private String nombre;
     private String lugar;
     private Integer valor;
     private Set bs = new HashSet(0);

    public D() {
    }

	
    public D(int numero) {
        this.numero = numero;
    }
    public D(int numero, String nombre, String lugar, Integer valor, Set bs) {
       this.numero = numero;
       this.nombre = nombre;
       this.lugar = lugar;
       this.valor = valor;
       this.bs = bs;
    }
   
    public int getNumero() {
        return this.numero;
    }
    
    public void setNumero(int numero) {
        this.numero = numero;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getLugar() {
        return this.lugar;
    }
    
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
    public Integer getValor() {
        return this.valor;
    }
    
    public void setValor(Integer valor) {
        this.valor = valor;
    }
    public Set getBs() {
        return this.bs;
    }
    
    public void setBs(Set bs) {
        this.bs = bs;
    }




}

