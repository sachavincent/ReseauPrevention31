<?php 
// changement de couleur des onglets selectionnes
switch ($_GET['e']) {
    case 'prive':       $onglet_prive = "onglet-selected";
                        $onglet_annonce = "onglets";
                        $onglet_conseil = "onglets";
                        $onglet_corbeille = "onglets";
    break;
    case 'annonce':     $onglet_prive = "onglets";
                        $onglet_annonce = "onglet-selected";
                        $onglet_conseil = "onglets";
                        $onglet_corbeille = "onglets";
    break;
    case 'conseil':     $onglet_prive = "onglets";
                        $onglet_annonce = "onglets";
                        $onglet_conseil = "onglet-selected";
                        $onglet_corbeille = "onglets";
    break;
    case 'profil':      $onglet_prive = "onglets";
                        $onglet_annonce = "onglets";
                        $onglet_conseil = "onglets";
                        $onglet_corbeille = "onglets";
    break;
    case 'new_annonce': $onglet_prive = "onglets";
                        $onglet_annonce = "onglets";
                        $onglet_conseil = "onglets";
                        $onglet_corbeille = "onglets";
    break;
    case 'new_conseil': $onglet_prive = "onglets";
                        $onglet_annonce = "onglets";
                        $onglet_conseil = "onglets";
                        $onglet_corbeille = "onglets";
    break;
}

function nbNewMessage() {
    include("../script/connexionBDD.php");
    $nbNewMsg = 0;
    $reqNewMsg = $bdd->query('SELECT idDernierMessage FROM FilDeDiscussion');
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
    include("../script/connexionBDD.php");
    $reqNewAnnonce = $bdd->query('SELECT idAnnonce FROM Annonce WHERE ouvert = 0');
    $reqNewAnnonce = $reqNewAnnonce->fetchAll();

    if (isset($reqNewAnnonce)) {
        $nbNewAnnonce = count($reqNewAnnonce);
        if ($nbNewAnnonce != 0) {
            echo $nbNewAnnonce;
        } else {
            echo '';
        }
    }
}

function nbNewConseil() {
    include("../script/connexionBDD.php");
    $reqNewConseil = $bdd->query('SELECT idConseil FROM Conseil WHERE ouvert = 0');
    $reqNewConseil = $reqNewConseil->fetchAll();

    if (isset($reqNewConseil)) {
        $nbNewConseil = count($reqNewConseil);
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
  <!-- onglets d'interfaces -->
  <div class=<?php echo $onglet_prive; ?> onclick=window.location.href="../force/demandes.php?e=prive&m=none" >
    <img class="img-nav" src="../images/received.png" />Messages privés<p class="number-msg">
    <!-- nb de demandes -->
    <?php nbNewMessage(); ?></p>
  </div>

  <div class=<?php echo $onglet_annonce; ?> onclick=window.location.href="../force/demandes.php?e=annonce&m=none" >
    <img class="img-nav" src="../images/annonce.png" />Annonces<p class="number-msg">
    <!-- nb de demandes -->
    <?php nbNewAnnonce(); ?></p>
  </div>

  <div class=<?php echo $onglet_conseil; ?> onclick=window.location.href="../force/demandes.php?e=conseil&m=none">
    <img class="img-nav" src="../images/conseil.png" />Conseils<p class="number-msg">
    <!-- nb de demandes -->
    <?php nbNewConseil(); ?></p>
  </div>

  <!-- logo -->
  <img src=<?php echo $_SESSION['logoChambre']; ?> class="logo" width="150px" height="150px"/>
</nav>

<!-- ============================================== -->