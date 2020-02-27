/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pqServlets;

import Tablas.*;
import Operaciones.Operacion;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
@WebServlet(name = "ServletFinal", urlPatterns = {"/ServletFinal"})
public class ServletFinal extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequestInicio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            if (Operacion.getCliente()==null){
                A usuario=Operacion.logIN(request.getParameter("nombre"),request.getParameter("codigo"));
                Operacion.s=request.getSession();
                if (usuario==null)
                    processRequestExit(request, response);
                else{
                    Operacion.setCliente(usuario);
                    request.getRequestDispatcher("Base.html").include(request, response);
                    out.println("<h1>"+usuario.getNombre()+"</h1>");
                    out.println("<table class='table table-striped table-bordered text-center shadow'><thead class='thead-dark'><tr><th>Número</th><th>Producto</th><th>Lugar</th><th>Cantidad</th><th>Fecha</th><th>Estado</th></tr></thead><tbody>");
                    List<B> transaccionesUsuario=Operacion.listarB(usuario.getId());
                    B cliente;
                    for (Iterator it = transaccionesUsuario.iterator(); it.hasNext();) {
                        cliente = (B)it.next();
                        out.println("<tr><td>"+cliente.getNumerot()+"</td><td>"+cliente.getProducto()+"</td><td>"+cliente.getLugar()+"</td><td>"+cliente.getCantidad()+"</td><td>"+cliente.getFecha()+"</td><td>");
                        if (!cliente.getEstado()){
                            out.println("<form action='ServletFinal' method='post' id='ModificarTransaccion' name='ModificarTransaccion'><input type='hidden' name='Format' value='M-"+cliente.getNumerot()+"'><button type='submit'>Modificar</button></form>");
                        }
                        out.println("</td></tr>");
                    }
                    out.println("</tbody></table><br>");
                    out.println("<form action='ServletFinal' method='post' id='FormTransaccion' name='FormTransaccion'>"+
                            "<label for='place'>Lugar de compra: <input type='text' id='place' name='place' placeholder='Lugar'/></label><br>"+///OPTION
                        "<label for='product'>Producto: <input type='text' id='product' name='product' placeholder='Producto'/></label><br>"+
                        "<label for='amount'>Cantidad: <input type='text' id='amount' name='amount' placeholder='Cantidad'/></label><br><button type='submit'>Enviar</button><input type='hidden' name='Format' value='I'></form>");

                    out.println("</div></body></html>");// Cerrar el include
                }
            }else{
                processRequestExit(request, response);
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Ocurrio un error");
            processRequestExit(request, response);
        }
        
    }
    protected void processRequestModificar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            if (Operacion.getCliente()==null){
                String fo= request.getParameter("Format");
                int transa = Integer.valueOf(fo.substring(2, fo.length()-1));
                B aModificar = Operacion.conseguirModificable(transa, Operacion.getCliente().getId());
                Operacion.setPresente(aModificar);
                if (!(aModificar==null)){
                    request.getRequestDispatcher("Base.html").include(request, response);
                    A usuario = Operacion.getCliente();
                    out.println("<h1>"+usuario.getNombre()+"<h1>");
                    out.println("<form action='ServletNombre' method='post' id='ReemplazoTransaccion' name='ReemplazoTransaccion'><label>Numero: "+aModificar.getNumerot()+"</label><br><label>Cliente: "+Operacion.getCliente().getNombre()+"</label><br>"+
                            "<label for='place'>Lugar de compra: <input type='text' id='place' name='place' placeholder='Lugar'/></label><br>"+
                        "<label for='product'>Producto: <input type='text' id='product' name='product' placeholder='Producto'/></label><br>"+
                        "<label for='amount'>Cantidad: <input type='text' id='amount' name='amount' placeholder='Cantidad'/></label><br><label for='fecha'>Fecha: <input type='date' id='fecha' name='fecha'/></label><br><label>Estado:</label><br>");
                    if (aModificar.getEstado())
                        out.println("<label for='EstadoYes'><input type='radio' id='EstadoYes' name='estado' value='true' checked/>Yes</label><br><label for='EstadoNo'><input type='radio' id='EstadoNo' name='estado' value='false' />No</label><br>");
                    else
                        out.println("<label for='EstadoYes'><input type='radio' id='EstadoYes' name='estado' value='true' />Yes</label><br><label for='EstadoNo'><input type='radio' id='EstadoNo' name='estado' value='false' checked/>No</label><br>"); 
                    out.println("<button type='submit'>Reemplazar</button><input type='hidden' name='Format' value='R'></form>");
                    
                    out.println("</div></body></html>");// Cerrar el include
                }else{
                    JOptionPane.showMessageDialog(null, "Ocurrio un error");
                    processRequestExit(request, response);
                }
                
            }else
                processRequestExit(request, response);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Ocurrio un error");
            processRequestExit(request, response);
        }
    }
    protected void processRequestRemplazar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            if (Operacion.getCliente()==null){
                String startDateStr = request.getParameter("fecha");
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                //surround below line with try catch block as below code throws checked exception
                Date f = sdf.parse(startDateStr);
                Operacion.modificarTransaccion(new B( Operacion.getModificado().getNumerot(), Operacion.getCliente().getId(), Operacion.conseguirC(request.getParameter("product")).getNombre(), Operacion.conseguirD(Integer.valueOf(request.getParameter("place"))).getNumero(), Integer.valueOf(request.getParameter("amount")), f,Boolean.valueOf(request.getParameter("estado"))));
                processRequestBase(request, response);
            }else{
                JOptionPane.showMessageDialog(null, "Ocurrio un error");
                processRequestExit(request, response);
            }
   
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Ocurrio un error");
            processRequestExit(request, response);
        }
    }
    protected void processRequestBuscar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServletFinal</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServletFinal at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            //request.getRequestDispatcher("Base.html").include(request, response);
            out.println("</html>");
        }
    }
    protected void processRequestBase(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            if (Operacion.getCliente()==null){
                    A usuario = Operacion.getCliente();
                    request.getRequestDispatcher("Base.html").include(request, response);
                    out.println("<h1>"+usuario.getNombre()+"<h1>");
                    out.println("<table class='table table-striped table-bordered text-center shadow'><thead class='thead-dark'><tr><th>Número</th><th>Producto</th><th>Lugar</th><th>Cantidad</th><th>Fecha</th><th>Estado</th></tr></thead>");
                    List transaccionesUsuario=Operacion.listarB(usuario.getId());
                    B cliente;
                    for (Iterator it = transaccionesUsuario.iterator(); it.hasNext();) {
                        cliente = (B)it.next();
                        out.println("<tr><td>"+cliente.getNumerot()+"</td><td>"+cliente.getProducto()+"</td><td>"+cliente.getLugar()+"</td><td>"+cliente.getCantidad()+"</td><td>"+cliente.getFecha()+"</td><td>");
                        if (!cliente.getEstado()){
                            out.println("<form action='ServletFinal' method='post' id='ModificarTransaccion' name='ModificarTransaccion'><input type='hidden' name='Format' value='M-"+cliente.getNumerot()+"'><button type='submit'>Modificar</button></form>");
                        }
                        out.println("</td></tr>");
                    }
                    out.println("</table><br>");
                    out.println("<form action='ServletFinal' method='post' id='FormTransaccion' name='FormTransaccion'>"+
                            "<label for='place'>Lugar de compra: <input type='text' id='place' name='place' placeholder='Lugar'/></label><br>"+///OPTION
                        "<label for='product'>Producto: <input type='text' id='product' name='product' placeholder='Producto'/></label><br>"+
                        "<label for='amount'>Cantidad: <input type='text' id='amount' name='amount' placeholder='Cantidad'/></label><br><button type='submit'>Enviar</button><input type='hidden' name='Format' value='I'></form>");

                    out.println("</div></body></html>");// Cerrar el include
                
            }else{
                processRequestExit(request, response);
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Ocurrio un error");
            processRequestExit(request, response);
        }
    }
    protected void processRequestHibernate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            if (Operacion.getCliente()==null){
                    
                    request.getRequestDispatcher("Base.html").include(request, response);
                    out.println("Para el funcionamiento del hibernate se tuvo que crear la base de datos Postgres<br>Crear un paquete nuevo llamado Hibernate<br>");
                    out.println("En este paquete crear (en la opcion Other, Hibernate), hibernate configutation wizard, reverse engineeting wizard y mapping files and POJO's<br>");
                    out.println("Al cerarlos en ese orden se creara la conexion a la base de datos, interpretar las tablas(clases) que seran neseasrias de estas y la creacion de estas clases<br>");
                    out.println("Aquí he optado por crear una clase que tiene metodos estaticos para cada operacion<br>");

                    out.println("</div></body></html>");// Cerrar el include
                
            }else{
                processRequestExit(request, response);
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Ocurrio un error");
            processRequestExit(request, response);
        }
    }
    protected void processRequestExit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            Operacion.setCliente(null);
            Operacion.s.invalidate();
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            request.getRequestDispatcher("index.html").include(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequestExit(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
        
        char primero = request.getParameter("Format").charAt(0);
        switch (primero){
            case 'I': processRequestInicio(request, response); break;
            case 'M': processRequestModificar(request, response); break;
            case 'R': processRequestRemplazar(request, response); break;
            case 'B': processRequestBuscar(request, response); break;
            case 'H': processRequestHibernate(request, response); break;
            case 'O': processRequestBase(request, response); break;
            default: processRequestExit(request, response); break;
        }
        }catch(Exception ex){
            processRequestExit(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Interpreta todo uso de Post en la página web.";
    }// </editor-fold>

}
