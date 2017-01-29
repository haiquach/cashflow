<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<li class="dropdown">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#"><i class="fa fa-usd"> Cash Flow</i><span class="caret"></span></a>
    <ul class="dropdown-menu">
        <li><a href="<s:url value='/finance/incomes'/>"> <i class="fa fa-indent"></i> Incomes</a></li>
        <li><a href="<s:url value='/finance/expenses'/>"><i class="fa fa-outdent"></i> Expenses</a></li>
        <div class="divider"></div>
        <li><a href="<s:url value='/finance/revenue'/>"><i class="fa fa-money"></i> Revenue</a></li>
    </ul>
</li>
<li><a href="<s:url value='/finance/report'/>"><i class="fa fa-clipboard"> Report</i></a></li>
<li class="dropdown">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#"><i class="fa fa-text-height"> Transactions </i><span class="caret"></span></a>
    <ul class="dropdown-menu">
        <li><a href="<s:url value='/txn/statement'/>"><i class="fa fa-file-text"></i> Import Bank Statement</a></li>
        <li><a href="<s:url value='/txn/list'/>"><i class="fa fa-list"></i> View Transactions</a></li>
    </ul>
</li>
<sec:authorize access="hasRole('ROLE_ADMIN')">
<li class="dropdown">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#"><i class="fa fa-gears"> Administration</i><span class="caret"></span></a>
    <ul class="dropdown-menu">
        <li><a href="<s:url value='/admin/import'/>"><i class="fa fa-cloud-upload"></i> Import</a></li>
        <li><a href="<s:url value='/admin/export'/>"><i class="fa fa-cloud-download"></i> Export</a></li>
    </ul>
</li>
</sec:authorize>