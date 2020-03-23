package com.excilys.librarymanager.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.model.Livre;
import com.excilys.librarymanager.model.Membre;
import com.excilys.librarymanager.service.EmpruntService;
import com.excilys.librarymanager.service.EmpruntServiceImpl;
import com.excilys.librarymanager.service.LivreService;
import com.excilys.librarymanager.service.LivreServiceImpl;
import com.excilys.librarymanager.service.MembreService;
import com.excilys.librarymanager.service.MembreServiceImpl;

/**
 * <b>EmpruntAddServlet</b>
 * Add lending viewer
 * 
 * @author  Kevin Sanchez <i>[kevin-alexandro,sanchez-diaz@ensta.fr]</i>
 * @version 1.0
 * @since   2020-03-23
 */
public class EmpruntAddServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    
    /** 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LivreService livreService = LivreServiceImpl.getInstance();
        MembreService membreService = MembreServiceImpl.getInstance();
        
        List<Livre> livres = new ArrayList<>();
        List<Membre> membres = new ArrayList<>();

        try {
            livres = livreService.getListDispo();
            membres = membreService.getListMembreEmpruntPossible();    
        } catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
        }

        HttpSession session = request.getSession(true);
        request.setAttribute("error", session.getAttribute("error"));
        request.setAttribute("idMembre", session.getAttribute("idMembre"));
        request.setAttribute("idLivre", session.getAttribute("idLivre"));
        session.removeAttribute("error");
        session.removeAttribute("idMembre");
        session.removeAttribute("idLivre");

        request.setAttribute("livres", livres);
        request.setAttribute("membres", membres);

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/View/emprunt_add.jsp");
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
            if (request.getParameter("idMembre") == null || request.getParameter("idLivre") == null) {
                throw new ServletException("Problème lors de la création du emprunt: vous devez sélectionner un livre et un membre");
            }
    
            int idMembre = Integer.parseInt(request.getParameter("idMembre"));
            int idLivre = Integer.parseInt(request.getParameter("idLivre"));
            
            try {
                empruntService.create(idMembre, idLivre, LocalDate.now());
            } catch (ServiceException e) {
                throw new ServletException(e.getMessage());
            }
            response.sendRedirect("emprunt_list");
        } catch (ServletException e) {
            HttpSession session = request.getSession(false);
            session.setAttribute("idMembre", request.getParameter("idMembre"));
            session.setAttribute("idLivre", request.getParameter("idLivre"));
            session.setAttribute("error", e.getMessage());
            response.sendRedirect("emprunt_add");
        }
        
	}

}