package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Productos;
import modelo.ProductosDAO;

/**
 *
 * @author Miri
 */
@WebServlet(name = "ProductosController", urlPatterns = {"/ProductosController"})
public class ProductosController extends HttpServlet {

 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
            ProductosDAO productosDAO = new ProductosDAO();
            String accion;
            RequestDispatcher dispatcher= null;
            
            accion = request.getParameter("accion");
            
            if(accion==null || accion.isEmpty()){
                
                dispatcher= request.getRequestDispatcher("Productos/index.jsp");
                List <Productos> listaProductos = productosDAO.listarProductos();
                request.setAttribute("lista", listaProductos);
            }else if("nuevo".equals(accion)){
                dispatcher= request.getRequestDispatcher("Productos/nuevo.jsp");
            }else if("insertar".equals(accion)){
                String codigo=request.getParameter("codigo");
                String nombre=request.getParameter("nombre");
                Double precio=Double.parseDouble(request.getParameter("precio"));
                int existencia=Integer.parseInt(request.getParameter("existencia"));
                
                Productos producto = new Productos(0,codigo,nombre,precio,existencia);
                
                productosDAO.insertar(producto);
                dispatcher= request.getRequestDispatcher("Productos/index.jsp");
                List <Productos> listaProductos = productosDAO.listarProductos();
                request.setAttribute("lista", listaProductos);
            }else if("modificar".equals(accion)){
                dispatcher= request.getRequestDispatcher("Productos/modificar.jsp");
                int id=Integer.parseInt(request.getParameter("id"));
                
                Productos producto= productosDAO.mostrarProducto(id);
                request.setAttribute("producto", producto);
            }else if("actualizar".equals(accion)){
                int id=Integer.parseInt(request.getParameter("id"));
                String codigo=request.getParameter("codigo");
                String nombre=request.getParameter("nombre");
                Double precio=Double.parseDouble(request.getParameter("precio"));
                int existencia=Integer.parseInt(request.getParameter("existencia"));
                
                Productos producto = new Productos(id,codigo,nombre,precio,existencia);
                
                productosDAO.actualizar(producto);
                dispatcher= request.getRequestDispatcher("Productos/index.jsp");
                List <Productos> listaProductos = productosDAO.listarProductos();
                request.setAttribute("lista", listaProductos);
            }else if("eliminar".equals(accion)){
                int id=Integer.parseInt(request.getParameter("id"));                
                productosDAO.eliminar(id);
                System.out.println("skajdaks");
                dispatcher= request.getRequestDispatcher("Productos/index.jsp");
                List <Productos> listaProductos = productosDAO.listarProductos();
                request.setAttribute("lista", listaProductos);
            }else{
                dispatcher= request.getRequestDispatcher("Productos/index.jsp");
                List <Productos> listaProductos = productosDAO.listarProductos();
                request.setAttribute("lista", listaProductos);
            }
            
            dispatcher.forward(request, response);
       
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
