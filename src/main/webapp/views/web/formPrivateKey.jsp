<%@ include file="/common/taglib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>
    <title>Private key</title>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css"
          integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
    <link
            href="https://fonts.googleapis.com/css2?family=Open+Sans:ital,wght@0,300;0,400;0,500;0,600;0,700;0,800;1,300;1,400;1,500;1,600;1,700&display=swap"
            rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/templates/styles/Login.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/templates/styles/Header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/templates/styles/Footer.css">
    <style>
        ._4z_d ._4z_f {
            font-size: 16px!important;
            line-height: 14px;
            padding: 2px 6px;
        }
        :root {
            --bs-blue-default: #1a94ff;
        }
        .privateKey {
            font-family: Arial, sans-serif;
            background-color: #ffffff;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .privateKey form {
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 60px;
            width: 600px;
        }

        .privateKey fieldset {
            border-radius: 5px;
            padding: 10px;
            margin-bottom: 15px;
        }

        .privateKey legend {
            font-size: 24px;
            font-weight: bold;
            color: #333;
            text-align: center;
        }

        .privateKey label {
            display: block;
            margin-bottom: 8px;
            font-size: 14px;
            color: #333;
        }

        .privateKey textarea {
            width: 100%;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            margin-bottom: 10px;
            resize: vertical;
            font-size: 12px;
        }

        .privateKey button {
            background-color: #1a94ff;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            width: 100%;
            text-align: center;
        }

        button:hover {
            opacity: 0.8;
        }
    </style>
</head>

<body>
<%@include file="/common/web/header.jsp"%>

<!--------- end header---------- -->
<!--------- login form---------- -->
<div class="privateKey">
    <form action="${pageContext.request.contextPath}/privateKey" method="post">
        <fieldset>
            <legend>Private Key</legend>
            <label for="privateKey">Nhập key private của bạn ở đây:</label>
            <textarea id="privateKey" name="privateKey" rows="8" cols="60" required></textarea><br>
            <button type="submit">Tiếp tục</button>
        </fieldset>
    </form>
</div>
<!-----end login---->
<!-----footer------>
<%@include file="/common/web/footer.jsp"%>


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
<script src="/templates/scripts/header.js"></script>

</body>

</html>