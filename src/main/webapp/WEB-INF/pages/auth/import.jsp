<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<form:form method="post" modelAttribute="data" action="data" class="form-horizontal">
    <div class="form-group">
        <label for="name" class="col-sm-4 control-label">Name</label>
        <div class="col-sm-8">
            <form:input type="text" path="name" class="form-control" placeholder="Name"/>
            <div class="has-error">
                <form:errors path="name" class="control-label"/>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label for="category" class="col-sm-4 control-label">Category</label>

        <div class="col-sm-8">
            <form:input type="number" path="category" class="form-control" placeholder="Catgory Index"/>
            <div class="has-error">
                <form:errors path="category" class="control-label"/>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label for="date" class="col-sm-4 control-label">Date</label>

        <div class="col-sm-8">
            <form:input type="number" path="date" class="form-control" placeholder="Date Index"/>
            <div class="has-error">
                <form:errors path="date" class="control-label"/>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label for="amount" class="col-sm-4 control-label">Amount</label>

        <div class="col-sm-8">
            <form:input type="number" path="amount" class="form-control" placeholder="Amount Index"/>
            <div class="has-error">
                <form:errors path="amount" class="control-label"/>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label for="currency" class="col-sm-4 control-label">Currency</label>

        <div class="col-sm-8">
            <form:input type="number" path="currency" class="form-control" placeholder="Currency Index"/>
            <div class="has-error">
                <form:errors path="currency" class="control-label"/>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label for="description" class="col-sm-4 control-label">Description</label>

        <div class="col-sm-8">
            <form:input type="number" path="description" class="form-control" placeholder="Description Index"/>
            <div class="has-error">
                <form:errors path="description" class="control-label"/>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label for="tags" class="col-sm-4 control-label">Tags</label>

        <div class="col-sm-8">
            <form:input type="number" path="tags" class="form-control" placeholder="Tags Index"/>
            <div class="has-error">
                <form:errors path="tags" class="control-label"/>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label for="account" class="col-sm-4 control-label">Account</label>

        <div class="col-sm-8">
            <form:input type="number" path="account" class="form-control" placeholder="Account Index"/>
            <div class="has-error">
                <form:errors path="account" class="control-label"/>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label for="receipt" class="col-sm-4 control-label">Receipt</label>

        <div class="col-sm-8">
            <form:input type="number" path="receipt" class="form-control" placeholder="Receipt Index"/>
            <div class="has-error">
                <form:errors path="receipt" class="control-label"/>
            </div>
        </div>
    </div>

    <div class="form-group">
        <div class="col-xs-offset-4 col-xs-8">
            <a href="<s:url value='/users/profile'/>" class="btn btn-default"> Cancel</a>
            <button type="submit" class="btn btn-primary"><i class="fa fa-save"></i> Save</button>
        </div>
    </div>
</form:form>