
# Projet Protocole Internet #

- Meriem Fekih-Ahmed
- Luxon JEAN-PIERRE


## Compilation ##

Allez dans le répertoire `src/` puis lancez la commande suivante:

    make

Pour lancer le serveur:

    make run-server
    # ou bien
    java srv.Server

Pour lancer le client:

    make run-client
    # ou bien
    java srv.MainClient <server_ip> <server_port> <msg_port>

Pour lancer le serveur du client (pour le client client):

    java client.ClientSrv
    
