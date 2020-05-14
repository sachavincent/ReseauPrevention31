<?php
session_start();

function accepterDemande($infoUtilisateur){
    include("../../script/connexionBDD.php");
    $requeteUpdateUtilisateur = $bdd->prepare('UPDATE `Utilisateur` SET cle = ?, demande = ? WHERE `Utilisateur`.`idUtilisateur` = ?');


    $requeteUpdateUtilisateur->execute(array($infoUtilisateur['cle'], 'VALIDE', $infoUtilisateur['idUtilisateur']));

    //include '../../script/envoiMail.php';
    //include 'chargerInfo.php';
}

function refuserDemande($infoUtilisateur){
    include("../../script/connexionBDD.php");
    $requeteUpdateUtilisateur = $bdd->prepare('UPDATE `Utilisateur` SET  demande = ? WHERE `Utilisateur`.`idUtilisateur` = ?');
    $requeteUpdateUtilisateur->execute(array('REFUSE', $infoUtilisateur['idUtilisateur']));

    // si on refuse la demande apres l'avoir accepté (cas d'erreur)
    $requeteSupprCle = $bdd->prepare('UPDATE `Utilisateur` SET `cle` = NULL WHERE `utilisateur`.`idUtilisateur` = ?');
    $requeteSupprCle->execute(array($infoUtilisateur['idUtilisateur']));
}

// redirections
switch ($_GET['e']) {
    case 'attente': switch ($_GET['b']) {
                        case 'accepter' :
                            accepterDemande($_SESSION["EN_COURS"][$_GET['m']]);
                        break;
                        case 'refuser' :
                            refuserDemande($_SESSION["EN_COURS"][$_GET['m']]);
                        break;
                    }
                    header('Location: ../demandes.php?e=attente&m=none');
                    exit();
    break;
    case 'accepte': switch ($_GET['b']) {
                        case 'accepter' :
                            accepterDemande($_SESSION["VALIDE"][$_GET['m']]);
                        break;
                        case 'refuser' :
                            refuserDemande($_SESSION["VALIDE"][$_GET['m']]);
                        break;
                    }
                    header('Location: ../demandes.php?e=accepte&m=none');
                    exit();
    break;
    case 'refuse' : switch ($_GET['b']) {
                        case 'accepter' :
                            accepterDemande($_SESSION["REFUSE"][$_GET['m']]);
                        break;
                        case 'refuser' :
                            refuserDemande($_SESSION["REFUSE"][$_GET['m']]);
                        break;
                    }
                    header('Location: ../demandes.php?e=refuse&m=none');
                    exit();
    break;
}

?>
