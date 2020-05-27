
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
