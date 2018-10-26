package main;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import blocks.BlockChain;
import transactions.Transaction;
import wallets.Wallet;

public class Main {
	//https://medium.com/programmers-blockchain/creating-your-first-blockchain-with-java-part-2-transactions-2cdac335e0ce
	public static void main(String args[]) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		BlockChain blockChain = new BlockChain();
		
		
		Wallet minerWallet = new Wallet("2A00404B663D81A18D5CE616D6663DCCCEAC7C2F0F28EBF7C591B7BB05EE116E");
		Wallet wallet1 = new Wallet("ED319B4CFE85AD8485E033F72E93CEBFDEA6C9F7A158F7FB5468F3C4914B3432");
		
		wallet1.createTransaction(minerWallet.getPrivateKey(), 100, wallet1.getPublicKey());
	}
	
}
