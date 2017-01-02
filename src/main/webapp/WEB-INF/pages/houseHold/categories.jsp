<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${! empty errorMessage}">
    <div class="alert alert-danger">
        ${errorMessage}
    </div>
</c:if>
<c:if test="${empty errorMessage}">
    <div class="panel panel-info">
        <div class="panel-heading">
            <button id="btnAdd" class="btn btn-primary pull-right"><i class="fa fa-plus-square"></i>  Add Category</button>
            <a href="<c:url value='/household/category/reset'/>" class="btn btn-danger pull-left">
                <i class="fa fa-refresh"></i> Reset </a>
            <div class="clearfix"></div>
        </div>
        <div class="panel-body">
            <div id="category" class="row">
                <form:form modelAttribute="form" method="post" action="<c:url value='/household/category/add'/>" class="form-horizontal">
                    <fieldset>
                        <div class="form-group">
                            <label for="name" class="col-sm-4 control-label">Cash Flow Type</label>
                            <div class="col-sm-8">
                                <form:radiobutton path="type" value="INC"/>  Income
                                <form:radiobutton path="type" value="EXP"/>  Expense
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="name" class="col-sm-4 control-label">Cash Flow Category</label>
                            <div class="col-sm-8">
                                <form:input path="name" class="form-control" placeholder="Category"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="item" class="col-sm-4 control-label">Sub Category</label>
                            <div class="col-sm-8">
                                <form:input path="item" class="form-control" placeholder="Sub Category"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-lg-8 col-lg-offset-4">
                                <button type="button" id="btnCancel" class="btn btn-default">Cancel</button>
                                <button type="submit" id="btnSubmit" class="btn btn-primary">Submit</button>
                            </div>
                        </div>
                    </fieldset>
                </form:form>
            </div>
            <div class="row">
                <div class="col-lg-6">
                    <table id="income" class="table table-bordered table-striped">
                        <tbody>
                        <c:forEach items="${existingIncomes}" var="income">
                            <c:forEach items="${income.value}" var="item">
                                <tr>
                                    <td>${income.key}</td>
                                    <td>${item}</td>
                                    <td>
                                        <a href="<c:url value='/household/category/income/remove?type=${income.key}&name=${item}'/>">
                                            <i class="fa fa-trash"></i>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="col-lg-6">
                    <table id="expense" class="table table-bordered table-striped">
                        <tbody>
                        <c:forEach items="${existingExpenses}" var="expense">
                            <c:forEach items="${expense.value}" var="item">
                                <tr>
                                    <td>${expense.key}</td>
                                    <td>${item}</td>
                                    <td>
                                        <a href="<c:url value='/household/category/expense/remove?type=${expense.key}&name=${item}'/>">
                                            <i class="fa fa-trash"></i>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <script>

        var incomeOptions = ${iCategories};
        var expenseOptions = ${eCategories};
        $( document ).ready(function() {
            $('#category').toggle();
            $('#btnAdd').click(function() {
                $('#category').toggle();
                $(this).toggle();
            });
            $('#btnCancel').click(function() {
                $('#category').toggle();
                $('#btnAdd').toggle();
            });

            $("input[name=type]:radio").change(function(){
                /*
                $el = $('#name');
                $el.empty();
                if ($(this).val() == "INC") {
                    $.each(incomeOptions, function(key, value) {
                        $el.append($('<option></option>').attr("value", value).text(value));
                    });
                } else {
                    $.each(expenseOptions, function(key, value) {
                        $el.append($('<option></option>').attr("value", value).text(value))
                    });
                }
                */
                if ($(this).val() == "INC") {
                    $('#name').typeahead('destroy').typeahead({
                        source: incomeOptions
                    });
                } else {
                    $('#name').typeahead('destroy').typeahead({
                        lookup: expenseOptions
                    });
                }
            });


        });

    </script>
</c:if>
