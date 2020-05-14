<?php

// /* Connexion à la bdd */
include("../script/connexionBDD.php");

?>

<!-- ============================================== -->

<!-- barre grise a droite (decoratif) -->
<div id="deco_droite"></div>

<!-- ============================================== -->

<!-- panel gauche -->
<nav id="nav_gauche">
    <!-- onglets d'interfaces -->
    <div class="onglets"><img class="img_nav" src="../images/received.png" />Boîte de réception<p class="number_msg">4</p></div>
    <div class="onglets"><img class="img_nav" src="../images/sent.png" />Envoyés<p class="number_msg">1</p></div>
    <div class="onglets"><img class="img_nav" src="../images/brouillon.png" />Brouillons</div>
    <div class="onglets"><img class="img_nav" src="../images/bin.png" />Corbeille</div>

    <!-- logo -->
    <img src=<?php echo $_SESSION['logoChambre']; ?> class="logo" width="150px" height="150px"/>
</nav>

<!-- ============================================== -->