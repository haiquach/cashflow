<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="panel panel-info">
  <div class="panel-heading">
      <a href="<c:url value='/admin/newuser'/>" class="btn btn-primary pull-right"> <i class="fa fa-plus-square"></i>  Add User</a>
    <div class="clearfix"></div>
  </div>
  <!-- /.box-header -->
  <div class="panel-body">
    <c:if test="${not empty message}">
      <div class="alert alert-success">
        ${message}
      </div>
    </c:if>
    <table id="users" class="table table-bordered table-striped">
      <thead>
      <tr>
        <th>Firstname</th>
        <th>Lastname</th>
        <th>Email</th>
        <th>Username</th>
        <th>Action</th>
      </tr>
      </thead>
      <tbody>
        <c:forEach items="${users}" var="user">
          <tr>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.email}</td>
            <td>${user.userId}</td>
            <td>
              <a href="<c:url value='/admin/edit-user-${user.userId}'/>">
                <i class="fa fa-edit"></i>
              </a>
              <a href="<c:url value='/admin/reset-user-${user.userId}'/>">
                <i class="fa fa-refresh"></i>
              </a>
              <a href="<c:url value='/admin/delete-user-${user.userId}'/>">
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
  $(document).ready(function() {
    $('#users').DataTable({
        "paging": true,
        "lengthChange": false,
        "searching": false,
        "ordering": true,
        "info": true,
        "autoWidth": false
      }
    );
  });
</script>
