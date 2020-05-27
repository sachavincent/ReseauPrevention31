<?php
session_start();
?>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="../style.css" />
  </head>

  <body>
    <!-- header -->
    <?php include("page/headerForce.php"); ?>

    <!-- barre d'actions -->
    <div id="barre-actions">
      <input type="button" class="new-annonce" value="+ Annonce" onclick=window.location.href='demandes.php?e=new_annonce'>
      <input type="button" class="new-conseil" value="+ Conseil" onclick=window.location.href='demandes.php?e=new_conseil'>
    </div>

    <!-- navigation gauche -->
    <?php include("page/navForce.php"); ?>

    <!-- ============================================== -->
    <!-- page du profil -->
    <section id="pan-profil">
      <aside>
          <h1>Mon Profil</h1>
      </aside>
    <!-- conteneur image de profil avec nom prenom -->
    <section id="pan-nom-prenom">
      <!-- affichage nom / prenom -->
      <p class="img-profil"><img src="../images/user.png"></p><p class="affichage-nom"> <?php echo $_SESSION['nom'] ?> </p>
      <p class="affichage-prenom"> <?php echo $_SESSION['prenom'] ?> </p>
      <p classe="info-user">Vous vous êtes connecté <?= $_SESSION['nbConnexion']?> fois</p>
    </section>

    <!-- ============================================== -->

    <!-- conteneur avec les infos modifiables -->
    <form action="../script/modifier_profil.php?return=none" method="post">
    <section id="pan-infos-user">
      <!-- nom -->
      <p class="infos-user">Nom <input class="input-infos" name="nom"
          value=<?php echo $_SESSION['nom']; ?> /></p>
      <!-- prenom -->
      <p class="infos-user">Prénom <input class="input-infos" name="prenom"
          value=<?php echo $_SESSION['prenom']; ?> /></p>
      <!-- mail -->
      <p class="infos-user">Adresse Mail <input class="input-infos" name="mail" type="email"
          value=<?php echo $_SESSION['mail']; ?> /></p>
      <!-- mdp actuel -->
      <p class="infos-user">Mot de passe actuel <input class="input-infos" name="mdp" type="password" required/></p>
      <!-- nouveau mdp -->
      <p class="infos-user">Nouveau mot de passe <input class="input-infos" name="nouveauMdp" type="password"/></p>
      <!-- confirmation mdp -->
      <p class="infos-user">Confirmation <input class="input-infos" name="confirmationMdp" type="password"/></p>
      <!-- bouton valider -->
      <p class="infos-user"><input class="valider-infos" type="submit" value="valider" /></p>
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
                echo '<p id="erreur">Erreur lors de la modification du profil.</p>';
            break;
        }
      ?>
    </section>
  </body>
</html>