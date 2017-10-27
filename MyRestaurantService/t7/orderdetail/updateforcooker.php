<?php
$id = intval($_POST['id']);
$order_id = intval($_POST['order_id']);
$status = intval($_POST['status']);
if($id>0 && $status){
	$sql =  "UPDATE `order_detail` SET `status`={$status} WHERE `id` = {$id} ";
	$mysqli->query($sql);
    $sql =  "SELECT * FROM `order_detail` WHERE `order_id` = {$order_id} ";
    $rs = $mysqli->query($sql);
    $details = $rs->fetch_all(MYSQLI_ASSOC);
    $outp['result'] = array();
    foreach($details as $detail){
        $psql =  "SELECT * FROM `products` WHERE `id`={$detail['product_id']}";
        $prs = $mysqli->query($psql);
        $detail['product'] = array_pop($prs->fetch_all(MYSQLI_ASSOC));
        $outp['result'][] = $detail;
    }
    $outp['size'] = $rs->num_rows;
    $outp['hasErr'] = false;
}
?>