<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="panel panel-info">
    <div class="page-header">
        <a href="<c:url value='/users/data'/>" class="btn btn-default">Add New Data Mapping</a>
    </div>
    <div class="panel-body">
        <table id="reportTable" class="table table-bordered table-striped">
            <thead>
            <tr>
                <th>Name</th>
                <th>Category</th>
                <th>Date</th>
                <th>Amount</th>
                <th>Currency</th>
                <th>Description</th>
                <th>Tags</th>
                <th>Account Name</th>
                <th>Receipt</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${data}" var="item">
                <tr>
                    <td>${item.key}</td>
                    <td>${item.value.category}</td>
                    <td>${item.value.date}</td>
                    <td>${item.value.amount}</td>
                    <td>${item.value.currency}</td>
                    <td>${item.value.description}</td>
                    <td>${item.value.tags}</td>
                    <td>${item.value.account}</td>
                    <td>${item.value.receipt}</td>
                    <td><a href="<c:url value='/users/data/remove-${item.key}'/>"><i class="fa fa-trash"></i></a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>