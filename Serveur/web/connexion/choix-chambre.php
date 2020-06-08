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
    <link rel="icon" type="image/png" href="../images/doggo.png" />
  </head>

  <body>
    <!-- header -->
    <header>
      <title>Identification</title>
    </header>

    <!-- conteneur principal -->
    <section id="page-identification">
      <input type="button" value="Administrateur" title="Accès administration des chambres"
      onclick="window.location.href='../script/ajoutGestionnaire.php'" >
      <!-- choix avec selection de logos -->
      <div class="pan-chambres">
        <h1>Gestion des chambres</h1>
        <div id="logo-ca">
          <a href="identification.php?chambre=CA">
            <img src="../images/ca.png" type="submit" name="chambre" />
          </a>
        </div>
  
        <div id="logo-cma">
          <a href="identification.php?chambre=CMA">
            <img src="../images/cma.png" type="submit" name="chambre" />
          </a>
        </div>
  
        <div id="logo-cci">
          <a href="identification.php?chambre=CCI">
            <img src="../images/cci.png" type="submit" name="chambre" />
          </a>
        </div>
      </div>
  
      <div class="pan-forces">
        <h1>Gestion des forces de l'ordre</h1>
        <div id="logo-gendarmerie">
          <a href="identification.php?chambre=G">
            <img src="../images/gendarmerie.png" type="submit" name="force" />
          </a>
        </div>
  
        <div id="logo-police">
          <a href="identification.php?chambre=P">
            <img src="../images/police.png" type="submit" name="force" />
          </a>
        </div>
      </div>
    </section>
  </body>
</html>
