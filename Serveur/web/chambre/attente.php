<!-- chargement de la BDD -->
<?php include("../script/chargerInfos.php"); ?>

<!-- ============================================== -->

<!-- barre d'actions -->
<div id="barre_actions">
    <input type="button" class="new_msg" value="Demandes" onclick=window.location.href='demandes.php?e=attente&m=none'>

    <!-- actions rapides -->
    <img class="refresh_rapide" src="../images/refresh.png" onclick="window.location.href='demandes.php?e=attente&m=none'"/>

    <!-- actions de la demande ouverte -->
    <input class="actions" type="button" value="accepter" onclick="window.location.href='../script/gestionBoutons.php?e=attente&m=<?php echo $_GET['m'] ?>&b=accepter'"/> 
    <input class="actions" type="button" value="refuser" onclick="window.location.href='../script/gestionBoutons.php?e=attente&m=<?php echo $_GET['m'] ?>&b=refuser'"/> 
</div>

<!-- barre grise a droite (decoratif) -->
<div id="deco_droite"></div>

<!-- ============================================== -->

<!-- affichage menu gauche -->
<?php include("page/navChambre.php"); ?>

<!-- ============================================== -->

<!-- affichage des onglets -->
<?php include("../script/afficherInfos.php"); ?>

<!-- ============================================== -->