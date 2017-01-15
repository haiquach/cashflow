<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<s:url value="/finance/search" var="report"/>
<div class="panel panel-info">
    <div class="panel-body">
        <form:form modelAttribute="report" method="post" action="${report}" class="form-inline">
            <div class="row">
                <div class="form-group">
                    <label for="startDate">From</label>
                    <form:input path="startDate" class="form-control"/>
                </div>
                <div class="form-group">
                    <label for="endDate">To</label>
                    <form:input path="endDate" class="form-control"/>
                </div>
                <button type="submit" id="btnSubmit" class="btn btn-primary"><i class="fa fa-search"></i></button>
            </div>
            <div class="row">
                <div class="form-group">
                    <label for="incomes">Incomes</label>
                    <form:select path="incomes" items="${incomeCategories}" class="form-control"
                                 size="10" multiple="true"/>
                </div>
                <div class="form-group">
                    <label for="expenses">Expenses</label>
                    <form:select path="expenses" items="${expenseCategories}" class="form-control"
                                 size="10" multiple="true"/>
                </div>
                <c:if test="${! empty notes}">
                    <div class="form-group">
                        <label for="notes">Note</label>
                        <form:select path="notes" items="${notes}" class="form-control"
                                     size="10" multiple="true"/>
                    </div>
                </c:if>
            </div>
        </form:form>
        <table id="reportTable" class="table table-bordered table-striped">
            <thead>
            <tr>
                <th>Income/Expense</th>
                <th>Category</th>
                <th>Effective Date</th>
                <th>Amount</th>
                <th>Submit by</th>
                <th>Notes</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${result}" var="item">
                <tr>
                    <td>
                        <c:choose>
                            <c:when test="${item.income}">
                                <span class="label label-primary">Income</span>
                            </c:when>
                            <c:otherwise>
                                <span class="label label-warning">Expense</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>${item.category}</td>
                    <td><fmt:formatDate value="${item.effective}" pattern="MM/dd/yyyy"/></td>
                    <td><fmt:formatNumber value="${item.amount}" type="currency"/></td>
                    <td>${item.userId}</td>
                    <td>${item.note}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<script>
    $(document).ready(function () {
        $('#startDate').datepicker();
        $('#endDate').datepicker();
        $('#reportTable').DataTable({
            "order": [[ 2, 'desc' ]],
            "displayLength": 50
        });
    });
</script>