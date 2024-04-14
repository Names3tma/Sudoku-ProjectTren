function addData()
{
    var username = document.getElementById('username').value;
    var password = document.getElementById("password").value;

    localStorage.setIteam('userEmail',username);
    localStorage.setItem('userPassword',password);
}