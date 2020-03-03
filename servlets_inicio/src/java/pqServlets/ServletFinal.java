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
            
            A usuario=Operacion.logIN(request.getParameter("nombre"),request.getParameter("codigo"));
            Operacion.s=request.getSession();
            if (usuario==null)
                processRequestExit(request, response);
            else{
                Operacion.setCliente(usuario);
                request.getRequestDispatcher("Base.html").include(request, response);
                out.println("<h1>"+usuario.getNombre()+"</h1>");

                List<B> transaccionesUsuario=Operacion.listarB(usuario.getId());
                out.println("<table class='table table-striped table-bordered text-center shadow'><thead class='thead-dark'><tr><th>Número</th><th>Producto</th><th>Lugar</th><th>Cantidad</th><th>Fecha</th><th>Estado</th></tr></thead><tbody>");
                for (B cliente: transaccionesUsuario) {
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
                    "<label for='amount'>Cantidad: <input type='text' id='amount' name='amount' placeholder='Cantidad'/></label><br><button type='submit'>Enviar</button><input type='hidden' name='Format' value='N'></form>");

                out.println("</div></body></html>");// Cerrar el include
            }
            
        }catch(Exception ex){
            processRequestExit(request, response);
        }
        
    }
    protected void processRequestModificar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            if (Operacion.getCliente()!=null){
                String fo= request.getParameter("Format");
                int transa = Integer.valueOf(fo.substring(2));
                B aModificar = Operacion.conseguirModificable(transa, Operacion.getCliente().getId());
                Operacion.setPresente(aModificar);
                if (!(aModificar==null)){
                    request.getRequestDispatcher("Base.html").include(request, response);
                    A usuario = Operacion.getCliente();
                    out.println("<h1>"+usuario.getNombre()+"</h1>");
                    out.println("<form action='ServletFinal' method='post' id='ReemplazoTransaccion' name='ReemplazoTransaccion'><label>Numero: "+aModificar.getNumerot()+"</label><br><label>Cliente: "+Operacion.getCliente().getNombre()+"</label><br>"+
                            "<label for='place'>Lugar de compra: <input type='text' id='place' name='place' value='"+aModificar.getLugar()+"'/></label><br>"+
                        "<label for='product'>Producto: <input type='text' id='product' name='product' value='"+aModificar.getProducto()+"'/></label><br>"+
                        "<label for='amount'>Cantidad: <input type='text' id='amount' name='amount' value='"+aModificar.getCantidad()+"'/></label><br><label for='fecha'>Fecha: <input type='date' id='fecha' name='fecha' value='"+aModificar.getFecha().toString()+"'/></label><br><label>Estado:</label><br>");
                    if (aModificar.getEstado())
                        out.println("<label for='EstadoYes'><input type='radio' id='EstadoYes' name='estado' value='true' checked/>Yes</label><br><label for='EstadoNo'><input type='radio' id='EstadoNo' name='estado' value='false' />No</label><br>");
                    else
                        out.println("<label for='EstadoYes'><input type='radio' id='EstadoYes' name='estado' value='true' />Yes</label><br><label for='EstadoNo'><input type='radio' id='EstadoNo' name='estado' value='false' checked/>No</label><br>"); 
                    out.println("<button type='submit'>Reemplazar</button><input type='hidden' name='Format' value='R'></form>");
                    
                    out.println("</div></body></html>");// Cerrar el include
                }else{
                    processRequestExit(request, response);
                }
                
            }else
                processRequestExit(request, response);
        }catch(Exception ex){
            processRequestExit(request, response);
        }
    }
    protected void processRequestRemplazar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            if (Operacion.getCliente()!=null){
                String startDateStr = request.getParameter("fecha");
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date f = sdf.parse(startDateStr);
                Operacion.modificarTransaccion(new B( Operacion.getPresente().getNumerot(),Operacion.getCliente().getId() , Operacion.conseguirC(request.getParameter("product")).getNombre(), Operacion.conseguirD(Integer.valueOf(request.getParameter("place"))).getNumero(), Integer.valueOf(request.getParameter("amount")), f ,Boolean.valueOf(request.getParameter("estado"))));
                processRequestBase(request, response);
            }else{
                processRequestExit(request, response);
            }
   
        }catch(Exception ex){
            processRequestExit(request, response);
        }
    }
    protected void processRequestBuscar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            if (Operacion.getCliente()!=null){
                    request.getRequestDispatcher("Base.html").include(request, response);
                    out.println("<form action='ServletFinal' method='post' id='Buscar' name='Buscar'><label for='search'>Buscar:<input type='text' id='search' name='search'/></label><br><label for='orden1'><input type='radio' id='orden1' name='orden' value='1' />Precio</label><br><label for='orden2'><input type='radio' id='orden2' name='orden' value='2' checked/>Nombre</label><br><label for='orden2'><input type='radio' id='orden3' name='orden' value='0' checked/>Ninguno</label><br><button type='submit'>Buscar</button><input type='hidden' name='Format' value='B'></form>");
                        
                    out.println("<table class='table table-striped table-bordered text-center shadow'><thead class='thead-dark'><tr><th>Número</th><th>Nombre</th><th>Lugar</th><th>Valor</th></tr></thead><tbody>");
                    String search = request.getParameter("search");
                    List lugares;
                    if (search==null || search.length()==0){
                        lugares=Operacion.listarD();
                    }else{
                        lugares=Operacion.filtrarD(search);
                    }
                    D lug;
                    for (Iterator it = lugares.iterator(); it.hasNext();) {
                        lug = (D)it.next();
                        out.println("<tr><td>"+lug.getNumero()+"</td><td>"+lug.getNombre()+"</td><td>"+lug.getLugar()+"</td><td>"+lug.getValor()+"</td>");
                        
                        out.println("</tr>");
                    }
                    out.println("</tbody></table><br>");
                    
                    out.println("<section class='container-fluid'>");
                    String orden = request.getParameter("orden");
                    List productos;
                    if ("0".equals(orden) || orden==null){
                        productos=Operacion.listarC();
                    }else{
                        productos=Operacion.listarCOrden(orden);
                    }
                    C pro;
                    for (Iterator it = productos.iterator(); it.hasNext();) {
                        pro = (C)it.next();
                        out.println("<article style='Border:2px;border-style: solid; border-color: black; display:inline-block;width: 10%;'>");
                        out.println("<p>"+pro.getNombre()+"</p><br><p>"+pro.getPrecio()+"</p><br><img src='"+pro.getImagen()+".jpg' alt='Image' height='20' width='20'>");
                        
                        out.println("</article>");
                    }
                    out.println("</section><br>");
                    
                    out.println("</div></body></html>");// Cerrar el include
                
            }else{
                processRequestExit(request, response);
            }
        }catch(Exception ex){
            processRequestExit(request, response);
        }
    }
    protected void processRequestBase(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            if (Operacion.getCliente()!=null){
                    A usuario = Operacion.getCliente();
                    request.getRequestDispatcher("Base.html").include(request, response);
                    out.println("<h1>"+usuario.getNombre()+"</h1>");
                    out.println("<table class='table table-striped table-bordered text-center shadow'><thead class='thead-dark'><tr><th>Número</th><th>Producto</th><th>Lugar</th><th>Cantidad</th><th>Fecha</th><th>Estado</th></tr></thead><tbody>");
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
                    out.println("</tbody></table><br>");
                    out.println("<form action='ServletFinal' method='post' id='FormTransaccion' name='FormTransaccion'>"+
                            "<label for='place'>Lugar de compra: <input type='text' id='place' name='place' placeholder='Lugar'/></label><br>"+///OPTION
                        "<label for='product'>Producto: <input type='text' id='product' name='product' placeholder='Producto'/></label><br>"+
                        "<label for='amount'>Cantidad: <input type='text' id='amount' name='amount' placeholder='Cantidad'/></label><br><button type='submit'>Enviar</button><input type='hidden' name='Format' value='N'></form>");

                    out.println("</div></body></html>");// Cerrar el include
                
            }else{
                processRequestExit(request, response);
            }
        }catch(Exception ex){
            processRequestExit(request, response);
        }
    }
    protected void processRequestHibernate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            if (Operacion.getCliente()!=null){
                    
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
            processRequestExit(request, response);
        }
    }
    protected void processRequestExit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            Operacion.setCliente(null);
            Operacion.s.invalidate();
            /*response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");*/
            request.getRequestDispatcher("index.html").include(request, response);
        }
    }
    
    protected void processRequestInsertar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            if (Operacion.getCliente()!=null){
                B insertado = new B();
                insertado.setCliente(Operacion.getCliente().getId());
                insertado.setProducto(Operacion.conseguirC(request.getParameter("product")).getNombre());
                insertado.setLugar(Operacion.conseguirD(Integer.valueOf(request.getParameter("place"))).getNumero());
                insertado.setCantidad(Integer.valueOf(request.getParameter("amount")));
                insertado.setFecha(new Date());
                insertado.setEstado(Boolean.FALSE);
                Operacion.altaTransaccion(insertado);
                processRequestBase(request, response);
            }else{
                processRequestExit(request, response);
            }
   
        }catch(Exception ex){
            processRequestExit(request, response);
        }
    }
    
    protected void processLimpiar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* TODO output your page here. You may use following sample code. */
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
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
            case 'I': processLimpiar(request, response);processRequestInicio(request, response); break;
            case 'M': processLimpiar(request, response);processRequestModificar(request, response); break;
            case 'R': processLimpiar(request, response);processRequestRemplazar(request, response); break;
            case 'B': processLimpiar(request, response);processRequestBuscar(request, response); break;
            case 'H': processLimpiar(request, response);processRequestHibernate(request, response); break;
            case 'O': processLimpiar(request, response);processRequestBase(request, response); break;
            case 'N': processLimpiar(request, response);processRequestInsertar(request, response); break;
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
//                                out.println("<script>alert('Hola')</script>");
}
