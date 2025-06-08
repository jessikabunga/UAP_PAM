<?php
namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Plant extends Model
{
    protected $table = 'plants';

    protected $fillable = [
        'plant_name', 'description', 'price'
    ];
}
