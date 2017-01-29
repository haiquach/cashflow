<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<s:url value="/txn/processStatement" var="importProcess">
    <s:param name="${_csrf.parameterName}" value="${_csrf.token}"/>
</s:url>
<s:hasBindErrors name="*">
    <div class="erorr">
        <form:errors path="*" cssClass="has-error"/>
    </div>
</s:hasBindErrors>
<form:form method="POST" modelAttribute="fileBucket" action="${importProcess}"
           enctype="multipart/form-data" class="form-inline">
    <div class="form-group">
        <div class="col-sm-12">
            <form:input type="file" path="file" class="file-loading"/>
            <div id="errorBlock" class="help-block">
                <form:errors path="file" class="help-inline"/>
            </div>
        </div>
    </div>
</form:form>
<script>
    $( document ).ready(function() {
        $("#file").fileinput({
            showPreview: false,
            showCaption: false,
            browseClass: "btn btn-primary",
            allowedFileExtensions: ["csv"],
            elErrorContainer: "#errorBlock"
        });

    });
</script>

