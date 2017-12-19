<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://vdab.be/tags" prefix="vdab" %>
<!doctype html>
<html lang="nl">
	<head>
		<vdab:head title="Unshipped orders"/>
		<style>
			.id {
				text-align: right;
				width: 2em;
			}
			.datum {
				text-align: right;
				width: 5em;
			}
			.status {
				width: 7em;
			}
			.customer {
				width: 8em;
			}
		</style>
	</head>
	<body>
		<vdab:menu/>
		<h1>Unshipped orders</h1>
		<c:if test="${not empty failedIds}">
			<h2>Shipping failed for order(s)
			<c:forEach items="${failedIds}" var="failedId">
			${failedId}, 
			</c:forEach>
			not enough stock</h2>
		</c:if>
		<c:choose>
			<c:when test="${not empty unshippedOrders}">
			<form method="post" id="setShippedForm">
				<table>
					<thead>
						<tr>
							<th>ID</th>
							<th>Ordered</th>
							<th>Required</th>
							<th>Customer</th>
							<th>Comments</th>
							<th>Status</th>
							<th>Ship</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${unshippedOrders}" var="order">
							<tr>
								<td class="id">
									<c:url value="/orders/detail.htm" var="detailURL">
										<c:param name="id" value="${order.id}"/>
									</c:url>
									<a href="${detailURL}" title="${order.id}">${order.id}</a>
								</td>
								<td class="datum">
								<fmt:parseDate value="${order.orderDate}" pattern="yyyy-MM-dd"
									var="orderDate" type="date"/>
									<fmt:formatDate value="${orderDate}" type="date" 
									dateStyle="short" pattern="d-M-yy"/>
								</td>
								<td class="datum">
								<fmt:parseDate value="${order.requiredDate}" pattern="yyyy-MM-dd"
									var="requiredDate" type="date"/>
									<fmt:formatDate value="${requiredDate}" type="date" 
									dateStyle="short" pattern="d-M-yy"/>
								</td>
								<td class="customer">${order.customer.name}</td>
								<td>${order.comments}</td>
								<td class="status">
									<img src="<c:url value="/images/${order.status}.png"/>" alt="${order.status.status}"
									title="${order.status.status}">${order.status.status}
								</td>
								<td>
									<input type="checkbox" name="id" value="${order.id}">
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<c:if test="${vanafRij != 0 }">
					<c:url value="" var="vorigePaginaURL">
						<c:param name="vanafRij" value="${vanafRij - aantalRijen}"/>
					</c:url>
					<a href="<c:out value="${vorigePaginaURL}"/>" title="vorige pagina"
					 class="pagineren">&larr;</a>
				</c:if>
				<c:if test="${empty laatstePagina}">
					<c:url value="" var="volgendePaginaURL">
						<c:param name="vanafRij" value="${vanafRij +aantalRijen}"/>
					</c:url>
					<a href="<c:out value="${volgendePaginaURL}"/>" title="volgende pagina"
					 class="pagineren">&rarr;</a>
				</c:if>
				<input type="submit" value="Set as shipped" id="setShippedKnop">
			</form>
			</c:when>
			<c:otherwise>
			Er zijn geen onverscheepte orders op dit moment.
			</c:otherwise>
		</c:choose>
		<script>
			document.getElementById("setShippedForm").addEventListener("submit", function() {
				document.getElementById("setShippedKnop").disabled = true;
			});
		</script>
	</body>
</html>
