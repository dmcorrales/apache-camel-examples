<?php

namespace App;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Facades\DB;

class Shop extends Model
{
    /**
     * ******* WITH LARAVEL TRANSACTION ***********
     */
    /*
    public static function updateUserAndInsert($cod_user,$id_user,$nit_shop,$nom_shop,$addr_shop,$phon_shop,$vehicule)
    {
        $transaction="
            BEGIN;
                UPDATE usuario SET id_user=?, cod_t_user=3 WHERE cod_user=?;
                INSERT INTO shop(nit_shop, nom_shop, addr_shop, phon_shop, img_shop, cod_city, cod_user, cod_state) VALUES ('93498567234',?,'crr56 n° 165 - 67','4562347658','def.png',1,3,1);
            COMMIT;";
        try
        {
            $params=[$cod_user,$id_user,$nit_shop,$nom_shop,$addr_shop,$phon_shop,$vehicule];
            DB::transaction(function ($params) use ($params) 
            {
                DB::table('usuario')->where([
                        ['cod_user','=',$params[0]]
                    ])->update([
                        'id_user' => $params[1],
                        'cod_t_user' => 3
                    ]);
            
                DB::table('shop')->insert([
                        'nit_shop' => $params[2],
                        'nom_shop' => $params[3],
                        'addr_shop' => $params[4],
                        'phon_shop' => $params[5],
                        'img_shop' => 'def.png',
                        'cod_city' => 1,
                        'cod_user' => $params[0],
                        'cod_state' => 1,
                        'vehicule' => $params[6]
                    ]
                );
            });
            return true;
        }
        catch(Exception $e)
        {
            return false;
        }

    }
    */

    /**
     * Insert by values script given
     * 
     * @param String sql Values sintax, ([value]),([value]),([value]),([value])
     * 
     * @return boolean
     */
    public static function createSchedule($sqlValues)
    {
        try
        {
            $sql="INSERT INTO sched(cod_shop, cod_day, cod_hour) VALUES".$sqlValues;
            DB::insert($sql);
            return true;
        }
        catch(Excpetion $e)
        {
            return false;
        }
    }

    public static function createValuesSQLSched($cod_shop,$data)
    {
        $sqlValues="";
        for($i=0;$i<count($data);$i++)
        {
            for($j=0;$j<count($data[$i]['hours']);$j++)
            {
                $sqlValues=$sqlValues."(".$cod_shop.",".$data[$i]['cod_day'].",".$data[$i]['hours'][$j].")";
                if($j+1<count($data[$i]['hours']))
                {
                    $sqlValues=$sqlValues.",";
                }
            }
            if($i+1<count($data))
            {
                $sqlValues=$sqlValues.",";
            }
        }
        return $sqlValues;
    }

    /**
     * Get All Active Shops
     */
    public static function getAllShopsByState($cod_state)
    {
        $sql="SELECT shop.cod_shop, shop.nit_shop, shop.nom_shop, shop.addr_shop, shop.phon_shop, shop.img_shop, usuario.cod_user, city.cod_city, city.nom_city 
        FROM shop,city, usuario, state 
        WHERE shop.cod_city=city.cod_city AND shop.cod_user=usuario.cod_user AND shop.cod_state=state.cod_state AND state.cod_state =?";
        return DB::select($sql,[$cod_state]);
    }

    /**
     * Get shop by city
     */
    public static function getAllShopsByCity($nom_city)
    {
        $sql="SELECT shop.cod_shop, shop.nit_shop, shop.nom_shop, shop.addr_shop, shop.phon_shop, shop.special, shop.img_shop,shop.vehicule,shop.vehicule_price, usuario.cod_user, city.cod_city, city.nom_city
        FROM shop,city, usuario, state 
        WHERE shop.cod_city=city.cod_city AND shop.cod_user=usuario.cod_user AND shop.cod_state=state.cod_state AND state.cod_state =1 AND city.nom_city=?";
        return DB::select($sql,[$nom_city]);
    }

    /**
     * Get shop by user
     */
    public static function getAllShopsByUSer($cod_user)
    {
        $sql="SELECT shop.cod_shop, shop.nit_shop, shop.nom_shop, shop.addr_shop, shop.phon_shop, shop.img_shop, usuario.cod_user, city.cod_city, city.nom_city 
        FROM shop,city, usuario, state
        WHERE shop.cod_city=city.cod_city AND shop.cod_user=usuario.cod_user AND shop.cod_state=state.cod_state AND state.cod_state =1 AND usuario.cod_user=?";
        return DB::select($sql,[$cod_user]);
    }

    /**
     * Get shop by user
     */
    public static function getFirstShopsByUSer($cod_user)
    {
        $sql="SELECT shop.cod_shop, shop.nit_shop, shop.nom_shop, shop.addr_shop, shop.phon_shop, shop.img_shop, usuario.cod_user, city.cod_city, city.nom_city 
        FROM shop,city, usuario, state
        WHERE shop.cod_city=city.cod_city AND shop.cod_user=usuario.cod_user AND shop.cod_state=state.cod_state AND state.cod_state =1 AND usuario.cod_user=? LIMIT 1";
        return DB::select($sql,[$cod_user]);
    }

    /**
     * Get shop by cod_shop and cod_user
     */
    public static function getShopByIdAndUser($cod_shop,$cod_user)
    {
        $sql="SELECT shop.cod_shop, shop.nit_shop, shop.nom_shop, shop.addr_shop, shop.phon_shop, shop.img_shop, usuario.cod_user, city.cod_city, city.nom_city
        FROM shop,city, usuario, state
        WHERE shop.cod_city=city.cod_city AND shop.cod_user=usuario.cod_user AND shop.cod_state=state.cod_state AND state.cod_state =1 AND shop.cod_shop=? AND usuario.cod_user=?";
        return DB::select($sql,[$cod_shop,$cod_user]);
    }

    /**
     * Get shop by nom_shop
     */
    public static function getShopByName($nom_shop)
    {
        $sql="SELECT shop.cod_shop, shop.nit_shop, shop.nom_shop, shop.addr_shop, shop.phon_shop, shop.img_shop
        FROM shop
        WHERE shop.nom_shop=?";
        return DB::select($sql,[$nom_shop]);
    }

    public static function updateStateById($cod_shop)
    {
        $sql='UPDATE shop SET cod_state = ? WHERE cod_shop = ?';
        try
        {
            DB::update($sql,[2,$cod_shop]);
            return true;
        }
        catch(Exception $e)
        {
            return false;
        }
    }

    /**
     * All cities
     */
    public static function getCities()
    {
        return DB::select('SELECT * FROM city ORDER BY nom_city');
    } 

    public static function getCategoriesCountShop($cod_shop)
    {
        $sql="SELECT category.cod_cat,category.nom_cat,shop.nom_shop,img_cat, COUNT(product.cod_prod) AS quant FROM shop, category,product WHERE shop.cod_shop=? AND shop.cod_state=1 AND product.cod_state=1 AND product.cod_shop=shop.cod_shop AND product.cod_cat=category.cod_cat GROUP BY category.cod_cat, category.nom_cat,shop.nom_shop,img_cat";
        return DB::select($sql,[$cod_shop]);
    }

    public static function getSchedByDay($cod_shop,$day)
    {
        $sql='SELECT hour.cod_hour,hour.nom_hour AS nom_time FROM sched,shop,hour,day WHERE sched.cod_shop=? AND day.nom_day=? AND sched.cod_shop=shop.cod_shop AND sched.cod_day=day.cod_day AND sched.cod_hour=hour.cod_hour';
        return DB::select($sql,[$cod_shop,$day]);
    }

    /**
     * Method used on dearch bar
     * Browse shop by name or city, using LIKE %abc% STATEMENT
     */
    public static function getShopBrowse($name,$state)
    {
        $sql="SELECT shop.cod_shop, shop.nit_shop, shop.nom_shop, shop.addr_shop, shop.phon_shop, shop.special, shop.img_shop,shop.vehicule,shop.vehicule_price, usuario.cod_user, city.cod_city, city.nom_city
        FROM shop,city, usuario, state WHERE (shop.nom_shop LIKE ? OR city.nom_city LIKE ?) AND shop.cod_state=? AND shop.cod_city=city.cod_city AND shop.cod_user=usuario.cod_user AND shop.cod_state=state.cod_state ORDER BY shop.nom_shop ASC";
        return DB::select($sql,['%'.$name.'%','%'.$name.'%',$state]);
    }
}
