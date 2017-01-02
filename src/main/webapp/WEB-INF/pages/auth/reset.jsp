<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-md-6 col-md-offset-3">
    <div class="panel panel-info">
        <form:form method="post" modelAttribute="resetForm" action="processPassword" class="form-horizontal">
            <div class="panel-body">
                <c:if test="${not empty message}">
                    <div class="alert alert-success">${message}</div>
                </c:if>
                <form:errors path="*" class="alert alert-danger text-center" element="div"/>

                <div class="form-group">
                    <label for="currentPassword" class="col-sm-4 control-label">Current Password</label>

                    <div class="col-sm-8">
                        <form:input type="password" path="currentPassword" class="form-control" id="currentPassword" placeholder="Current Password"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="newPassword" class="col-sm-4 control-label">New Password</label>

                    <div class="col-sm-8">
                        <form:input type="password" path="newPassword" class="form-control" id="newPassword" placeholder="New Password"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="confirmPassword" class="col-sm-4 control-label">Confirm Password</label>

                    <div class="col-sm-8">
                        <form:input type="password" path="confirmPassword" class="form-control" id="confirmPassword" placeholder="Confirm Password"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-xs-offset-4 col-xs-8">
                        <a href="<c:url value='/users/profile'/>" class="btn btn-default">Cancel</a>
                        <button type="submit" class="btn btn-primary"><i class="fa fa-refresh"></i> Reset</button>
                    </div>
                </div>
            </div>
        </form:form>
    </div>
</div>
