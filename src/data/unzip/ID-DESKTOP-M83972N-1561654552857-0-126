<?php

namespace App;
use Illuminate\Support\Facades\DB;

use Illuminate\Database\Eloquent\Model;

class State extends Model
{
    public static function getAll()
    {
        $states=DB::select('SELECT * FROM state');
        return $states;
    }
}
