<?php
// changement de couleur des onglets selectionnes
switch ($_GET['e']) {
    case 'attente':
        $onglet_attente   = "onglet-selected";
        $onglet_accepte   = "onglets";
        $onglet_refuse    = "onglets";
        $onglet_corbeille = "onglets";
        break;
    case 'accepte':
        $onglet_attente   = "onglets";
        $onglet_accepte   = "onglet-selected";
        $onglet_refuse    = "onglets";
        $onglet_corbeille = "onglets";
        break;
    case 'refuse':
        $onglet_attente   = "onglets";
        $onglet_accepte   = "onglets";
        $onglet_refuse    = "onglet-selected";
        $onglet_corbeille = "onglets";
        break;
    case 'profil':
        $onglet_attente   = "onglets";
        $onglet_accepte   = "onglets";
        $onglet_refuse    = "onglets";
        $onglet_corbeille = "onglets";
        break;
}

function nbNewMsg($categorie)
{
    include("../script/connexionBDD.php");
    $reqNewMsg = $bdd->prepare('SELECT * FROM Utilisateur WHERE chambre = ? AND ouvert = 0 AND demande = ?');
    $reqNewMsg->execute(array(
        $_SESSION['chambre'],
        $categorie
    ));
    $reqNewMsg = $reqNewMsg->fetchAll();
    
    if (isset($reqNewMsg)) {
        $nbNewMsg = count($reqNewMsg);
        if ($nbNewMsg != 0) {
            echo $nbNewMsg;
        } else {
            echo '';
        }
    }
}

?>

<!-- ============================================== -->

<!-- panel gauche -->
<nav id="nav-gauche">
  <!-- onglets d'interfaces -->
  <div class=<?= $onglet_attente; ?> onclick=window.location.href='../chambre/demandes.php?e=attente&m=none' >
    <img class="img-nav" src="../images/received.png" />En attente
    <!-- nb de demandes -->
    <p class="number-msg"><?php nbNewMsg("EN_COURS") ?></p>
  </div>

  <div class=<?= $onglet_accepte; ?> onclick=window.location.href='../chambre/demandes.php?e=accepte&m=none' >
    <img class="img-nav" src="../images/accepter.png" />Acceptées
    <!-- nb de demandes -->
    <p class="number-msg"><?php nbNewMsg("VALIDE") ?></p>
  </div>

  <div class=<?= $onglet_refuse; ?> onclick=window.location.href='../chambre/demandes.php?e=refuse&m=none' >
    <img class="img-nav" src="../images/refuser.png" />Refusées
    <!-- nb de demandes -->
    <p class="number-msg"><?php nbNewMsg("REFUSE") ?></p>
  </div>

  <!-- logo -->
  <img src=<?= $_SESSION['logoChambre']; ?> class="logo" width="150px" height="150px"/>
</nav>

<!-- ============================================== -->