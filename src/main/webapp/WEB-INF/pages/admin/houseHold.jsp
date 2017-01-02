<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="panel panel-info">
    <div class="panel-heading">
        <button id="btnAdd" class="btn btn-primary pull-right"><i class="fa fa-plus-square"></i>  Add HouseHold</button>
        <div class="clearfix"></div>
    </div>
    <!-- /.box-header -->
    <div class="panel-body">
        <div class="row">
            <form:form modelAttribute="houseHold" id="houseHold" method="post" action="<c:url value='/admin/houseHold/add'/>" class="form-horizontal">
                <fieldset>
                    <form:hidden path="houseHoldId"/>
                    <div class="form-group">
                        <label for="name" class="col-sm-4 control-label">Name</label>

                        <div class="col-sm-8">
                            <form:input path="name" class="form-control" placeholder="Name"/>
                            <div class="has-error">
                                <form:errors path="name" class="help-inline"/>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="members" class="col-sm-4 control-label">Members</label>

                        <div class="col-sm-8">
                            <form:select items="${users}" itemValue="userId" itemLabel="fullName" path="members" class="form-control"/>
                            <div class="has-error">
                                <form:errors path="members" class="help-inline"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-lg-8 col-lg-offset-4">
                            <button type="button" id="cancel" class="btn btn-default">Cancel</button>
                            <button type="submit" class="btn btn-primary"><i class="fa fa-save"></i> Save</button>
                        </div>
                    </div>
                </fieldset>
            </form:form>
        </div>
        <table id="hhTable" class="table table-bordered table-striped">
            <thead>
            <tr>
                <th>Name</th>
                <th>Members</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${houseHolds}" var="houseHold">
                <tr>
                    <td>${houseHold.name}</td>
                    <td>
                        <c:forEach items="${houseHold.members}" var="m">
                            ${m}<br/>
                        </c:forEach>
                    </td>
                    <td>
                        <a href="<c:url value='/admin/houseHold/${houseHold.houseHoldId}'/>">
                            <i class="fa fa-trash"></i>
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<script>
    $( document ).ready(function() {
        $('#houseHold').toggle();
        $('#btnAdd').click(function() {
            $('#houseHold').toggle();
            $(this).toggle();
        });
        $('#cancel').click(function() {
            $('#houseHold').toggle();
            $('#btnAdd').toggle();
        });
        $('#hhTable').DataTable();
    });

</script>

