package com.coderkan.services.impl;


import com.coderkan.models.Asset;
import com.coderkan.models.Operation;
import com.coderkan.models.Wallet;
import com.coderkan.repositories.AssetRepository;
import com.coderkan.repositories.OperationRepository;
import com.coderkan.repositories.WalletRepository;
import com.coderkan.services.WalletService;
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
@CacheConfig(cacheNames = "walletCache")
public class WalletServiceImpl implements WalletService {

	@Autowired
	private WalletRepository walletRepository;
	@Autowired
	private OperationRepository operationRepository;

	@Autowired
	private AssetRepository assetRepository;

	static final Logger LOGGER =
			Logger.getLogger(WalletServiceImpl.class.getName());


	@Cacheable(cacheNames = "wallets")
	@Override
	public List<Wallet> getAll() {
		//waitSomeTime();
		return this.walletRepository.findAll();
	}

	@CacheEvict(cacheNames = "wallets", allEntries = true)
	@Override
	public Wallet add(Wallet wallet) {
		return this.walletRepository.save(wallet);
	}

	@CacheEvict(cacheNames = "wallets", allEntries = true)
	@Override
	public Wallet update(Wallet wallet) {
		Optional<Wallet> optWallet = this.walletRepository.findById(wallet.getId());
		if (!optWallet.isPresent())
			return null;
		Wallet repWallet = optWallet.get();
		repWallet.setName(wallet.getName());
		repWallet.setAmount(wallet.getAmount());
		repWallet.setAsset(wallet.getAsset());
		repWallet.setOwner(wallet.getOwner());
		return this.walletRepository.save(repWallet);
	}

	@Caching(evict = { @CacheEvict(cacheNames = "wallet", key = "#id"),
			@CacheEvict(cacheNames = "wallets", allEntries = true) })
	@Override
	public void delete(long id) {
		this.walletRepository.deleteById(id);
	}

	@Cacheable(cacheNames = "wallet", key = "#id", unless = "#result == null")
	@Override
	public Wallet getWalletById(long id) {
		//waitSomeTime();
		return this.walletRepository.findById(id).orElse(null);
	}


	public Operation transferFrom(long sourceWalletId, long targetWalletId, int amount, long assetId) {
		//waitSomeTime();

		//add new Operation
		Wallet sourceWalletObject = walletRepository.findById(sourceWalletId).get();
		Wallet targetWalletObject = walletRepository.findById(targetWalletId).get();
		Asset assetObject = assetRepository.findById(assetId).get();
		Operation operation = new Operation();
		operation.setSourceWallet(sourceWalletObject);
		operation.setTargetWallet(targetWalletObject);
		operation.setAmount(amount);
		operation.setAsset(assetObject);
		add(operation);


		//update sourceWallet sub amount
		sourceWalletObject.setAmount(sourceWalletObject.getAmount()-amount);
		this.update(sourceWalletObject);
		//update targetWallet add amount
		targetWalletObject.setAmount(targetWalletObject.getAmount()+amount);
		this.update(targetWalletObject);

		return operation;

	}

	@CacheEvict(cacheNames = "operations", allEntries = true)
	public Operation add(Operation operation) {

		return this.operationRepository.save(operation);
	}

	public Operation getOperationId(long id){
		return this.operationRepository.findById(id).orElse(null);
	}

	public List<Operation> getAllTx(){
		return this.operationRepository.findAll();
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