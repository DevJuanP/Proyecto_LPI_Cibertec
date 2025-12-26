package controller;
import DAO.BookDAO;
import model.Book;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/HomePageCotroller")
public class HomePageCotroller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 List<Book> misterio = BookDAO.listByCategory("Misterio");
	        List<Book> accion = BookDAO.listByCategory("Accion");
	        List<Book> romance = BookDAO.listByCategory("Romance");

	        //  Enviar a la vista  
	        request.setAttribute("misterio", misterio);
	        request.setAttribute("accion", accion);
	        request.setAttribute("romance", romance);

	        request.getRequestDispatcher("rshome.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
