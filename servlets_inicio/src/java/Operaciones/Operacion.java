/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Operaciones;

import Hibernate.NewHibernateUtil;
import Tablas.*;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author alumno
 */
public class Operacion {
    private static A Cliente=null;
    private static B presente=null;
    private static B modificado=null;
    public static HttpSession s=null;
    
    public static A getCliente() {
        return Cliente;
    }

    public static void setCliente(A Cliente) {
        Operacion.Cliente = Cliente;
    }

    public static B getPresente() {
        return presente;
    }

    public static void setPresente(B presente) {
        Operacion.presente = presente;
    }

    public static B getModificado() {
        return modificado;
    }

    public static void setModificado(B modificado) {
        Operacion.modificado = modificado;
    }
    
    public static void altaTransaccion(B enviado){
        SessionFactory sessionF = NewHibernateUtil.getSessionFactory();
        Session session= sessionF.openSession();
        Transaction tx = session.beginTransaction();
        session.save(enviado);
        tx.commit();
        session.close();
    }
    public static void modificarTransaccion(B nuevo){
        SessionFactory sessionF = NewHibernateUtil.getSessionFactory();
        Session session= sessionF.openSession();
        Transaction tx = session.beginTransaction();
        session.update(nuevo);
        tx.commit();
        session.close();
    }
    
    public static B conseguirModificable(int n, String cli){
        SessionFactory sessionF = NewHibernateUtil.getSessionFactory();
        Session session= sessionF.openSession();
        String hql = "FROM B where cliente= '"+cli+"' and numerot = "+n;
        Query query = session.createQuery(hql);
        List<B> result = (List<B>)query.list();
        session.close();
        if ((result == null) || (result.isEmpty())) {
            return null;
        } else {
            return result.get(0);
        }
    }
    public static C conseguirC(String nomb){
        SessionFactory sessionF = NewHibernateUtil.getSessionFactory();
        Session session= sessionF.openSession();
        String hql = "FROM C where nombre= '"+nomb+"'";
        Query query = session.createQuery(hql);
        List<C> result = (List<C>)query.list();
        session.close();
        if ((result == null) || (result.isEmpty())) {
            return null;
        } else {
            return result.get(0);
        }
    }
    
    public static D conseguirD(int num){
        SessionFactory sessionF = NewHibernateUtil.getSessionFactory();
        Session session= sessionF.openSession();
        String hql = "FROM D where numero="+num;
        Query query = session.createQuery(hql);
        List<D> result = (List<D>)query.list();
        session.close();
        if ((result == null) || (result.isEmpty())) {
            return null;
        } else {
            return result.get(0);
        }
    }
     
    public static List<C> listarC(){
        SessionFactory sessionF = NewHibernateUtil.getSessionFactory();
        Session session= sessionF.openSession();
        String hql = "FROM C";
        Query query = session.createQuery(hql);
        List<C> results = (List<C>)query.list();
        session.close();
        return results;
    }
    public static List<D> listarD(){
        SessionFactory sessionF = NewHibernateUtil.getSessionFactory();
        Session session= sessionF.openSession();
        String hql = "FROM D";
        Query query = session.createQuery(hql);
        List<D> results = (List<D>)query.list();
        session.close();
        return results;
    }
    public static List<B> listarB(String cliente){
        SessionFactory sessionF = NewHibernateUtil.getSessionFactory();
        Session session= sessionF.openSession();
        String hql = "FROM B where cliente='"+cliente+"'";
        Query query = session.createQuery(hql);
        List<B> results = (List<B>)query.list();
        session.close();
        return results;
    }

    public static List<C> listarCOrden(String tipo){
        SessionFactory sessionF = NewHibernateUtil.getSessionFactory();
        Session session= sessionF.openSession();
        String hql;
        if("1".equals(tipo))
            hql = "FROM C ORDER BY precio DESC";
        else
            hql = "FROM C ORDER BY nombre ASC";
        Query query = session.createQuery(hql);
        List<C> results = (List<C>)query.list();
        session.close();
        return results;
    }
    
    public static A logIN(String nombre,String codigo){
        SessionFactory sessionF = NewHibernateUtil.getSessionFactory();
        Session session= sessionF.openSession();
        String hql = "FROM A where nombre= '"+nombre+"' and codigo = '"+codigo+"'";
        Query query = session.createQuery(hql);
        List<A> result = (List<A>)query.list();
        session.close();
        if ((result == null) || (result.isEmpty())) {
            return null;
        } else {
            return result.get(0);
        }
    }
    public static List<D> filtrarD(String filtardo){
        SessionFactory sessionF = NewHibernateUtil.getSessionFactory();
        Session session= sessionF.openSession();
        String hql = "FROM D where nombre like '%"+filtardo+"%'";
        Query query = session.createQuery(hql);
        List<D> results = (List<D>)query.list();
        session.close();
        return results;
    }
}
