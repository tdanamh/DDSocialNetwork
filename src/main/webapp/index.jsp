<html>
<head>
 <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
<div class="topBar">
    <div class="headingTextContainer">
        <h2 class="headingText">DD Social Network</h2>
    </div>
    <div class="loginFormContainer">
        <form id="loginForm" action="login" method="post">
            <label class="loginLabel" for="email">Email</label>
            <input id="email" type="email" name="email" required>
            <label class="loginLabel" for="password">Password</label>
            <input id="password" type="password" name="password" required>
            <input id="loginButton" type="submit" value="Log In">
        </form>
        <%
            String loginError = "";
            if(request.getAttribute("loginErrorMessage") != null){
                loginError = request.getAttribute("loginErrorMessage").toString();
            }
        %>
        <h4 class="loginError"><%= loginError %></h4>
    </div>
</div>
<div id="mainView">
    <div class="registerFormContainer">

        <h2 class="registerHeadingText">Create a new account</h2>
        <h3 class="registerText">It's completely free.</h3>
        <h2 class="registerError">
            <%
                String error = "";
                String firstName = "";
                String lastName = "";
                String email = "";
                if(request.getAttribute("errorMessage") != null){
                    error = request.getAttribute("errorMessage").toString();
                    firstName = request.getAttribute("firstName").toString();
                    lastName = request.getAttribute("lastName").toString();
                    email = request.getAttribute("email").toString();
                 }
            %>
            <%= error %>
        </h2>
        <form id="registerForm" action="register" method="POST">
            <div id="userNames">
                <input id="firstName" type="text" name="firstName" placeholder=" First Name"  value="<%= firstName %>" required>
                <input id="lastName" type="text" name="lastName" placeholder=" Last Name" value="<%= lastName %>" required>
            </div>
            <input id="registerEmail" type="email" name="email" placeholder=" Email address" value="<%= email %>" required>
            <input id="registerPassword" type="password" name="password" placeholder=" New password" required>
            <div id="userGenre">
                <input type="radio" name="gender" value="male" checked> Male
                <input type="radio" name="gender" value="female"> Female
                <input type="radio" name="gender" value="other"> Something strange
            </div>
            <input id="registerButton" type="submit" value="Sign Up">

        </form>
    </div>
    <div class="aboutContainer">
        <ul class="aboutList">
            <li class="about">Meet new people right now</li>
            <li class="about">Discover new places</li>
            <li class="about">Share your photos and videos</li>
        </ul>
    </div>
</div>


</body>
</html>
