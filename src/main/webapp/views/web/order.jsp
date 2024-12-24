<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ndl22
  Date: 12/5/2022
  Time: 10:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css"
        integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css"
        integrity="sha512-xh6O/CkQoPOWDdYTDqeRdPCVd1SpvCA9XXcUnZS2FmJNp1coAFzvtCN9BmamE+4aHK8yyUHUSCcJHgXloTyT2A=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
  <link
          href="https://fonts.googleapis.com/css2?family=Open+Sans:ital,wght@0,300;0,400;0,500;0,600;0,700;0,800;1,300;1,400;1,500;1,600;1,700&display=swap"
          rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick.min.css"
        integrity="sha512-yHknP1/AwR+yx26cB1y0cjvQUMvEa2PFzt1c9LlS4pRQ5NOTZFWbhBig+X9G9eYW/8m0/4OXNx8pxJ6z57x0dw=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
  <link rel="stylesheet" href="<c:url value='/templates/styles/Header.css'/> " />
  <link rel="stylesheet" href="<c:url value='/templates/styles/Order.css'/> " />
  <link rel="stylesheet" href="<c:url value='/templates/styles/Footer.css'/> " />
  <title>Order</title>
</head>
<style>

  .container_left {
    float: left;
    width: 70%;
    padding-left: 12px;
  }
</style>
<body>
<!-- -----------phần header----------------  -->
<%@include file="/common/web/header.jsp"%>
<!--------- end header---------- -->
<div class="container content">
  <h1 class="header_top">ĐẶT HÀNG</h1>
  <c:forEach items="${list}" var="item">
    <h1>${item}</h1>
  </c:forEach>
  <div class="container mt-5">
    <form id="submit_form" action="${pageContext.request.contextPath}/generate-pdf" method="post" target="_blank">
      <div class="row">
        <!-- Thông tin đơn hàng -->
        <div class="col-md-6">
          <h2 class="mb-4">THÔNG TIN ĐƠN HÀNG</h2>
          <div class="form-group">
            <label for="name">Họ và tên:</label>
            <input type="text" class="form-control" id="name" name="name" placeholder="Họ và tên"
                   value="${customer.firstName} ${customer.lastName}" required>
          </div>
          <div class="form-group">
            <label for="phone">Điện thoại:</label>
            <input name="phone" type="text" class="form-control" id="phone" placeholder="Số điện thoại"
                   value="${customer.phone}" required>
          </div>
          <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" class="form-control" id="email" name="email" placeholder="Email"
                   value="${customer.email}" required>
          </div>
          <div class="form-group">
            <label for="address">Địa chỉ nhận:</label>
            <input name="address" type="text" class="form-control" id="address" placeholder="Địa chỉ nhận" required>
          </div>
          <div class="form-row">
            <div class="form-group col-md-4">
              <label for="city">Tỉnh/Thành:</label>
              <select name="city" class="form-control" id="city" required>
                <option value="" selected>Chọn tỉnh thành</option>
                <!-- Options -->
              </select>
            </div>
            <div class="form-group col-md-4">
              <label for="district">Quận/Huyện:</label>
              <select name="district" class="form-control" id="district" required>
                <option value="" selected>Chọn quận huyện</option>
                <!-- Options -->
              </select>
            </div>
            <div class="form-group col-md-4">
              <label for="ward">Phường/Xã:</label>
              <select name="ward" class="form-control" id="ward" required>
                <option value="" selected>Chọn phường xã</option>
                <!-- Options -->
              </select>
            </div>
          </div>
          <div class="form-group">
            <label for="pack">Đóng gói:</label>
            <select name="pack" class="form-control" id="pack" required>
              <option value="" selected>Chọn quy cách đóng gói</option>
              <option value="0">Bọc Blatic</option>
              <option value="1">Để nguyên seal</option>
            </select>
          </div>
          <div class="form-group">
            <label for="pay">Hình thức thanh toán:</label>
            <select name="pay" class="form-control" id="pay" required>
              <option value="" selected>Chọn hình thức</option>
              <option value="0">COD: Giao hàng nhận tiền</option>
              <option value="1">Chuyển khoản</option>
            </select>
          </div>
          <div class="form-group">
            <label for="note">Ghi chú:</label>
            <textarea class="form-control" name="note" rows="3" id="note" placeholder="Ghi chú về đơn hàng"></textarea>
          </div>
        </div>

        <!-- Chi tiết đơn hàng -->
        <div class="col-md-6">
          <h2 class="mb-4">CHI TIẾT ĐƠN HÀNG</h2>
          <div class="container_order">
            <div class="wrap_order">
              <c:forEach var="item" items="${sessionScope.cartOrder.map}">
                <input type="hidden" name="productNames[]" value="${item.value.product.name}">
                <input type="hidden" name="productQuantities[]" value="${item.value.quantity}">
                <input type="hidden" name="productIds[]" value="${item.key}">
                <h5 class="mb-2">${item.value.product.name}</h5>
                <p class="mb-1">Mã SP: ${item.key}</p>
                <p>Số lượng: ${item.value.quantity}</p>
                <hr>
              </c:forEach>
            </div>
            <div class="wrap_left">
              <p class="mt-3"><strong>Tổng tiền:</strong> ${sessionScope.cartOrder.totalPrice} đ</p>
              <input type="hidden" name="totalPrice" value="${sessionScope.cartOrder.totalPrice}">
              <p><strong>Phí vận chuyển:</strong> 0 đ</p>
              <input type="hidden" name="shippingFee" value="0">
              <p><strong>Giảm giá:</strong> ${sessionScope.cartOrder.voucher} đ</p>
              <input type="hidden" name="voucher" value="${sessionScope.cartOrder.voucher}">
              <p><strong>Thanh toán:</strong> ${sessionScope.cartOrder.totalPrice - sessionScope.cartOrder.voucher} đ</p>
            </div>
          </div>
        </div>
      </div>


      <!-- Submit button -->
      <div class="text-center mt-4">
        <button type="submit" class="btn btn-success btn-lg">Xác nhận và tải PDF</button>
      </div>
    </form>
  </div>



</div>

<!-- --------footer ------------->
<!-- nut cuon len dau trang -->
<%@include file="/common/web/footer.jsp"%>

<script>
  // Biến lưu số lượng đơn hàng ban đầu
  let initialOrderCount = ${sessionScope.orderCount}; // Số lượng đơn hàng lúc bắt đầu (lấy từ server)
  console.log("Initial order count:", initialOrderCount); // In ra số lượng đơn hàng ban đầu

  // Hàm kiểm tra số lượng đơn hàng
  function checkOrderStatus() {
    fetch('${pageContext.request.contextPath}/check-order-status', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      }
    })
            .then(response => {
              console.log("API response status:", response.status); // Kiểm tra trạng thái phản hồi API
              return response.json();
            })
            .then(data => {
              console.log("Current order count from server:", data.orderCount); // In ra số lượng đơn hàng hiện tại từ API
              console.log("Initial order count:", initialOrderCount); // In ra số lượng đơn hàng ban đầu để so sánh

              if (data.orderCount !== initialOrderCount) {
                // Số lượng đơn hàng đã thay đổi
                console.log("Order count has changed. Redirecting..."); // Thông báo trước khi chuyển trang
                window.location.href = '${pageContext.request.contextPath}/order/reviewOrder?orderSuccess=1&isVerify=1';
              }
            })
            .catch(error => console.error('Lỗi khi kiểm tra trạng thái đơn hàng:', error)); // Xử lý lỗi
  }

  // Kiểm tra đơn hàng mỗi 10 giây
  const intervalId = setInterval(checkOrderStatus, 10000); // 10000 ms = 10 giây

  // Sau 30 phút, dừng kiểm tra và chuyển hướng nếu chưa thay đổi
  setTimeout(function () {
    clearInterval(intervalId); // Dừng kiểm tra
    console.log("Timeout reached. Redirecting to default page..."); // Thông báo trước khi chuyển trang mặc định
    window.location.href = '${pageContext.request.contextPath}/order/reviewOrder?orderSuccess=1&isVerify=0';
  }, 1800000); // 30 phút

</script>

<!-- ----js phần header -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.3/jquery.min.js" integrity="sha512-STof4xm1wgkfm7heWqFJVn58Hm3EtS31XFaagaa8VMReCXAkQnJZ+jEy8PCC/iT18dFy95WcExNHFTqLyp72eQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
        integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.min.js"
        integrity="sha384-+sLIOodYLS7CIrQpBjl+C7nPvqq+FbNUBDunl/OZv93DB7Ln/533i8e/mZXLi/P+"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct"
        crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/templates/scripts/header.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.1/axios.min.js"></script>
<script src="${pageContext.request.contextPath}/templates/scripts/order.js"></script>


<script>
  $('.id_voucher').on('click', function () {
    const pId = $(this).val()
    window.location.href = '${pageContext.request.contextPath}/order?id=' + pId
  })
</script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>

</body>

</html>
