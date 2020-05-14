<?php
session_start();
?>

<!DOCTYPE html>
<html>
    <head>
        <title>Demandes</title>
        <meta charset="utf-8" />
        <link rel="stylesheet" href="../style.css" />
    </head>

    <body>
        <!-- header -->
        <?php include("page/headerChambre.php"); ?>

        <!-- ouverture du contenu | En attente | Acceptees | Refusees | -->
        <?php switch ($_GET['e']) {
            case 'attente' :
                include("attente.php");
            break;
            case 'accepte' :
                include("accepte.php");
            break;
            case 'refuse' :
                include("refuse.php");
            break;
            // a rajouter corbeille ou pas
        }
        ?>

    </body>
</html>