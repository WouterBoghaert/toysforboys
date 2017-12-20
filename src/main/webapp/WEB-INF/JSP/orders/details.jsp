<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://vdab.be/tags" prefix="vdab" %>
<!doctype html>
<html lang="nl">
	<head>
		<vdab:head title="Details order ${order.id}"/>
		<style>
			.rechts {
				text-align: right;
			}
			.midden {
				text-align: center;
			}
		</style>
	</head>
	<body>
		<vdab:menu/>
		<c:if test="${not empty order and not empty orderKlant}">
			<h1>Order ${orderKlant.id}</h1>
			<dl>
				<dt>Ordered:</dt>
				<dd><fmt:parseDate value="${orderKlant.orderDate}" pattern="yyyy-MM-dd"
				var="orderDate" type="date"/>
				<fmt:formatDate value="${orderDate}" type="date" dateStyle="short"
				pattern="dd/MM/yy"/>
				</dd>
				<dt>Required:</dt>
				<dd><fmt:parseDate value="${orderKlant.requiredDate}" pattern="yyyy-MM-dd"
				var="requiredDate" type="date"/>
				<fmt:formatDate value="${requiredDate}" type="date" dateStyle="short"
				pattern="dd/MM/yy"/>
				</dd>
				<dt>Customer:</dt>
				<dd><c:out value="${orderKlant.customer.name}"/><br>
				<c:out value="${orderKlant.customer.adres.streetAndNumber}"/><br>
				${orderKlant.customer.adres.postalCode} <c:out value="${orderKlant.customer.adres.city}"/> <c:out value="${orderKlant.customer.adres.state}"/><br>
				${orderKlant.customer.country.name}
				</dd>
				<dt>Comments:</dt>
				<dd><c:out value="${orderKlant.comments}"/></dd>				
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
						<tbody class="rechts">
							<c:forEach items="${order.orderDetails}" var="orderDetail">
								<tr>
									<td><c:out value="${orderDetail.product.name}"/></td>
									<td><fmt:formatNumber value="${orderDetail.priceEach}"
									minFractionDigits="2" maxFractionDigits="2"/></td>
									<td>${orderDetail.quantityOrdered}</td>
									<td><fmt:formatNumber value="${orderDetail.getValue()}"
									minFractionDigits="2" maxFractionDigits="2"/></td>
									<td class="midden">${orderDetail.isDeliverable() ? "&check;" : "&cross;"}</td> 
								</tr>								
							</c:forEach>
						</tbody>
					</table>
				</dd>
				<dt>Value:</dt>
				<dd><fmt:formatNumber value="${order.getTotalValue()}"
				minFractionDigits="2" maxFractionDigits="2"/></dd>
				</c:if>
				<c:if test="${empty order.orderDetails}">
					Er zijn geen details beschikbaar voor dit order.
				</c:if>			
			</dl>
		</c:if>
		<c:if test="${empty order and not empty param}">
			<p>Het gevraagde order is niet gevonden in de database.</p>
		</c:if>
		<c:if test="${empty order and empty param}">
			<p>Er is iets misgegaan. Probeer opnieuw het detail van uw order te bekijken
			vanuit de Unshipped orders pagina. Contacteer de helpdesk indien dit niet lukt.</p>
		</c:if>
	</body>
</html>
<!--  herschrijven met juiste associaties!!! orderdetails gebruiken
ook order.getTotalValue() checken en evt herschrijven -->