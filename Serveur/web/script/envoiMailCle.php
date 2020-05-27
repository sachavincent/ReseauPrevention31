<?php 
$requete = $bdd->prepare('SELECT nomUtilisateur, prenomUtilisateur, mail FROM Utilisateur WHERE cle = ?');
$requete->execute(array($cle));
$infoUtilisateur = $requete->fetch();
/*
This call sends a message to one recipient.
*/
require 'vendor/autoload.php';
use \Mailjet\Resources;
$mj = new \Mailjet\Client('0904172806ab77b1da0f835836cbadc3','6c72c75e671030418bdcdc09004fee86',true,['version' => 'v3.1']);
$body = [
    'Messages' => [
        [
            'From' => [
                'Email' => "reseauprevention31@gmail.com",
                'Name' => "Prevention31"
            ],
            'To' => [
                [
                    'Email' => $infoUtilisateur['mail'],
                    'Name' => $infoUtilisateur['nomUtilisateur'] . $infoUtilisateur['prenomUtilisateur']
                ]
            ],
            'Subject' => "Validation de votre inscription",
            'TextPart' => "Bonjour, suite à votre inscription sur l'application prevention31 voici votre de clé de connexion unique:" . $cle,
            'HTMLPart' => "<p> Bonjour,<br><br> suite à votre inscription sur l'application prevention31 voici votre de clé de connexion unique: </p> <p><h2>" . $cle ."</h2></p>" 
        ]
    ]
];
$response = $mj->post(Resources::$Email, ['body' => $body]);
$response->success() && var_dump($response->getData());
?>