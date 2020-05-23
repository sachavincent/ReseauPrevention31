<?php
// /* Connexion à la bdd */
include("../script/connexionBDD.php");
?>

<header>
  <title>Profil</title>
  <!-- boutons profil / deconnexion -->
  <p class="disconnect">
    <img src="../images/disconnect.png" width="20px" height="20px" onclick=window.location.href='../connexion/choix-chambre.php' />
  </p> 
  <p class="profil" onclick=window.location.href='../chambre/profil-ch.php?return=none&e=profil'><?php echo $_SESSION['prenom']?></p>
</header>