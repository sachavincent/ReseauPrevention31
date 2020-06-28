<?php
session_start();
include("../script/connexionBDD.php");
?>

<!DOCTYPE html>
<html>
    <head>
        <title>Demandes</title>
        <meta charset="utf-8" />
        <link rel="stylesheet" href="../style/style.css" />
        <link rel="icon" type="image/png" href="../images/icon.png" />
        <script src="https://kit.fontawesome.com/161f845565.js" crossorigin="anonymous"></script>
    </head>
    <body>
        <!-- affichage menu gauche -->
        <?php include("page/navChambre.php"); ?>
        
        <!-- pan liste demandes -->
        <section id="pan-content">
            <!-- chargement de la BDD -->
            <?php include("../script/chargerInfos.php"); ?>
            <!-- ouverture du contenu | En attente | Acceptees | Refusees | -->
            <?php include("../script/afficherInfos.php"); ?>
        </section>
    </body>
</html>