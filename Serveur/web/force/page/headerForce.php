<?php
// /* Connexion à la bdd */
include("../script/connexionBDD.php");
?>

<header>
    <title>Profil</title>
    <!-- boutons profil / deconnexion -->
    <p class="disconnect">
        <img onclick=window.location.href='../connexion/choix-chambre.php'; src="../images/disconnect.png" width="20px" height="20px"/>
    </p> 
    <p class="profil" onclick=window.location.href='../force/profil-fo.php?return=none&e=profil'><?php echo $_SESSION['prenom']?></p>

</header>