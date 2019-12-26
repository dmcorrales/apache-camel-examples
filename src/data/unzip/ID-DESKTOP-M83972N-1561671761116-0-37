<?php

namespace App;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Facades\DB;

class Product extends Model
{
    public static function getAllCategories()
    {
        return DB::select('SELECT * FROM category');
    }

    public static function getByName($name)
    {
        return DB::select('SELECT * FROM product WHERE nom_prod=?',[$name]);
    }

    public static function insert($name,$desc,$img,$quant,$price,$cod_cat,$cod_shop)
    {
        try
        {
            $sql='INSERT INTO product(nom_prod, desc_prod, img_prod, quant_prod, price_prod, cod_cat, cod_shop, cod_state) VALUES (?,?,?,?,?,?,?,1)';
            DB::insert($sql,[$name,$desc,$img,$quant,$price,$cod_cat,$cod_shop]);
            return true;
        }
        catch(Exception $e)
        {
            return false;
        }
    }

    public static function getByStateCatShop($cod_state,$cod_cat,$cod_shop)
    {
        $sql='SELECT product.cod_prod, product.nom_prod, product.desc_prod, product.img_prod, product.quant_prod, product.price_prod, product.off_price, shop.cod_shop, shop.nom_shop, shop.nit_shop, shop.addr_shop, shop.phon_shop, shop.img_shop, city.nom_city, category.nom_cat FROM product,city,shop,category WHERE product.cod_cat=category.cod_cat AND product.cod_shop = shop.cod_shop AND city.cod_city = shop.cod_city AND product.cod_state = ? AND product.cod_cat=? AND product.cod_shop=? ORDER BY product.nom_prod';
        return DB::select($sql,[$cod_state,$cod_cat,$cod_shop]);
    }

    public static function getByStateCity($cod_state,$nom_sity)
    {
        $sql='SELECT product.cod_prod, product.nom_prod, product.desc_prod, product.img_prod, product.quant_prod, product.price_prod, product.off_price, shop.cod_shop, shop.nom_shop, shop.nit_shop, shop.addr_shop, shop.phon_shop, shop.img_shop, city.nom_city, category.nom_cat FROM product,city,shop,category WHERE product.cod_state = ? AND city.nom_city=? AND product.cod_cat=category.cod_cat AND product.cod_shop = shop.cod_shop AND city.cod_city = shop.cod_city ORDER BY product.nom_prod';
        return DB::select($sql,[$cod_state,$nom_sity]);
    }

    public static function getById($cod_prod)
    {
        $sql='SELECT product.cod_prod, product.nom_prod, product.desc_prod, product.img_prod, product.quant_prod, product.price_prod, product.off_price, shop.cod_shop, shop.nom_shop, shop.nit_shop, shop.addr_shop, shop.phon_shop, shop.img_shop, city.nom_city, category.nom_cat FROM product,city,shop,category WHERE product.cod_prod=? AND city.nom_city=? AND product.cod_cat=category.cod_cat AND product.cod_shop = shop.cod_shop AND city.cod_city = shop.cod_city';
        return DB::select($sql,[$cod_prod]);
    }

    public static function getOrdered($cod_prod)
    {
        $sql='SELECT COUNT(order_prod.cod_prod) AS total FROM order_prod WHERE order_prod.cod_prod=? AND (order_prod.cod_state=1 OR order_prod.cod_state=3)';
        $count=json_decode(json_encode(DB::select($sql,[$cod_prod])),true);
        return $count[0]['total'];
    }

    public static function deleteProd($cod_prod)
    {
        try
        {
            $sql='UPDATE product SET cod_state=5 WHERE cod_prod=?';
            DB::update($sql,[$cod_prod]);
            return true;
        }
        catch(Exception $e)
        {
            return false;
        }            
    }
}
