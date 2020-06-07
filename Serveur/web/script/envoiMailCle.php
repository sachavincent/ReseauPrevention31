<?php
$cle = $infoUtilisateur['cle'];
$requete = $bdd->prepare('SELECT nomUtilisateur, prenomUtilisateur, mail FROM Utilisateur WHERE cle = ?');
$requete->execute(array($cle));
$infoUtilisateur = $requete->fetch();

shell_exec ("echo 'Bonjour,\n\nsuite à votre inscription sur l`application prevention31 voici votre clé de connexion unique: \n\n" . $cle 
	. "\n\nPour vous connecter sur l`application :\n	- Copiez la cle\n	- Rendez-vous sur l`application\n	- Selectionnez connexion puis collez la clé\n	- Enfin validez, vous voilà maintenant sur votre espace\n\nL`équipe RéseauPrévention31' | mail -s 'Validation de votre inscription' " . $infoUtilisateur['mail']);
?>
