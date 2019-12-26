<?php

namespace App;

use Illuminate\Auth\Authenticatable;
use Laravel\Lumen\Auth\Authorizable;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Contracts\Auth\Authenticatable as AuthenticatableContract;
use Illuminate\Contracts\Auth\Access\Authorizable as AuthorizableContract;
use Tymon\JWTAuth\Contracts\JWTSubject;
use Illuminate\Support\Facades\DB;

class User extends Model implements JWTSubject, AuthenticatableContract, AuthorizableContract
{
    use Authenticatable, Authorizable;

    /**
     * The table associated with the model.
     *
     * @var string
     */
    protected $table = 'usuario';

    /**
     * The PRIMARY KEY associated with the table.
     *
     * @var string
     */
    protected $primaryKey="cod_user";

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'cod_user', 'login_user', 'mail_user', 'cod_t_user', 'img_user'
    ];

    /**
     * The attributes excluded from the model's JSON form.
     *
     * @var array
     */
    protected $hidden = [
        'pass_user',
    ];

    public function getJWTIdentifier()
    {
        return $this->getKey();
    }

    public function getJWTCustomClaims()
    {
        return [];
    }

    /********************************************************
     * Select Methods
     */

    /**
     * Get all users with usuario params.
     * No relations
     */
    public static function getAll()
    {
        $users=DB::select('SELECT cod_user,nom_user, login_user, mail_user, img_user, date_login, cod_t_user, cod_state FROM usuario');
        return $users;
    }

    /**
     * Get user by id, 
     */
    public static function getById($id)
    {
        $user=DB::select('SELECT cod_user,nom_user, login_user, mail_user, img_user, date_login, cod_t_user, cod_state FROM usuario WHERE cod_user=?',[$id]);
        return $user;
    } 

    public static function getByLoginMail($login,$mail)
    {
        $user=DB::select('SELECT cod_user,nom_user, login_user, mail_user, pass_user, img_user, date_login, cod_t_user, cod_state, id_user FROM usuario WHERE login_user=? OR mail_user=?',[$login,$mail]);
        return $user;
    }

    /***********************************************************
     * Insert User Methods
     */

    /**
     * Insert a new User
     * @param name Name of the user
     * @param login User Name for login of the user
     * @param mail Mail of the user
     * @param pass Password of the user
     * 
     * @return boolean
     */
    public static function insert($name,$login,$mail,$pass,$addr)
    {
        $pass=hash('sha256' ,md5($pass));
        try
        {
            $query='INSERT INTO usuario(nom_user, login_user, mail_user, pass_user, img_user, date_login, addr_user, cod_t_user, cod_state) VALUES (?, ?, ?, ?, "def.png", ?, ?, 2, 1)';
            DB::insert($query,[$name,$login,$mail,$pass,date('Y-m-d'),$addr]);
            return true;
        }
        catch(Exception $e)
        {
            return false;
        }
    }

    /**********************************************************
     * SQL STATEMENTS
     */

    /**********************************************************
     * Update User methods
     */

    /**
      * Update user Date Login
      * @param Cod_user Integer, user's code or ID on table
      * 
      * @return boolean
      */
    public static function updateDateLogin($cod_user)
    {
        try
        {
            DB::update('UPDATE usuario SET date_login=? WHERE cod_user=?',[date('Y-m-d H:i:s'),$cod_user]);
            return true;
        }
        catch(Exception $e)
        {
            return false;
        }
    }

    public static function updateImage($img_user,$cod_user)
    {
        try
        {
            DB::update('UPDATE usuario SET img_user=? WHERE cod_user=?',[$img_user,$cod_user]);
            return true;
        }
        catch(Exception $e)
        {
            return false;
        }
    }

    public static function updateInfo($nom_user,$login_user,$mail_user,$id_user,$cod_user)
    {
        try
        {
            DB::update('UPDATE usuario SET nom_user=?, login_user=?, mail_user=?, id_user=? WHERE cod_user=?',[$nom_user,$login_user,$mail_user,$id_user,$cod_user]);
            return true;
        }
        catch(Exception $e)
        {
            return false;
        }
    }
}
