<?php 
unset($_SESSION);
session_start();
$_SESSION['connexion'] = false;

?>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <link rel="stylesheet" href="../style.css" />
    </head>

    <body>
        <!-- header -->
        <header>
            <title>Identification</title>
        </header>

        <!-- conteneur principal -->
        <section id="page_identification">
            <!-- conteneur identification -->
            <section id="panel_choix">
                <h2>IDENTIFICATION</h2>

                <!-- choix avec selection de logos -->
                <div id="logo_ca">
                    <a href="identification.php?chambre=CA">
                    <img src="../images/ca.png" type="submit" name='chambre' width="180px" height="180px" />
                    </a>
                </div>

                <div id="logo_cma">
                    <a href="identification.php?chambre=CMA">
                    <img src="../images/cma.png" type="submit" name='chambre' width="180px" height="180px" />
                    </a>
                </div>

                <div id="logo_cci">
                    <a href="identification.php?chambre=CCI">
                    <img src="../images/cci.png" type="submit" name='chambre' width="180px" height="180px" />
                    </a>
                </div>

                <div id="logo_gendarmerie">
                    <a href="identification.php?chambre=G">
                    <img src="../images/gendarmerie.png" type="submit" name='force' width="180px" height="180px" />
                    </a>
                </div>
                         
                <div id="logo_police">
                    <a href="identification.php?chambre=P">
                    <img src="../images/police.png" width="180px" height="180px" />
                    </a>
                </div>

            </section>
        </section>

    </body>
</html>