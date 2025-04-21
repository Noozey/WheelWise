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
        <a href="${pageContext.request.contextPath}/login" class="btn login-btn">Login</a>
        <a href="${pageContext.request.contextPath}/register" class="btn signup-btn">Sign Up</a>
      </div>
    </nav>
  </header>
  <script type="text/javascript">
  
  </script>
</div>
