package com.spon.utils.sc.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 
 * </BR>�[�K�y�{ ��NString #�Y-->DES�[�K-->��XBASE64 --->socket </BR>�ѱK�y�{
 * socket-->�ѽXBASE64 -->DES�[�K�٭� -->String ��#�Y
 */
public class Security {

	/**
	 * 
	 */
	public Security() {
		// TODO Auto-generated constructor stub
	}

	public String decode(String cipherText) throws Exception {
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		// System.out.println( "\n" + cipher.getProvider().getInfo() );
		//
		// encrypt using the key and the plaintext

		Key myprikey = getkey();
		sun.misc.BASE64Decoder decoderBASE64 = new sun.misc.BASE64Decoder();
		cipherText=cipherText.replaceAll("\\{;\\}","+");
		byte[] byteText = decoderBASE64.decodeBuffer(cipherText);
		cipher.init(Cipher.DECRYPT_MODE, myprikey);

		String plainText = "";

		plainText = UnGzip(cipher.doFinal(byteText));

		return plainText;
	}

	public String encode(String plainText) throws Exception {

		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		// System.out.println( "\n" + cipher.getProvider().getInfo() );
		//
		// encrypt using the key and the plaintext

		Key myprikey = getkey();

		cipher.init(Cipher.ENCRYPT_MODE, myprikey);

		// System.out.println( new String(cipherText) );
		String cipherText = "";
		byte[] b = null;

		// �P�_�O�_�n#�Y���

		b = Gzip(plainText);

		// �s�XBASE 64
		sun.misc.BASE64Encoder encodeBASE64 = new sun.misc.BASE64Encoder();
		cipherText = encodeBASE64.encode(cipher.doFinal(b));
		cipherText=cipherText.replaceAll("\\+","{;}");
		return cipherText;
	}

	private Key getkey() throws Exception {

		// KeyGenerator generator = KeyGenerator.getInstance("DES");

		byte[] password = new byte[8192];

		byte[] pass = "spon".getBytes();
		for (int i = 0; i < pass.length; i++) {
			password[i] = pass[i];
		}

		DESKeySpec dks = new DESKeySpec(password);

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey myprikey = keyFactory.generateSecret(dks);

		return myprikey;
	}

	private byte[] Gzip(String object) {
		byte[] data = null;
		try {
			ByteArrayOutputStream o = new ByteArrayOutputStream();
			GZIPOutputStream gzout = new GZIPOutputStream(o);
			ObjectOutputStream out = new ObjectOutputStream(gzout);
			out.writeObject(object);
			out.flush();
			out.close();
			gzout.close();
			data = o.toByteArray();
			o.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

	private String UnGzip(byte[] aa) {

		String object_ = null;
		try {
			ByteArrayInputStream i = new ByteArrayInputStream(aa);
			GZIPInputStream gzin = new GZIPInputStream(i);
			ObjectInputStream in = new ObjectInputStream(gzin);
			object_ = (String) in.readObject();
			i.close();
			gzin.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object_;
	}

	public static void main(String[] args) {

		try {
			Security se = new Security();

			String a = "select * from aaccaa where aa='aa' and bb='bb' ";

			String en = se.encode(a);

			System.out.println(en.length() + " " + en);
			String dn = se.decode(en);
			System.out.println(dn.length() + " " + dn);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}