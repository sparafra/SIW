<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>FoodiFy</title>
    <!-- HTML5 Shim and Respond.js IE10 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 10]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
      <![endif]-->
    <!-- Meta -->
	<meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimal-ui">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="#">
    <meta name="keywords" content="Admin , Responsive, Landing, Bootstrap, App, Template, Mobile, iOS, Android, apple, creative app">
    <meta name="author" content="#">
    
    
    
    
    <!-- Favicon icon -->
    <link rel="icon" href="${pageContext.request.contextPath}\Dashboard\files\assets\images\favicon.ico" type="image/x-icon">
    <!-- Google font-->
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:400,600" rel="stylesheet">
    <!-- Required Fremwork -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}\Dashboard\files\bower_components\bootstrap\css\bootstrap.min.css">
    <!-- feather Awesome -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}\Dashboard\files\assets\icon\feather\css\feather.css">
    <!-- Style.css -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}\Dashboard\files\assets\css\style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}\Dashboard\files\assets\css\jquery.mCustomScrollbar.css">
     <!--foo table Grid-->
     <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}\Dashboard\files\assets\pages\foo-table\css\footable.bootstrap.min.css">
     <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}\Dashboard\files\assets\pages\foo-table\css\jquery.dataTables.min.css">
     <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}\Dashboard\files\assets\pages\foo-table\css\footable.standalone.min.css">
    <!-- themify-icons line icon -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}\Dashboard\files\assets\icon\themify-icons\themify-icons.css">
    <!-- ico font -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}\Dashboard\files\assets\icon\icofont\css\icofont.css">
    
     <!-- Font awesome star css -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}\Dashboard\files\bower_components\font-awesome\css\font-awesome.min.css">
    <!-- Font awesome star css -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}\Dashboard\files\bower_components\jquery-bar-rating\css\fontawesome-stars.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}\Dashboard\files\bower_components\jquery-bar-rating\css\fontawesome-stars-o.css">

	<!-- jquery file upload Frame work -->
    <link href="${pageContext.request.contextPath}\Dashboard\files\assets\pages\jquery.filer\css\jquery.filer.css" type="text/css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}\Dashboard\files\assets\pages\jquery.filer\css\themes\jquery.filer-dragdropbox-theme.css" type="text/css" rel="stylesheet">
    
	 <!-- <link rel="stylesheet" href="../../assets/styles/main.css" type="text/css"> -->
	

</head>

<body>
	<%@ page import="model.Restaurant" %>
	<%@ page import="database.*" %>
	
	<%! Restaurant restaurant;  %>
	<%! DBConnection dbConnection; %>
	<%! RestaurantDaoJDBC RestaurantDao;%>
	<%! Restaurant rest; %>
	
	<%  restaurant = (Restaurant)session.getAttribute("Restaurant");%>
	<%  dbConnection = new DBConnection(); %>
	<%  RestaurantDao = new RestaurantDaoJDBC(dbConnection); %>
	<%  if(restaurant != null)rest = RestaurantDao.findByPrimaryKeyJoin(restaurant.getId()); %>
	<% String pathWebcontent=request.getContextPath(); %>
	<% String refererURL=request.getHeader("referer"); %>
	
    <!-- Pre-loader start -->
    <div class="theme-loader">
        <div class="ball-scale">
            <div class='contain'>
                <div class="ring">
                    <div class="frame"></div>
                </div>
                <div class="ring">
                    <div class="frame"></div>
                </div>
                <div class="ring">
                    <div class="frame"></div>
                </div>
                <div class="ring">
                    <div class="frame"></div>
                </div>
                <div class="ring">
                    <div class="frame"></div>
                </div>
                <div class="ring">
                    <div class="frame"></div>
                </div>
                <div class="ring">
                    <div class="frame"></div>
                </div>
                <div class="ring">
                    <div class="frame"></div>
                </div>
                <div class="ring">
                    <div class="frame"></div>
                </div>
                <div class="ring">
                    <div class="frame"></div>
                </div>
            </div>
        </div>
    </div>
    <!-- Pre-loader end -->
    <div id="pcoded" class="pcoded">
        <div class="pcoded-overlay-box"></div>
        <div class="pcoded-container navbar-wrapper">

            <nav class="navbar header-navbar pcoded-header">
                <div class="navbar-wrapper">

                    <div class="navbar-logo">
                        <a class="mobile-menu" id="mobile-collapse" href="#!">
                            <i class="feather icon-menu"></i>
                        </a>
                        <a href="index.htm">
                        <%System.out.println(pathWebcontent+"/"+rest.getLogoURL());%>
                            <img id="logo" class="img-fluid" src="<%=pathWebcontent%>/<%=rest.getLogoURL()%>" alt="Theme-Logo">
                        </a>
                        <a class="mobile-options">
                            <i class="feather icon-more-horizontal"></i>
                        </a>
                    </div>

                   
                </div>
            </nav>

            

            <div class="pcoded-main-container">
                <div class="pcoded-wrapper">
                    <nav class="pcoded-navbar">
                        <div class="pcoded-inner-navbar main-menu">
                            <div class="pcoded-navigatio-lavel">Navigation</div>
                            <ul class="pcoded-item pcoded-left-item">
                                <li class="pcoded-hasmenu active pcoded-trigger">
                                    <a href="index.html">
                                        <span class="pcoded-micon"><i class="feather icon-home"></i></span>
                                        <span class="pcoded-mtext">Dashboard</span>
                                    </a>
                                </li>
                                
                                <li class="">
                                    <a href="Ordini.html" id="OrdiniMenu">
                                        <span class="pcoded-micon"><i class="feather icon-shopping-cart"></i></span>
                                        <span class="pcoded-mtext">Ordini</span>
                                    </a>
                                </li>
                                <li class="pcoded-hasmenu">
                                    <a href="javascript:void(0)">
                                        <span class="pcoded-micon"><i class="feather icon-layers"></i></span>
                                        <span class="pcoded-mtext">Prodotti</span>
                                    </a>
                                    <ul class="pcoded-submenu">
                                        <li class=" ">
                                            <a href="#">
                                                <span class="pcoded-mtext">Prodotti</span>
                                            </a>
                                        </li>
                                        

                                    </ul>
                                    <ul class="pcoded-submenu">
                                        <li class=" ">
                                            <a href="Ingredienti.html">
                                                <span class="pcoded-mtext">Ingredienti</span>
                                            </a>
                                        </li>
                                        

                                    </ul>
                                </li>
                                <li class="">
                                    <a href="Utenti.html" id="UtentiMenu">
                                        <span class="pcoded-micon"><i class="feather icon-users"></i></span>
                                        <span class="pcoded-mtext">Utenti</span>
                                        <span class="pcoded-badge label label-danger">100+</span>
                                    </a>
                                </li>
                                <li class="">
                                    <a href="Locale.html">
                                        <span class="pcoded-micon"><i class="feather icon-briefcase"></i></span>
                                        <span class="pcoded-mtext">Locale</span>
                                    </a>
                                </li>
                            </ul>
                            
                                                        
                            <div class="pcoded-navigatio-lavel">Grafica</div>
                            <ul class="pcoded-item pcoded-left-item">
                                <li class="pcoded-hasmenu ">
                                    <a href="javascript:void(0)">
                                        <span class="pcoded-micon"><i class="feather icon-sidebar"></i></span>
                                        <span class="pcoded-mtext">Pagina</span>
                                    </a>
                                    <ul class="pcoded-submenu">
                                        <li class="">
                                            <a href="auth-normal-sign-in.htm" target="_blank">
                                                <span class="pcoded-mtext">Home</span>
                                            </a>
                                        </li>
                                        <li class="">
                                            <a href="auth-sign-in-social.htm" target="_blank">
                                                <span class="pcoded-mtext">Chi Siamo</span>
                                            </a>
                                        </li>
                                        <li class="">
                                            <a href="auth-sign-in-social-header-footer.htm" target="_blank">
                                                <span class="pcoded-mtext">Menu</span>
                                            </a>
                                        </li>
                                        <li class="">
                                            <a href="auth-normal-sign-in-header-footer.htm" target="_blank">
                                                <span class="pcoded-mtext">Contatti</span>
                                            </a>
                                        </li>
                                        
                                    </ul>
                                </li>
                                <li>
                                	<a href="../../index.html" >
                                    	<span class="pcoded-micon"><i class="feather icon-power"></i></span>
                                        <span class="pcoded-mtext">Back</span>
                                    </a>
                                </li>
                            </ul>
                            
                        </div>
                    </nav>
                    <div class="pcoded-content">
                        <div class="pcoded-inner-content">
                            <div class="main-body">
                                <div class="page-wrapper">

                                    <div class="page-body">
                                    	<h2>${requestScope.message}</h2>
                                    	
                                    	<div>
                                    		<a class="btn btn-primary btn-block" href="<%=refererURL%>">Refresh</a>
                                    	</div>
                           
                            		</div>
                            
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
		</div>
	</div>
    
    <!-- Required Jquery -->
    <script data-cfasync="false" src="..\..\..\cdn-cgi\scripts\5c5dd728\cloudflare-static\email-decode.min.js"></script><script type="text/javascript" src="${pageContext.request.contextPath}\Dashboard\files\bower_components\jquery\js\jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}\Dashboard\files\bower_components\jquery-ui\js\jquery-ui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}\Dashboard\files\bower_components\popper.js\js\popper.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}\Dashboard\files\bower_components\bootstrap\js\bootstrap.min.js"></script>
    <!-- jquery slimscroll js -->
    <script type="text/javascript" src="${pageContext.request.contextPath}\Dashboard\files\bower_components\jquery-slimscroll\js\jquery.slimscroll.js"></script>
    <!-- modernizr js -->
    <script type="text/javascript" src="${pageContext.request.contextPath}\Dashboard\files\bower_components\modernizr\js\modernizr.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}\Dashboard\files\bower_components\modernizr\js\css-scrollbars.js"></script>
   
    <!-- Chart js -->
    <script type="text/javascript" src="${pageContext.request.contextPath}\Dashboard\files\bower_components\chart.js\js\Chart.js"></script>
     <!-- echart js -->
     <script src="${pageContext.request.contextPath}\Dashboard\files\assets\pages\chart\echarts\js\echarts-all.js" type="text/javascript"></script>
     <!-- Custom js -->
    <script type="text/javascript" src="${pageContext.request.contextPath}\Dashboard\files\assets\pages\chart\echarts\echart-custom.js"></script>
    <!--FOO Table js-->
    <script src="${pageContext.request.contextPath}\Dashboard\files\assets\pages\foo-table\js\footable.min.js"></script>
    <script src="${pageContext.request.contextPath}\Dashboard\files\assets\pages\foo-table\js\foo-table-custom.js"></script>
    
    <!-- Editable-table js -->
    <script type="text/javascript" src="${pageContext.request.contextPath}\Dashboard\files\assets\pages\edit-table\jquery.tabledit.js"></script> 
    <!-- <script type="text/javascript" src="..\files\assets\pages\edit-table\editable.js"></script> -->
    
    <!-- amchart js -->
    <script src="${pageContext.request.contextPath}\Dashboard\files\assets\pages\widget\amchart\amcharts.js"></script>
    <script src="${pageContext.request.contextPath}\Dashboard\files\assets\pages\widget\amchart\serial.js"></script>
    <script src="${pageContext.request.contextPath}\Dashboard\files\assets\pages\widget\amchart\light.js"></script>
    <script src="${pageContext.request.contextPath}\Dashboard\files\assets\js\jquery.mCustomScrollbar.concat.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}\Dashboard\files\assets\js\SmoothScroll.js"></script>
    <script src="${pageContext.request.contextPath}\Dashboard\files\assets\js\pcoded.min.js"></script>
    <!-- custom js -->
    <script src="${pageContext.request.contextPath}\Dashboard\files\assets\js\vartical-layout.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}\Dashboard\files\assets\pages\dashboard\custom-dashboard.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}\Dashboard\files\assets\js\script.min.js"></script>

	<script type="text/javascript" src="${pageContext.request.contextPath}\assets\js\loader_info.js" ></script>

    <!-- rating js -->
    <script type="text/javascript" src="${pageContext.request.contextPath}\Dashboard\files\bower_components\jquery-bar-rating\js\jquery.barrating.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}\Dashboard\files\assets\js\rating.js"></script>

	<!-- Custom js -->
    <script type="text/javascript" src="${pageContext.request.contextPath}\Dashboard\files\assets\pages\form-validation\form-validation.js"></script>
	
	<!-- Validation js -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.8.3/underscore-min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.6/moment.min.js"></script>
   
    <!-- jquery file upload js -->
    <script src="${pageContext.request.contextPath}\Dashboard\Dashboard\files\assets\pages\jquery.filer\js\jquery.filer.min.js"></script>
    <script src="${pageContext.request.contextPath}\Dashboard\files\assets\pages\filer\custom-filer.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}\Dashboard\files\assets\pages\filer\jquery.fileuploads.init.js" type="text/javascript"></script><script type="text/javascript" src="..\files\assets\pages\form-validation\validate.js"></script>
    
    
	<!-- <script type="text/javascript" src="..\files\assets\js\loader_info.js" ></script>-->

<!-- Global site tag (gtag.js) - Google Analytics -->
<script async="" src="https://www.googletagmanager.com/gtag/js?id=UA-23581568-13"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'UA-23581568-13');
</script>
</body>

</html>