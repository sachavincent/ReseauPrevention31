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
        $requeteSupprAnnonce = $bdd->prepare('UPDATE `annonce` SET `corbeille` = 1 WHERE `annonce`.`idAnnonce` = ?');
        $requeteSupprAnnonce->execute(array($infoAnnonce['idAnnonce']));
    }

    function supprimerConseil($infoConseil){
        include("connexionBDD.php");
        $requeteSupprConseil = $bdd->prepare('UPDATE `conseil` SET `corbeille` = 1 WHERE `conseil`.`idConseil` = ?');
        $requeteSupprConseil->execute(array($infoConseil['idConseil']));
    }

    function supprimerPrive($infoMsg){
        include("connexionBDD.php");
        $requeteSupprMsg = $bdd->prepare('UPDATE `messageprive` SET `corbeille` = 1 WHERE `messageprive`.`idMessagePrive` = ?');
        $requeteSupprMsg->execute(array($infoMsg['idMessagePrive']));
    }

    switch ($_GET['e']) {
        case 'prive':   if ($_GET['b'] == 'supprimer') {
                            supprimerPrive($_SESSION["MESSAGE_PRIVE"][$_GET['m']]);
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
        case 'corbeille': 
            echo '';
        break;
    }

}
?>
