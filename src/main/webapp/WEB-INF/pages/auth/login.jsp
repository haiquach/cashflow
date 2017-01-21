<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title><tiles:putAttribute name="title"/></title>

    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

    <!-- Bootstrap Core CSS -->
    <link href="<s:url value='/resources/css/bootstrap.min.css'/>" rel="stylesheet" type="text/css">

    <!-- Custom Fonts -->
    <link href="<s:url value='/resources/css/font-awesome.min.css'/>" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style>
        .login-page {
            background: #d2d6de;
        }
        .login-box {
            width: 360px;
            margin: 7% auto;
        }
        .login-logo {
            font-size: 35px;
            text-align: center;
            margin-bottom: 25px;
            font-weight: 300;
        }
        .login-box-body {
            background: #fff;
            padding: 20px;
            border-top: 0;
            color: #666;
        }
        .login-box-body .form-control-feedback {
            color: #777;
        }
        .login-box-msg {
            margin: 0;
            text-align: center;
            padding: 0 20px 20px 20px;
        }
        .form-control-feedback.fa {
            line-height: 34px;
        }
        .btn.btn-flat {
            border-radius: 0;
            -webkit-box-shadow: none;
            -moz-box-shadow: none;
            box-shadow: none;
            border-width: 1px;
        }
    </style>
</head>
<body class="login-page">

<div class="login-box">
    <div class="login-logo">
        Profit and Loss
    </div>
    <div class="login-box-body">
        <c:if test="${param.error != null}">
            <div class="alert alert-danger">
                <p>Invalid username and password.</p>
            </div>
        </c:if>
        <c:if test="${param.logout != null}">
            <div class="alert alert-success">
                <p>You have been logged out successfully.</p>
            </div>
        </c:if>
        <p class="login-box-msg">Sign in to start your session</p>
        <form action="login" method="post">
            <div class="form-group has-feedback">
                <input type="text" name="userId" class="form-control" placeholder="Username" autofocus/>
                <span class="fa fa-user form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="password" name="password" class="form-control" placeholder="Password"/>
                <span class="fa fa-lock form-control-feedback"></span>
            </div>
            <div class="row">
                <div class="col-xs-8">
                    <div class="checkbox icheck">
                        <label>
                            <input name="remember-me" type="checkbox" value="Remember Me">Remember Me
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </label>
                    </div>
                </div>
                <div class="col-xs-4">
                    <button type="submit" class="btn btn-primary btn-block btn-flat"><i class="fa fa-sign-in"></i> Sign In</button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
