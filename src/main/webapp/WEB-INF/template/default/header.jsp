<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<sec:authorize access="isAuthenticated()">
    <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
            <i class="fa fa-user fa-fw"></i>
            <sec:authentication property="principal.username" />
            <i class="fa fa-caret-down"></i>
        </a>
        <ul class="dropdown-menu dropdown-user">
            <li><a href="<c:url value='/users/profile'/>"><i class="fa fa-user fa-fw"></i> User Profile</a></li>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <li class="divider"></li>
                <li><a href="<c:url value='/admin/list'/>"><i class="fa fa-gear fa-fw"></i> Administration</a></li>
                <li><a href="<c:url value='/admin/houseHold'/>"><i class="fa fa-home fa-fw"></i> House Hold</a></li>
            </sec:authorize>
            <li class="divider"></li>
            <li><a href="<c:url value='/logout'/>"><i class="fa fa-sign-out fa-fw"></i> Logout</a></li>
        </ul>
    </li>
</sec:authorize>
<sec:authorize access="!isAuthenticated()">
    <li><a href="<c:url value='/login'/>"><i class="fa fa-sign-out fa-fw"></i> Sign In</a></li>
</sec:authorize>
