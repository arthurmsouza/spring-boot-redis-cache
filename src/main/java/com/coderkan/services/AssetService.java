package com.coderkan.services;

import com.coderkan.models.Asset;
import com.coderkan.models.Wallet;

import java.util.List;

public interface AssetService {

	public List<Asset> getAll();

	public Asset add(Asset asset);

	public Asset update(Asset asset);

	public void delete(long id);

	public Asset getAssetById(long id);

}
