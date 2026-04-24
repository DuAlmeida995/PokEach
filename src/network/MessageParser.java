package network;

public class MessageParser {

    private P2PNode node;

    // o Parser precisa conhecer o no para poder mexer na blockchain dele
    public MessageParser(P2PNode node) {
        this.node = node;
    }

    //metodo principal que será chamado sempre que o servidor ouvir algo
    public void processarMensagem(String mensagem) {
        if (mensagem == null || mensagem.trim().isEmpty()) {
            return;
        }

        try {
            // Divide o texto em duas partes: o COMANDO e o RESTO (Payload)
            // Exemplo de entrada: "PING|Olá vizinho"
            String[] partes = mensagem.split("\\|", 2);
            String comando = partes[0];
            String dados = (partes.length > 1) ? partes[1] : "";

            switch (comando) {
                case "PING":
                    System.out.println("[PARSER] Mensagem de controle recebida: " + dados);
                    break;
                    
                case "TX":
                    System.out.println("[PARSER] Nova Transação recebida pela rede!");
                    // TODO: nas prox fases, vamos pegar a String 'dados', remontar o 
                    // objeto Transaction e chamar o node.getBlockchain().adicionarTransacao()
                    break;
                    
                case "BLOCK":
                    System.out.println("[PARSER] Novo Bloco recebido! Analisando Proof of Work...");
                    // TODO: remontar o bloco e verificar se ele eh valido antes de aceitar
                    break;
                    
                default:
                    System.out.println("[PARSER] Comando desconhecido ignorado: " + comando);
            }
        } catch (Exception e) {
            System.out.println("[PARSER] Erro ao tentar entender a mensagem da rede: " + e.getMessage());
        }
    }
}