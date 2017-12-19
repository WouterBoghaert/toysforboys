package be.vdab.servlets.orders;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.vdab.services.OrderService;
import be.vdab.util.StringUtils;

@WebServlet("/orders/details.htm")
public class DetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/orders/details.jsp";
	private final transient OrderService orderService = new OrderService();
    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idString = request.getParameter("id");
		if(StringUtils.isNotNullNotEmpty(idString)) {
			if(StringUtils.isLong(idString)) {
				orderService.read(Long.parseLong(idString)).ifPresent(order ->
					request.setAttribute("order", order));
			}
		}
		request.getRequestDispatcher(VIEW).forward(request, response);
	}
}
