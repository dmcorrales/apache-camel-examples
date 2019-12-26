<?php

namespace App;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Facades\DB;

class Orders extends Model
{
    public static function generteOrder($cod_user,$codPago,$services,$products,$addr,$phone)
    {
        $res=1;
        try
        {
            $sql="INSERT INTO order_prod(total_price,date_order,date_deliver,quant_order,cod_t_order,cod_user,cod_prod,cod_state,cod_f_pago,addr_order,phone_order) VALUES";
            for($i=0;$i<count($products);$i++)
            {
                $sql=$sql."(".$products[$i]['total_price'].",'".date("Y-m-d")."','".$products[$i]['date_deliver']."',".$products[$i]['quant_order'].",".$products[$i]['cod_t_order'].",".$cod_user.",".$products[$i]['cod_prod'].",1,".$codPago.",".$addr.",".$phone.")";
                if(($i+1)!=count($products))
                {
                    $sql=$sql.",";
                }
            }
            if(DB::insert($sql))
            {
                $sql="INSERT INTO order_service(date_order,date_serv,hour_serv,addr_client,vehicule,total_price,cod_user,cod_serv,cod_state,cod_f_pago,addr_order,phone_order) VALUES";
                for($i=0;$i<count($services);$i++)
                {
                    $sql=$sql."('".date("Y-m-d")."','".$services[$i]['date_serv']."',".$services[$i]['hour_serv'].",'".$services[$i]['addr_client']."',".$services[$i]['vehicule'].",".$services[$i]['total_price'].",".$cod_user.",".$services[$i]['cod_serv'].",1,".$codPago.",".$addr.",".$phone.")";
                    if(($i+1)!=count($services))
                    {
                        $sql=$sql.",";
                    }
                }
                if(DB::insert($sql))
                {
                    $res=2;
                }
            }            
            return $res;
        }
        catch(\Exception $e)
        {
            return -1;
        }        
    }

    public static function getShopOrdersProducts($cod_shop)
    {
        $sql="SELECT f_pago.nom_f_pago,order_prod.total_price, order_prod.quant_order,order_prod.date_deliver,order_prod.date_order,order_prod.addr_order,order_prod.phone_order,product.nom_prod,usuario.mail_user
        FROM usuario,order_prod,product,f_pago WHERE product.cod_shop=? AND order_prod.cod_state=1 AND order_prod.cod_user=usuario.cod_user AND order_prod.cod_prod=product.cod_prod AND order_prod.cod_f_pago=f_pago.cod_f_pago";
        return DB::select($sql,[$cod_shop]);
    }

    public static function getShopOrdersServices($cod_shop)
    {
        $sql="SELECT order_service.date_order,order_service.date_serv,order_service.addr_order,order_service.total_price,order_service.phone_order,order_service.vehicule,f_pago.nom_f_pago,hour.nom_hour,
        usuario.mail_user,service.nom_serv FROM order_service,hour,usuario,service,f_pago
        WHERE order_service.cod_state=1 AND service.cod_shop=? AND order_service.cod_f_pago=f_pago.cod_f_pago AND order_service.cod_user=usuario.cod_user AND order_service.hour_serv=hour.cod_hour AND order_service.cod_serv=service.cod_serv";
        return DB::select($sql,[$cod_shop]);
    }
}