package com.excilys.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.service.LivreService;
import com.excilys.librarymanager.service.LivreServiceImpl;

/**
 * LivreAddServlet
 */
public class LivreAddServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        request.setAttribute("error", session.getAttribute("error"));
        request.setAttribute("titre", session.getAttribute("titre"));
        request.setAttribute("auteur", session.getAttribute("auteur"));
        request.setAttribute("isbn", session.getAttribute("isbn"));
        session.removeAttribute("error");
        session.removeAttribute("titre");
        session.removeAttribute("auteur");
        session.removeAttribute("isbn");

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/View/livre_add.jsp");
        dispatcher.forward(request, response);
    }

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LivreService livreService = LivreServiceImpl.getInstance();

        try {
            try {
                int id = livreService.create(
                    request.getParameter("titre"),
                    request.getParameter("auteur"),
                    request.getParameter("isbn")
                );
                response.sendRedirect("livre_details?id=" + id);
            } catch (ServiceException e) {
                throw new ServletException(e.getMessage());
            }

        } catch (ServletException e) {
            HttpSession session = request.getSession(false);
            session.setAttribute("error", e.getMessage());
            session.setAttribute("titre", request.getParameter("titre"));
            session.setAttribute("auteur", request.getParameter("auteur"));
            session.setAttribute("isbn", request.getParameter("isbn"));
            response.sendRedirect("livre_add");
        }
        
    }
    
}