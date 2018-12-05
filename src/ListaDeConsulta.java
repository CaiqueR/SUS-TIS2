import org.json.JSONObject;
import java.time.LocalDateTime;
import java.util.List;

public class ListaDeConsulta implements JsonFormatter, ListaDAO {
    public static final String DESCRICAO_PADRAO = "Nova Consulta";
    private String nome, especialidade, email;
    private LocalDateTime dataConsulta;


    private static int cont = 0;
    private static int instancias = 0;

    public ListaDeConsulta(String n, String em, String e, LocalDateTime d) {
        setNome(n);
        setEmail(em);
        setEspecialidade(e);
        setDataConsulta(d);
        instancias++;
    }

    public ListaDeConsulta() {
        nome = DESCRICAO_PADRAO;
        especialidade = null;
        email = null;
        dataConsulta = LocalDateTime.now();

        instancias++;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome.length() >= 3)
            this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email.length() >= 3)
            this.email = email;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public LocalDateTime getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(LocalDateTime dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public static int getCont() {
        return cont;
    }

    public static int getInstancias() {
        return instancias;
    }


    /**
     * Método toString sobreposto para exibir em String o dados
     * Nome do paciente
     * Especialidade do Medico
     * Data da consulta
     */
    @Override
    public String toString() {
        return "Nome do Paciente: " + nome +"\nEmail: "+email+ "   \nEspecialidade do medico: " + especialidade
                + "\nData: " + dataConsulta + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        return this.nome.equals(((ListaDeConsulta) obj).getNome());
    }

    @Override
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("nome", this.getNome());
        obj.put("especialidade", this.getEspecialidade());
        obj.put("dataConsulta", this.getDataConsulta());
        obj.put("email", this.getEmail());
        return obj;
    }

    @Override
    public Object get(Object chave) {
        return null;
    }

    @Override
    public void add(Object p) {

    }

    @Override
    public ListaDAO update(Object p) {
        return null;
    }

    @Override
    public ListaDAO delete(Object p) {
        return null;
    }

    @Override
    public List getAll() {
        return null;
    }
}



