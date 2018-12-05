import org.simpleframework.http.core.Container;
import org.simpleframework.http.core.ContainerSocketProcessor;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URI;

public class Main {
    public static void main(String[] list) throws Exception {

        // Instancia o para o ConsultaService
        Site.consultaService = new ConsultaService();

        // Porta para iniciar o servidor.
        // Se aparecer este erro "Address already in use: bind error", alterando a porta pode resolver.

        int porta = 880;

        // Configura uma conexão soquete para o servidor HTTP.
        Container container = new Site();
        ContainerSocketProcessor servidor = new ContainerSocketProcessor(container);
        Connection conexao = new SocketConnection(servidor);
        SocketAddress endereco = new InetSocketAddress(porta);
        conexao.connect(endereco);

        /*try {
            // Testa a conexão abrindo o navegador padrãoo.
            Desktop.getDesktop().browse(new URI("file:///C:/Users/Caique/Documents/Tis%202/Sus%20Tis2/consulta.html"));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de caminho inválido. ",
                    JOptionPane.ERROR_MESSAGE);
        }*/
        System.out.println("Tecle ENTER para interromper o servidor...");
        System.in.read();

        conexao.close();
        servidor.stop();

    }
}
