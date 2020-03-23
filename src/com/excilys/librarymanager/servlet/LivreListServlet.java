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
import com.excilys.librarymanager.model.Livre;
import com.excilys.librarymanager.service.LivreService;
import com.excilys.librarymanager.service.LivreServiceImpl;

/**
 * LivreListServlet
 */
public class LivreListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LivreService livreService = LivreServiceImpl.getInstance();
        List<Livre> livres = new ArrayList<>();
        
        try {
            livres = livreService.getList();
        } catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
        }

        request.setAttribute("livres", livres);

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/View/livre_list.jsp");
        dispatcher.forward(request, response);
    }
    
}