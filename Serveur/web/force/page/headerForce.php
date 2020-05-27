<?php
// /* Connexion à la bdd */
include("../script/connexionBDD.php");
?>

<header>
  <!-- boutons profil / deconnexion -->
  <p class="disconnect"><img src="../images/disconnect.png" width="20px" height="20px" onclick="window.location.href='../connexion/choix-chambre.php'" ; /></p>

  <p class="profil" onclick="window.location.href='../force/profil-fo.php?return=none&e=profil'">
    <?= $_SESSION['prenom']?>
  </p>
</header>
