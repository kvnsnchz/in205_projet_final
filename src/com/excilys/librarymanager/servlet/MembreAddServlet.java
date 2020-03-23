package com.excilys.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.service.MembreService;
import com.excilys.librarymanager.service.MembreServiceImpl;
import com.excilys.librarymanager.utils.Util;

/**
 * MembreAddServlet
 */
public class MembreAddServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Util.setAttributesWithSession(request, new String[]{
            "error",
            "nom",
            "prenom",
            "adresse",
            "email",
            "telephone",
            "abonnement"
        });

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/View/membre_add.jsp");
        dispatcher.forward(request, response);
    }

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MembreService membreService = MembreServiceImpl.getInstance();

        try {
            try {
                int id = membreService.create(
                    request.getParameter("nom"),
                    request.getParameter("prenom"),
                    request.getParameter("adresse"),
                    request.getParameter("email"),
                    request.getParameter("telephone")
                );
                response.sendRedirect("membre_details?id=" + id);
            } catch (ServiceException e) {
                throw new ServletException(e.getMessage());
            }

        } catch (ServletException e) {
            HttpSession session = request.getSession(false);
            session.setAttribute("error", e.getMessage());
            session.setAttribute("nom", request.getParameter("nom"));
            session.setAttribute("prenom", request.getParameter("prenom"));
            session.setAttribute("adresse", request.getParameter("adresse"));
            session.setAttribute("email", request.getParameter("email"));
            session.setAttribute("telephone", request.getParameter("telephone"));
            session.setAttribute("abonnement", request.getParameter("abonnement"));
            response.sendRedirect("membre_add");
        }
        
    }
    
}