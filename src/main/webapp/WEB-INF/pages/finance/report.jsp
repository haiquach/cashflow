<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<div class="panel panel-info">
    <div class="panel-body">
        <form:form modelAttribute="report" method="post" action="search" class="form-horizontal">
            <div class="form-group">
                <label for="startDate" class="col-md-2">Start</label>
                <div class="col-md-4">
                    <form:input path="startDate" class="form-control"/>
                </div>
                <label for="endDate" class="col-md-2">End</label>
                <div class="col-md-4">
                    <form:input path="endDate" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label for="category" class="col-md-2">Category</label>
                <div class="col-md-4">
                    <form:select path="category" items="${categories}" class="form-control"/>
                </div>
                <label for="tags" class="col-md-2">Tags</label>
                <div class="col-md-4">
                    <form:input path="tags" class="form-control"/>
                </div>
            </div>
            <div class="btn-group">
                <button type="submit" id="btnSearch" class="btn btn-primary"><i class="fa fa-search"></i></button>
                <button type="submit" id="btnDownload" class="btn btn-primary"><i class="fa fa-download"></i></button>
            </div>
        </form:form>
        <table id="reportTable" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>Category</th>
                    <th>Date</th>
                    <th>Amount</th>
                    <th>Description</th>
                    <th>Account Name</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${result}" var="item">
                <tr>
                    <td>${item.category}</td>
                    <td>${item.date}</td>
                    <td><fmt:formatNumber value="${item.amount}" type="currency"/></td>
                    <td>${item.description}</td>
                    <td>${item.account}</td>
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
        $('#tags').tagsinput({
            // no enter key, it will submit form
            confirmKeys: [32, 44]
        });
        $('#reportTable').DataTable({
            "order": [[ 1, 'desc' ]],
            "displayLength": 50
        });
        $('#btnSearch').click(function (e) {
            e.preventDefault();
            $('#report').attr('action', '<s:url value="/txn/search"/>').submit();
        });
        $('#btnDownload').click(function (e) {
            e.preventDefault();
            $('#report').attr('action', '<s:url value="/txn/download"/>').submit();
        });
    });
</script>