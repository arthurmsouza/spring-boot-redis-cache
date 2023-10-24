package com.coderkan.controllers;

import com.coderkan.models.Asset;
import com.coderkan.services.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class AssetController {
	@Autowired
	private AssetService assetService;

	@GetMapping(value = "/assets")
	public ResponseEntity<Object> getAllAssets() {
		List<Asset> assets = this.assetService.getAll();
		return ResponseEntity.ok(assets);
	}

	@GetMapping(value = "/assets/{id}")
	public ResponseEntity<Object> getAssetById(@PathVariable("id") String id) {
		Long _id = Long.valueOf(id);
		Asset asset = this.assetService.getAssetById(_id);
		return ResponseEntity.ok(asset);
	}

	@PostMapping(value = "/assets")
	public ResponseEntity<Object> addAsset(@RequestBody Asset asset) {
		Asset created = this.assetService.add(asset);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@PutMapping(value = "/assets")
	public ResponseEntity<Object> updateAsset(@RequestBody Asset asset) {
		Asset updated = this.assetService.update(asset);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping(value = "/assets/{id}")
	public ResponseEntity<Object> deleteAssetById(@PathVariable("id") String id) {
		Long _id = Long.valueOf(id);
		this.assetService.delete(_id);
		return ResponseEntity.ok().build();
	}
}
