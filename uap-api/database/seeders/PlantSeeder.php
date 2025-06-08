<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use App\Models\Plant;
use GuzzleHttp\Client;

class PlantSeeder extends Seeder
{
    public function run()
    {
        $client = new Client();

        // Ganti ini dengan URL API eksternal yang valid
        $response = $client->get('https://uappam.kuncipintu.my.id/plant/all');
        $body = $response->getBody()->getContents();

        dd($body);
        foreach ($plants as $plant) {
            Plant::updateOrCreate(
                ['plant_name' => $plant['plant_name']],
                [
                    'description' => $plant['description'],
                    'price' => $plant['price'],
                ]
            );
        }
    }
}