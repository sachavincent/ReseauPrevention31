<?php
// chargement categorie
switch ($_GET['e']) {
    // forces de l'ordre
    case 'prive':
        $onglet = "FIL_DE_DISCUSSION";
        $lien   = 'prive';
    break;
    case 'annonce':
        $onglet = "ANNONCE";
        $lien   = 'annonce';
    break;
    case 'conseil':
        $onglet = "CONSEIL";
        $lien   = 'conseil';
    break;
    // chambres
    case 'attente':
        $onglet = "EN_COURS";
        $lien   = 'attente';
    break;
    case 'accepte':
        $onglet = "VALIDE";
        $lien   = 'accepte';
    break;
    case 'refuse':
        $onglet = "REFUSE";
        $lien   = 'refuse';
    break;
}

// fonction délimiteur de caracteres dans les onglets
function trunc($chaine, $nbChar) {
    if(strlen($chaine) >= $nbChar)
        $chaine = substr($chaine, 0, $nbChar).'...';
    return $chaine;
}

/* ========== GESTION DES MESSAGES NON LUS ========== */

// forces 
function priveNonLu($i) {
    include("connexionBDD.php");
    $reqPrive = $bdd->prepare('UPDATE `MessagePrive` SET `ouvert` = 1 WHERE `MessagePrive`.`idMessagePrive` = ?');
    $reqPrive->execute(array($_SESSION['FIL_DE_DISCUSSION'][$i]['idDernierMessage']));
    include("chargerInfos.php");
}

function annonceNonLu($i) {
    include("connexionBDD.php");
    $reqAnnonce = $bdd->prepare('UPDATE `Annonce` SET `ouvert` = 1 WHERE `Annonce`.`idAnnonce` = ?');
    $reqAnnonce->execute(array($_SESSION['ANNONCE'][$i]['idAnnonce']));
    include("chargerInfos.php");
}

function conseilNonLu($i) {
    include("connexionBDD.php");
    $reqConseil = $bdd->prepare('UPDATE `Conseil` SET `ouvert` = 1 WHERE `Conseil`.`idConseil` = ?');
    $reqConseil->execute(array($_SESSION['CONSEIL'][$i]['idConseil']));
    include("chargerInfos.php");
}

// chambres
function attenteNonLu($i) {
    include("connexionBDD.php");
    $reqAttente = $bdd->prepare('UPDATE `Utilisateur` SET `ouvert` = 1 WHERE `Utilisateur`.`idUtilisateur` = ?');
    $reqAttente->execute(array($_SESSION['EN_COURS'][$i]['idUtilisateur']));
    include("chargerInfos.php");
}

function valideNonLu($i) {
    include("connexionBDD.php");
    $reqValide = $bdd->prepare('UPDATE `Utilisateur` SET `ouvert` = 1 WHERE `Utilisateur`.`idUtilisateur` = ?');
    $reqValide->execute(array($_SESSION['VALIDE'][$i]['idUtilisateur']));
    include("chargerInfos.php");
}

function refuseNonLu($i) {
    include("connexionBDD.php");
    $reqAttente = $bdd->prepare('UPDATE `Utilisateur` SET `ouvert` = 1 WHERE `Utilisateur`.`idUtilisateur` = ?');
    $reqAttente->execute(array($_SESSION['REFUSE'][$i]['idUtilisateur']));
    include("chargerInfos.php");
}

// fonction pour avoir la commune avec le code postal
function infoCommune($infosUser) {       
    include("connexionBDD.php");  
    $requete = $bdd->prepare("SELECT codePostal, commune FROM Commune WHERE idCommune = ?");
    $requete->execute(array($infosUser['idCommune']));
    $resultatRequete         = $requete->fetch();
    $infosUser["codePostal"] = $resultatRequete["codePostal"];
    $infosUser["commune"]    = $resultatRequete["commune"];
    return $infosUser;
}
?>

<!-- pan liste msg -->
<section id="pan-content">
  <!-- barre d'actions -->
  <div id="barre-refresh">
    <img class="refresh-rapide" src="../images/refresh.png" onclick="window.location.href='demandes.php?e=<?= $lien ?>&m=none'" />
    <em onclick="window.location.href='demandes.php?e=<?= $lien ?>&m=none'">Actualiser</em>
  </div> 

  <!-- barre d'actions boutons -->
  <div id="barre-actions">
    <?php if (($_GET['m']) != 'none') {    
        switch ($_GET['e']) { 
            case 'attente': ?>
                <input class="actions" type="button" value="accepter" onclick="window.location.href='../script/gestionBoutons.php?e=<?= $lien ?>&m=<?= $_GET['m'] ?>&b=accepter'" />
                <input class="actions" type="button" value="refuser" onclick="window.location.href='../script/gestionBoutons.php?e=<?= $lien ?>&m=<?= $_GET['m'] ?>&b=refuser'" />
            <?php
                break;
            case 'accepte': ?>
                <input class="actions" type="button" value="accepter" onclick="window.location.href='../script/gestionBoutons.php?e=<?= $lien ?>&m=<?= $_GET['m'] ?>&b=accepter'" />
            <?php
                break;
            case 'refuse': ?>
                <input class="actions" type="button" value="supprimer" onclick="window.location.href='../script/gestionBoutons.php?e=<?= $lien ?>&m=<?= $_GET['m'] ?>&b=refuser'" />
            <?php
                break;
            case 'annonce': ?>
                <input class="actions" type="button" value="supprimer" onclick="window.location.href='../script/gestionBoutons.php?e=<?= $lien ?>&m=<?= $_GET['m'] ?>&b=supprimer'" />
            <?php
                break;
            case 'conseil': ?>
                <input class="actions" type="button" value="supprimer" onclick="window.location.href='../script/gestionBoutons.php?e=<?= $lien ?>&m=<?= $_GET['m'] ?>&b=supprimer'" />
            <?php
                break;
            case 'prive': ?>
                <input class="actions" type="button" value="supprimer" onclick="window.location.href='../script/gestionBoutons.php?e=<?= $lien ?>&m=<?= $_GET['m'] ?>&b=supprimer'" />
            <?php
        } 
    } ?>
  </div>

  <nav id="liste-onglets">
    <?php   
    /* ======================== LISTE ONGLETS FORCES ========================== */

    if($_SESSION['chambre'] == 'G' OR $_SESSION['chambre'] == 'P') { 
        include "../force/page/listeOngletsForce.php";
    }
    /* ======================== LISTE ONGLETS CHAMBRES ========================== */
    else {
        include "../chambre/page/listeOngletsChambre.php";
    }
    ?>
  </nav>
  <?php
    /* ===================== ZONE OUVERTURE DU MESSAGE FORCES ============+============= */
    
    if($_SESSION['chambre'] == 'G' OR $_SESSION['chambre'] == 'P') { 
        include "../force/page/ongletOuvertForce.php";
    }
    /* ===================== ZONE OUVERTURE DEMANDE CHAMBRE ========================= */
    else {
        include "../chambre/page/ongletOuvertChambre.php";
    }
  ?>
</section>