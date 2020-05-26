<?php

include 'connexionBDD.php';

if (!(isset($_POST['input-objet-annonce']) AND isset($_POST['texte'])) OR empty($_POST['input-objet-annonce']) OR empty($_POST['texte'])){
    $success = false;
    header('Location: ../force/demandes.php?e=new_annonce');
    exit();
} else {
    $success = true;

    if (isset($_POST['toutes-activites']) AND isset($_POST['toutes-communes']) AND isset($_POST['toutes-zones'])){
        $requeteListeDestinataire=$bdd->query('SELECT idUtilisateur FROM Utilisateur');

    } elseif (isset($_POST['toutes-activites']) AND isset($_POST['toutes-communes'])){
        $requeteListeDestinataire=$bdd->prepare('SELECT idUtilisateur FROM Utilisateur WHERE secteur = ? OR secteur = ? OR secteur = ?');
        $requeteListeDestinataire->execute(array($_POST['secteur1'], $_POST['secteur2'], $_POST['secteur3']));

    } elseif(isset($_POST['toutes-activites']) AND isset($_POST['toutes-zones'])){
        $requeteListeDestinataire=$bdd->prepare('SELECT idUtilisateur FROM Utilisateur WHERE idCommune = ? OR idCommune = ? OR idCommune = ?');
        $requeteListeDestinataire->execute(array($_POST['commune1'], $_POST['commune2'], $_POST['commune3']));

    } elseif(isset($_POST['toutes-communes']) AND isset($_POST['toutes-zones'])){
        $requeteListeDestinataire=$bdd->prepare('SELECT idUtilisateur FROM Utilisateur WHERE codeAct = ? OR codeAct = ? OR codeAct = ?');
        $requeteListeDestinataire->execute(array($_POST['activite1'], $_POST['activite2'], $_POST['activite3']));

    } elseif(isset($_POST['toutes-activites'])){
        $requeteListeDestinataire = $bdd->prepare(' SELECT idUtilisateur FROM Utilisateur 
                                                    WHERE (idCommune = ? OR idCommune = ? OR idCommune = ?)
                                                    AND (secteur = ? OR secteur = ? OR secteur = ?)');
        $requeteListeDestinataire->execute(array(   $_POST['commune1'], $_POST['commune2'], $_POST['commune3'],
                                                    $_POST['secteur1'], $_POST['secteur2'], $_POST['secteur3']));

    } elseif(isset($_POST['toutes-communes'])){
        $requeteListeDestinataire = $bdd->prepare(' SELECT idUtilisateur FROM Utilisateur 
                                                    WHERE (codeAct = ? OR codeAct = ? OR codeAct = ?)
                                                    AND (secteur = ? OR secteur = ? OR secteur = ?)');
        $requeteListeDestinataire->execute(array(   $_POST['activite1'], $_POST['activite2'], $_POST['activite3'],
                                                    $_POST['secteur1'], $_POST['secteur2'], $_POST['secteur3']));
    
    } elseif(isset($_POST['toutes-zones'])){
        $requeteListeDestinataire = $bdd->prepare(' SELECT idUtilisateur FROM Utilisateur 
                                                    WHERE (idCommune = ? OR idCommune = ? OR idCommune = ?)
                                                    AND (codeAct = ? OR codeAct = ? OR codeAct = ?)');
        $requeteListeDestinataire->execute(array(   $_POST['commune1'], $_POST['commune2'], $_POST['commune3'],
                                                    $_POST['activite1'], $_POST['activite2'], $_POST['activite3']));
    } else{
        $requeteListeDestinataire = $bdd->prepare(' SELECT idUtilisateur FROM Utilisateur 
                                                    WHERE (idCommune = ? OR idCommune = ? OR idCommune = ?)
                                                    AND (codeAct = ? OR codeAct = ? OR codeAct = ?)
                                                    AND (secteur = ? OR secteur = ? OR secteur = ?)');
        $requeteListeDestinataire->execute(array(   $_POST['commune1'], $_POST['commune2'], $_POST['commune3'],
                                                    $_POST['activite1'], $_POST['activite2'], $_POST['activite3'],
                                                    $_POST['secteur1'], $_POST['secteur2'], $_POST['secteur3']));   
    }

    $listeDestinataire = $requeteListeDestinataire->fetchAll();

    //Creation de l'annonce
    $requeteCreationAnnonce = $bdd->prepare('INSERT INTO `Annonce`(`objet`, `texte`, `nbDestinataire`) VALUES (?,?,?)');
    $requeteCreationAnnonce->execute(array($_POST['input-objet-annonce'], $_POST['texte'], count($listeDestinataire)));
    
    //Recuperationde l'id de l'annonce
    $requeteIdAnnonce = $bdd->prepare('SELECT idAnnonce FROM Annonce WHERE objet = ? AND texte = ?');
    $requeteIdAnnonce->execute(array($_POST['input-objet-annonce'], $_POST['texte']));
    $idAnnonce = ($requeteIdAnnonce->fetch()['idAnnonce']);
    

   //Creation des lignes de destinationMessage
    foreach($listeDestinataire as $destinataire){
        $requeteAjoutDest = $bdd->prepare('INSERT INTO `DestinationAnnonce`(`idAnnonce`, `idUtilisateur`) VALUES (?,?)');
        $requeteAjoutDest->execute(array($idAnnonce, $destinataire['idUtilisateur']));
    }
    // echo 'Message envoye a : ' . $nbDest . ' utilisateurs';
    header('Location: ../force/demandes.php?e=prive&m=0');
    exit();
}

?>