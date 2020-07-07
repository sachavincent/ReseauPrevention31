<?php
session_start();
include("../script/connexionBDD.php");
?>

<!DOCTYPE html>
<html>
<head>
    <title>Boîte de réception</title>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="../style/style.css" />
    <link rel="icon" type="image/png" href="../images/icon.png" />
    <script src="https://kit.fontawesome.com/161f845565.js" crossorigin="anonymous"></script>
</head>

<body>
    <span class="switch-mode">🌓</span>
    <!-- Chargement de la BDD -->
    <?php include("../script/chargerInfos.php"); ?>
    <!-- affichage menu gauche -->
    <?php include("page/navForce.php"); ?>

    <!-- pan liste demandes -->
    <section id="pan-content">
        <!-- ouverture du contenu | Privé | Annonce | Conseil | -->
        <?php switch ($_GET['e']) {
            // prive OR annonce OR conseil
            case 'prive' :
            case 'annonce' :
            case 'conseil' :
                // affichage des infos
                include("../script/afficherInfos.php");
                break;
            case 'new_annonce' :
                include("new_annonce.php");
                break;
            case 'new_conseil' :
                include("new_conseil.php");
                break;
        } ?>
    </section>
    <script src="../script/dark-mode.js"></script>
</body>
</html>