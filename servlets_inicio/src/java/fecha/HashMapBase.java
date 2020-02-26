package fecha;

import java.util.HashMap;

public class HashMapBase {

    static public HashMap<Long, SocioDatos> UsuariosBase = new HashMap<Long, SocioDatos>();
    static boolean estado =false,encontrado; 

    private static long contador=16;

    public static long getContador() {
        return contador;
    }


    public HashMapBase(boolean x) {
        
    }
    public HashMapBase() {
		SocioDatos s;
		s = new SocioDatos("Yura","Z",680,"1999/04/15");
		UsuariosBase.put(1l,s);
		s = new SocioDatos("Anton","A",789,"2001/02/10");
		UsuariosBase.put(2l,s);
		s = new SocioDatos("Petrov","B",978,"2001/06/25");
		UsuariosBase.put(3l,s);
		s = new SocioDatos("Dimitriev","C",897,"2002/08/07");
		UsuariosBase.put(4l,s);
		s = new SocioDatos("Oleganovich","D",823,"2002/05/03");
		UsuariosBase.put(5l,s);
		s = new SocioDatos("Stanislav","E",983,"2003/01/21");
		UsuariosBase.put(6l,s);
		s = new SocioDatos("Gregori","F",936,"2003/09/22");
		UsuariosBase.put(7l,s);
		s = new SocioDatos("Sasha","G",712,"2004/10/13");
		UsuariosBase.put(8l,s);
		s = new SocioDatos("Max","H",1033,"2012/03/18");
		UsuariosBase.put(9l,s);
		s = new SocioDatos("Ian","I",1178,"2012/07/29");
		UsuariosBase.put(10l,s);
		s = new SocioDatos("Sean","J",1198,"2014/06/14");
		UsuariosBase.put(11l,s);
		s = new SocioDatos("Jack","K",1096,"2014/09/15");
		UsuariosBase.put(12l,s);
		s = new SocioDatos("Mark","L",1214,"2015/02/04");
		UsuariosBase.put(13l,s);
		s = new SocioDatos("Felix","M",1256,"2015/01/07");
		UsuariosBase.put(14l,s);
		s = new SocioDatos("Ethan","N",1349,"2019/11/01");
		UsuariosBase.put(15l,s);
                estado =true; 
    } 
    
    public static HashMap<Long, SocioDatos> getUsuariosBase() {
        return UsuariosBase;
    }
    
    public static void añadirUsuario(SocioDatos s) {
        encontrado=false;
        UsuariosBase.entrySet().stream().filter((socio) -> (socio.getValue().getNombre().equals(s.getNombre()))).forEachOrdered((_item) -> {
            encontrado=true;
        });
        if(!encontrado){
            UsuariosBase.put(contador,s);
            contador++;
        }
    }
    public static boolean buscarSocio(String x){
        encontrado=false;
        UsuariosBase.entrySet().stream().filter((socio) -> (socio.getValue().getNombre().equals(x))).forEachOrdered((_item) -> {
            encontrado=true;
        });
        return encontrado;
    }
    
    public static SocioDatos consegirUsuario(Long x) {
        return UsuariosBase.get(x);
    }
    public static boolean getEstado(){
        return estado;
    }
}
