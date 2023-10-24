package com.coderkan.services.impl;


import com.coderkan.models.Asset;
import com.coderkan.repositories.AssetRepository;
import com.coderkan.services.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@CacheConfig(cacheNames = "assetCache")
public class AssetServiceImpl implements AssetService {

	@Autowired
	private AssetRepository assetRepository;

	static final Logger LOGGER =
			Logger.getLogger(AssetServiceImpl.class.getName());

	@Cacheable(cacheNames = "assets")
	@Override
	public List<Asset> getAll() {
		waitSomeTime();
		return this.assetRepository.findAll();
	}

	@CacheEvict(cacheNames = "assets", allEntries = true)
	@Override
	public Asset add(Asset asset) {
		return this.assetRepository.save(asset);
	}

	@CacheEvict(cacheNames = "assets", allEntries = true)
	@Override
	public Asset update(Asset asset) {
		Optional<Asset> optAsset = this.assetRepository.findById(asset.getId());
		if (!optAsset.isPresent())
			return null;
		Asset repAsset = optAsset.get();
		repAsset.setName(asset.getName());
		repAsset.setSymbol(asset.getSymbol());
		repAsset.setUsdPrice(asset.getUsdPrice());
		return this.assetRepository.save(repAsset);
	}

	@Caching(evict = { @CacheEvict(cacheNames = "asset", key = "#id"),
			@CacheEvict(cacheNames = "assets", allEntries = true) })
	@Override
	public void delete(long id) {
		this.assetRepository.deleteById(id);
	}

	@Cacheable(cacheNames = "asset", key = "#id", unless = "#result == null")
	@Override
	public Asset getAssetById(long id) {
		//waitSomeTime();
		return this.assetRepository.findById(id).orElse(null);
	}

	private void waitSomeTime() {
		LOGGER.info("Long Wait Begin" );
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LOGGER.info("Long Wait End" );
	}

}