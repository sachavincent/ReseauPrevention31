<?php 

// changement de couleur des onglets selectionnes
switch ($_GET['e']) {
    case 'prive':       $ongletPrive = "ongletSelected";
                        $ongletAnnonce = "onglets";
                        $ongletConseil = "onglets";
                        $ongletCorbeille = "onglets";
    break;
    case 'annonce':     $ongletPrive = "onglets";
                        $ongletAnnonce = "ongletSelected";
                        $ongletConseil = "onglets";
                        $ongletCorbeille = "onglets";
    break;
    case 'conseil':     $ongletPrive = "onglets";
                        $ongletAnnonce = "onglets";
                        $ongletConseil = "ongletSelected";
                        $ongletCorbeille = "onglets";
    break;
    case 'corbeille':   $ongletPrive = "onglets";
                        $ongletAnnonce = "onglets";
                        $ongletConseil = "onglets";
                        $ongletCorbeille = "ongletSelected";
    break;
    case 'profil':      $ongletPrive = "onglets";
                        $ongletAnnonce = "onglets";
                        $ongletConseil = "onglets";
                        $ongletCorbeille = "onglets";
}

function nbNewMessage() {
    include("../script/connexionBDD.php");
    $reqNewMsg = $bdd->query('SELECT idMessagePrive FROM MessagePrive WHERE ouvert = 0 AND corbeille = 0');
    $reqNewMsg = $reqNewMsg->fetchAll();

    if (isset($reqNewMsg)) {
        $nbNewMsg = count($reqNewMsg);
        echo $nbNewMsg;
    }
}

function nbNewAnnonce() {
    include("../script/connexionBDD.php");
    $reqNewMsg = $bdd->query('SELECT idAnnonce FROM Annonce WHERE ouvert = 0 AND corbeille = 0');
    $reqNewMsg = $reqNewMsg->fetchAll();

    if (isset($reqNewMsg)) {
        $nbNewMsg = count($reqNewMsg);
        echo $nbNewMsg;
    }
}

function nbNewConseil() {
    include("../script/connexionBDD.php");
    $reqNewMsg = $bdd->query('SELECT idConseil FROM Conseil WHERE ouvert = 0 AND corbeille = 0');
    $reqNewMsg = $reqNewMsg->fetchAll();

    if (isset($reqNewMsg)) {
        $nbNewMsg = count($reqNewMsg);
        echo $nbNewMsg;
    }
}

// TODO
function nbNewCorbeille() {
    include("../script/connexionBDD.php");
    $reqNewMsg = $bdd->query('SELECT idMessagePrive FROM MessagePrive WHERE ouvert = 0 AND corbeille = 0');
    $reqNewMsg = $reqNewMsg->fetchAll();

    echo "<p>".$reqNewMsg."</p>";

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
    <div class=<?php echo $ongletPrive; ?> onclick=window.location.href="../force/demandes.php?e=prive&m=none" >
        <img class="img_nav" src="../images/received.png" />Messages privés<p class="number_msg">
        <!-- nb de demandes -->
        <?php nbNewMessage(); ?></p></div>

    <div class=<?php echo $ongletAnnonce; ?> onclick=window.location.href="../force/demandes.php?e=annonce&m=none" >
        <img class="img_nav" src="../images/annonce.png" />Annonces<p class="number_msg">
        <!-- nb de demandes -->
        <?php nbNewAnnonce(); ?></p></div>

    <div class=<?php echo $ongletConseil; ?> onclick=window.location.href="../force/demandes.php?e=conseil&m=none">
        <img class="img_nav" src="../images/conseil.png" />Conseils<p class="number_msg">
        <!-- nb de demandes -->
        <?php nbNewConseil(); ?></p></div>

    <div class=<?php echo $ongletCorbeille; ?> onclick=window.location.href="../force/demandes.php?e=corbeille&m=none">
        <img class="img_nav" src="../images/bin.png" />Corbeille<p class="number_msg">
        <!-- nb de demandes -->
        <?php // if (isset($_SESSION["REFUSE"])) { echo (count ($_SESSION["REFUSE"])); } ?></p></div>

    <!-- logo -->
    <img src=<?php echo $_SESSION['logoChambre']; ?> class="logo" width="150px" height="150px"/>
</nav>

<!-- ============================================== -->