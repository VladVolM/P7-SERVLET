/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Operaciones;

import Hibernate.NewHibernateUtil;
import Tablas.A;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author alumno
 */
public class Operacion {
    public static A presente;
    public static A modificado;
    
    public void altaTransaccion(A enviado){
        SessionFactory sessionF = NewHibernateUtil.getSessionFactory();
        Session session= sessionF.openSession();
        Transaction tx = session.beginTransaction();
        session.save(enviado);
        tx.commit();
        session.close();
        JOptionPane.showMessageDialog(null, "Transaccion Insertada");
    }
    public void modificarTransaccion(A nuevo){
        SessionFactory sessionF = NewHibernateUtil.getSessionFactory();
        Session session= sessionF.openSession();
        Transaction tx = session.beginTransaction();
        session.update(nuevo);
        tx.commit();
        session.close();
        JOptionPane.showMessageDialog(null, "Transaccion Modificada");
    }
    
    public List listarC(){
        SessionFactory sessionF = NewHibernateUtil.getSessionFactory();
        Session session= sessionF.openSession();
        String hql = "FROM C";
        Query query = session.createQuery(hql);
        List results = query.list();
        return results;
    }
    public List listarD(){
        SessionFactory sessionF = NewHibernateUtil.getSessionFactory();
        Session session= sessionF.openSession();
        String hql = "FROM D";
        Query query = session.createQuery(hql);
        List results = query.list();
        return results;
    }
    public List listarB(String cliente){
        SessionFactory sessionF = NewHibernateUtil.getSessionFactory();
        Session session= sessionF.openSession();
        String hql = "FROM B where C="+cliente;
        Query query = session.createQuery(hql);
        List results = query.list();
        return results;
    }
    /*
    for(Employee emp:emplist){
		System.out.println("Child---->"+emp);
		//get all childs of each parent
			Department dept=emp.getDept();
			System.out.println("Parent--->"+dept);
		//for(PhoneNumber ph:childs){
			
		//}//for
	
		}//for
    */
    public List listarCOrden(int tipo){
        SessionFactory sessionF = NewHibernateUtil.getSessionFactory();
        Session session= sessionF.openSession();
        String hql;
        if(tipo==1)
            hql = "FROM C ORDER BY precio DESC";
        else
            hql = "FROM C ORDER BY nombre ASC";
        Query query = session.createQuery(hql);
        List results = query.list();
        return results;
    }
}
