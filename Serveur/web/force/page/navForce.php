<?php
// changement de couleur des onglets selectionnes
switch ($_GET['e']) {
    case 'prive':
        $onglet_prive     = "onglet-selected";
        $onglet_annonce   = "onglets";
        $onglet_conseil   = "onglets";
        $onglet_corbeille = "onglets";
    break;
    case 'annonce':
        $onglet_prive     = "onglets";
        $onglet_annonce   = "onglet-selected";
        $onglet_conseil   = "onglets";
        $onglet_corbeille = "onglets";
    break;
    case 'conseil':
        $onglet_prive     = "onglets";
        $onglet_annonce   = "onglets";
        $onglet_conseil   = "onglet-selected";
        $onglet_corbeille = "onglets";
    break;
    // profil OR new annonce OR new conseil
    case 'profil' :
    case 'new_annonce' :  
    case 'new_conseil' :
        $onglet_prive     = "onglets";
        $onglet_annonce   = "onglets";
        $onglet_conseil   = "onglets";
        $onglet_corbeille = "onglets";
    break;
}

function nbNewMessage() {
    include ("../script/connexionBDD.php");
    $nbNewMsg   = 0;
    $reqNewMsg  = $bdd->query('SELECT idDernierMessage FROM FilDeDiscussion');

    while ($fil = $reqNewMsg->fetch()) {
        $reqNew = $bdd->prepare('SELECT idMessagePrive FROM MessagePrive WHERE ouvert = 0 AND idMessagePrive = ?');
        $reqNew->execute(array($fil['idDernierMessage']));
        $res = $reqNew->fetch();
        if (!empty($res['idMessagePrive'])) {
            $nbNewMsg++;
        }
    }

    if ($nbNewMsg != 0) {
        echo $nbNewMsg;
    } else {
        echo '';
    }
}

function nbNewAnnonce() {
    include ("../script/connexionBDD.php");
    $reqNewAnnonce = $bdd->query('SELECT idAnnonce FROM Annonce WHERE ouvert = 0');
    $reqNewAnnonce = $reqNewAnnonce->fetchAll();

    if (isset($reqNewAnnonce)) {
        $nbNewAnnonce  = count($reqNewAnnonce);
        if ($nbNewAnnonce != 0) {
            echo $nbNewAnnonce;
        } else {
            echo '';
        }
    }
}

function nbNewConseil() {
    include ("../script/connexionBDD.php");
    $reqNewConseil = $bdd->query('SELECT idConseil FROM Conseil WHERE ouvert = 0');
    $reqNewConseil = $reqNewConseil->fetchAll();

    if (isset($reqNewConseil)) {
        $nbNewConseil  = count($reqNewConseil);
        if ($nbNewConseil != 0) {
            echo $nbNewConseil;
        } else {
            echo '';
        }
    }
}

?>

<!-- ============================================== -->

<!-- panel gauche -->
<nav id="nav-gauche">
    <!-- boutons profil / deconnexion -->
    <div class="onglet-profil">
        <span onclick="window.location.href='../force/profil-fo.php?return=none&e=profil'">
        PROFIL
        </span>
        <img onclick="window.location.href='../connexion/choix-chambre.php'" src="../images/disconnect.png"/>
    </div>

    <input type="button" class="new-annonce" value="+  Nouvelle Annonce" onclick="window.location.href='demandes.php?e=new_annonce'" />
    <input type="button" class="new-conseil" value="+  Nouveau Conseil" onclick="window.location.href='demandes.php?e=new_conseil'" />

    <!-- onglets d'interfaces -->
    <div class=<?= $onglet_prive; ?> onclick=window.location.href='../force/demandes.php?e=prive&m=none' >
        Messages privés
        <!-- nb de demandes -->
        <p class="number-msg"><?php nbNewMessage(); ?></p>
    </div>

    <div class=<?= $onglet_annonce; ?> onclick=window.location.href='../force/demandes.php?e=annonce&m=none' >
        Annonces
        <!-- nb de demandes -->
        <p class="number-msg"><?php nbNewAnnonce(); ?></p>
    </div>

    <div class=<?= $onglet_conseil; ?> onclick=window.location.href='../force/demandes.php?e=conseil&m=none' >
        Conseils
        <!-- nb de demandes -->
        <p class="number-msg"><?php nbNewConseil(); ?></p>
    </div>

    <p class="nb-connexions">Nombre de connexions : <?= $_SESSION['nbConnexion']?></p>
    <!-- logo -->
    <img src=<?= $_SESSION['logoChambre']; ?> class="logo" width="150px" height="150px"/>
</nav>

<!-- ============================================== -->