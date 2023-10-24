package com.coderkan.services;

import com.coderkan.models.Operation;
import com.coderkan.models.Wallet;

import java.util.List;

public interface WalletService {

	public List<Wallet> getAll();

	public Wallet add(Wallet wallet);

	public Wallet update(Wallet customer);

	public void delete(long id);

	public Wallet getWalletById(long id);

	public Operation transferFrom(long SourceWalletId, long targetWalletId, int amount, long assetId);

	public Operation getOperationId(long id);

	public List<Operation> getAllTx();
}
