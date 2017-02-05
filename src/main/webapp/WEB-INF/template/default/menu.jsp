<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<li class="dropdown">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#"><i class="fa fa-text-height"> Transactions </i><span class="caret"></span></a>
    <ul class="dropdown-menu">
        <li><a href="<s:url value='/txn/statement'/>"><i class="fa fa-file-text"></i> Import Bank Statement</a></li>
        <div class="divider"></div>
        <li><a href="<s:url value='/txn/newtxn'/>"><i class="fa fa-bank"></i> New Transaction</a></li>
        <li><a href="<s:url value='/txn/list'/>"><i class="fa fa-list"></i> View Transactions</a></li>
    </ul>
</li>
<li class="dropdown">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#"><i class="fa fa-clipboard"> Report</i><span class="caret"></span></a>
    <ul class="dropdown-menu">
        <li><a href="<s:url value='/txn/report'/>"> <i class="fa fa-search"></i> Search</a></li>
        <div class="divider"></div>
        <li><a href="<s:url value='/txn/revenue'/>"><i class="fa fa-money"></i> Revenue</a></li>
    </ul>
</li>