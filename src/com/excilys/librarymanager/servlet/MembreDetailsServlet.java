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
import com.excilys.librarymanager.model.Membre;
import com.excilys.librarymanager.service.EmpruntService;
import com.excilys.librarymanager.service.EmpruntServiceImpl;
import com.excilys.librarymanager.service.MembreService;
import com.excilys.librarymanager.service.MembreServiceImpl;
import com.excilys.librarymanager.utils.Abonnement;

/**
 * MembreDetailsServlet
 */
public class MembreDetailsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MembreService membreService = MembreServiceImpl.getInstance();
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();
        Membre membre = new Membre();
        List<Emprunt> emprunts = new ArrayList<>();

        int id = -1;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException | NullPointerException e) {
            throw new ServletException("la valeur de l'id n'est pas formatée correctement.");
        }

        try {
            if(id > -1) {
                membre = membreService.getById(id);
                emprunts = empruntService.getListCurrentByMembre(id);
            }
        } catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
        }

        HttpSession session = request.getSession(true);
        request.setAttribute("error", session.getAttribute("error"));
        session.removeAttribute("error");

        request.setAttribute("membre", membre);
        request.setAttribute("emprunts", emprunts);

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/View/membre_details.jsp");
        dispatcher.forward(request, response);
    }

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MembreService membreService = MembreServiceImpl.getInstance();

        try {  
            try {
                membreService.update(new Membre(
                    Integer.parseInt(request.getParameter("id")),
                    request.getParameter("nom"),
                    request.getParameter("prenom"),
                    request.getParameter("adresse"),
                    request.getParameter("email"),
                    request.getParameter("telephone"),
                    Abonnement.valueOf(request.getParameter("abonnement"))
                ));
                
                response.sendRedirect("membre_details?id=" + request.getParameter("id"));
            } catch (ServiceException e) {
                throw new ServletException(e.getMessage());
            }
        } catch (ServletException e) {
            HttpSession session = request.getSession(false);
            session.setAttribute("error", e.getMessage());
            response.sendRedirect("membre_details?id=" + request.getParameter("id"));
        }
        
    }
}