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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DasbuchServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DasbuchServlet - livraria Virtual at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
            
        String pedido = request.getParameter("pedidoLivraria");
        String notaFiscal = request.getParameter("notaLivraria");
        
        Endereco retirada = new Endereco();
        retirada.setLogradouro(request.getParameter("logradouroRetirada"));
        retirada.setNumero(request.getParameter("numeroRetirada"));
        retirada.setComplemento(request.getParameter("complementoRetirada"));
        retirada.setBairro(request.getParameter("bairroRetirada"));
        retirada.setCidade(request.getParameter("cidadeRetirada"));
        retirada.setEstado(request.getParameter("estadoRetirada"));
        
        Endereco entrega = new Endereco();
        entrega.setLogradouro(request.getParameter("logradouroEntrega"));
        entrega.setNumero(request.getParameter("numeroEntrega"));
        entrega.setComplemento(request.getParameter("complementoEntrega"));
        entrega.setBairro(request.getParameter("bairroEntrega"));
        entrega.setCidade(request.getParameter("cidadeEntrega"));
        entrega.setEstado(request.getParameter("estadoEntrega"));
        
        Cliente cliente = new Cliente();
        cliente.setCpf(request.getParameter("cpfCliente"));
        cliente.setNome(request.getParameter("nomeCliente"));
        cliente.setEmail(request.getParameter("emailCliente"));
        cliente.setTelefone(request.getParameter("telefoneCliente"));
        cliente.setEndereco(entrega);
        
        Livro livro = new Livro();
        livro.setIsbn(request.getParameter("isbnLivro"));
        livro.setTitulo(request.getParameter("nomeLivro"));
        livro.setComprimento(Double.valueOf(request.getParameter("comprimentoLivro")));
        livro.setLargura(Double.valueOf(request.getParameter("larguraLivro")));
        livro.setAltura(Double.valueOf(request.getParameter("alturaLivro")));
        livro.setPeso(Double.valueOf(request.getParameter("pesoLivro")));
        
        ReciboTransporte response2 = procederTransporte(pedido, notaFiscal, cliente, retirada, entrega, livro);
        
        out.println("Número do pedido de transporte: " + response2.getNumeroDoPedidoTransporte());
        out.println("Número do pedido de cliente: " + response2.getNumeroDoPedidoCliente());
        out.println("Data de retirada: " + response2.getDataRetirada().toString());
        out.println("Data de entrega: " + response2.getDataEntrega().toString());
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
