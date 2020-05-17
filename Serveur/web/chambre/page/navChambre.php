<?php 

// changement de couleur des onglets selectionnes
switch ($_GET['e']) {
    case 'attente': $ongletAttente = "ongletSelected";
                    $ongletAccepte = "onglets";
                    $ongletRefuse = "onglets";
    break;
    case 'accepte': $ongletAttente = "onglets";
                    $ongletAccepte = "ongletSelected";
                    $ongletRefuse = "onglets";
    break;
    case 'refuse':  $ongletAttente = "onglets";
                    $ongletAccepte = "onglets";
                    $ongletRefuse = "ongletSelected";
                    $categorie = "REFUSE";
    break;
    case 'profil': $ongletAttente = "onglets";
                    $ongletAccepte = "onglets";
}

function nbNewMsg($categorie) {
    include("../script/connexionBDD.php");
    $reqNewMsg = $bdd->prepare('SELECT idUtilisateur FROM Utilisateur WHERE ouvert = 0 AND demande = ?');
    $reqNewMsg->execute(array($categorie));
    $reqNewMsg = $reqNewMsg->fetchAll();

    if (isset($reqNewMsg)) {
        $nbNewMsg = count($reqNewMsg);
        echo $nbNewMsg;
    }
}

?>

<!-- ============================================== -->

<!-- panel gauche -->
<nav id="nav_gauche">
    <!-- onglets d'interfaces -->
    <div class=<?php echo $ongletAttente; ?> onclick=window.location.href="../chambre/demandes.php?e=attente&m=none" >
        <img class="img_nav" src="../images/received.png" />En attente<p class="number_msg">
        <!-- nb de demandes -->
        <?php nbNewMsg("EN_COURS") ?></p></div>

    <div class=<?php echo $ongletAccepte; ?> onclick=window.location.href="../chambre/demandes.php?e=accepte&m=none" >
        <img class="img_nav" src="../images/accepter.png" />Acceptées<p class="number_msg">
        <!-- nb de demandes -->
        <?php nbNewMsg("VALIDE") ?></p></div>

    <div class=<?php echo $ongletRefuse; ?> onclick=window.location.href="../chambre/demandes.php?e=refuse&m=none">
        <img class="img_nav" src="../images/refuser.png" />Refusées<p class="number_msg">
        <!-- nb de demandes -->
        <?php nbNewMsg("REFUSE") ?></p></div>
    
    <!-- logo -->
    <img src=<?php echo $_SESSION['logoChambre']; ?> class="logo" width="150px" height="150px"/>
</nav>

<!-- ============================================== -->