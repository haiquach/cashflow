<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:forEach items="${txn}" var="snapshot">
    <div class="panel panel-default">
        <div class="panel-heading">
            ${snapshot.displayDate}
        </div>
        <div class="panel-body">
            <c:forEach items="${snapshot.sortedByDateTransactions}" var="transaction">
                <div class="col-md-2">${transaction.date}</div>
                <div class="col-md-10">
                    <div class="row-fluid">
                        <div class="col-sm-9">${transaction.description}</div>
                        <div class="col-sm-3">
                            <c:if test="${transaction.amount > 0}">
                                <span style="color: blue;">
                                    <fmt:formatNumber value="${transaction.amount}" type="currency" currencySymbol="${transaction.currency.symbol}"/>
                                </span>
                            </c:if>
                            <c:if test="${transaction.amount < 0}">
                                <span style="color: red;">
                                    <fmt:formatNumber value="${transaction.amount * (-1)}" type="currency" currencySymbol="${transaction.currency.symbol}"/>
                                </span>
                            </c:if>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</c:forEach>
