<?php
$dbhost = "localhost";
$dbname = "prm";
$dbuser = "root";
$dbpass = "123456";



// config for pusher
$pusherConfig=array(
    "app_id" => "419005",
    "key" => "ff3ea1c0573eb96b7ae2",
    "secret" => "b62da17ebc67e1fdc0dd",
    "cluster" => "ap1"
);

$pusherOptions = array(
    'cluster' => 'ap1',
    'encrypted' => true
);

error_reporting(0);

$mysqli = new mysqli($dbhost,$dbuser,$dbpass,$dbname);
if ($mysqli->connect_errno) {
    printf("Connection fail: %s\n", $mysqli->connect_error);
    exit();
}
$mysqli->set_charset('utf8');
?>