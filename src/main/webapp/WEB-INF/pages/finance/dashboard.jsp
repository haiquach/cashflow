<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row">
    <div class="col-lg-4 col-md-4">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-xs-4">
                        <i class="fa fa-indent fa-5x"></i>
                    </div>
                    <div class="col-xs-8">
                        <div class="huge">
                            <fmt:formatNumber value="${income}" type="currency"/>
                        </div>
                        <div>Total Income</div>
                    </div>
                </div>
            </div>
            <a href="<c:url value='/finance/incomes'/>">
                <div class="panel-footer">
                    <span class="pull-left">View Details</span>
                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                    <div class="clearfix"></div>
                </div>
            </a>
        </div>
    </div>
    <div class="col-lg-4 col-md-4">
        <div class="panel panel-warning">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-xs-4">
                        <i class="fa fa-outdent fa-5x"></i>
                    </div>
                    <div class="col-xs-8">
                        <div class="huge"><fmt:formatNumber value="${expense}" type="currency"/></div>
                        <div>Total Expense</div>
                    </div>
                </div>
            </div>
            <a href="<c:url value='/finance/expenses'/>">
                <div class="panel-footer">
                    <span class="pull-left">View Details</span>
                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                    <div class="clearfix"></div>
                </div>
            </a>
        </div>
    </div>
    <c:choose>
        <c:when test="${revenue > 0}">
            <c:set var="color" value="panel-success"/>
        </c:when>
        <c:otherwise>
            <c:set var="color" value="panel-danger"/>
        </c:otherwise>
    </c:choose>
    <div class="col-lg-4 col-md-4">
        <div class="panel ${color}">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-xs-4">
                        <i class="fa fa-money fa-5x"></i>
                    </div>
                    <div class="col-xs-8">
                        <div class="huge">
                            <fmt:formatNumber value="${revenue}" type="currency"/>
                        </div>
                        <div>Revenue</div>
                    </div>
                </div>
            </div>
            <a href="<c:url value='/finance/revenue'/>">
                <div class="panel-footer">
                    <span class="pull-left">View Details</span>
                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                    <div class="clearfix"></div>
                </div>
            </a>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-lg-4">
        <div class="panel panel-default">
            <div class="panel-heading">
                <i class="fa fa-pie-chart fa-fw"></i> Incomes Summary Chart - <fmt:formatNumber value="${income}" type="currency"/>
            </div>
            <div class="panel-body">
                <div id="incomeCharts"></div>
            </div>
        </div>
    </div>
    <div class="col-lg-4">
        <div class="panel panel-default">
            <div class="panel-heading">
                <i class="fa fa-pie-chart fa-fw"></i> Expenses Summary Chart - <fmt:formatNumber value="${expense}" type="currency"/>
            </div>
            <div class="panel-body">
                <div id="expenseCharts"></div>
            </div>
        </div>
    </div>
    <div class="col-lg-4">
        <div class="panel panel-default">
            <div class="panel-heading">
                <i class="fa fa-pie-chart fa-fw"></i> Summary Chart - <fmt:formatNumber value="${revenue/income}" type="PERCENT"/>
            </div>
            <div class="panel-body">
                <div id="profitCharts"></div>
            </div>
        </div>
    </div>
</div>
<div class="row">
    <div class="panel panel-default">
        <div class="panel-heading">
            <i class="fa fa-bar-chart-o fa-fw"></i> Monthly Summary Chart
        </div>
        <div class="panel-body">
            <div id="summary"></div>
        </div>
    </div>
</div>

<script>

    $(document).ready(function() {
        $.ajax({
            type : "GET",
            contentType : "application/json",
            url : "<c:url value='/finance/summary'/>",
            timeout : 100000,
            success : function(data) {
                console.log("SUCCESS: ", data);
                Morris.Bar({
                    element: 'summary',
                    data: data,
                    barColors: ['#00a65a', '#f56954'],
                    xkey: 'label',
                    ykeys: ['income', 'expense'],
                    labels: ["Income", "Expense"]
                }).on('click', function(i, row){
                    console.log(i, row);
                });
            },
            error : function(e) {
                console.log("ERROR: ", e);
            },
            done : function(e) {
                console.log("DONE");
            }
        });
        $.ajax({
            type : "GET",
            contentType : "application/json",
            url : "<c:url value='/finance/summary/inc'/>",
            timeout : 100000,
            success : function(data) {
                console.log("SUCCESS: ", data);
                Morris.Donut({
                    element: 'incomeCharts',
                    data: data,
                    colors: ["#3c8dbc", "#f56954", "#00a65a"]
                });
            },
            error : function(e) {
                console.log("ERROR: ", e);
            },
            done : function(e) {
                console.log("DONE");
            }
        });
        $.ajax({
            type : "GET",
            contentType : "application/json",
            url : "<c:url value='/finance/summary/exp'/>",
            timeout : 100000,
            success : function(data) {
                console.log("SUCCESS: ", data);
                Morris.Donut({
                    element: 'expenseCharts',
                    data: data,
                    colors: ["#3c8dbc", "#f56954", "#00a65a"]
                });
            },
            error : function(e) {
                console.log("ERROR: ", e);
            },
            done : function(e) {
                console.log("DONE");
            }
        });
        Morris.Donut({
            element: 'profitCharts',
            data: [
                {value: ${expense}, label: "Total Expenses"},
                {value: ${income - expense}, label: "Total Earning"}
            ],
            colors: ['#f56954', '#00a65a']
        });
    });
</script>

