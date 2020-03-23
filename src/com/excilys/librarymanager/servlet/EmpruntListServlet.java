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

/**
 * <b>EmpruntListServlet</b>
 * List lending viewer
 * 
 * @author  Kevin Sanchez <i>[kevin-alexandro,sanchez-diaz@ensta.fr]</i>
 * @version 1.0
 * @since   2020-03-23
 */
public class EmpruntListServlet extends HttpServlet {

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
        String show = request.getParameter("show");
        
        try {
            if(show != null && show.equals("all")) {
                emprunts = empruntService.getList();
            }
            else {
                emprunts = empruntService.getListCurrent();
            }
        } catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
        }

        request.setAttribute("emprunts", emprunts);

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/View/emprunt_list.jsp");
        dispatcher.forward(request, response);
    }
}