
//MAIL 
function sendMail()
{
	var Nome = $('#nameContact').val();
	var Mail = $('#mailContact').val();
	var Subject = $('#subjectContact').val();
	var Message = $('#messageContact').val();
	
	$('#return-msg').addClass('show-return-msg');
	$('.return-msg').on('click', function(e) {
		$(this).removeClass('show-return-msg');
	});
	if(Nome != "" && Mail != "" && Subject != "" && Message != "")
	{
		$.get("/Restaurant/servlet/SendMail?Name=" + Nome + "&Mail=" + Mail +"&Subject=" + Subject + "&Message="+Message, function(data) {
			$('#return-msg').text("Email inviata correttamente!");
			insertLog("Mail_Inviata");
	    });
	}
	else if(Nome == "")
	{
		$('#return-msg').text("Inserisci il nome");
	}
	else if(Mail == "")
	{
		$('#return-msg').text("Inserisci la mail");
	}
	else if(Subject == "")
	{
		$('#return-msg').text("Inserisci l'oggetto");
	}
	else if(Message == "")
	{
		$('#return-msg').text("Inserisci il messaggio");
	}

}


function loadInfoLocal(){
    $.get("/Restaurant/servlet/LocalInfo", function(data) {
        var obj = JSON.parse(data);
        $('#logo').attr("src", obj.LogoURL);
        $('#NomeLocale').text(obj.Name);
        $('#logoFooter').attr("src", obj.LogoURL);
        $('#NomeLocaleFooter').text(obj.Name);
        $("#Address").text(obj.Address);
        $("#Mail").append(obj.Mail);
        $("#Telephone").append(obj.Telephone);
        $("#Telephone1").append(obj.Telephone);
    });
}
function showPreviewLogo(){
    $.get("/Restaurant/servlet/LocalInfo", function(data) {
        var obj = JSON.parse(data);
        $('#logoAnteprima').attr("src", "../../" + obj.LogoURL);
        
    });
}
function localIsActive()
{
	var boolres = "false";
	$.ajax({
        url: "/Restaurant/servlet/LocalBySession",
        type: 'get',
        async: false,
        success: function(data) {
        	if(data != "BLANK_SESSION")
        	{
	        	var obj = JSON.parse(data);
	        	var res = Boolean(obj.Active);
	        	if(res == true)
	        		boolres = "true";
	        	
	        }
        } 
     });
	return boolres;
}
var b = true;

sliderTestImage = "/Restaurant/assets/images/slider/1.jpg";
thumbnailTestImage = "/Restaurant/assets/images/slider2/1_small.jpg";
	
window.onload = function() 
{
	
	//loadInfoLocal();
	hideMenuForUser();
	if(window.location.pathname != "/Restaurant/ChooseLocal.html" && window.location.pathname != "/Restaurant/coming-soon/index.jsp" && window.location.pathname != "/Restaurant/Dashboard/default/Login.html" && window.location.pathname != "/Restaurant/Dashboard/landingpage/Affiliazione.html" && !isLogged() && window.location.pathname != "/Restaurant/servlet/CreateLocal")
	{
		
		var res = localIsActive();
		//var res = Boolean(localIsActive());
		if(res == "false")
			window.location.replace("/Restaurant/coming-soon/index.jsp");

		
		insertAnalytic(window.location.pathname);
		
	}
	if(window.location.pathname == "/Restaurant/menu_1.html")
	{
		isRestaurantChosen();
		loadInfoLocal();
		loadRatingLocal();
		hideReviewLocal();
		if(isLogged())
		{
			loadCartQuantity();
			showMenuForUser();
			//var switchStatus = false;

			
		}

		
		loadPizze(1);
		
	}
	else if(window.location.pathname == "/Restaurant/index.html")
	{
		isRestaurantChosen();
		loadInfoLocal();
		loadProductSlider();
		loadRatingLocal();
		hideReviewLocal();
		if(isLogged())
		{	
			loadCartQuantity();
			showMenuForUser();
			
			/*
			
			*/
			
		}
		else
		{
				hideMenuForUser();
		}
		
	}
	else if(window.location.pathname == "/Restaurant/MyAccount.html")
	{
		isRestaurantChosen();
		loadInfoLocal();
		loadRatingLocal();
		hideReviewLocal();
		if(isLogged())
		{	
			showMenuForUser();
			$("#Delimiter").hide();
			$("#updateButton").hide();
			if(!isAdmin())
				$('#UsersListPanel').hide();
			/*
			
			*/
			
		}
		else
		{
			window.location.replace("/Restaurant/index.html");

				
		}
		
	}
	else if(window.location.pathname == "/Restaurant/about.html")
	{
		loadInfoLocal();
		isRestaurantChosen();
		loadRatingLocal();
		hideReviewLocal();
		if(isLogged())
		{	
			loadCartQuantity();
			showMenuForUser();
			
			/*
			
			*/
			
		}
		else
		{
				hideMenuForUser();
		}
	}
	else if(window.location.pathname == "/Restaurant/contact.html")
	{
		loadInfoLocal();
		isRestaurantChosen();
		loadRatingLocal();
		hideReviewLocal();
		if(isLogged())
		{	
			loadCartQuantity();
			showMenuForUser();
			
			/*
			
			*/
			
		}
		else
		{
				hideMenuForUser();
		}
	}
	else if(window.location.pathname == "/Restaurant/Login.html")
	{
		loadInfoLocal();
		
		isRestaurantChosen();
		if(isLogged())
		{	
			loadCartQuantity();
			showMenuForUser();
			
			/*
			
			*/
			
		}
		else
		{
				hideMenuForUser();
		}
	}
	else if(window.location.pathname == "/Restaurant/Sign-In.html")
	{
		loadInfoLocal();
		isRestaurantChosen();
		if(isLogged())
		{	
			loadCartQuantity();
			showMenuForUser();
			
			/*
			
			*/
			
		}
		else
		{
				hideMenuForUser();
		}
	}
	else if(window.location.pathname == "/Restaurant/ConfermaUtente.html")
	{
		loadInfoLocal();
		var url_string = window.location.href;
		var url = new URL(url_string);
		var Mail = url.searchParams.get("Mail");
		var NTelefono = url.searchParams.get("NumeroTelefono");
		
		
		confermaUtente(Mail, NTelefono);
		
		if(isLogged())
		{	
			loadCartQuantity();
			showMenuForUser();
			
			/*
			
			*/
			
		}
		else
		{
				hideMenuForUser();
		}
	}
	else if(window.location.pathname == "/Restaurant/ChooseLocal.html")
	{
		//loadInfoLocal();
		loadLocals();
		$("body select").msDropDown();
	}
	else if(window.location.pathname == "/Restaurant/Dashboard/default/index.html")
	{
		loadInfoLocal();
		if(isLogged() && isAdmin())
		{	
			loadDashboardHomeInfo();
			showNewOrder();
			
		}
		else
		{
			window.location.replace("/Restaurant/index.html");

				
		}
	}
	else if(window.location.pathname == "/Restaurant/Dashboard/default/Login.html")
	{
		var par = getQueryVariable("id");
		if(par == null)
		{
			alert("Error");
			window.location.replace("/Restaurant/index.html");
		}
		
	}
	else if(window.location.pathname == "/Restaurant/Dashboard/default/Ordini.html")
	{

		loadInfoLocal();
		if(isLogged() && isAdmin())
		{	
			loadDashboardHomeInfo();
			showNewOrder();
			loadOrderTable("All", 1);
			//tableEditInitialize();
			//test();
		}
		else
		{
			window.location.replace("/Restaurant/index.html");

				
		}
	}
	else if(window.location.pathname == "/Restaurant/Dashboard/default/Utenti.html")
	{

		loadInfoLocal();
		if(isLogged() && isAdmin())
		{	
			loadDashboardHomeInfo();
			showNewOrder();
			loadUsersTable("All", 1);
			//tableEditInitialize();
			//test();
		}
		else
		{
			window.location.replace("/Restaurant/index.html");

				
		}
	}
	else if(window.location.pathname == "/Restaurant/Dashboard/default/Prodotti.html")
	{

		loadInfoLocal();
		if(isLogged() && isAdmin())
		{	
			loadDashboardHomeInfo();
			showNewOrder(); //Icon Menu
			showAllProducts(1);
			showIngredientOfProduct();
			loadIngredientsTable();
			loadTypeProducts();
			//tableEditInitialize();
			//test();
		}
		else
		{
			window.location.replace("/Restaurant/index.html");

				
		}
	}
	
	else if(window.location.pathname == "/Restaurant/Dashboard/default/Ingredienti.html")
	{

		loadInfoLocal();
		if(isLogged() && isAdmin())
		{	
			loadDashboardHomeInfo();
			showAllIngredients1(1);
			//tableEditInitialize();
			//test();
		}
		else
		{
			window.location.replace("/Restaurant/index.html");

				
		}
	}
	else if(window.location.pathname == "/Restaurant/Dashboard/default/Locale.html")
	{
		showLocalInfo();
		loadInfoLocal();
		showPreviewLogo();
		if(isLogged() && isAdmin())
		{	
			loadDashboardHomeInfo();
			showNewOrder();
			
			//tableEditInitialize();
			//test();
		}
		else
		{
			window.location.replace("/Restaurant/index.html");

				
		}
	}
};

function getQueryVariable(variable) {
	  var query = window.location.search.substring(1);
	  var vars = query.split("&");
	  for (var i=0;i<vars.length;i++) {
	    var pair = vars[i].split("=");
	    if (pair[0] == variable) {
	      return pair[1];
	    }
	  } 
	  return null;
	  //alert('Query Variable ' + variable + ' not found');
}

function openLocal(id)
{
	//var id = $("#locals :selected").val(); // The text content of the selected option
	$.ajax({
        url: "/Restaurant/servlet/AddLocalSession?id=" + id,
        type: 'get',
        async: false,
        success: function(data) {
        	var obj = JSON.parse(data);
        	if(obj.Stato != "Ok")
        	{
        		alert("Error: " + obj.Stato);
        	}
        	else
        	{
    			window.location.replace("/Restaurant/index.html");

        	}
        } 
     });
	
}

function loadLocals()
{
	
    $('#locals').text("");
    $('#locals').append("");
    
    $.ajax({
        url: "/Restaurant/servlet/AllLocals",
        type: 'get',
        async: false,
        success: function(data) {
        	var array = JSON.parse(data);
        	showLocals(array);
        	for (var i=0; i<array.length; i++)
            {
        		var obj = array[i];
        		
        		$('#locals').append("<option value='" + obj.id + "' title='" + obj.LogoURL + "'></option>")		
            }
        } 
     });
}

function confermaUtente(Mail, NumeroTelefono)
{
	$('#return-msg').addClass('show-return-msg');
	$('.return-msg').on('click', function(e) {
		$(this).removeClass('show-return-msg');
	});
	$.ajax({
        url: "/Restaurant/servlet/ConfermaUtente?Mail="+ Mail + "&NumeroTelefono=" + NumeroTelefono,
        type: 'get',
        async: false,
        success: function(data) {
        	var obj = JSON.parse(data);
			if(obj.Stato == "Confermato")
    		{
    			var obj = JSON.parse(data);
				$('#return-msg').text("Utente confermato correttamente! Effettua il login");
				insertLog("Utente_Confermato");

    		}
    		else
    		{		
    			$('#return-msg').text("Errore: utente non confermato");
    		}
        } 
     });
}

function accordionOrder(idOrdine)
{
	$(".collapse").collapse("hide");
	$("#collapseOrder" + idOrdine).collapse("show");
}
function accordionOrderProduct(idOrdine, idProduct)
{
	$(".collapse").collapse("hide");
	$("#collapseOrder" + idOrdine).collapse("show");
	$("#collapseProduct" + idOrdine + idOrdine).collapse("show");
}

function loadUser()
{
	$('#TextData').addClass('text-center');

	$("#UserPanel").addClass('active');
	$("#OrdersPanel").removeClass('active');
	$("#Delimiter").show();
	$("#Delimiter").text("User");
	
	$("#updateButton").show();
	
	var data = "<div class='row'>"+
                	"<div class='col-sm-6'>"+
            			"<input type='text' id='Nome' name='Nome' placeholder='Inserisci il tuo Nome'>"+
                	"</div>"+
                    "<div class='col-sm-6'>"+
            			"<input type='text' id='Cognome' name='Cognome' placeholder='Inserisci il tuo Cognome'>"+
                	"</div>"+
                "</div>"+
            	"<div class='row'>"+
                	"<div class='col-sm-6'>"+
                		"<input type='email' id='Mail' name='Mail' placeholder='Mail'>"+
                	"</div>"+
                    "<div class='col-sm-6'>"+
                    	"<div class='row'>"+
                        	"<div class='col-sm-6'>"+
                				"<input type='tel' id='Telefono' name='Telefono' placeholder='Numero di telefono'>"+
                        	"</div>"+
                        "</div>"+
                	"</div>"+
                "</div>"+
                "<h1 class='bottom-line'>Address</h1>"+
                "<div class='row'>"+
                	"<div class='col-sm-6'>"+
            			"<input type='text' id='Via' name='via' placeholder='Inserisci la via'>"+
                	"</div>"+
                	"<div class='col-sm-6'>"+
                    	"<div class='row'>"+
                        	"<div class='col-sm-6'>"+
            					"<input type='text' id='nCivico' name='nCivico' placeholder='Inserisci il numero civico'>"+
                        	"</div>"+
                        "</div>"+
                	"</div>"+
                "</div>"+
                "<div class='row'>"+
            	"<div class='col-sm-6'>"+
        			"<input type='text' id='Citta' name='citta' placeholder='Città'>"+
            	"</div>"+
            	"<div class='col-sm-6'>"+
                	"<div class='row'>"+
                    	"<div class='col-sm-6'>"+
        					"<input type='text' id='Cap' name='cap' placeholder='CAP'>"+
                    	"</div>"+
                    "</div>"+
            	"</div>"+
            "</div>"+
                "<div class='margin-30'></div>"+
            "</form>";
	$('#TextData').text("");
	$('#TextData').append(data);

	$.get("/Restaurant/servlet/isLogged", function(data) {

    	obj = JSON.parse(data);
    	
    	$('#Nome').val(obj.Nome);
    	$('#Cognome').val(obj.Cognome);
    	$('#Telefono').val(obj.NumeroTelefono);
    	$('#Mail').val(obj.Mail);
    	
    	var indirizzo = obj.Indirizzo;
    	var via = indirizzo.substring(0, indirizzo.indexOf(','));
    	indirizzo = indirizzo.substring(indirizzo.indexOf(',')+1, indirizzo.length);
    	var nCivico = indirizzo.substring(0, indirizzo.indexOf(','));
    	indirizzo = indirizzo.substring(indirizzo.indexOf(',')+1, indirizzo.length);
    	var citta = indirizzo.substring(0, indirizzo.indexOf(','));
    	indirizzo = indirizzo.substring(indirizzo.indexOf(',')+1, indirizzo.length);
    	var cap = indirizzo;

    	
    	$('#Via').val(via);
    	$('#nCivico').val(nCivico);
    	$('#Citta').val(citta);
    	$('#Cap').val(cap);
    	
	});
	
}

function showLocals(arrayLocals)
{
	
	$('#containerChoose').text("");
	
	
	var contenuto = "";
	
	var k=1;
	for (var i=0; i<arrayLocals.length; i++)
    {
		if(k==1)
		{
			contenuto = contenuto + "<div class='row'>";
			
			
			
			contenuto = contenuto + "<div class='col-sm-6 responsive-column'>";
			contenuto = contenuto + "<div class='row'>";
			
		}
		else if(k==3)
		{
			contenuto = contenuto + "</div>"; //close row
			contenuto = contenuto + "</div>"; //close col
			
			
			contenuto = contenuto + "<div class='col-sm-6 responsive-column'>";
			contenuto = contenuto + "<div class='row'>";
		
		}
		
		contenuto = contenuto + "<div class='col-md-6 responsive-column'>"+
				"<div class='icon-box'>"+
					"<div class='icon-box-inner' onclick='openLocal("+ arrayLocals[i].id +")'>"+
						"<div class='icon-box-icon'>"+
							"<img src='"+ arrayLocals[i].LogoURL +"' class='center' alt='LogoLocals'>"+
						"</div>"+
					"</div>"+
					"<h3 class='bottom-line'>" + arrayLocals[i].Name +"</h3>"+
				"</div>"+
			"</div>";
		
		
		if(k==4 || i==arrayLocals.length -1)
		{
			
			contenuto = contenuto + "</div>"; //close row
			contenuto = contenuto + "</div>"; //close col
			contenuto = contenuto + "</div>"; //close row
			
			
			k=1;
			
		}
		else
			k++;
    }
	
	
	$('#containerChoose').append(contenuto);

}

function updateUser()
{
	var Nome = $('#Nome').val();
	var Cognome = $('#Cognome').val();
	var NumeroTelefono = $('#Telefono').val();
	var Password = $('#Password').val();
	var ConfermaPassword = $('#ConfermaPassword').val();
	var Mail = $('#Mail').val();
	var Via = $('#Via').val();
	var NCivico = $('#nCivico').val();
	var Citta = $('#Citta').val();
	var Cap = $('#Cap').val();
	
	$('#return-msg').addClass('show-return-msg');
	$('.return-msg').on('click', function(e) {
		$(this).removeClass('show-return-msg');
	});
	if(Nome != "" && Cognome != "" && NumeroTelefono != "" && Password != "" && ConfermaPassword != "" && Mail != "" && Via != "" && NCivico != "" && Citta!="" && Cap!="")
	{
		var indirizzo = Via + ", " + NCivico + ", " + Citta + ", " + Cap;
		$.get("/Restaurant/servlet/UpdateLoggedUser?Nome=" + Nome + "&Cognome=" + Cognome +"&NumeroTelefono=" + NumeroTelefono + "&Password="+Password 
				+"&Mail=" + Mail + "&Indirizzo=" + indirizzo +"&Disabilitato=false", function(data) {
			$('#return-msg').text("Utente aggiornato correttamente!");
			insertLog("Utente_Aggiornato");
	    });
	}
	else if(Nome == "")
	{
		$('#return-msg').text("Inserisci il nome");
	}
	else if(Cognome == "")
	{
		$('#return-msg').text("Inserisci il cognome");
	}
	else if(NumeroTelefono == "")
	{
		$('#return-msg').text("Inserisci il numero di telefono");
	}
	else if(Password == "")
	{
		$('#return-msg').text("Inserisci la password");
	}
	else if(ConfermaPassword == "" || Password != ConfermaPassword)
	{
		if(ConfermaPassword == "")
		{
			$('#return-msg').text("Inserisci di nuovo la password");
		}
		else
		{
			$('#return-msg').text("Le password non coincidono");
		}
	}
	else if(Mail == "")
	{
		$('#return-msg').text("Inserisci la mail");
	}
	else if(Via == "")
	{
		$('#return-msg').text("Inserisci la via");
	}
	else if(NCivico == "")
	{
		$('#return-msg').text("Inserisci il numero civico");
	}
	else if(Citta == "")
	{
		$('#return-msg').text("Inserisci la citta");
	}
	else if(Cap == "")
	{
		$('#return-msg').text("Inserisci il CAP: es 87042");
	}
}

function isRestaurantChosen()
{
	var resultbool = false;
	$.ajax({
        url: "/Restaurant/servlet/isRestaurantChosen",
        type: 'get',
        async: false,
        success: function(data) {
            result = data;
            if(data != "NOT_FOUNDED")
    		{
    			resultbool = true;
    		}
    		else
    		{		
    			window.location.replace("/Restaurant/ChooseLocal.html");
    			resultbool = false;
    		}
        } 
     });
	return resultbool;
}

function loadOrders(Filter)
{
	
	
	var array;
	$('#TextData').addClass('text-center');
	$("#UserPanel").removeClass('active');
	$("#OrdersPanel").addClass('active');
	$("#Delimiter").show();
	$("#Delimiter").text("Orders");
	
	$("#updateButton").hide();
	$("#return-msg").removeClass('show-return-msg');
	
	$('#TextData').text("");
	
	
	
	$('#TextData').append("<div class='text-center'>"+
						    "Filter:"+
						    "<ul class='list-filter'>"+
						        "<li><a onclick='loadOrders('All');' id='OrdersPanelAll'>All</a></li>"+
						        "<li><a onclick='loadOrders('Richiesto');' id='OrdersPanelRichiesto'>Richiesti</a></li>"+
						        "<li><a onclick='loadOrders('In Preparazione');' id='OrdersPanelPreparazione'>In Preparazione</a></li>"+
						        "<li><a onclick='loadOrders('In Consegna');' id='OrdersPanelConsegna'>In Consegna</a></li>"+
						        "<li><a onclick='loadOrders('Pronto');' id='OrdersPanelPronto'>Pronti</a></li>"+
						        "<li><a onclick='loadOrders('Consegnato');' id='OrdersPanelConsegnato'>Consegnati</a></li>"+
						    "</ul>"+
						    "</div>");
	$("#OrdersPanelAll").on("click", function(){loadOrders('All'); });
	$("#OrdersPanelRichiesto").on("click", function(){loadOrders('Richiesto'); });
	$("#OrdersPanelPreparazione").on("click", function(){loadOrders('In Preparazione'); });
	$("#OrdersPanelConsegna").on("click", function(){loadOrders('In Consegna'); });
	$("#OrdersPanelPronto").on("click", function(){loadOrders('Pronto'); });
	$("#OrdersPanelConsegnato").on("click", function(){loadOrders('Consegnato'); });

	
	
	if(isAdmin())
	{
		$("#OrdersPanelAll").removeClass('active');
		$("#OrdersPanelRichiesto").removeClass('active');
		$("#OrdersPanelPreparazione").removeClass('active');
		$("#OrdersPanelConsegna").removeClass('active');
		$("#OrdersPanelPronto").removeClass('active');
		$("#OrdersPanelConsegnato").removeClass('active');
		
		if(Filter == 'All')
		{
			$("#OrdersPanelAll").addClass('active');

			$.get("/Restaurant/servlet/AllOrders", function(data) {
				
		    	array = JSON.parse(data);
		    	
		    	showOrders(array);
		    	   	
		        
		    });
		}
		else
		{
			if(Filter == "In Preparazione")
				$("#OrdersPanelPreparazione").addClass('active');
			else if(Filter == "In Consegna")
				$("#OrdersPanelConsegna").addClass('active');
			else 
				$("#OrdersPanel"+Filter).addClass('active');

			$.get("/Restaurant/servlet/AllOrders?Stato="+Filter, function(data) {
						
				 array = JSON.parse(data);
				    	
				 showOrders(array);
				    	   	
				        
			});
		}
	}
	else
	{
		if(Filter == 'All')
		{
			$.get("/Restaurant/servlet/OrdersByUser", function(data) {
				
		    	array = JSON.parse(data);
		    	
		    	showOrders(array);
		        
		    });
		}
		else
		{
			$.get("/Restaurant/servlet/OrdersByUser?Stato="+Filter, function(data) {
						
				 array = JSON.parse(data);
				    	
				 showOrders(array);
				        
			});
		}
	}
     
}

function showOrders(array)
{
	for (var i=0; i<array.length; i++)
    {
    	var obj = array[i];

    	var idOrdine = obj.idOrdine;
    	var data = obj.DataOra;
    	var stato = obj.Stato;
    	var asporto = obj.Asporto;
    	var costo = obj.Costo;
    	var ProductsArray = obj.Products;
    	
    	var collapseDataProduct = "";
    	
    	collapseDataProduct = collapseDataProduct +"<div class='collapse' id='collapseOrder"+ idOrdine + "'>"+
	  			"<div class='card card-body'>";
    	
    	var collapseDataIngredient = "";
		
    	
    	for (var k=0; k<ProductsArray.length; k++)
        {
    		var product = ProductsArray[k];
    		var idProdotto = product.idProdotto;
    		var Nome = product.Name;
    		var Prezzo = product.Price;
    		var Tipo = product.Type;
    		var Quantita = product.Quantity;
    		var IngredientsArray = product.Ingredients;
    		
    		collapseDataProduct= collapseDataProduct +"<p class='active' onclick='accordionOrder(" + idOrdine + idProdotto +");' data-toggle='collapse' data-parent='#TextData' href='#collapseProduct" + idOrdine + idProdotto +"' role='button' aria-expanded='false' aria-controls='collapseProduct"+ idOrdine + idProdotto + "'> Nome: " + Nome + " - Tipo: " + Tipo + " - Costo: " + Prezzo + " - Quantità: " + Quantita + "</p>";
    		
    		collapseDataIngredient = collapseDataIngredient +"<div class='collapse' id='collapseProduct" + idOrdine + idProdotto + "'>"+
  			"<div class='card card-body'>";
    		
    		for (var j=0; j<IngredientsArray.length; j++)
            {
    			var ingredient = IngredientsArray[j];
    			var idIngrediente = ingredient.idIngredient;
    			var NomeI = ingredient.Name; 
    			var PrezzoI = ingredient.Price;
    			collapseDataIngredient= collapseDataIngredient +"<p class='active' data-parent='#TextData" + idOrdine + "'> Nome: " + NomeI + " - Costo: " + PrezzoI +"</p>";

            }
    		collapseDataIngredient = collapseDataIngredient +"</div></div>";
        }
    	collapseDataProduct = collapseDataProduct +"</div></div>";
    	
    	
    	if(isAdmin())
    	{
    		$('#TextData').append("<div class='row' id='" + idOrdine + "'><a onclick='accordionOrder(" + idOrdine +");' data-parent='#TextData' data-toggle='collapse' href='#collapseOrder" +idOrdine +"' role='button' aria-expanded='false' aria-controls='collapseOrder" + idOrdine+"'>ID: "+
        			idOrdine + " - Data: " + data + " - Stato: " + stato + " - Asporto: " + asporto + " - Costo: " +
        			costo + " </a><a href='#' onclick='modifyOrder(" + idOrdine +");' class='fa fa-edit'</a></div>");
    	}
    	else
    	{
    		$('#TextData').append("<div class='row' id='" + idOrdine + "'><a onclick='accordionOrder(" + idOrdine +");' data-parent='#TextData' data-toggle='collapse' href='#collapseOrder" +idOrdine +"' role='button' aria-expanded='false' aria-controls='collapseOrder" + idOrdine+"'>ID: "+
        			idOrdine + " - Data: " + data + " - Stato: " + stato + " - Asporto: " + asporto + " - Costo: " +
        			costo + " </a></div>");
    	}
    	
    	

    	$('#TextData').append(collapseDataProduct);
    	$('#TextData').append(collapseDataIngredient);        	
    }
}

function modifyOrder(idOrder)
{
	$('#TextData').removeClass('text-center');
	$("#Delimiter").text("Order");
	$("#updateButton").show();

	var dataShow = "<div class='table100 ver3 m-b-110'>"+
	"<div class='table100-head'>"+
		"<table>"+
			"<thead>"+
				"<tr class='row100 head'>"+
					"<th class='cell100 column1'>id</th>"+
					"<th class='cell100 column2'>Data</th>"+
					"<th class='cell100 column7'>Costo</th>"+
					"<th class='cell100 column5'>Indirizzo</th>"+
					"<th class='cell100 column3'>Stato</th>"+
					"<th class='cell100 column4'>Utente</th>"+
					"<th class='cell100 column6'>Asp.</th>"+
				"</tr>"+
			"</thead>"+
		"</table>"+
	"</div>"+
	"<div class='table100-body js-pscroll'>"+
		"<table>"+
			"<tbody>";
	var statoOrdine;
	
	$.ajax({
	        url: "/Restaurant/servlet/OrdersById?idOrdine=" + idOrder,
	        type: 'get',
	        async: false,
	        success: function(data){
	        	
    	obj = JSON.parse(data);
    	
    	var index = -1;
    	
    	var NumeroTelefono = obj.NumeroTelefono;
    	
        var idOrdine = obj.idOrdine;
        var data = obj.DataOra;
        statoOrdine = obj.Stato;
        var asporto = obj.Asporto;
        var costo = obj.Costo;
        var ProductsArray = obj.Products;
        
        var Utente;
        var Indirizzo;
        
        
        $.ajax({
	        url: "/Restaurant/servlet/UserById?NumeroTelefono=" + NumeroTelefono,
	        type: 'get',
	        async: false,
	        success: function(data){
	        	
	        	var objtmp = JSON.parse(data);
	        	Utente = objtmp.Nome + " " + objtmp.Cognome;
	        	Indirizzo = objtmp.Indirizzo;
    	
	        }
        });
        
        
        
        dataShow = dataShow + "<tr class='row100 body'>"+
			"<td class='cell100 column1'>" + idOrdine +"</td>"+
			"<td class='cell100 column2'>" + data + "</td>"+
			"<td class='cell100 column7'>" + costo + "</td>"+
			"<td class='cell100 column5'>" + Indirizzo +"</td>"+
			"<td class='cell100 column3'>"+
			"<select class='Table' name='OrderState' id='OrderState'>"+
							"<option value='Richiesto'>Richiesto</option>"+
						    "<option value='In Preparazione'>In Preparazione</option>"+
						    "<option value='In Consegna'>In Consegna</option>"+
						    "<option value='Pronto'>Pronto</option>"+
						    "<option value='Consegnato'>Consegnato</option>"+
  			"</select> </td>"+
        	"<td class='cell100 column4'>" + Utente + "</td>"+
			"<td class='cell100 column6'>";
			
        
        
			if(asporto)
        		dataShow = dataShow + "<label class='switch'> <input id='switchAsporto" + obj.idOrdine +"' type='checkbox' value='false' readonly checked disabled><span class='slider round'></span></label>";
        	else
        		dataShow = dataShow + "<label class='switch'> <input id='switchAsporto" + obj.idOrdine +"' type='checkbox' value='false' readonly disabled><span class='slider round'></span></label>";
			
        dataShow = dataShow + "</td></tr>"+
        						"</tbody>"+
							"</table>"+
						"</div>"+
					"</div>";
                
        dataShow = dataShow + "<h1 class='bottom-line'>Products</h1>";
        
        dataShow = dataShow + "<div class='table100 ver3 m-b-110'>"+
		    	"<div class='table100-head'>"+
		    		"<table>"+
		    			"<thead>"+
		    				"<tr class='row100 head'>"+
		    					"<th class='cell100 column1'>Nome</th>"+
		    					"<th class='cell100 column5'>Ingredienti</th>"+
		    					"<th class='cell100 column2'>Tipo</th>"+
		    					"<th class='cell100 column7'>Costo</th>"+
		    					"<th class='cell100 column6'>QTY</th>"+
		    				"</tr>"+
		    			"</thead>"+
		    		"</table>"+
		    	"</div>"+
		    	"<div class='table100-body js-pscroll'>"+
		    		"<table>"+
		    			"<tbody>";
        
        for (var k=0; k<ProductsArray.length; k++)
        {
        	var product = ProductsArray[k];
        	var idProdotto = product.idProdotto;
        	var Nome = product.Name;
        	var Prezzo = product.Price;
        	var Tipo = product.Type;
        	var Quantita = product.Quantity;
        	var IngredientsArray = product.Ingredients;
        	
        	
        	
        	var ingredientsFinal = "";
        	for (var j=0; j<IngredientsArray.length; j++)
            {
        		var ingredient = IngredientsArray[j];
        		var idIngrediente = ingredient.idIngredient;
        		var NomeI = ingredient.Name; 
        		var PrezzoI = ingredient.Price;
        		
        		if(j==IngredientsArray.length-1)
        			ingredientsFinal = ingredientsFinal + NomeI;
        		else
        			ingredientsFinal = ingredientsFinal + NomeI + ", ";
        		
            }
        	
        	dataShow = dataShow + "<tr class='row100 body'>"+
			"<td class='cell100 column1'>" + Nome +"</td>"+
			"<td class='cell100 column5'>" + ingredientsFinal + "</td>"+
			"<td class='cell100 column2'>" + Tipo +"</td>"+
			"<td class='cell100 column7'>" + Prezzo + "</td>"+
        	"<td class='cell100 column6'>" + Quantita + "</td>";

        }
        dataShow = dataShow + "</tr>"+
									"</tbody>"+
								"</table>"+
							"</div>"+
							"</div>";
      
        	

        
    }
	});
	
	$('#TextData').text("");
	$('#TextData').append(dataShow);
    $("#OrderState").val(statoOrdine);

}

function loadUsersList()
{
	$('#TextData').removeClass('text-center');

	$("#UserPanel").removeClass('active');
	$("#UsersListPanel").addClass('active');
	$("#OrdersPanel").removeClass('active');
	$("#Delimiter").show();
	$("#Delimiter").text("User");
	
	$("#updateButton").show();
	
	var dataShow = "<div class='table100 ver3 m-b-110'>"+
					"<div class='table100-head'>"+
						"<table>"+
							"<thead>"+
								"<tr class='row100 head'>"+
									"<th class='cell100 column1'>Telefono</th>"+
									"<th class='cell100 column2'>Nome</th>"+
									"<th class='cell100 column3'>Cognome</th>"+
									"<th class='cell100 column4'>Mail</th>"+
									"<th class='cell100 column5'>Indirizzo</th>"+
									"<th class='cell100 column6'>Admin</th>"+
									"<th class='cell100 column7'>Conf</th>"+
								"</tr>"+
							"</thead>"+
						"</table>"+
					"</div>"+
					"<div class='table100-body js-pscroll'>"+
						"<table>"+
							"<tbody>";
								


	 $.ajax({
	        url: "/Restaurant/servlet/AllUsers",
	        type: 'get',
	        async: false,
	        success: function(data) {
	            
	        	array = JSON.parse(data);
	        	for (var i=0; i<array.length; i++)
	            {
	        		var obj = array[i];
	        		
	        		dataShow = dataShow + "<tr class='row100 body'>"+
									"<td class='cell100 column1'>" + obj.NumeroTelefono +"</td>"+
									"<td class='cell100 column2'>" + obj.Nome + "</td>"+
									"<td class='cell100 column3'>" + obj.Cognome + "</td>"+
									"<td class='cell100 column4'>" + obj.Mail + "</td>"+
									"<td class='cell100 column5'>" + obj.Indirizzo + "</td>"+
									
									"<td class='cell100 column6'>";
	        		
	        		if(obj.Amministratore)
	        			dataShow = dataShow + "<label class='switch'> <input id='switchAdmin"+ obj.NumeroTelefono +"' type='checkbox' value='true' readonly checked><span class='slider round'></span></label>";
	        		else
	        			dataShow = dataShow + "<label class='switch'> <input id='switchAdmin"+ obj.NumeroTelefono +"' type='checkbox' value='true' readonly><span class='slider round'></span></label>";
									
	        		dataShow = dataShow + "</td> <td class='cell100 column7'>";

	        		if(obj.Confermato)
	        			dataShow = dataShow + "<label class='switch'> <input id='switchConfermato" + obj.NumeroTelefono +"' type='checkbox' value='false' readonly checked><span class='slider round'></span></label>";
	        		else
	        			dataShow = dataShow + "<label class='switch'> <input id='switchConfermato" + obj.NumeroTelefono +"' type='checkbox' value='false' readonly><span class='slider round'></span></label>";

	        		dataShow = dataShow + "</td> </tr>";
									
	        		
	        		
	            }
	        }
	 });
	
	 dataShow = dataShow + "</tbody>"+
						"</table>"+
					"</div>"+
				"</div>";
	
	$('#TextData').text("");
	$('#TextData').append(dataShow);
	
}

function updateButton()
{
	alert(29);
	
	if($("#UserPanel").hasClass('active'))
	{
		updateUser();
	}
	else if($("#OrdersPanel").hasClass('active'))
	{
		updateOrder();
	}
}

function updateUsers()
{
	alert("Da sviluppare");
}
function updateOrder()
{
	alert("Da Sviluppare");
}
function loadProductSlider()
{
	
	$.get("/Restaurant/servlet/AllProducts", function(data) {

		if(data != "error")
		{

	    	array = JSON.parse(data);
	    	
	    	var index = -1;
	    	for (var i=0; i<array.length && i<=5; i++)
	        {
	        	var obj = array[i];
	        	var name = obj.Name;
	        	var price = obj.Price;
	        	var imageURL = obj.ImageURL;
	        	
	        	if (!UrlExists(imageURL))
	        	{
	        		imageURL = sliderTestImage;
	        		//alert(imageURL);
	        	}
	        	var ingredients = obj.Ingredients;
	        	
	        	var p1;
	        	var p2;
	        	if(String(price).indexOf(".") != -1)
	        	{
	        		p1 = String(price).substring(0, String(price).indexOf(".")+1);
	        		p2 = String(price).substring(String(price).indexOf(".")+1);
	        	}
	        	else
	        	{
	        		p1 = String(price);
	        		p2 = "";
	        	}
	
	        	$("#TitleSlider"+(i+1)).text(name);
	        	$("#Price1Slider"+(i+1)).text("");
	        	$("#Price2Slider"+(i+1)).text("");
	
	        	$("#Price1Slider"+(i+1)).append("<span class='price-currency'>€</span><span class='price-val-1'>" + p1 +"</span>");
	        	$("#Price2Slider"+(i+1)).append("<span class='price-val-2'>" + p2 +"</span>");
	        	if(imageURL != "null" && imageURL != sliderTestImage)
	        	{
	        		var temp = String(imageURL).substring(0, String(imageURL).indexOf("MainImage"));
	        		//alert(temp);
	        		var tempSlider = temp + "Slider/" + name + ".png";
	        		//tempSlider = tempSlider + String(imageURL).substring(String(imageURL).indexOf("."));

	        		var tempThumbnail = temp + "Thumbnail/" + name + ".png";
	        		//tempThumbnail = tempThumbnail + String(imageURL).substring(String(imageURL).indexOf("."));
	
	        		$("#ImageSlider"+(i+1)).attr('src',  tempSlider );
	        		$("#ImageThumbnail"+(i+1)).attr('src', tempThumbnail );
	        	}
	        	else if (imageURL == sliderTestImage)
	        	{
	        		//alert("OK");
	        		$("#ImageSlider"+(i+1)).attr('src',  imageURL );
	        		$("#ImageThumbnail"+(i+1)).attr('src', thumbnailTestImage );

	        	}
	        	$("#IngredientSlider"+(i+1)).text("");
	        	
	        	
		        for(var k=0; k<ingredients.length; k++)
		        {
		        	if(ingredients[k].Name != "null")
		        	{
		        		$("#IngredientSlider"+(i+1)).append(ingredients[k].Name);
		        		if(k < ingredients.length-1)
		        			$("#IngredientSlider"+(i+1)).append(", ");
		        	}
		        }
		        
	        }
		}
		else
		{
			window.location.replace("/Restaurant/ChooseLocal.html");
		}
	});
	
}

function loadPizze(pageNumber){
    
	loadVisibilityProduct();
	var array;
	
	$("#Delimiter").text("Pizze");
	$("#PaniniFilter").removeClass('active');
	$("#PizzaFilter").addClass('active');
	$("#FrittiFilter").removeClass('active');
	$("#InsalateFilter").removeClass('active');
	$("#AllFilter").removeClass('active');
	$.get("/Restaurant/servlet/ProductsByType?Type=Pizza", function(data) {

		var array = JSON.parse(data);
    	
    	var nPage = array.length/6;
    	for (var i=((pageNumber*1)-1); i<array.length; i++)
        {
        	var obj = array[i];
        	//alert(c.Name);
        	var price = obj.Price;
        	var p1;
        	var p2;
        	if(String(price).indexOf(".") != -1)
        	{
        		p1 = String(price).substring(0, String(price).indexOf(".")+1);
        		p2 = String(price).substring(String(price).indexOf(".")+1);
        	}
        	else
        	{
        		p1 = String(price);
        		p2 = "";
        	}

        	$("#Title"+(i+1)).text(obj.Name);
        	$("#Price"+(i+1)).text("");
        	$("#Price"+(i+1)).append("<sub>€</sub>" + p1 + "<sup>" + p2 + "</sup>");
        	if(obj.ImageURL != "null" && !UrlExists(obj.ImageURL))
        		$("#ImageProduct"+(i+1)).attr('src', obj.ImageURL);
        	else
        		$("#ImageProduct"+(i+1)).attr('src', 'assets/images/products/1.png');
            $("#ProductPreview"+(i+1)).show();
        	$("#idProduct"+(i+1)).text(obj.id);

        	var reviewsArray = obj.Reviews;
    		var SVoti = 0;
    		for (var k=0; k<reviewsArray.length; k++)
            {
    			SVoti += reviewsArray[k].Voto;
            }
    		var mediaVoti = 0;
    		if(reviewsArray.length > 0)
    			mediaVoti = SVoti/reviewsArray.length;
    		//alert(mediaVoti);
    		
    		$('#rating'+(i+1)).text("");
        	$('#rating'+(i+1)).append("<select id='example-fontawesome-o"+(i+1)+"' name='rating' data-current-rating='" + mediaVoti + "' autocomplete='off'>"+
                    "<option value='' label='0'></option>"+
                    "<option value='1'>1</option>"+
                    "<option value='2'>2</option>"+
                    "<option value='3'>3</option>"+
                    "<option value='4'>4</option>"+
                    "<option value='5'>5</option>"+

                "</select>");
        	
        	$('.rating-enable').on('click',function(event) {
	            event.preventDefault();

	            ratingEnable();

	            $(this).addClass('deactivated');
	            $('.rating-disable').removeClass('deactivated');
	        });

	        $('.rating-disable').on('click',function(event) {
	            event.preventDefault();

	            ratingDisable();

	            $(this).addClass('deactivated');
	            $('.rating-enable').removeClass('deactivated');
	        });

	        ratingEnable(i+1);
        	
        	
        	loadIngredients(obj.id, (i+1));
        	
        }
    	
    	$('#pageNumber').text("");
    	if(pageNumber != 1)
    	{
        	$('#pageNumber').append("<li><a onclick='loadPizze(" + (pageNumber-1) +");'><i class='fa fa-angle-left'></i></a></li>");

    	}
    	for(var i=0; i< nPage; i++)
    	{
    		$('#pageNumber').append("<li><a onclick='loadPizze(" + (i+1) +");'>" + (i+1) + "</a></li>");
    	}
    	if( pageNumber < nPage)
    	{
        	$('#pageNumber').append("<li><a onclick='loadPizze(" + (pageNumber+1) +")'><i class='fa fa-angle-right'></i></a></li>");

    	}

    });
    
	
}

function SaveReview()
{

    var currentRating = $('#example-fontawesome-o0').data('your-rating');
    var Review = $("#Recensione").val();
	
	if(isLogged())
	{
		saveReviewLocal(currentRating, Review);
	}
	else
	{
		alert("Devi aver effettuato il login per dare un voto ad un prodotto");
		$('#example-fontawesome-o0').barrating('clear');
	    $('#example-fontawesome-o0').data("your-rating",0); //setter
	}

	

	
}

function loadRatingLocal()
{
    $('#example-fontawesome-o0').data("your-rating",0); //setter

	$('.rating-enable').on('click',function(event) {
        event.preventDefault();

        ratingEnable();

        $(this).addClass('deactivated');
        $('.rating-disable').removeClass('deactivated');
    });

    $('.rating-disable').on('click',function(event) {
        event.preventDefault();

        ratingDisable();

        $(this).addClass('deactivated');
        $('.rating-enable').removeClass('deactivated');
    });

    ratingEnable(0);
}

function loadAllProducts(pageNumber){
    
	
	
	loadVisibilityProduct();
	
	$("#Delimiter").text("All");
	$("#PaniniFilter").removeClass('active');
	$("#PizzaFilter").removeClass('active');
	$("#FrittiFilter").removeClass('active');
	$("#InsalateFilter").removeClass('active');
	$("#AllFilter").addClass('active');

	$.get("/Restaurant/servlet/AllProducts", function(data) {

    	var array = JSON.parse(data);
    	
    	var nPage = array.length/6;
    	var index = 0;
    	for (var i=0; i<array.length && i<6; i++)
        {
    		var index;
    		if(pageNumber == 1)
    		{
    			index = (i*pageNumber);
    		}else
    		{
    			index = ((i*pageNumber)-(6+i))+(pageNumber*6);
    		}
    		
        	var obj = array[index];
        	//alert(c.Name);
        	var price = obj.Price;
        	var p1;
        	var p2;
        	if(String(price).indexOf(".") != -1)
        	{
        		p1 = String(price).substring(0, String(price).indexOf(".")+1);
        		p2 = String(price).substring(String(price).indexOf(".")+1);
        	}
        	else
        	{
        		p1 = String(price);
        		p2 = "";
        	}

        	$("#Title"+(i+1)).text(obj.Name);
        	$("#Price"+(i+1)).text("");
        	$("#Price"+(i+1)).append("<sub>€</sub>" + p1 + "<sup>" + p2 + "</sup>");
        	if(obj.ImageURL != "null" && !UrlExists(obj.ImageURL))
        		$("#ImageProduct"+(i+1)).attr('src', obj.ImageURL);
        	else
        		$("#ImageProduct"+(i+1)).attr('src', 'assets/images/products/1.png');
            $("#ProductPreview"+(i+1)).show();
        	$("#idProduct"+(i+1)).text(obj.id);
        	
        	var reviewsArray = obj.Reviews;
    		var SVoti = 0;
    		for (var k=0; k<reviewsArray.length; k++)
            {
    			SVoti += reviewsArray[k].Voto;
            }
    		var mediaVoti = 0;
    		if(reviewsArray.length > 0)
    			mediaVoti = SVoti/reviewsArray.length;
    		
    		$('#rating'+(i+1)).text("");
        	$('#rating'+(i+1)).append("<select id='example-fontawesome-o"+(i+1)+"' name='rating' data-current-rating='" + mediaVoti + "' autocomplete='off'>"+
                    "<option value='' label='0'></option>"+
                    "<option value='1'>1</option>"+
                    "<option value='2'>2</option>"+
                    "<option value='3'>3</option>"+
                    "<option value='4'>4</option>"+
                    "<option value='5'>5</option>"+

                "</select>");
        	
        	$('.rating-enable').on('click',function(event) {
	            event.preventDefault();

	            ratingEnable();

	            $(this).addClass('deactivated');
	            $('.rating-disable').removeClass('deactivated');
	        });

	        $('.rating-disable').on('click',function(event) {
	            event.preventDefault();

	            ratingDisable();

	            $(this).addClass('deactivated');
	            $('.rating-enable').removeClass('deactivated');
	        });

	        ratingEnable(i+1);
    		
        	loadIngredients(obj.id, (i+1));
        	
        }
    	$('#pageNumber').text("");
    	if(pageNumber != 1)
    	{
        	$('#pageNumber').append("<li><a onclick='loadAllProducts(" + (pageNumber-1) +");'><i class='fa fa-angle-left'></i></a></li>");

    	}
    	for(var i=0; i< nPage; i++)
    	{
    		$('#pageNumber').append("<li><a onclick='loadAllProducts(" + (i+1) +");'>" + (i+1) + "</a></li>");
    	}
    	if( pageNumber < nPage)
    	{
        	$('#pageNumber').append("<li><a onclick='loadAllProducts(" + (pageNumber+1) +")'><i class='fa fa-angle-right'></i></a></li>");

    	}
    });
    
}
function loadPanini(pageNumber){
    
	
	
	loadVisibilityProduct();
	var array;
	
	$("#Delimiter").text("Panini");
	$("#PaniniFilter").addClass('active');
	$("#PizzaFilter").removeClass('active');
	$("#FrittiFilter").removeClass('active');
	$("#InsalateFilter").removeClass('active');
	$("#AllFilter").removeClass('active');

	$.get("/Restaurant/servlet/ProductsByType?Type=Panino", function(data) {

    	array = JSON.parse(data);
    	
    	var nPage = array.length/6;
    	for (var i=0; i<array.length; i++)
        {
        	var obj = array[i];
        	//alert(c.Name);
        	var price = obj.Price;
        	var p1;
        	var p2;
        	if(String(price).indexOf(".") != -1)
        	{
        		p1 = String(price).substring(0, String(price).indexOf(".")+1);
        		p2 = String(price).substring(String(price).indexOf(".")+1);
        	}
        	else
        	{
        		p1 = String(price);
        		p2 = "";
        	}

        	$("#Title"+(i+1)).text(obj.Name);
        	$("#Price"+(i+1)).text("");
        	$("#Price"+(i+1)).append("<sub>€</sub>" + p1 + "<sup>" + p2 + "</sup>");
        	if(obj.ImageURL != "null" && !UrlExists(obj.ImageURL))
        		$("#ImageProduct"+(i+1)).attr('src', obj.ImageURL);
        	else
        		$("#ImageProduct"+(i+1)).attr('src', 'assets/images/products/1.png');
            $("#ProductPreview"+(i+1)).show();
        	$("#idProduct"+(i+1)).text(obj.id);

        	var reviewsArray = obj.Reviews;
    		var SVoti = 0;
    		for (var k=0; k<reviewsArray.length; k++)
            {
    			SVoti += reviewsArray[k].Voto;
            }
    		var mediaVoti = 0;
    		if(reviewsArray.length > 0)
    			mediaVoti = SVoti/reviewsArray.length;
    		
    		$('#rating'+(i+1)).text("");
        	$('#rating'+(i+1)).append("<select id='example-fontawesome-o"+(i+1)+"' name='rating' data-current-rating='" + mediaVoti + "' autocomplete='off'>"+
                    "<option value='' label='0'></option>"+
                    "<option value='1'>1</option>"+
                    "<option value='2'>2</option>"+
                    "<option value='3'>3</option>"+
                    "<option value='4'>4</option>"+
                    "<option value='5'>5</option>"+

                "</select>");
        	
        	$('.rating-enable').on('click',function(event) {
	            event.preventDefault();

	            ratingEnable();

	            $(this).addClass('deactivated');
	            $('.rating-disable').removeClass('deactivated');
	        });

	        $('.rating-disable').on('click',function(event) {
	            event.preventDefault();

	            ratingDisable();

	            $(this).addClass('deactivated');
	            $('.rating-enable').removeClass('deactivated');
	        });

	        ratingEnable(i+1);
        	
        	loadIngredients(obj.id, (i+1));
        	
        }
    	$('#pageNumber').text("");
    	if(pageNumber != 1)
    	{
        	$('#pageNumber').append("<li><a onclick='loadPanini(" + (pageNumber-1) +");'><i class='fa fa-angle-left'></i></a></li>");

    	}
    	for(var i=0; i< nPage; i++)
    	{
    		$('#pageNumber').append("<li><a onclick='loadPanini(" + (i+1) +");'>" + (i+1) + "</a></li>");
    	}
    	if( pageNumber < nPage)
    	{
        	$('#pageNumber').append("<li><a onclick='loadPanini(" + (pageNumber+1) +")'><i class='fa fa-angle-right'></i></a></li>");

    	}
    });
    
}
function loadFritti(pageNumber){
    
	loadVisibilityProduct();

	$("#Delimiter").text("Fritti");
	$("#PaniniFilter").removeClass('active');
	$("#PizzaFilter").removeClass('active');
	$("#FrittiFilter").addClass('active');
	$("#InsalateFilter").removeClass('active');
	$("#AllFilter").removeClass('active');

	$.get("/Restaurant/servlet/ProductsByType?Type=Fritti", function(data) {

		var array = JSON.parse(data);
    	
    	var nPage = array.length/6;
    	for (var i=0; i<array.length; i++)
        {
        	var obj = array[i];
        	//alert(c.Name);
        	var price = obj.Price;
        	var p1;
        	var p2;
        	if(String(price).indexOf(".") != -1)
        	{
        		p1 = String(price).substring(0, String(price).indexOf(".")+1);
        		p2 = String(price).substring(String(price).indexOf(".")+1);
        	}
        	else
        	{
        		p1 = String(price);
        		p2 = "";
        	}

        	$("#Title"+(i+1)).text(obj.Name);
        	$("#Price"+(i+1)).text("");
        	$("#Price"+(i+1)).append("<sub>€</sub>" + p1 + "<sup>" + p2 + "</sup>");
        	if(obj.ImageURL != "null" && !UrlExists(obj.ImageURL))
        		$("#ImageProduct"+(i+1)).attr('src', obj.ImageURL);
        	else
        		$("#ImageProduct"+(i+1)).attr('src', 'assets/images/products/1.png');
        	$("#idProduct"+(i+1)).text(obj.id);

        	$("#ProductPreview"+(i+1)).show();
        	
        	var reviewsArray = obj.Reviews;
    		var SVoti = 0;
    		for (var k=0; k<reviewsArray.length; k++)
            {
    			SVoti += reviewsArray[k].Voto;
            }
    		var mediaVoti = 0;
    		if(reviewsArray.length > 0)
    			mediaVoti = SVoti/reviewsArray.length;
    		
    		$('#rating'+(i+1)).text("");
        	$('#rating'+(i+1)).append("<select id='example-fontawesome-o"+(i+1)+"' name='rating' data-current-rating='" + mediaVoti + "' autocomplete='off'>"+
                    "<option value='' label='0'></option>"+
                    "<option value='1'>1</option>"+
                    "<option value='2'>2</option>"+
                    "<option value='3'>3</option>"+
                    "<option value='4'>4</option>"+
                    "<option value='5'>5</option>"+

                "</select>");
        	
        	$('.rating-enable').on('click',function(event) {
	            event.preventDefault();

	            ratingEnable();

	            $(this).addClass('deactivated');
	            $('.rating-disable').removeClass('deactivated');
	        });

	        $('.rating-disable').on('click',function(event) {
	            event.preventDefault();

	            ratingDisable();

	            $(this).addClass('deactivated');
	            $('.rating-enable').removeClass('deactivated');
	        });

	        ratingEnable(i+1);
        	
        	loadIngredients(obj.id, (i+1));
        	
        }
    	$('#pageNumber').text("");
    	if(pageNumber != 1)
    	{
        	$('#pageNumber').append("<li><a onclick='loadFritti(" + (pageNumber-1) +");'><i class='fa fa-angle-left'></i></a></li>");

    	}
    	for(var i=0; i< nPage; i++)
    	{
    		$('#pageNumber').append("<li><a onclick='loadFritti(" + (i+1) +");'>" + (i+1) + "</a></li>");
    	}
    	if( pageNumber < nPage)
    	{
        	$('#pageNumber').append("<li><a onclick='loadFritti(" + (pageNumber+1) +")'><i class='fa fa-angle-right'></i></a></li>");

    	}
    });
    
}
function loadInsalate(pageNumber){
    
	loadVisibilityProduct();
	$("#Delimiter").text("Insalate");
	$("#PaniniFilter").removeClass('active');
	$("#PizzaFilter").removeClass('active');
	$("#FrittiFilter").removeClass('active');
	$("#InsalateFilter").addClass('active');
	$("#AllFilter").removeClass('active');

	$.get("/Restaurant/servlet/ProductsByType?Type=Insalata", function(data) {

		var array = JSON.parse(data);
    	
		var nPage = array.length/6;
		for (var i=0; i<array.length; i++)
        {
        	var obj = array[i];
        	//alert(c.Name);
        	var price = obj.Price;
        	var p1;
        	var p2;
        	if(String(price).indexOf(".") != -1)
        	{
        		p1 = String(price).substring(0, String(price).indexOf(".")+1);
        		p2 = String(price).substring(String(price).indexOf(".")+1);
        	}
        	else
        	{
        		p1 = String(price);
        		p2 = "";
        	}

        	$("#Title"+(i+1)).text(obj.Name);
        	$("#Price"+(i+1)).text("");
        	$("#Price"+(i+1)).append("<sub>€</sub>" + p1 + "<sup>" + p2 + "</sup>");
        	if(obj.ImageURL != "null" && !UrlExists(obj.ImageURL))
        		$("#ImageProduct"+(i+1)).attr('src', obj.ImageURL);
        	else
        		$("#ImageProduct"+(i+1)).attr('src', 'assets/images/products/1.png');
        	$("#idProduct"+(i+1)).text(obj.id);

        	$("#ProductPreview"+(i+1)).show();
        	
        	var reviewsArray = obj.Reviews;
    		var SVoti = 0;
    		for (var k=0; k<reviewsArray.length; k++)
            {
    			SVoti += reviewsArray[k].Voto;
            }
    		var mediaVoti = 0;
    		if(reviewsArray.length > 0)
    			mediaVoti = SVoti/reviewsArray.length;
    		
    		$('#rating'+(i+1)).text("");
        	$('#rating'+(i+1)).append("<select id='example-fontawesome-o"+(i+1)+"' name='rating' data-current-rating='" + mediaVoti + "' autocomplete='off'>"+
                    "<option value='' label='0'></option>"+
                    "<option value='1'>1</option>"+
                    "<option value='2'>2</option>"+
                    "<option value='3'>3</option>"+
                    "<option value='4'>4</option>"+
                    "<option value='5'>5</option>"+

                "</select>");
        	
        	$('.rating-enable').on('click',function(event) {
	            event.preventDefault();

	            ratingEnable();

	            $(this).addClass('deactivated');
	            $('.rating-disable').removeClass('deactivated');
	        });

	        $('.rating-disable').on('click',function(event) {
	            event.preventDefault();

	            ratingDisable();

	            $(this).addClass('deactivated');
	            $('.rating-enable').removeClass('deactivated');
	        });

	        ratingEnable(i+1);
        	
        	loadIngredients(obj.id, (i+1));
        	
        }
    	$('#pageNumber').text("");
    	if(pageNumber != 1)
    	{
        	$('#pageNumber').append("<li><a onclick='loadInsalate(" + (pageNumber-1) +");'><i class='fa fa-angle-left'></i></a></li>");

    	}
    	for(var i=0; i< nPage; i++)
    	{
    		$('#pageNumber').append("<li><a onclick='loadInsalate(" + (i+1) +");'>" + (i+1) + "</a></li>");
    	}
    	if( pageNumber < nPage)
    	{
        	$('#pageNumber').append("<li><a onclick='loadInsalate(" + (pageNumber+1) +")'><i class='fa fa-angle-right'></i></a></li>");

    	}
    });
    
}
function loadIngredients(idProd, pos)
{
	$.get("/Restaurant/servlet/IngredientsOfProduct?id=" + idProd, function(data) {
		
    	var array = JSON.parse(data);
    	for (var k=0; k<array.length; k++)
    	{
    		var obj = array[k];
    		//alert(index);
    		if(obj.Name!="null")
    		{
    			if(k==0)
    				$("#Ingredients"+pos).text(obj.Name);
    			else
    				$("#Ingredients"+pos).append(", " + obj.Name);
    		}
    	}
	});
}

function loadVisibilityProduct()
{
	for(var k=1; k<=12; k++)
	{
		$("#ProductPreview"+k).hide();
		$("#idProduct"+k).hide();
	}
}
function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
      console.log('User signed out.');
    });
}
function onSignIn(googleUser) {
	  var id_token = googleUser.getAuthResponse().id_token;

	  $.ajax({
	        url: "/Restaurant/servlet/tokenSignin?Token="+id_token,
	        type: 'get',
	        async: false,
	        success: function(data) {
	            result = data;
	            var obj = JSON.parse(data);
	            //alert(obj.Stato);
				if(obj.Stato == "Logged")
				{
					loadCartQuantity();
					showMenuForUser();
					return true;
				}
				else
				{
					signOut();
					alert(obj.Stato);
					return false;
				}
	        } 
	     });
	  isLogged();

}

  function onFailure(error) {
    console.log(error);
  }
  function renderButton() {
    gapi.signin2.render('my-signin2', {
      'scope': 'profile email',
      'width': 90,
      'height': 25,
      'longtitle': false,
      'theme': 'dark',
      'onsuccess': onSignIn,
      'onfailure': onFailure
    });
  }
  
function loginDashboard()
{
	var par = getQueryVariable("id");
	if(par == null)
	{
		alert("Error");
		window.location.replace("/Restaurant/index.html");
	}	

	
	var Mail = $("#mail").val();
	var Pass = $("#password").val();
	alert(Mail);
	alert(Pass);

	$.get("/Restaurant/servlet/Login?Mail=" + Mail + "&Password=" + Pass + "&idLocal=" + par, function(data) {
		var obj = JSON.parse(data);
		if(obj.Stato == "Logged")
		{
			window.location.href = "/Restaurant/Dashboard/default/index.html";
			insertLog("Login");
		}
		else
		{
			alert(obj.Stato);
		}
	
	});
	
}
  
function Login()
{
	var Mail = $('#Mail').val();
	var Password = $('#Password').val();
	//alert(Mail);
	if(Mail != "" && Password != "")
	{
		$.get("/Restaurant/servlet/Login?Mail=" + Mail + "&Password=" + Password, function(data) {
			var obj = JSON.parse(data);
			if(obj.Stato == "Logged")
			{
				window.location.href = "index.html";
				insertLog("Login");
			}
			else
			{
				alert(obj.Stato);
			}
		
		});
	}
	else
		alert("Inserisci Mail e Password");

}
function isLogged()
{
	var resultbool = false;
	$.ajax({
        url: "/Restaurant/servlet/isLogged",
        type: 'get',
        async: false,
        success: function(data) {
            result = data;
            if(data != "BLANK_SESSION")
    		{
    			var obj = JSON.parse(data);
    			$("#WelcomeMessage").text("Benvenuto " + obj.Nome + " " + obj.Cognome);
    			$("#WelcomeMessage").append("<a href='index.html' class='text-uppercase' onclick='Logout();'> Exit</a>");
    			$('#my-signin2').hide();
    			showMenuForUser();
    			resultbool = true;
    		}
    		else
    		{		
    			$('#my-signin2').show();
    			resultbool = false;
    		}
        } 
     });
	return resultbool;
	
}
function isAdmin()
{
	var resultbool = false;
	$.ajax({
        url: "/Restaurant/servlet/isLogged",
        type: 'get',
        async: false,
        success: function(data) {
            result = data;
            if(data != "Not Logged")
    		{
    			var obj = JSON.parse(data);
    			
    			resultbool = obj.Amministratore;
    		}
    		
        } 
     });
	return resultbool;
}
function Logout()
{
	
	signOut();
	$.get("/Restaurant/servlet/Logout", function(data) {
		$('#my-signin2').hide();
		location.reload();
	});
}

function registraUser()
{
	var Nome = $('#Nome').val();
	var Cognome = $('#Cognome').val();
	var NumeroTelefono = $('#Telefono').val();
	var Password = $('#Password').val();
	var ConfermaPassword = $('#ConfermaPassword').val();
	var Mail = $('#Mail').val();
	var Via = $('#Via').val();
	var NCivico = $('#nCivico').val();
	var Citta = $('#Citta').val();
	var Cap = $('#Cap').val();
	
	
	
	$('#return-msg').addClass('show-return-msg');
	$('.return-msg').on('click', function(e) {
		$(this).removeClass('show-return-msg');
	});
	if(Nome != "" && Cognome != "" && NumeroTelefono != "" && Password != "" && ConfermaPassword != "" && Mail != "" && Via != "" && NCivico != "" && Citta!="" && Cap!="")
	{
		var indirizzo = Via + ", " + NCivico + ", " + Citta + ", " + Cap;
		$.get("/Restaurant/servlet/SaveUser?Nome=" + Nome + "&Cognome=" + Cognome +"&NumeroTelefono=" + NumeroTelefono + "&Password="+Password 
				+"&Mail=" + Mail + "&Indirizzo=" + indirizzo + "&Amministratore=0&Confermato=0", function(data) {
			$('#return-msg').text("Utente registrato correttamente! Controlla la mail per confermare l'account");
			insertLog("Utente_Registrato");
	    });
	}
	else if(Nome == "")
	{
		$('#return-msg').text("Inserisci il nome");
	}
	else if(Cognome == "")
	{
		$('#return-msg').text("Inserisci il cognome");
	}
	else if(NumeroTelefono == "")
	{
		$('#return-msg').text("Inserisci il numero di telefono");
	}
	else if(Password == "")
	{
		$('#return-msg').text("Inserisci la password");
	}
	else if(ConfermaPassword == "" || Password != ConfermaPassword)
	{
		if(ConfermaPassword == "")
		{
			$('#return-msg').text("Inserisci di nuovo la password");
		}
		else
		{
			$('#return-msg').text("Le password non coincidono");
		}
	}
	else if(Mail == "")
	{
		$('#return-msg').text("Inserisci la mail");
	}
	else if(Via == "")
	{
		$('#return-msg').text("Inserisci la via");
	}
	else if(NCivico == "")
	{
		$('#return-msg').text("Inserisci il numero civico");
	}
	else if(Citta == "")
	{
		$('#return-msg').text("Inserisci la citta");
	}
	else if(Cap == "")
	{
		$('#return-msg').text("Inserisci il CAP: es 87042");
	}
}

function IncreaseQuantityProduct(index)
{
	var id = $('#idProductCart'+index).text();

	return $.get("/Restaurant/servlet/IncreaseQuantityProduct?idProduct=" + id, function(data) {
		if(data == "Ok")
		{
			loadCart();
			return true;
		}
		else
			return false;
	});
}
function DecreaseQuantityProduct(ind)
{
	var id = $('#idProductCart'+ind).text();
	
	return $.get("/Restaurant/servlet/DecreaseQuantityProduct?idProduct=" + id, function(data) {
		
		loadCart();
			if(data == "Ok")
			{
				return true;
			}
			else
				return false;
												
	});
}

function addCart(indexButton)
{
	
	//alert(x);
	if(isLogged())	
	{		
		var id = $('#idProduct'+indexButton).text();
		$.get("/Restaurant/servlet/addToCart?idProduct=" + id, function(data) {
			//alert(data);
			var array = JSON.parse(data);
			
			if(array.length > 0)
			{
				
				loadCart();
				return true;
		    }
			return false;
		});
	}
	else
	{
		alert("Effettua il login!");
	}
	
}

function loadCart()
{
	
	return $.get("/Restaurant/servlet/GetCart", function(data) {
		
		var array = JSON.parse(data);
		if(array.length > 0)
		{
				//$('#QuantityProductCart'+ind).val(value-1);
				$('#CartForm').text("");
				$('#CartForm').append("<div class='border-lines-container'>"+
                    "<h1 class='no-top-margin border-lines'>Carrello</h1>"+
                "</div>")
		    	var cartQuantity = 0;
				var total = 0;
		    	for (var i=0; i<array.length; i++)
		        {
		    		var obj = array[i];
		    		//alert(obj.ImageURL);
		    		cartQuantity += obj.Quantity;
		    		total+= obj.Quantity*obj.Price;
		    		$('#QuantityProductCart'+i).val(obj.Quantity);
		    		$('#CartForm').append("<div class='product-preview-small'>" +
	                        "<div class='product-img'>"+
	                            "<img alt='product-photo' src='"+ obj.ImageURL+ "'>"+
	                        "</div>"+
	                        "<div class='product-content'>"+
	                            "<div class='row'>"+
	                                "<div class='col-md-8'>"+
	                                    "<h4 class='product-title'>" + obj.Name + "</h4>" + "<h4 hidden class='product-title' id='idProductCart"+ i + "'>"+ obj.id + "</h4>"+
	                                    "Prezzo €"+ obj.Price +"/, order"+
	                                    "<div class='product-pieces'>"+
	                                        "<input id='QuantityProductCart" + i + "' type='text' value='" + obj.Quantity + "'>"+
	                                        "<div class='product-pieces-up' onclick='IncreaseQuantityProduct(" + i + ");'></div>"+
	                                        "<div class='product-pieces-down' onclick='DecreaseQuantityProduct(" + i + ");'></div>"+
	                                    "</div>"+
	                                    "pieces"+
	                                "</div>"+
	                                "<div class='col-md-4 product-price'>€"+
	                                    (obj.Price*obj.Quantity)+
	                                "</div>"+
	                            "</div>"+
	                        "</div><!-- .product-content -->"+
	                    "</div><!-- .product-preview-small -->");
		        }
		    	$('#cartQuantity').text(cartQuantity);
				$('#CartForm').append("<div class='product-preview-small'>"+
                        "<div class='product-img'>"+
                            "<i class='fa fa-truck'></i>"+
                        "</div>"+
                        "<div class='product-content'>"+
                            "<div class='row'>"+
                                "<div class='col-md-8'>"+
                                    "<h4 class='product-title'>Consegna</h4>"+
                                    "Prezzo €1, order"+
                                    "<div class='product-pieces product-pieces-readonly'>"+
                                        "<label class='switch'> <input id='switchDelivery' type='checkbox' value='false' readonly><span class='slider round'></span></label>"+
                                    "</div>"+
                                "</div>"+
                                "<div class='col-md-4 product-price'>"+
                                    "€1"+
                                "</div>"+
                            "</div>"+
                        "</div><!-- .product-content -->"+
                    "</div><!-- .product-preview-small -->"+
                    "<hr>"+
                    "<p class='text-right text-bigger' id='TotalCart'>Totale: €" + total + "</p>"+
                    "<div class='row text-xs-center'>"+
                        "<div class='col-sm-6' id='buttonPay'>"+
	                        "<form action='/Restaurant/servlet/AcceptPaymentRequest' method='POST'>"+
	                        "<script "+
	                            "src='https://checkout.stripe.com/checkout.js' class='stripe-button' "+
	                            "data-key='pk_test_xinVSJEltPQmhlIxl5fKY0ja00Q0hCQqc9' "+
	                            "data-amount='" + total.toString().replace('.', '') +"00' "+
	                            "data-name='" + $('#NomeLocale').text() + "' "+
	                            "data-description='Example charge' "+
	                            "data-image='https://stripe.com/img/documentation/checkout/marketplace.png' "+
	                            "data-locale='auto' "+
	                            "data-currency='eur'>"+
	                        "</script>"+
	                        "</form>"+
                        "</div>"+
                        "<div class='col-sm-6 text-right text-xs-center'>"+
                            "<div class='margin-15'></div>"+
                            "<a id='Delivery' onclick='Order(false);' class='button-yellow button-text-low button-long button-low scroll-to cart-trigger'>Ordina</a>"+
                        "</div>"+
                    "</div>");
				
				$('#buttonPay').text("");
				var tmp = total.toString().substring(total.toString().indexOf(".")+1);
				//alert(tmp.length  );
				//alert(tmp);
				if(total.toString().indexOf(",") == 1 && tmp.length == 1 || total.toString().indexOf(".") != -1 && tmp.length == 1)
				{
					
						$('#buttonPay').append("<form action='/Restaurant/servlet/AcceptPaymentRequest?Asporto="+ $("#switchDelivery").is(':checked') +"&Pagato=true&Costo="+total.toString().replace('.', '')+"0' method='POST'>"+
		                        "<script "+
	                            "src='https://checkout.stripe.com/checkout.js' class='stripe-button' "+
	                            "data-key='pk_test_xinVSJEltPQmhlIxl5fKY0ja00Q0hCQqc9' "+
	                            "data-amount='" + total.toString().replace('.', '') +"0' "+
	                            "data-name='" + $('#NomeLocale').text() + "' "+
	                            "data-description='Pagamento' "+
	                            "data-image='https://stripe.com/img/documentation/checkout/marketplace.png' "+
	                            "data-locale='auto' "+
	                            "data-currency='eur'>"+
	                        "</script>"+
	                        "</form>");
					
				}
				else if(total.toString().indexOf(",") == 1 && tmp.length == 2 || total.toString().indexOf(".") != -1 && tmp.length == 2)
				{
					
						$('#buttonPay').append("<form action='/Restaurant/servlet/AcceptPaymentRequest?Asporto="+ $("#switchDelivery").is(':checked') +"&Pagato=true&Costo="+total.toString().replace('.', '')+"' method='POST'>"+
		                        "<script "+
	                            "src='https://checkout.stripe.com/checkout.js' class='stripe-button' "+
	                            "data-key='pk_test_xinVSJEltPQmhlIxl5fKY0ja00Q0hCQqc9' "+
	                            "data-amount='" + total.toString().replace('.', '') +"' "+
	                            "data-name='" + $('#NomeLocale').text() + "' "+
	                            "data-description='Pagamento' "+
	                            "data-image='https://stripe.com/img/documentation/checkout/marketplace.png' "+
	                            "data-locale='auto' "+
	                            "data-currency='eur'>"+
	                        "</script>"+
	                        "</form>");
					
				}
				else
				{
					
						$('#buttonPay').append("<form action='/Restaurant/servlet/AcceptPaymentRequest?Asporto="+ $("#switchDelivery").is(':checked') +"&Pagato=true&Costo="+total.toString().replace('.', '')+"00' method='POST'>"+
		                        "<script "+
	                            "src='https://checkout.stripe.com/checkout.js' class='stripe-button' "+
	                            "data-key='pk_test_xinVSJEltPQmhlIxl5fKY0ja00Q0hCQqc9' "+
	                            "data-amount='" + total.toString().replace('.', '') +"00' "+
	                            "data-name='" + $('#NomeLocale').text() + "' "+
	                            "data-description='Pagamento' "+
	                            "data-image='https://stripe.com/img/documentation/checkout/marketplace.png' "+
	                            "data-locale='auto' "+
	                            "data-currency='eur'>"+
	                        "</script>"+
	                        "</form>");
					
				}
				
				$("#switchDelivery").on('change', function() {
				    if ($(this).is(':checked')) {
				    	total += 1;
				    	$("#TotalCart").text("Total: $" + total)
				        //switchStatus = $(this).is(':checked');
				        //alert(switchStatus);// To verify
				    	$('#buttonPay').text("");
						var tmp = total.toString().substring(total.toString().indexOf(".")+1);
						//alert(tmp.length  );
						//alert(tmp);
						if(total.toString().indexOf(",") == 1 && tmp.length == 1 || total.toString().indexOf(".") != -1 && tmp.length == 1)
						{
							$('#buttonPay').append("<form action='/Restaurant/servlet/AcceptPaymentRequest?Asporto="+ $("#switchDelivery").is(':checked') +"&Pagato=true&Costo="+total.toString().replace('.', '')+"0' method='POST'>"+
			                        "<script "+
		                            "src='https://checkout.stripe.com/checkout.js' class='stripe-button' "+
		                            "data-key='pk_test_xinVSJEltPQmhlIxl5fKY0ja00Q0hCQqc9' "+
		                            "data-amount='" + total.toString().replace('.', '') +"0' "+
		                            "data-name='" + $('#NomeLocale').text() + "' "+
		                            "data-description='Pagamento' "+
		                            "data-image='https://stripe.com/img/documentation/checkout/marketplace.png' "+
		                            "data-locale='auto' "+
		                            "data-currency='eur'>"+
		                        "</script>"+
		                        "</form>");
						}
						else if(total.toString().indexOf(",") == 1 && tmp.length == 2 || total.toString().indexOf(".") != -1 && tmp.length == 2)
						{
							$('#buttonPay').append("<form action='/Restaurant/servlet/AcceptPaymentRequest?Asporto="+ $("#switchDelivery").is(':checked') +"&Pagato=true&Costo="+total.toString().replace('.', '')+"' method='POST'>"+
			                        "<script "+
		                            "src='https://checkout.stripe.com/checkout.js' class='stripe-button' "+
		                            "data-key='pk_test_xinVSJEltPQmhlIxl5fKY0ja00Q0hCQqc9' "+
		                            "data-amount='" + total.toString().replace('.', '') +"' "+
		                            "data-name='" + $('#NomeLocale').text() + "' "+
		                            "data-description='Pagamento' "+
		                            "data-image='https://stripe.com/img/documentation/checkout/marketplace.png' "+
		                            "data-locale='auto' "+
		                            "data-currency='eur'>"+
		                        "</script>"+
		                        "</form>");
						}
						else
						{
							$('#buttonPay').append("<form action='/Restaurant/servlet/AcceptPaymentRequest?Asporto="+ $("#switchDelivery").is(':checked') +"&Pagato=true&Costo="+total.toString().replace('.', '')+"00' method='POST'>"+
			                        "<script "+
		                            "src='https://checkout.stripe.com/checkout.js' class='stripe-button' "+
		                            "data-key='pk_test_xinVSJEltPQmhlIxl5fKY0ja00Q0hCQqc9' "+
		                            "data-amount='" + total.toString().replace('.', '') +"00' "+
		                            "data-name='" + $('#NomeLocale').text() + "' "+
		                            "data-description='Pagamento' "+
		                            "data-image='https://stripe.com/img/documentation/checkout/marketplace.png' "+
		                            "data-locale='auto' "+
		                            "data-currency='eur'>"+
		                        "</script>"+
		                        "</form>");
						}
				    	
				    }
				    else {
				    	total -= 1;
				    	$("#TotalCart").text("Total: $" + total)
				    	//switchStatus = $(this).is(':checked');
				    	//alert(switchStatus);// To verify
				    	$('#buttonPay').text("");
						var tmp = total.toString().substring(total.toString().indexOf(".")+1);
						//alert(tmp.length  );
						//alert(tmp);
						if(total.toString().indexOf(",") == 1 && tmp.length == 1 || total.toString().indexOf(".") != -1 && tmp.length == 1)
						{
							$('#buttonPay').append("<form action='/Restaurant/servlet/AcceptPaymentRequest?Asporto="+ $("#switchDelivery").is(':checked') +"&Pagato=true&Costo="+total.toString().replace('.', '')+"0' method='POST'>"+
			                        "<script "+
		                            "src='https://checkout.stripe.com/checkout.js' class='stripe-button' "+
		                            "data-key='pk_test_xinVSJEltPQmhlIxl5fKY0ja00Q0hCQqc9' "+
		                            "data-amount='" + total.toString().replace('.', '') +"0' "+
		                            "data-name='" + $('#NomeLocale').text() + "' "+
		                            "data-description='Pagamento' "+
		                            "data-image='https://stripe.com/img/documentation/checkout/marketplace.png' "+
		                            "data-locale='auto' "+
		                            "data-currency='eur'>"+
		                        "</script>"+
		                        "</form>");
						}
						else if(total.toString().indexOf(",") == 1 && tmp.length == 2 || total.toString().indexOf(".") != -1 && tmp.length == 2)
						{
							$('#buttonPay').append("<form action='/Restaurant/servlet/AcceptPaymentRequest?Asporto="+ $("#switchDelivery").is(':checked') +"&Pagato=true&Costo="+total.toString().replace('.', '')+"' method='POST'>"+
			                        "<script "+
		                            "src='https://checkout.stripe.com/checkout.js' class='stripe-button' "+
		                            "data-key='pk_test_xinVSJEltPQmhlIxl5fKY0ja00Q0hCQqc9' "+
		                            "data-amount='" + total.toString().replace('.', '') +"' "+
		                            "data-name='" + $('#NomeLocale').text() + "' "+
		                            "data-description='Pagamento' "+
		                            "data-image='https://stripe.com/img/documentation/checkout/marketplace.png' "+
		                            "data-locale='auto' "+
		                            "data-currency='eur'>"+
		                        "</script>"+
		                        "</form>");
						}
						else
						{
							$('#buttonPay').append("<form action='/Restaurant/servlet/AcceptPaymentRequest?Asporto="+ $("#switchDelivery").is(':checked') +"&Pagato=true&Costo="+total.toString().replace('.', '')+"00' method='POST'>"+
			                        "<script "+
		                            "src='https://checkout.stripe.com/checkout.js' class='stripe-button' "+
		                            "data-key='pk_test_xinVSJEltPQmhlIxl5fKY0ja00Q0hCQqc9' "+
		                            "data-amount='" + total.toString().replace('.', '') +"00' "+
		                            "data-name='" + $('#NomeLocale').text() + "' "+
		                            "data-description='Pagamento' "+
		                            "data-image='https://stripe.com/img/documentation/checkout/marketplace.png' "+
		                            "data-locale='auto' "+
		                            "data-currency='eur'>"+
		                        "</script>"+
		                        "</form>");
						}
				    }
				});
				
				
				
				return true;
			}
			else
			{
				$('#CartForm').text("");
				$('#CartForm').append("<div class='border-lines-container'>"+
                    "<h1 class='no-top-margin border-lines'>Cart</h1>"+
                "</div>")
		    	var cartQuantity = 0;
				$('#cartQuantity').text(0);
				$('#QuantityProductCart'+i).val(obj.Quantity);
				return false;
			}
	});
}


function loadCartQuantity()
{
	return $.get("/Restaurant/servlet/GetCart", function(data) {
		
		var array = JSON.parse(data);
		if(array.length > 0)
		{
							
		    	var cartQuantity = 0;
		    	for (var i=0; i<array.length; i++)
		        {
		    		var obj = array[i];
		    		//alert(obj.ImageURL);
		    		cartQuantity += obj.Quantity;
		    		
		        }
		    	$('#cartQuantity').text(cartQuantity);
		}
	});
}

function SaveNewsletter()
{
	$('#return-msg').addClass('show-return-msg');
	$('.return-msg').on('click', function(e) {
		$(this).removeClass('show-return-msg');
	});
	var mail = $("#newsletterMail").val();
	return $.get("/Restaurant/servlet/SaveNewsletter?Mail="+ mail , function(data) {
			
			
			if(data == "Ok")
			{
				$('#return-msg').text("Sei stato iscritto alla newsletter!");
				insertLog("Iscrizione_Newsletter");
			}
			else
			{
				$('#return-msg').text("Error!");

			}
		});
}

function Order(Pagato)
{
		
	var switchStatus = false;
	var checked = $("#switchDelivery").is(':checked');
	
	
	return $.get("/Restaurant/servlet/SaveOrder?Asporto="+ checked+"&Pagato="+Pagato, function(data) {
		
		
		if(data == "Ok")
		{
			alert("Ordine effettuato con successo");
			insertLog("Ordine_Effettuato");
			//loadCart();
			
			$('#cartQuantity').text(0);
			//REDIRECT TO ORDER SECTION
			window.location.replace("/Restaurant/MyAccount.html");
		}
	});
	
	
	
}
function showMenuForUser()
{
	$("#deliveryMenu").show();
	$("#myAccountMenu").show();
	if(isAdmin())
	{
		$("#dashboard").show();

	}
	else
		$("#dashboard").hide();
}
function hideMenuForUser()
{
	$('#deliveryMenu').hide();
	$('#myAccountMenu').hide();
	$("#dashboard").hide();
}

function insertAnalytic(Page)
{
	$.get("/Restaurant/servlet/SaveAnalytic?Pagina="+ Page , function(data) {
		
		if(data == "Ok")
		{
			
		}
		
	});
}

//LOG 
function insertLog(Evento)
{
	$.get("/Restaurant/servlet/SaveLog?Event="+ Evento , function(data) {
		
		if(data == "Ok")
		{
			
		}
		
	});
}

//UTILS
function UrlExists(url) 
{
    var http = new XMLHttpRequest();
    http.open('HEAD', url, false);
    http.send();
    if (http.status != 404)
        return true;
    else
        return false;
}

//DASHBOARD FUNCTION

function loadDashboardHomeInfo()
{
	if(isAdmin())
	{
		$.get("/Restaurant/servlet/LocalInfo", function(data) {
	        var obj = JSON.parse(data);
	        $('#logo').attr("src", "../../"+obj.LogoURL);
	        /*
	        $('#NomeLocale').text(obj.Name);
	        $('#logoFooter').attr("src", obj.LogoURL);
	        $('#NomeLocaleFooter').text(obj.Name);
	        $("#Address").text(obj.Address);
	        $("#Mail").append(obj.Mail);
	        $("#Telephone").append(obj.Telephone);
	        $("#Telephone1").append(obj.Telephone);
	        */
	    });
		
		$.get("/Restaurant/servlet/AllAnalytic" , function(data) {
			
			array = JSON.parse(data);
			
			$('#NVisite').text(array.length);
			
		});
		
		$.get("/Restaurant/servlet/AllOrders", function(data) {
			
	    	array = JSON.parse(data);
	    	
	    	$('#NOrdini').text(array.length);
	    	var Guadagno = 0;
	    	for (var k=0; k<array.length; k++)
            {
	    		var obj = array[k];
	    		Guadagno += obj.Costo;
            }
	        $('#GuadagnoTot').text("€"+Guadagno);
	        
	        var today = new Date();
	        var time = today.getHours() + ":" + today.getMinutes();
	        $('#currentTime1').text("aggiornato : " + time);
	        $('#currentTime2').text("aggiornato : " + time);
	    	$('#currentTime3').text("aggiornato : " + time);
	        $('#currentTime4').text("aggiornato : " + time);
	    });
		
		$.get("/Restaurant/servlet/AllUsers", function(data) {
			
	    	array = JSON.parse(data);
	    	$('#NUsers').text(array.length);

		});

		
		
		$.get("/Restaurant/servlet/AllProducts", function(data) {
			
	    	array = JSON.parse(data);
	    	
	    	$('#tabTopProduct1').text("");
	    	
	    	for (var k=0; k<array.length; k++)
            {
	    		var obj = array[k];
	    		var Nome = obj.Name;
	    		var Tipo = obj.Type;
	    		var Costo = obj.Price;
	    		var NVendite = 0;
	    		
	    		$.ajax({
	    	        url: "/Restaurant/servlet/OrdersByProduct?idProdotto="+obj.id,
	    	        type: 'get',
	    	        async: false,
	    	        success: function(data_temp) {
	    	        	
	    	        	array_tmp = JSON.parse(data_temp);
		    	    	NVendite = array_tmp.length;
	    	           
	    	        } 
	    	     });
	    		
	    		
	    		var reviewsArray = obj.Reviews;
	    		var SVoti = 0;
	    		for (var i=0; i<reviewsArray.length; i++)
	            {
	    			SVoti += reviewsArray[i].Voto;
	            }
	    		var mediaVoti = 0;
	    		if(reviewsArray.length > 0)
	    			mediaVoti = SVoti/reviewsArray.length;
	    		//alert(mediaVoti);
	    		
	    	
	    		$('#tabTopProduct1').append("<tr>"+
                                                            "<td>" + Nome +"</td>"+
                                                            "<td>" + Tipo +"</td>"+
                                                            "<td>" + Costo +"</td>"+
                                                            "<td>" + NVendite +"</td>"+
                                                            "<td>"+
                                                                     "<div class='stars stars-example-fontawesome-o'>"+
                                                                            "<select id='example-fontawesome-o"+k+"' name='rating' data-current-rating='" + mediaVoti + "' autocomplete='off'>"+
						                                                        "<option value='' label='0'></option>"+
						                                                        "<option value='1'>1</option>"+
						                                                        "<option value='2'>2</option>"+
						                                                        "<option value='3'>3</option>"+
						                                                        "<option value='4'>4</option>"+
						                                                        "<option value='5'>5</option>"+
						                                
						                                                    "</select>"+
                                                                           
                                                                    "</div>"+
                                                            "</td>"+
                                                        "</tr>");
	    	
	    		 
	    		$('.rating-enable').on('click',function(event) {
		            event.preventDefault();

		            ratingEnable();

		            $(this).addClass('deactivated');
		            $('.rating-disable').removeClass('deactivated');
		        });

		        $('.rating-disable').on('click',function(event) {
		            event.preventDefault();

		            ratingDisable();

		            $(this).addClass('deactivated');
		            $('.rating-enable').removeClass('deactivated');
		        });

		        ratingEnable(k);
	    		
            }
            
	    	
	       
	    		
	    });
	        
	}
	
	
}

function showNewOrder()
{
	$.get("/Restaurant/servlet/OrdersByState?Stato=Richiesto" , function(data) {
		
		array = JSON.parse(data);
		
		$('#OrdiniMenu').append("<span class='pcoded-badge label label-danger'>" + array.length + "</span>");
		
	});
	
}

function allAnalytic()
{
	
}

//Ordini Dashboard

function loadOrderTable(Filter, page)
{
	//alert("Load"+page);
	if(isAdmin())
	{
		$("#OrdersPanelAll").removeClass('active');
		$("#OrdersPanelRichiesto").removeClass('active');
		$("#OrdersPanelPreparazione").removeClass('active');
		$("#OrdersPanelConsegna").removeClass('active');
		$("#OrdersPanelPronto").removeClass('active');
		$("#OrdersPanelConsegnato").removeClass('active');
		
		
		if(Filter == 'All')
		{
			
			$("#OrdersPanelAll").addClass('active');

			$.get("/Restaurant/servlet/AllOrders", function(data) {
				
		    	array = JSON.parse(data);
		    	
		    	showOrderTable(array, page);
		    	   	
		        
		    });
		}
		else
		{
			if(Filter == "In Preparazione")
				$("#OrdersPanelPreparazione").addClass('active');
			else if(Filter == "In Consegna")
				$("#OrdersPanelConsegna").addClass('active');
			else 
				$("#OrdersPanel"+Filter).addClass('active');

			$.get("/Restaurant/servlet/AllOrders?Stato="+Filter, function(data) {
						
				 array = JSON.parse(data);
				 //alert(array);
				 showOrderTable(array, page);
				    	   	
				        
			});
		}
	}
	else
	{
		if(Filter == 'All')
		{
			$.get("/Restaurant/servlet/OrdersByUser", function(data) {
				
		    	array = JSON.parse(data);
		    	
		    	showOrderTable(array, page);
		        
		    });
		}
		else
		{
			$.get("/Restaurant/servlet/OrdersByUser?Stato="+Filter, function(data) {
						
				 array = JSON.parse(data);
				    	
				 showOrderTable(array, page);
				        
			});
		}
	}
}

function showOrderTable(array, pageNumber)
{

	$('#tablediv').text("");
	
	
	var rows = "<table class='table table-striped table-bordered' id='example-2'>"+
				    "<thead>"+
				        "<tr>"+
				            "<th>ID</th>"+
				            "<th>Stato</th>"+
				            "<th>Data</th>"+
				            "<th>Asporto</th>"+
				            "<th>Costo</th>"+
				        "</tr>"+
				    "</thead>"+
				    "<tbody id='ordertablebody'>";

	var nPage = array.length/6;
	var index = -1;
	for (var i=0; index<array.length && i<6; i++)
    {
		
		if(index == -1)
		{
			if(pageNumber == 1)
			{
				index = (i*pageNumber);
			}else
			{
				index = ((i*pageNumber)-(6+i))+(pageNumber*6);
			}
		}
		var obj = array[index];

    	var idOrdine = obj.idOrdine;
    	var data = obj.DataOra;
    	var stato = obj.Stato;
    	var asporto = obj.Asporto;
    	var costo = obj.Costo;
    	var ProductsArray = obj.Products;
    	
    	var asp = "No";
    	if(asporto)
    		asp = "Si";
    	
    	
    	
    	var row = "<tr onclick='showOrderProducts("+ idOrdine +")'>"+
                   	"<th scope='row' id='idOrdineTable'>"+ idOrdine +"</th>"+
                        "<td class='tabledit-view-mode'><span class='tabledit-span'>"+ stato +"</span>"+
	                        "<select id='stato" + idOrdine +"' class='tabledit-input form-control input-sm' name='Stato' disabled='' style='display:none;'>"+
					           "<option value='Richiesto'>Richiesto</option>"+
					           "<option value='In Preparazione'>In Preparazione</option>"+
					           "<option value='In Consegna'>In Consegna</option>"+
					           "<option value='Pronto'>Pronto</option>"+
					           "<option value='Consegnato'>Consegnato</option>"+
					        "</select>"+
                        "</td>"+
                        "<td class='tabledit-view-mode'><span class='tabledit-span'>"+ data +"</span>"+
                              "<input id='data"+ idOrdine +"' class='tabledit-input form-control input-sm' style='display:none;' type='text' name='Data' value='"+ data +"'>"+
                        "</td>"+
                        "<td class='tabledit-view-mode'><span class='tabledit-span'>"+ asp +"</span>"+
                              "<select id='asporto"+ idOrdine +"' class='tabledit-input form-control input-sm' name='Asporto' disabled='' style='display:none;'>"+
						           "<option value='Si'>Si</option>"+
						           "<option value='No'>No</option>"+
						      "</select>"+
                         "</td>"+
                         "<td class='tabledit-view-mode'><span class='tabledit-span'>"+ costo +"</span>"+
                         	"<input id='costo"+ idOrdine +"' class='tabledit-input form-control input-sm' style='display:none;' type='text' name='Costo' value='"+ costo +"'>"+
                         "</td>"+
                    "</tr>";                                             
    	
    	rows = rows + row; 
    	
    	var collapseDataProduct = "";
    	
    	collapseDataProduct = collapseDataProduct +"<div class='collapse' id='collapseOrder"+ idOrdine + "'>"+
	  			"<div class='card card-body'>";
    	
    	var collapseDataIngredient = "";
		
    	
    	for (var k=0; k<ProductsArray.length; k++)
        {
    		var product = ProductsArray[k];
    		var idProdotto = product.idProdotto;
    		var Nome = product.Name;
    		var Prezzo = product.Price;
    		var Tipo = product.Type;
    		var Quantita = product.Quantity;
    		var IngredientsArray = product.Ingredients;
    		
    		collapseDataProduct= collapseDataProduct +"<p class='active' onclick='accordionOrder(" + idOrdine + idProdotto +");' data-toggle='collapse' data-parent='#TextData' href='#collapseProduct" + idOrdine + idProdotto +"' role='button' aria-expanded='false' aria-controls='collapseProduct"+ idOrdine + idProdotto + "'> Nome: " + Nome + " - Tipo: " + Tipo + " - Costo: " + Prezzo + " - Quantità: " + Quantita + "</p>";
    		
    		collapseDataIngredient = collapseDataIngredient +"<div class='collapse' id='collapseProduct" + idOrdine + idProdotto + "'>"+
  			"<div class='card card-body'>";
    		
    		for (var j=0; j<IngredientsArray.length; j++)
            {
    			var ingredient = IngredientsArray[j];
    			var idIngrediente = ingredient.idIngredient;
    			var NomeI = ingredient.Name; 
    			var PrezzoI = ingredient.Price;
    			collapseDataIngredient= collapseDataIngredient +"<p class='active' data-parent='#TextData" + idOrdine + "'> Nome: " + NomeI + " - Costo: " + PrezzoI +"</p>";

            }
    		collapseDataIngredient = collapseDataIngredient +"</div></div>";
        }
    	collapseDataProduct = collapseDataProduct +"</div></div>";
    	
    	
    	
    	index++;
    }
	
	rows = rows + "</tbody></table>";
	
	$('#paginationTable').text("");
	
	
	pagin =  "<li class='footable-page-nav disabled' data-page='first'>" +
				"<a class='footable-page-link' href='#'>«</a>" +
			"</li>" +
			"<li class='footable-page-nav disabled' data-page='prev'>" +
				"<a class='footable-page-link' href='#'>‹</a>" +
			"</li>";
	
	var Filter = "";
	if($("#OrdersPanelAll").hasClass('active'))
		Filter = "All";
	else if($("#OrdersPanelRichiesto").hasClass('active'))
		Filter = "Richiesto";
	else if($("#OrdersPanelPreparazione").hasClass('active'))
		Filter = "In Preparazione";
	else if($("#OrdersPanelConsegna").hasClass('active'))
		Filter = "In Consegna";
	else if($("#OrdersPanelPronto").hasClass('active'))
		Filter = "Pronto";
	else if($("#OrdersPanelConsegnato").hasClass('active'))
		Filter = "Consegnato";
	else
		Filter = "All";
	
	
	for(var i=0; i<nPage; i++)
	{
		if((i+1) == pageNumber)
		{
			
			
			pagin = pagin + "<li class='footable-page visible active' data-page='" + (i+1) +"'>" +
								"<a class='footable-page-link' id='page"+ (i+1) +"' onclick=loadOrderTable(\"" + Filter+ "\","+ (i+1)+")>" + (i+1) +"</a>" +
							"</li>";
		}
		else
		{
			
			pagin = pagin + "<li class='footable-page visible' data-page='" + (i+1) +"'>" +
								"<a class='footable-page-link' id='page"+ (i+1) +"' onclick=loadOrderTable(\"" + Filter + "\","+ (i+1) +") >" + (i+1) +"</a>" +
							"</li>";
		}
	}
	
	pagin = pagin + "<li class='footable-page-nav' data-page='next'>" +
						"<a class='footable-page-link' href='#'>›</a>" +
					"</li>" +
					"<li class='footable-page-nav' data-page='last'>" +
						"<a class='footable-page-link' href='#'>»</a>" +
					"</li>";
	
	
	$('#paginationTable').append(pagin);
	

	$('#tablediv').append(rows);
	
	
	
	$('#example-2').Tabledit({
    	
        columns: {

          identifier: [0, 'id'],

          editable: []

      }

	});
	
	$("#example-2 .tabledit-edit-button").on("click", function(){
		var $row = $(this).closest("tr");  
		var $id = $row.find("#idOrdineTable").text(); // Find the text
		alert("Modifica: " + $id ); 
	});
	$("#example-2 .tabledit-save-button").on("click", function(){
		var $row = $(this).closest("tr");  
		var $id = $row.find("#idOrdineTable").text(); // Find the text
		//alert("Modifica: " + $id ); 
		
		var stato = $("#stato"+$id).val();
		var asporto = $("#asporto"+$id).val();
		var costo = $("#costo"+$id).val();
		var aspBool = false;
		if(asporto == "Si")
			aspBool = true;
		
		updateOrder($id, stato, aspBool, costo, false);
		//alert(costo);
		
	});
	
	$("#example-2 .tabledit-confirm-button").on("click", function(){
		var $row = $(this).closest("tr");  
		var $id = $row.find("#idOrdineTable").text(); // Find the text
		//alert("Modifica: " + $id ); 
		deleteOrder($id);
		
		
	});
	
}


function showOrderProducts(idOrder)
{
	$.get("/Restaurant/servlet/OrdersById?idOrdine="+idOrder , function(data) {
		
		var obj = JSON.parse(data);
		
		//RESET TABLE INGREDIENTI
		$("#idProdotto").text("Ingredienti Prodotto: ");
		$('#orderproductingredientstablediv').text("");
		
		
		$("#idOrdine").text("Prodotti Ordine: "+idOrder);
		
		$('#orderproducttablediv').text("");
		
		
		var rows = "<table class='table table-striped table-bordered' id='orderproducttable'>"+
					    "<thead>"+
					        "<tr>"+
					            "<th>ID</th>"+
					            "<th>Nome</th>"+
					            "<th>Costo</th>"+
					            "<th>Tipo</th>"+
					            "<th>Quantità</th>"+
					        "</tr>"+
					    "</thead>"+
					    "<tbody id='orderproductstablebody'>";
		
		var idOrdine = obj.idOrdine;
    	var data = obj.DataOra;
    	var stato = obj.Stato;
    	var asporto = obj.Asporto;
    	var costo = obj.Costo;
    	var ProductsArray = obj.Products;
    	
    	for (var k=0; k<ProductsArray.length; k++)
        {
    		var product = ProductsArray[k];
    		var idProdotto = product.idProdotto;
    		var Nome = product.Name;
    		var Prezzo = product.Price;
    		var Tipo = product.Type;
    		var Quantita = product.Quantity;
    		var IngredientsArray = product.Ingredients;
    		
    		
    		var row = "<tr onclick='showOrderProductIngredients("+ idProdotto +")'>"+
           	"<th scope='row' id='idIngredienteTable'>"+ idProdotto +"</th>"+
                "<td class='tabledit-view-mode'><span class='tabledit-span'>"+ Nome +"</span>"+

                "</td>"+
                "<td class='tabledit-view-mode'><span class='tabledit-span'>"+ Prezzo +"</span>"+
                      "<input class='tabledit-input form-control input-sm' type='text' name='Costo' value='"+ Prezzo +"'>"+
                "</td>"+
                "<td class='tabledit-view-mode'><span class='tabledit-span'>"+ Tipo +"</span>"+
                "<select id='tipo" + Tipo +"' class='tabledit-input form-control input-sm' name='Tipo' disabled='' style='display:none;'>"+
		           "<option value='Richiesto'>Richiesto</option>"+
		           "<option value='In Preparazione'>In Preparazione</option>"+
		           "<option value='In Consegna'>In Consegna</option>"+
		           "<option value='Pronto'>Pronto</option>"+
		           "<option value='Consegnato'>Consegnato</option>"+
		        "</select>"+
                "</td>"+
                 "<td class='tabledit-view-mode'><span class='tabledit-span'>"+ Quantita +"</span>"+
                 	"<input class='tabledit-input form-control input-sm' type='text' name='Quantita' value='"+ Quantita +"'>"+
                 "</td>"+
            "</tr>";   
    		
        	rows = rows + row; 
        	
        	
    		
        }
    	rows = rows + "</tbody></table>";
    	
    	$('#orderproducttablediv').append(rows);
    	    	
    	
    	$('#orderproducttable').Tabledit({
        	
            columns: {

              identifier: [0, 'id'],

              editable: [[2, 'Costo'], [4, 'Quantity']]

          }
    	});
    	
    	$("#orderproducttable .tabledit-edit-button").on("click", function(){
    		
    		var $row = $(this).closest("tr");  
    		var $id = $row.find("#idIngredienteTable").text(); // Find the text
    		alert("Modifica: " + $id ); 
    	});
		
    	$("#orderproducttable .tabledit-save-button").on("click", function(){
    		var $row = $(this).closest("tr");  
    		var $id = $row.find("#idOrdineTable").text(); // Find the text
    		alert("Modifica: " + $id ); 
    		
    		var stato = $("#stato"+$id).val();
    		var asporto = $("#asporto"+$id).val();
    		var costo = $("#costo"+$id).val();
    		var aspBool = false;
    		if(asporto == "Si")
    			aspBool = true;
    		
    		updateOrder($id, stato, asp, costo, false);
    		alert(costo);
    		
    	});
    	
    	$("#orderproducttable .tabledit-confirm-button").on("click", function(){
    		var $row = $(this).closest("tr");  
    		var $id = $row.find("#idOrdineTable").text(); // Find the text
    		alert("Modifica: " + $id ); 
    		
    		
    		
    	});
    	
	});
	
	
	

	
	
}

function updateOrder(idOrder, Stato, Asporto, Costo, Pagato)
{
	/*
	var Asp = false;
	if(Asporto == "Si")
		Asp = true;
	*/
	alert(Costo);
	$.get("/Restaurant/servlet/UpdateOrder?idOrdine="+idOrder+"&Stato="+Stato+"&Asporto="+Asporto+"&Costo="+Costo+"&Pagato="+Pagato , function(data) {
		
		if(data != "Ok")
			alert("error");
	});
}

function deleteOrder(idOrder)
{
	$.get("/Restaurant/servlet/DeleteOrder?idOrdine="+idOrder, function(data) {
		
		if(data != "Ok")
			alert("error");
	});
}


// TABLE
function ratingEnable(k) {
    $('#example-1to10').barrating('show', {
        theme: 'bars-1to10',
    });

    $('#example-movie').barrating('show', {
        theme: 'bars-movie'
    });

    $('#example-movie').barrating('set', 'Mediocre');

    $('#example-square').barrating('show', {
        theme: 'bars-square',
        showValues: true,
        showSelectedRating: false
    });

    $('#example-pill').barrating('show', {
        theme: 'bars-pill',
        initialRating: 'A',
        showValues: true,
        showSelectedRating: false,
        allowEmpty: true,
        emptyValue: '-- no rating selected --',
        onSelect: function(value, text) {
            alert('Selected rating: ' + value);
        }
    });

    $('#example-reversed').barrating('show', {
        theme: 'bars-reversed',
        showSelectedRating: true,
        reverse: true
    });

    $('#example-horizontal').barrating('show', {
        theme: 'bars-horizontal',
        reverse: true,
        hoverState: false
    });

    $('#example-fontawesome').barrating({
        theme: 'fontawesome-stars',
        showSelectedRating: false
    });
    
    $('.fontawesome-stars').barrating({
        theme: 'fontawesome-stars',
        showSelectedRating: false
    });

    $('.rating-star').barrating({
        theme: 'css-stars',
        showSelectedRating: false
    });

    $('#example-bootstrap').barrating({
        theme: 'bootstrap-stars',
        showSelectedRating: false
    });

    var currentRating = $('#example-fontawesome-o'+k).data('current-rating');

    $('.stars-example-fontawesome-o .current-rating')
        .find('span')
        .html(currentRating);

    $('.stars-example-fontawesome-o .clear-rating').on('click', function(event) {
        event.preventDefault();

        $('#example-fontawesome-o'+k)
            .barrating('clear');
    });

    $('#example-fontawesome-o'+k).barrating({
        theme: 'fontawesome-stars-o',
        showSelectedRating: false,
        initialRating: currentRating,
        onSelect: function(value, text) {
        	if(window.location.pathname == "/Restaurant/menu_1.html" && k != 0)
        	{
	        	if(isLogged())
	        	{
	        		var id = $("#idProduct"+k).text();
	        		saveReviewProduct(id, value);
		        	//alert(value);
		            if (!value) {
		                $('#example-fontawesome-o'+k)
		                    .barrating('clear');
		            } else {
		                $('.stars-example-fontawesome-o .current-rating')
		                    .addClass('hidden');
		
		                $('.stars-example-fontawesome-o .your-rating')
		                    .removeClass('hidden')
		                    .find('span')
		                    .html(value);
		            }
	        	}
	        	else
	        	{
	        		alert("Devi aver effettuato il login per dare un voto ad un prodotto");
	        		$('#example-fontawesome-o'+k).barrating('clear');
	       		}
        	}
        	else
        	{
        		
	            if (!value) {
	                $('#example-fontawesome-o'+k)
	                    .barrating('clear');
	            } else {
	                $('.stars-example-fontawesome-o .current-rating')
	                    .addClass('hidden');
	
	                $('.stars-example-fontawesome-o .your-rating')
	                    .removeClass('hidden')
	                    .find('span')
	                    .html(value);
	                $('#example-fontawesome-o'+k).data("your-rating",value); //setter


	            }
        		
        	}
        },
        onClear: function(value, text) {
            $('.stars-example-fontawesome-o')
                .find('.current-rating')
                .removeClass('hidden')
                .end()
                .find('.your-rating')
                .addClass('hidden');
        }
    });
    
}
function ratingDisable() {
    $('select').barrating('destroy');
}

function hideReviewLocal()
{
	//alert("1");
	if(isLogged())
	{
		$.get("/Restaurant/servlet/ReviewLocalByLocalUser" , function(data) {
			
			if(data != "null")
				$('#ReviewDIV').text("");
	
		});
	}
}

function saveReviewProduct(idProduct, rating)
{
	
	$.get("/Restaurant/servlet/SaveReviewProduct?idProdotto="+idProduct+"&Voto="+rating , function(data) {
		
		if(data != "Ok")
			alert("error");
		
	});
	
	
}
function saveReviewLocal(rating, Review)
{
	
	$.get("/Restaurant/servlet/SaveReviewLocal?Voto="+rating +"&Recensione=" + Review , function(data) {
		
		if(data != "Ok")
			alert("error");
		else
		{
			alert("Recensione Inviata");
			hideReviewLocal();
		}
	});
	
}
//EDIT_TABLE

function showOrderProductIngredients(idProduct)
{
	
	$.get("/Restaurant/servlet/IngredientsOfProduct?id="+idProduct , function(data) {
		
		var array = JSON.parse(data);
		
		$("#idProdotto").text("Ingredienti Prodotto: "+idProduct);
		
		$('#orderproductingredientstablediv').text("");
		
		
		var rows = "<table class='table table-striped table-bordered' id='orderproductingredienttable'>"+
					    "<thead>"+
					        "<tr>"+
					            "<th>ID</th>"+
					            "<th>Nome</th>"+
					            "<th>Costo</th>"+
					        "</tr>"+
					    "</thead>"+
					    "<tbody id='orderproductstablebody'>";
		
		var c2 = "";
		$.ajax({
	        url: "/Restaurant/servlet/AllIngredients",
	        type: 'get',
	        async: false,
	        success: function(data) {
	        	var array = JSON.parse(data);
	        	
	        	//c2 = "<span class='tabledit-span' >"+ array[0].Nome + "</span><select id='ingredientiSelect' class='tabledit-input form-control input-sm' onchange='up()' style='display:none;' name='Nome'>";
	        	
	        	for (var k=0; k<array.length; k++)
	            {
	        		var ing = array[k];
	        		
	        		c2 = c2 + "<option value='"+ ing.idIngrediente +"'>"+ ing.Nome +"</option>";
	        		
	            }
	        	
	        	//c2 = c2 + "</select>";
	    		
	        } 
	     });
		
		//alert(c2);
		
    	for (var k=0; k<array.length; k++)
        {
    		var product = array[k];
    		var idIngrediente = product.id;
    		var Nome = product.Name;
    		var Prezzo = product.Price;
    		
    		
    		var row = "<tr>"+
           	"<th scope='row' id='idIngredienteTable'>"+ idIngrediente +"</th>"+
                "<td class='tabledit-view-mode'> <span class='tabledit-span' >"+ Nome + "</span>"+
           			"<select id='ingredientiSelect"+ idIngrediente +"' class='tabledit-input form-control input-sm' onchange='refreshIngredientPrice("+ idIngrediente +")' style='display:none;' name='Nome'>"+ c2 +
           			
           			"</select>"+
                "</td>"+
                "<td class='tabledit-view-mode'><span class='tabledit-span' >"+ Prezzo +"</span>"+
                      "<input id='PrezzoIngrediente"+idIngrediente+"' class='tabledit-input form-control input-sm' style='display:none;' type='text' name='Costo' value='"+ Prezzo +"'>"+
                "</td>"+
            "</tr>";  
    		
    		/*
    		var row = "<tr'>"+
           	"<th scope='row'>"+ idIngrediente +"</th>"+
                "<td class='tabledit-view-mode'><span class='tabledit-span'>"+ Nome +"</span>"+
                	"<input class='tabledit-input form-control input-sm' style='display:none;' type='text' name='Nome' value='"+ Nome +"'>"+
                "</td>"+
                "<td class='tabledit-view-mode'><span class='tabledit-span'>"+ Prezzo +"</span>"+
                      "<input class='tabledit-input form-control input-sm' style='display:none;' type='text' name='Costo' value='"+ Prezzo +"'>"+
                "</td>"+
            "</tr>";   
    		*/
        	rows = rows + row; 
        	
        	
    		
        }
    	rows = rows + "</tbody></table>";
    	
    	$('#orderproductingredientstablediv').append(rows);
    	    	
    	
    	$('#orderproductingredienttable').Tabledit({
        	
            columns: {

              identifier: [0, 'id'],

              editable: [ ]

            }
    	});
		
    	$("#orderproductingredienttable .tabledit-edit-button").on("click", function(){
    		var $row = $(this).closest("tr");  
    		var $id = $row.find("#idIngredienteTable").text(); // Find the text
    		//$("#PrezzoIngrediente"+id).prop('disabled', true);
    		alert("Modifica: " + $id ); 
    	});
	});
	
	
	
	
}
function add_row_product()
{
	$.get("/Restaurant/servlet/AllIngredients", function(data) {
           
        	var array = JSON.parse(data);
        	
        	var c2 = "<span class='tabledit-span' >"+ array[0].Nome + "</span><select class='tabledit-input form-control input-sm' name='Tipo' style='display:none;' disabled='' >";
        	
        	for (var k=0; k<array.length; k++)
            {
        		var ing = array[k];
        		
        		c2 = c2 + "<option value='1'>"+ ing.Nome +"</option>";
        		
            }
        	
        	c2 = c2 + "</select>";
        	
        	
        	var table = document.getElementById("orderproducttable");
            var t1=(table.rows.length);
            var row = table.insertRow(t1);
            var cell1 = row.insertCell(0);
            var cell2 = row.insertCell(1);
            var cell3 = row.insertCell(2);
            var cell4 = row.insertCell(3);
            var cell5 = row.insertCell(4);
            var cellButton = row.insertCell(5);
            
             cell1.className='abc';
             cell2.className='abc';
             cell4.className='tabledit-edit-mode';
             
             $(c2).appendTo(cell4);
             cell4 = cell4 + c2;
             
           $('<span class="tabledit-span" >ND</span><input class="tabledit-input form-control input-sm" type="text" name="Id" style="display:none;" value="ND" disabled="">').appendTo(cell1);
           $('<span class="tabledit-span" >Personalizzata</span><input class="tabledit-input form-control input-sm" type="text" style="display:none;" name="Name" value="Personalizzata" disabled="">').appendTo(cell2);
           $('<span class="tabledit-span" >0</span><input class="tabledit-input form-control input-sm" type="text" style="display:none;" name="Costo" value="0">').appendTo(cell3);

           var cButton = "<td style='white-space: nowrap; width: 1%;'><div class='tabledit-toolbar btn-toolbar' style='text-align: left;'>"+
                                           "<div class='btn-group btn-group-sm' style='float: none;'><button class='tabledit-edit-button btn btn-primary waves-effect waves-light' style='float: none;margin: 5px;' type='button'>"+
                                           "<span class='icofont icofont-ui-edit'></span></button><button class='tabledit-delete-button btn btn-danger waves-effect waves-light' style='float: none;margin: 5px;' type='button'>"+
                                           "<span class='icofont icofont-ui-delete'></span></button></div>"+
                                           "<button class='tabledit-save-button btn btn-sm btn-success' style='display: none; float: none;' type='button'>Save</button>"+
                                           "<button class='tabledit-confirm-button btn btn-sm btn-danger' style='display: none; float: none;' type='button'>Confirm</button>"+
                                           "<button class='tabledit-restore-button btn btn-sm btn-warning' style='display: none; float: none;' type='button'>Restore</button>"+
                                       "</div></td>";
           $(cButton).appendTo(cellButton);
           /*
           $('#orderproducttable').Tabledit({
           	
               columns: {

                 identifier: [0, 'id'],

                 editable: [[2, 'Costo'], [4, 'Quantity']]

             }
       	});
       	*/
       	
             
     });
	
	
    
 	
}
function add_row_ingredient()
{
	alert("as");
	$.get("/Restaurant/servlet/AllIngredients", function(data) {
           
        	var array = JSON.parse(data);
        	
        	var c2 = "<span class='tabledit-span' >"+ array[0].Nome + "</span><select class='tabledit-input form-control input-sm' name='Nome'  disabled='' >";
        	
        	for (var k=0; k<array.length; k++)
            {
        		var ing = array[k];
        		
        		c2 = c2 + "<option value='1'>"+ ing.Nome +"</option>";
        		
            }
        	
        	c2 = c2 + "</select>";
        	
        	
        	var table = document.getElementById("orderproductingredienttable");
            var t1=(table.rows.length);
            var row = table.insertRow(t1);
            var cell1 = row.insertCell(0);
            var cell2 = row.insertCell(1);
            var cell3 = row.insertCell(2);

             cell1.className='abc';
             cell2.className='abc';

             $(c2).appendTo(cell2);
             cell2 = cell2 + c2;
             
           $('<span class="tabledit-span" >ND</span><input class="tabledit-input form-control input-sm" type="text" name="Id" value="ND" disabled="">').appendTo(cell1);
             //$('<span class="tabledit-span" >Click Me To Edit</span><input class="tabledit-input form-control input-sm" type="text" name="Nome" value="undefined"  disabled="">').appendTo(cell2);
             $('<span class="tabledit-span" >0</span><select class="tabledit-input form-control input-sm" name="Nickname"  disabled="" ><option value="1">@mdo</option><option value="2">@fat</option><option value="3">@twitter</option></select>').appendTo(cell3);

             
             
     });
	
	
    
 	
};

function refreshIngredientPrice(idIngrediente)
{
	//alert($("#ingredientiSelect"+idIngrediente ).val());
	var ingSelect = $("#ingredientiSelect"+idIngrediente ).val();
	$.get("/Restaurant/servlet/IngredientById?idIngrediente="+ingSelect , function(data) {
		
		obj = JSON.parse(data);
		
		$("#PrezzoIngrediente"+idIngrediente).val(obj.Prezzo);
		$("#PrezzoIngrediente"+idIngrediente).prop('disabled', true);
		//alert($("#PrezzoIngrediente"+idIngrediente).val(obj.Prezzo));
		$("#PrezzoIngrediente"+idIngrediente).text(obj.Prezzo);
	});
}


//Utenti Dashboard

function loadUsersTable(Filter, page)
{
	//alert("Load"+page);
	if(isAdmin())
	{
		$("#OrdersPanelAll").removeClass('active');
		$("#OrdersPanelInAttesa").removeClass('active');
		$("#OrdersPanelConfermati").removeClass('active');
		
		
		if(Filter == 'All')
		{
			
			$("#OrdersPanelAll").addClass('active');

			$.get("/Restaurant/servlet/AllUsers", function(data) {
				
		    	array = JSON.parse(data);
		    	
		    	showUserTable(array, page);
		    	   	
		        
		    });
		}
		else
		{
			var parameter;
			if(Filter == "In Attesa")
			{
				$("#OrdersPanelInAttesa").addClass('active');
				parameter = "false";
			}
			else if(Filter == "Confermati")
			{
				$("#OrdersPanelConfermati").addClass('active');
				parameter = "true";
			}
			
			$.get("/Restaurant/servlet/AllUsersByConfirm?Confermati="+parameter, function(data) {
				
				 array = JSON.parse(data);
				 //alert(array);
				 showUserTable(array, page);
				    	   	
				        
			});
		}
	}
	
}

function showUserTable(array, pageNumber)
{

	$('#tablediv').text("");
	
	
	var rows = "<table class='table table-striped table-bordered' id='usertable'>"+
				    "<thead>"+
				        "<tr>"+
					        "<th>Telefono</th>"+
	                        "<th>Nome</th>"+
	                        "<th>Cognome</th>"+
	                        "<th>Mail</th>"+
	                        "<th>Indirizzo</th>"+
	                        "<th>Admin</th>"+
	                        "<th>Confermato</th>"+
				        "</tr>"+
				    "</thead>"+
				    "<tbody id='usertablebody'>";

	var nPage = array.length/6;
	var index = -1;
	for (var i=0; index<array.length && i<6; i++)
    {
		
		if(index == -1)
		{
			if(pageNumber == 1)
			{
				index = (i*pageNumber);
			}else
			{
				index = ((i*pageNumber)-(6+i))+(pageNumber*6);
			}
		}
		var obj = array[index];

    	var NumeroTelefono = obj.NumeroTelefono;
    	var Nome = obj.Nome;
    	var Cognome = obj.Cognome;
    	var Mail = obj.Mail;
    	var Indirizzo = obj.Indirizzo;
    	var Amministratore = obj.Amministratore;
    	var Confermato = obj.Confermato;
    	
    	var Admin = "No";
    	if(Amministratore)
    		Admin = "Si";
    	
    	var Confirm = "No";
    	if(Confermato)
    		Confirm = "Si";
    	
    	
    	var row = "<tr>"+
                   	"<th scope='row' id='NumeroTelefono'>"+ NumeroTelefono +"</th>"+
                   	"<td class='tabledit-view-mode'><span class='tabledit-span'>"+ Nome +"</span>"+
                   		"<input id='Nome"+ NumeroTelefono +"' class='tabledit-input form-control input-sm' type='text' name='Data' style='display:none;' value='"+ Nome +"'>"+
                    "</td>"+
                    "<td class='tabledit-view-mode'><span class='tabledit-span'>"+ Cognome +"</span>"+
                    	"<input id='Cognome"+ NumeroTelefono +"' class='tabledit-input form-control input-sm' type='text' name='Data' style='display:none;' value='"+ Cognome +"'>"+
                    "</td>"+
                    "<td class='tabledit-view-mode'><span class='tabledit-span'>"+ Mail +"</span>"+
                    	"<input id='Mail"+ NumeroTelefono +"' class='tabledit-input form-control input-sm' type='text' name='Data' style='display:none;' value='"+ Mail +"'>"+
                    "</td>"+
                    "<td class='tabledit-view-mode'><span class='tabledit-span'>"+ Indirizzo +"</span>"+
                    	"<input id='Indirizzo"+ NumeroTelefono +"' class='tabledit-input form-control input-sm' type='text' name='Data' style='display:none;' value='"+ Indirizzo +"'>"+
                    "</td>"+
                        "<td class='tabledit-view-mode'><span class='tabledit-span'>"+ Admin +"</span>"+
	                        "<select id='AdminSelect"+ NumeroTelefono +"' class='tabledit-input form-control input-sm' name='Stato' style='display:none;' disabled='' style='display:none;'>";
	                        	if(Amministratore)
	                        	{
	                        		row = row + "<option value='Si' selected='selected'>Si</option>"+
	 					           	"<option value='No'>No</option>";
	                        	}
	                        	else
	                        	{
	                        		row = row +"<option value='Si'>Si</option>"+
	 					           	"<option value='No' selected='selected'>No</option>";
	                        	}
					           
					        row = row + "</select>"+
                        "</td>"+
                        "<td class='tabledit-view-mode'><span class='tabledit-span'>"+ Confirm +"</span>"+
                              "<select id='ConfirmSelect"+ NumeroTelefono +"' class='tabledit-input form-control input-sm' name='Asporto' style='display:none;' disabled='' style='display:none;'>";
						        if(Confermato)
	                        	{
	                        		row = row + "<option value='Si' selected='selected'>Si</option>"+
	 					           	"<option value='No'>No</option>";
	                        	}
	                        	else
	                        	{
	                        		row = row +"<option value='Si' selected='selected'>Si</option>"+
	 					           	"<option value='No' selected='selected'>No</option>";
	                        	}
						           
						      "</select>"+
                         "</td>"+
                    "</tr>";                                             
    	
    	rows = rows + row; 
    	    	
    	
    	index++;
    }
	
	rows = rows + "</tbody></table>";
	
	$('#paginationTable').text("");
	
	
	pagin =  "<li class='footable-page-nav disabled' data-page='first'>" +
				"<a class='footable-page-link' href='#'>«</a>" +
			"</li>" +
			"<li class='footable-page-nav disabled' data-page='prev'>" +
				"<a class='footable-page-link' href='#'>‹</a>" +
			"</li>";
	
	var Filter = "";
	if($("#OrdersPanelAll").hasClass('active'))
		Filter = "All";
	else if($("#OrdersPanelInAttesa").hasClass('active'))
		Filter = "In Attesa";
	else if($("#OrdersPanelConfermati").hasClass('active'))
		Filter = "Confermati";
	else
		Filter = "All";
	
	
	for(var i=0; i<nPage; i++)
	{
		if((i+1) == pageNumber)
		{
			
			
			pagin = pagin + "<li class='footable-page visible active' data-page='" + (i+1) +"'>" +
								"<a class='footable-page-link' id='page"+ (i+1) +"' onclick=loadUserTable(\"" + Filter+ "\","+ (i+1)+")>" + (i+1) +"</a>" +
							"</li>";
		}
		else
		{
			
			pagin = pagin + "<li class='footable-page visible' data-page='" + (i+1) +"'>" +
								"<a class='footable-page-link' id='page"+ (i+1) +"' onclick=loadUserTable(\"" + Filter + "\","+ (i+1) +") >" + (i+1) +"</a>" +
							"</li>";
		}
	}
	
	pagin = pagin + "<li class='footable-page-nav' data-page='next'>" +
						"<a class='footable-page-link' href='#'>›</a>" +
					"</li>" +
					"<li class='footable-page-nav' data-page='last'>" +
						"<a class='footable-page-link' href='#'>»</a>" +
					"</li>";
	
	
	$('#paginationTable').append(pagin);
	

	$('#tablediv').append(rows);
	
	
	
	$('#usertable').Tabledit({
    	
        columns: {

          identifier: [0, 'id'],

          editable: []

      }

	});
	
	$("#usertable .tabledit-edit-button").on("click", function(){
		
		var $row = $(this).closest("tr");  
		var $id = $row.find("#NumeroTelefono").text(); // Find the text
	});
	
	$("#usertable .tabledit-save-button").on("click", function(){
		var $row = $(this).closest("tr");  
		var $id = $row.find("#NumeroTelefono").text(); // Find the text
		
		var nome = $("#Nome"+$id).val();
		var cognome = $("#Cognome"+$id).val();
		var mail = $("#Mail"+$id).val();
		var indirizzo = $("#Indirizzo"+$id).val();
		var admin = $("#AdminSelect"+$id).val();
		var confermato = $("#ConfirmSelect"+$id).val();

		
		AdminBool = false;
		ConfirmBool = false;
		if(admin == "Si")
			AdminBool = true;
		if(confermato == "Si")
			ConfirmBool = true;
		updateUserWithPar($id, nome, cognome, mail, indirizzo, AdminBool, ConfirmBool, false);
		
	});
	
	$("#usertable .tabledit-confirm-button").on("click", function(){
		var $row = $(this).closest("tr");  
		var $id = $row.find("#NumeroTelefono").text(); // Find the text
		
		var nome = $("#Nome"+$id).val();
		var cognome = $("#Cognome"+$id).val();
		var mail = $("#Mail"+$id).val();
		var indirizzo = $("#Indirizzo"+$id).val();
		var admin = $("#AdminSelect"+$id).val();
		var confermato = $("#ConfirmSelect"+$id).val();
		AdminBool = false;
		ConfirmBool = false;
		if(admin == "Si")
			AdminBool = true;
		if(confermato == "Si")
			ConfirmBool = true;
		updateUserWithPar($id, nome, cognome, mail, indirizzo, AdminBool, ConfirmBool, true);

		
		
	});
	
	
}

function updateUserWithPar(numerotelefono, nome, cognome, mail, indirizzo, admin, confermato, disabilitato)
{
	$.get("/Restaurant/servlet/UpdateUser?NumeroTelefono="+numerotelefono+"&Nome="+nome+"&Cognome="+cognome+"&Mail="+mail+"&Indirizzo="+indirizzo+"&Amministratore="+admin+"&Confermato="+confermato+"&Disabilitato="+disabilitato , function(data) {
		
		if(data != "Ok")
			alert("error");
		loadUsersTable("All", 1);
	});
}



//Prodotti Dashboard

function showAllProducts(pageNumber)
{
	
	$.get("/Restaurant/servlet/AllProducts" , function(data) {
		
		var array = JSON.parse(data);

		
		$('#orderproducttablediv').text("");
		
		
		var rows = "<table class='table table-striped table-bordered' id='orderproducttable'>"+
					    "<thead>"+
					        "<tr>"+
					            "<th>ID</th>"+
					            "<th>Nome</th>"+
					            "<th>Costo</th>"+
					            "<th>Tipo</th>"+
					        "</tr>"+
					    "</thead>"+
					    "<tbody id='orderproductstablebody'>";
		
		
		
    	var nPage = array.length/6;
		var index = -1;
		for (var i=0; index<array.length && i<6; i++)
    	{

			if(index == -1)
			{
				if(pageNumber == 1)
				{
					index = (i*pageNumber);
				}else
				{
					index = ((i*pageNumber)-(6+i))+(pageNumber*6);
				}
			}

    		var product = array[index];
    		var idProdotto = product.id;
    		var Nome = product.Name;
    		var Prezzo = product.Price;
    		var Tipo = product.Type;
    		var Quantita = product.Quantity;
    		var IngredientsArray = product.Ingredients;
    		
			var type = "<option value='"+ Tipo +"'>"+ Tipo +"</option>";

			$.ajax({
				url: "/Restaurant/servlet/AllTypeOfProduct",
				type: 'get',
				async: false,
				success: function(data) {
					var array = JSON.parse(data);
					var row;
					
					for (var k=0; k<array.length; k++)
					{	
						if(array[k].Tipo != Tipo)
							row = row + "<option value='"+ array[k].Tipo +"'>"+ array[k].Tipo +"</option>";
					}
					type = type + row; 
				} 
			 });
			 
			

    		var row = "<tr onclick='showOrderProductIngredients("+ idProdotto +")'>"+
           	"<th scope='row' id='idIngredienteTable'>"+ idProdotto +"</th>"+
                "<td class='tabledit-view-mode'><span class='tabledit-span'>"+ Nome +"</span>"+
					"<input class='tabledit-input form-control input-sm' id='nome"+ idProdotto +"' type='text' name='Nome' style='display:none;' value='"+ Nome +"'>"+
                "</td>"+
                "<td class='tabledit-view-mode'><span class='tabledit-span'>"+ Prezzo +"</span>"+
                      "<input class='tabledit-input form-control input-sm' id='costo"+ idProdotto +"' type='text' name='Costo' style='display:none;' value='"+ Prezzo +"'>"+
                "</td>"+
                "<td class='tabledit-view-mode'><span class='tabledit-span'>"+ Tipo +"</span>"+
                "<select id='tipo" + idProdotto +"' class='tabledit-input form-control input-sm' name='Tipo' disabled='' style='display:none;'>"+
		          type+
		        "</select>"+
                "</td>"+
            "</tr>";   
    		
        	rows = rows + row; 
        	index++;
        	
    		
        }
    	rows = rows + "</tbody></table>";
    	
    	$('#orderproducttablediv').append(rows);
				
		
		$('#paginationTable').text("");
	
	
		pagin =  "<li class='footable-page-nav disabled' data-page='first'>" +
					"<a class='footable-page-link' href='#'>«</a>" +
				"</li>" +
				"<li class='footable-page-nav disabled' data-page='prev'>" +
					"<a class='footable-page-link' href='#'>‹</a>" +
				"</li>";
				for(var i=0; i<nPage; i++)
				{
					if((i+1) == pageNumber)
					{
						
						
						pagin = pagin + "<li class='footable-page visible active' data-page='" + (i+1) +"'>" +
											"<a class='footable-page-link' id='page"+ (i+1) +"' onclick=showAllProducts("+ (i+1)+")>" + (i+1) +"</a>" +
										"</li>";
					}
					else
					{
						
						pagin = pagin + "<li class='footable-page visible' data-page='" + (i+1) +"'>" +
											"<a class='footable-page-link' id='page"+ (i+1) +"' onclick=showAllProducts(" + (i+1) +") >" + (i+1) +"</a>" +
										"</li>";
					}
				}
				
		pagin = pagin + "<li class='footable-page-nav' data-page='next'>" +
							"<a class='footable-page-link' href='#'>›</a>" +
						"</li>" +
						"<li class='footable-page-nav' data-page='last'>" +
							"<a class='footable-page-link' href='#'>»</a>" +
						"</li>";		
				
				
		$('#paginationTable').append(pagin);


    	$('#orderproducttable').Tabledit({
        	
            columns: {

              identifier: [0, 'id'],

              editable: []

          }
    	});
    	
    	$("#orderproducttable .tabledit-edit-button").on("click", function(){
    		
    		var $row = $(this).closest("tr");  
    		var $id = $row.find("#idIngredienteTable").text(); // Find the text
    	});
		
    	$("#orderproducttable .tabledit-save-button").on("click", function(){
    		var $row = $(this).closest("tr");  
    		var $id = $row.find("#idIngredienteTable").text(); // Find the text
    		
    		var nome = $("#nome"+$id).val();
			var costo = $("#costo"+$id).val();
			var tipo = $("#tipo"+$id).val();
			

    		updateProduct($id, nome, costo, tipo);
    		//alert(costo);
    		
    	});
    	
    	$("#orderproducttable .tabledit-confirm-button").on("click", function(){
    		var $row = $(this).closest("tr");  
    		var $id = $row.find("#idOrdineTable").text(); // Find the text
    		alert("Modifica: " + $id ); 
    		
    		
    		showAllProducts(pageNumber);
    		
    	});
    	
	});
		
}

function showIngredientOfProduct()
{
	$('#ingredientTableDiv').text("");

	
	//var Nome ="test";
	//var Prezzo = "as";
	
	var rows = "<table class='table table-striped table-bordered' name='ingredientsOfProductTable' id='ingredientsOfProductTable'>"+
				    "<thead>"+
				        "<tr>"+
				            "<th>Ingrediente</th>"+
				            "<th>Costo</th>"+
				        "</tr>"+
				    "</thead>"+
				    "<tbody id='ingredientsOfProductBody'>";
				    
	
	/*
	var row = "<tr>"+
        "<td class='tabledit-view-mode'><span class='tabledit-span'>"+ Nome +"</span>"+
        "</td>"+
        "<td class='tabledit-view-mode'><span class='tabledit-span'>"+ Prezzo +"</span>"+
        "</td>"+
    "</tr>";   
	rows = rows + row;
	*/
	rows = rows + "</tbody></table>";
	

	$('#ingredientTableDiv').append(rows);


	
}

function addIngredient()
{
	var id = $("#ingredientiSelect option:selected").val();
	var Nome = $("#ingredientiSelect option:selected").text();
	var Prezzo = $("#PrezzoIngrediente").text();
	var presente = false;
	$('#ingredientsOfProductTable tr').each(function() 
	{
			var row = $(this);
			if(row.find("td:eq(0)").text() == Nome)
				presente = true;
			//alert(row.find("td:eq(0)").text());
	});

	
	
	var cButton = "<td style='white-space: nowrap; width: 1%;'><div class='tabledit-toolbar btn-toolbar' style='text-align: left;'>"+
		    "<div class='btn-group btn-group-sm' style='float: none;'><button class='tabledit-delete-button btn btn-danger waves-effect waves-light' style='float: none;margin: 5px;' type='button'>"+
		    "<span class='icofont icofont-ui-delete'></span></button></div>"+
		    "<button class='tabledit-restore-button btn btn-sm btn-warning' style='display: none; float: none;' type='button'>Restore</button>"+
		"</div></td>";
	
	var row = "<tr>"+
	    "<td class='tabledit-view-mode'  id='name'><span class='tabledit-span' name='NomeIngrediente' id='NomeIngrediente'>"+ Nome +"</span><input type='hidden' id='NomeIng' name='NomeIng' value="+ id +">"+
	    "</td>"+
	    "<td class='tabledit-view-mode' id='price'><span class='tabledit-span'>"+ Prezzo +"</span>"+
	    "</td>"+
	    cButton;
	if(!presente)
	{
		$('#ingredientsOfProductBody').append(row);
		
		
		
		$("#ingredientsOfProductTable .tabledit-delete-button").on("click", function(){
			
			var $row = $(this).closest("tr"); 
			$row.remove();
			//var $id = $row.find("#idIngredienteTable").text(); // Find the text
			//alert("Modifica: " + $id ); 
		});
	}
	else
		alert("Ingrediente già inserito!");
}

function loadIngredientsTable()
{
	$('#tableIngredientsDiv').text("");
	
	var rows = "<table class='table table-striped table-bordered' id='ingredientsProductsListTable'>"+
    "<thead>"+
        "<tr>"+
            "<th>Nome Ingrediente</th>"+
            "<th>Costo</th>"+
        "</tr>"+
    "</thead>"+
    "<tbody id='ingredientstablebody'>";
	
	
	var c2 = "";
	$.ajax({
        url: "/Restaurant/servlet/AllIngredients",
        type: 'get',
        async: false,
        success: function(data) {
        	var array = JSON.parse(data);
        	
        	
        	for (var k=0; k<array.length; k++)
            {
        		var ing = array[k];
        		
        		c2 = c2 + "<option value='"+ ing.idIngrediente +"'>"+ ing.Nome +"</option>";
        		
            }
        	
        	var row = "<tr>"+
           	//"<th scope='row' id='idIngredienteTable'>"+ ing.idIngrediente +"</th>"+
                "<td class='tabledit-view-mode'>"+
           			"<select id='ingredientiSelect' class='tabledit-input form-control input-sm' onchange='refreshIngredientProductPrice("+ ing.idIngrediente +")' name='Nome'>"+ c2 +
           			
           			"</select>"+
                "</td>"+
                "<td class='tabledit-view-mode'><span class='tabledit-span' id='PrezzoIngrediente' >"+ ing.Costo +"</span>"+
                	//"<a id='PrezzoIngrediente"+ ing.idIngrediente+"'>"+ ing.Costo +" </a>"
                	//"<input id='PrezzoIngrediente"+ ing.idIngrediente+"' class='tabledit-input form-control input-sm' style='display:none;' type='text' name='Costo' value='"+ ing.Costo +"'>"+
                "</td>"+
            "</tr>";  
    		
        	rows = rows + row;
        } 
     });
	
	rows = rows + "</tbody></table>";
	
	$('#tableIngredientsDiv').append(rows);
	
}
function refreshIngredientProductPrice(idIngrediente)
{
	//alert($("#ingredientiSelect"+idIngrediente ).val());
	var ingSelect = $("#ingredientiSelect").val();
	$.get("/Restaurant/servlet/IngredientById?idIngrediente="+ingSelect , function(data) {
		
		obj = JSON.parse(data);
		
		
		$("#PrezzoIngrediente").text(obj.Prezzo);
	});
}

function loadTypeProducts()
{
	$('#selectTypeDiv').text("");

	var rows = "<select name='TipoSelect' class='tabledit-input form-control'>";
	rows = rows + "<option value='null'>Seleziona la tipologia</option>";

	$.ajax({
        url: "/Restaurant/servlet/AllTypeOfProduct",
        type: 'get',
        async: false,
        success: function(data) {
        	var array = JSON.parse(data);
        	var row;
        	
        	for (var k=0; k<array.length; k++)
            {
        		
        		
        		row = row + "<option value='"+ array[k].Tipo +"'>"+ array[k].Tipo +"</option>";
        		
            } 
    		
        	rows = rows + row;
        } 
	 });
	 
	 rows = rows + "</select>";

	 $('#selectTypeDiv').append(rows);
}

function updateProduct(id, nome, costo, tipo)
{
	//alert(costo);
	$.get("/Restaurant/servlet/UpdateProduct?idProdotto="+id+"&Nome="+nome+"&Costo="+costo+"&Tipo="+tipo+"&ImageURL=null" , function(data) {
		
		if(data != "Ok")
			alert("error");
	});
}

//Locale Dashboard

function showLocalInfo()
{
	$.get("/Restaurant/servlet/LocalInfo", function(data) {
        var obj = JSON.parse(data);
        //$('#logo').attr("src", obj.LogoURL);
        $('#nome').val(obj.Name);
        $("#indirizzo").val(obj.Address);
        $("#mail").val(obj.Mail);
        $("#numero").val(obj.Telephone);
        
        if(obj.Active == true || obj.Active == "true")
        {	
        	$('#switchVisible').attr('checked', true);
        	$('#switchVisible').val("true");
        }
       	else
       	{
       		$('#switchVisible').attr('checked', false);
        	$('#switchVisible').val("false");
        	
       	}
        $("#switchVisible").on('change', function() {
		    if ($(this).is(':checked')) {
	        	$('#switchVisible').val("true");

		    }
		    else {
	        	$('#switchVisible').val("false");

		    }
		});
    });
}

//Ingredienti Dashboard


function showAllIngredients1(pageNumber)
{
	$.get("/Restaurant/servlet/AllIngredients" , function(data) {
		
		var array = JSON.parse(data);
		
		$('#orderproducttablediv').text("");
		
		
		var rows = "<table class='table table-striped table-bordered' id='orderproducttable'>"+
					    "<thead>"+
					        "<tr>"+
					            "<th>ID</th>"+
					            "<th>Nome</th>"+
					            "<th>Costo</th>"+
					        "</tr>"+
					    "</thead>"+
					    "<tbody id='orderproductstablebody'>";
		
		
		
    	var nPage = array.length/6;
		var index = -1;
		for (var i=0; index<array.length && i<6; i++)
    	{

			if(index == -1)
			{
				if(pageNumber == 1)
				{
					index = (i*pageNumber);
				}else
				{
					index = ((i*pageNumber)-(6+i))+(pageNumber*6);
				}
			}

    		var product = array[index];
    		var idProdotto = product.idIngrediente;
    		var Nome = product.Nome;
    		var Prezzo = product.Costo;
    		

    		var row = "<tr onclick='showOrderProductIngredients("+ idProdotto +")'>"+
           	"<th scope='row' id='idIngredienteTable'>"+ idProdotto +"</th>"+
                "<td class='tabledit-view-mode'><span class='tabledit-span'>"+ Nome +"</span>"+
					"<input class='tabledit-input form-control input-sm' id='nome"+ idProdotto +"' type='text' name='Nome' style='display:none;' value='"+ Nome +"'>"+
                "</td>"+
                "<td class='tabledit-view-mode'><span class='tabledit-span'>"+ Prezzo +"</span>"+
                      "<input class='tabledit-input form-control input-sm' id='costo"+ idProdotto +"' type='text' name='Costo' style='display:none;' value='"+ Prezzo +"'>"+
                "</td>"+
                
            "</tr>";   
    		
        	rows = rows + row; 
        	index++;
        	
    		
        }
    	rows = rows + "</tbody></table>";
    	
    	$('#orderproducttablediv').append(rows);
				
		
		$('#paginationTable').text("");
	
	
		pagin =  "<li class='footable-page-nav disabled' data-page='first'>" +
					"<a class='footable-page-link' href='#'>«</a>" +
				"</li>" +
				"<li class='footable-page-nav disabled' data-page='prev'>" +
					"<a class='footable-page-link' href='#'>‹</a>" +
				"</li>";
				for(var i=0; i<nPage; i++)
				{
					if((i+1) == pageNumber)
					{
						
						
						pagin = pagin + "<li class='footable-page visible active' data-page='" + (i+1) +"'>" +
											"<a class='footable-page-link' id='page"+ (i+1) +"' onclick=showAllIngredients1("+ (i+1)+")>" + (i+1) +"</a>" +
										"</li>";
					}
					else
					{
						
						pagin = pagin + "<li class='footable-page visible' data-page='" + (i+1) +"'>" +
											"<a class='footable-page-link' id='page"+ (i+1) +"' onclick=showAllIngredients1(" + (i+1) +") >" + (i+1) +"</a>" +
										"</li>";
					}
				}
				
		pagin = pagin + "<li class='footable-page-nav' data-page='next'>" +
							"<a class='footable-page-link' href='#'>›</a>" +
						"</li>" +
						"<li class='footable-page-nav' data-page='last'>" +
							"<a class='footable-page-link' href='#'>»</a>" +
						"</li>";		
				
				
		$('#paginationTable').append(pagin);
		

    	$('#orderproducttable').Tabledit({
        	
            columns: {

              identifier: [0, 'id'],

              editable: []

          }
    	});
    	
    	$("#orderproducttable .tabledit-edit-button").on("click", function(){
    		
    		var $row = $(this).closest("tr");  
    		var $id = $row.find("#idIngredienteTable").text(); // Find the text
    	});
		
    	$("#orderproducttable .tabledit-save-button").on("click", function(){
    		var $row = $(this).closest("tr");  
    		var $id = $row.find("#idIngredienteTable").text(); // Find the text
    		
    		var nome = $("#nome"+$id).val();
			var costo = $("#costo"+$id).val();
			

    		updateIngrediente($id, nome, costo);
    		//alert(costo);
    		
    	});
    	
    	$("#orderproducttable .tabledit-confirm-button").on("click", function(){
    		var $row = $(this).closest("tr");  
    		var $id = $row.find("#idOrdineTable").text(); // Find the text
    		alert("Modifica: " + $id ); 
    		
    		
    		showAllIngredients1(pageNumber);
    		
		});
		
    	
	});
}
function updateIngrediente(id, nome, costo)
{
	$.get("/Restaurant/servlet/UpdateIngrediente?idIngrediente="+id+"&Nome="+nome+"&Costo="+costo, function(data) {
		
		if(data != "Ok")
			alert("error");
	});
}

//Landing Page

function createLocal()
{
	alert(1);
}

