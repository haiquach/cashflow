<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="panel panel-info">
    <div class="panel-heading">
        <a href="<c:url value='/finance/item/inc'/>" class="btn btn-primary pull-right"><i class="fa fa-plus-square"></i>  Add Income</a>
        <div class="clearfix"></div>
    </div>
    <!-- /.box-header -->
    <div class="panel-body">
        <table id="incomeTable" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>Category</th>
                    <th>Effective Date</th>
                    <th>Amount</th>
                    <th>Note</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${incomes}" var="income">
                <tr>
                    <td>${income.category}</td>
                    <td><fmt:formatDate value="${income.effective}" pattern="MM/dd/yyyy"/></td>
                    <td><fmt:formatNumber value="${income.amount}" type="currency"/></td>
                    <td>${income.note}</td>
                    <td>
                        <c:if test="${income.yours}">
                            <a href="<c:url value='/finance/items/remove/inc/${income.id}'/>">
                                <i class="fa fa-trash"></i>
                            </a>
                        </c:if>
                        <c:if test="${!empty income.receipt}">
                            <a data-toggle="modal" data-uri="<c:url value='/finance/download/${income.id}'/>" title="Receipt" class="viewReceipt" href="#viewReceiptModal">
                                <i class="fa fa-dropbox"></i>
                            </a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<div id="viewReceiptModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <img src="#" class="img-responsive">
            </div>
        </div>
    </div>
</div>

<script>
    $( document ).ready(function() {
        $('#incomeTable').DataTable({
            "order": [[ 1, "desc" ]]
        });
        $('#viewReceiptModal').on('show.bs.modal', function(e) {
            var uri = $(e.relatedTarget).data('uri');
            $(e.currentTarget).find('img').attr('src', uri);
        });
    });
</script>

