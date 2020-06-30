
<!-- ========== NEW CONSEIL =========== -->

<form method="post" action="../script/creationConseil.php">

<!-- barre d'envoi -->
<div id="barre-envoi">
    <input class="btn-new-annonce-conseil" type="submit" value="Envoyer" />
    <input class="btn-new-annonce-conseil" type="button" value="supprimer" onclick="window.location.href='demandes.php?e=prive&m=none'" />
</div>

<!-- zone de redaction de l'objet -->
<div id="objet-new-conseil">
    <fieldset>
    <legend>Objet du nouveau conseil :</legend>
    <input name='input-objet-conseil' required>
    <p class="user-concernes">à destination des utilisateurs de l'application mobile.</p>
    </fieldset>
</div>

<!-- zone de redaction du message -->
<div id="write-conseil">
    <textarea name="texte" required></textarea>
</div>

</form>
