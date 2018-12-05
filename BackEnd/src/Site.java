import java.io.PrintStream;
import java.util.List;


import org.json.JSONException;
import org.json.JSONObject;

import org.simpleframework.http.Query;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.Status;
import org.simpleframework.http.core.Container;

public class Site implements Container {


    static ConsultaService consultaService;


    public void handle(Request request, Response response) {
        try {
            // Recupera a URL e o método utilizado.

            request.getContent();

            String path = request.getPath().getPath();
            String method = request.getMethod();
            String mensagem;

            // Verifica qual ação está sendo chamada

            if (path.startsWith("/adicionarConsulta") && "GET".equals(method)) {
                mensagem = consultaService.adicionarConsulta(request);
                this.enviaResposta(Status.CREATED, response, mensagem);
            } else if (path.startsWith("/pesquisarConsulta") && "GET".equals(method)) {
                mensagem = consultaService.pesquisarConsulta(request);
                    this.enviaResposta(Status.CREATED, response, mensagem);
            } else if (path.startsWith("/cancelarConsulta") && "GET".equals(method)) {
                mensagem = consultaService.cancelarConsulta(request);
                    this.enviaResposta(Status.CREATED, response, mensagem);

            } else if (path.startsWith("/atualizarConsulta") && "GET".equals(method)) {
                mensagem = consultaService.atualizarConsulta(request);
                    this.enviaResposta(Status.CREATED, response, mensagem);

            } else if (path.startsWith("/verificaFila") && "GET".equals(method)) {
                mensagem = consultaService.verificaFila(request);
                    this.enviaResposta(Status.CREATED, response, mensagem);

            }  else if (path.startsWith("/mandaResultado") && "GET".equals(method)) {
                mensagem = consultaService.mandaResultado(request);
                this.enviaResposta(Status.CREATED, response, mensagem);

            }else if (path.startsWith("/verificarStatusConsulta") && "GET".equals(method)) {
                mensagem = consultaService.verificarStatusConsulta(request);
                this.enviaResposta(Status.CREATED, response, mensagem);

            } else {
                this.naoEncontrado(response, path);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void naoEncontrado(Response response, String path) throws Exception {
        JSONObject error = new JSONObject();
        error.put("error", "Não encontrado.");
        error.put("path", path);
        enviaResposta(Status.NOT_FOUND, response, error.toString());
    }

    private void pacienteNaoEncontrado(Response response, Request request) throws Exception {
        Query query = request.getQuery();
        String NomePaciente = query.get("Nome");
        enviaResposta(Status.NOT_FOUND, response, "Paciente " + NomePaciente + " nao encontrado.");
    }

    private void enviaResposta(Status status, Response response, String str) throws Exception {

        PrintStream body = response.getPrintStream();
        long time = System.currentTimeMillis();

        response.setValue("Content-Type", "text/plain");
        response.setValue("Server", "Controle de consultaService (1.0)");
        response.setValue("Access-Control-Allow-Origin", "*");

        response.setDate("Date", time);
        response.setDate("Last-Modified", time);
        response.setStatus(status);

        if (str != null)
            body.println(str);
        body.close();
    }
}

