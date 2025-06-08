<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Plant;

class PlantController extends Controller
{
    public function getAll() {
        return response()->json(Plant::all());
    }

    public function getByName($name) {
        $plant = Plant::where('plant_name', $name)->first();
        if (!$plant) return response()->json(['message' => 'Tanaman tidak ditemukan'], 404);
        return response()->json($plant);
    }

    public function create(Request $request) {
        $this->validate($request, [
            'plant_name' => 'required|unique:plants',
            'description' => 'required',
            'price' => 'required',
        ]);

        $plant = Plant::create($request->all());
        return response()->json($plant, 201);
    }

    public function update(Request $request, $name) {
        $plant = Plant::where('plant_name', $name)->first();
        if (!$plant) return response()->json(['message' => 'Tanaman tidak ditemukan'], 404);

        $plant->update($request->all());
        return response()->json($plant);
    }

    public function delete($name) {
        $plant = Plant::where('plant_name', $name)->first();
        if (!$plant) return response()->json(['message' => 'Tanaman tidak ditemukan'], 404);

        $plant->delete();
        return response()->json(['message' => 'Tanaman berhasil dihapus']);
    }
}

