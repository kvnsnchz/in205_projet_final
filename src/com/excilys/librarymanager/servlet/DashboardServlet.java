package com.excilys.librarymanager.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.model.Emprunt;
import com.excilys.librarymanager.service.EmpruntService;
import com.excilys.librarymanager.service.EmpruntServiceImpl;
import com.excilys.librarymanager.service.LivreService;
import com.excilys.librarymanager.service.LivreServiceImpl;
import com.excilys.librarymanager.service.MembreService;
import com.excilys.librarymanager.service.MembreServiceImpl;

/**
 * DashboardServlet
 */
public class DashboardServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LivreService livreService = LivreServiceImpl.getInstance();
        MembreService membreService = MembreServiceImpl.getInstance();
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();

        int numberOfLivres = 0;
        int numberOfMembres = 0;
        int numberOfEmprunts = 0;
        List<Emprunt> emprunts = new ArrayList<>();
        
		try {
            numberOfLivres = livreService.count();
            numberOfMembres = membreService.count();
            numberOfEmprunts = empruntService.count();
			emprunts =  empruntService.getListCurrent();
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
        }
        
		request.setAttribute("numberOfLivres", numberOfLivres);
		request.setAttribute("numberOfMembres", numberOfMembres);
		request.setAttribute("numberOfEmprunts", numberOfEmprunts);
		request.setAttribute("emprunts", emprunts);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/View/dashboard.jsp");
        dispatcher.forward(request, response);
    }
}