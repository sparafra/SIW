# SIW
Progetto Web Computing

Attenzione:
l’applicazione utilizza Maven per importare le librerie necessarie
(in eclipse):
fare click destro sul progetto-> run as… -> Maven clean
Prima di lanciare l’applicazione su Tomcat.


Descrizione:

alPachino è un applicazione web che permette la visualizzazione di prodotti offerti e la gestione e invio di ordini per un ristorante.
L’utente può osservare l’offerta gastronomica del ristorante e (dopo essersi autenticato) effettuare un ordine.

Analisi dei requisiti:

1.	l’utente deve poter visuale l’offerta gastronomica corrente
2.	l’utente deve poter autenticarsi
3.	l’utente deve poter aggiungere e gestire i prodotti nel proprio carrello
4.	l’utente deve poter visualizzare lo storico dei suoi ordini
5.	l’utente deve poter effettuare un ordine e decidere il metodo di consegna, ritiro o domicili
6.	il sistema deve notificare al gestore l’ordine effettuato

















Casi d’uso:
 I principali casi d’uso supportati sono due:

1.	Visualizzazione del catalogo
Indipendentemente dalla sessione il sito dovrà offrire all’utente, oltre ad informazioni riguardo al locale, la possibilità di consultare le offerte gastronomiche del ristorante, queste offerte saranno di vario genere (pizze, contorni, speciali) dinamiche e aggiornate regolarmente sul database. 
2.	Effettuare un’ordinazione
L’utente dovrà essere in grado di effettuare il login tramite le sue credenziali oppure tramite Google, selezionare elementi dal catalogo da aggiungere al proprio carrello, visualizzare gli elementi nel carrello, modificare la quantità di ogni articolo selezionato, effettuare l’ordine scegliendo la consegna a domicilio o il ritiro 


















Librerie ed API utilizzate:

L’applicazione utilizza Apache Maven per la sincronizzazione delle librerie tra le varie
versioni.

Lato client l’applicazione utilizza HTML5, CSS, e Javascript/JQuery

Vengono utilizzati oggetti JSON per ricevere informazioni dal database

L’applicazione usa esclusivamente Ajax per la comunicazione di dati e updates tra client e server. 

Viene utilizzata l’API Google Sign-In per l’autenticazione degli utenti

Per inviare le e-mail di ordine ricevuto abbiamo usato JavaMail API.

Il sistema utilizza Bootstrap per la gestione grafica e delle
Animazioni ed è possibile utilizzare l’applicazione su sistemi mobile in quanto si dimostra full
responsive.

Package, Classi e pagine create
Pagine Vetrina:
• about.html : pagina con informazioni riguardo al ristorante
• ChooseLocal.html
• ConfermaUtente.html
• contact.html
• index.html
• login.html 
• menu_1.html
• MyAccount.html
• SignIn.html: Pagina di registrazione.
Pagine Dashboard:
• Ingredienti.html 
• Locale.html
• Login.html
• Ordini.html
• Prodotti.html
• result.jsp 
• Utenti.html
Pagine Landingpage:
• Affiliazione.html
• result.jsp 
Pagine ComingSoon:
• index.jsp











Classi:

Package classes: (Package che contiene le servlet )

Address.java
addtoCart.java
AllProducts.java
DecreaseQuantityProduct.java
GetCart.java
IncreaseQuantityProduct.java
IngredientsOfProduct.java
isLogged.java
Login.java
Logout.java
OrdersByUser.java
ProductsByOrder.java
ProductsByType.java
SaveOrder.java
AllProducts.java


Package dao: (Package che gestisce il login e la sessione)

IngredientDAO.java
OrderDAO.java
ProductDAO.java
OrderDAO.java
IngredientDAO.java
OrderDAO.java


Package DataBase: (Package che comprende le classi interne principali del sistema)

DBConnection.java
idBroker.java
IngredientDaoJDBC.java
OrderDaoJDBC.java
PersistenceException.java
ProductDaoJDBC.java
RestaurantDaoJDBC.java
UserDaoJDBC.java


Package model: (package di logica interna all’applicazione)

Cart.java
Ingredient.java
Order.java
Product.java
Restaurant.java
State.java
User.java
