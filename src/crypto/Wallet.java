package crypto;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Base64;

public class Wallet {
    
    private PrivateKey chavePrivada;
    private PublicKey chavePublica;

    // a carteira ja eh criada assim que o jogador abre o jogo pela primeira vez

    public Wallet() {
        gerarParDeChaves();
    }

    private void gerarParDeChaves() {
        try {
            // utilizamos o algoritmo RSA (o mesmo exigido pela nossa classe Transaction)
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            
            // o SecureRandom nos garante que a semente matematica seja real caotica e imprevisivei vei
            SecureRandom random = SecureRandom.getInstanceStrong();
            
            // 2048 ouvi falar que eh um bom padrao do mercado que n pesa mto na cpu
            keyGen.initialize(2048, random); 
            
            KeyPair pair = keyGen.generateKeyPair();
            this.chavePrivada = pair.getPrivate();
            this.chavePublica = pair.getPublic();
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar as chaves criptográficas da carteira", e);
        }
    }

    // metodo para imprimir a chave publica como uma string legivel (Base64)
    // util para mostrar na tela do jogo: "O seu ID de Treinador meu consagrado é: MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8A..."
    public String getEnderecoPublico() {
        return Base64.getEncoder().encodeToString(chavePublica.getEncoded());
    }

    public PrivateKey getChavePrivada() {
        return chavePrivada;
    }

    public PublicKey getChavePublica() {
        return chavePublica;
    }
}