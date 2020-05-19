<!-- chargement de la BDD -->
<?php include("../script/chargerInfos.php");?>

<!-- ============================================== -->

<!-- barre d'actions -->
<div id="barre-actions">
  <input type="button" class="new-annonce" value="+ Annonce" onclick=window.location.href='demandes.php?e=new_annonce'>
  <input type="button" class="new-conseil" value="+ Conseil" onclick=window.location.href='demandes.php?e=new_conseil'>
  <!-- actions rapides -->
  <img class="refresh-rapide" src="../images/refresh.png" onclick="window.location.href='demandes.php?e=prive&m=none'"/>
  <!-- actions de la demande ouverte -->
  <input class="actions" type="button" value="supprimer" onclick="window.location.href='../script/gestionBoutons.php?e=prive&m=<?php echo $_GET['m'] ?>&b=supprimer'"/> 
</div>

<!-- barre grise a droite (decoratif) -->
<div id="deco-droite"></div>

<!-- ============================================== -->

<!-- affichage menu gauche -->
<?php include("page/navForce.php"); ?>

<!-- ============================================== -->

<!-- affichage des onglets -->
<?php include("../script/afficherInfos.php"); ?>

<!-- ============================================== -->

