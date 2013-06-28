/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientservlet;

import br.uniriotec.dasbuch.Cliente;
import br.uniriotec.dasbuch.DasbuchWS_Service;
import br.uniriotec.dasbuch.Endereco;
import br.uniriotec.dasbuch.Livro;
import br.uniriotec.dasbuch.ReciboTransporte;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author labccet
 */
@WebServlet(name = "DasbuchServlet", urlPatterns = {"/DasbuchServlet"})
public class DasbuchServlet extends HttpServlet {
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/DasbuchWS/DasbuchWS.wsdl")
    private DasbuchWS_Service service;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DasbuchServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DasbuchServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
            
        
        String pedido = "20678";
        String notaFiscal = "AFG-77898";
        Cliente cliente = new Cliente();
        Endereco retirada = new Endereco();
        Endereco entrega = new Endereco();
        Livro livro = new Livro();
        
        cliente.setNome("José Kanaam");
        cliente.setCpf("10877788800");
        
        livro.setTitulo("Trem Parador");
        livro.setAno("2012");
        livro.setEditora("Independente");
        livro.setIdioma("Português");
        livro.setAltura(15.00);
        livro.setLargura(10.00);
        livro.setComprimento(2.00);
        livro.setPeso(0.2);
        
        ReciboTransporte response2 = procederTransporte(pedido, notaFiscal, cliente, retirada, entrega, livro);
        
        out.println("Número do pedido de transporte: " + response2.getNumeroDoPedidoTransporte());
        out.println("Número do pedido de cliente: " + response2.getNumeroDoPedidoCliente());
        out.println("Data de retirada: " + response2.getDataRetirada());
        out.println("Data de entrega: " + response2.getDataEntrega());
        out.println("Custo do transporte: " + response2.getCusto());
        
        out.println(request.getParameter("nomeCliente"));
            
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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

    private ReciboTransporte procederTransporte(java.lang.String pedido, java.lang.String notaFiscal, br.uniriotec.dasbuch.Cliente cliente, br.uniriotec.dasbuch.Endereco retirada, br.uniriotec.dasbuch.Endereco entrega, br.uniriotec.dasbuch.Livro livro) {
        br.uniriotec.dasbuch.DasbuchWS port = service.getDasbuchWSPort();
        return port.procederTransporte(pedido, notaFiscal, cliente, retirada, entrega, livro);
    }
}
