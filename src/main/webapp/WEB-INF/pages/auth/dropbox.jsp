<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<s:url value='/users/dropbox' var="dropbox"/>
<form:form modelAttribute="dropbox" method="post" action="${dropbox}" class="form-horizontal">
    <fieldset>
        <div class="form-group">
            <label class="col-sm-12 control-label">Access to your Dropbox Console to generate Access Token</label>
            <label class="col-sm-12 control-label">Dropbox Console Link: https://www.dropbox.com/developers/apps</label>
        </div>
        <div class="form-group">
            <label for="token" class="col-sm-4 control-label">Access Token</label>

            <div class="col-sm-8">
                <form:input path="token" class="form-control" placeholder="Access Token"/>
                <div class="has-error">
                    <form:errors path="token" class="help-inline"/>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label for="email" class="col-sm-4 control-label">Email</label>

            <div class="col-sm-8">
                <form:input path="email" class="form-control" disabled="true"/>
            </div>
        </div>

        <div class="form-group">
            <label for="displayName" class="col-sm-4 control-label">Display Name</label>

            <div class="col-sm-8">
                <form:input path="displayName" class="form-control" disabled="true"/>
            </div>
        </div>

        <div class="form-group">
            <div class="col-lg-8 col-lg-offset-4">
                <a href="<s:url value='/users/profile'/>" class="btn btn-default">Cancel</a>
                <button type="submit" class="btn btn-primary"><i class="fa fa-save"></i> Save</button>
            </div>
        </div>
    </fieldset>
</form:form>