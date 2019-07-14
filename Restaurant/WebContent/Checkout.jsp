<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <form action="/Restaurant/servlet/AcceptPaymentRequest" method="POST">
            <script
                src="https://checkout.stripe.com/checkout.js" class="stripe-button"
                data-key="pk_test_xinVSJEltPQmhlIxl5fKY0ja00Q0hCQqc9"
                data-amount="1"
                data-name="Alpachino"
                data-description="Example charge"
                data-image="assets/images/Restaurants/alPachino/Logo/logo.png"
                data-locale="auto"
                data-currency="eur">
            </script>
            <button class="button-yellow button-heavy" style="visibility: visible;" type="submit"><span style="display: block; min-height: 30px;">Pay with Card</span></button>
            
        </form>
        
    </body>
</html>