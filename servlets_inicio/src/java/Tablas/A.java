package Tablas;
// Generated Mar 2, 2020 7:35:40 PM by Hibernate Tools 4.3.1



/**
 * A generated by hbm2java
 */
public class A  implements java.io.Serializable {


     private String id;
     private String nombre;
     private String codigo;

    public A() {
    }

	
    public A(String id) {
        this.id = id;
    }
    public A(String id, String nombre, String codigo) {
       this.id = id;
       this.nombre = nombre;
       this.codigo = codigo;
    }
   
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getCodigo() {
        return this.codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }




}


