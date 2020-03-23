package com.excilys.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.model.Membre;
import com.excilys.librarymanager.service.MembreService;
import com.excilys.librarymanager.service.MembreServiceImpl;

/**
 * <b>MembreDeleteServlet</b>
 * Remove member viewer
 * 
 * @author  Kevin Sanchez <i>[kevin-alexandro,sanchez-diaz@ensta.fr]</i>
 * @version 1.0
 * @since   2020-03-23
 */
public class MembreDeleteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    
    /** 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MembreService membreService = MembreServiceImpl.getInstance();
        Membre membre = new Membre();
        
        int id = -1;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException | NullPointerException e) {
            throw new ServletException("la valeur de l'id n'est pas formatée correctement.");
        } 

        try {
            if(id > -1) {
                membre = membreService.getById(id);
            } 
        } catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
        }

        HttpSession session = request.getSession(true);
        request.setAttribute("error", session.getAttribute("error"));
        session.removeAttribute("error");

        request.setAttribute("membre", membre);

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/View/membre_delete.jsp");
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
        MembreService membreService = MembreServiceImpl.getInstance();
        
        try {
            try {
                membreService.delete(Integer.parseInt(request.getParameter("id")));
            } catch (ServiceException e) {
                throw new ServletException(e.getMessage());
            }

            response.sendRedirect("membre_list");
        } catch (ServletException e) {
            HttpSession session = request.getSession(false);
            session.setAttribute("error", e.getMessage());
            response.sendRedirect("membre_delete?id=" + request.getParameter("id"));
        }
        
    }
}