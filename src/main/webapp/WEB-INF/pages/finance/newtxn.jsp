<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form:form commandName="transaction" method="post" action="newtxn?${_csrf.parameterName}=${_csrf.token}" class="form-horizontal" enctype="multipart/form-data">
    <fieldset>
        <c:if test="${not empty message}">
            <div class="alert alert-success">
                ${message}
            </div>
        </c:if>
        <div class="form-group">
            <label for="type" class="col-sm-4 control-label">Type</label>

            <div class="col-sm-8">
                <form:select path="type" class="form-control">
                    <form:option value="1">Income</form:option>
                    <form:option value="-1">Expense</form:option>
                </form:select>
                <div class="has-error">
                    <form:errors path="type" cssClass="control-label"/>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label for="category" class="col-sm-4 control-label">Category</label>

            <div class="col-sm-8">
                <form:select items="${categories}" path="category" class="form-control"/>
                <div class="has-error">
                    <form:errors path="category" cssClass="control-label"/>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label for="date" class="col-sm-4 control-label">Date</label>

            <div class="col-sm-8">
                <form:input path="date" class="form-control" placeholder="Date"/>
                <div class="has-error">
                    <form:errors path="date" cssClass="control-label"/>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label for="amount" class="col-sm-4 control-label">Currency</label>

            <div class="col-sm-8">
                <form:select path="currency" items="${currencies}" class="form-control"
                    itemValue="currencyCode" itemLabel="displayName"/>
                <div class="has-error">
                    <form:errors path="currency" cssClass="control-label"/>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label for="amount" class="col-sm-4 control-label">Amount</label>

            <div class="col-sm-8">
                <form:input path="amount" class="form-control" placeholder="Amount"/>
                <div class="has-error">
                    <form:errors path="amount" cssClass="control-label"/>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label for="description" class="col-sm-4 control-label">Description</label>

            <div class="col-sm-8">
                <form:input path="description" class="form-control"/>
                <div class="has-error">
                    <form:errors path="description" cssClass="control-label"/>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label for="account" class="col-sm-4 control-label">Account Name</label>

            <div class="col-sm-8">
                <form:input path="account" class="form-control"/>
                <div class="has-error">
                    <form:errors path="account" cssClass="control-label"/>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label for="tags" class="col-sm-4 control-label">Tags</label>

            <div class="col-sm-8">
                <form:input path="tags" class="form-control"/>
                <div class="has-error">
                    <form:errors path="tags" cssClass="control-label"/>
                </div>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-8 col-sm-offset-4">
                <label>Receipt Image</label>
                <form:input type="file" path="file"/>
                <div class="has-error">
                    <form:errors path="file" cssClass="control-label"/>
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
    $( document ).ready(function() {
        $('#date').datepicker();
        $('#tags').tagsinput({
            // no enter key, it will submit form
            confirmKeys: [32, 44]
        });
        $("#file").fileinput({
            showUpload: false,
            showCaption: false,
            browseClass: "btn btn-primary btn-lg",
            allowedFileExtensions: ['jpg', 'gif', 'png', 'pdf']
        });
    });
</script>