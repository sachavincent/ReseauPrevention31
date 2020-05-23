<?php
/* session_start();
include 'connexionBdd.php';

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
$_SESSION['nbDestinataire'] = 0;
unset($_SESSION['destinataires']);

while($res = $requeteListeDestinataire->fetch()){
    $_SESSION['nbDestinataire']++;
    $_SESSION['destinataires'][] = $res['idDestinataire'];
}
echo 'nbDestinataire ' . $_SESSION['nbDestinataire'];
print_r ($_SESSION['destinataires']); */
echo $_POST['commune1'];
?>