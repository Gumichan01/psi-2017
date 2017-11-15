# Projet Petites Annonces - Groupe B

--------------------------------------------

## Protocol de Communication
--------------------------------------------

### Format Executable

Client :

		`./client <ip-serveur> <port-serveur> <port-msg>`

Serveur :

		`./serveur <port>`


--------------------------------------------

### Fonctionalite Client :

1) Connection : "connect:port\n"

	port : Port sur lequel le client reçoit les messages d'autres client.

	Réponse Serveur :

		- "code:con:OK\n" (connexion OK)


2) Liste des annonces : "annonce:list\n"

	Réponse Serveur :

		- "listannonce:|id;titre|id;titre|...|id;titre|\n"

			Liste des annonces en pair (id,titre)

		- "code:list:FAIL\n"


3) Créer une nouvelle annonce : "annonce:titre:texte\n"
	titre : titre de l'annonce
	texte : contenu de l'annonce

	Réponse Serveur :

		- "code:ann:OK\n" (annonce a ete bien ajoute)

		- "code:ann:FAIL\n" (annonce n'a pas ete ajoute)


4) Récupérer une annonce : "annonce:get:id\n"

	id : ID de l'annonce (récupère dans la liste)

	Réponse Serveur :

		- "annonce:titre:texte\n"
				titre : titre de l'annonce
				texte : contenu de l'annonce

		- "code:FAIL\n"

5) Récupérer l'ip du propriétaire d'une annonce : "annonce:com:id\n"

	id : ID de l'annonce

	Réponse Serveur :

		- "client:ip:port\n"

		- "code:con:FAIL\n"

6) Supprimer une annonce : "annonce:del:id\n"

    id : ID de l'annonce
    
    Réponse Serveur :
        
        - "annonce:del:OK\n"
        
        - "annonce:del:FAIL\n"

7) Déconnexion : "disconnect\n"


#### Communication Client/Client :

- Connexion : "connect:port\n"

	port : Port sur lequel le client recevant la demande doit répondre.

	Réponse :
    
		- "code:OK\n"


- Message : "msg:text\n"


- Déconnexion : "disconnect\n"