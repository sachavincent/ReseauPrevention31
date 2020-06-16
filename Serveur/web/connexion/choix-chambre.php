<?php 
session_start();
session_destroy();
session_start();
$_SESSION['connexion'] = false;
?>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="../style/style.css" />
    <link rel="icon" type="image/png" href="../images/icon.png" />
  </head>

  <body>
    <!-- header -->
    <header>
      <title>Identification</title>
    </header>

    <!-- conteneur principal -->
    <section id="page-identification">
      <input class="btn-admin" type="button" value="Administrateur" title="Accès administration des chambres"
      onclick="window.location.href='../script/ajoutGestionnaire.php'" >
      <!-- choix avec selection de logos -->
      <div class="pan-chambres">
        <h1>Gestion des chambres</h1>
        <div id="logos-chambres">
          <a href="identification.php?chambre=CA">
            <img src="../images/ca.png" type="submit" name="chambre" />
          </a>
          <a href="identification.php?chambre=CMA">
            <img src="../images/cma.png" type="submit" name="chambre" />
          </a>
          <a href="identification.php?chambre=CCI">
            <img src="../images/cci.png" type="submit" name="chambre" />
          </a>
        </div>
      </div>
  
      <div class="pan-forces">
        <h1>Gestion des forces de l'ordre</h1>
        <div id="logos-forces">
          <a href="identification.php?chambre=G">
            <img src="../images/gendarmerie.png" type="submit" name="force" />
          </a>
          <a href="identification.php?chambre=P">
            <img src="../images/police.png" type="submit" name="force" />
          </a>
        </div>
      </div>
    </section>
  </body>
</html>
