import org.json.JSONObject;
import java.time.LocalDateTime;
import java.util.List;

public class ListaDeResultado implements JsonFormatter, ListaDAO {
    public static final String DESCRICAO_PADRAO = "Nova Consulta";
    private String email, resumoresult;

    public ListaDeResultado(String em, String re) {
        setEmail(em);
        setResumoresult(re);
    }

    public ListaDeResultado(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email.length() >= 3)
            this.email = email;
    }

    public String getResumoresult() {
        return resumoresult;
    }

    public void setResumoresult(String resumoresult) {
        this.resumoresult = resumoresult;
    }

    /**
     * Método toString sobreposto para exibir em String o dados
     * Nome do paciente
     * Especialidade do Medico
     * Data da consulta
     */
    @Override
    public String toString() {
        return "Email: " + email + "\nResumo: " + resumoresult + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        return this.email.equals(((ListaDeConsulta) obj).getNome());
    }

    @Override
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("email", this.getEmail());
        obj.put("resumo", this.getResumoresult());
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