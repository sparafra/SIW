<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>FoodiFy</title>
    <!-- Meta -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Landing page template for creative dashboard">
    <meta name="keywords" content="Landing page template">
    <!-- Favicon icon -->
    <link rel="icon" href="assets\logos\favicon.ico" type="image/png" sizes="16x16">
    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}\Dashboard\landingpage\assets\css\bootstrap.min.css" rel="stylesheet" type="text/css" media="all">
    <!-- Font -->
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,700" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Poppins:400,300,500,700,600" rel="stylesheet" type="text/css">
    <!-- Animate CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}\Dashboard\landingpage\assets\css\animate.css">
    <!-- Owl Carousel -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}\Dashboard\landingpage\assets\css\owl.carousel.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}\Dashboard\landingpage\assets\css\owl.theme.css">
    <!-- Magnific Popup -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}\Dashboard\landingpage\assets\css\magnific-popup.css">
    <!-- Full Page Animation -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}\Dashboard\landingpage\assets\css\animsition.min.css">
    <!-- Ionic Icons -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}\Dashboard\landingpage\assets\css\ionicons.min.css">
    <!-- Main Style css -->
    <link href="${pageContext.request.contextPath}\Dashboard\landingpage\assets\css\style.css" rel="stylesheet" type="text/css" media="all">
     <!-- Icon-Font -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/font-awesome/font-awesome/css/font-awesome.min.css" type="text/css">
    <!--<link rel="stylesheet" href="../../assets/styles/main.css" type="text/css"> -->
    
    
</head>

<body>
	
	
	
	<% String refererURL=request.getHeader("referer"); %>
	
    <div class="wrapper animsition" data-animsition-in-class="fade-in" data-animsition-in-duration="1000" data-animsition-out-class="fade-out" data-animsition-out-duration="1000">
        <div class="container">
             <nav class="navbar navbar-expand-lg navbar-light navbar-default navbar-fixed-top" role="navigation">
                <div class="container">
                    <a class="navbar-brand page-scroll" href="#main"><img src="${pageContext.request.contextPath}\Dashboard\landingpage\assets\logos\logo.png" alt="adminity Logo"></a>
                    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul class="navbar-nav mr-auto">
                        </ul>
                        <ul class="navbar-nav my-2 my-lg-0">
                            <li class="nav-item">
                                <a class="nav-link page-scroll" href="#main">Home</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link page-scroll" href="#services">Registrati</a>
                            </li>
                             <li class="nav-item">
                                <a class="nav-link page-scroll" href="${pageContext.request.contextPath}/ChooseLocal.html">Scegli un Locale</a>
                            </li>
                            
                        </ul>
                    </div>
                </div>
            </nav>
        </div>
        <div class="main" id="main">
            <!-- Main Section-->
            <div class="hero-section app-hero">
                <div class="container">
                    <div class="hero-content app-hero-content text-center">
                        <div class="row justify-content-md-center">
                            <div class="col-md-10">
                                <p class="wow fadeInUp" data-wow-delay="0.2s">
                                    ${requestScope.message}
                                </p>
                                <a class="btn btn-primary btn-action" href="<%=refererURL%>" data-wow-delay="0.2s" href="#">Refresh</a>
                            </div>
                            <div class="col-md-12">
                                <div class="hero-image">
                                    <img class="img-fluid" src="${pageContext.request.contextPath}\Dashboard\landingpage\assets\images\app_hero_1.png" alt="">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            

            
            <div class="footer">
                <div class="container">
                    <div class="col-md-12 text-center">
                        <img src="${pageContext.request.contextPath}\Dashboard\landingpage\assets\logos\logo.png" alt="Adminty Logo">
                        <ul class="footer-menu">
                            
                        </ul>
                        <div class="footer-text">
                            <p>
                                2019 All rights reserved. By Francesco Sparano & Diego De Bartolo. 
                            </p>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Scroll To Top -->
            <a id="back-top" class="back-to-top page-scroll" href="#main">
                <i class="ion-ios-arrow-thin-up"></i>
            </a>
            <!-- Scroll To Top Ends-->
        </div>
        <!-- Main Section -->
    </div>
    <!-- Wrapper-->

    <!-- Jquery and Js Plugins -->
    <script type="text/javascript" src="${pageContext.request.contextPath}\Dashboard\landingpage\assets\js\jquery-2.1.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}\Dashboard\landingpage\assets\js\bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}\Dashboard\landingpage\assets\js\plugins.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}\Dashboard\landingpage\assets\js\menu.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}\Dashboard\landingpage\assets\js\custom.js"></script>
    
    <script type="text/javascript" src="${pageContext.request.contextPath}\assets\js\loader_info.js" ></script>
    
    
    
</body>

</html>