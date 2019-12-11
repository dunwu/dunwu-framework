package io.github.dunwu.util.code;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public interface ISignature {

    default KeyPair genKeyPair(String algorithm, int keySize)
        throws NoSuchAlgorithmException {

        // 初始化密钥对生成器
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(algorithm);
        // 实例化密钥对生成器
        keyPairGen.initialize(keySize);
        // 实例化密钥对
        return keyPairGen.genKeyPair();
    }

    byte[] sign(byte[] plaintext, byte[] privateKey) throws Exception;

    boolean verify(byte[] plaintext, byte[] publicKey, byte[] ciphertext)
        throws Exception;

    byte[] getPrivateKey();

    byte[] getPublicKey();

}
