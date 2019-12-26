<?php

namespace App;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Facades\DB;

class Service extends Model
{
    public static function getTServices()
    {
        return DB::select('SELECT * FROM t_service');
    }

    public static function getByName($name)
    {
        return DB::select('SELECT * FROM service WHERE nom_serv=?',[$name]);
    }

    public static function insert($name,$desc,$price,$qhr,$tservice,$shop)
    {
        try
        {
            $sql='INSERT INTO service(nom_serv, desc_serv, price_serv, quant_hour, cod_t_serv, cod_shop, cod_state) VALUES (?, ?, ?, ?, ?, ?, 1)';
            DB::insert($sql,[$name,$desc,$price,$qhr,$tservice,$shop]);
            return true;
        }
        catch(Exception $e)
        {
            return false;
        }
    } 

    public static function getByStateShop($state,$cod_shop)
    {
        $sql='SELECT service.cod_serv, service.nom_serv, service.desc_serv, service.price_serv, service.quant_hour, shop.cod_shop, shop.nom_shop, shop.addr_shop, shop.img_shop, shop.phon_shop, city.nom_city FROM service, shop, city WHERE service.cod_shop=shop.cod_shop AND shop.cod_city=city.cod_city AND service.cod_state=? AND service.cod_shop=? ORDER BY service.nom_serv';
        return DB::select($sql,[$state,$cod_shop]);
    }

    public static function getByStateCity($state,$nom_city)
    {
        $sql='SELECT service.cod_serv, service.nom_serv, service.desc_serv, service.price_serv, service.quant_hour, shop.cod_shop, shop.nom_shop, shop.addr_shop, shop.img_shop, shop.phon_shop, city.nom_city FROM service, shop, city WHERE service.cod_shop=shop.cod_shop AND shop.cod_city=city.cod_city AND service.cod_state=? AND city.nom_city=? ORDER BY service.nom_serv';
        return DB::select($sql,[$state,$nom_city]);
    }

    public static function getServicesByShop($cod_shop)
    {
        $sql='SELECT service.cod_serv, service.nom_serv,t_service.img_t_service,service.price_serv,service.desc_serv,service.quant_hour FROM service,shop,t_service WHERE shop.cod_shop=? AND service.cod_shop=shop.cod_shop AND service.cod_t_serv=t_service.cod_t_serv;';
        return DB::select($sql,[$cod_shop]);
    }

    public static function getCountAssigns($date)
    {
        $sql='SELECT hour.nom_hour AS nom_time,service.quant_hour, COUNT(order_service.cod_ord_serv) AS total FROM order_service,hour,service WHERE order_service.date_serv=? AND (order_service.cod_serv=? OR order_service.cod_serv=?) AND order_service.hour_serv=hour.cod_hour AND order_service.cod_serv=service.cod_serv GROUP BY nom_time,quant_hour';
        return DB::select($sql,[$date,1,3]);
    }

    public static function verifyServiceDisponibility($codService,$dateService,$codHour)
    {
        $sql="SELECT sf_v_s_d(?,?,?) AS salida;";
        $result=json_decode(json_encode(DB::select($sql,[$codService,$dateService,$codHour])),true);
        return $result[0]['salida'];
    }
}
