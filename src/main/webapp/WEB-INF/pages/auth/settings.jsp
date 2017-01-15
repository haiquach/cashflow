<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<s:url value="/users/update/${user.userId}" var="postUrl"/>
<div class="col-lg-6 col-lg-offset-3">
    <div class="panel panel-info">
        <form:form method="post" modelAttribute="user" action="${postUrl}" class="form-horizontal">
            <c:if test="${not empty message}">
                <div class="alert alert-success">
                     ${message}
                </div>
            </c:if>
            <div class="panel-body">
                <div class="form-group">
                    <label for="userId" class="col-sm-4 control-label">User Name</label>
                    <div class="col-sm-8">
                        <form:input type="text" path="userId" class="form-control" placeholder="User name" disabled="true"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="firstName" class="col-sm-4 control-label">First Name</label>

                    <div class="col-sm-8">
                        <form:input type="text" path="firstName" class="form-control" placeholder="First name"/>
                        <div class="has-error">
                            <form:errors path="firstName" class="help-inline"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="lastName" class="col-sm-4 control-label">Last Name</label>

                    <div class="col-sm-8">
                        <form:input type="text" path="lastName" class="form-control" placeholder="Last name"/>
                        <div class="has-error">
                            <form:errors path="lastName" class="help-inline"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="email" class="col-sm-4 control-label">Email</label>

                    <div class="col-sm-8">
                        <form:input type="text" path="email" class="form-control" placeholder="Email"/>
                        <div class="has-error">
                            <form:errors path="email" class="help-inline"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-xs-offset-4 col-xs-8">
                        <a href="<c:url value='/users/profile'/>" class="btn btn-default"> Cancel</a>
                        <button type="submit" class="btn btn-primary"><i class="fa fa-save"></i> Save</button>
                    </div>
                </div>
            </div>
        </form:form>
    </div>
</div>