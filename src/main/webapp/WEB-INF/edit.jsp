<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Form</title>
</head>
<body>
<form name="f2" method="post" action="edit-user">
    Login: <br />
    <input name="login" type="text" size="25" maxlength="30" value="${user.login}" /> <br />
    Password: <br />
    <input name="pd" type="password" size="25" maxlength="30" value="${user.password}" /> <br />
    <input type="submit" name="enter" value="Send" />
</form>
</body>
</html>