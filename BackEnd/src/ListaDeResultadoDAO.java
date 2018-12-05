import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;

/**
 * Lista para armazenar todas as consultas existentes!
 */
public class ListaDeResultadoDAO implements ListaDAO<ListaDeResultado, String> {

    public ListaDeResultadoDAO() {

    }

    // Metodo para adicionar uma nova Consulta na lista de collection
    @Override
    public void add(ListaDeResultado p) {
        ListaDeResultado b = p;
        try (BufferedWriter buffer_saida = new BufferedWriter(new FileWriter("resultadoConsulta.txt", true))) {
            String separadorDeLinha = System.getProperty("line.separator");
            buffer_saida.write(b.getEmail() + separadorDeLinha);
            buffer_saida.write(b.getResumoresult() + separadorDeLinha);
            buffer_saida.flush();

        } catch (Exception e) {
            System.out.println("ERRO ao adicionar uma novo resultado no arquivo!");
            e.printStackTrace();
        }
    }

    // Metodo para obter a consulta requerida
    @Override
    public ListaDeResultado get(String chave) {
        ListaDeResultado retorno = null;
        ListaDeResultado b = null;

        try (BufferedReader buffer_entrada = new BufferedReader(new FileReader("resultadoConsulta.txt"))) {
            String idSTR;

            while ((idSTR = buffer_entrada.readLine()) != null) {
                b = new ListaDeResultado();
                b.setEmail(idSTR);
                b.setResumoresult(buffer_entrada.readLine());

                if (chave.equals(b.getEmail())) {
                    retorno = b;
                    break;
                }
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        catch (Exception e) {
            System.out.println("ERRO ao tentar acessar os dados no arquivo!");
            e.printStackTrace();
        }
        return retorno;
    }

    // Metodo retorna todas os dados de todas as consultas
    @Override
    public List<ListaDeResultado> getAll() {
        List<ListaDeResultado> consultas = new ArrayList<ListaDeResultado>();
        ListaDeResultado b = null;
        try (BufferedReader buffer_entrada = new BufferedReader(new FileReader("resultadoConsulta.txt"))) {
            String idSTR;

            while ((idSTR = buffer_entrada.readLine()) != null) {
                b = new ListaDeResultado();
                b.setEmail(idSTR);
                b.setResumoresult(buffer_entrada.readLine());
                consultas.add(b);
            }
        } catch (Exception e) {
            System.out.println("ERRO ao acessar toda lista de resultado existentes!");
            e.printStackTrace();
        }
        return consultas;
    }

    @Override
    public ListaDeResultado update(ListaDeResultado p) {
        ListaDeResultado temp = null;
        List<ListaDeResultado> consultas = getAll();
        int index = consultas.indexOf(p);
        if (index != -1) {
            consultas.set(index, p);
            temp=p;
        }
        saveToFile(consultas);
        return temp;
    }

    @Override
    public ListaDeResultado delete(ListaDeResultado p) {
        ListaDeResultado temp = null;
        List<ListaDeResultado> consultas = getAll();
        int index = consultas.indexOf(p);
        if (index != -1) {
            temp=p;
            consultas.remove(index);
        }
        saveToFile(consultas);
        return temp;
    }

    private void saveToFile(List<ListaDeResultado> consultas) {
        try (BufferedWriter buffer_saida = new BufferedWriter(new FileWriter("resultadoConsulta.txt", false))) {

            String separadorDeLinha = System.getProperty("line.separator");
            for (ListaDeResultado b : consultas) {
                buffer_saida.write(b.getEmail() + separadorDeLinha);
                buffer_saida.write(b.getResumoresult() + separadorDeLinha);
                buffer_saida.flush();
            }
        } catch (Exception e) {
            System.out.println("ERRO ao gravar lista de resultado no disco!");
            e.printStackTrace();
        }
    }

}