<!-- chargement de la BDD -->
<?php include("../script/chargerInfos.php");?>

<!-- ============================================== -->

<!-- barre d'actions -->
<div id="barre-actions">
  <input type="button" class="new-annonce" value="+ Annonce" onclick=window.location.href='demandes.php?e=new_annonce'>
  <input type="button" class="new-conseil" value="+ Conseil" onclick=window.location.href='demandes.php?e=new_conseil'>
  <!-- actions de la demande ouverte -->
  <input class="actions" type="button" value="Envoyer"/> 
  <input class="actions" type="button" value="supprimer"/>
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
      <b>Objet :</b>
      <input name='input-objet-conseil'>
  </div>

  <!-- zone de redaction du message -->
  <textarea id="write-msg"></textarea>

</section>
