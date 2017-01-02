<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="col-md-offset-2 col-md-8 col-lg-offset-3 col-lg-6">
    <div class="col-sm-12">
        <div class="col-xs-12 col-sm-8">
            <h2>${self.fullName}</h2>
            <p><strong>User ID: </strong> ${self.userId} </p>
            <p><strong>Email: </strong> ${self.email} </p>
            <p><strong>Members since: </strong> ${self.createdDate} </p>
            <p><strong>Roles: </strong>
                <c:forEach items="${self.roles}" var="role">
                    <span class="tags">${role}</span>
                </c:forEach>
            </p>
        </div>
        <div class="col-xs-12 col-sm-4 text-center">
            <figure>
                <img src="http://babyinfoforyou.com/wp-content/uploads/2014/10/avatar-300x300.png" alt="" class="img-circle img-responsive">
                <figcaption class="ratings">
                    <p>
                        <a href="#">
                            <span class="fa fa-star"></span>
                        </a>
                        <a href="#">
                            <span class="fa fa-star"></span>
                        </a>
                        <a href="#">
                            <span class="fa fa-star"></span>
                        </a>
                        <a href="#">
                            <span class="fa fa-star"></span>
                        </a>
                        <a href="#">
                            <span class="fa fa-star"></span>
                        </a>
                    </p>
                </figcaption>
            </figure>
        </div>
    </div>
    <div class="col-xs-12 divider text-center">
        <div class="col-xs-12 col-sm-4 emphasis">
            <a href="<c:url value='/users/reset'/>" class="btn btn-success btn-block"><span class="fa fa-key"></span> Reset Password </a>
        </div>
        <div class="col-xs-12 col-sm-4 emphasis">
            <a href="<c:url value='/users/settings'/>" class="btn btn-info btn-block"><span class="fa fa-user"></span> Edit Profile </a>
        </div>
        <div class="col-xs-12 col-sm-4 emphasis">
            <div class="btn-group">
                <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <span class="fa fa-gear"></span> Settings <span class="caret"></span>
                </button>
                <ul class="dropdown-menu">
                    <li><a href="<c:url value='/household/category'/>"><span class="fa fa-list"></span> Categories </a></li>
                    <li><a href="<c:url value='/household/dropbox'/>"><span class="fa fa-dropbox fa-fw"></span> Dropbox</a></li>
                </ul>
            </div>

        </div>
    </div>
</div>