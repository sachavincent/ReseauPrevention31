<!-- chargement de la BDD -->
<?php include("../script/chargerInfos.php");?>

<!-- ============================================== -->

<!-- barre d'actions -->
<!-- <div id="barre-actions">
  <input type="button" class="new-annonce" value="+ Annonce" onclick="window.location.href='demandes.php?e=new_annonce'" />
  <input type="button" class="new-conseil" value="+ Conseil" onclick="window.location.href='demandes.php?e=new_conseil'" />

  <img class="refresh-rapide" src="../images/refresh.png" onclick="window.location.href='demandes.php?e=conseil&m=none'" />

  <input class="actions" type="button" value="supprimer" onclick="window.location.href='../script/gestionBoutons.php?e=conseil&m=<?php echo $_GET['m'] ?>&b=supprimer'" />
</div> -->

<!-- ============================================== -->

<!-- affichage des onglets -->
<?php include("../script/afficherInfos.php"); ?>

<!-- ============================================== -->

