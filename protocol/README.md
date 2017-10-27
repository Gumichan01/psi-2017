# Projet Petites Annonces - Groupe B

--------------------------------------------

## Protocol de Communication 
--------------------------------------------

### Format Executable 

- ./client <ip-serveur> <port-serveur> <port-msg>
	


- ./serveur <port>


--------------------------------------------

### Fonctionalite Client : 

1) Connection : "connect:port\n"

	port : Port sur lequel le client recoit les messages d'autres client.
	
	Reponse Serveur : 

		- "code:con:OK\n" (connection OK)

		- "code:con:FAIL\n" (connection failed)


2) List des annonces : "annonce:list\n"

	Reponse Serveur : 

		- "listannonce:|id;titre|id;titre|...|id;titre|\n"

			Liste des annonces en pair (id,titre)

		- "code:list:FAIL\n"


3) Creer une nouvelle annonce : "annonce:titre:texte\n"
	titre : titre de l'annonce
	texte : contenu de l'annonce

	Reponse Serveur : 

		- "code:ann:OK\n" (annonce a ete bien ajoute)

		- "code:ann:FAIL\n" (annonce n'a pas ete ajoute)


4) Recuperer une annonce : "annonce:get:id\n"

	id : ID de l'annonce (recupere dans la liste)

	Reponse Serveur : 

		- "annonce:titre:texte\n"
				titre : titre de l'annonce
				texte : contenu de l'annonce

		- "code:FAIL\n"

5) Recuperer l'ip du proprietaire d'une annonce : "annonce:com:id\n"

	id : ID de l'annonce

	Reponse Serveur : 

		- "client:ip:port\n"

		- "code:con:FAIL\n"

6) Deconnection : "disconnect\n"


#### Communication Client/Client :

- Connection : "connect:port\n"

	port : Port sur lequel le client recevant la demande doit repondre.

	Reponse : 

		- "code:OK\n"

		- "code:FAIL\n"

- Message : "msg:text\n"

- Deconnection : "disconnect\n"