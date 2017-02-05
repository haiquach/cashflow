<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
    .clickable{
        cursor: pointer;
    }

    .panel-heading span {
        margin-top: -20px;
        font-size: 15px;
    }
</style>
<c:forEach items="${txn}" var="snapshot">
    <div class="panel panel-info">
        <div class="panel-heading">
            <h4 class="panel-title">${snapshot.displayDate}</h4>
            <span class="pull-right clickable"><i class="fa fa-chevron-up"></i></span>
        </div>
        <div class="panel-body">
            <c:forEach items="${snapshot.sortedByDateTransactions}" var="transaction">
                <div class="col-md-2">${transaction.date}</div>
                <div class="col-md-10">
                    <div class="row-fluid">
                        <div class="col-sm-9">${transaction.description}</div>
                        <div class="col-sm-3">
                            <c:if test="${transaction.amount > 0}">
                                <span style="color: blue;">
                                    <fmt:formatNumber value="${transaction.amount}" type="currency" currencySymbol="${transaction.currency.symbol}"/>
                                </span>
                            </c:if>
                            <c:if test="${transaction.amount < 0}">
                                <span style="color: red;">
                                    <fmt:formatNumber value="${transaction.amount * (-1)}" type="currency" currencySymbol="${transaction.currency.symbol}"/>
                                </span>
                            </c:if>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</c:forEach>

<script>
    $(document).on('click', '.panel-heading span.clickable', function(e){
        var $this = $(this);
        if(!$this.hasClass('panel-collapsed')) {
            $this.parents('.panel').find('.panel-body').slideUp();
            $this.addClass('panel-collapsed');
            $this.find('i').removeClass('fa fa-chevron-up').addClass('fa fa-chevron-down');
        } else {
            $this.parents('.panel').find('.panel-body').slideDown();
            $this.removeClass('panel-collapsed');
            $this.find('i').removeClass('fa fa-chevron-down').addClass('fa fa-chevron-up');
        }
    })
</script>