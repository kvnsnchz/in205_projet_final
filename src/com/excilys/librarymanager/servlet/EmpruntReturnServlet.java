package com.excilys.librarymanager.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.model.Emprunt;
import com.excilys.librarymanager.service.EmpruntService;
import com.excilys.librarymanager.service.EmpruntServiceImpl;

/**
 * <b>EmpruntReturnServlet</b>
 * Returns lending viewer
 * 
 * @author  Kevin Sanchez <i>[kevin-alexandro,sanchez-diaz@ensta.fr]</i>
 * @version 1.0
 * @since   2020-03-23
 */
public class EmpruntReturnServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    
    /** 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();

        try {
            emprunts = empruntService.getListCurrent();
        } catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
        }

        HttpSession session = request.getSession(true);
        request.setAttribute("error", session.getAttribute("error"));
        session.removeAttribute("error");

        int id = -1;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException | NullPointerException e) {
            id = -1;
        } 

        request.setAttribute("id", id);
        request.setAttribute("emprunts", emprunts);

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/View/emprunt_return.jsp");
        dispatcher.forward(request, response);
    }
    
    
    /** 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();
    
        try {
            if (request.getParameter("id") == null) {
                throw new ServletException("Problème lors du retour du livre: vous devez sélectionner un livre");
            }

            int id = Integer.parseInt(request.getParameter("id"));

            try {
                empruntService.returnBook(id);
            } catch (ServiceException e) {
                throw new ServletException(e.getMessage());
            }
            response.sendRedirect("emprunt_list");
        } catch (ServletException e) {
            HttpSession session = request.getSession(false);
            session.setAttribute("error", e.getMessage());
            response.sendRedirect("emprunt_return");
        }
    }
}