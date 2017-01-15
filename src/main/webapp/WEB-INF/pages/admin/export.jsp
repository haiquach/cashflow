<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<s:url value="/admin/download" var="download"/>

<div class="panel panel-info">
    <div class="panel-body">
        <form:form modelAttribute="form" method="post" action="${download}" class="form-inline">
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