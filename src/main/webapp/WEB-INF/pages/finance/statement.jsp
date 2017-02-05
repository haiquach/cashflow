<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<s:url value="/txn/processStatement" var="importProcess">
    <s:param name="${_csrf.parameterName}" value="${_csrf.token}"/>
</s:url>

<form:form method="POST" modelAttribute="fileBucket" action="${importProcess}"
           enctype="multipart/form-data" class="form-inline">
    <s:hasBindErrors name="fileBucket" >
        <div class="alert alert-danger">
            <form:errors path="*" />
        </div>
    </s:hasBindErrors>

    <div class="form-group">
        <label for="name" class="col-sm-4 control-label">Name</label>
        <div class="col-sm-8">
            <form:select path="name" items="${dataNames}" class="form-control"/>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-12">
            <form:input type="file" path="file" class="file-loading"/>
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

