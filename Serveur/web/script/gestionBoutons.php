<?php
session_start();

// traitement chambre
if ($_SESSION['chambre'] == 'CCI' OR $_SESSION['chambre'] == 'CA' OR $_SESSION['chambre'] == 'CMA') {

    function accepterDemande($infoUtilisateur){
        include("connexionBDD.php");
        $requeteUpdateUtilisateur = $bdd->prepare('UPDATE `Utilisateur` SET cle = ?, demande = ? WHERE `Utilisateur`.`idUtilisateur` = ?');

        $requeteUpdateUtilisateur->execute(array($infoUtilisateur['cle'], 'VALIDE', $infoUtilisateur['idUtilisateur']));

        // include '../../script/envoiMail.php';
    }

    function refuserDemande($infoUtilisateur){
        include("connexionBDD.php");
        $requeteUpdateUtilisateur = $bdd->prepare('UPDATE `Utilisateur` SET  demande = ? WHERE `Utilisateur`.`idUtilisateur` = ?');
        $requeteUpdateUtilisateur->execute(array('REFUSE', $infoUtilisateur['idUtilisateur']));

        // si on refuse la demande apres l'avoir accepté (cas d'erreur)
        $requeteSupprCle = $bdd->prepare('UPDATE `Utilisateur` SET `cle` = NULL WHERE `Utilisateur`.`idUtilisateur` = ?');
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
                        header('Location: ../chambre/demandes.php?e=attente&m=none');
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
                        header('Location: ../chambre/demandes.php?e=accepte&m=none');
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
                        header('Location: ../chambre/demandes.php?e=refuse&m=none');
                        exit();
        break;
    }
}

/* ============================================================================ */ 

// traitement forces de l'ordre
else {

    function supprimerAnnonce($infoAnnonce){
        include("connexionBDD.php");
        $requeteSupprDestinationAnnonce = $bdd->prepare('DELETE FROM `DestinationAnnonce` WHERE idAnnonce = ?');
        $requeteSupprDestinationAnnonce->execute(array($infoAnnonce['idAnnonce']));

        $requeteSupprAnnonce = $bdd->prepare('DELETE FROM `Annonce` WHERE `Annonce`.`idAnnonce` = ?');
        $requeteSupprAnnonce->execute(array($infoAnnonce['idAnnonce']));
    }

    function supprimerConseil($infoConseil){
        include("connexionBDD.php");
        $requeteSupprConseil = $bdd->prepare('DELETE FROM `Conseil` WHERE `Conseil`.`idConseil` = ?');
        $requeteSupprConseil->execute(array($infoConseil['idConseil']));
    }

    function supprimerPrive($infoMsg){
        include("connexionBDD.php");

        $requeteSupprDernierMessage=$bdd->prepare('UPDATE `FilDeDiscussion` SET `idDernierMessage` = NULL WHERE `idFilDeDiscussion` = ?');
        $requeteSupprDernierMessage->execute(array($infoMsg['idFilDeDiscussion']));

        $requeteSupprMessagePrive = $bdd->prepare('DELETE FROM `MessagePrive` WHERE idFilDeDiscussion = ?');
        $requeteSupprMessagePrive->execute(array($infoMsg['idFilDeDiscussion']));

        $requeteSupprMsg = $bdd->prepare('DELETE FROM `FilDeDiscussion` WHERE idFilDeDiscussion = ?');
        $requeteSupprMsg->execute(array($infoMsg['idFilDeDiscussion'])); 
    }

    switch ($_GET['e']) {
        case 'prive':   if ($_GET['b'] == 'supprimer') {
                            supprimerPrive($_SESSION["FIL_DE_DISCUSSION"][$_GET['m']]);
                        }
                        header('Location: ../force/demandes.php?e=prive&m=none');
                        exit();
        break;
        case 'annonce': if ($_GET['b'] == 'supprimer') {
                            supprimerAnnonce($_SESSION["ANNONCE"][$_GET['m']]);
                        }
                        header('Location: ../force/demandes.php?e=annonce&m=none');
                        exit();
        break;
        case 'conseil': if ($_GET['b'] == 'supprimer') {
                            supprimerConseil($_SESSION["CONSEIL"][$_GET['m']]);
                        }
                        header('Location: ../force/demandes.php?e=conseil&m=none');
                        exit();
        break;
    }
}
?>