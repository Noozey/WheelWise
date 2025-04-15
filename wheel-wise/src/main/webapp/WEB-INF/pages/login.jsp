<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!doctype html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Login to your account</title>
        <!-- Set contextPath variable for reuse -->
        <link
            rel="stylesheet"
            type="text/css"
            href="${pageContext.request.contextPath}/css/log.css"
        />
    </head>
    <body>
        <div class="login-box">
            <h2>Login</h2>

            <!-- Display error message if available -->

            <form
                action="${pageContext.request.contextPath}/login"
                method="post"
            >
                <div class="row">
                    <div class="col">
                        <label for="username">Username:</label>
                        <input
                            type="text"
                            id="username"
                            name="username"
                            required
                        />
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <label for="password">Password:</label>
                        <input
                            type="password"
                            id="password"
                            name="password"
                            required
                        />
                    </div>
                </div>
                <button type="submit" class="login-button">Login</button>
            </form>
        </div>
    </body>
</html>
