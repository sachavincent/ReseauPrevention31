<!-- chargement de la BDD -->
<?php include("../script/chargerInfos.php");?>

<!-- ============================================== -->

<!-- barre d'actions -->
<div id="barre_actions">
    <input type="button" class="new_msg" value="Nouveau message" onclick=window.location.href='demandes.php?e=new_msg&m=none'>

    <!-- actions rapides -->
    <img class="refresh_rapide" src="../images/refresh.png" onclick="window.location.href='demandes.php?e=conseil&m=none'"/>

    <!-- actions de la demande ouverte -->
    <input class="actions" type="button" value="supprimer" onclick="window.location.href='../script/gestionBoutons.php?e=conseil&m=<?php echo $_GET['m'] ?>&b=supprimer'"/> 
</div>

<!-- barre grise a droite (decoratif) -->
<div id="deco_droite"></div>

<!-- ============================================== -->

<!-- affichage menu gauche -->
<?php include("page/navForce.php"); ?>

<!-- ============================================== -->

<!-- affichage des onglets -->
<?php include("../script/afficherInfos.php"); ?>

<!-- ============================================== -->

