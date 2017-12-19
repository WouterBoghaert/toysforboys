<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://vdab.be/tags" prefix="vdab" %>
<!doctype html>
<html lang="nl">
	<head>
		<vdab:head title="Details order ${order.id}"/>
	</head>
	<body>
		<vdab:menu/>
		<c:if test="${not empty order}">
			<h1>Order ${order.id}</h1>
			<dl>
				<dt>Ordered:</dt>
				<dd><fmt:parseDate value="${order.orderDate}" pattern="yyyy-MM-dd"
				var="orderDate" type="date"/>
				<fmt:formatDate value="${orderDate}" type="date" dateStyle="short"
				pattern="dd/MM/yy"/>
				</dd>
				<dt>Required:</dt>
				<dd><fmt:parseDate value="${order.requiredDate}" pattern="yyyy-MM-dd"
				var="requiredDate" type="date"/>
				<fmt:formatDate value="${requiredrDate}" type="date" dateStyle="short"
				pattern="dd/MM/yy"/>
				</dd>
				<dt>Customer:</dt>
				<dd>${order.customer.name} 
				${order.customer.adres.streetAndNumber}
				${order.customer.adres.postalCode} ${order.customer.adres.city} ${order.customer.adres.state}
				${order.customer.country.name}
				</dd>
				<dt>Comments:</dt>
				<dd>${order.comments}</dd>				
				<c:if test="${not empty order.orderDetails}">
				<dt>Details:</dt>
				<dd>
					<table>
						<thead>
							<tr>
								<th>Product</th>
								<th>Price each</th>
								<th>Quantity</th>
								<th>Value</th>
								<th>Devilerable</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${order.products}" var="product">
								<tr>
									<td>${product.name}</td>
									<td><fmt:formatNumber value="${product.priceEach}"
									minFractionDigits="2" maxFractionDigits="2"/></td>
									<td>${product.quantityOrdered}</td>
									<td><fmt:formatNumber value="${product.getValue()}"
									minFractionDigits="2" maxFractionDigits="2"/></td>
									<td>${product.getDeliverable() ? "&check;" : "&cross;"}</td> 
								</tr>								
							</c:forEach>
						</tbody>
					</table>
				</dd>
				<dt>Value:</dt>
				<dd>${order.getTotalValue()}</dd>
				</c:if>			
			</dl>
		</c:if>
	</body>
</html>
<!--  herschrijven met juiste associaties!!! orderdetails gebruiken -->