<?php
session_start();
?>

<!DOCTYPE html>
<html>
  <head>
    <title>Profil</title>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="../style/style.css" />
  </head>  
  <body>
    <header>
        <title>Profil</title>
    </header>
    
    <!-- affichage menu gauche -->
    <?php include("page/navChambre.php"); ?>
    <!-- ============================================== -->  
    <!-- page du profil -->
    <section id="pan-profil">
      <h1>Mon Profil</h1>
      <!-- conteneur image de profil avec nom prenom -->
      <section id="pan-profil-interieur">
        <!-- affichage nom / prenom -->
        <div class="img-profil"><img src="../images/user.jpg"></div>
        <p class="nom"> <?= $_SESSION['nom'] ?></p>
        <p class="prenom"> <?= $_SESSION['prenom'] ?></p>

      <!-- ============================================== -->  
      <!-- conteneur avec les infos modifiables -->
      <form action="../script/modifier_profil.php?return=none" method="post">
        <!-- nom -->
        <div class="zone-infos">
          <p class="infos-user">Nom</p>
          <input class="input-infos" name="nom" value=<?= $_SESSION['nom']; ?> />
          <!-- prenom -->
          <p class="infos-user">Prénom</p>
          <input class="input-infos" name="prenom" value=<?= $_SESSION['prenom']; ?> />
          <!-- mail -->
          <p class="infos-user">Adresse Mail</p>
          <input class="input-infos" name="mail" type="email" value=<?= $_SESSION['mail']; ?> />
        </div>
        <!-- mdp actuel -->
        <div class="zone-mdp">
          <p class="mdp-user">Mot de passe actuel</p>
          <img class="required-input" src="../images/required.png" title="champ obligatoire" />
          <input class="input-mdp" name="mdp" type="password" required/>
          <!-- nouveau mdp -->
          <p class="mdp-user">Nouveau mot de passe</p>
          <input class="input-mdp" name="nouveauMdp" type="password"/>
          <!-- confirmation mdp -->
          <p class="mdp-user">Confirmation</p>
          <input class="input-mdp" name="confirmationMdp" type="password"/>
        </div>
        <!-- bouton valider -->
        <input type="submit" value="valider" />

        <!-- ligne d'erreur si la modification échoue -->
        <?php 
          /* recuperation de l'URL pour les modifications de profil */
          switch ($_GET['return']) {
              case 'none' :
                  default;
              break;
              case 'success' :
                  echo '<p id="success">Modifications effectuées.</p>';
              break;
              case 'error' :
                  echo '<p id="error">Erreur lors de la modification du profil.</p>';
              break;
          } ?>  
      </section>
    </section>
  </body>
</html>