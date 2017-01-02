<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${edit}">
    <c:set var="postURL" value="edit-user-${user.userId}" />
</c:if>
<c:if test="${!edit}">
    <c:set var="postURL" value="newuser" />
</c:if>

<div class="col-md-6">
    <div class="panel panel-info">
        <form:form method="post" modelAttribute="user" action="${postURL}" class="form-horizontal">
            <form:hidden path="createdDate"/>
            <form:hidden path="updatedDate"/>
            <c:if test="${not empty message}">
                <div class="alert alert-success">
                        ${message}
                </div>
            </c:if>
            <div class="panel-body">
                <div class="form-group">
                    <label for="firstName" class="col-sm-4 control-label">First Name</label>

                    <div class="col-sm-8">
                        <form:input type="text" path="firstName" class="form-control" id="firstName"
                                    placeholder="First name"/>
                        <div class="has-error">
                            <form:errors path="firstName" class="help-inline"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="lastName" class="col-sm-4 control-label">Last Name</label>

                    <div class="col-sm-8">
                        <form:input type="text" path="lastName" class="form-control" id="lastName"
                                    placeholder="Last name"/>
                        <div class="has-error">
                            <form:errors path="lastName" class="help-inline"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="userId" class="col-sm-4 control-label">User Name</label>

                    <div class="col-sm-8">
                        <form:input type="text" path="userId" class="form-control" id="userId" placeholder="User name"
                                    disabled="${edit}"/>
                        <div class="has-error">
                            <form:errors path="userId" class="help-inline"/>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="email" class="col-sm-4 control-label">Email</label>

                    <div class="col-sm-8">
                        <form:input type="text" path="email" class="form-control" id="email" placeholder="Email"/>
                        <div class="has-error">
                            <form:errors path="email" class="help-inline"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="roles" class="col-sm-4 control-label">Roles</label>

                    <div class="col-sm-8">
                        <form:select path="roles" class="form-control" items="${roles}" multiple="true"/>
                        <div class="has-error">
                            <form:errors path="roles" class="help-inline"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-xs-offset-4 col-xs-8">
                        <a href="<c:url value='/admin/list'/>" class="btn btn-default">Cancel</a>
                        <button type="submit" class="btn btn-primary"><i class="fa fa-save"></i> Save</button>
                    </div>
                </div>
            </div>
        </form:form>
    </div>
</div>
