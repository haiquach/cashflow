<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:forEach items="${txn}" var="snapshot">
    <c:forEach items="${snapshot.transactions}" var="txnDate">
        <div class="panel panel-default">
        <div class="panel-body">
            <div class="col-md-2">${snapshot.month}/${txnDate.key}/${snapshot.year}</div>
            <div class="col-md-10">
                <div class="row-fluid">
                    <c:forEach items="${txnDate.value}" var="transaction">
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
                    </c:forEach>
                </div>
            </div>
        </div>
        </div>
    </c:forEach>
</c:forEach>
