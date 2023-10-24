package com.coderkan.controllers;

import com.coderkan.models.Operation;
import com.coderkan.models.Wallet;
import com.coderkan.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class WalletController {
	@Autowired
	private WalletService walletService;

	@GetMapping(value = "/wallets")
	public ResponseEntity<Object> getAllWallets() {
		List<Wallet> wallets = this.walletService.getAll();
		return ResponseEntity.ok(wallets);
	}

	@GetMapping(value = "/wallets/operations/{id}")
	public ResponseEntity<Object> getOperationById(@PathVariable("id") String id) {
		Long _id = Long.valueOf(id);
		Operation operation = this.walletService.getOperationId(_id);
		return ResponseEntity.ok(operation);
	}

	@GetMapping(value = "/wallets/operations")
	public ResponseEntity<Object> getAllOperations() {
		List<Operation> operations = this.walletService.getAllTx();
		return ResponseEntity.ok(operations);
	}

	@GetMapping(value = "/wallets/{id}")
	public ResponseEntity<Object> getWalletById(@PathVariable("id") String id) {
		Long _id = Long.valueOf(id);
		Wallet wallet = this.walletService.getWalletById(_id);
		return ResponseEntity.ok(wallet);
	}

	@PostMapping(value = "/wallets")
	public ResponseEntity<Object> addWallet(@RequestBody Wallet wallet) {
		Wallet created = this.walletService.add(wallet);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@PostMapping(value = "/wallets/transferFrom")
	public ResponseEntity<Object> transferFrom(@RequestBody Operation operation) {
		Operation created = this.walletService.transferFrom(operation.getSourceWallet().getId(), operation.getTargetWallet().getId(), operation.getAmount(), operation.getAsset().getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@PutMapping(value = "/wallets")
	public ResponseEntity<Object> updateWallet(@RequestBody Wallet wallet) {
		Wallet updated = this.walletService.update(wallet);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping(value = "/wallets/{id}")
	public ResponseEntity<Object> deleteWalletById(@PathVariable("id") String id) {
		Long _id = Long.valueOf(id);
		this.walletService.delete(_id);
		return ResponseEntity.ok().build();
	}
}
