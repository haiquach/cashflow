<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="panel panel-info">
    <div class="panel-heading">
        <a href="<c:url value='/finance/item/exp'/>" class="btn btn-primary pull-right"><i class="fa fa-plus-square"></i>  Add Expense</a>
        <div class="clearfix"></div>
    </div>
    <!-- /.box-header -->
    <div class="panel-body">
        <table id="expenseTable" class="table table-bordered table-striped">
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
            <c:forEach items="${expenses}" var="expense">
                <tr>
                    <td>${expense.category}</td>
                    <td><fmt:formatDate value="${expense.effective}" pattern="MM/dd/yyyy"/></td>
                    <td><fmt:formatNumber value="${expense.amount}" type="currency"/></td>
                    <td>${expense.note}</td>
                    <td>
                        <c:if test="${expense.yours}">
                            <a href="<c:url value='/finance/items/remove/exp/${expense.id}'/>">
                                <i class="fa fa-trash"></i>
                            </a>
                        </c:if>
                        <c:if test="${!empty expense.receipt}">
                            <a data-toggle="modal" data-uri="c:url value='/finance/download/${expense.id}'/>" title="Receipt" class="viewReceipt" href="#viewReceiptModal">
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
        $('#expenseTable').DataTable({
            "order": [[ 1, "desc" ]]
        });

        $('#viewReceiptModal').on('show.bs.modal', function(e) {
            var uri = $(e.relatedTarget).data('uri');
            $(e.currentTarget).find('img').attr('src', uri);
        });
    });
</script>