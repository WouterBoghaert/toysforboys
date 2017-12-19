package be.vdab.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.vdab.entities.Order;
import be.vdab.exceptions.RecordAangepastException;
import be.vdab.services.OrderService;

@WebServlet("/index.htm")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/index.jsp";
	private final transient OrderService orderService = new OrderService();
	private static final int AANTAL_RIJEN = 20;
 
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameterValues("id") != null) {
			request.setAttribute("failedIds", request.getParameterValues("id"));
		}
		int vanafRij = request.getParameter("vanafRij") == null ? 0 :
			Integer.parseInt(request.getParameter("vanafRij"));
		request.setAttribute("vanafRij", vanafRij);
		request.setAttribute("aantalRijen", AANTAL_RIJEN);
		List<Order> unshippedOrders = orderService.findUnshippedOrders(vanafRij, AANTAL_RIJEN + 1);
		if(unshippedOrders.size() <= AANTAL_RIJEN) {
			request.setAttribute("laatstePagina", true);
		}
		else {
			unshippedOrders.remove(AANTAL_RIJEN);
		}
		request.setAttribute("unshippedOrders", unshippedOrders);
		request.getRequestDispatcher(VIEW).forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String [] idStrings = request.getParameterValues("id");
		if(idStrings!= null && idStrings.length != 0) {
			List<Long> ids = Arrays.stream(idStrings).map(idString -> Long.parseLong(idString))
				.collect(Collectors.toList());
			try {
				List<Long> failedIds = orderService.setAsShipped(ids);
				StringBuilder redirectBuilder = new StringBuilder();
				redirectBuilder.append(request.getRequestURI() + "?");
				failedIds.stream().forEach(id -> redirectBuilder.append("id=" + id + "&"));
				redirectBuilder.deleteCharAt(redirectBuilder.length()-1);
				response.sendRedirect(response.encodeRedirectURL(redirectBuilder.toString()));
			}
			catch(RecordAangepastException ex) {
				request.setAttribute("fouten", Collections.singletonMap("setAsShipped",
					"Een andere gebruiker heeft de records aangepast!"));
				request.getRequestDispatcher(VIEW).forward(request, response);
			}
		}
	}
}