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
import com.excilys.librarymanager.model.Livre;
import com.excilys.librarymanager.service.EmpruntService;
import com.excilys.librarymanager.service.EmpruntServiceImpl;
import com.excilys.librarymanager.service.LivreService;
import com.excilys.librarymanager.service.LivreServiceImpl;

/**
 * LivreDetailsServlet
 */
public class LivreDetailsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LivreService livreService = LivreServiceImpl.getInstance();
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();
        Livre livre = new Livre();
        List<Emprunt> emprunts = new ArrayList<>();

        int id = -1;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException | NullPointerException e) {
            throw new ServletException("la valeur de l'id n'est pas formatée correctement.");
        }

        try {
            if(id > -1) {
                livre = livreService.getById(id);
                emprunts = empruntService.getListCurrentByLivre(id);
            }
        } catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
        }

        HttpSession session = request.getSession(true);
        request.setAttribute("error", session.getAttribute("error"));
        session.removeAttribute("error");

        request.setAttribute("livre", livre);
        request.setAttribute("emprunts", emprunts);

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/View/livre_details.jsp");
        dispatcher.forward(request, response);
    }

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LivreService livreService = LivreServiceImpl.getInstance();

        try {  
            try {
                livreService.update(new Livre(
                    Integer.parseInt(request.getParameter("id")),
                    request.getParameter("titre"),
                    request.getParameter("auteur"),
                    request.getParameter("isbn")
                ));
                
                response.sendRedirect("livre_details?id=" + request.getParameter("id"));
            } catch (ServiceException e) {
                throw new ServletException(e.getMessage());
            }
        } catch (ServletException e) {
            HttpSession session = request.getSession(false);
            session.setAttribute("error", e.getMessage());
            response.sendRedirect("livre_details?id=" + request.getParameter("id"));
        }
        
    }
}