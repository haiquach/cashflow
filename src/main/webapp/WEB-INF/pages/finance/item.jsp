<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form:form modelAttribute="cashFlow" method="post" action="add?${_csrf.parameterName}=${_csrf.token}" class="form-horizontal" enctype="multipart/form-data">
    <fieldset>
        <form:hidden path="type"/>
        <div class="form-group">
            <label for="category" class="col-sm-4 control-label">Category</label>

            <div class="col-sm-8">
                <form:select items="${categories}" path="category" class="form-control" placeholder="Category"/>
                <div class="has-error">
                    <form:errors path="category" class="help-inline"/>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label for="effective" class="col-sm-4 control-label">Effective Date</label>

            <div class="col-sm-8">
                <form:input path="effective" class="form-control" placeholder="Effective Date"/>
                <div class="has-error">
                    <form:errors path="effective" class="help-inline"/>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label for="amount" class="col-sm-4 control-label">Amount</label>

            <div class="col-sm-8">
                <form:input path="amount" class="form-control" placeholder="Amount"/>
                <div class="has-error">
                    <form:errors path="amount" class="help-inline"/>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label for="note" class="col-sm-4 control-label">Notes</label>

            <div class="col-sm-8">
                <form:input path="note" class="form-control"/>
                <div class="has-error">
                    <form:errors path="note" class="help-inline"/>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-8 col-sm-offset-4">
                <label>Preview File Icon</label>
                <form:input type="file" path="file"/>
                <div class="has-error">
                    <form:errors path="file" class="help-inline"/>
                </div>
            </div>

        </div>
        <div class="form-group">
            <div class="col-sm-8 col-sm-offset-4">
                <button type="submit" class="btn btn-primary"><i class="fa fa-save"></i> Save</button>
            </div>
        </div>
    </fieldset>
</form:form>

<script>
    var lookupData = ${lookupData};
    $( document ).ready(function() {
        $('#effective').datepicker();
        var data = lookupData[$('#category').val()];
        $('#note').typeahead({ source:data });
        $('#category').change(function() {
            $('#note').typeahead('destroy').typeahead({ source: lookupData[$(this).val()] });
        });
        $("#file").fileinput({
            showUpload: false,
            showCaption: false,
            browseClass: "btn btn-primary btn-lg",
            allowedFileExtensions: ['jpg', 'gif', 'png', 'pdf']
        });
    });
</script>