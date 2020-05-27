<?php
require '../../vendor/autoload.php';
use \Mailjet\Resources;
$mj = new \Mailjet\Client('0904172806ab77b1da0f835836cbadc3','6c72c75e671030418bdcdc09004fee86',true,['version' => 'v3.1']);

$objet = strip_tags($_POST['input-objet-annonce']);
$texte = strip_tags($_POST['texte']);

foreach($listeDestinataire as $dest){
    $body = [
        'Messages' => [
            [
                'From' => [
                    'Email' => "reseauprevention31@gmail.com",
                    'Name' => "Prevention31"
                ],
                'To' => [
                    [
                        'Email' => $dest['mail'],
                        'Name' => $dest['nomUtilisateur'] . $dest['prenomUtilisateur']
                    ]
                ],
                'Subject' => $objet,
                'TextPart' => $texte,
                'HTMLPart' => $texte
            ]
        ]
    ];
    $response = $mj->post(Resources::$Email, ['body' => $body]);
    //$response->success() && var_dump($response->getData());    
}
?>