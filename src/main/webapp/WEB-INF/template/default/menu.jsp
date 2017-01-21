<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<li class="dropdown">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#" id="cashflow">Cash Flow <span class="caret"></span></a>
    <ul class="dropdown-menu" aria-labelledby="cashflow">
        <li><a href="<s:url value='/finance/incomes'/>"> <i class="fa fa-indent fa-fw"></i> Incomes</a></li>
        <li><a href="<s:url value='/finance/expenses'/>"><i class="fa fa-outdent fa-fw"></i> Expenses</a></li>
        <div class="divider"></div>
        <li><a href="<s:url value='/finance/revenue'/>"><i class="fa fa-money fa-fw"></i> Revenue</a></li>
    </ul>
</li>
<li><a href="<s:url value='/finance/report'/>">Report</a></li>
<sec:authorize access="hasRole('ROLE_ADMIN')">
<li class="dropdown">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#" id="admin">Administration <span class="caret"></span></a>
    <ul class="dropdown-menu" aria-labelledby="admin">
        <li><a href="<s:url value='/admin/import'/>"><i class="fa fa-cloud-upload fa-fw"></i> Import</a></li>
        <li><a href="<s:url value='/admin/export'/>"><i class="fa fa-cloud-download fa-fw"></i> Export</a></li>
    </ul>
</li>
</sec:authorize>