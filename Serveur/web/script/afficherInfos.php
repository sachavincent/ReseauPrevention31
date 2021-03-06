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

<script>
function confirmDelete(delUrl) {
    if (confirm("Voulez-vous vraiment supprimer ?")) {
    document.location = delUrl;
    }
}

function confirmChoice(linkUrl) {
    if (confirm("Confirmez-vous votre choix ?")) {
    document.location = linkUrl;
    }
}
</script>

<!-- barre d'actions boutons -->
<div id="barre-actions">
<?php if (($_GET['m']) != 'none') {    
    switch ($_GET['e']) { 
        /* ===== CHAMBRES ===== */
        // page en attente
        case 'attente': ?>
            <!-- bouton accepter / refuser -->
            <a href="javascript:confirmChoice('../script/gestionBoutons.php?e=<?= $lien ?>&m=<?= $_GET['m'] ?>&b=accepter')">
                <input class="actions" type="button" value="Accepter"/></a>
            <a href="javascript:confirmChoice('../script/gestionBoutons.php?e=<?= $lien ?>&m=<?= $_GET['m'] ?>&b=refuser')">
                <input class="actions" type="button" value="Refuser" /></a>
        <?php
            break;
        // page acceptees
        case 'accepte': ?>
            <!-- bouton refuser / supprimer -->
            <a href="javascript:confirmChoice('../script/gestionBoutons.php?e=<?= $lien ?>&m=<?= $_GET['m'] ?>&b=refuser')">
                <input class="actions" type="button" value="Refuser" /></a>
            <a href="javascript:confirmDelete('../script/gestionBoutons.php?e=<?= $lien ?>&m=<?= $_GET['m'] ?>&b=supprimer')">
                <input class="actions" type="button" value="Supprimer" /></a>
        <?php
            break;
        // page refusees
        case 'refuse': ?>
            <!-- bouton accepter / supprimer -->
            <a href="javascript:confirmChoice('../script/gestionBoutons.php?e=<?= $lien ?>&m=<?= $_GET['m'] ?>&b=accepter')">
                <input class="actions" type="button" value="Accepter"/></a>
            <a href="javascript:confirmDelete('../script/gestionBoutons.php?e=<?= $lien ?>&m=<?= $_GET['m'] ?>&b=supprimer')">
                <input class="actions" type="button" value="Supprimer" /></a>
        <?php
            break;  

        /* ===== FORCES ===== */
        case 'annonce' || 'conseil' || 'prive': ?>
            <a href="javascript:confirmDelete('../script/gestionBoutons.php?e=<?= $lien ?>&m=<?= $_GET['m'] ?>&b=supprimer')">
                <input class="actions" type="button" value="Supprimer" /></a>
        <?php
            break;
    } 
} ?>
</div>

<nav id="liste-onglets">
    <!-- barre d'actions -->
    <div id="barre-refresh">
        <i class="fas fa-redo-alt" onclick="window.location.href='demandes.php?e=<?= $lien ?>&m=none'"></i>
        <em onclick="window.location.href='demandes.php?e=<?= $lien ?>&m=none'">Actualiser</em>
    </div>

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