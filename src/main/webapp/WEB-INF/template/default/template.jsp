<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <tiles:putAttribute name="title"/>

    <!-- Bootstrap Core CSS -->
    <link href="<c:url value='/resources/css/bootstrap.min.css'/>" rel="stylesheet">
    <!-- Custom Fonts -->
    <link href="<c:url value='/resources/css/font-awesome.min.css'/>" rel="stylesheet" type="text/css">
    <!-- Datatable CSS -->
    <link href="<c:url value='/resources/css/dataTables.bootstrap.min.css'/>" rel="stylesheet" type="text/css">
    <!-- DatePicker CSS -->
    <link href="<c:url value='/resources/css/datepicker.css'/>" rel="stylesheet">
    <!-- Morris CSS -->
    <link href="<c:url value='/resources/css/morris.css'/>" rel="stylesheet">
    <!-- bootstrap file input -->
    <!-- http://plugins.krajee.com/file-input -->
    <link href="<c:url value='/resources/css/fileinput.min.css'/>" rel="stylesheet">

    <link href="<c:url value='/resources/css/bootstrap-tagsinput.css'/>" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- jQuery -->
    <script src="<c:url value='/resources/js/jquery-3.1.0.min.js'/>"></script>
    <!-- Bootstrap Core JavaScript -->
    <script src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
    <!-- datatable -->
    <script src="<c:url value='/resources/js/jquery.dataTables.min.js'/>"></script>
    <script src="<c:url value='/resources/js/dataTables.bootstrap.min.js'/>"></script>
    <!-- Date Picker JavaScript -->
    <script src="<c:url value='/resources/js/bootstrap-datepicker.js'/>"></script>
    <!-- Bootstrap3 tagsinput JavaScript -->
    <script src="<c:url value='/resources/js/bootstrap-tagsinput.min.js'/>"></script>
    <!-- Morris JavaScript -->
    <script src="<c:url value='/resources/js/raphael-min.js'/>"></script>
    <script src="<c:url value='/resources/js/morris.min.js'/>"></script>

    <!-- file style -->
    <script src="<c:url value='/resources/js/fileinput.min.js'/>"></script>
</head>
<body>
<div class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <a href="<c:url value='/'/>" class="navbar-brand"><i class="fa fa-2x fa-home"></i></a>
            <button class="navbar-toggle" type="button" data-toggle="collapse" data-target="#navbar-main">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>

        <div class="navbar-collapse collapse" id="navbar-main">
            <ul class="nav navbar-nav">
                <tiles:insertAttribute name="menu" />
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <tiles:insertAttribute name="header"/>
            </ul>
        </div>
    </div>
</div>
<div class="container">
    <div class="page-header" id="banner">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header"><tiles:getAsString name="headerTitle"/></h1>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <tiles:insertAttribute name="body"/>
            </div>
        </div>
    </div>
</div>
</body>
</html>