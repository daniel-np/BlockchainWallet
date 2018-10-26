package wallets;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

import org.bitcoinj.core.ECKey;

import transactions.Transaction;
import transactions.UTXOset;


public class Wallet{
	
	private String privateKey;
	private String publicAddress;
	private String publicKey;
	private AddressUtil util = new AddressUtil();
	private UTXOset utxoSet;
	private ArrayList<Transaction> inputs = new ArrayList<Transaction>();
	
	public String getPrivateKey() {
		return privateKey;
	}
	
	public Wallet() {
		try {
			System.out.println("Creating a new wallet:");
			privateKey = util.createPrivateKey();
			publicAddress = util.createAddress(privateKey);
			publicKey = util.derivePublicKeyHex(privateKey);
		} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public Wallet(String privateKey) {
		this.privateKey = privateKey;
		try {
			System.out.println("Opening existing wallet: ");
			publicAddress = util.createAddress(privateKey);
			publicKey = util.derivePublicKeyHex(privateKey);
		} catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | NoSuchProviderException
				| UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public void createTransaction(String privateKey, Integer value, String recipient) {
		Transaction transaction = new Transaction(privateKey, publicAddress, value, recipient);
		transaction.processTransaction();
	}

	public String getPublicAddress() {
		return publicAddress;
	}

	public String getPublicKey() {
		return publicKey;
	}
}
