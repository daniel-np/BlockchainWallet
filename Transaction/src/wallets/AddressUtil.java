package wallets;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.ECGenParameterSpec;
import java.util.Arrays;

import org.bitcoinj.core.Base58;
import org.bitcoinj.core.ECKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class AddressUtil {
	public String createAddress(String privateKey) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException,
			NoSuchProviderException, UnsupportedEncodingException {

		// ECDSA public key
		String bcPublicKey = derivePublicKeyHex(privateKey);

		// Perform SHA-256
		MessageDigest sha = MessageDigest.getInstance("SHA-256");
		byte[] s1 = sha.digest(bcPublicKey.getBytes("UTF-8"));
		// System.out.println(" sha: " + bytesToHex(s1).toUpperCase());

		// Perform RIPEMD-160
		Security.addProvider(new BouncyCastleProvider());
		MessageDigest rmd = MessageDigest.getInstance("RipeMD160", "BC");
		byte[] r1 = rmd.digest(s1);

		// Adding a version byte to the beginning of the hash
		byte[] r2 = new byte[r1.length + 1];
		r2[0] = 0;
		for (int i = 0; i < r1.length; i++)
			r2[i + 1] = r1[i];
		// System.out.println(" rmd: " + bytesToHex(r2).toUpperCase());

		// Repeat SHA-256 hash twice
		byte[] s2 = sha.digest(r2);
		// System.out.println(" sha: " + bytesToHex(s2).toUpperCase());
		byte[] s3 = sha.digest(s2);
		// System.out.println(" sha: " + bytesToHex(s3).toUpperCase());

		// First 4 bytes of the second hash is added to the RIPEMD160 hash
		byte[] a1 = new byte[25];
		for (int i = 0; i < r2.length; i++)
			a1[i] = r2[i];
		for (int i = 0; i < 4; i++)
			a1[21 + i] = s3[i];

		// Encode in Base58
		String publicAddress = Base58.encode(a1);
		System.out.println("Public Address: " + publicAddress);
		return publicAddress;
	}

	public String createPrivateKey() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException {
		// Create KeyPair for EC generator
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");

		// Using secp256k1 elliptic curve
		ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256k1");
		keyGen.initialize(ecSpec);

		// Generate a private and a public key
		KeyPair keyPair = keyGen.generateKeyPair();
		PrivateKey privateKey = keyPair.getPrivate();

		// ECDSA private key
		String ePrivate = ecdsaPrivateKey(privateKey);
		return ePrivate;
	}

	public String derivePublicKeyHex(String privateKey) {
		BigInteger pk = new BigInteger(privateKey, 16);
		ECKey key = ECKey.fromPrivate(pk);
		String hexKey = key.getPublicKeyAsHex();
		return hexKey;
	}
	
	public byte[] derivePublicKeyBytes(String privateKey) {
		BigInteger pk = new BigInteger(privateKey, 16);
		ECKey key = ECKey.fromPrivate(pk);
		byte[] byteKey = key.getPubKey();
		return byteKey;
	}

	private String ecdsaPrivateKey(PrivateKey privateKey) {
		ECPrivateKey ePrivateKey = (ECPrivateKey) privateKey;
		String ePrivateKeyString = adjustTo64(ePrivateKey.getS().toString(16).toUpperCase());
		System.out.println("Private Key: " + ePrivateKeyString);
		return ePrivateKeyString;
	}

	// Adds padding to get a 64 character string
	private String adjustTo64(String s) {
		switch (s.length()) {
		case 62:
			return "00" + s;
		case 63:
			return "0" + s;
		case 64:
			return s;
		default:
			throw new IllegalArgumentException("Not a valid key: " + s);
		}
	}

	private static final String ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";

	/**
	 * https://rosettacode.org/wiki/Bitcoin/address_validation#Java
	 * 
	 * @param addr
	 * @return true/false
	 */
	public boolean validateBitcoinAddress(String addr) {
		if (addr.length() < 26 || addr.length() > 35)
			return false;
		byte[] decoded = decodeBase58To25Bytes(addr);
		if (decoded == null)
			return false;

		byte[] hash1 = sha256(Arrays.copyOfRange(decoded, 0, 21));
		byte[] hash2 = sha256(hash1);

		return Arrays.equals(Arrays.copyOfRange(hash2, 0, 4), Arrays.copyOfRange(decoded, 21, 25));
	}

	private byte[] decodeBase58To25Bytes(String input) {
		BigInteger num = BigInteger.ZERO;
		for (char t : input.toCharArray()) {
			int p = ALPHABET.indexOf(t);
			if (p == -1)
				return null;
			num = num.multiply(BigInteger.valueOf(58)).add(BigInteger.valueOf(p));
		}

		byte[] result = new byte[25];
		byte[] numBytes = num.toByteArray();
		System.arraycopy(numBytes, 0, result, result.length - numBytes.length, numBytes.length);
		return result;
	}

	private byte[] sha256(byte[] data) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(data);
			return md.digest();
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		}
	}

	public boolean validPrivateKey(String privateKey) {
		if (privateKey.length() != 64) {
			return false;
		} else if (privateKey.matches("^[0]+$")) {
			return false;
		}
		return true;
	}

}
