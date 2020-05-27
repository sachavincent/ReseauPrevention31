<!-- chargement de la BDD -->
<?php include("../script/chargerInfos.php"); ?>

<!-- ============================================== -->

<!-- barre d'actions -->
<div id="barre-actions">
  <input type="button" class="demandes" value="Demandes" onclick="window.location.href='demandes.php?e=attente&m=none'">
  <!-- actions rapides -->
  <img class="refresh-rapide" src="../images/refresh.png" onclick="window.location.href='demandes.php?e=refuse&m=none'"/>  
  <!-- actions de la demande ouverte -->
  <input class="actions" type="button" value="accepter" onclick="window.location.href='../script/gestionBoutons.php?e=refuse&m=<?php echo $_GET['m'] ?>&b=accepter'"/> 
</div>

<!-- barre grise a droite (decoratif) -->
<div id="deco-droite"></div>

<!-- ============================================== -->

<!-- affichage menu gauche -->
<?php include("page/navChambre.php"); ?>

<!-- ============================================== -->

<!-- affichage des onglets -->
<?php include("../script/afficherInfos.php"); ?>

<!-- ============================================== -->