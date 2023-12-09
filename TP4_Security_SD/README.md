## Activité Pratique N°4 :  Sécurité des Systèmes Distribués


####  Architecture : 
![archi](captures/archi.png) 

####  Partie 1 : 

##### 1. Télécharger Keycloak 19
depuis le site officel de keycloak on telecharge la derniere version [Download](https://www.keycloak.org/)  

##### 2. Démarrer Keycloak 
on le demare par la commande suivante : 
```bash
kc.bat start-dev
```
##### 3. Créer un compte Admin 
![admin](captures/admin.png) 
puis on fait connecter au compte admin  
![admin](captures/signadmin.png) 
##### 4. Créer une Realm 
* wallet-realm
![admin](captures/createrealm.png)
![realm](captures/wallet-realm.png) 
##### 5. Créer un client à sécuriser 
![client](captures/client1.png) 
![client](captures/client2.png) 
##### 6. Créer des utilisateurs 
* user1 :
![client](captures/user1.png) 
![client](captures/cred-bouargalne.png)
* user2 : 
![client](captures/cred-hamid.png)
![client](captures/all-users.png) 
 
##### 7. Créer des rôles
* USER :

![client](captures/role-user.png) 

* ADMIN :
  
![client](captures/role-admin.png) 

##### 8. Affecter les rôles aux utilisateurs 
dans cette partie on va assigner le role ADMIN & USER au user "bouargalne" et USER au user "hamid" 
* user "bouargalne" :

![client](captures/assignrole-bouargalne.png) 

* user "hamid" :
  
![client](captures/assignrole-hamid.png) 

##### 9. Avec PostMan :
   - Tester l'authentification avec le mot de passe
    ![client](captures/test-pass1.png)
    ![client](captures/test-pass2.png)
    ![client](captures/test-pass3.png) 
   - Analyser les contenus des deux JWT Access Token et Refresh Token
    ![client](captures/ana-token1.png)
    ![client](captures/ana-token2.png) 
   - Tester l'authentification avec le Refresh Token
    ![client](captures/auth-refresh1.png)
    ![client](captures/auth-refresh2.png)
   - Tester l'authentification avec Client ID et Client Secret
    ![client](captures/client-modif.png)
    ![client](captures/clientsecret.png)
    ![client](captures/auth-secret1.png)
    ![client](captures/auth-secret2.png)
   - Changer les paramètres des Tokens Access Token et Refresh Toke
    ![client](captures/test-pass1.png)
    ![client](captures/test-pass1.png) 
