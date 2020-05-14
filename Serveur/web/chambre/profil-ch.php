<?php
session_start();
?>

<!DOCTYPE html>
<html>
    <head>
        <title>Profil</title>
        <meta charset="utf-8" />
        <link rel="stylesheet" href="../style.css" />
    </head>

    <body>
        <!-- header -->
        <?php include("page/headerChambre.php"); ?>

        <!-- barre d'actions -->
        <div id="barre_actions">
            <input class="new_msg" type="button" value="Demandes" onclick=window.location.href='../chambre/demandes.php?e=attente&m=none'>
        </div>
        
        <!-- affichage menu gauche -->
        <?php include("page/navChambre.php"); ?>

        <!-- ============================================== -->

        <!-- page du profil -->
        <section id="pan_profil">
            <aside>
                <h1>Mon Profil</h1>
            </aside>
        <!-- conteneur image de profil avec nom prenom -->
        <section id="pan_nom_prenom">
            <!-- affichage nom / prenom -->
            <p class="img_profil"><img src="../images/user.png"></p><p class="affichage_nom"> <?php echo $_SESSION['nom'] ?> </p>
            <p class="affichage_prenom"> <?php echo $_SESSION['prenom'] ?> </p>
        </section>

        <!-- ============================================== -->

        <!-- conteneur avec les infos modifiables -->
        <form action="script/modifier_profil.php?return=none" method="post">
        <section id="pan_infos_user">
            <!-- nom -->
            <p class="infos_user">Nom <input class="input_infos" name="nom"
                value=<?php echo $_SESSION['nom']; ?> /></p>
            <!-- prenom -->
            <p class="infos_user">Prénom <input class="input_infos" name="prenom"
                value=<?php echo $_SESSION['prenom']; ?> /></p>
            <!-- mail -->
            <p class="infos_user">Adresse Mail <input class="input_infos" name="mail" type="email"
                value=<?php echo $_SESSION['mail']; ?> /></p>
            <!-- mdp actuel -->
            <p class="infos_user">Mot de passe actuel <input class="input_infos" name="mdp" type="password" required/></p>
            <!-- nouveau mdp -->
            <p class="infos_user">Nouveau mot de passe <input class="input_infos" name="nouveauMdp" type="password"/></p>
            <!-- confirmation mdp -->
            <p class="infos_user">Confirmation <input class="input_infos" name="confirmationMdp" type="password"/></p>
            <!-- bouton valider -->
            <p class="infos_user"><input class="valider_infos" type="submit" value="valider" /></p>
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