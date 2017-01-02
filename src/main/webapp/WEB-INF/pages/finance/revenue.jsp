<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="row">
    <div class="row">
        <div class="col-sm-4"><a href="<c:url value='/finance/incomes'/>">Total Incomes</a></div>
        <div class="pull-right">
            <fmt:formatNumber value="${totalIncomes}" type="currency"/>
        </div>
    </div>
    <div class="divider"></div>
    <c:forEach items="${incomes}" var="income">
        <div class="row">
            <div class="col-sm-4">${income.category}</div>
            <div class="col-sm-8"><fmt:formatNumber value="${income.total}" type="currency"/></div>
        </div>
    </c:forEach>
</div>
<hr>
<div class="row">
    <div class="row">
        <div class="col-sm-4"><a href="<c:url value='/finance/expenses'/>">Total Expenses</a></div>
        <div class="pull-right">
            <fmt:formatNumber value="${totalExpenses}" type="currency"/>
        </div>
    </div>
    <div class="divider"></div>
    <div class="row">
        <c:forEach items="${expenses}" var="expense">
            <div class="col-sm-4">${expense.category}</div>
            <div class="col-sm-8"><fmt:formatNumber value="${expense.total}" type="currency"/></div>
        </c:forEach>
    </div>
</div>
<hr>
<div class="row">
    <div class="row">
        <div class="col-sm-4">Total Incomes</div>
        <div class="pull-right"><fmt:formatNumber value="${totalIncomes}" type="currency"/></div>
    </div>
    <div class="row">
        <div class="col-sm-4">Total Expenses</div>
        <div class="pull-right"><fmt:formatNumber value="${totalExpenses}" type="currency"/></div>
    </div>
    <hr>
    <div class="row">
        <div class="col-sm-4">Revenue</div>
        <div class="pull-right"><fmt:formatNumber value="${revenue}" type="currency"/></div>
    </div>
</div>