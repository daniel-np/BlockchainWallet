package transactions;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;

import javax.xml.bind.DatatypeConverter;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Sha256Hash;

public class TransactionUtil {

	public static String calculateHashID(String hashData) {
		// do sha256 on the data to make an ID
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			byte[] s1 = sha.digest(hashData.getBytes("UTF-8"));
			return new String(DatatypeConverter.printHexBinary(s1));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Sha256Hash hashInput(String input) {
		Sha256Hash hashInput = Sha256Hash.wrap(Sha256Hash.hash(input.getBytes()));
		return hashInput;
	}

	public static byte[] applySignature(String signature, Sha256Hash hashInput) {
		ECKey privateKey = getPrivateKeyFromHex(signature);
		ECKey.ECDSASignature sig = privateKey.sign(hashInput);
		return sig.encodeToDER();
	}

	public static boolean verifySignature(byte[] data, byte[] signature, byte[] publicKey) {
		return ECKey.verify(data, signature, publicKey);
	}

	public static ECKey getPrivateKeyFromHex(String privateKey) {
		BigInteger pk = new BigInteger(privateKey, 16);
		ECKey key = ECKey.fromPrivate(pk);
		return key;
	}
}
