<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="header">
  <header>
    <nav class="navbar">
      <!-- Logo -->
      <a href="${pageContext.request.contextPath}/home" class="logo">
        <img src="${pageContext.request.contextPath}/resources/images/logo/logo.png" alt="Car Logo" class="logo-img">
        <span class="brand-name">WheelWise</span>
      </a>

      <!-- Hamburger -->
      <div class="menu-toggle" onclick="toggleMenu()">
        <span></span><span></span><span></span>
      </div>

      <!-- Menu -->
      <ul id="nav-menu" class="nav-menu">
        <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
        <li><a href="${pageContext.request.contextPath}/product">Product</a></li>
        <li><a href="${pageContext.request.contextPath}/profile">Profile</a></li>
        <li><a href="${pageContext.request.contextPath}/about">About Us</a></li>
        <li><a href="${pageContext.request.contextPath}/contact">Contact</a></li>
      </ul>

      <!-- Auth Buttons -->
      <div class="auth-buttons">
        <c:choose>
          <c:when test="${not empty sessionScope.userId}">
            <a href="${pageContext.request.contextPath}/cart" class="btn login-btn">Cart</a>
            <a href="${pageContext.request.contextPath}/logout" class="btn signup-btn">Sign Out</a>
          </c:when>
          <c:otherwise>
            <a href="${pageContext.request.contextPath}/login" class="btn login-btn">Login</a>
            <a href="${pageContext.request.contextPath}/register" class="btn signup-btn">Sign Up</a>
          </c:otherwise>
        </c:choose>
      </div>
    </nav>
  </header>

  <script type="text/javascript">
    function toggleMenu() {
      const menu = document.getElementById("nav-menu");
      menu.classList.toggle("active");
    }
  </script>
</div>
