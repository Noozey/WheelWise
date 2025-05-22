<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Contact Messages</title>
    <link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body style="display:flex;">
<jsp:include page="adminNavBar.jsp" />
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Subject</th>
            <th>Message</th>
            <th>Submitted At</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="msg" items="${messages}">
            <tr>
                <td>${msg.id}</td>
                <td>${msg.name}</td>
                <td>${msg.email}</td>
                <td>${msg.phone}</td>
                <td>${msg.subject}</td>
                <td class="message-box">${msg.message}</td>
                <td>${msg.submittedAt}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
