function getAllTransactions() {
    var xhr =  new XMLHttpRequest();

    var url = 'http://localhost:8080/transactions';

    xhr.open("GET", url);
    xhr.setRequestHeader("ApiKeyAuth", sessionStorage.getItem("AuthToken"));
    xhr.onload= (e) => {
    }
    xhr.send();

    xhr.onreadystatechange=(e)=>{
        var obj = JSON.parse(xhr.responseText);
        CreateTableFromJSON(obj)
        document.getElementById("statusdisplay").innerHTML = "";
        // document.getElementById("search").reset();
    }
}

function CreateTableFromJSON(obj) {
    var col = [];
    for (var i = 0; i < obj.length; i++) {
        for (var key in obj[i]) {
            if (col.indexOf(key) === -1) {
                col.push(key);
            }
        }
    }

    // CREATE DYNAMIC TABLE.
    var table = document.createElement("table");

    // CREATE HTML TABLE HEADER ROW USING THE EXTRACTED HEADERS ABOVE.

    var tr = table.insertRow(-1);                   // TABLE ROW.

    for (var i = 0; i < col.length; i++) {
        var th = document.createElement("th");      // TABLE HEADER.
        th.innerHTML = col[i];
        tr.appendChild(th);
    }

    // ADD JSON DATA TO THE TABLE AS ROWS.
    for (var i = 0; i < obj.length; i++) {

        tr = table.insertRow(-1);

        for (var j = 0; j < col.length; j++) {
            var tabCell = tr.insertCell(-1);
            tabCell.innerHTML = obj[i][col[j]];
        }
    }

    // FINALLY ADD THE NEWLY CREATED TABLE WITH JSON DATA TO A CONTAINER.
    var divContainer = document.getElementById("datadisplay");

    divContainer.innerHTML = "";
    divContainer.appendChild(table);
}

function CreateTableFromJSONUserPage(obj) {
    var col = [];
    for (var i = 0; i < obj.length; i++) {
        for (var key in obj[i]) {
            if (col.indexOf(key) === -1) {
                col.push(key);
            }
        }
    }

    // CREATE DYNAMIC TABLE.
    var table = document.createElement("table");

    // CREATE HTML TABLE HEADER ROW USING THE EXTRACTED HEADERS ABOVE.

    var tr = table.insertRow(-1);                   // TABLE ROW.

    for (var i = 0; i < col.length; i++) {
        var th = document.createElement("th");      // TABLE HEADER.
        th.innerHTML = col[i];
        tr.appendChild(th);
    }

    // ADD JSON DATA TO THE TABLE AS ROWS.
    for (var i = 0; i < obj.length; i++) {

        tr = table.insertRow(-1);

        for (var j = 0; j < col.length; j++) {
            var tabCell = tr.insertCell(-1);
            tabCell.innerHTML = obj[i][col[j]];
        }
    }

    for (var i = 1; i < table.rows.length; i++) {
        for (var j = 0; j < 1; j++)
        table.rows[i].cells[j].onclick = function () {
            toUpdateScreen(this);
        };
    }

    function toUpdateScreen(tableCell) {
    var idArr = tableCell.outerHTML.split(">");
    var idArr2 = idArr[1].split("<");
    var id = idArr2[0];
    sessionStorage.setItem("UpdateId", id);
    if (id.length > 10) {
        window.location.href = "http://localhost:8080/updateAccount.html";
    }
    else {
        window.location.href = "http://localhost:8080/updateUser.html";
    }
    var divContainer = document.getElementById("datadisplay");
    divContainer.innerHTML = sessionStorage.getItem("UpdateId");
    }

    // FINALLY ADD THE NEWLY CREATED TABLE WITH JSON DATA TO A CONTAINER.
    var divContainer = document.getElementById("datadisplay");

    divContainer.innerHTML = "";
    divContainer.appendChild(table);
}

function getAllTransactionsForUser(){
    var xhr =  new XMLHttpRequest();
    var user_id = sessionStorage.getItem("UserId");
    var url = 'http://localhost:8080/users/'+user_id+'/transactions';
    //alert(url);
    xhr.open("GET", url);
    xhr.setRequestHeader("ApiKeyAuth", sessionStorage.getItem("AuthToken"));
    xhr.onload= (e) => {
    }
    xhr.send();

    xhr.onreadystatechange=(e)=>{
        var obj = JSON.parse(xhr.responseText);
        CreateTableFromJSON(obj)
        document.getElementById("statusdisplay").innerHTML = "";
        //document.getElementById("search").reset();
    }
}


function getTransactionById() {
    var xhr =  new XMLHttpRequest();
    var url = 'http://localhost:8080/users/'+document.forms["search"]["userId"].value+'/transactions';
    xhr.open("GET", url);
    xhr.setRequestHeader("ApiKeyAuth", sessionStorage.getItem("AuthToken"));

    xhr.onload= (e) => {
    }

    xhr.send();

    xhr.onreadystatechange=(e)=>{
        var obj = JSON.parse(xhr.responseText);
        CreateTableFromJSON(obj);
        //document.getElementById("search").reset();
    }
}

function getAllTransactionsWithPagination(command, url) {
    //alert("getAllTransactionsWithPagination1");
    var offset = Number(document.forms["search"]["offset"].value);
    var limit = Number(document.forms["search"]["limit"].value);
    var xhr =  new XMLHttpRequest();

    var account_url =document.forms["search"]["url"].value;

    if(account_url!=''){
        url = account_url+'&offset='+offset+'&limit='+limit;
    }
    else if(url==""){
        url = 'http://localhost:8080/transactions?offset='+offset+'&limit='+limit;
    }else{
        url = url+'?offset='+offset+'&limit='+limit;
    }
    //alert(url);
    xhr.open("GET", url);
    xhr.setRequestHeader("ApiKeyAuth", sessionStorage.getItem("AuthToken"));
    xhr.onload= (e) => {
    }
    xhr.send();
    var obj ;
    xhr.onreadystatechange=(e)=>{
        obj = JSON.parse(xhr.responseText);
        CreateTableFromJSON(obj)
        document.getElementById("statusdisplay").innerHTML = "";
        var length= obj.length;
        if(length<limit && command==2){
            var new_offset = offset-limit;
            if(new_offset<0){new_offset=0;}
            document.forms["search"]["offset"].value = new_offset;
            //alert("end");

        }
    }
}


function searchTransactionByCustomer(){

    var user_id = sessionStorage.getItem("UserId");
    document.forms["search"]["offset"].value = 0;
    document.forms["search"]["userId"].value=user_id;
    document.forms["search"]["url"].value= '';
    getTransactionNext();
}

function getTransactionNextByCustomer(){
    var user_id = sessionStorage.getItem("UserId");
    document.forms["search"]["userId"].value=user_id;
    getTransactionNext();
}

function getTransactionPrevByCustomer(){
    var user_id = sessionStorage.getItem("UserId");
    document.forms["search"]["userId"].value=user_id;
    getTransactionPrev();

}

function searchTransactionById(){
    document.forms["search"]["offset"].value = 0;
    document.forms["search"]["url"].value= '';
    getTransactionNext();
}

function searchTransactionByIBAN(){
    document.forms["search"]["offset"].value = 0;
    var iban = document.forms["search"]["iban"].value;
    if(iban== ''){
        alert("Please enter iban")
    }else{
        var url = 'http://localhost:8080/transactions?iban='+iban;
        document.forms["search"]["url"].value= url;
        getTransactionNext();
    }
}


function getTransactionPrev(){
    //alert("getTransactionPrev");
    var user_id = document.forms["search"]["userId"].value;
    var url = 'http://localhost:8080/transactions';
    if(user_id != ''){
        url = 'http://localhost:8080/users/'+document.forms["search"]["userId"].value+'/transactions';
    }
    //alert(url);
    var offset = document.forms["search"]["offset"].value;
    var limit = document.forms["search"]["limit"].value;
    if(offset < 0){
        alert("End"); //no prev data
    }else{
        getAllTransactionsWithPagination(1,url);
        var new_offset = offset-limit;
        document.forms["search"]["offset"].value = new_offset;
        document.forms["search"]["limit"].value = limit;
    }

}

function getTransactionNext(){
    var user_id = document.forms["search"]["userId"].value;
    var url = 'http://localhost:8080/transactions';
    if(user_id!=""){
        url = 'http://localhost:8080/users/'+document.forms["search"]["userId"].value+'/transactions';
    }
    var offset = Number(document.forms["search"]["offset"].value);
    var limit = Number(document.forms["search"]["limit"].value);
    getAllTransactionsWithPagination(2,url);
    var new_offset = offset+limit;
    document.forms["search"]["offset"].value = new_offset;
    document.forms["search"]["limit"].value = limit;

}

function GETAllUsers() {

    var xhr =  new XMLHttpRequest();

    var url = 'http://localhost:8080/users';

    xhr.open("GET", url);
    xhr.setRequestHeader("ApiKeyAuth", sessionStorage.getItem("AuthToken"));
    xhr.onload= (e) => {
        alert(xhr.status);
    }
    xhr.send();

    xhr.onreadystatechange=(e)=>{
        var obj = JSON.parse(xhr.responseText);
        CreateTableFromJSONUserPage(obj)
        document.getElementById("statusdisplay").innerHTML = "";
        document.getElementById("search").reset();
    }
}

function GETUserById() {

    var xhr =  new XMLHttpRequest();

    var url = 'http://localhost:8080/users/'+document.forms["search"]["user_id"].value;

    xhr.open("GET", url);
    xhr.setRequestHeader("ApiKeyAuth", sessionStorage.getItem("AuthToken"));

    xhr.onload= (e) => {
        alert(xhr.status);
    }

    xhr.send();

    xhr.onreadystatechange=(e)=>{
        var obj = JSON.parse("[" + xhr.responseText + "]");
        CreateTableFromJSONUserPage(obj);
        document.getElementById("search").reset();
    }
}

function GETUserByEmail() {

    var xhr =  new XMLHttpRequest();

    var url = 'http://localhost:8080/users?email='+document.forms["search"]["email"].value;

    xhr.open("GET", url);
    xhr.setRequestHeader("ApiKeyAuth", sessionStorage.getItem("AuthToken"));

    xhr.onload= (e) => {
        alert(xhr.status);
    }

    xhr.send();

    xhr.onreadystatechange=(e)=>{
        var obj = JSON.parse(xhr.responseText);
        CreateTableFromJSONUserPage(obj);
        document.getElementById("search").reset();
    }
}

function GETUserByName() {

    var xhr =  new XMLHttpRequest();

    var url = 'http://localhost:8080/users?name='+document.forms["search"]["lastName"].value;

    xhr.open("GET", url);
    xhr.setRequestHeader("ApiKeyAuth", sessionStorage.getItem("AuthToken"));

    xhr.onload= (e) => {
        alert(xhr.status);
    }

    xhr.send();

    xhr.onreadystatechange=(e)=>{
        var obj = JSON.parse(xhr.responseText);
        CreateTableFromJSONUserPage(obj);
        document.getElementById("search").reset();
    }
}

function DELETEUserById() {

    var xhr =  new XMLHttpRequest();

    var url = 'http://localhost:8080/users/'+document.forms["search"]["user_id"].value;

    xhr.open("DELETE", url);
    xhr.setRequestHeader("ApiKeyAuth", sessionStorage.getItem("AuthToken"));

    xhr.onload= (e) => {
        alert(xhr.status);
    }

    xhr.send();

    xhr.onreadystatechange=(e)=>{
        document.getElementById("statusdisplay").innerHTML = "User " + document.forms["search"]["user_id"].value + " succesfully deleted!";
        document.getElementById("datadisplay").innerHTML = "";
        document.getElementById("search").reset();
    }
}

function POSTUser() {

   var xhr =  new XMLHttpRequest();
    xhr.open('POST','http://localhost:8080/users');
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("ApiKeyAuth", sessionStorage.getItem("AuthToken"));
    xhr.onload= (e) => {
        alert(xhr.status);
        location.reload();
    }
    xhr.send(JSON.stringify(
        {
            "firstName": document.forms["signupform"]["firstName"].value ,
            "lastName": document.forms["signupform"]["lastName"].value ,
            "email": document.forms["signupform"]["email"].value ,
            "password": document.forms["signupform"]["password"].value,
            "user_type": document.forms["signupform"]["user_type"].value
        }
    ));

}

function PUTUser() {

   var userid = document.getElementById("uid").value;

   var xhr =  new XMLHttpRequest();
    xhr.open('PUT','http://localhost:8080/users/'+userid);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("ApiKeyAuth", sessionStorage.getItem("AuthToken"));
    xhr.onload= (e) => {
        alert(xhr.status);
        document.getElementById("updateform").reset();
    }
    xhr.send(JSON.stringify(
        {
            "firstName": document.forms["updateform"]["firstName"].value ,
            "lastName": document.forms["updateform"]["lastName"].value ,
            "email": document.forms["updateform"]["email"].value ,
            "password": document.forms["updateform"]["password"].value
        }
    ));

}

function DELETELogout()
{
  var xhr =  new XMLHttpRequest();
    xhr.open('DELETE','http://localhost:8080/logout');
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("ApiKeyAuth", sessionStorage.getItem("AuthToken"));
    xhr.onload= (e) => {
        alert(xhr.status);
        sessionStorage.setItem("AuthToken", "");
        sessionStorage.setItem("UserId", "");
        location.reload();
    }
    xhr.send();
}
function POSTRegister()
{
    var xhr =  new XMLHttpRequest();
    xhr.open('POST','http://localhost:8080/register');
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onload= (e) => {
        alert(xhr.status);

    }
    xhr.send(JSON.stringify(
        {
            "firstName": document.forms["registerForm"]["firstName"].value ,
            "lastName": document.forms["registerForm"]["lastName"].value,
            "email": document.forms["registerForm"]["email"].value,
            "password": document.forms["registerForm"]["passwordRegister"].value
        }
    ));
}

function POSTLogin(){
    var xhr =  new XMLHttpRequest();
    xhr.open('POST','http://localhost:8080/login');
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onload= (e) => {
        alert(xhr.status);
        let response = JSON.parse(xhr.response);
        sessionStorage.setItem("AuthToken", response.authToken);
        sessionStorage.setItem("UserId", response.userId);
        var userId = sessionStorage.getItem("UserId");
        location.reload();
    }
    xhr.send(JSON.stringify(
        {
            "username": document.forms["loginForm"]["username"].value ,
            "password": document.forms["loginForm"]["password"].value
        }
    ));

}
function GetUserRequests()
{
  let table = document.getElementById("userRequestsTable");
        table.innerHTML = "   <tr>\n" +
            "       <th>Request id</th>\n" +
            "       <th>Firstname</th>\n" +
            "       <th>Lastname</th>\n" +
            "       <th>Email</th>\n" +
            "       <th>Accept/Decline</th>\n" +
            "   </tr>";


        var xhr =  new XMLHttpRequest();
        xhr.open('GET','http://localhost:8080/users/requests');
        xhr.setRequestHeader("ApiKeyAuth" ,sessionStorage.getItem("AuthToken"));
        xhr.onload= (e) => {
            alert(xhr.status);
            let response = JSON.parse(xhr.response);
            for (i=0;i<response.length;i++){
                table.innerHTML+="<tr><td>%j</td><td>%k</td><td>%l</td><td>%m</td><td>%n</td></tr>"
                    .replace("%j",JSON.stringify(response[i].registerId))
                    .replace("%k",JSON.stringify(response[i].firstName))
                    .replace("%l",JSON.stringify(response[i].lastName))
                    .replace("%m",JSON.stringify(response[i].email))
                    .replace("%n","<button onclick='acceptRequest("+ response[i].registerId+ ", "+ JSON.stringify(response[i].firstName) +","+ JSON.stringify(response[i].lastName)+ "," +JSON.stringify(response[i].password) + ","+JSON.stringify(response[i].email)+ ")'>Accept</button><button onclick='declineRequest("+ response[i].registerId+ ")'>Decline</button>")

            }
        }
        xhr.send();
}
function acceptRequest(requestId, firstName, lastName, password, email)
{

        var xhr =  new XMLHttpRequest();
        xhr.open('POST','http://localhost:8080/users');
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.setRequestHeader("ApiKeyAuth", sessionStorage.getItem("AuthToken"));
        xhr.onload= (e) => {
            alert(xhr.status);
            declineRequest(requestId);
        }

        xhr.send(JSON.stringify(
            {
                "firstName": firstName,
                "lastName": lastName,
                "email":  email,
                "password": password,
                "user_type": "customer"
            }
        ));
}

function declineRequest(requestId)
{
    var xhr =  new XMLHttpRequest();
        xhr.open('DELETE','http://localhost:8080/users/requests/' + requestId);
        xhr.setRequestHeader("ApiKeyAuth" ,sessionStorage.getItem("AuthToken"));
        xhr.onload= (e) => {
            alert(xhr.status);
            location.reload();
        }
        xhr.send();;
}

function PostMachineTransfer()
{
    var amount = document.getElementById("amount").value;
    var t = document.getElementById("transferType");
    var transferType = t.options[t.selectedIndex].value;

    var xhr =  new XMLHttpRequest();

    xhr.open('POST','http://localhost:8080/users/'+sessionStorage.getItem("UserId")+ '/machine');
    xhr.setRequestHeader("Content-Type", "application/json" );
    xhr.setRequestHeader("ApiKeyAuth" ,sessionStorage.getItem("AuthToken"));
    xhr.onload= (e) => {
    //haal transactie op
        alert(xhr.status);
    }
    xhr.send(JSON.stringify(
        {
            "amount": amount,
            "transfer_type": transferType
        }
    ));
}

function getAllAccounts() {

    var xhr =  new XMLHttpRequest();

    var url = 'http://localhost:8080/accounts';

    xhr.open("GET", url);
    xhr.setRequestHeader("ApiKeyAuth", sessionStorage.getItem("AuthToken"));
    xhr.onload= (e) => {
        alert(xhr.status);
    }
    xhr.send();

    xhr.onreadystatechange=(e)=>{
        var obj = JSON.parse(xhr.responseText);
        CreateTableFromJSONUserPage(obj)
        document.getElementById("statusdisplay").innerHTML = "";
        document.getElementById("search").reset();
    }
}

function getAccountByUserId() {

    var xhr =  new XMLHttpRequest();

    var url = 'http://localhost:8080/users/'+document.forms["search"]["owner"].value+'/accounts';

    xhr.open("GET", url);
    xhr.setRequestHeader("ApiKeyAuth", sessionStorage.getItem("AuthToken"));

    xhr.onload= (e) => {
        alert(xhr.status);
    }

    xhr.send();

    xhr.onreadystatechange=(e)=>{
        var obj = JSON.parse(xhr.responseText);
        CreateTableFromJSONUserPage(obj);
        document.getElementById("search").reset();
    }
}

function getAccountByIban() {

    var xhr =  new XMLHttpRequest();

    var url = 'http://localhost:8080/accounts/'+document.forms["search"]["iban"].value;

    xhr.open("GET", url);
    xhr.setRequestHeader("ApiKeyAuth", sessionStorage.getItem("AuthToken"));

    xhr.onload= (e) => {
        alert(xhr.status);
    }

    xhr.send();

    xhr.onreadystatechange=(e)=>{
        var obj = JSON.parse("[" + xhr.responseText + "]");
        CreateTableFromJSONUserPage(obj);
        document.getElementById("search").reset();
    }
}

function deleteAccountByIban() {

    var xhr =  new XMLHttpRequest();

    var url = 'http://localhost:8080/accounts/'+document.forms["search"]["iban"].value;

    xhr.open("DELETE", url);
    xhr.setRequestHeader("ApiKeyAuth", sessionStorage.getItem("AuthToken"));

    xhr.onload= (e) => {
        alert(xhr.status);
        location.reload();
    }

    xhr.send();

    xhr.onreadystatechange=(e)=>{
        document.getElementById("statusdisplay").innerHTML = "User " + document.forms["search"]["user_id"].value + " succesfully deleted!";
        document.getElementById("datadisplay").innerHTML = "";
        document.getElementById("search").reset();
    }
}

function updateAccount() {

   var xhr =  new XMLHttpRequest();
    xhr.open('PUT','http://localhost:8080/accounts/'+document.forms["updateform"]["iban"].value);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("ApiKeyAuth", sessionStorage.getItem("AuthToken"));
    xhr.onload= (e) => {
        alert(xhr.status);
        document.getElementById("updateform").reset();
    }
    xhr.send(JSON.stringify(
        {
            "iban": document.forms["updateform"]["iban"].value ,
            "balanceLimit": document.forms["updateform"]["balanceLimit"].value ,
            "transactionAmountLimit": document.forms["updateform"]["transactionAmountLimit"].value ,
            "transactionDayLimit": document.forms["updateform"]["transactionDayLimit"].value
        }
    ));
}

function postAccount(){
    var xhr =  new XMLHttpRequest();
    var t = document.getElementById("account_type");

    var userId = sessionStorage.getItem("UserId");
    xhr.open('POST','http://localhost:8080/users/'+userId+'/accounts');
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("ApiKeyAuth", sessionStorage.getItem("AuthToken"));
    xhr.onload= (e) => {
        alert(xhr.status);
        location.reload();
    }
    xhr.send(JSON.stringify(
        {
            "iban": "nl00inho0000000000" ,
            "account_type": t.options[t.selectedIndex].value,
            "balance": document.forms["createAccountForm"]["balance"].value ,
            "transactionDayLimit": document.forms["createAccountForm"]["transactionDayLimit"].value ,
            "transactionAmountLimit": document.forms["createAccountForm"]["transactionAmountLimit"].value ,
            "balanceLimit": document.forms["createAccountForm"]["balanceLimit"].value
        }
    ));
}

function createNewTransaction() {

    var xhr =  new XMLHttpRequest();
    xhr.open('POST','http://localhost:8080/transactions');
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("ApiKeyAuth", sessionStorage.getItem("AuthToken"));
    xhr.onload= (e) => {
        alert(xhr.status);
        location.reload();
    }
    xhr.send(JSON.stringify(
        {
            "account_from": document.forms["createNewTransaction"]["account_from"].value,
            "account_to": document.forms["createNewTransaction"]["account_to"].value,
            "amount": document.forms["createNewTransaction"]["amount"].value
        }
    ));

}

window.onload = function() {
   var userId = sessionStorage.getItem("UserId");
   var loginbtn = document.getElementById("login");
   var logoutbtn = document.getElementById("logout");

   if (userId === null || userId === "") {
       document.getElementById("currentuser").innerHTML = "Not logged in";
       logoutbtn.style.display='none';
   }
   else {
       document.getElementById("currentuser").innerHTML = "Hello user '" + userId + "'";
       loginbtn.style.display='none';
   }
}