<!-- chargement de la BDD -->
<?php include("../script/chargerInfos.php");?>

<!-- ============================================== -->
<form method="post" action="../script/creationConseil.php">
<!-- barre d'actions -->
<div id="barre-actions">
  <input type="button" class="new-annonce" value="+ Annonce" onclick="window.location.href='demandes.php?e=new_annonce'" />
  <input type="button" class="new-conseil" value="+ Conseil" onclick="window.location.href='demandes.php?e=new_conseil'" />
  <!-- actions de la demande ouverte -->
  <input class="envoyer" type="submit" value="Envoyer" />
  <input class="actions" type="button" value="supprimer" onclick="window.location.href='demandes.php?e=prive&m=none'" />
</div>

<!-- barre grise a droite (decoratif) -->
<div id="deco-droite"></div>

<!-- ============================================== -->

<!-- affichage menu gauche -->
<?php include("page/navForce.php"); ?>

<!-- ============================================== -->

<!-- pan liste msg -->
<section id="pan-content">
  <div id="objet-new-conseil">
    <b><u>Objet :</u></b>
    <input name="input-objet-conseil" />
    <p id="user-concernes">à destination des utilisateurs de l'application mobile.</p>
  </div>

  <!-- zone de redaction du message -->
  <textarea name="texte" id="write-conseil"></textarea>
</section>

</form>
