package network;

import java.net.ServerSocket;
import java.net.Socket;

// a classe herda de Thread para rodar em segundo plano sem travar o jogo
public class SocketServer extends Thread {
    
    private int porta;
    private boolean rodando;

    public SocketServer(int porta) {
        this.porta = porta;
        this.rodando = true;
    }

    // metodo que o Java executa automaticamente em background quando damos um .start()
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(porta);
            System.out.println("[REDE] Servidor P2P escutando na porta: " + porta);

            // loop infinito: o servidor fica sempre acordado esperando novos vizinhos
            while (rodando) {
                // O codigo pausa nesta linha ate que outro computador bata na porta
                Socket socketVizinho = serverSocket.accept();
                
                String ipVizinho = socketVizinho.getInetAddress().getHostAddress();
                System.out.println("[REDE] Novo vizinho conectado! IP: " + ipVizinho);

                java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(socketVizinho.getInputStream()));
                String mensagemRecebida = in.readLine();
                System.out.println("[REDE] Mensagem recebida: " + mensagemRecebida);
            }
            
            serverSocket.close();
            
        } catch (Exception e) {
            System.out.println("[REDE] Erro fatal no servidor: " + e.getMessage());
        }
    }

    public void pararServidor() {
        this.rodando = false;
    }
}