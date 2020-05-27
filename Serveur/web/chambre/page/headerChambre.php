<?php
// /* Connexion à la bdd */
include("../script/connexionBDD.php");
?>

<header>
  <!-- boutons profil / deconnexion -->
  <p class="disconnect"><img height="20px" onclick="window.location.href='../connexion/choix-chambre.php'" src="../images/disconnect.png" width="20px" /></p>

  <p class="profil" onclick="window.location.href='../chambre/profil-ch.php?return=none&e=profil'">
    <?= $_SESSION['prenom']?>
  </p>
</header>
