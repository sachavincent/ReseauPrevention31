<?php
$objet = strip_tags($_POST['input-objet-annonce']);
$texte = strip_tags($_POST['texte']);

foreach($listeDestinataire as $dest){
    shell_exec("echo '" . $texte . "' | mail -s '" . $objet . "' " . $dest['mail']); 
}
?>

