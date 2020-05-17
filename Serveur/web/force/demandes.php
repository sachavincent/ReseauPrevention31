<?php
session_start();
?>

<!DOCTYPE html>
<html>
    <head>
        <title>Boîte de réception</title>
        <meta charset="utf-8" />
        <link rel="stylesheet" href="../style.css" />
    </head>

    <body>
        <!-- header -->
        <?php include("page/headerForce.php"); ?>

        <!-- ouverture du contenu | Privé | Annonce | Conseil | Corbeille | -->
        <?php switch ($_GET['e']) {
            case 'prive' :
                include("prive.php");
            break;
            case 'annonce' :
                include("annonce.php");
            break;
            case 'conseil' :
                include("conseil.php");
            break;
            case 'corbeille' :
                include("corbeille.php");
            break;
        }
        ?>
    
    </body>
</html>