<?php
$dbhost = "localhost";
$dbname = "dbname";
$dbuser = "dbuser";
$dbpass = "dbpass";

// error_reporting(0);

$mysqli = new mysqli($dbhost,$dbuser,$dbpass,$dbname);
if ($mysqli->connect_errno) {
    printf("Connection fail: %s\n", $mysqli->connect_error);
    exit();
}
$mysqli->set_charset('utf8');
?>