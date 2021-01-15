<?php
    ini_set('display_errors', 1);
    ini_set('display_startup_errors', 1);
    error_reporting(E_ALL);
    
    if(
           !empty($_POST['id_cube']) 
        || !empty($_POST['capacidad']) 
        || !empty($_POST['temperatura']) 
        || !empty($_POST['humo']) 
        || !empty($_POST['metano']) 
        || !empty($_POST['co2']) 
        || !empty($_POST['MQ2_resistencia']) 
        || !empty($_POST['MQ135_resistencia']) 
        ){
        $id_cube = $_POST['id_cube'];
        $capacidad = $_POST['capacidad'];
        $co2 = $_POST['co2'];
        $metano = $_POST['metano'];
        $humo = $_POST['humo'];
        $temperatura = $_POST['temperatura'];
        $resistance1 = $_POST['MQ2_resistencia'];
        $resistance2 = $_POST['MQ135_resistencia'];
    
    	insertCubeDataRecord($id_cube, $capacidad, $co2, $metano, $humo, $temperatura, $resistance1, $resistance2);
    }

    function insertCubeDataRecord($id_cube, $capacity, $co2, $methane, $smoke, $temperature, $resistance1, $resistance2){
        $host = "localhost";
        $dbname = "postgres";
        $username = "postgres";
        $password = "12345";

        $db_handle=pg_connect("host=$host dbname=$dbname user=$username password=$password");

        $sql = "SELECT MAX(id) FROM cube_data_record;";

        $result = pg_query($db_handle,$sql);

        $id = pg_fetch_row($result)[0]+1;

        $db_handle=pg_connect("host=$host dbname=$dbname user=$username password=$password");

        $timestamp = date("Y-m-d H:i:s");
        $data_timestamp = "'$timestamp'";
        $sql = "insert into CUBE_DATA_RECORD (id_cube, id, capacity, co2, methane, smoke, data_timestamp, temperature, MQ2_resistance, MQ135_resistance) values ($id_cube, $id, $capacity, $co2, $methane, $smoke, $data_timestamp, $temperature, $resistance1, $resistance2)";

        $result = pg_query($db_handle,$sql);
    }
?>
~
