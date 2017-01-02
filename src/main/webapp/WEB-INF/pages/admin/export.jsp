<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="panel panel-info">
    <div class="panel-body">
        <form:form modelAttribute="form" method="post" action="<c:url value='/admin/download'/>" class="form-inline">
            <div class="row">
                <div class="form-group">
                    <label for="startDate">Effective Date From</label>
                    <form:input path="startDate" class="form-control"/>
                    <div>
                        <form:errors path="startDate"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="endDate">To</label>
                    <form:input path="endDate" class="form-control"/>
                    <div>
                        <form:errors path="endDate"/>
                    </div>
                </div>
                <button type="submit" id="btnSubmit" class="btn btn-primary"><i class="fa fa-download"></i></button>
            </div>
        </form:form>
    </div>
</div>
<script>
    $(document).ready(function () {
        $('#startDate').datepicker();
        $('#endDate').datepicker();
    });
</script>