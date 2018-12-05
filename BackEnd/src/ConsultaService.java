import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.simpleframework.http.Query;
import org.simpleframework.http.Request;

public final class ConsultaService {
    ListaDeConsultaDAO ListaDeConsultaDAO = new ListaDeConsultaDAO();
    ListaDeResultadoDAO ListaDeResultadoDAO = new ListaDeResultadoDAO();

    public ConsultaService() {
    }

    public String adicionarConsulta(Request request) {
        String nome;
        String especialidade;
        String email;
        LocalDateTime dataConsulta;

        //Criando query para poder acessar os dados do site
        Query query = request.getQuery();

        //Obtendo valores digitados pelo usuario no site
        nome = query.get("Nome");
        especialidade = query.get("especialidade1");
        email = query.get("Email");
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        LocalDate date = LocalDate.parse(query.get("dataConsulta"), formatter);
        dataConsulta = date.atStartOfDay();
        System.out.println(nome);

        // Adicionando uma consulta a uma lista de consulta usando collection
        List<ListaDeConsulta> Lista = ListaDeConsultaDAO.getAll();

        // Filtro para verificar se o paciente existe na lista
        List<String> Paciente = Lista.stream()
                .filter(lista -> lista.getEmail().equals(email))
                .map(ListaDeConsulta::getEmail)
                .collect(Collectors.toList());

        // Se o paciente existe, sera mostrado o nome dele na tela
        if (Paciente.toString().replace("[", "").replace("]", "").equals(email))
           return "Falha";
        else {
            ListaDeConsultaDAO.add(new ListaDeConsulta(nome,email, especialidade, dataConsulta));

            // Retorna os dados sobre a consulta que acabou de ser registrada
            return ListaDeConsultaDAO.get(nome).toString();
        }


    }

    public String pesquisarConsulta(Request request) {
        //Criando query para poder acessar os dados do site
        Query query = request.getQuery();
        String Email = query.get("Email");

        List<ListaDeConsulta> Lista = ListaDeConsultaDAO.getAll();

        // Filtro para verificar se o paciente existe na lista
        List<String> Paciente = Lista.stream()
                .filter(lista -> lista.getEmail().equals(Email))
                .map(ListaDeConsulta::getEmail)
                .collect(Collectors.toList());

        // Se o paciente existe, sera mostrado o nome dele na tela
        if (Paciente.toString().replace("[", "").replace("]", "").equals(Email))
            return Lista.stream()
                    .filter(lista -> lista.getEmail().equals(Email))
                    .map(lista -> lista.toString())
                    .collect(Collectors.toList())
                    .toString().replace("[", "").replace("]", "");
        else return "Falha";

    }

    public String cancelarConsulta(Request request) {

        //Criando query para poder acessar os dados do site
        Query query = request.getQuery();
        String Nome = query.get("Nome");
        String Email = query.get("Email");

        // Retorna todos os dados sobre a consulta

        List<ListaDeConsulta> Lista = ListaDeConsultaDAO.getAll();

        List<String> PacienteNome = Lista.stream()
                .filter(lista -> {
                    if(lista.getNome().equals(Nome) && lista.getEmail().equals(Email))
                    return  lista.getEmail().equals(Email);
                    return  lista.getEmail().equals(Email);
                })
                .map(ListaDeConsulta::getEmail)
                .collect(Collectors.toList());

        // Se o paciente existe, sera mostrado o nome dele na tela
        if (PacienteNome.toString().replace("[", "").replace("]", "").equals(Email)) {
            ListaDeConsultaDAO.delete(new ListaDeConsulta(Nome, Email,"", LocalDateTime.now()));
            return "Paciente " + Email + " removido com sucesso.";
        } else return "Falha";

    }


    public String atualizarConsulta(Request request) {
        //Criando query para poder acessar os dados do site
        Query query = request.getQuery();


        String NomePaciente = query.get("Nome");
        String especialidade = query.get("especialidade1");
        String email = query.get("Email");
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        LocalDate date = LocalDate.parse(query.get("dataConsulta"), formatter);
        LocalDateTime dataConsulta = date.atStartOfDay();

        List<ListaDeConsulta> Lista = ListaDeConsultaDAO.getAll();

        // Filtro para verificar se o paciente existe na lista
        List<String> Paciente = Lista.stream()
                .filter(lista -> {
                    if(lista.getNome().equals(NomePaciente) && lista.getEmail().equals(email))
                        return  lista.getEmail().equals(email);
                    return  lista.getEmail().equals(email);
                })
                .map(ListaDeConsulta::getEmail)
                .collect(Collectors.toList());


        if (Paciente.toString().replace("[", "").replace("]", "").equals(email)) {
            ListaDeConsultaDAO.update(new ListaDeConsulta(NomePaciente, email, especialidade, dataConsulta));
            Lista = ListaDeConsultaDAO.getAll();
            return "Consulta do paciente " + NomePaciente + " atualizada com sucesso.\n\n" +
                    "Novos dados: \n" + Lista.stream()
                    .filter(lista -> lista.getNome().equals(NomePaciente))
                    .map(lista -> lista.toString())
                    .collect(Collectors.toList())
                    .toString().replace("[", "").replace("]", "");
        } else return "Falha";
    }

    public String verificaFila(Request request) {
        List<ListaDeConsulta> Lista = ListaDeConsultaDAO.getAll();
        //Criando query para poder acessar os dados do site
        Query query = request.getQuery();
        String Email = query.get("Email");


        for (int i = 0; i < Lista.size(); i++) {
            if (Lista.get(i).getEmail().equals(Email))
                return "O paciente " + Lista.get(i).getNome() + " esta na " + (i + 1) + "° posicao ";
        }
        return "Falha";
    }

    public String mandaResultado(Request request) {
        //Criando query para poder acessar os dados do site
        Query query = request.getQuery();

        //Obtendo valores digitados pelo usuario no site
        String email = query.get("Email");
        String resumo = query.get("Resumo");
        String nome = query.get("Nome");


        List<ListaDeConsulta> Lista = ListaDeConsultaDAO.getAll();

        // Filtro para verificar se o paciente existe na lista
        List<String> Paciente = Lista.stream()
                .filter(lista -> lista.getEmail().equals(email))
                .map(ListaDeConsulta::getEmail)
                .collect(Collectors.toList());

        // Se o paciente existe, sera mostrado o nome dele na tela
        if (Paciente.toString().replace("[", "").replace("]", "").equals(email)) {

            //Adiciona o paciente a lista com o resultado da consulta
            ListaDeResultadoDAO.add(new ListaDeResultado(email, resumo));

            ListaDeConsultaDAO.delete(new ListaDeConsulta(nome, email,"", LocalDateTime.now()));
            return "Registrado";
        }
        else
            return "Falha";


    }
    public String verificarStatusConsulta(Request request) {
        //Criando query para poder acessar os dados do site
        Query query = request.getQuery();

        //Obtendo valores digitados pelo usuario no site
        String email = query.get("Email");
        String resumo = query.get("Resumo");
        String nome = query.get("Nome");


        List<ListaDeResultado> List = ListaDeResultadoDAO.getAll();
        List<ListaDeConsulta> Lista = ListaDeConsultaDAO.getAll();

        // Filtro para verificar se o paciente existe na lista
        List<String> Paciente = Lista.stream()
                .filter(lista -> lista.getEmail().equals(email))
                .map(ListaDeConsulta::getEmail)
                .collect(Collectors.toList());

        List<String> ListResult = List.stream()
                .filter(lista -> lista.getEmail().equals(email))
                .map(ListaDeResultado::getEmail)
                .collect(Collectors.toList());

        // Se o paciente existe, sera mostrado o nome dele na tela
        if (Paciente.toString().replace("[", "").replace("]", "").equals(email))
            return "Falha1";
        else if(ListResult.toString().replace("[", "").replace("]", "").equals(email))
            return ListaDeResultadoDAO.get(email).toString();

        return "Falha";

    }
}
