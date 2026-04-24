package network;

import core.Blockchain;
import java.util.ArrayList;
import java.util.List;

public class P2PNode {
    
    private Blockchain blockchain;
    private int portaLocal;
    private List<String> vizinhosConhecidos; // lista de nos na rede (Ex: "192.168.0.5:8082")
    private SocketServer servidor;

    public P2PNode(Blockchain blockchain, int portaLocal) {
        this.blockchain = blockchain;
        this.portaLocal = portaLocal;
        this.vizinhosConhecidos = new ArrayList<>();
    }

    public void iniciar() {
        // Passamos 'this' (o próprio P2PNode) para o servidor, assim ele poderá
        // repassar as mensagens recebidas para a nossa Blockchain no futuro.
        servidor = new SocketServer(portaLocal);
        servidor.start();
    }

    public void parar() {
        if (servidor != null) {
            servidor.pararServidor();
        }
    }

    // ==========================================
    // GOSSIP PROTOCOL (A Fofoca da Rede)
    // ==========================================

    // adiciona o IP e porta de um colega da equipe à lista de contatos
    public void adicionarVizinho(String ip, int porta) {
        String endereco = ip + ":" + porta;
        if (!vizinhosConhecidos.contains(endereco)) {
            vizinhosConhecidos.add(endereco);
            System.out.println("[NODE] Novo vizinho adicionado à lista de rotas: " + endereco);
        }
    }

    // pega uma mensagem e dispara para todos os vizinhos conhecidos simultaneamente.
    public void broadcast(String mensagem) {
        System.out.println("[NODE] Iniciando Broadcast para " + vizinhosConhecidos.size() + " vizinhos...");
        
        for (String vizinho : vizinhosConhecidos) {
            // quebra a string "127.0.0.1:8082" em IP e Porta
            String[] partes = vizinho.split(":");
            String ip = partes[0];
            int porta = Integer.parseInt(partes[1]);
            
            SocketClient.enviarMensagem(ip, porta, mensagem);
        }
    }

    public Blockchain getBlockchain() {
        return blockchain;
    }
}