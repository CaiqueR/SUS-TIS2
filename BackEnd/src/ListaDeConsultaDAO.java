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
public class ListaDeConsultaDAO implements ListaDAO<ListaDeConsulta, String> {

    public ListaDeConsultaDAO() {

    }

    // Metodo para adicionar uma nova Consulta na lista de collection
    @Override
    public void add(ListaDeConsulta p) {
        ListaDeConsulta b = p;
        try (BufferedWriter buffer_saida = new BufferedWriter(new FileWriter("listadeconsulta.txt", true))) {
            String separadorDeLinha = System.getProperty("line.separator");
            buffer_saida.write(b.getNome() + separadorDeLinha);
            buffer_saida.write(b.getEmail() + separadorDeLinha);
            buffer_saida.write(b.getEspecialidade() + separadorDeLinha);
            buffer_saida.write(b.getDataConsulta() + separadorDeLinha);
            buffer_saida.flush();

        } catch (Exception e) {
            System.out.println("ERRO ao adicionar uma nova consultado do Paciente '" + b.getNome() + "' no arquivo!");
            e.printStackTrace();
        }
    }

    // Metodo para obter a consulta requerida
    @Override
    public ListaDeConsulta get(String chave) {
        ListaDeConsulta retorno = null;
        ListaDeConsulta b = null;

        try (BufferedReader buffer_entrada = new BufferedReader(new FileReader("listadeconsulta.txt"))) {
            String idSTR;

            while ((idSTR = buffer_entrada.readLine()) != null) {
                b = new ListaDeConsulta();
                b.setNome(idSTR);
                b.setEmail(buffer_entrada.readLine());
                b.setEspecialidade(buffer_entrada.readLine());
                b.setDataConsulta(LocalDateTime.parse(buffer_entrada.readLine()));

                if (chave.equals(b.getNome())) {
                    retorno = b;
                    break;
                }
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        catch (Exception e) {
            System.out.println("ERRO ao tentar acessar os dados de '" + b.getNome() + "' no arquivo!");
            e.printStackTrace();
        }
        return retorno;
    }

    // Metodo retorna todas os dados de todas as consultas
    @Override
    public List<ListaDeConsulta> getAll() {
        List<ListaDeConsulta> consultas = new ArrayList<ListaDeConsulta>();
        ListaDeConsulta b = null;
        try (BufferedReader buffer_entrada = new BufferedReader(new FileReader("listadeconsulta.txt"))) {
            String idSTR;

            while ((idSTR = buffer_entrada.readLine()) != null) {
                b = new ListaDeConsulta();
                b.setNome(idSTR);
                b.setEmail(buffer_entrada.readLine());
                b.setEspecialidade(buffer_entrada.readLine());
                b.setDataConsulta(LocalDateTime.parse(buffer_entrada.readLine()));
                consultas.add(b);
            }
        } catch (Exception e) {
            System.out.println("ERRO ao acessar toda lista de consulta existentes!");
            e.printStackTrace();
        }
        return consultas;
    }

    @Override
    public ListaDeConsulta update(ListaDeConsulta p) {
        ListaDeConsulta temp = null;
        List<ListaDeConsulta> consultas = getAll();
        int index = consultas.indexOf(p);
        if (index != -1) {
            consultas.set(index, p);
            temp=p;
        }
        saveToFile(consultas);
        return temp;
    }

    @Override
    public ListaDeConsulta delete(ListaDeConsulta p) {
        ListaDeConsulta temp = null;
        List<ListaDeConsulta> consultas = getAll();
        int index = consultas.indexOf(p);
        if (index != -1) {
            temp=p;
            consultas.remove(index);
        }
        saveToFile(consultas);
    return temp;
    }

    private void saveToFile(List<ListaDeConsulta> consultas) {
        try (BufferedWriter buffer_saida = new BufferedWriter(new FileWriter("listadeconsulta.txt", false))) {

            String separadorDeLinha = System.getProperty("line.separator");
            for (ListaDeConsulta b : consultas) {
                buffer_saida.write(b.getNome() + separadorDeLinha);
                buffer_saida.write(b.getEmail() + separadorDeLinha);
                buffer_saida.write(b.getEspecialidade() + separadorDeLinha);
                buffer_saida.write(b.getDataConsulta() + separadorDeLinha);
                buffer_saida.flush();
            }
        } catch (Exception e) {
            System.out.println("ERRO ao gravar lista de consultas no disco!");
            e.printStackTrace();
        }
    }

}