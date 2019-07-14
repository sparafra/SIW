<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>

	<meta charset="ISO-8859-1">
	<title>Adminty - Premium Admin Template by Colorlib</title>
	<meta name="description" content="Comming soon page - flat able">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="author" content="Codedthemes">
	<!-- Favicon -->
	<link rel="shortcut icon" href="img\favicon.ico">
    <link rel="stylesheet" href="css\style-v2.css">
    <!-- Modernizr runs quickly on page load for detecting features -->
    <script src="js\modernizr.custom.js"></script>
	
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
	
	<%!
		boolean isInSession(){
			
			if(restaurant != null)
				return true;
			else
				return false;
		}
	
		boolean isOffline(){
		
			
			if(!rest.getActive())
				return true;
			else
				return false;
		}
	%>


    <!-- Loading overlay -->
    <div id="loading" class="dark-back">
        <div class="loading-bar"></div>
        <span class="loading-text opacity-0">So Excited ?</span>
    </div>
    <!-- Informations bar on top of the screen -->
    <div class="info-bar bar-intro opacity-0">
        <div class="row">
            <div class="col-xs-12 col-sm-6 col-lg-6 info-bar-left">
            	<a class="btn btn-primary btn-action" data-wow-delay="0.2s" href="../ChooseLocal.html">Scegli un altro Locale</a>
            </div>
            <div class="col-xs-12 col-sm-6 col-lg-6 info-bar-right">
                <a data-dialog="somedialog" class="action-btn trigger">Click Me !</a>
            </div>
        </div>
    </div>
    <!-- END - Informations bar on top of the screen -->
    <!-- Slider Wrapper -->
    <div id="slider" class="sl-slider-wrapper">
        <div class="sl-slider">
            <!-- SLIDE 1 / Home -->
            <div class="sl-slide bg-1" data-orientation="horizontal" data-slice1-rotation="-25" data-slice2-rotation="-25" data-slice1-scale="2" data-slice2-scale="2">
                <div class="sl-slide-inner">
                    <div class="content-slide">
                        <div class="container">
                        	<% if (isInSession()) { %>
                        		<% if(isOffline()) { System.out.println(rest.getLogoURL());%>
									<img src="../<%=rest.getLogoURL()%>" alt="" class="brand-logo text-intro opacity-0">
								<% } else { %>
									<script type="text/javascript">
									    window.location = "../index.html";
									</script>
								<% } %>
							<% } else { %>
								<script type="text/javascript">
									    window.location = "../ChooseLocal.html";
									</script>
							<% } %>
                            
                        
                            <h1 class="text-intro opacity-0">Coming Soon</h1>
                            <p class="text-intro opacity-0">So Excited !!!<strong> Product Launch</strong> by Sparano & De Bartolo Code.
                                
                            </p>
                            <!-- Text or Icons, as you want :-) / Uncomment the part you need and comment or delete the other one -->
                            <!-- <p class="social-text text-intro opacity-0">
			    					<a href="#" target="_blank">TWITTER</a> / 
			    					<a href="#" target="_blank">FACEBOOK</a> / 
			    					<a href="#" target="_blank">YOUTUBE</a>
			    				</p> -->
                           
                        </div>
                    </div>
                </div>
            </div>
            <!-- END - SLIDE 1 / Home -->
            <!-- SLIDE 2 / About -->
            <div class="sl-slide bg-2" data-orientation="vertical" data-slice1-rotation="10" data-slice2-rotation="-15" data-slice1-scale="1.5" data-slice2-scale="1.5">
                <div class="sl-slide-inner">
                    <div class="content-slide">
                        <div class="container">
                            <h2>Our great Story</h2>
                            <div class="row about-part">
                                <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                    <p>Hi, I'm WAVE and I'm ready to boost your web project by my elegance, exclusive design and animations. I'm ready-to-use, just upload me on your server, add your pictures and edit my texts. Handmade, precisely built with the famous Bootstrap Framework. Salut, je suis WAVE et je suis prêt à booster votre projet web par mon élégance, mon design et mes animations exclusives. Je suis prêt à l'emploi, il suffit de me mettre sur votre serveur.</p>
                                </div>
                                <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                    <p>Ajoutez vos photos et éditer mes textes. Fait à la main, précisément construit avec le célèbre Framework Bootstrap. Hola, soy WAVE y estoy listo para impulsar su proyecto web con mi estilo, mi diseño y mis animaciones exclusivas. Estoy listo, me acaba de poner en su servidor, añadir sus fotos y editar mis textos. Hecho a mano, precisamente, construido con el famoso Framework Bootstrap.</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- END - SLIDE 2 / About -->
            
            <!-- SLIDE 5 / Contact -->
            <div class="sl-slide bg-5" data-orientation="horizontal" data-slice1-rotation="-5" data-slice2-rotation="10" data-slice1-scale="2" data-slice2-scale="1">
                <div class="sl-slide-inner">
                    <div class="content-slide">
                        <div class="container">
                            <h2>Get in touch</h2>
                            <p class="resume">We are here to help you <strong>Tuesday through Saturday</strong>, from 9:00 AM to 10:00 PM.
                                <br> Fill the next online form to get in touch with our friendly support team!</p>
                            <div class="row">
                                <div class="col-xs-12 col-sm-12 col-md-6 col-lg-5 info-contact">
                                    <h3>WAVE Service Department</h3>
                                    <p>If you have any further suggestions, questions or comments, here are the best ways to connect with us!</p>
                                    <br>
                                    <p class="list-info">
                                        <i class="icon ion-ios-telephone"></i> Phone : (+33) 66-1254-611
                                        <br>
                                        <i class="icon ion-ios-email"></i> Email : <a href="..\..\..\..\..\cdn-cgi\l\email-protection.htm#641d0b1116490109050d0824011c05091408014a070b09" class="phone-mail-link"><span class="__cf_email__" data-cfemail="166177607356736e777b667a733875797b">[email&#160;protected]</span></a>
                                        <br>
                                        <i class="icon ion-ios-location"></i> Location : 66 Grand Central, NY 66564, USA
                                    </p>
                                </div>
                                <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 col-lg-offset-1">
                                    <!-- Contact Form -->
                                    <form id="contact-form" name="contact-form" method="POST" data-name="Contact Form">
                                        <div class="row">
                                            <!-- Full name -->
                                            <div class="col-xs-12 col-sm-12 col-lg-6">
                                                <div class="form-group">
                                                    <input type="text" id="name" class="form form-control" placeholder="Your Name*" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Your Name*'" name="name" data-name="Name" required="">
                                                </div>
                                            </div>
                                            <!-- E-mail -->
                                            <div class="col-xs-12 col-sm-12 col-lg-6">
                                                <div class="form-group">
                                                    <input type="email" id="email" class="form form-control" placeholder="Your Email*" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Your Email*'" name="email-address" data-name="Email Address" required="">
                                                </div>
                                            </div>
                                            <!-- Subject -->
                                            <div class="col-xs-12 col-sm-12 col-lg-12">
                                                <div class="form-group">
                                                    <input type="text" id="subject" class="form form-control" placeholder="Regarding..." onfocus="this.placeholder = ''" onblur="this.placeholder = 'Regarding...'" name="subject" data-name="Subject">
                                                </div>
                                            </div>
                                            <!-- Message -->
                                            <div class="col-xs-12 col-sm-12 col-lg-12 no-padding">
                                                <div class="form-group">
                                                    <textarea id="text-area" class="form textarea form-control" placeholder="Your message here... 20 characters Min.*" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Your message here... 20 characters Min.*'" name="message" data-name="Text Area" required=""></textarea>
                                                </div>
                                                <span class="sub-text">* Required fields</span>
                                            </div>
                                        </div>
                                        <!-- Button submit -->
                                        <button type="submit" id="valid-form" class="btn btn-color">Send my Message</button>
                                    </form>
                                    <!-- END - Contact Form -->
                                    <!-- Answer for the contact form is displayed in the next div, do not remove it. -->
                                    <div id="block-answer">
                                        <div id="answer"></div>
                                    </div>
                                    <!-- END - Answer for the contact form is displayed in the next div, do not remove it. -->
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- END - SLIDE 5 / Contact -->
        </div>
        <!-- END - sl-slider -->
        <!-- Arrow nav -->
        <nav id="nav-arrows" class="nav-arrows">
            <span class="nav-arrow-prev">Previous</span>
            <span class="nav-arrow-next">Next</span>
        </nav>
        <!-- END - Arrow nav -->
        <!-- Bottom nav -->
        <nav id="nav-multi-square" class="nav-multi-square nav-intro opacity-0">
            <span class="nav-square-current nav-square">
					<span><i class="fa fa-home"></i>Home</span>
            </span>
            <span class="nav-square">
					<span><i class="fa fa-user-secret"></i>About</span>
            </span>
            
            <span class="nav-square">
					<span><i class="fa fa-commenting-o"></i>Contact</span>
            </span>
            <span class="nav-square"></span>
            <span class="nav-square"></span>
        </nav>
        <!-- END - Bottom nav -->
    </div>
    <!-- END - Slider Wrapper -->
    <!-- Newsletter Popup -->
    <div id="somedialog" class="dialog">
        <div class="dialog__overlay"></div>
        <!-- dialog__content -->
        <div class="dialog__content">
            <div class="header-picture"></div>
            <!-- dialog-inner -->
				<!-- dialog-inner -->
				<div class="dialog-inner">
							
					<h4>Notify Popup Highlight</h4>
							
					<p>Just write the pefect description for your launch product here.... <strong>Codedthemes Product launch in next XX weeks. Enjoy !!!</strong></p>

					<!-- Newsletter Form -->
					<div id="subscribe">

		                <form action="php/notify-me.php" id="notifyMe" method="POST">

		                    <div class="form-group">

		                        <div class="controls">
		                            
		                        	<!-- Field  -->
		                        	<input type="text" id="mail-sub" name="email" placeholder="Click here to write your email" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Your Email Address'" class="form-control email srequiredField">

		                        	<!-- Spinner top left during the submission -->
		                        	<i class="fa fa-spinner opacity-0"></i>

		                            <!-- Button -->
		                            <button class="btn btn-lg submit">Submit</button>

		                            <div class="clear"></div>

		                        </div>

		                    </div>

		                </form>

						<!-- Answer for the newsletter form is displayed in the next div, do not remove it. -->
						<div class="block-message">

							<div class="message">

								<p class="notify-valid">

							</div>

						</div>
						<!-- END - Answer for the newsletter form is displayed in the next div, do not remove it. -->

        			</div>
        			<!-- END - Newsletter Form -->
				</div>
				<!-- END - dialog-inner -->
            <!-- END - dialog-inner -->
            <!-- Cross for closing the Newsletter Popup -->
            <button class="close-newsletter" data-dialog-close=""><i class="icon ion-android-close"></i></button>
        </div>
        <!-- END - dialog__content -->
    </div>
    <!-- END - Newsletter Popup -->
    <!-- Root element of PhotoSwipe, the gallery. Must have class pswp. -->
    <div class="pswp" tabindex="-1" role="dialog" aria-hidden="true">
        <!-- Background of PhotoSwipe. 
	        	It's a separate element as animating opacity is faster than rgba(). -->
        <div class="pswp__bg"></div>
        <!-- Slides wrapper with overflow:hidden. -->
        <div class="pswp__scroll-wrap">
            <!-- Container that holds slides. 
		            PhotoSwipe keeps only 3 of them in the DOM to save memory.
		            Don't modify these 3 pswp__item elements, data is added later on. -->
            <div class="pswp__container">
                <div class="pswp__item"></div>
                <div class="pswp__item"></div>
                <div class="pswp__item"></div>
            </div>
            <!-- Default (PhotoSwipeUI_Default) interface on top of sliding area. Can be changed. -->
            <div class="pswp__ui pswp__ui--hidden">
                <div class="pswp__top-bar">
                    <!--  Controls are self-explanatory. Order can be changed. -->
                    <div class="pswp__counter"></div>
                    <button class="pswp__button pswp__button--close" title="Close (Esc)"></button>
                    <button class="pswp__button pswp__button--share" title="Share"></button>
                    <button class="pswp__button pswp__button--fs" title="Toggle fullscreen"></button>
                    <button class="pswp__button pswp__button--zoom" title="Zoom in/out"></button>
                    <!-- Preloader demo http://codepen.io/dimsemenov/pen/yyBWoR -->
                    <!-- element will get class pswp__preloader - -active when preloader is running -->
                    <div class="pswp__preloader">
                        <div class="pswp__preloader__icn">
                            <div class="pswp__preloader__cut">
                                <div class="pswp__preloader__donut"></div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="pswp__share-modal pswp__share-modal--hidden pswp__single-tap">
                    <div class="pswp__share-tooltip"></div>
                </div>
                <button class="pswp__button pswp__button--arrow--left" title="Previous (arrow left)">
                </button>
                <button class="pswp__button pswp__button--arrow--right" title="Next (arrow right)">
                </button>
                <div class="pswp__caption">
                    <div class="pswp__caption__center"></div>
                </div>
            </div>
        </div>
    </div>
    <!-- END - Root element of PhotoSwipe, the gallery. Must have class pswp. -->
    <!-- //////////////////////\\\\\\\\\\\\\\\\\\\\\\ -->
    <!-- ********** List of jQuery Plugins ********** -->
    <!-- \\\\\\\\\\\\\\\\\\\\\\////////////////////// -->
    <!-- * Libraries jQuery, Easing and Bootstrap - Be careful to not remove them * -->
    <script data-cfasync="false" src="..\..\..\..\..\cdn-cgi\scripts\5c5dd728\cloudflare-static\email-decode.min.js"></script><script src="js\jquery.min.js"></script>
    <script src="js\jquery.easings.min.js"></script>
    <script src="js\bootstrap.min.js"></script>
    <!-- SlitSlider plugin -->
    <script src="js\jquery.ba-cond.min.js"></script>
    <script src="js\jquery.slitslider.js"></script>
    <!-- Newsletter plugin -->
    <script src="js\notifyMe.js"></script>
    <!-- Contact form plugin -->
    <script src="js\contact-me.js"></script>
    <!-- Popup Newsletter Form -->
    <script src="js\classie.js"></script>
    <script src="js\dialogFx.js"></script>
    <!-- PhotoSwipe Core JS file -->
    <script src="js\photoswipe.js"></script>
    <!-- PhotoSwipe UI JS file -->
    <script src="js\photoswipe-ui-default.js"></script>
    <!-- Custom Scrollbar plugin -->
    <script src="js\jquery.mCustomScrollbar.js"></script>
    <!-- Countdown plugin -->
    <script src="js\jquery.countdown.js"></script>
    <script>
    $("#countdown")
        // Year/Month/Day Hour:Minute:Second
        .countdown("2018/10/24 15:30:30", function(event) {
            $(this).html(
                event.strftime('%D Days %Hh %Mm %Ss')
            );
        });
    </script>
    <!-- Main application scripts -->
    <script src="js\main.js"></script>
</body>

</html>