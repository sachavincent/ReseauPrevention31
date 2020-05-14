<?php
session_start();

$_SESSION['modifie'] = false;

?>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <link rel="stylesheet" href="../style.css" />
    </head>

    <body>
        <!-- header -->
        <?php include("../page/headerForce.php"); ?>

        <!-- barre d'actions -->
        <div id="barre_actions"><input type="button" class="new_msg" value="Nouveau message" 
            onclick=window.location.href='../force/envoi-msg-fo.html';>
        </div>

        <!-- navigation gauche -->
        <?php include("../page/navForce.php"); ?>

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
        <form action="profil-fo.php" method="post">
        <section id="pan_infos_user">
            
            <!-- nom -->
            <p class="infos_user">Nom <input class="input_infos" name="nom" 
                value=<?php echo $_SESSION['nom'] ?> /></p>

            <!-- prenom -->
            <p class="infos_user">Prénom <input class="input_infos" name="prenom" 
                value=<?php echo $_SESSION['prenom'] ?> /></p>

            <!-- mail -->
            <p class="infos_user">Adresse Mail <input class="input_infos" name="mail" type="email" 
                value=<?php echo $_SESSION['mail'] ?> /></p>

            <!-- mdp actuel -->
            <p class="infos_user">Mot de passe actuel <input class="input_infos" name="mdp" type="password" required/></p>

            <!-- nouveau mdp -->
            <p class="infos_user">Nouveau mot de passe <input class="input_infos" name="new_mdp" type="password"/></p>

            <!-- confirmation mdp -->
            <p class="infos_user">Confirmation <input class="input_infos" name="confirm_mdp" type="password"/></p>

            <!-- bouton valider -->
            <p class="infos_user"><input class="valider_infos" type="submit" value="valider"
                onclick="modifier_profil.php" /></p>

            <!-- ligne d'erreur si la modification échoue -->
            <?php if (!$_SESSION['modifie']) { echo '<p id="erreur">Erreur lors de la modification du profil.</p>'; }?>

        </section>

    </body>
</html>