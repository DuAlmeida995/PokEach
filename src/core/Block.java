package core;

import java.security.MessageDigest;
import java.util.Date;
import java.util.List;


public class Block {
    private String hash;
    private String previousHash;
    private List<Transaction> transactions; // aqui vai ser a lista de capturas e trocas
    private long timestamp;
    private int nonce;

    public Block(String previousHash, List<Transaction> transactions) {
        this.previousHash = previousHash;
        this.transactions = transactions;
        this.timestamp = new Date().getTime();
        this.nonce = 0;
        this.hash = calculateHash();
    }

    public String calculateHash() {
        // aqui pegamos todos os dados do bloco e transforma em uma String gigante
        String dataToHash = previousHash + Long.toString(timestamp) + Integer.toString(nonce) + transactions.toString();
        
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(dataToHash.getBytes("UTF-8"));
            StringBuilder buffer = new StringBuilder();
            for (byte b : bytes) {
                buffer.append(String.format("%02x", b));
            }
            return buffer.toString();
        } catch (Exception e) {
            throw new RuntimeException("erro ao calcular hash", e);
        }
    }

    // aqui eh o algoritmo de mineracao (Proof of Work)
    // o 'difficulty' vai ser a quantidade de zeros que o Hash deve ter no começo
    public void mineBlock(int difficulty) {
        // criamos uma string cheia de zeros baseada na dificuldade (ex: difficulty 3 = "000")
        String target = new String(new char[difficulty]).replace('\0', '0');
        
        // ai fica recalculando o Hash sem parar ate que ele comece com os zeros exigidos
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Bloco Minerado com sucesso! Hash válido: " + hash);
    }
    
    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public List<Transaction> getTransactions() {
        return transactions; // em um sistema super restrito, retornariamos uma copia nao modificavel desta lista, mas pro ep aqui acho q n precisa
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getNonce() {
        return nonce;
    }

    // Override do toString para facilitar o debug nosso terminalzinho
    @Override
    public String toString() {
        return "Block{" +
                "\n  hash='" + hash + '\'' +
                ",\n  previousHash='" + previousHash + '\'' +
                ",\n  timestamp=" + timestamp +
                ",\n  nonce=" + nonce +
                ",\n  transactions=" + transactions.size() +
                "\n}";
    }

}